package org.jabelpeeps.sentries.commands;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.jabelpeeps.sentries.S;
import org.jabelpeeps.sentries.S.Col;
import org.jabelpeeps.sentries.SentryTrait;
import org.jabelpeeps.sentries.Utils;


public class GreetingCommand implements SentriesComplexCommand {

    private String helpTxt;
    
    @Override
    public void call( CommandSender sender, String npcName, SentryTrait inst, int nextArg, String... inargs ) {
        
        if ( inargs.length >= 2 + nextArg ) {

            String str = Utils.removeQuotes( Utils.joinArgs( 1 + nextArg, inargs ) );
            str = ChatColor.translateAlternateColorCodes( '&', str );
            inst.greetingMsg = str;
            
            Utils.sendMessage( sender, Col.GREEN, npcName, ": Greeting message set to:- ", S.Col.RESET, str );
        }
        else {
            Utils.sendMessage( sender, Col.GOLD, npcName, "'s Greeting Message is:- ", S.Col.RESET, inst.greetingMsg );
        }
    }

    @Override
    public String getShortHelp() { return "set how the sentry greets friends"; }

    @Override
    public String getLongHelp() {
        if ( helpTxt == null ) {
            helpTxt = String.join( "", "do ", Col.GOLD, "/sentry ", S.GREETING, " <text to use>", Col.RESET,
                    " to set the sentry's greeting text. <NPC> and <PLAYER> can be used ",
                    "as placeholders, and will be replaced by the appropriate names." );
        }
        return helpTxt;
    }

    @Override
    public String getPerm() { return S.PERM_GREETING; }
}
