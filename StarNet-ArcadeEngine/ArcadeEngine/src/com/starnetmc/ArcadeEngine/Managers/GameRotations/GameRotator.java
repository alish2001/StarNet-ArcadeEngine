package com.starnetmc.ArcadeEngine.Managers.GameRotations;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import com.starnetmc.ArcadeCore;
import com.starnetmc.ArcadeEngine.ArcadeManager;
import com.starnetmc.ArcadeEngine.Events.GameStateChangeEvent;
import com.starnetmc.ArcadeEngine.Managers.GameState;
import com.starnetmc.ArcadeEngine.Utils.All;
import com.starnetmc.ArcadeEngine.Utils.Logger;
import com.starnetmc.core.util.Tickifier;
import com.starnetmc.core.util.Tickifier.Time;

public class GameRotator implements Listener {
	
	private ArcadeManager manager;
	
	private boolean active;
	
	public GameRotator(ArcadeManager manager, boolean active){
		this.manager = manager;
		this.active = active;
		Bukkit.getServer().getPluginManager().registerEvents(this, ArcadeCore.getPlugin());
		Logger.log("<GameRotator> Rotator registered.");
	}
	
	public GameRotator(ArcadeManager manager){
		this.manager = manager;
		this.active = ArcadeCore.getConfigFile().getBoolean("AutoGameRotation");
		Bukkit.getServer().getPluginManager().registerEvents(this, ArcadeCore.getPlugin());
		Logger.log("<GameRotator> Rotator registered.");
	}
	
	@EventHandler
	public void onGameEnd(GameStateChangeEvent e){
		if (!active) return;
		if (e.getChangedState() != GameState.POSTGAME) return;
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				rotate();	
			}
		}.runTaskLater(ArcadeCore.getPlugin(), Tickifier.tickify(10, Time.SECONDS));
	}
	
	public void rotate(){
		int id = 0;
		Random r = new Random();
		id = r.nextInt(All.getAllGameTypes().size()); 
		
		if (All.getAllGameTypes().get(id) != manager.getGame()){
		    manager.setGame(All.getAllGameTypes().get(id));
		} else {
			if (All.getAllGameTypes().get(id+1) != null){
			    manager.setGame(All.getAllGameTypes().get(id + 1));
			} else {
			    manager.setGame(All.getAllGameTypes().get(id - 1));
			}
		}
		
		Logger.log("<GameRotator> The Game has been rotated.");
	}
	
	public void setActive(boolean active){
	    this.active = active;
	    Logger.log("<GameRotator> The rotator activity has been temporarly set to: " + active);
	}
	
	public void setActive(boolean active, boolean saveConfig){
	    this.active = active;
	    if (saveConfig){
	    	ArcadeCore.getConfigFile().set("AutoGameRotation", active);
		    Logger.log("<GameRotator> The rotator activity has been permanently set to: " + active);
		    return;
	    }
	    
	    Logger.log("<GameRotator> The rotator activity has been temporarly set to: " + active);
	}
	
	public boolean isActive(){
		return active;
	}

}