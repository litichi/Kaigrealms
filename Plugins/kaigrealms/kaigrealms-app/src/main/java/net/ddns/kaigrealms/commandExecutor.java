/*
 * @author Colton Tipton
 */

package net.ddns.kaigrealms;

import java.util.logging.Level;
import net.ddns.kaigrealms.commands.*;
import static org.bukkit.Bukkit.getLogger;
import org.bukkit.command.*;


public class commandExecutor implements CommandExecutor{

    private final main plugin;
    
    public commandExecutor(main plugin){
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
        
        getLogger().log(Level.INFO, "{0}, {1}", new Object[]{cmd.getName(), cmdLabel});
        
        switch(cmdLabel.toLowerCase()){
            
            case "home":
                
                new home(plugin).run(sender, args);
                break;
            default:
                break;
            
        }
        
        return true;
    }

}
