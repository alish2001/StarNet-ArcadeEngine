package com.starnetmc.ArcadeEngine.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.starnetmc.ArcadeEngine.Games.TeamGame.Team;

public class TeamChangeEvent extends Event {
	
	private static final HandlerList handlers = new HandlerList();
	
    private Player player;
    private Team team;
    
    public TeamChangeEvent(Player player, Team team) {
        this.player = player;
        this.team = team;
    }
 
    public Player getPlayer() {
        return player;
    }
    
    public Team getTeam(){
    	return team;
    }
	 
	public HandlerList getHandlers() {
	    return handlers;
	}
	 
	public static HandlerList getHandlerList() {
	    return handlers;
	}


}