/*
 * @author Colton Tipton
 */
package net.ddns.kaigrealms.commands;

import java.io.IOException;
import java.util.*;
import net.ddns.kaigrealms.*;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class home {

    private final main plugin;
    private final config config;

    public home(main plugin) {
        this.plugin = plugin;
        this.config = new config(plugin);
    }

    public void run(CommandSender sender, String[] args) throws IOException {

        if (sender instanceof Player) {

            Player player = (Player) sender;
            FileConfiguration con = config.getConfig("homes.yml");

            if (args.length == 0) {
                player.sendMessage("/home [place] -- to teleport to your [place].");
                player.sendMessage("/home set [place] -- to create a [place].");
                player.sendMessage("/home remove [place] -- to remove a [place].");
                player.sendMessage("/home list -- to list your places.");
                return;
            }

            if (con != null) {
                if (con.contains("players." + player.getName())) {
                    if (args[0] != null) {
                        switch (args[0]) {
                            case "set":
                                this.set(con, player, args);
                                break;
                            case "remove":
                                this.remove(con, player, args);
                                break;
                            case "list":
                                this.list(con, player);
                                break;
                            default:
                                if (!args[1].isEmpty() || !args[1].trim().isEmpty()) {
                                    break;
                                }
                                this.teleport(con, player, args);
                                break;
                        }
                    }
                } else {
                    con.set("players." + player.getName() + ".home", "world");
                    con.set("players." + player.getName() + ".home.world", "world");
                    con.set("players." + player.getName() + ".home.x", player.getWorld().getSpawnLocation().getX());
                    con.set("players." + player.getName() + ".home.y", player.getWorld().getSpawnLocation().getY());
                    con.set("players." + player.getName() + ".home.z", player.getWorld().getSpawnLocation().getZ());
                    config.saveConfig("homes.yml");
                }
            }

        } else {
            sender.sendMessage("You must be a player!");
        }

    }

    public void teleport(FileConfiguration con, Player player, String[] args) {
        if (args.length > 0) {
            if (con.contains("players." + player.getName() + "." + args[0])) {
                String name = args[0];
                String worldName = con.getString("players." + player.getName() + "." + name + ".world");
                Integer x = con.getInt("players." + player.getName() + "." + name + ".x");
                Integer y = con.getInt("players." + player.getName() + "." + name + ".y");
                Integer z = con.getInt("players." + player.getName() + "." + name + ".z");
                World world = Bukkit.getWorld(worldName);
                if (world != null) {
                    Location location = new Location(world, x, y, z);
                    player.teleport(location);
                } else {
                    player.sendMessage(ChatColor.RED + (worldName + " does not exist!, if this is wrong please contact an admin."));
                }
            } else {
                player.sendMessage(ChatColor.RED + (args[0] + " does not exist!"));
            }
        }
    }

    public void set(FileConfiguration con, Player player, String[] args) {
        if (args.length >= 2) {
            if (!args[1].isEmpty() || args[1].trim().length() > 0) {
                String name = args[1];
                con.set("players." + player.getName() + "." + name + ".world", player.getWorld().getName());
                con.set("players." + player.getName() + "." + name + ".x", player.getLocation().getX());
                con.set("players." + player.getName() + "." + name + ".y", player.getLocation().getY());
                con.set("players." + player.getName() + "." + name + ".z", player.getLocation().getZ());
                config.saveConfig("homes.yml");
                player.sendMessage(name + " has been set!");
            }
        }
    }

    public void remove(FileConfiguration con, Player player, String[] args) {
        if (args.length >= 2) {
            String name = args[1];
            if (con.contains("players." + player.getName() + "." + name)) {
                con.set("players." + player.getName() + "." + name, null);
                con.set("players." + player.getName() + "." + name + ".world", null);
                con.set("players." + player.getName() + "." + name + ".x", null);
                con.set("players." + player.getName() + "." + name + ".y", null);
                con.set("players." + player.getName() + "." + name + ".z", null);
                config.saveConfig("homes.yml");
                player.sendMessage(name + " has been removed!");
            } else {
                player.sendMessage("That home does not exist!");
            }
        }
    }

    public void list(FileConfiguration con, Player player) {
        Set<String> list = con.getConfigurationSection("players." + player.getName()).getKeys(false);
        for (String name : list) {
            player.sendMessage(name);
        }
    }

}
