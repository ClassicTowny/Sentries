package org.jabelpeeps.sentries;

/**
 *  Base interface for Sentries Plugin commands.
 *  <p>
 *  NOTE: Do not implement this interface directly, is not complete as-is, 
 *  instead implement either {@link#SentriesComplexCommand} or 
 *  {@link#SentriesToggleCommand} which provide different call() methods.
 */
public interface SentriesCommand {

    /**
     * @return a short (<1 line) String of help text, for the command listing.
     */
    String getShortHelp();
    
    /**
     * @return a multi-line (if needed) String of help text, giving full details
     * of possible arguments that can be used, and expected results.
     */
    String getLongHelp();
    
    /**
     * @return a String representing the permission node for the command.
     * It is recommended to use one of the Strings from the 'S' class in order to
     * keep things DRY.
     */
    String getPerm();
}
