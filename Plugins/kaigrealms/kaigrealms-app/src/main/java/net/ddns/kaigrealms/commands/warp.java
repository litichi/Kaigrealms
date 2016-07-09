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

public class warp {

    private final main plugin;
    private final config config;
    private final String worldName = "world";

    public warp(main plugin) {
        this.plugin = plugin;
        this.config = new config(plugin);
    }

    public void run(CommandSender sender, String[] args) throws IOException {

        if (sender instanceof Player) {

            Player player = (Player) sender;
            FileConfiguration con = config.getConfig("warps.yml");

            if (args.length == 0) {
                player.sendMessage("/warp [place] -- to teleport to your [place].");
                player.sendMessage("/warp set [place] -- to create a [place].");
                player.sendMessage("/warp list -- to list your places.");
                return;
            }

            if (con != null) {
                if (args[0] != null) {
                    if (!"set".equals(args[0]) && !"list".equals(args[0])) {
                        if (!args[0].isEmpty() || !args[0].trim().isEmpty()) {
                            if (con.contains("warps." + args[0])) {
                                String name = args[0];
                                String worldName = con.getString("warps." + name + ".world");
                                Integer x = con.getInt("warps." + name + ".x");
                                Integer y = con.getInt("warps." + name + ".y");
                                Integer z = con.getInt("warps." + name + ".z");
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
                    } else if ("set".equals(args[0])) {
                        if (args[1] != null) {
                            if (!args[1].isEmpty() || !args[1].trim().isEmpty()) {
                                String name = args[1];
                                con.set("warps." + name + ".world", player.getWorld().getName());
                                con.set("warps." + name + ".x", player.getLocation().getX());
                                con.set("warps." + name + ".y", player.getLocation().getY());
                                con.set("warps." + name + ".z", player.getLocation().getZ());
                                config.saveConfig("warps.yml");
                            }
                        }
                    } else if ("list".equals(args[0])) {
                        Set<String> list = con.getConfigurationSection("warps").getKeys(false);
                        for (String name : list) {
                            player.sendMessage(name);
                        }
                    }
                }
            } else {
                con.set("warps.spawn.world", worldName);
                con.set("warps.spawn.x", Bukkit.getWorld(worldName).getSpawnLocation().getX());
                con.set("warps.spawn.y", Bukkit.getWorld(worldName).getSpawnLocation().getY());
                con.set("warps.spawn.z", Bukkit.getWorld(worldName).getSpawnLocation().getZ());
                config.saveConfig("warps.yml");
            }

        } else {
            sender.sendMessage("You must be a player!");
        }

    }

    public void createHome(String name) {

    }

}
