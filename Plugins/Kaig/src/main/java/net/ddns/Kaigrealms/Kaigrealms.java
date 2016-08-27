package net.ddns.Kaigrealms;
//Still not doing something right I have no idea what I screwed up :|

import java.util.ArrayList;
import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.*;

public class Kaigrealms extends JavaPlugin implements Listener{
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
	
	static ArrayList<HashMap<Player, Player>> list = new ArrayList<HashMap<Player, Player>>();
	
	static void tpRequest(Player sender, Player sent, String which){
		HashMap<Player, Player> thing = new HashMap<Player, Player>();
		if (which == "tpt"){
			thing.put(sent, sender);
			list.add(thing);
			sent.sendMessage("Type /tpyes to accept "+sender.getDisplayName()+"'s request to tp to you, or");
			sent.sendMessage("type /tpno to decline their request");
			sender.sendMessage("Request sent.");
		} else if (which == "tph"){
			thing.put(sender, sent);
			list.add(thing);
			sent.sendMessage("Type /tpyes to accept "+sender.getDisplayName()+"'s request for you to tp to them, or");
			sent.sendMessage("type /tpno to decline their request");
			sender.sendMessage("Request sent.");
		}
	}
	static void tpResponse(Player sent, Player sender, boolean which){
		if (which==true){
			for (int num = 1; num < list.size(); num++){
				if (list.get(num).get(sent) != null){
					if (list.get(num).get(sent) == sender){
						if (sender==sent){sent.sendMessage("Teleported to yourself");}
						sender.teleport(sent.getLocation());
						list.remove(num);
					} else {
						sent.sendMessage("You don't have any tp requests from that player right now.");
					}
				} else if (list.get(num).get(sender) != null){
					if (list.get(num).get(sender) == sent){
						if (sent==sender){sender.sendMessage("Teleported to yourself");}
						sent.teleport(sender.getLocation());
						list.remove(num);
					} else {
						sent.sendMessage("You don't have any tp requests from that player right now.");
					}
				} else {
					sent.sendMessage("You don't have any tp requests from that player right now.");
				}
			}
		} else {
			for (int num = 1; num < list.size(); num++){
				if (list.get(num).get(sent) != null){
					sent.sendMessage("Request denied");
					sender.sendMessage("Your request was declined");
					list.remove(num);
				} else if (list.get(num).get(sender) != null){
					sent.sendMessage("Request denied");
					sender.sendMessage("Your request was declined");
					list.remove(num);
				} else if (num == list.size() && list.get(num).get(sender) ==  null){
					sent.sendMessage("You don't have any tp requestions from that player right now.");
				} else if (num==list.size() && list.get(num).get(sent) == null) {
					sent.sendMessage("You don't have any tp requestions from that player right now.");
				}
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
						} else if(cmd.getName().equalsIgnoreCase("fly")){
							if (plyr.getAllowFlight() && !(plyr.isFlying())){
								plyr.setAllowFlight(false);
								plyr.sendMessage("Toggled flight off");
								return true;
							} else if (plyr.getAllowFlight() && plyr.isFlying()){
								plyr.setAllowFlight(false);
								plyr.sendMessage("GERONIMOOOOOOO");
								return true;
							} else {
								plyr.setAllowFlight(true);
								plyr.sendMessage("You can fly now c:");
								return true;
							}
						}
					} else if(args.length==1){
						if(cmd.getName().equalsIgnoreCase("tpt")){
							if (Bukkit.getServer().getPlayer(args[0]).isOnline()){
							tpRequest(plyr, Bukkit.getServer().getPlayer(args[0]), "tpt");
							return true;
							} else {
								plyr.sendMessage("That player doesn't exist or is offline");
								return true;
							}
						} else if(cmd.getName().equalsIgnoreCase("tph")){
							if (Bukkit.getServer().getPlayer(args[0]).isOnline()){
							tpRequest(plyr, Bukkit.getServer().getPlayer(args[0]), "tph");
							return true;
							} else {
								plyr.sendMessage("That player doesn't exist or is offline");
								return true;
							}
						} else if(cmd.getName().equalsIgnoreCase("tpyes")){
							if (Bukkit.getServer().getPlayer(args[0]).isOnline()){
							tpResponse(plyr, Bukkit.getServer().getPlayer(args[0]), true);
							return true;
							} else {
								plyr.sendMessage("That player doesn't exist or is offline");
								return true;
							}
						} else if(cmd.getName().equalsIgnoreCase("tpno")){
							if (Bukkit.getServer().getPlayer(args[0]).isOnline()){
							tpResponse(plyr, Bukkit.getServer().getPlayer(args[0]), false);
							return true;
							} else {
								plyr.sendMessage("That player doesn't exist or is offline");
								return true;
							}
						}
					} else {
						plyr.sendMessage("You have too many arguments");
						return false;
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
	 * set home as variable under player in saved.yml
	 * getBlockX, getBlockY, getBlockZ, getWorld
	 * simple enough hopefully
	 * warps as well, same setup as homes but global
	 * use type: as a property of saved location
	 * 
	 * system ran like multiworld
	 * 
	 * jailing system, no breaking or PvP but can chat with OPs in JailedChat xD? just to see if we can do that!
	 * 
	 * decide how to split this up because it's so big, doubt will actually do that though, I'm too inefficient
	 * 
	 */
	//diguises as mobs eventually {eventually is probably right after saved yml stuff
}