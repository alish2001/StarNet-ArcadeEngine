package com.starnetmc.core.events;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.starnetmc.core.util.UpdateType;

public class UpdateEvent extends Event implements Cancellable {

	private final UpdateType _ut;

	private static final HandlerList handlers = new HandlerList();

	public UpdateEvent(UpdateType type) {
		_ut = type;
	}

	@Override
	public boolean isCancelled() {
		return false;
	}

	@Override
	public void setCancelled(boolean arg0) {
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	public UpdateType getType() {
		return this._ut;

	}

}
