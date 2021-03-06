package org.jabelpeeps.sentries.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.jabelpeeps.sentries.S;
import org.jabelpeeps.sentries.S.Col;
import org.jabelpeeps.sentries.SentryTrait;
import org.jabelpeeps.sentries.Utils;

import lombok.Getter;


public class WarningCommand implements SentriesComplexCommand {

    private String helpTxt;
    @Getter private String shortHelp = "set how the sentry warns enemies";
    @Getter private String perm = S.PERM_WARNING;
    
    @Override
    public void call( CommandSender sender, String npcName, SentryTrait inst, int nextArg, String... inargs ) {
        
        if ( inargs.length >= 2 + nextArg ) {

            String str = Utils.removeQuotes( Utils.joinArgs( 1 + nextArg, inargs ) );
            str = ChatColor.translateAlternateColorCodes( '&', str );
            inst.warningMsg = str;
            
            Utils.sendMessage( sender, Col.GREEN, npcName, ": warning message set to:- ", 
                                       System.lineSeparator(), Col.RESET, str );
        }
        else {
            Utils.sendMessage( sender, Col.GOLD, npcName, "'s warning message is:- ", 
                                       System.lineSeparator(), Col.RESET, inst.warningMsg );
        }
    }

    @Override
    public String getLongHelp() {

        if ( helpTxt == null ) {
            helpTxt = Utils.join( "do ", Col.GOLD, "/sentry ", S.WARNING, " <text to use>", Col.RESET,
                    " to set the sentry's warning text. <NPC> and <PLAYER> can be used ",
                    "as placeholders, and will be replaced by the appropriate names.", System.lineSeparator(),
                    "The warning will only be used if ", Col.GOLD, "/sentry ", S.VOICE_RANGE, Col.RESET, 
                    " is set to a value getter than 0 (the default)" );
        }
        return helpTxt;
    }
}
