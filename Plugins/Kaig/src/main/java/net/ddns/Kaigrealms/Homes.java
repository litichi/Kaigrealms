package net.ddns.Kaigrealms;

import java.io.File;
import java.io.IOException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Homes extends Kaig{
	@Override
	public void onEnable(){
		//check for homes yml and warps yml existence, if not then make them
		//yep that's basically all that's needed
		File homes = new File("plugins/Kaigrealms/homes.yml");
		if(!(homes.exists())){
			try {
				homes.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		File warps = new File("plugins/Kaigrealms/warps.yml");
		if(!(warps.exists())){
			try {
				warps.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public boolean onCommand(CommandSender usr, Command cmd, String label, String[] args){
		if (usr instanceof Player){
			Player plyr = (Player) usr;
			if(!(plyr.isDead())){
				if(cmd.getName().equalsIgnoreCase("sethome")){
					//put homename in variable
					//send error message if not a-z, 0-9
					//check homes yml for the player's homes
					//if player has no home, guess what you get to add theirs
					//before checking home amount, handle re-setting homes with the same name first, just replacing the x y z and world
					//check how many homes player has, limit 12 for base limit
					//if the homes amount is not 12 or more, then add the home
					return true;
				} else if (cmd.getName().equalsIgnoreCase("delhome")) {
					//put homename in variable
					//check player's homes, error if not found in the homes list, if no homes give an ironic
					//message to them c:
					//if the home exists, delete it//remove it and be like "muahahha your home was successfully destroyed"
					return true;
				} else if (cmd.getName().equalsIgnoreCase("homes")){
					//just list the player's homes.
					//if they have no homes give that ironic message again like with the delhome irony
					return true;
				}
			} else {
				plyr.sendMessage("You aren't even alive dude");
				return false;
			}
		} else {
			usr.sendMessage("You aren't even a player kthx bye");
			return false;
		}
		return false;
	}
}
/* 
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
*/