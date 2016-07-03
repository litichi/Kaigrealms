package net.ddns.Kaigrealms;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.*;

public class Kaig extends JavaPlugin{
		public boolean onCommand(CommandSender usr, Command cmd, String label, String[] args){
			if((usr instanceof Player)){
				Player plyr = (Player) usr;
				if(!(plyr.isDead())){
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