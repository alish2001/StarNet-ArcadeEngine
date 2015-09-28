package com.starnetmc.ArcadeEngine.Managers.Maps.MapTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import com.starnetmc.ArcadeCore;
import com.starnetmc.ArcadeEngine.Managers.Maps.MapProperties.MapProperties;

public class Map {
	
	protected String name;
	protected String var_name;
	protected String maker;
	protected List<Location> spawns;
	protected Location specSpawn;
	protected int ID;
    protected Location borderCenter;
	protected double borderRad;
	protected String mapWorldname;
	protected ArrayList<String> parameters;
	protected MapProperties properties;
    
	public Map(String name, String maker, String var_name, List<Location> spawns, Location specSpawn, int ID, Location borderCenter, double borderRad, MapProperties properties){
		
		this.name = name;
		this.maker = maker;
		
		if (var_name != null){
		this.var_name = var_name.toLowerCase();
		} else {
			this.var_name = var_name;
		}
		
		this.spawns = spawns;
		this.specSpawn = specSpawn;
		this.ID = ID;
		this.borderCenter = borderCenter;
		this.borderRad = borderRad;
		this.mapWorldname = ArcadeCore.getArcadeManager().getMapper().forgeCurrentMapWorldName(var_name, ID) !=null ? ArcadeCore.getArcadeManager().getMapper().forgeCurrentMapWorldName(var_name, ID) : "WorldNotFound";
		this.properties = properties;
		
		this.parameters = new ArrayList<String>();
		this.parameters.add("Name");
		this.parameters.add("Author");
		this.parameters.add("var_name");
		this.parameters.add("Spawn");
		this.parameters.add("SpecSpawn");
		this.parameters.add("BorderCenter");
		this.parameters.add("BorderRadius");
		this.parameters.add("Rain");
		this.parameters.add("Time");
	}
	
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
            
	        ArcadeCore.getArcadeManager().getMapper().getConfigManager().save();
	}
	
	@Deprecated
	public void forgeParamsList(){
		this.parameters = new ArrayList<String>();
		this.parameters.add("Name");
		this.parameters.add("Author");
		this.parameters.add("var_name");
		this.parameters.add("Spawn");
		this.parameters.add("SpecSpawn");
		this.parameters.add("BorderCenter");
		this.parameters.add("BorderRadius");
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setMaker(String maker){
		this.maker = maker;
	}
	
	public void setVarName(String var_name){
		this.var_name = var_name;
	}
	
	public void setSpawns(List<Location> spawns){
		this.spawns = spawns;
	}
	
	public void addSpawn(Location spawn){
		this.spawns.add(spawn);
	}
	
	public void setSpecSpawn(Location specSpawn){
		this.specSpawn = specSpawn;
	}
	
	public void setID(int ID){
		this.ID = ID;
	}
	
	public void setCenter(Location center){
		this.borderCenter = center;
		this.mapWorldname = center.getWorld().getName();
	}
	
	public void setBRad(double borderRad){
		this.borderRad = borderRad;
	}
	
	public void setMapWorldName(String mapWorldName){
		this.mapWorldname = mapWorldName;
	}
	
	public void setProperties(MapProperties properties){
		this.properties = properties;
	}
	
	public void setParams(ArrayList<String> params){
		this.parameters = params;
	}
	
	public void addParam(String param){
		this.parameters.add(param);
	}
	
	public void removeParam(String param){
		if (this.parameters.contains(param)) this.parameters.remove(param);
	}
	
	public String getName(){
		return name;
	}
	
	public String getMaker() {
		return maker;
	}
	
	public String getAuthor() {
		return maker;
	}

	public String getVarName(){
		return var_name;
	}
	
	public List<Location> getSpawns(){
		return spawns;
	}
	
	public Location getSpecSpawn(){
		return specSpawn;
	}
	
	public int getID(){
		return ID;
	}
	
	public Location getCenter(){
		return borderCenter;
	}
	
	public double getBRad(){
		return borderRad;
	}
	
	public String getMapWorldName(){
		return mapWorldname;
	}
	
	public MapProperties getProperties(){
		return properties;
	}
	
	public Location getRandomSpawnPoint(){
	      Random r = new Random();
          int spawnPoint = 0;
	      
		  for(int counter = 1; counter<=1; counter++) {
			  spawnPoint = r.nextInt(spawns.size());
		  }	  
		  
		  return spawns.get(spawnPoint);
	}
	
	public ArrayList<String> getParams(){
		return parameters;
	}

}