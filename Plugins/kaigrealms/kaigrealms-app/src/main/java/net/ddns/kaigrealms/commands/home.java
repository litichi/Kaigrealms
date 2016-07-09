/*
 * @author Colton Tipton
 */

package net.ddns.kaigrealms.commands;

import java.util.logging.Level;
import net.ddns.kaigrealms.*;
import static org.bukkit.Bukkit.getLogger;
import org.bukkit.command.*;
import org.bukkit.entity.Player;


public class home extends commandExecutor{
    
    public home(main plugin) {
        super(plugin);
    }
    
    public void run(CommandSender sender, String[] args){
        
        if (sender instanceof Player){
            Player player = (Player) sender;
            
            getLogger().log(Level.INFO, "argument 1 -> {0}", args[0]);
            
        }else{
            sender.sendMessage("You must be a player!");
        }
        
    }
    
    
    
}
