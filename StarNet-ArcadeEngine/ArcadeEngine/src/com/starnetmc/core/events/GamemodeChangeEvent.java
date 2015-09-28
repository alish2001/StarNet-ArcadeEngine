package com.starnetmc.core.events;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GamemodeChangeEvent extends Event implements Cancellable {

	private static HandlerList handlers = new HandlerList();

	public Player player;
	public GameMode gamemode;

	public GamemodeChangeEvent(Player player, GameMode gamemode) {
		this.player = player;
		this.gamemode = gamemode;
	}

	public GamemodeChangeEvent(Player player) {

	}

	@Override
	public HandlerList getHandlers() {
		// TODO Auto-generated method stub
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
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

	public Player getPlayer() {
		return player;
	}

	public GameMode getTo() {
		return gamemode;

	}

}
