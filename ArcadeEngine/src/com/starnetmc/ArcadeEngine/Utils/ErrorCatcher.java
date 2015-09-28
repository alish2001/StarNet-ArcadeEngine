package com.starnetmc.ArcadeEngine.Utils;

import java.util.ArrayList;
import java.util.List;

import com.starnetmc.ArcadeEngine.Managers.Maps.MapTypes.Map;

public class ErrorCatcher {

	public static List<String> debugMap(Map map){
		
		List<String> errs = new ArrayList<String>();
		
		if (map.getVarName() == null){
			errs.add("Map var_name is not defined");
		}
		
		if (map.getMaker() == null){
			errs.add("Map maker is not defined");
		}
		
		if (map.getName() == null){
			errs.add("Map name is not defined");
		}
		
		if (map.getSpawns() == null){
			errs.add("Map spawns are not defined");
		}
		
		if (map.getSpecSpawn() == null){
			errs.add("Map SpecSpawn is not defined");
		}
		
		if (map.getCenter() == null){
			errs.add("Map Center is not defined");
		}
		
		if (map.getBRad() == 0){
			errs.add("Map BRad is 0");
		}
		
		return errs;
	}
}
