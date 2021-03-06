package org.jabelpeeps.sentries;

import java.text.DecimalFormat;
import java.util.StringJoiner;
import java.util.regex.Pattern;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.inventory.ItemStack;
import org.bukkit.projectiles.ProjectileSource;

import net.citizensnpcs.api.ai.NavigatorParameters;
import net.citizensnpcs.api.npc.NPC;

/**
 * An abstract collection of useful static methods.
 */
public abstract class Utils {
    
    public static boolean isLoaded( Location location ) {
        if ( location == null || location.getWorld() == null ) return false;
        
        return location.getWorld().isChunkLoaded( location.getBlockX() >> 4, location.getBlockZ() >> 4 );
    }
    
    public static double sqr( double d ) { return d * d; }
    
    public static void copyNavParams( NavigatorParameters from, NavigatorParameters to ) {
        to.attackRange( from.attackRange() );
        to.attackDelayTicks( from.attackDelayTicks() );
    }
     
    public static void removeMount( int npcid ) {

        NPC npc = Sentries.registry.getById( npcid );

        if ( npc != null ) {
            if ( npc.getEntity() != null ) {
                npc.getEntity().setPassenger( null );
            }
            npc.destroy();
        }
    }

    /**
     * checks a player's permissions for having a bodyguard in the specified world.
     * (the sentry.bodyguard.<world-name> permission.)
     * 
     * @param Entity
     *            entity this is checked to be an instance of Player, and then has perms checked
     * @param worldname
     *            a String identifying the world to be checked.
     * 
     * @returns true if player has permission "sentry.bodyguard.xxx" for the current world.
     */
    public static boolean CanWarp( Entity entity, String worldname ) {

        if ( entity instanceof Player ) {

            Player player = (Player) entity;
            String worldPerm = S.PERM_BODYGUARD + worldname;
            
            if ( player.hasPermission( S.PERM_BODYGUARD + "*" ) ) {
                // all players have "*" perm by default.             
                return !player.isPermissionSet( worldPerm ) || player.hasPermission( worldPerm );
                // returns false if denied in this world.
            }
            return player.hasPermission( worldPerm );
            // no "*"" but specifically allowed this world.
        }
        return false;
    }

    /**
     * Reformat the supplied String, replacing the tags <NPC>, <PLAYER>, <ITEM>,
     * & <AMOUNT> with the names of the objects supplied as arguments, and
     * translating any colour codes.
     * 
     * The method will return immediately if 'input' is null, and will remove
     * the tags related to any other arguments that are null objects.
     */
    public static String format( String input, NPC npc, CommandSender player, ItemStack item, String amount ) {

        if ( input == null ) return "";

        input = input.replace( "<NPC>", (npc == null) ? "" : npc.getName() );
        input = input.replace( "<PLAYER>", (player == null) ? "" : player.getName() );
        input = input.replace( "<ITEM>", (item == null) ? "" : Utils.getLocalItemName( item ) );
        input = input.replace( "<AMOUNT>", (amount == null) ? "" : amount );

        return input;
    }
    
    static Pattern initialDoubleQuote = Pattern.compile( "^\"" );
    static Pattern endDoubleQuote = Pattern.compile( "\"$" );
    static Pattern initialSingleQuote = Pattern.compile( "^'" );
    static Pattern endSingleQuote = Pattern.compile( "'$" );

    /**
     * Convenience method that removes single and double quotes from the ends of
     * the supplied string.
     * 
     * @param input
     *            - the string to be parsed
     * @return - the string without quotes
     */
    public static String removeQuotes( String input ) {

        input = initialDoubleQuote.matcher( input ).replaceAll( "" );
        input = endDoubleQuote.matcher( input ).replaceAll( "" );
        input = initialSingleQuote.matcher( input ).replaceAll( "" );
        input = endSingleQuote.matcher( input ).replaceAll( "" );

        return input;
    }
    
    /**
     * Returns the name of the material or item matching the supplied ID, or
     * "Hand".
     * 
     * @param int
     *            MatID the ID to be named.
     */
    static String getLocalItemName( ItemStack item ) {
        Material mat = item.getType();
        
        if ( mat == null || mat == Material.AIR )
            return "Hand";

        return mat.name();
    }

    /**
     * method to convert String values to int's.
     * <p>
     * It catches any NumberFormatExceptions and returns Integer.MIN_VALUE instead.
     * 
     * @param value
     *            - the string to be converted
     * @return the int value represented by the string.
     */
    public static int string2Int( String value ) {
        try {
            return Integer.parseInt( value );
        } catch ( NumberFormatException e ) {
            return Integer.MIN_VALUE;
        }
    }

    /**
     * method to convert String values to doubles.
     * <p>
     * It catches any NumberFormatExceptions and returns Double.MIN_VALUE instead.
     * 
     * @param value
     *            - the string to be converted
     * @return the double value represented by the string.
     */
    public static double string2Double( String value ) {
        try {
            return Double.parseDouble( value );
        } catch ( NumberFormatException e ) {
            return Double.MIN_VALUE;
        }
    }

    public static SentryTrait getSentryTrait( Entity ent ) {
    
        if ( ent != null && ent instanceof LivingEntity && ent.hasMetadata( S.SENTRIES_META ) ) {
            return getSentryTrait( Sentries.registry.getNPC( ent ) );
        }
        return null;
    }

    static SentryTrait getSentryTrait( NPC npc ) {
    
        if ( npc != null && npc.hasTrait( SentryTrait.class ) ) {
            return npc.getTrait( SentryTrait.class );
        }
        return null;
    }
    
    /**
     * Returns the shooter of a projectile, if possible. (or the source of primed TNT)
     * @param damager - the entity that did some damage.
     * @return damager - if it is already an instanceof living entity. 
     *         The shooter or source - if damager is a projectile or primed TNT.
     *         otherwise - null.
     */
    public static Entity getSource( Entity damager ) {
        
        if ( damager instanceof LivingEntity ) return damager;
        
        if ( damager instanceof Projectile ) {
            ProjectileSource source = ((Projectile) damager).getShooter();

            if ( source instanceof Entity ) return (Entity) source;
        }  
        else if ( damager instanceof TNTPrimed ) {
            Entity thrower = ThrownEntities.getThrower( damager );
            return thrower != null ? thrower : ((TNTPrimed) damager).getSource();
        }
        return null;
    }
    
    /**
     * Concatenates the supplied String[] starting at the position indicated.
     * 
     * @param startFrom
     *            - the starting position (zero-based)
     * @param args
     *            - the String[] to be joined
     * @param delimiter
     *            - a string to be inserted between the joined array's values.
     * @return - the resulting String.
     */
    public static String joinArgs( int startFrom, String[] args, String delimiter ) {

        StringJoiner joiner = new StringJoiner( delimiter );
    
        for ( int i = startFrom; i < args.length; i++ ) {
            joiner.add( args[i] );
        }
        return joiner.toString();
    }
    
    /**
     * Concatenates the supplied String[] starting at the position indicated. 
     * Inserts a space between the values of the array.
     * 
     * @param startFrom
     *            - the starting position (zero-based)
     * @param args
     *            - the String[] to be joined
     * @return - the resulting String.
     */
    public static String joinArgs( int startFrom, String[] args ) {
        return joinArgs( startFrom, args, " " );
    }
    /** Concatenates the args with no delimiter */
    public static String join( String... args ) {
        return String.join( "", args );
    }
    
    /**
     * Convenience method to send messages. Before sending it performs a null-check
     * on sender, and then calls String.join() on the String args, with a delimiter of ""
     */
    public static void sendMessage( CommandSender sender, String...strings ) {
        if ( sender != null ) 
            sender.sendMessage( String.join( "", strings ) );
        else if ( Sentries.debug )
            Sentries.debugLog( String.join( "", strings ));
    }

    public static Pattern colon = Pattern.compile( ":" );
    
    private static DecimalFormat df = new DecimalFormat( "0.0#" );
    public static String formatDbl( double d ) {
        return df.format( d );
    }
    
    public static String prettifyLocation( Location loc ) {
        return join( "World:", loc.getWorld().getName(), " at X:", df.format( loc.getX() ), 
                           " Y:", df.format(  loc.getY() ), " Z:", df.format(  loc.getZ() ) );
    }
}
