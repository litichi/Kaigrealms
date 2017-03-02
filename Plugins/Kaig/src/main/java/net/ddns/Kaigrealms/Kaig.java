package net.ddns.Kaigrealms;

import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.*;

public class Kaig extends JavaPlugin implements Listener{
	@Override
	public void onEnable(){
		Bukkit.getPluginManager().registerEvents(this, this);
	}
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent evnt){
		if (evnt.getMessage().toLowerCase().contains("fishy")){
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "give "+evnt.getPlayer().getName()+" cooked_fish 5");
			evnt.getPlayer().sendMessage("Eh? You think so?");
		}
	}
	static ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
	static void tpRequest(Player sender, Player sent, String which){
		for (int v=0; v==list.size(); v++){
			if ((list.get(v).get(0)==sender.getUniqueId().toString() && which == "tpt") |
					(list.get(v).get(0)==sent.getUniqueId().toString() && which == "tph")){
				list.get(v).set(2, which);
			} else if(v == list.size()){
				ArrayList<String> request = new ArrayList<String>();
				request.add(sender.getUniqueId().toString());
				request.add(sent.getUniqueId().toString());
				list.add(request);
			}
			sender.sendMessage("Your request was sent!");
			if (which == "tpt"){
				sent.sendMessage(sender.getDisplayName()+" wants to teleport to you");
				sent.sendMessage("Use /tpyes "+sender.getDisplayName()+" or tpno "+sender.getDisplayName()+" to respond.");
			} else if (which == "tph"){
				
			}
		}
	}
	static void tpResponse(Player sent, Player sender, boolean which){
		for(int i=0; i==list.size(); i++){
			if (!(list.isEmpty()) && list.get(i).get(0) == sender.getUniqueId().toString()){
				System.out.println("Do you really this getting this far is good");
				if (list.get(i).get(1) == sent.getUniqueId().toString()){
					System.out.println("Where did the logic screw up");
					if(which){
						if (list.get(i).get(2) == "tpt"){
							System.out.println("Seriously.");
							sender.teleport(sent.getLocation());
							System.out.println("Teleported");
							list.remove(i);
						} else if (list.get(i).get(2) == "tph"){
							System.out.println("Cmon dude.");
							sent.teleport(sender.getLocation());
							System.out.println("Teleported");
							list.remove(i);
						}
					} else {
						sender.sendMessage(sent.getDisplayName()+" declined your request");
					}
				} else if (list.get(i).get(1) != sent.getUniqueId().toString() && i == list.size()){
					sent.sendMessage("You don't have a teleport request from this player right now");
				}
			} else if (!(list.isEmpty()) && (list.get(i).get(0) != sender.getUniqueId().toString() && i == list.size())){
				sent.sendMessage("You don't have a teleport request from this player right now");
			} else if (list.isEmpty()){
				sent.sendMessage("You don't have a teleport request from this player right now");
			}
		}
	}
	@SuppressWarnings("deprecation")
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
				} else if(cmd.getName().equalsIgnoreCase("fly")){
					if(plyr.isFlying()==true){
						plyr.sendMessage("GERONIMO!!!");
					}
					plyr.setAllowFlight(!(plyr.getAllowFlight()));
					return true;
				}
				if(args.length==1){
					if(cmd.getName().equalsIgnoreCase("tpt")){ //Teleport to
						if (Bukkit.getServer().getPlayer(args[0]).isOnline()){
							tpRequest(plyr, Bukkit.getServer().getPlayer(args[0]), "tpt");
							return true;
						} else {
							plyr.sendMessage("That player doesn't exist or is offline");
							return false;
						}
					} else if(cmd.getName().equalsIgnoreCase("tph")){ //Teleport here
						if (Bukkit.getServer().getPlayer(args[0]).isOnline()){
							tpRequest(Bukkit.getServer().getPlayer(args[0]), plyr, "tpt");
							return true;
						} else {
							plyr.sendMessage("That player doesn't exist or is offline");
							return false;
						}
					} else if(cmd.getName().equalsIgnoreCase("tpyes")){ //Teleport accept
						if (Bukkit.getServer().getPlayer(args[0]).isOnline()){
							tpResponse(plyr, Bukkit.getServer().getPlayer(args[0]), true);
							return true;
						} else {
							plyr.sendMessage("That player doesn't exist or is offline");
							return false;
						}
					} else if(cmd.getName().equalsIgnoreCase("tpno")){ //Teleport decline
						if (Bukkit.getServer().getPlayer(args[0]).isOnline()){
							tpResponse(plyr, Bukkit.getServer().getPlayer(args[0]), true);
							return true;
						} else {
							plyr.sendMessage("That player doesn't exist or is offline");
							return false;
						}
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
	 * sethome
	 * player: homes: world:homename:x:y:z:
	 * getBlockX, getBlockY, getBlockZ, getWorld
	 * warps useable by all, cross worlds as well
	 * random tp system, possibly biome related
	 * permissions system.. like silencing and freezing
	 * tags for usernames just for fun
	 */
	//diguises as mobs eventually [likely sooner rather than later]
}