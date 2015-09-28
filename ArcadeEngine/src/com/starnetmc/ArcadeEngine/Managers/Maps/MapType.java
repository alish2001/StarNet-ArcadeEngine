package com.starnetmc.ArcadeEngine.Managers.Maps;

import java.util.ArrayList;

import com.starnetmc.ArcadeEngine.Managers.Maps.MapTypes.BBMap;
import com.starnetmc.ArcadeEngine.Managers.Maps.MapTypes.DSMap;
import com.starnetmc.ArcadeEngine.Managers.Maps.MapTypes.Map;
import com.starnetmc.ArcadeEngine.Managers.Maps.MapTypes.TeamMaps.FourTeamMap;
import com.starnetmc.ArcadeEngine.Managers.Maps.MapTypes.TeamMaps.TwoTeamMap;
import com.starnetmc.ArcadeEngine.Utils.All;

public enum MapType {
	
	DEFAULT(new Map(null, null, null, null, null, -1, null, -1.0d, null).getParams()),
	DSMAP(new DSMap(null, null, null, null, null, null, null, -1, null, -1.0d, null).getParams()),
	BBMAP(new BBMap(null, null, null, null, null, null, -1, null, -1.0d, null).getParams()),
	TEAMMAP_TWO(new TwoTeamMap(null, null, null, null, null, -1, null, null, null, -1.0d, null).getParams()),
	TEAMMAP_FOUR(new FourTeamMap(null, null, null, null, null, -1, null, null, null, null, null, -1.0d, null).getParams());

	private ArrayList<String> params;
	
    MapType(ArrayList<String> params){
        this.params = params;
	}
	
	public ArrayList<String> getParams(){
		return params;
	}
	
	public static MapType getMapTypeFromString(String mapType){
		MapType returnType = DEFAULT;
		
		for (MapType type : All.getAllMapTypes()){
			if (mapType.equalsIgnoreCase(type.toString())){
				returnType = type;
			}
		}
		
		return returnType;
	}
}