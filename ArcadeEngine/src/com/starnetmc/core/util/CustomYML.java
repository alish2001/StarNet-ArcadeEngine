package com.starnetmc.core.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.starnetmc.core.Main;

public class CustomYML {
	public static Main plugin;
	public CustomYML(Main plugin){
		CustomYML.plugin = plugin;
	}
	
	private FileConfiguration customConfig = null;
	private File customConfigFile = null;

	public void reloadCustomConfig(){
		if (customConfigFile == null){
			customConfigFile = new File(plugin.getDataFolder(), "customConfigFile.yml");
		}
	
		customConfig = YamlConfiguration.loadConfiguration(customConfigFile);
		
		
		InputStream defConfigStream = plugin.getResource("customConfigFile.yml");
		if(defConfigStream != null){
			@SuppressWarnings("deprecation")
			YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
			customConfig.setDefaults(defConfig);
		}
	}

		
	public FileConfiguration getCustomConfig(){
		if(customConfig == null){
			reloadCustomConfig();
		}
		return customConfig;
	}

	public void saveCustomConfig(){
		if(customConfig == null || customConfigFile == null){
			return;
		}
		try{
			getCustomConfig().save(customConfigFile);
		} catch (IOException ex){
			plugin.getLogger().log(Level.SEVERE, "Could not save config to " + customConfigFile, ex);
		}
	}

	public void saveDefaultConfig(){
		if(customConfigFile == null){
			customConfigFile = new File(plugin.getDataFolder(), "filter.yml");
		}
		if(!customConfigFile.exists()){
			plugin.saveResource("customConfigFile.yml", false);
		}
	}

}
