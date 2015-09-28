package com.starnetmc.ArcadeEngine.Managers.GameProperties;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import com.starnetmc.ArcadeCore;
import com.starnetmc.ArcadeEngine.Utils.Logger;

public class PropertiesManager implements Listener {
	
	private GamePropertiesManager gamePropertiesManager;
	
	private String propertiesManagerName;
	
	public PropertiesManager(GamePropertiesManager gamePropertiesManager, String propertiesManagerName){
		this.gamePropertiesManager = gamePropertiesManager;
		this.propertiesManagerName = propertiesManagerName;
	}
	
	public PropertiesManager(GamePropertiesManager gamePropertiesManager){
		this.gamePropertiesManager = gamePropertiesManager;
		this.propertiesManagerName = "Undefined";
	}
	
	public void enable(){
		Bukkit.getServer().getPluginManager().registerEvents(this, ArcadeCore.getPlugin());
		log("Enabled.");
	}
	
	public void disable(){
		HandlerList.unregisterAll(this);
		log("Disabled.");
	}
	
	public void log(String msg){
		Logger.log("<PropertiesManager> <" + propertiesManagerName + "> " + msg);
	}
	
	public void setGamePropertiesManager(GamePropertiesManager gamePropertiesManager){
		this.gamePropertiesManager = gamePropertiesManager;
	}
	
	public void setName(String propertiesManagerName){
		this.propertiesManagerName = propertiesManagerName;
	}
	
	public GamePropertiesManager getGamePropertiesManager(){
		return gamePropertiesManager;
	}
	
	public String getName(){
		return propertiesManagerName;
	}

}