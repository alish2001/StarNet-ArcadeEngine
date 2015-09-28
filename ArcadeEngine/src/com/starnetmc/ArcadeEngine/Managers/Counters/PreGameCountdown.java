package com.starnetmc.ArcadeEngine.Managers.Counters;

import org.bukkit.Sound;

import com.starnetmc.ArcadeCore;
import com.starnetmc.ArcadeEngine.Utils.USound;

public class PreGameCountdown implements Runnable {
	
	public static int time;
	
	public void run() {
		    	  
				  if (time == 1) {
					  ArcadeCore.getArcadeManager().getCounterManager().stopPreGameCountdown();
					  //ArcadeCore.getArcadeManager().getPropertiesManager().setFreeze(false);
					  USound.AllPSound(Sound.NOTE_PLING, 1.35f, 1.20f);
					  ArcadeCore.getArcadeManager().getCounterManager().stopPreGameCountdown();
				  } else {
				  
				  time -= 1;
		          USound.AllPSound(Sound.NOTE_STICKS, 1, 1); 
				  }
		  }

}