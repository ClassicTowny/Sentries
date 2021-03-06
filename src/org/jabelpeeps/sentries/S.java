package org.jabelpeeps.sentries;

import org.bukkit.ChatColor;

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
    public final static String ERROR_WRONG_DENIZEN = "This version of Sentries is only compatable with Denizen 0.9 & 1.0 versions";
    public final static String ERROR_PLUGIN_NOT_FOUND = "Could not find the plugin called ";

    public final static String ERROR = Col.RED + "Error: ";
    public final static String ERROR_NOT_NUMBER = " was not recognised as a valid number";
    public final static String ERROR_NOT_SENTRY = "That command can only be used on a sentry";
    public final static String ERROR_NO_NPC = "You must have an NPC selected or provide an NPC #id";
    public final static String ERROR_ID_INVALID = "Could not find an NPC with #id of ";
    public final static String ERROR_NO_COMMAND_PERM = "You do not have permission for that command";
    public final static String ERROR_UNKNOWN_COMMAND = "The command name was not recognised";

    public final static String GET_COMMAND_HELP = "Use /sentry help for command reference";
    public final static String CANT_FOLLOW = " cannot follow you to ";
    public final static String ADDED_TO_LIST = "was added to this sentry's list of";
    public final static String ALLREADY_ON_LIST = "is already on this sentry's list of";
    public final static String REMOVED_FROM_LIST = "was removed from this sentry's list of";
    public final static String NOT_FOUND_ON_LIST = "was not found on this sentry's list of";
    public final static String NOT_ANY = "does not have any";
    public final static String ALREADY_LISTED = " is already listed as either a target or ignore for ";

    public final static String HELP_ADD_REMOVE_TYPES = " can be any of the following: ";  

    // words used as command options, keep all lower case.
    public final static String SET_SPAWN = "setspawn";
    public final static String TARGET = "target";
    public final static String IGNORE = "ignore";
    public final static String EVENT = "event";
    public final static String EQUIP = "equip";
    public final static String GUARD = "guard";
    public final static String RETALIATE = "retaliate";
    public final static String INVINCIBLE = "invincible";
    public final static String DROPS = "drops";
    public final static String KILLS_DROP = "killsdrop";
    public final static String CRITICALS = "criticals";
    public final static String MOBS_ATTACK = "mobsattack";
    public final static String MOUNT = "mount";
    public final static String RELOAD = "reload";
    public final static String GREETING = "greeting";
    public final static String WARNING = "warning";
    public final static String FOLLOW = "follow";
    public final static String HEALTH = "health";
    public final static String NIGHT_VISION = "nightvision";
    public final static String ARMOUR = "armour";
    public final static String ARMOR = "armor";
    public final static String STRENGTH = "strength";
    public final static String RESPAWN = "respawn";
    public final static String SPEED = "speed";
    public final static String ATTACK_RATE = "attackrate";
    public final static String HEALRATE = "healrate";
    public final static String RANGE = "range";
    public final static String VOICE_RANGE = "voicerange";
    public final static String ADD = "add";
    public final static String REMOVE = "remove";
    public final static String INFO = "info";
    public final static String LIST = "list";
    public final static String LIST_MOBS = "listmobs";
    public final static String CLEAR = "clear";
    public final static String CLEARALL = "clearall";
    public final static String JOIN = "join";
    public final static String LEAVE = "leave";
    public final static String HELP = "help";
    
    // a random selection of single words.
    public final static String SECONDS = "seconds";
    public final static String TRUE = "true";
    public final static String FALSE = "false";
    public final static String ON = "On";
    public final static String OFF = "Off";
    public final static String YET = " yet.";
    
    // Strings used as keys for config values, and npc attributes for saving.
    // these need to correspond exactly with the key Strings in the config.yml 
    public final static String CON_STRENGTH = "Strength";
    public final static String CON_ARMOUR = "Armour";
    public final static String CON_NIGHT_VIS = "NightVision";
    public final static String CON_RANGE = "Range";
    public final static String CON_VOICE_RANGE = "VoiceRange";
    public final static String CON_RESPAWN_DELAY = "RespawnDelay";
    public final static String CON_FOLLOW_DIST = "FollowDistance";
    public final static String CON_SPEED = "Speed";
    public final static String CON_HEALTH = "Health";
    public final static String CON_ARROW_RATE = "ArrowRate";
    public final static String CON_HEAL_RATE = "HealRate";
    public final static String CON_WEIGHT = "Weight";
    public final static String CON_DROP_INV = "DropInventory";
    public final static String CON_KILLS_DROP = "KillsDrop";
    public final static String CON_CRIT_HITS = "CriticalHits";
    public final static String CON_INVINCIBLE = "Invincible";
    public final static String CON_RETALIATION = "Retaliation";
    public final static String CON_MOBS_ATTACK = "MobsAttack";
    public final static String CON_IGNORE_LOS = "IgnoreLOS";
    public final static String CON_WEAPON4STRGTH = "UseWeapon4Strength";
    public final static String CON_NEW_ARMOUR_CALC = "UseNewArmourCalc";
    public final static String DEFAULT_TARGETS = "DefaultTargets";
    public final static String DEFAULT_IGNORES = "DefaultIgnores";
    public final static String PERSIST_MOUNT = "MountID";
    public final static String PERSIST_GUARDEE = "PersistGuardee";
    public final static String CON_WARNING = "Warning";
    public final static String CON_GREETING = "Greeting";
    public final static String PERSIST_SPAWN = "Spawn";
    public final static String TARGETS = "Targets";
    public final static String IGNORES = "Ignores";
    public final static String EVENTS = "Events";

    // Some strings to hold the names of external plugins in one location (in case of future changes to the names.)
    public final static String CITIZENS = "Citizens";
    public final static String DENIZEN = "Denizen";
    // the last one is not an external plugin, but refers to the minecraft scoreboard system.
    public final static String SCORE = "ScoreboardTeams";

    // Strings used for permission strings - these *must* be the same as those in plugin.yml
    public final static String PERM_TARGET = "sentry.target";
    public final static String PERM_IGNORE = "sentry.ignore";
    public final static String PERM_EVENT = "sentry.event";
    public final static String PERM_DEBUGINFO = "sentry.debuginfo";
    public final static String PERM_INFO = "sentry.info";
    public final static String PERM_EQUIP = "sentry.equip";
    public final static String PERM_RELOAD = "sentry.reload";
    public final static String PERM_SET_SPAWN = "sentry.setspawn";
    public final static String PERM_GUARD = "sentry.guard";
    public final static String PERM_WARNING = "sentry.warning";
    public final static String PERM_GREETING = "sentry.greeting";
    public final static String PERM_LIST_ALL = "sentry.listall";
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
    public final static String SENTRIES_META = "SentriesNPC";
}
