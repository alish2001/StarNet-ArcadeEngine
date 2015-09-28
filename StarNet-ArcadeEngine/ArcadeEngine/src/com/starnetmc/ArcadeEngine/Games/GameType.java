package com.starnetmc.ArcadeEngine.Games;

import com.starnetmc.ArcadeEngine.Managers.Maps.MapType;
import com.starnetmc.ArcadeEngine.Utils.All;

public enum GameType {

	SPLEEF("Spleef", GameInstancer.forge_SS_GClass(), MapType.DEFAULT),
	DRAGONSWORD("Dragon's Blade", GameInstancer.forge_DS_GClass(), MapType.DSMAP),
	SURVIVALGAMES("Survival Games", GameInstancer.forge_SG_GClass(), MapType.DEFAULT),
	BORDERBUSTERS("Border Busters", GameInstancer.forge_BB_GClass(), MapType.BBMAP);
	
	private String name;
	private Game gClass;
	private MapType mapType;
	
	GameType(String name, Game gClass, MapType mapType){
		this.name = name;
		this.gClass = gClass;
		this.mapType = mapType;
	}
	
	public String getName(){
		return name;
	}
	
	public Game getGClass(){
		return gClass;
	}
	
	public MapType getMapType(){
		return mapType;
	}
	
	public GameType getGameTypeFromVarName(String var_name){
		GameType returnType = GameType.DRAGONSWORD;
		
		for (GameType type : All.getAllGameTypes()){
			if (var_name.equalsIgnoreCase(type.toString().toLowerCase())){
				returnType = type;
			}
		}
		
		return returnType;
	}
	
	public GameType getGameTypeFromName(String name){
		GameType returnType = GameType.DRAGONSWORD;
		
		for (GameType type : All.getAllGameTypes()){
			if (name.equalsIgnoreCase(type.getName())){
				returnType = type;
			}
		}
		
		return returnType;
	}
}