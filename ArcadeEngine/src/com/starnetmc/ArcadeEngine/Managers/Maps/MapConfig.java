package com.starnetmc.ArcadeEngine.Managers.Maps;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.starnetmc.ArcadeEngine.Utils.Logger;

public class MapConfig {
	
	private FileConfiguration mapConfig = null;
	private File mapConfigFile = null;
	
	public MapConfig(){
		
	}

	public void reloadCustomConfig(){
		if (mapConfigFile == null){
			mapConfigFile = new File(MapLoader.mapDir + "Maps.yml");
		}
	
		mapConfig = YamlConfiguration.loadConfiguration(mapConfigFile);	
	}

		
	public FileConfiguration getConfig(){
		if(mapConfig == null){
			reloadCustomConfig();
		}
		return mapConfig;
	}

	public void save(){
		if(mapConfigFile == null){
			mapConfigFile = new File(MapLoader.mapDir + "Maps.yml");
		}
		
		try {
			mapConfig.save(mapConfigFile);
		} catch (IOException e) {
			Logger.log("<MapConfig> Error while saving Maps.yml");
			e.printStackTrace();
		}
	}

}