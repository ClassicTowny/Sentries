package org.jabelpeeps.sentries.commands;

import org.bukkit.command.CommandSender;
import org.jabelpeeps.sentries.S;
import org.jabelpeeps.sentries.S.Col;
import org.jabelpeeps.sentries.SentryTrait;
import org.jabelpeeps.sentries.Util;


public class HealRateCommand implements SentriesNumberCommand {

    private String helpTxt;
    
    @Override
    public boolean call( CommandSender sender, String npcName, SentryTrait inst, String number ) {
        
        if ( number == null ) {
            sender.sendMessage( 
                String.join( "", Col.GOLD, npcName," will heal every ", String.valueOf( inst.healRate ), " seconds" ) );
        }
        else {
            double healrate = Util.string2Double( number );
            if ( healrate < 0.0 ) {
                sender.sendMessage( String.join( "", S.ERROR, number, S.ERROR_NOT_NUMBER ) );
                return true;
            }
            if ( healrate > 300.0 ) healrate = 300.0;
            
            inst.healRate = healrate;
            sender.sendMessage( 
                String.join( " ", Col.GREEN, npcName, " will now heal every ", String.valueOf( healrate ), " seconds" ) );
        }
        return true;
    }

    @Override
    public String getShortHelp() {
        return "set how fast a sentry will heal";
    }

    @Override
    public String getLongHelp() {

        if ( helpTxt == null ) {
            helpTxt = String.join( "", "do ", Col.GOLD, "/sentry ", S.HEALRATE, " (#)", Col.RESET, System.lineSeparator(),
            "  where # is the number (0-300) of seconds the sentry will take to heal 1 HP.",
            System.lineSeparator(), "  0 = no healing (this is the default setting)",
            System.lineSeparator(), "If no number is given the current value is shown.");
        }
        return helpTxt;
    }

    @Override
    public String getPerm() {
        return S.PERM_HEAL_RATE;
    }
}