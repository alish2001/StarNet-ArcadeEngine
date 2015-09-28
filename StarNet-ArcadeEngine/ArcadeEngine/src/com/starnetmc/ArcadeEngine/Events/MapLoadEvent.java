package com.starnetmc.ArcadeEngine.Events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class MapLoadEvent extends Event {

	private static final HandlerList handlers = new HandlerList();
	
    private String mapWorldName;
    
    public MapLoadEvent(String mapWorldName) {
        this.mapWorldName = mapWorldName;
    }
 
    public String getMapWorldName() {
        return mapWorldName;
    }
	 
	public HandlerList getHandlers() {
	    return handlers;
	}
	 
	public static HandlerList getHandlerList() {
	    return handlers;
	}
	
}