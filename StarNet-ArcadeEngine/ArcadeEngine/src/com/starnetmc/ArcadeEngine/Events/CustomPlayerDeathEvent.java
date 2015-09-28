package com.starnetmc.ArcadeEngine.Events;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class CustomPlayerDeathEvent extends Event {
	
	private static final HandlerList handlers = new HandlerList();
	
    private Player killed;
    private Entity killer;
    
    public CustomPlayerDeathEvent(Player killed, Entity killer) {
        this.killed = killed;
        this.killer = killer;
    }
 
    public Player getKilled() {
        return killed;
    }
    
    public Entity getKiller() {
        return killer;
    }
    
    public boolean entityIsPlayer(){
    	if (killer instanceof Player){
    		return true;
    	} else {
    		return false;
    	}
    }
	 
	public HandlerList getHandlers() {
	    return handlers;
	}
	 
	public static HandlerList getHandlerList() {
	    return handlers;
	}

}
