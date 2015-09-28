package com.starnetmc.ArcadeEngine.Managers.Maps;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.starnetmc.ArcadeCore;
import com.starnetmc.ArcadeEngine.ArcadeManager;
import com.starnetmc.ArcadeEngine.Events.MapChangeEvent;
import com.starnetmc.ArcadeEngine.Games.GameType;
import com.starnetmc.ArcadeEngine.Managers.Maps.MapProperties.MapProperties;
import com.starnetmc.ArcadeEngine.Managers.Maps.MapProperties.MapPropertiesManager;
import com.starnetmc.ArcadeEngine.Managers.Maps.MapTypes.BBMap;
import com.starnetmc.ArcadeEngine.Managers.Maps.MapTypes.DSMap;
import com.starnetmc.ArcadeEngine.Managers.Maps.MapTypes.Map;
import com.starnetmc.ArcadeEngine.Managers.Maps.MapTypes.TeamMaps.FourTeamMap;
import com.starnetmc.ArcadeEngine.Managers.Maps.MapTypes.TeamMaps.TwoTeamMap;
import com.starnetmc.ArcadeEngine.Utils.AF;
import com.starnetmc.ArcadeEngine.Utils.Logger;

public class Mapper {
	
	private ArcadeManager manager;
	private MapPropertiesManager propertiesManager;
	
	private MapConfig config;
	
	private Location lobby;
    private int activeMapID;
    private Map activeMap;
	private Random r = new Random();
	
	public Mapper(ArcadeManager manager){
		
		this.manager = manager;
		config = new MapConfig();
		World lobbyworld = getConfig().getString("LobbyWorld") != null ? Bukkit.getServer().getWorld(getConfig().getString("LobbyWorld")) : Bukkit.getServer().getWorlds().get(0);
		this.lobby = lobbyworld.getSpawnLocation();
		Bukkit.getServer().getPluginManager().registerEvents(new MapLoader(), ArcadeCore.getPlugin());
		propertiesManager = new MapPropertiesManager(manager, new MapProperties());
		propertiesManager.register();
		Logger.log("<Mapper> Mapper mapped ;D");
	}
	
    public Map despenseMap(){
    	return despenseMap(manager.getGame());
	}
    
    public Map despenseMap(GameType gType){
    	
    	int id;
		  id = randomiseID();
		  if (activeMapID == id){
			  id = randomiseID(gType);
		  }
				  
			  activeMapID = id;
			  activeMap = null;
			  activeMap = forgeMapFromConfig(gType, "" + activeMapID);
		      Bukkit.getServer().getPluginManager().callEvent(new MapChangeEvent(activeMap));
	          return activeMap;
	 }
    
    public void setMap(GameType gType, int id){
    	
    	String wrappedID = "" + id;
    	
    	if (forgeMapFromConfig(gType, wrappedID).getAuthor().equalsIgnoreCase("Mapper")){
    		Logger.log("<Maps> Tried to set map...no map with the id:" + id + " exists.");
    		return;
    	}
    	
    	activeMap = forgeMapFromConfig(gType, wrappedID);
    	Bukkit.getServer().getPluginManager().callEvent(new MapChangeEvent(activeMap));
		Logger.log("<Maps> Map set to " + activeMap.getName() + "with the id:" + activeMap.getID());
		AF.broadcast("Map set to " + activeMap.getName());
		return;
    }
    
    public void setMap(Player p, GameType gType, int id){
    	
    	String wrappedID = "" + id;
    	
    	if (forgeMapFromConfig(gType, wrappedID) == null){
    		Logger.log("<Maps> " + p.getName()  + " Tried to set map...no map with the id:" + id + " exists.");
    		p.sendMessage(AF.map("Tried to set map...no map with the id:" + id + " exists."));
    		return;
    	}
    	
    	activeMap = forgeMapFromConfig(gType, wrappedID);
    	Bukkit.getServer().getPluginManager().callEvent(new MapChangeEvent(activeMap));
		Logger.log("<Maps> " + p.getName()  + " set Map to " + activeMap.getName() + "with the id:" + activeMap.getID());
		AF.broadcast("Map set to " + activeMap.getName() + " by " + p.getName());
		return;
    }
    
    public void setMap(String var_name, MapType mapType, int id){
    	
    	String wrappedID = "" + id;
    	
    	if (forgeMapFromConfig(var_name, mapType, wrappedID) == null){
    		Logger.log("<Maps> Tried to set map...no map with the id:" + id + " exists.");
    		return;
    	}
    	
    	activeMap = forgeMapFromConfig(var_name, mapType, wrappedID);
    	Bukkit.getServer().getPluginManager().callEvent(new MapChangeEvent(activeMap));
		Logger.log("<Maps> Map set to " + activeMap.getName() + "with the id:" + activeMap.getID());
		AF.broadcast("Map set to " + activeMap.getName());
		return;
    }
    
    public void setMap(Player p, String var_name, MapType mapType, int id){
    	
    	String wrappedID = "" + id;
    	
    	if (forgeMapFromConfig(var_name, mapType, wrappedID) == null){
    		Logger.log("<Maps> " + p.getName()  + " Tried to set <CUSTOM> Map...no map with the id:" + id + " exists.");
    		p.sendMessage(AF.map("Tried to set <CUSTOM> Map...no map with the id:" + id + " exists."));
    		return;
    	}
    	
    	activeMap = forgeMapFromConfig(var_name, mapType, wrappedID);
    	Bukkit.getServer().getPluginManager().callEvent(new MapChangeEvent(activeMap));
		Logger.log("<Maps> " + p.getName()  + " set Map to <CUSTOM> " + activeMap.getName() + "with the id:" + activeMap.getID());
		AF.broadcast("Map set to " + activeMap.getName() + " by " + p.getName());
		return;
    }
	
   private int randomiseID(){
	int id = 0;
	  for(int counter = 1; counter<=1; counter++) {
		  id = 1+r.nextInt(getAllMaps(manager.getGame()).size());
	  }	  
	  
	  return id;
    }
   
   private int randomiseID(GameType game){
	int id = 0;
	  for(int counter = 1; counter<=1; counter++) {
		  id = 1+r.nextInt(getAllMaps(game).size());
	  }	  
	  
	  return id;
    }
   
   public Map getActiveMap(){
	   return activeMap;
   }
   
   public Location getLobby(){
	   return lobby;
   }
   
   public String forgeCurrentMapWorldName(){
	   return forgeCurrentMapWorldName(activeMap.getVarName(), activeMapID);
   }
   
   public String forgeCurrentMapWorldName(String var_name, int mapID){
		String path = var_name + ".Maps." + mapID;
		return getConfig().getString(path + ".MapWorld") != null ? getConfig().getString(path + ".MapWorld") : "WorldNotFound";
   }
   
   public void refreshCurrentMap(){
	   activeMap = forgeMapFromConfig(manager.getGame(), "" + activeMapID);
	   Logger.log("<Maps> Refreshed Map locations for map: " + activeMap.getName());
   }
    
	public Map forgeMapFromConfig(GameType gameType, String mapID){
            String var_name = gameType.toString().toLowerCase();
            MapType mapType = gameType.getMapType();
        
            Map forgedMap = forgeMapFromConfig(var_name, mapType, mapID);
            return forgedMap;
          }
	
	public Map forgeMapFromConfig(String var_name, MapType mapType, String mapID){
		
	        List<Location> spns = new ArrayList<Location>();
	        int mapNumber = Integer.parseInt(mapID);
	        
			String path = var_name + ".Maps." + mapNumber;
            String name = getConfig().getString(path + ".MapName");
            String author = getConfig().getString(path + ".Author");
            
	        //Loads basic map needs
			if (getConfig().getConfigurationSection(var_name + ".Maps") != null){
			for (String id : getConfig().getConfigurationSection(var_name + ".Maps." + mapNumber + ".gameSpawns").getKeys(false)){
				
				int gNumber = Integer.parseInt(id);
				String sPath = path + ".gameSpawns." + gNumber;	                
	            spns.add(loadLocFromConfig(sPath));
			  }
			}
			
            if (name == null){
            	
				Logger.log("<Maps> Tried to forgeMap for var_name: " + var_name + " but Map was null.");
			    Map emptyMap = new Map("No Map Found", "Mapper", "nomap", null, null, -1, null, -1, new MapProperties());
			    return emptyMap;
            }
            
            boolean rain = getConfig().getBoolean(path + ".MapProperties.Rain");
            long time = getConfig().getLong(path + ".MapProperties.Time");
			
            
	        Location sSpawn = loadLocFromConfig(path + ".specSpawn");
	        Location BCenter = loadLocFromConfig(path + ".borderCenter");
	        double BRad = getConfig().getDouble(path + ".borderRad");
	        
	        if (mapType == MapType.DEFAULT){
			
	            Map forgedMap = new Map(name, author, var_name, spns, sSpawn, mapNumber, BCenter, BRad, new MapProperties(rain, time));
			    return forgedMap;
	        }
	        
	        if (mapType == MapType.TEAMMAP_TWO){
	        	
	        	List<Location> fTeamSpns = new ArrayList<Location>();
	        	List<Location> sTeamSpns = new ArrayList<Location>();
	        	
	    		if (getConfig().getConfigurationSection(var_name + ".Maps") != null){
	    			for (String id : getConfig().getConfigurationSection(var_name + ".Maps." + mapNumber + ".firstTeamSpawns").getKeys(false)){
	    				
	    				int gNumber = Integer.parseInt(id);
	    				String sPath = var_name + ".Maps." + mapNumber + ".firstTeamSpawns." + gNumber;	                
	    	            fTeamSpns.add(loadLocFromConfig(sPath));
	    			  }
	    			}
	    		
	    		if (getConfig().getConfigurationSection(var_name + ".Maps") != null){
	    			for (String id : getConfig().getConfigurationSection(var_name + ".Maps." + mapNumber + ".secondTeamSpawns").getKeys(false)){
	    				
	    				int gNumber = Integer.parseInt(id);
	    				String sPath = var_name + ".Maps." + mapNumber + ".secondTeamSpawns." + gNumber;	                
	    				sTeamSpns.add(loadLocFromConfig(sPath));
	    			  }
	    			}
	    		
	    		TwoTeamMap forgedMap = new TwoTeamMap(name, author, var_name, spns, sSpawn, mapNumber, fTeamSpns, sTeamSpns, BCenter, BRad, new MapProperties(rain, time));
	    	    return forgedMap;
	        	
	        }
	        
	        if (mapType == MapType.TEAMMAP_FOUR){
	        	
	        	List<Location> fTeamSpns = new ArrayList<Location>();
	        	List<Location> sTeamSpns = new ArrayList<Location>();
	        	List<Location> tTeamSpns = new ArrayList<Location>();
	        	List<Location> fRTeamSpns = new ArrayList<Location>();
	        	
	    		if (getConfig().getConfigurationSection(var_name + ".Maps") != null){
	    			for (String id : getConfig().getConfigurationSection(var_name + ".Maps." + mapNumber + ".firstTeamSpawns").getKeys(false)){
	    				
	    				int gNumber = Integer.parseInt(id);
	    				String sPath = var_name + ".Maps." + mapNumber + ".firstTeamSpawns." + gNumber;	                
	    	            fTeamSpns.add(loadLocFromConfig(sPath));
	    			  }
	    			}
	    		
	    		if (getConfig().getConfigurationSection(var_name + ".Maps") != null){
	    			for (String id : getConfig().getConfigurationSection(var_name + ".Maps." + mapNumber + ".secondTeamSpawns").getKeys(false)){
	    				
	    				int gNumber = Integer.parseInt(id);
	    				String sPath = var_name + ".Maps." + mapNumber + ".secondTeamSpawns." + gNumber;	                
	    				sTeamSpns.add(loadLocFromConfig(sPath));
	    			  }
	    			}
	    		
	    		if (getConfig().getConfigurationSection(var_name + ".Maps") != null){
	    			for (String id : getConfig().getConfigurationSection(var_name + ".Maps." + mapNumber + ".thirdTeamSpawns").getKeys(false)){
	    				
	    				int gNumber = Integer.parseInt(id);
	    				String sPath = var_name + ".Maps." + mapNumber + ".thirdTeamSpawns." + gNumber;	                
	    	            tTeamSpns.add(loadLocFromConfig(sPath));
	    			  }
	    			}
	    		
	    		if (getConfig().getConfigurationSection(var_name + ".Maps") != null){
	    			for (String id : getConfig().getConfigurationSection(var_name + ".Maps." + mapNumber + ".fourthTeamSpawns").getKeys(false)){
	    				
	    				int gNumber = Integer.parseInt(id);
	    				String sPath = var_name + ".Maps." + mapNumber + ".fourthTeamSpawns." + gNumber;	                
	    				fRTeamSpns.add(loadLocFromConfig(sPath));
	    			  }
	    			}
	        	
	    		FourTeamMap forgedMap = new FourTeamMap(name, author, var_name, spns, sSpawn, mapNumber, fTeamSpns, sTeamSpns, tTeamSpns, fRTeamSpns, BCenter, BRad, new MapProperties(rain, time));
	    	    return forgedMap;
	        }
	        
	        //Add other MapTypes here
	        if (mapType == MapType.DSMAP){
	        	
	        	Location swordSpawn = loadLocFromConfig(path + ".swordSpawn");
	        	Location portalSpawn = loadLocFromConfig(path + ".portalSpawn");
	        	
	        	DSMap forgedMap = new DSMap(name, author, var_name, spns, swordSpawn, portalSpawn, sSpawn, mapNumber, BCenter, BRad, new MapProperties(rain, time));
	        	return forgedMap;
	        }
	        
	        if (mapType == MapType.BBMAP){
	        	
	        	List<Location> safezones = new ArrayList<Location>();
	        	
				for (String id : getConfig().getConfigurationSection(var_name + ".Maps." + mapNumber + ".safezones").getKeys(false)){
					
					int gNumber = Integer.parseInt(id);
					String sPath = path + ".safezones." + gNumber;	                
					safezones.add(loadLocFromConfig(sPath));
				  }
	        	
	        	BBMap forgedMap = new BBMap(name, author, var_name, spns, sSpawn, safezones, mapNumber, BCenter, BRad, new MapProperties(rain, time));
	        	return forgedMap;
	        }
	        
	        Logger.log("<Maps> Tried to getAllMaps for var_name: " + var_name + " but no Maps were found.");
	        return new Map("No Map Found", "Mapper", "nomap", null, null, -1, null, -1, new MapProperties());
	}
	
	public ArrayList<Map> getAllMaps(GameType gameType){
		return getAllMaps(gameType.toString().toLowerCase(), gameType.getMapType());
	}
	
	public ArrayList<Map> getAllMaps(String var_name, MapType mapType){
		
		ArrayList<Map> allmaps = new ArrayList<Map>();
		
		if (getConfig().getConfigurationSection(var_name + ".Maps") != null){
			for (String id : getConfig().getConfigurationSection(var_name + ".Maps").getKeys(false)){
			 
				Map forged = forgeMapFromConfig(var_name, mapType, id);
				allmaps.add(forged);
			}
		}
		
			if (allmaps.size() == 0){
				Logger.log("<Maps> Tried to getAllMaps for var_name: " + var_name + " but no Maps were found.");
			     allmaps.add(new Map("No Map Found", "Mapper", "nomap", null, null, -1, null, -1, new MapProperties()));
			}
			
		return allmaps;
		
	}
	
	public Location loadLocFromConfig(String path) {
		
		double x = getConfig().getDouble(path + ".x");
		double y = getConfig().getDouble(path + ".y");
		double z = getConfig().getDouble(path + ".z");
		
		//Example Path: var_name.Maps.mapNumber.MapWorld;
		String[] pathArray = path.split("\\.");
		String pathVar = pathArray[0];
		String pathBase = pathArray[1];
		String pathMapID = pathArray[2];
		
		String forgedWorldPath = pathVar + "." + pathBase + "." + pathMapID + ".MapWorld";
		String mapWorldName = getConfig().getString(forgedWorldPath);
		
		World world = Bukkit.getServer().getWorld(mapWorldName);
		
		return new Location(world, x, y, z);
	}
	
	public void setConfigManager(MapConfig config){
		this.config = config;
	}
	
	public FileConfiguration getConfig(){
		return config.getConfig();
	}
	
	public MapConfig getConfigManager(){
		return config;
	}
}