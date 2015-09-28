package com.starnetmc.core.util;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.starnetmc.ArcadeEngine.Utils.UPotion;

public class UPlayer {
	
	public static void resetPlayer(Player p){
		removeArrows(p);
		p.setMaxHealth(20D);
		p.setExp(0);
		p.setFoodLevel(20);
		p.setHealthScale(20D);
		UPotion.clearEffect(p);
	}
	
	public static void resetPlayers(){
		for (Player p : Bukkit.getServer().getOnlinePlayers()){
			resetPlayer(p);
		}
	}
	
	public static void removeArrows(Player p){
		((CraftPlayer) p).getHandle().getDataWatcher().watch(9, (byte)0);
	}
	
	public static void hidePlayerFromAll(Player hider){
		
		for (Player p : Bukkit.getServer().getOnlinePlayers()){
			p.hidePlayer(hider);
		}
	}
	
	public static void showPlayerToAll(Player unHider){
		
		for (Player p : Bukkit.getServer().getOnlinePlayers()){
			p.showPlayer(unHider);
		}
	}
	
	public static Player getOnlinePlayerFromName(String p){
		Player sp = Bukkit.getServer().getPlayer(p);
		if (sp == null){
			return null;
		}
		else{
			return sp;
		}
	}
	
	@SuppressWarnings("deprecation")
	public static OfflinePlayer getOfflinePlayerFromName(String p){
		OfflinePlayer sp = Bukkit.getServer().getOfflinePlayer(p);
		if (sp == null){
			return null;
		}
		else{
			return sp;
		}
	}
	
	public static Player getPlayerFromUUID(UUID uid){
		return Bukkit.getServer().getPlayer(uid);
	}
	
	public static boolean isOnline(String name){
		if (getOnlinePlayerFromName(name) == null){
			return false;
		}
		else { return true; }
	}

}