/*
 * @author Colton Tipton
 */
package net.ddns.kaigrealms;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.*;
import java.util.logging.Level;
import static org.bukkit.Bukkit.*;
import org.bukkit.configuration.file.*;

public class config {
    
    private final main plugin;
    private List<File> configFiles = new ArrayList<>();
    private File file;
    private FileConfiguration config;

    public config(main plugin){
        this.plugin = plugin;
    }
        
    public boolean configFileExists(String name) {
        for (File configFile : configFiles) {
            if (configFile.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public File getConfigFile(String name) {
        for (File configFile : configFiles) {
            if (configFile.getName().equals(name)) {
                return configFile;
            }
        }
        return null;
    }

    public void reloadConfig(String name) throws java.io.IOException {
        if (!configFileExists(name)) {
            file = new File(plugin.getDataFolder(), name);
            configFiles.add(file);
        } else {
            file = getConfigFile(name);
        }

        config = YamlConfiguration.loadConfiguration(file);

        // Look for defaults in the jar
        Reader defConfigStream = new InputStreamReader(plugin.getResource(name), "UTF8");
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            config.setDefaults(defConfig);
        }
    }

    public FileConfiguration getConfig(String name) throws IOException {
        if (config == null) {
            reloadConfig(name);
        }
        return config;
    }

    public void saveConfig(String name) {
        if (config == null || config == null) {
            return;
        }
        try {
            file = getConfigFile(name);
            getConfig(name).save(file);
        } catch (IOException ex) {
            getLogger().log(Level.SEVERE, "Could not save config to " + file, ex);
        }
    }

    public void saveDefaultConfig(String name) {
        if (!configFileExists(name)) {
            file = new File(plugin.getDataFolder(), name);
        }
        if (!file.exists()) {
            plugin.saveResource(name, false);
        }
    }

}
