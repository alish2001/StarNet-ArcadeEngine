package com.starnetmc.ArcadeEngine.Managers.GameProperties.PropertiesManagers;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.starnetmc.ArcadeCore;
import com.starnetmc.ArcadeEngine.Events.GameStateChangeEvent;
import com.starnetmc.ArcadeEngine.Managers.GameState;
import com.starnetmc.ArcadeEngine.Managers.GameProperties.GamePropertiesManager;
import com.starnetmc.ArcadeEngine.Managers.GameProperties.PropertiesManager;
import com.starnetmc.core.util.F;
import com.starnetmc.core.util.Tickifier;
import com.starnetmc.core.util.Tickifier.Time;

public class PropertiesManager_Grace extends PropertiesManager {

	private boolean freeze;
	
	public PropertiesManager_Grace(GamePropertiesManager gamePropertiesManager, String propertiesManagerName) {
		super(gamePropertiesManager, "Grace");
		freeze = false;
	}
	
	public PropertiesManager_Grace(GamePropertiesManager gamePropertiesManager) {
		super(gamePropertiesManager);
		setName("Grace");
		freeze = false;
	}
	
	@EventHandler
	public void gracer(GameStateChangeEvent e){
		
		if (e.getChangedState() != GameState.INGAME) return;
		if (!getGamePropertiesManager().getProperties().getGracePeriod()) return;
		
		log("Starting grace period...");
		
		if (getGamePropertiesManager().getProperties().getGraceAnnounce()){
			Bukkit.getServer().broadcastMessage(F.boldYellow + "GRACE PERIOD WILL END " + Tickifier.unTickify(getGamePropertiesManager().getProperties().getDefaultTime(), Time.SECONDS) + " SECONDS!");
		}
		
		getGamePropertiesManager().getProperties().setPvP(false);
		getGamePropertiesManager().getProperties().setPvE(false);
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				getGamePropertiesManager().setProperties(getGamePropertiesManager().getManager().getGame().getGClass().getProperties());
				log("Grace period over, PVP&PVE setttings returned to normal.");
				
			}
		}.runTaskLater(ArcadeCore.getPlugin(), getGamePropertiesManager().getProperties().getGraceTime());
	}
	
	@EventHandler
	public void freezer(GameStateChangeEvent e){
		
		if (e.getChangedState() != GameState.INGAME) return;
		freeze = true;
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				freeze = false;
				
			}
		}.runTaskLater(ArcadeCore.getPlugin(), Tickifier.tickify(5, Time.SECONDS));
	}
	
	@EventHandler
	public void freezafier(PlayerMoveEvent e){
		
		if (freeze != true) return;
		if (!(getGamePropertiesManager().getManager().isState(GameState.INGAME))) return;
		e.setTo(e.getFrom());
	}

}