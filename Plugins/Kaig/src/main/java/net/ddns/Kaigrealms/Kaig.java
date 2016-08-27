package net.ddns.Kaigrealms;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.*;

public class Kaig extends JavaPlugin{
	static ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
		static void tpRequest(Player sender, Player sent, String which){
			for (int v = 1; v<list.size(); v++){
				if (list.get(v).get(0) == sender.getUniqueId().toString()
						&& list.get(v).get(1) == sent.getUniqueId().toString()){
					list.get(v).set(2, which);
				} else if (v == list.size() && list.get(v).get(0) != sender.getUniqueId().toString()
						| list.get(v).get(1) != sent.getUniqueId().toString()){
					ArrayList<String> request = new ArrayList<String>();
					request.set(0, sender.getUniqueId().toString());
					request.set(1, sent.getUniqueId().toString());
					request.set(2, which);
					list.add(request);
				}
			}
			sender.sendMessage("Request sent.");
			if (which == "tpt"){
				sent.sendMessage(sender.getDisplayName()+" wants to teleport to you");
				sent.sendMessage("Use /tpyes "+sender.getDisplayName()+" to accept the request, or");
				sent.sendMessage("use /tpno "+sender.getDisplayName()+" to deny the request");
			} else if(which == "tph"){
				sent.sendMessage(sender.getDisplayName()+" wants you to teleport to them");
				sent.sendMessage("Use /tpyes "+sender.getDisplayName()+" to accept the request, or");
				sent.sendMessage("use /tpno "+sender.getDisplayName()+" to deny the request");
			}
		}
		static void tpResponse(Player sent, Player sender, boolean which){
			for(int i=1; i<list.size(); i++){
				if (list.get(i).get(0) == sender.getUniqueId().toString()){
					if (list.get(i).get(1) == sent.getUniqueId().toString()){
						if(which){
							if (list.get(i).get(2) == "tpt"){
								sender.teleport(sent.getLocation());
							} else if (list.get(i).get(2) == "tph"){
								sent.teleport(sender.getLocation());
							}
						} else {
							sender.sendMessage(sent.getDisplayName()+" declined your request");
						}
					} else if (list.get(i).get(1) != sent.getUniqueId().toString() && i == list.size()){
						sent.sendMessage("You don't have a teleport request from this player right now");
					}
				} else if (list.get(i).get(0) != sender.getUniqueId().toString() && i == list.size()){
					sent.sendMessage("You don't have a teleport request from this player right now");
				}
			}
		}
		@SuppressWarnings("deprecation")
		public boolean onCommand(CommandSender usr, Command cmd, String label, String[] args){
			if((usr instanceof Player)){
				Player plyr = (Player) usr;
				if(!(plyr.isDead())){
					if (args.length==0){
						if(cmd.getName().equalsIgnoreCase("suicide")){
						plyr.setHealth(0.0D);
						return true;
						} else if(cmd.getName().equalsIgnoreCase("spawn")){
							plyr.teleport(plyr.getWorld().getSpawnLocation());
							plyr.sendMessage(plyr.getName()+", you have been teleported to spawn.");
							return true;
						} else if(cmd.getName().equalsIgnoreCase("setspawn")){
							if (plyr.isOp()){
								Location loc = plyr.getLocation();
								plyr.getWorld().setSpawnLocation(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
								plyr.sendMessage(plyr.getName()+", you have successfully set the spawn of this world.");
								return true;
							} else {
								plyr.sendMessage(plyr.getName()+", currently you have to be an operator to do this.");
								return false;
							}
						}
					} else if(args.length==1){
						if(cmd.getName().equalsIgnoreCase("tpt")){
							if (Bukkit.getServer().getPlayer(args[0]).isOnline()){
							tpRequest(plyr, Bukkit.getServer().getPlayer(args[0]), "tpt");
							} else {
								plyr.sendMessage("That player doesn't exist or is offline");
							}
						} else if(cmd.getName().equalsIgnoreCase("tph")){
							if (Bukkit.getServer().getPlayer(args[0]).isOnline()){
							tpRequest(plyr, Bukkit.getServer().getPlayer(args[0]), "tpt");
							} else {
								plyr.sendMessage("That player doesn't exist or is offline");
							}
						} else if(cmd.getName().equalsIgnoreCase("tpyes")){
							if (Bukkit.getServer().getPlayer(args[0]).isOnline()){
							tpResponse(plyr, Bukkit.getServer().getPlayer(args[0]), true);
							} else {
								plyr.sendMessage("That player doesn't exist or is offline");
							}
						} else if(cmd.getName().equalsIgnoreCase("tpno")){
							if (Bukkit.getServer().getPlayer(args[0]).isOnline()){
							tpResponse(plyr, Bukkit.getServer().getPlayer(args[0]), true);
							} else {
								plyr.sendMessage("That player doesn't exist or is offline");
							}
						}
					} else {
						plyr.sendMessage("You have too many arguments");
					}
				} else {
					usr.sendMessage("...you aren't even alive.");
					return false;
				}
			} else {
				usr.sendMessage("I get it. You wanna be cool. But you can't without a body.");
				return false;
			}
			return false;
		}
	/*
	 * 
	 * sethome
	 * set home as variable under player
	 * should be easy enough, with players.yml
	 * and using that to reference homes and such
	 * maybe make it easy to add to manual, eh?
	 * 
	 * player: homes: world:homename:x:y:z:
	 * getBlockX, getBlockY, getBlockZ, getWorld
	 * simple enough hopefully
	 * 
	 * warps like homes but global and accessable
	 * by all, cross worlds as well
	 * 
	 * eventually put together system like multiworld :?
	 * and a random tp system, possibly biome related
	 * 
	 * permissions system.. this will be the last thing I make, probably. just a thing to update permissions yml with. heh
	 *  
	 * able to create groups through console, add users to group as admin through console or game
	 * change user tags based on perms specific to chat tag, not necessarily group
	 * 
	 * permissions needed...
	 * Kaigrealms for all commands
	 * iDisguise commands
	 * multiworld commands
	 * 
	 * 
	 */
	//diguises as mobs eventually
}