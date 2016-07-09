/*
 * @author Colton Tipton
 */

package net.ddns.kaigrealms;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.ddns.kaigrealms.commands.*;
import org.bukkit.command.*;


public class commandExecutor implements CommandExecutor{

    private final main plugin;
    
    public commandExecutor(main plugin){
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
        
        plugin.getLogger().log(Level.INFO, "{0}, {1}", new Object[]{cmd.getName(), cmdLabel});
        
        switch(cmdLabel.toLowerCase()){
            
            case "home":
                try {
                    new home(plugin).run(sender, args);
                } catch (IOException ex) {
                    Logger.getLogger(commandExecutor.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
           case "warp":
               try {
                    new warp(plugin).run(sender, args);
                } catch (IOException ex) {
                    Logger.getLogger(commandExecutor.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            default:
                break;
            
        }
        
        return true;
    }

}
