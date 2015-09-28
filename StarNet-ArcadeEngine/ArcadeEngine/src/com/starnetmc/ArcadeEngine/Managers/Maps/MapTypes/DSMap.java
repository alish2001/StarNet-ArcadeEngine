package com.starnetmc.ArcadeEngine.Managers.Maps.MapTypes;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import com.starnetmc.ArcadeCore;
import com.starnetmc.ArcadeEngine.Managers.Maps.MapProperties.MapProperties;

public class DSMap extends Map{

	private Location swordSpawn;
	private Location portalSpawn;
	
	public DSMap(String name, String maker, String var_name, List<Location> spawns, Location swordSpawn, Location portalSpawn, Location specSpawn, int ID, Location borderCenter, double borderRad, MapProperties properties) {
		super(name, maker, var_name, spawns, specSpawn, ID, borderCenter, borderRad, properties);
		
		this.swordSpawn = swordSpawn;
		this.portalSpawn = portalSpawn;
		addParam("DSwordSpawn");
		addParam("DSPortalSpawn");
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
		    
            for (Location loc : getSpawns()){
            	config.set(path + ".gameSpawns." + spawnNumber + ".x", loc.getX());
            	config.set(path + ".gameSpawns." + spawnNumber + ".y", loc.getY());
            	config.set(path + ".gameSpawns." + spawnNumber + ".z", loc.getZ());
		        
                spawnNumber++;
	        }
            
        	config.set(path + ".portalSpawn." + ".x", portalSpawn.getX());
        	config.set(path + ".portalSpawn." + ".y", portalSpawn.getY());
        	config.set(path + ".portalSpawn." + ".z", portalSpawn.getZ());
            
        	config.set(path + ".swordSpawn." + ".x", swordSpawn.getX());
        	config.set(path + ".swordSpawn." + ".y", swordSpawn.getY());
        	config.set(path + ".swordSpawn." + ".z", swordSpawn.getZ());
            
	        ArcadeCore.getArcadeManager().getMapper().getConfigManager().save();
	}
	
	public void setSwordSpawn(Location swordSpawn){
		this.swordSpawn = swordSpawn;
	}
	
	public void setPortalSpawn(Location portalSpawn){
		this.portalSpawn = portalSpawn;
	}
	
	public Location getSwordSpawn(){
		return swordSpawn;
	}
	
	public Location getPortalSpawn(){
		Location pSpawn = new Location(portalSpawn.getWorld(), portalSpawn.getX(), portalSpawn.getY(), portalSpawn.getZ());
		return pSpawn;
	}

}