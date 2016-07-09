/*
 * @author Colton Tipton
 */

package net.ddns.kaigrealms;

import java.util.logging.Level;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;


public final class main extends JavaPlugin{
    
    public PluginDescriptionFile pdf = this.getDescription();
    public String[] commands = {"home"};
    
    @Override
    public void onEnable(){
        
        for (String command : commands){
           this.getCommand(command).setExecutor(new commandExecutor(this));
        }
        
        getLogger().log(Level.INFO, "{0} {1} has been enabled!", new Object[]{pdf.getName(), pdf.getVersion()});
    }
    
    @Override
    public void onDisable(){
        getLogger().log(Level.INFO, "{0} {1} has been disabled!", new Object[]{pdf.getName(), pdf.getVersion()});
    }
    
    
}
