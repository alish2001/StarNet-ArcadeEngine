package com.starnetmc.ArcadeEngine.Managers.Maps.MapTypes;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import com.starnetmc.ArcadeCore;
import com.starnetmc.ArcadeEngine.Managers.Maps.MapProperties.MapProperties;

public class BBMap extends Map {

	private List<Location> safezones;
	
	public BBMap(String name, String maker, String var_name, List<Location> spawns, Location specSpawn, List<Location> safezones, int ID, Location borderCenter, double borderRad, MapProperties properties) {
		super(name, maker, var_name, spawns, specSpawn, ID, borderCenter, borderRad, properties);
		this.safezones = safezones;
		addParam("SafeZone");
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
			
		    int spawnNumber = 1;
		    int zoneNumber = 1;
		    
			String path = var_name + ".Maps." + mapNumbers;
		    config.set(path + ".MapName", getName());
		    config.set(path + ".Author", getMaker());
		    config.set(path + ".MapWorld", mapWorldname);
		    
		    config.set(path + ".MapProperties.Rain", properties.isRain());
		    config.set(path + ".MapProperties.Time", properties.getTime());
		    
		    config.set(path + ".specSpawn.x", specSpawn.getX());
		    config.set(path + ".specSpawn.y", specSpawn.getY());
		    config.set(path + ".specSpawn.z", specSpawn.getZ());
		    
		    config.set(path + ".borderRad", borderRad);
		    config.set(path + ".borderCenter.x", borderCenter.getX());
		    config.set(path + ".borderCenter.y", borderCenter.getY());
		    config.set(path + ".borderCenter.z", borderCenter.getZ());
		    
            for (Location loc : getSpawns()){
            	config.set(path + ".gameSpawns." + spawnNumber + ".x", loc.getX());
            	config.set(path + ".gameSpawns." + spawnNumber + ".y", loc.getY());
            	config.set(path + ".gameSpawns." + spawnNumber + ".z", loc.getZ());
		        
                spawnNumber++;
	        }
            
            for (Location loc : safezones){
            	config.set(path + ".safezones." + zoneNumber + ".x", loc.getX());
            	config.set(path + ".safezones." + zoneNumber + ".y", loc.getY());
            	config.set(path + ".safezones." + zoneNumber + ".z", loc.getZ());
		        
            	zoneNumber++;
	        }
            
	        ArcadeCore.getArcadeManager().getMapper().getConfigManager().save();
	}
	
	public void setSafeZones(List<Location> safezones){
		this.safezones = safezones;
	}
	
	public void addSafeZone(Location safezone){
		this.safezones.add(safezone.subtract(0, 1, 0));
	}
	
	public List<Location> getSafeZones(){
		return safezones;
	}
}