package org.jabelpeeps.sentries.pluginbridges;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;
import org.jabelpeeps.sentries.CommandHandler;
import org.jabelpeeps.sentries.PluginBridge;
import org.jabelpeeps.sentries.S;
import org.jabelpeeps.sentries.SentryTrait;
import org.jabelpeeps.sentries.commands.SentriesComplexCommand;
import org.jabelpeeps.sentries.targets.AbstractTargetType;

import com.tommytony.war.Team;
import com.tommytony.war.War;
import com.tommytony.war.Warzone;

public class WarBridge extends PluginBridge {

    Map<SentryTrait, Set<Team>> friends = new HashMap<>();
    Map<SentryTrait, Set<Team>> enemies = new HashMap<>();

    public WarBridge( int flag ) { super( flag ); }

    @Override
    public boolean activate() { return true; }

    @Override
    public String getPrefix() { return "WAR"; }

    @Override
    public String getActivationMessage() { return "War is active, The WAR: target will function"; }

    @Override
    public String getCommandHelp() { return "War:<TeamName> for a War Team."; }

    @Override
    public boolean isTarget( LivingEntity entity, SentryTrait inst ) {

        if ( !enemies.containsKey( inst ) )
            return false;

        return enemies.get( inst ).contains( Team.getTeamByPlayerName( entity.getName() ) );
    }

    @Override
    public boolean isIgnoring( LivingEntity entity, SentryTrait inst ) {

        if ( !friends.containsKey( inst ) )
            return false;

        return friends.get( inst ).contains( Team.getTeamByPlayerName( entity.getName() ) );
    }

    @Override
    public boolean add( SentryTrait inst, String args ) {
        return false;
        // TODO Auto-generated method stub
        
    }

    @Override
    public String add( String target, SentryTrait inst, boolean asTarget ) {

        String targetTeam = CommandHandler.colon.split( target, 2 )[1];
        List<Warzone> zones = War.war.getWarzones();
        Set<Team> teams = new HashSet<Team>();

        for ( Warzone zone : zones ) {
            teams.addAll( zone.getTeams() );
        }

        for ( Team team : teams ) {

            if ( team.getName().equalsIgnoreCase( targetTeam ) )
                return target.concat( addToList( inst, team, asTarget ) );
        }
        return "There is currently no Team matching ".concat( target );
    }

    private String addToList( SentryTrait inst, Team team, boolean asTarget ) {
        
        Map<SentryTrait, Set<Team>> map = asTarget ? enemies : friends;

        if ( !map.containsKey( inst ) )
            map.put( inst, new HashSet<Team>() );

        if ( map.get( inst ).add( team ) )
            return String.join( " ", S.ADDED_TO_LIST, asTarget ? S.TARGETS : S.IGNORES );

        return String.join( " ", S.ALLREADY_ON_LIST, asTarget ? S.TARGETS : S.IGNORES );
    }

    @Override
    public String remove( String entity, SentryTrait inst, boolean fromTargets ) {

        if ( !isListed( inst, fromTargets ) ) {
            return String.join( " ", inst.getNPC().getName(), S.NOT_ANY,
                    "Teams added as", fromTargets ? S.TARGETS : S.IGNORES, S.YET );
        }
        String targetTeam = CommandHandler.colon.split( entity, 2 )[1];

        Map<SentryTrait, Set<Team>> map = fromTargets ? enemies : friends;
        Set<Team> teams = map.get( inst );

        for ( Team team : teams ) {
            if (    team.getName().equalsIgnoreCase( targetTeam )
                    && teams.remove( team ) ) {

                if ( teams.isEmpty() )
                    map.remove( inst );

                return String.join( " ", entity, S.REMOVED_FROM_LIST, fromTargets ? S.TARGETS : S.IGNORES );
            }
        }
        return String.join( " ", entity, S.NOT_FOUND_ON_LIST, fromTargets ? S.TARGETS : S.IGNORES );
    }

    @Override
    public boolean isListed( SentryTrait inst, boolean asTarget ) {

        return (asTarget ? enemies.containsKey( inst )
                         : friends.containsKey( inst ));
    }

    public class WarTeamCommand implements SentriesComplexCommand {

        private String helpTxt;
        
        @Override
        public String getShortHelp() { return ""; }

        @Override
        public String getLongHelp() {

            if ( helpTxt == null )
                helpTxt = "";
            
            return helpTxt;
        }

        @Override
        public String getPerm() { return "sentry.warteam"; }

        @Override
        public void call( CommandSender sender, String npcName, SentryTrait inst, int nextArg, String... args ) {
            // TODO Auto-generated method stub
        }       
    }
    
    public class WarTeamTarget extends AbstractTargetType {
        
        private Team team;

        WarTeamTarget( Team t ) { 
            super( 80 );
            team = t; 
        }
        
        @Override
        public boolean includes( LivingEntity entity ) {
            // TODO Auto-generated method stub
            return false;
        }
        
        @Override
        public boolean equals( Object o ) {
            if (    o != null 
                    && o instanceof WarTeamTarget 
                    && ((WarTeamTarget) o).team.equals( team ) )
                return true;
            
            return false;           
        }       
        @Override
        public int hashCode() { return team.hashCode(); }
    }
}
