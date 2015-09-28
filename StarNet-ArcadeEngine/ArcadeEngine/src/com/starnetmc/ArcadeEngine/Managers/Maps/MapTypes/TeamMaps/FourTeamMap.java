package com.starnetmc.ArcadeEngine.Managers.Maps.MapTypes.TeamMaps;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import com.starnetmc.ArcadeCore;
import com.starnetmc.ArcadeEngine.Managers.Maps.MapProperties.MapProperties;
import com.starnetmc.ArcadeEngine.Managers.Maps.MapTypes.Map;

public class FourTeamMap extends Map {

	private List<Location> firstTeamSpawns;
	private List<Location> secondTeamSpawns;
	private List<Location> thirdTeamSpawns;
	private List<Location> fourthTeamSpawns;
	
	public FourTeamMap(String name, String maker, String var_name, List<Location> spawns, Location specSpawn, int ID, 
			List<Location> firstTeamSpawns, 
			List<Location> secondTeamSpawns, 
			List<Location> thirdTeamSpawns, 
			List<Location> fourthTeamSpawns, Location borderCenter, double borderRad, MapProperties properties) {
		super(name, maker, var_name, spawns, specSpawn, ID, borderCenter, borderRad, properties);
		
		this.firstTeamSpawns = firstTeamSpawns;
		this.secondTeamSpawns = secondTeamSpawns;
		this.thirdTeamSpawns = thirdTeamSpawns;
		this.fourthTeamSpawns = fourthTeamSpawns;
		
		removeParam("Spawn");
		addParam("FirstTSpawn");
		addParam("SecondTSpawn");
		addParam("ThirdTSpawn");
		addParam("FourthTSpawn");
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
		    int tTSpawnNumber = 1;
		    int fRTSpawnNumber = 1;
		    
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
            
            for (Location loc : thirdTeamSpawns){
	        	
            	config.set(path + ".thirdTeamSpawns." + tTSpawnNumber + ".x", loc.getX());
            	config.set(path + ".thirdTeamSpawns." + tTSpawnNumber + ".y", loc.getY());
            	config.set(path + ".thirdTeamSpawns." + tTSpawnNumber + ".z", loc.getZ());
		        
                tTSpawnNumber++;
	        }
            
            for (Location loc : fourthTeamSpawns){
	        	
            	config.set(path + ".fourthTeamSpawns." + fRTSpawnNumber + ".x", loc.getX());
            	config.set(path + ".fourthTeamSpawns." + fRTSpawnNumber + ".y", loc.getY());
            	config.set(path + ".fourthTeamSpawns." + fRTSpawnNumber + ".z", loc.getZ());
		        
                fRTSpawnNumber++;
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
	
	public void setTTSpawns(List<Location> spawns){
		this.thirdTeamSpawns = spawns;
	}
	
	public void addTTSpawn(Location spawn){
		this.thirdTeamSpawns.add(spawn);
	}
	
	public void setFrTSpawns(List<Location> spawns){
		this.fourthTeamSpawns = spawns;
	}
	
	public void addFrTSpawn(Location spawn){
		this.fourthTeamSpawns.add(spawn);
	}
	
	public List<Location> getFTSpawns(){
		return firstTeamSpawns;
	}
	
	public List<Location> getSTSpawns(){
		return secondTeamSpawns;
	}
	
	public List<Location> getTTSpawns(){
		return thirdTeamSpawns;
	}
	
	public List<Location> getFrTSpawns(){
		return fourthTeamSpawns;
	}
}