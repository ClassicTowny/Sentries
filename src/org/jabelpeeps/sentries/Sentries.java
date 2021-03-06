package org.jabelpeeps.sentries;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Projectile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jabelpeeps.sentries.commands.ExportCommand;
import org.jabelpeeps.sentries.commands.ImportCommand;

import net.aufdemrand.denizen.Denizen;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPCRegistry;
import net.citizensnpcs.api.trait.TraitInfo;

public class Sentries extends JavaPlugin {

    public static boolean debug = false;
    public static boolean bodyguardsObeyProtection, ignoreListIsInvincible, useNewArmourCalc;

    static boolean denizenActive = false;
    static Set<PluginBridge> activePlugins = new HashSet<>();

    // Lists of various armour items that will be accepted, by default.
    public static Set<Material> boots = EnumSet.of( Material.LEATHER_BOOTS, Material.CHAINMAIL_BOOTS, 
            Material.IRON_BOOTS, Material.DIAMOND_BOOTS, Material.GOLD_BOOTS );

    public static Set<Material> chestplates = EnumSet.of( Material.LEATHER_CHESTPLATE, Material.CHAINMAIL_CHESTPLATE, 
            Material.IRON_CHESTPLATE, Material.DIAMOND_CHESTPLATE, Material.GOLD_CHESTPLATE );

    public static Set<Material> helmets = EnumSet.of( Material.LEATHER_HELMET, Material.CHAINMAIL_HELMET, Material.IRON_HELMET,
            Material.DIAMOND_HELMET, Material.GOLD_HELMET, Material.PUMPKIN, Material.JACK_O_LANTERN );

    public static Set<Material> leggings = EnumSet.of( Material.LEATHER_LEGGINGS, Material.CHAINMAIL_LEGGINGS, 
            Material.IRON_LEGGINGS, Material.DIAMOND_LEGGINGS, Material.GOLD_LEGGINGS );
    
    public static Set<EntityType> mobs = EnumSet.allOf( EntityType.class );

    static {
        mobs.removeIf( e -> !e.isAlive() );
    }
    
    static Map<Material, Double> armorValues = new EnumMap<>( Material.class );
    static Map<Material, Double> speedBuffs = new EnumMap<>( Material.class );
    static Map<Material, Double> weaponStrengths = new EnumMap<>( Material.class );
    static Map<Material, List<PotionEffect>> weaponEffects = new EnumMap<>( Material.class );

    static Map<String, Boolean> defBooleans = new HashMap<>();
    static Map<String, Integer> defIntegers = new HashMap<>();
    static Map<String, Double> defDoubles = new HashMap<>();
    static List<String> defaultTargets;
    static List<String> defaultIgnores;
    static String defaultGreeting = "";
    static String defaultWarning = "";

    static int logicTicks = 10;
    static int sentryEXP = 5;

    public Queue<Projectile> arrows = new LinkedList<>();

    static Logger logger;
    public static Sentries plugin;
    static DenizenHook denizenHook;
    public static NPCRegistry registry;

    @Override
    public void onEnable() {

        if ( plugin != null ) return;
        plugin = this;
        logger = getLogger();
        PluginManager pluginManager = Bukkit.getPluginManager();
        
        if ( !checkPlugin( S.CITIZENS ) ) {
            logger.log( Level.SEVERE, S.ERROR_NO_CITIZENS );
            pluginManager.disablePlugin( this );
            return;
        }
        
        CommandHandler handler = new CommandHandler();
        getCommand( "sentries" ).setExecutor( handler );
        
        boolean sentinelEnabled = pluginManager.isPluginEnabled( "Sentinel" );
        if ( pluginManager.isPluginEnabled( "Sentry" ) ) {
            CommandHandler.addCommand( "import", new ImportCommand() );
        }
        else {
            getCommand( "sentry" ).setExecutor( handler );
            if ( sentinelEnabled ) {
                CommandHandler.addCommand( "import", new ImportCommand() );               
            }
        }
        if ( sentinelEnabled )
            CommandHandler.addCommand( "export", new ExportCommand() );
        
        registry = CitizensAPI.getNPCRegistry();     
        reloadMyConfig();  // must happen before the trait is registered.
        
        if ( checkPlugin( S.DENIZEN ) ) {

            String vers = pluginManager.getPlugin( S.DENIZEN ).getDescription().getVersion();

            if ( vers.startsWith( "0.9" ) || vers.startsWith( "1.0" ) ) {

                denizenHook = new DenizenHook( (Denizen) pluginManager.getPlugin( S.DENIZEN ) );
                denizenActive = DenizenHook.npcDeathTriggerActive || DenizenHook.npcDeathTriggerOwnerActive;
            }
            else {
                logger.log( Level.WARNING, S.ERROR_WRONG_DENIZEN );
            }
        }
        CitizensAPI.getTraitFactory().registerTrait( TraitInfo.create( SentryTrait.class ).withName( "sentries" ) );

        pluginManager.registerEvents( new SentryListener(), this );
        
        Bukkit.getScheduler().scheduleSyncRepeatingTask( this, 
                                    () -> { while ( arrows.size() > 200 ) {
                                                Projectile arrow = arrows.remove();
                                                
                                                if ( arrow != null ) arrow.remove();
                                            }
                                    }, 40, 20 * 120 );
    }
 
    void reloadMyConfig() {

        saveDefaultConfig();
        reloadConfig();

        FileConfiguration config = getConfig();

        loadIntoMaterialMap( config, "ArmorValues", armorValues );
        loadIntoMaterialMap( config, "WeaponStrengths", weaponStrengths );
        loadIntoMaterialMap( config, "SpeedBuffs", speedBuffs );

        loadWeaponEffects( config, "WeaponEffects", weaponEffects );

        loadIntoSet( config, "Helmets", helmets );
        loadIntoSet( config, "Chestplates", chestplates );
        loadIntoSet( config, "Leggings", leggings );
        loadIntoSet( config, "Boots", boots );

        AttackType.loadWeapons( config );
        Hits.loadConfig( config );

        bodyguardsObeyProtection = config.getBoolean( "Server.BodyguardsObeyProtection", true );
        ignoreListIsInvincible = config.getBoolean( "Server.IgnoreListInvincibility", true );
        useNewArmourCalc = config.getBoolean( "Server.UseNewArmourCalc", false );

        logicTicks = config.getInt( "Server.LogicTicks", 10 );
        sentryEXP = config.getInt( "Server.ExpValue", 5 );

        loadIntoStringMap( config, "DefaultOptions", defBooleans );
        loadIntoStringMap( config, "DefaultStats", defIntegers );
        loadIntoStringMap( config, "DefaultValues", defDoubles );
        if ( !defDoubles.containsKey( S.CON_ARMOUR ) ) defDoubles.put( S.CON_ARMOUR, 0.0 );
        if ( !defDoubles.containsKey(  S.CON_STRENGTH ) ) defDoubles.put( S.CON_STRENGTH, 1.0 );
        defaultTargets = config.getStringList( S.DEFAULT_TARGETS );
        defaultIgnores = config.getStringList( S.DEFAULT_IGNORES );
        defaultWarning = config.getString( "DefaultTexts.Warning" );
        defaultGreeting = config.getString( "DefaultTexts.Greeting" );
        
        for ( String each : config.getStringList( "OtherPlugins" ) ) {
            if ( !checkPlugin( each ) ) continue;

            PluginBridge bridge = null;

            try {
                Class<?> clazz = Class.forName( S.PACKAGE + "pluginbridges." + each + "Bridge" );

                bridge = clazz.asSubclass( PluginBridge.class ).getDeclaredConstructor().newInstance();
                
            } catch ( ClassNotFoundException | InstantiationException | IllegalAccessException |
                      ClassCastException | InvocationTargetException | NoSuchMethodException | SecurityException e ) {
                logger.log( Level.WARNING, "Error loading PluginBridge for the plugin: '" + each + "' from config.yml. " );
                e.printStackTrace();
            }

            if ( bridge == null || !bridge.activate() ) continue;           

            if ( debug ) debugLog( each + " activated" ); 
            
            logger.log( Level.INFO, bridge.getActivationMessage() );
            activePlugins.add( bridge );
        }
    }

    @Override
    public void onDisable() {
        logger.log( Level.INFO, " v" + getDescription().getVersion() + " disabled." );
        Bukkit.getScheduler().cancelTasks( this );
        plugin = null;
    }

    public void loadIntoSet( FileConfiguration config, String key, Set<Material> set ) {

        if ( config.getBoolean( "UseCustom" + key ) ) {

            List<String> strings = config.getStringList( key );

            if ( strings.size() > 0 ) {
                if ( debug ) debugLog( strings.toString() );
                
                set.clear();

                for ( String each : strings )
                    set.add( Material.getMaterial( each.trim() ) );
            }
        }
    }

    private void loadIntoMaterialMap( FileConfiguration config, String node, Map<Material, Double> map ) {
        map.clear();

        for ( String each : config.getStringList( node ) ) {
            if ( debug ) debugLog( each );

            String[] args = each.trim().split( " " );

            if ( args.length != 2 ) continue;

            double val = Utils.string2Double( args[1] );
            Material item = Material.getMaterial( args[0] );

            if (    item != null 
                    && val != Double.MIN_VALUE ) {

                map.put( item, val );
            }
        }
    }

    @SuppressWarnings( "unchecked" )
    private <T> void loadIntoStringMap( FileConfiguration config, String node, Map<String, T> map ) {
        map.clear();
        for ( Map.Entry<String, Object> each : config.getConfigurationSection( node ).getValues( false ).entrySet() )
            map.put( each.getKey(), (T) each.getValue() );
    }

    private void loadWeaponEffects( FileConfiguration config, String path, Map<Material, List<PotionEffect>> map ) {
        map.clear();

        for ( String each : config.getStringList( path ) ) {
            
            String[] args = each.trim().split( " " );

            if ( args.length < 2 ) continue;

            Material item = Material.getMaterial( args[0] );

            if ( item == null ) continue;

            List<PotionEffect> list = new ArrayList<>();

            for ( String string : args ) {

                PotionEffect val = getPotionEffect( string );

                if ( val != null ) list.add( val );
            }
            if ( !list.isEmpty() ) map.put( item, list );
        }
    }

    private PotionEffect getPotionEffect( String string ) {
        if ( string == null ) return null;

        String[] args = string.trim().split( ":" );

        int dur = 10;
        int amp = 1;

        PotionEffectType type = PotionEffectType.getByName( args[0] );

        if ( type == null ) return null;

        if ( args.length > 1 ) {
            dur = Utils.string2Int( args[1] );

            if ( dur < 0 ) dur = 10;
        }
        if ( args.length > 2 ) {
            amp = Utils.string2Int( args[2] );

            if ( amp < 0 ) amp = 1;
        }
        return new PotionEffect( type, dur, amp );
    }

    /**
     * Check if a named plugin is loaded and enabled.
     * <p>
     * If returning false, this method also calls:-<br>
     * getLogger().log( Level.INFO, "Could not find or register with " + name )
     * 
     * @param name
     *            - the name of the plugin.
     * @return true - if it is loaded <strong>and</strong> enabled.
     */
    private boolean checkPlugin( String name ) {

        if ( S.SCORE.equals( name ) ) return true;

        PluginManager pluginManager = Bukkit.getPluginManager();

        if (    pluginManager.getPlugin( name ) != null
                && pluginManager.getPlugin( name ).isEnabled() ) {

            if ( debug ) debugLog( name + " found by bukkit/spigot." );

            return true;
        }
        if ( debug ) debugLog( S.ERROR_PLUGIN_NOT_FOUND.concat( name ) );

        return false;
    }
    
    /** Returns the slot number appropriate to hold the supplied material, as defined in 
     * the config.yml (or the default config).  If the supplied material does not match any of
     * the configured armour types, then this method will return 0 (the slot number for the main hand). */
    public static int getSlot( Material equipment ) {      
        if ( helmets.contains( equipment ) ) return 1;
        if ( chestplates.contains( equipment ) ) return 2;
        if ( leggings.contains( equipment ) ) return 3;
        if ( boots.contains( equipment ) ) return 4;
        if ( Material.SHIELD == equipment ) return 5;       
        return 0;
    }
    
    /**
     * Sends a message to the logger associated with this plugin.
     * <p>
     * The caller should check the boolean Sentries.debug is true before calling -
     * to avoid the overhead of compiling the String if it is not needed.
     * 
     * @param s
     *            - the message to log.
     */
    public static void debugLog( String s ) {
        logger.info( s );
    }
}
