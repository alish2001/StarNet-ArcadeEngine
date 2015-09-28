package com.starnetmc.ArcadeEngine.Managers.Scoreboards;

import org.bukkit.entity.Player;

public class Starboard {

	private String scoreboardName;
	
	public Starboard(String name){
		this.scoreboardName = name;
	}
	
	public Starboard(){
		this.scoreboardName = "";
	}
	
	public void scoreboarder(Player p){
		
	}
	
	public void setName(String name){
		this.scoreboardName = name;
	}
	
	public String getName(){
		return scoreboardName;
	}
}
