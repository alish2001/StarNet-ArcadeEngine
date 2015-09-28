package com.starnetmc.ArcadeEngine.Managers;

import net.md_5.bungee.api.ChatColor;

import com.starnetmc.ArcadeEngine.Utils.AF;


public enum GameState {

	LOBBY(AF.boldGreen + "Awaiting Players"),
	LOADING(AF.boldYellow + "Starting..."),
	INGAME(AF.boldRed + "IN-GAME"),
	POSTGAME(AF.boldGold + "Post-Game");
	
	private String statusString;
	
	GameState(String statusString){
		this.statusString = statusString;
	}
	
	public String getStatus(boolean stripColor){
		
		if (stripColor){
			String returnString = ChatColor.stripColor(statusString);
			return returnString;
		}
		
		return statusString;
	}
	
}
