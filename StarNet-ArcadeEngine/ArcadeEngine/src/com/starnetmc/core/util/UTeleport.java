package com.starnetmc.core.util;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class UTeleport {
	
	public static void TpAll2P(Player p){
		for (Player all : Bukkit.getOnlinePlayers()){
			all.teleport(p);
		}		
	}
	
	public static void TpAll2Loc(Location loc){
		for (Player p : Bukkit.getServer().getOnlinePlayers()){
			p.teleport(loc);
		}
	}
	
	public static void TpAll2Loc(ArrayList<Player> ps, Location loc){
		for (Player p : ps){
			p.teleport(loc);
		}
	}

}
