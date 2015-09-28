package com.starnetmc.ArcadeEngine.Managers.Maps.MapTypes.TeamMaps;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import com.starnetmc.ArcadeCore;
import com.starnetmc.ArcadeEngine.Managers.Maps.MapProperties.MapProperties;
import com.starnetmc.ArcadeEngine.Managers.Maps.MapTypes.Map;

public class TwoTeamMap extends Map {
	
	private List<Location> firstTeamSpawns;
	private List<Location> secondTeamSpawns;
	
	public TwoTeamMap(String name, String maker, String var_name, List<Location> spawns, Location specSpawn, int ID, 
			List<Location> firstTeamSpawns, 
			List<Location> secondTeamSpawns, Location borderCenter, double borderRad, MapProperties properties) {
		super(name, maker, var_name, spawns, specSpawn, ID, borderCenter, borderRad, properties);
		
		this.firstTeamSpawns = firstTeamSpawns;
		this.secondTeamSpawns = secondTeamSpawns;
		
		removeParam("Spawn");
		addParam("FirstTSpawn");
		addParam("SecondTSpawn");
	}
	
	@Override
	public void register(boolean modify) {
		
		FileConfiguration config = ArcadeCore.getArcadeManager().getMapper().getConfig();
		
		int mapNumbers = 0;
		
		if (!modify){
			
			if (config.getConfigurationSection(var_name + ".Maps") == null){
				mapNumbers = 1;
			} else {
			mapNumbers = config.getConfigurationSection(var_name + ".Maps").getKeys(false).size() + 1;
			}
            setID(mapNumbers);
		} else {
			mapNumbers = ID;
		}
		    
			String path = var_name + ".Maps." + mapNumbers;
		    config.set(path + ".MapName", getName());
		    config.set(path + ".Author", getMaker());
		    config.set(path + ".MapWorld", getMapWorldName());
		    
		    config.set(path + ".MapProperties.Rain", properties.isRain());
		    config.set(path + ".MapProperties.Time", properties.getTime());
		    
		    config.set(path + ".specSpawn.x", specSpawn.getX());
		    config.set(path + ".specSpawn.y", specSpawn.getY());
		    config.set(path + ".specSpawn.z", specSpawn.getZ());
		    
		    config.set(path + ".borderRad", borderRad);
		    config.set(path + ".borderCenter.x", borderCenter.getX());
		    config.set(path + ".borderCenter.y", borderCenter.getY());
		    config.set(path + ".borderCenter.z", borderCenter.getZ());
		    
		    int fTSpawnNumber = 1;
		    int sTSpawnNumber = 1;
		    
            for (Location loc : firstTeamSpawns){
	        	
            	config.set(path + ".firstTeamSpawns." + fTSpawnNumber + ".x", loc.getX());
            	config.set(path + ".firstTeamSpawns." + fTSpawnNumber + ".y", loc.getY());
            	config.set(path + ".firstTeamSpawns." + fTSpawnNumber + ".z", loc.getZ());
		        
                fTSpawnNumber++;
            }
            
            for (Location loc : secondTeamSpawns){
	        	
            	config.set(path + ".secondTeamSpawns." + sTSpawnNumber + ".x", loc.getX());
            	config.set(path + ".secondTeamSpawns." + sTSpawnNumber + ".y", loc.getY());
            	config.set(path + ".secondTeamSpawns." + sTSpawnNumber + ".z", loc.getZ());
		        
                sTSpawnNumber++;
	        }
            
	        ArcadeCore.getArcadeManager().getMapper().getConfigManager().save();
	}
	
	public void setFTSpawns(List<Location> spawns){
		this.firstTeamSpawns = spawns;
	}
	
	public void addFTSpawn(Location spawn){
		this.firstTeamSpawns.add(spawn);
	}
	
	public void setSTSpawns(List<Location> spawns){
		this.secondTeamSpawns = spawns;
	}
	
	public void addSTSpawn(Location spawn){
		this.secondTeamSpawns.add(spawn);
	}
	
	public List<Location> getFTSpawns(){
		return firstTeamSpawns;
	}
	
	public List<Location> getSTSpawns(){
		return secondTeamSpawns;
	}

}