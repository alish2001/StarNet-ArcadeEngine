package com.starnetmc.ArcadeEngine.Events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.starnetmc.ArcadeEngine.Managers.GameState;

public class GameStateChangeEvent extends Event {
	
	private static final HandlerList handlers = new HandlerList();
	
    private GameState state;
    
    public GameStateChangeEvent(GameState state) {
        this.state = state;
    }
 
    public GameState getChangedState() {
        return state;
    }
	 
	public HandlerList getHandlers() {
	    return handlers;
	}
	 
	public static HandlerList getHandlerList() {
	    return handlers;
	}

}
