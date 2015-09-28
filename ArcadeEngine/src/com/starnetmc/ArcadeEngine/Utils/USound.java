package com.starnetmc.ArcadeEngine.Utils;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class USound {

	public static void PSound (Player p, Sound s, float f1, float f2){
		p.getWorld().playSound(p.getLocation(), s, f1, f2);
	}
	
	public static void AllPSound(Sound s, float f1, float f2){
		for (Player p : Bukkit.getOnlinePlayers()){
			PSound(p, s, f1, f2);
		}
	}
	
	public static void ESound (Entity e, Sound s, float f1, float f2){
		e.getWorld().playSound(e.getLocation(), s, f1, f2);
	}
	
	
}