package com.starnetmc.ArcadeEngine.Utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

public class UPotion {
	
	public static void clearAllOnlineEffects(){
		for (Player p : Bukkit.getOnlinePlayers()){
			
			clearEffect(p);
		}
	}
	
	public static void clearEffect(Player p){
		
		for (PotionEffect pf : p.getActivePotionEffects())
	     p.removePotionEffect(pf.getType());
	}
}