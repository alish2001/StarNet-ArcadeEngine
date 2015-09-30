package com.starnetmc.ArcadeEngine.Utils;

import java.util.ArrayList;
import java.util.List;

import com.starnetmc.ArcadeEngine.Games.GameType;
import com.starnetmc.ArcadeEngine.Managers.Maps.MapType;
import com.starnetmc.core.util.StarMap;

public class All {
	
	public static List<GameType> getAllGameTypes(){
		List<GameType> allGames = new ArrayList<GameType>();
		
		//Add all games here
		allGames.add(GameType.SPLEEF);
		allGames.add(GameType.DRAGONSWORD);
		allGames.add(GameType.SURVIVALGAMES);
		allGames.add(GameType.BORDERBUSTERS);
		allGames.add(GameType.PACMAN);
		return allGames;
	}
	
	public static StarMap<GameType, String> getAllVarNames(){
		StarMap<GameType, String> allVarNames = new StarMap<GameType, String>();
		
		for (GameType g : getAllGameTypes()){
		     allVarNames.put(g, g.toString().toLowerCase());
		}
		
		return allVarNames;
	}
	
	public static ArrayList<MapType> getAllMapTypes(){
		ArrayList<MapType> allMapTypes = new ArrayList<MapType>();
		
		//Add all MapTypes here
		allMapTypes.add(MapType.DEFAULT);
		allMapTypes.add(MapType.DSMAP);
		allMapTypes.add(MapType.BBMAP);
		allMapTypes.add(MapType.TEAMMAP_TWO);
		allMapTypes.add(MapType.TEAMMAP_FOUR);
		
		return allMapTypes;
	}
}