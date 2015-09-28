package com.starnetmc.ArcadeEngine.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.starnetmc.ArcadeEngine.Managers.Players.PlayerState;

public class PlayerStateChangeEvent extends Event {
	
	private static final HandlerList handlers = new HandlerList();
	
    private Player player;
    private PlayerState state;
    
    public PlayerStateChangeEvent(Player player, PlayerState state) {
        this.player = player;
        this.state = state;
    }
 
    public Player getPlayer() {
        return player;
    }
    
    public PlayerState getChangedState() {
        return state;
    }
	 
	public HandlerList getHandlers() {
	    return handlers;
	}
	 
	public static HandlerList getHandlerList() {
	    return handlers;
	}

}
