package com.starnetmc.ArcadeEngine.Managers.Maps.MapProperties;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import com.starnetmc.ArcadeCore;
import com.starnetmc.ArcadeEngine.ArcadeManager;
import com.starnetmc.ArcadeEngine.Events.GameStateChangeEvent;
import com.starnetmc.ArcadeEngine.Events.MapChangeEvent;
import com.starnetmc.ArcadeEngine.Managers.GameState;
import com.starnetmc.ArcadeEngine.Utils.Logger;

public class MapPropertiesManager implements Listener {
	
	private ArcadeManager manager;
	
	private MapProperties properties;
	
	public MapPropertiesManager(ArcadeManager manager, MapProperties properties){
		this.manager = manager;
		this.properties = properties;
	}
	
	public void register(){
		Bukkit.getServer().getPluginManager().registerEvents(this, ArcadeCore.getPlugin());
		Logger.log("<Map Properties Manager> An instance of a MapPropertiesManager has been instantiated and registered.");
	}
	
	public void unRegister(){
		HandlerList.unregisterAll(this);
		Logger.log("<Map Properties Manager> An instance of a MapPropertiesManager has been unregistered.");
	}
	
	public void setProperties(MapProperties newProperties){
		this.properties = newProperties;
	}
	
	@EventHandler
	public void onMapChange(MapChangeEvent e){
		setProperties(e.getChangedMap().getProperties());
	}
	
	@EventHandler
	public void weatherChanger(GameStateChangeEvent e){
		if (e.getChangedState() != GameState.INGAME) return;
		manager.getMapper().getActiveMap().getCenter().getWorld().setStorm(properties.isRain());
	}
	
	@EventHandler
	public void timeChanger(GameStateChangeEvent e){
		if (e.getChangedState() != GameState.INGAME) return;
		manager.getMapper().getActiveMap().getCenter().getWorld().setTime(properties.getTime());
	}

}