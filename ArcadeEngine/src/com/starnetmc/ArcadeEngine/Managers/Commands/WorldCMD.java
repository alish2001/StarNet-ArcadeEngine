package com.starnetmc.ArcadeEngine.Managers.Commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.starnetmc.ArcadeCore;
import com.starnetmc.ArcadeEngine.Managers.Maps.MapLoader;
import com.starnetmc.ArcadeEngine.Utils.AF;
import com.starnetmc.core.commands.util.CommandBase;
import com.starnetmc.core.util.F;
import com.starnetmc.core.util.Rank;
import com.starnetmc.core.util.UPlayer;

public class WorldCMD extends CommandBase<ArcadeCMDS> {

	public WorldCMD(ArcadeCMDS plugin) {
		super(plugin, Rank.DEVELOPER, new String[] { "world", "w" });
	}
	
	public void execute(Player player, String[] args) {
		
		Player p = player;
		
		if (args.length == 0){
			listCMDS(p);
		}
		
		if (args[0].equalsIgnoreCase("load")){
			
			if (args.length < 2){
				listCMDS(p);
				return;
			}
			
			p.sendMessage(AF.map("Attempting to load World: " + args[1] + "..."));		
			MapLoader.loadWorld(args[1]);
			p.sendMessage(AF.map("World: " + args[1] + " has been loaded."));
			return;
		}
		
		if (args[0].equalsIgnoreCase("unload")){
			
			if (args.length < 2){
				listCMDS(p);
				return;
			}
			
			if (Bukkit.getServer().getWorld(args[1]) == null){
				p.sendMessage(AF.map("The World " + args[1] + " is not currently loaded."));
				return;
			}
			
			p.sendMessage(AF.map("Attempting to unload World: " + args[1] + "..."));
		    MapLoader.unloadWorld(Bukkit.getServer().getWorld(args[1]));
			p.sendMessage(AF.map("World: " + args[1] + " has been unloaded."));
			return;
		}
		
		if (args[0].equalsIgnoreCase("tp") || args[0].equalsIgnoreCase("teleport")){
			
			if (args.length < 2){
				listCMDS(p);
				return;
			}
			
			if (Bukkit.getServer().getWorld(args[1]) == null){
				p.sendMessage(AF.map("The World " + args[1] + " is not currently loaded."));
				return;
			}
			
			if (args.length == 3){
				if (!UPlayer.isOnline(args[2])){
					p.sendMessage(F.error("PlayerFinder", args[2] + " is not online."));
					return;
				}
				
				Player t = UPlayer.getOnlinePlayerFromName(args[2]);
				t.teleport(Bukkit.getServer().getWorld(args[1]).getSpawnLocation());
				t.sendMessage(F.info("WorldManager", p.getName() + " Teleported you to the World: " + args[1]));
				p.sendMessage(F.info("WorldManager", "Teleported " + t.getName() + " to the World: " + args[1]));
				return;
			}
			
			p.teleport(Bukkit.getServer().getWorld(args[1]).getSpawnLocation());
			p.sendMessage(F.info("WorldManager", "Teleported you to the World: " + args[1]));
			return;
		}
		
		if (args[0].equalsIgnoreCase("setlobby")){
			
			if (args.length < 2){
				listCMDS(p);
				return;
			}
			
			if (Bukkit.getServer().getWorld(args[1]) == null){
				p.sendMessage(AF.map("The World " + args[1] + " is not currently loaded."));
				return;
			}
			
			ArcadeCore.getConfigFile().set("LobbyWorld", args[1]);
			p.sendMessage(F.info("WorldManager", "Set LobbyWorld to " + args[1]));
			return;
		}
		
	}
	
	private void listCMDS(Player p){
		p.sendMessage(AF.listTop("World Management"));
		p.sendMessage(AF.cmdList("/World Load <WorldName>", "Loads a world from the name in the root directory.", F.boldDP + "DEVELOPER"));
		p.sendMessage(AF.cmdList("/World UnLoad <WorldName>", "UnLoads a world from the name specified.", F.boldDP + "DEVELOPER"));
		p.sendMessage(AF.cmdList("/World Tp <WorldName> [Player]", "Teleports you or another player to the world specified.", F.boldDP + "DEVELOPER"));
		p.sendMessage(AF.cmdList("/World SetLobby <WorldName>", "Sets the lobby world.", F.boldDP + "DEVELOPER"));
	}

}