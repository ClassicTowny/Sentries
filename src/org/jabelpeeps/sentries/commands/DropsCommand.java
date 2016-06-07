package org.jabelpeeps.sentries.commands;

import org.bukkit.command.CommandSender;
import org.jabelpeeps.sentries.S;
import org.jabelpeeps.sentries.S.Col;
import org.jabelpeeps.sentries.SentriesToggleCommand;
import org.jabelpeeps.sentries.SentryTrait;


public class DropsCommand implements SentriesToggleCommand {

    private String helpTxt;
    @Override
    public boolean call( CommandSender sender, String npcName, SentryTrait inst, Boolean set ) {
        
        inst.dropInventory = (set == null) ? !inst.dropInventory : set;

        sender.sendMessage( String.join( " ", S.Col.GREEN, npcName, 
                            inst.dropInventory ? "will drop items"
                                               : "will not drop items" ) );
        return true;
    }

    @Override
    public String getShortHelp() {
        return "set drops from the Sentry when killed";
    }

    @Override
    public String getLongHelp() {
        
        if ( helpTxt == null ) {
            helpTxt = String.join( "", "do ", Col.GOLD, "/sentry ", S.DROPS, " (on|off| )", Col.RESET,
                            " to set whether the Sentry should drop their equiped items when they die.",
                            " (Specify 'on' or 'off', or leave blank to toggle state.)" );
        }      
        return helpTxt;
    }

    @Override
    public String getPerm() {
        return S.PERM_DROPS;
    }
}
