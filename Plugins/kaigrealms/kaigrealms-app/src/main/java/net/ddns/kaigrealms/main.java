/*
 * @author Colton Tipton
 */

package net.ddns.kaigrealms;

import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin{
    
    public PluginDescriptionFile pdf = this.getDescription();
    
    @Override
    public void onEnable(){
        
        for (Entry<String, Map<String, Object>> command : pdf.getCommands().entrySet()){
            this.getCommand(command.getKey()).setExecutor(new commandExecutor(this));
        }
        
        getLogger().log(Level.INFO, "{0} {1} has been enabled!", new Object[]{pdf.getName(), pdf.getVersion()});
    }
    
    @Override
    public void onDisable(){
        getLogger().log(Level.INFO, "{0} {1} has been disabled!", new Object[]{pdf.getName(), pdf.getVersion()});
    }
    
    
}
