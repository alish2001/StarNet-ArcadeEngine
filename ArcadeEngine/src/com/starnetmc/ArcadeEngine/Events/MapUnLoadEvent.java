package com.starnetmc.ArcadeEngine.Events;

import org.bukkit.World;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class MapUnLoadEvent extends Event {

	private static final HandlerList handlers = new HandlerList();
	
    private World mapWorld;
    
    public MapUnLoadEvent(World mapWorld) {
        this.mapWorld = mapWorld;
    }
 
    public World getMapWorld() {
        return mapWorld;
    }
	 
	public HandlerList getHandlers() {
	    return handlers;
	}
	 
	public static HandlerList getHandlerList() {
	    return handlers;
	}
	
}