package com.starnetmc.ArcadeEngine.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ScoreboardUpdateEvent extends Event {
	
	private static final HandlerList handlers = new HandlerList();
	
    private Player player;
    
    public ScoreboardUpdateEvent(Player player) {
        this.player = player;
    }
 
    public Player getPlayer() {
        return player;
    }
	 
	public HandlerList getHandlers() {
	    return handlers;
	}
	 
	public static HandlerList getHandlerList() {
	    return handlers;
	}

}