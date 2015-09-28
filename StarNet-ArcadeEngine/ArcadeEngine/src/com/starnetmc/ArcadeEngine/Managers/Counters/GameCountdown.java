package com.starnetmc.ArcadeEngine.Managers.Counters;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.starnetmc.ArcadeCore;
import com.starnetmc.ArcadeEngine.Events.ScoreboardUpdateEvent;
import com.starnetmc.ArcadeEngine.Utils.USound;

public class GameCountdown implements Runnable {

	public static int time;
	
	@Override
	public void run() {
		
		if (time == 0) {
			ArcadeCore.getArcadeManager().getCounterManager().stopGameCountdown();
			ArcadeCore.getArcadeManager().getGame().getGClass().startUp();
			ArcadeCore.getArcadeManager().getCounterManager().startPreGameCountdown();
			for (Player p : Bukkit.getServer().getOnlinePlayers()){
			Bukkit.getServer().getPluginManager().callEvent(new ScoreboardUpdateEvent(p));
			}
			ArcadeCore.getArcadeManager().getCounterManager().stopGameCountdown();
		} else {
           time -= 1;
			for (Player p : Bukkit.getServer().getOnlinePlayers()){
			Bukkit.getServer().getPluginManager().callEvent(new ScoreboardUpdateEvent(p));
			}
			if (time <= 10 && time > 1){
				USound.AllPSound(Sound.NOTE_PLING, 1.25f, 1);
			}
		}
		
	}

}
