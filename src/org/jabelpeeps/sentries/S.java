package org.jabelpeeps.sentries;

import java.util.StringJoiner;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import net.citizensnpcs.api.npc.NPC;

/**
 * A container to externalise the Strings used by Sentries.
 */
public abstract class S {

    public final static String PACKAGE = "org.jabelpeeps.sentries.";

    public interface Col {

        public final static String RED = ChatColor.RED.toString();
        public final static String GOLD = ChatColor.GOLD.toString();
        public final static String GREEN = ChatColor.GREEN.toString();
        public final static String BLUE = ChatColor.BLUE.toString();
        public final static String WHITE = ChatColor.WHITE.toString();
        public final static String YELLOW = ChatColor.YELLOW.toString();
        public final static String RESET = ChatColor.RESET.toString();
        public final static String BOLD = ChatColor.BOLD.toString();
    }

    // Strings used in messages for players and/or console errors.
    public final static String ERROR_NO_CITIZENS = "Sentries cannot be loaded without Citizens 2.0 Aborting";
    public final static String ERROR_WRONG_DENIZEN = "This version of Sentries is only compatable with Denizen 0.9 versions";
    public final static String ERROR_PLUGIN_NOT_FOUND = "Could not find the plugin called ";

    public final static String ERROR = Col.RED.concat( "Error: '" );
    public final static String ERROR_NOT_NUMBER = "' was not recognised as a valid number";

    public final static String ERROR_NOT_SENTRY = "That command can only be used on a Sentry";
    public final static String ERROR_NO_NPC = "You must have an NPC selected or provide an NPC #id";
    public final static String ERROR_ID_INVALID = "Could not find an NPC with #id of ";
    public final static String ERROR_NO_COMMAND_PERM = "You do not have permission for that command";
    public final static String ERROR_NO_MORE_HELP = "No further help is available for this command";

    public final static String GET_COMMAND_HELP = "Use /sentry help for command reference";
    public final static String CANT_FOLLOW = " cannot follow you to ";
    public final static String ADDED_TO_LIST = "was added to this sentry's list of";
    public final static String ALLREADY_ON_LIST = "is already on this sentry's list of";
    public final static String REMOVED_FROM_LIST = "was removed from this sentry's list of";
    public final static String NOT_FOUND_ON_LIST = "was not found on this sentry's list of";
    public final static String NOT_ANY = "does not have any";

    public final static String HELP_LIST = "- to display current list of";
    public final static String HELP_CLEAR = "- to clear the ALL the current";
    public final static String HELP_REMOVE_TYPE = " remove <type:name>";
    public final static String HELP_REMOVE = "- to remove <type:name> from the list";
    public final static String HELP_ADD_TYPE = " add <type:name>";
    public final static String HELP_ADD = "- to add <type:name> to the list";

    public final static String HELP_ADD_REMOVE_TYPES = "  <type:name> can be any of the following: entity:<MobName>, "
                                                        + "entity:monster, entity:player, entity:all, player:<PlayerName>";  

    // a random selection of single words.
    public final static String SECONDS = "seconds";
    public final static String HELP = "help";
    public final static String TARGET = "target";
    public final static String TARGETS = "Targets";
    public final static String IGNORE = "ignore";
    public final static String IGNORES = "Ignores";
    public final static String FOLLOW = "follow";
    public final static String MOUNT = "mount";
    public final static String GUARD = "guard";
    public final static String DROPS = "drops";
    public final static String SPAWN = "spawn";
    public final static String TRUE = "true";
    public final static String FALSE = "false";
    public final static String ON = "On";
    public final static String OFF = "Off";
    public final static String YET = " yet.";
    public final static String ADD = "add";
    public final static String REMOVE = "remove";
    public final static String EQUIP = "equip";
    public final static String INFO = "info";
    public final static String LIST = "list";
    public final static String CLEAR = "clear";
    public final static String CLEARALL = "clearall";
    public final static String JOIN = "join";
    public final static String RELOAD = "reload";

    // Strings used as keys for config values, and npc attributes for saving.
    public final static String RESPAWN_DELAY = "RespawnDelay";
    public final static String WARNING_RANGE = "WarningRange";
    public final static String ATTACK_RATE = "AttackRate";
    public final static String NIGHT_VISION = "NightVision";
    public final static String HEALRATE = "HealRate";
    public final static String FOLLOW_DISTANCE = "FollowDistance";
    public final static String GUARD_TARGET = "GuardTarget";
    public final static String DROP_INVENTORY = "DropInventory";
    public final static String KILLS_DROP = "KillDrops";
    public final static String TARGETABLE = "Targetable";
    public final static String RETALIATE = "Retaliate";
    public final static String INVINCIBLE = "Invincible";
    public final static String CRITICAL_HITS = "CriticalHits";
    public final static String DEFAULT_TARGETS = "DefaultTargets";
    public final static String DEFAULT_IGNORES = "DefaultIgnores";
    public final static String IGNORE_LOS = "IgnoreLOS";
    public final static String GREETING = "Greeting";
    public final static String WARNING = "Warning";
    public final static String HEALTH = "Health";
    public final static String RANGE = "Range";
    public final static String RESPAWN = "Respawn";
    public final static String SPEED = "Speed";
    public final static String WEIGHT = "Weight";
    public final static String ARMOR = "Armor";
    public final static String ARMOUR = "Armour";
    public final static String STRENGTH = "Strength";
    public final static String MOUNTID = "MountID";

    // Some strings to hold the names of external plugins in one location (in case of future changes to the names.)
    public final static String CITIZENS = "Citizens";
    public final static String DENIZEN = "Denizen";
    // the last one is not an external plugin, but refers to the minecraft scoreboard system.
    public final static String SCORE = "ScoreboardTeams";

    // Strings used for permission strings - these *must* be the same as those in plugin.yml
    public final static String PERM_TARGET = "sentry.target";
    public final static String PERM_IGNORE = "sentry.ignore";
    public final static String PERM_INFO = "sentry.info";
    public final static String PERM_EQUIP = "sentry.equip";
    public final static String PERM_RELOAD = "sentry.reload";
    public final static String PERM_DEBUG = "sentry.debug";
    public final static String PERM_SPAWN = "sentry.spawn";
    public final static String PERM_GUARD = "sentry.guard";
    public final static String PERM_WARNING = "sentry.warning";
    public final static String PERM_GREETING = "sentry.greeting";
    public final static String PERM_WARNING_RANGE = "sentry.stats.warningrange";
    public final static String PERM_SPEED = "sentry.stats.speed";
    public final static String PERM_RANGE = "sentry.stats.range";
    public final static String PERM_HEALTH = "sentry.stats.health";
    public final static String PERM_HEAL_RATE = "sentry.stats.healrate";
    public final static String PERM_ARMOUR = "sentry.stats.armour";
    public final static String PERM_STRENGTH = "sentry.stats.strength";
    public final static String PERM_NIGHTVISION = "sentry.stats.nightvision";
    public final static String PERM_ATTACK_RATE = "sentry.stats.attackrate";
    public final static String PERM_RESPAWN_DELAY = "sentry.stats.respawn";
    public final static String PERM_FOLLOW_DIST = "sentry.stats.follow";
    public final static String PERM_MOUNT = "sentry.options.mount";
    public final static String PERM_TARGETABLE = "sentry.options.targetable";
    public final static String PERM_KILLDROPS = "sentry.options.killdrops";
    public final static String PERM_DROPS = "sentry.options.drops";
    public final static String PERM_CRITICAL_HITS = "sentry.options.criticals";
    public final static String PERM_RETALIATE = "sentry.options.retaliate";
    public final static String PERM_INVINCIBLE = "sentry.options.invincible";
    public final static String PERM_BODYGUARD = "sentry.bodyguard."; // be sure to leave the last '.'
    public final static String PERM_CITS_ADMIN = "citizens.admin";

    /**
     * Convenience method to send a formatted message to the player regarding
     * the respawn status of the npc.
     * 
     * @param value
     *            - the number of seconds set as the respawn value.
     * @param npc
     *            - the npc
     * @param player
     *            - the player who sent the command.
     */
    static void respawnCommandMessage( int value, NPC npc, CommandSender player ) {

        if ( value == -1 )
            player.sendMessage( String.join( "", Col.GOLD, npc.getName(), " will be deleted upon death" ) );
        if ( value == 0 )
            player.sendMessage( String.join( "", Col.GOLD, npc.getName(), " will not automatically respawn" ) );
        if ( value > 0 )
            player.sendMessage( String.join( "", Col.GOLD, npc.getName(), " respawns after ", String.valueOf( value ), SECONDS ) );
    }
    
    static String guardCommandHelp;
    /**
     *  Returns the formatted help string for the guard command, lazily initialising it if needed.   
     */
    static String guardCommandHelp() {
        
        if ( guardCommandHelp == null ) {

            StringJoiner joiner = new StringJoiner( System.lineSeparator() ).add( "" );

            joiner.add( String.join( "", Col.GOLD, "do '/sentry guard'", Col.RESET ) );
            joiner.add( "  to discover what a sentry is guarding" );
            joiner.add( String.join( "", Col.GOLD, "do '/sentry guard clear'", Col.RESET ) );
            joiner.add( "  to clear the player/npc being guarded" );
            joiner.add( String.join( "", Col.GOLD, "do '/sentry guard (-p/l) <EntityName>'", Col.RESET ) );
            joiner.add( "  to have a sentry guard a player, or another NPC" );
            joiner.add( String.join( "", Col.GOLD, "    -p ", Col.RESET, "-> only search player names" ) );
            joiner.add( String.join( "", Col.GOLD, "    -l ", Col.RESET, "-> only search local entities" ) );
            joiner.add( "       -> only use one of -p or -l (or omit)" );

            guardCommandHelp = joiner.toString();
        }
        return guardCommandHelp;
    }
    
    static String equipCommandHelp;    
    /**
     *  Returns the formatted help string for the equip command, lazily initialising it if needed.   
     */
    static String equipCommandHelp() {

        if ( equipCommandHelp == null ) {

            StringJoiner joiner = new StringJoiner( System.lineSeparator() ).add( "" );

            joiner.add( String.join( "", Col.GOLD, "do '/sentry equip <ItemName>'", Col.RESET ) );
            joiner.add( "  to give the named item to the sentry" );
            joiner.add( "  item names are the offical MC item names" );
            joiner.add( String.join( "", Col.GOLD, "do '/sentry equip clearall'", Col.RESET ) );
            joiner.add( "  to clear all equipment slots." );
            joiner.add( String.join( "", Col.GOLD, "do '/sentry equip clear <slot>'", Col.RESET ) );
            joiner.add( "  to clear the specified slot, where slot can be one of: hand, helmet, chestplate, leggings or boots." );

            equipCommandHelp = joiner.toString();
        }
        return equipCommandHelp;
    }
    
    
    static String ignoreCommandHelp;

    static String ignoreCommandHelp() {

        if ( ignoreCommandHelp == null ) {

            StringJoiner joiner = new StringJoiner( System.lineSeparator() ) .add( "" );

            joiner.add( String.join( "", Col.GOLD, "do '/sentry ignore <option>' where <option is:-", Col.RESET ) );
            joiner.add( String.join( " ", Col.GOLD, "", LIST, Col.RESET, HELP_LIST, IGNORES ) );
            joiner.add( String.join( " ", Col.GOLD, "", CLEAR, Col.RESET, HELP_CLEAR, IGNORES ) );
            joiner.add( String.join( " ", Col.GOLD, HELP_ADD_TYPE, Col.RESET, HELP_ADD ) );
            joiner.add( String.join( " ", Col.GOLD, HELP_REMOVE_TYPE, Col.RESET, HELP_REMOVE ) );
            joiner.add( HELP_ADD_REMOVE_TYPES );
            joiner.add( getAdditionalTargets() );

            ignoreCommandHelp = joiner.toString();
        }
        return ignoreCommandHelp;
    }

    /**
     * iterates over the activated PluginBridges, polling each one for command
     * help text.
     * 
     * @return - the concatenated help Strings
     */
    public static String getAdditionalTargets() {
        String outString = "";

        if ( !Sentries.activePlugins.isEmpty() ) {
            StringJoiner joiner = new StringJoiner( System.lineSeparator() );

            joiner.add( "You may also use these additional types:- " );

            for ( PluginBridge each : Sentries.activePlugins.values() ) {
                joiner.add( each.getCommandHelp() );
            }
            outString = joiner.toString();
        }
        return outString;
    }
    
    static String mainHelpOutro;

    static String mainHelpOutro() {
        
        if ( mainHelpOutro == null ) {
            StringJoiner joiner = new StringJoiner( System.lineSeparator() );
    
            joiner.add( String.join( "", Col.GOLD, "-------------------------------", Col.RESET ) );
            joiner.add( String.join( "", "If '...' is shown do ", Col.GOLD, "/sentry help <command>", Col.RESET, " for further help" ) );
            joiner.add( String.join( "", Col.GOLD, "-------------------------------", Col.RESET ) );
            joiner.add( String.join( "", "Select NPC's with ", Col.GOLD, "'/npc sel'", Col.RESET, " before running commands, or use ",
                    Col.GOLD, "/sentry #npcid <command> [args]", Col.RESET, " to run command on the sentry with the given npcid number." ) );
            joiner.add( String.join( "", Col.GOLD, "-------------------------------", Col.RESET ) );
    
            mainHelpOutro = joiner.toString();
        }
        return mainHelpOutro;
    }
    
}
