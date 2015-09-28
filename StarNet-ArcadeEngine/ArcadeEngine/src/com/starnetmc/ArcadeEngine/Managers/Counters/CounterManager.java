package com.starnetmc.ArcadeEngine.Managers.Counters;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.starnetmc.ArcadeCore;
import com.starnetmc.ArcadeEngine.Events.GameStateChangeEvent;
import com.starnetmc.ArcadeEngine.Managers.GameState;
import com.starnetmc.ArcadeEngine.Utils.Logger;

public class CounterManager implements Listener {
	
	private int gameCountdownID;
	private int preGameCountdownID;
	private int hinterID;
	
	private int gameCountdownTime;
	private int preGameCountdownTime;
	
	private boolean gameCountdownInProgress;
	private boolean preGameCountdownInProgress;
	private boolean hinterInProgress;
	
	public CounterManager(int gameCountdownTime, int preGameCountdownTime){
		this.gameCountdownTime = gameCountdownTime;
		this.preGameCountdownTime = preGameCountdownTime;
		this.gameCountdownInProgress = false;
		this.preGameCountdownInProgress = false;
		this.hinterInProgress = false;
		startHinter();
		Bukkit.getServer().getPluginManager().registerEvents(this, ArcadeCore.getPlugin());
		
		Logger.log("<CounterManager> CounterManager enabled.");
	}
	
	@EventHandler
	public void hinterManager(GameStateChangeEvent e){
		if (e.getChangedState() == GameState.LOBBY){
			startHinter();
		}
		
		if (e.getChangedState() == GameState.INGAME){
			stopHinter();
		}
		
	}
	
	public void startGameCountdown(){
		GameCountdown.time = gameCountdownTime;
		gameCountdownID = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(ArcadeCore.getPlugin(), new GameCountdown(), 0L, 20L);
		gameCountdownInProgress = true;
		ArcadeCore.getArcadeManager().setState(GameState.LOADING);
		Logger.log("<CounterManager> GameCountdown has Started.");
	}
	
	public void startPreGameCountdown(){
		PreGameCountdown.time = preGameCountdownTime;
		preGameCountdownID = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(ArcadeCore.getPlugin(), new PreGameCountdown(), 0L, 20L);
		preGameCountdownInProgress = true;
		Logger.log("<CounterManager> Pre-GameCountdown has Started.");
	}
	
	public void startHinter(){
		if (!hinterInProgress){
		 hinterID = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(ArcadeCore.getPlugin(), new Hinter(), 0, 20L);
		 hinterInProgress = true;
		 Logger.log("<CounterManager> The Hinter has started.");
		 return;
		} else {
			Logger.log("<CounterManager> Tried to start Hinter but hinter already started.");
			return;
		}
	}
	
	public void stopGameCountdown(){
		Bukkit.getServer().getScheduler().cancelTask(gameCountdownID);
		gameCountdownInProgress = false;
	    Logger.log("<CounterManager> GameCountdown has been stopped.");
	}
	
	public void stopPreGameCountdown(){
		Bukkit.getServer().getScheduler().cancelTask(preGameCountdownID);
	    preGameCountdownInProgress = false;
	    Logger.log("<CounterManager> Pre-GameCountdown has been stopped.");
	}
	
	public void stopHinter(){
		Bukkit.getServer().getScheduler().cancelTask(hinterID);
		hinterInProgress = false;
		Logger.log("<CounterManager> The Hinter has been stopped.");
	}
	
	public int getGCoundownID(){
		return gameCountdownID;
	}
	
	public int getPreGCoundownID(){
		return preGameCountdownID;
	}
	
	public int getGCountdownTime(){
		return gameCountdownTime;
	}
	
	public int getPreGCountdownTime(){
		return preGameCountdownTime;
	}
	
	public boolean isGameCountdownInProgress(){
		return gameCountdownInProgress;
	}
	
	public boolean isPreGameCountdownInProgress(){
		return preGameCountdownInProgress;
	}
	
	public boolean isHinterInProgress(){
		return hinterInProgress;
	}

}