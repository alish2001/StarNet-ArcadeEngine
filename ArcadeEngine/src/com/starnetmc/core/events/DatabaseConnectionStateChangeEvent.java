package com.starnetmc.core.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class DatabaseConnectionStateChangeEvent extends Event {
	
	private static final HandlerList handlers = new HandlerList();

	public boolean connected;

	public DatabaseConnectionStateChangeEvent(boolean connected) {
		this.connected = connected;
	}
	
	public boolean isConnected(){
		return connected;
	}

	public HandlerList getHandlers() {
	    return handlers;
	}
	 
	public static HandlerList getHandlerList() {
	    return handlers;
	}

}