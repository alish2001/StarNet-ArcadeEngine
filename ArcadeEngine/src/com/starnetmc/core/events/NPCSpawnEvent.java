package com.starnetmc.core.events;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class NPCSpawnEvent extends Event implements Cancellable{

	private LivingEntity en;
	private static final HandlerList handlers = new HandlerList();
	private String name;
	private Location location;
	private String type;
	
	
	public NPCSpawnEvent(LivingEntity en, String name,String type, Location location) {
		
		this.en = en;
		this.name = name;
		this.location = location;
		this.type = type;
		
	}
	
	@Override
	public boolean isCancelled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setCancelled(boolean arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public HandlerList getHandlers() {
		// TODO Auto-generated method stub
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
	
	public LivingEntity getEntity() {
		
		return en;
		
	}
	
	public String getName() {
		return name;
		
	}
	
	public Location getLocation() {
		
		return location;
		
	}
	
	public String getType() {
		return type;
	}
	
}
