package com.starnetmc.core.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.starnetmc.core.gadgets.Gadget;

public class PlayerGadgetChangeEvent extends Event {
	
	private static final HandlerList handlers = new HandlerList();

	boolean removal;
	public Player player;
    public Gadget gadget;
	
	public PlayerGadgetChangeEvent(Player player, Gadget gadget, boolean removal) {
		this.player = player;
		this.gadget = gadget;
		this.removal = removal;
	}
	
	public Player getPlayer(){
		return player;
	}
	
	public Gadget getGadget(){
		return gadget;
	}
	
	public boolean isRemoved(){
		return removal;
	}

	public HandlerList getHandlers() {
	    return handlers;
	}
	 
	public static HandlerList getHandlerList() {
	    return handlers;
	}

}