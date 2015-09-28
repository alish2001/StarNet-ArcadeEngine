package com.starnetmc.ArcadeEngine.Events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.starnetmc.ArcadeEngine.Managers.Maps.MapTypes.Map;

public class MapChangeEvent extends Event {
	
	private static final HandlerList handlers = new HandlerList();
	
    private Map map;
    
    public MapChangeEvent(Map map) {
        this.map = map;
    }
 
    public Map getChangedMap() {
        return map;
    }
	 
	public HandlerList getHandlers() {
	    return handlers;
	}
	 
	public static HandlerList getHandlerList() {
	    return handlers;
	}


}