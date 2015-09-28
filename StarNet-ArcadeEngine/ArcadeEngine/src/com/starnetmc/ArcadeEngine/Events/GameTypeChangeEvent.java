package com.starnetmc.ArcadeEngine.Events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.starnetmc.ArcadeEngine.Games.GameType;

public class GameTypeChangeEvent extends Event {
	
	private static final HandlerList handlers = new HandlerList();
	
    private GameType type;
    
    public GameTypeChangeEvent(GameType type) {
        this.type = type;
    }
 
    public GameType getChangedType() {
        return type;
    }
	 
	public HandlerList getHandlers() {
	    return handlers;
	}
	 
	public static HandlerList getHandlerList() {
	    return handlers;
	}

}
