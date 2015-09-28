package com.starnetmc.ArcadeEngine.Managers.GameProperties;

import java.util.ArrayList;
import java.util.List;

import com.starnetmc.ArcadeEngine.ArcadeManager;
import com.starnetmc.ArcadeEngine.Managers.GameProperties.PropertiesManagers.PropertiesManager_Blocks;
import com.starnetmc.ArcadeEngine.Managers.GameProperties.PropertiesManagers.PropertiesManager_DeathByCause;
import com.starnetmc.ArcadeEngine.Managers.GameProperties.PropertiesManagers.PropertiesManager_Environment;
import com.starnetmc.ArcadeEngine.Managers.GameProperties.PropertiesManagers.PropertiesManager_Grace;
import com.starnetmc.ArcadeEngine.Managers.GameProperties.PropertiesManagers.PropertiesManager_Items;
import com.starnetmc.ArcadeEngine.Managers.GameProperties.PropertiesManagers.PropertiesManager_PVE;
import com.starnetmc.ArcadeEngine.Managers.GameProperties.PropertiesManagers.PropertiesManager_PVP;
import com.starnetmc.ArcadeEngine.Utils.Logger;

public class GamePropertiesManager {
	
	private ArcadeManager manager;
	private GameProperties gameProperties;
	private List<PropertiesManager> propertiesManagers;
	
	public GamePropertiesManager(ArcadeManager manager, GameProperties gameProperties){
		this.manager = manager;
		this.gameProperties = gameProperties;
		loadDefaultPropertiesManagers();
		Logger.log("<GamePropertiesManager> GamePropertiesManager has been enabled.");
	}
	
	public GamePropertiesManager(ArcadeManager manager, List<PropertiesManager> propertiesManagers, GameProperties gameProperties){
		this.manager = manager;
		this.gameProperties = gameProperties;
		this.propertiesManagers = propertiesManagers;
		loadPropertiesManagers();
		Logger.log("<GamePropertiesManager> GamePropertiesManager has been enabled.");
	}
	
	public void loadDefaultPropertiesManagers(){
		this.propertiesManagers = new ArrayList<PropertiesManager>();
		//Add PropertiesManagers
		addPropertiesManager(new PropertiesManager_PVP(this));
		addPropertiesManager(new PropertiesManager_PVE(this));
		addPropertiesManager(new PropertiesManager_DeathByCause(this));
		addPropertiesManager(new PropertiesManager_Items(this));
		addPropertiesManager(new PropertiesManager_Blocks(this));
		addPropertiesManager(new PropertiesManager_Environment(this));
		addPropertiesManager(new PropertiesManager_Grace(this));
		Logger.log("<GamePropertiesManager> Default PropertiesManagers have been loaded.");
	}
	
	public void loadPropertiesManagers(){
		for (PropertiesManager propertiesManager : propertiesManagers){
			propertiesManager.enable();
		}
		
		Logger.log("<GamePropertiesManager> PropertiesManagers have been enabled.");
	}
	
	public void unLoadPropertiesManagers(){
		for (PropertiesManager propertiesManager : propertiesManagers){
			propertiesManager.disable();
		}
		Logger.log("<GamePropertiesManager> PropertiesManagers have been disabled.");
	}
	
	public void setPropertiesManagers(List<PropertiesManager> propertiesManagers){
		this.propertiesManagers = propertiesManagers;
	}
	
	public void addPropertiesManager(PropertiesManager propertiesManager){
		this.propertiesManagers.add(propertiesManager);
		propertiesManager.enable();
		Logger.log("<GamePropertiesManager> PropertiesManager + " + propertiesManager.getName() + " has been added to the list of PropertiesManagers.");
	}
	
	public void removePropertiesManager(PropertiesManager propertiesManager){
		if (propertiesManagers.contains(propertiesManager)){
			propertiesManager.disable();
		    this.propertiesManagers.remove(propertiesManager);
			Logger.log("<GamePropertiesManager> PropertiesManager + " + propertiesManager.getName() + " has been removed from the list of PropertiesManagers.");
		    return;
		    
		} else {
			Logger.log("<GamePropertiesManager> Tried to remove PropertiesManager + " + propertiesManager.getName() + " from the list of PropertiesManagers but it wasn't in there.");
			return;
		}
		
	}
	
	public void removePropertiesManager(String pmName){
		Logger.log("Removing properties manager via String: " + pmName);
		
		pmName = pmName.trim();
		boolean indexFound = false;
		int indexLoc = 0;
		int finalIndexLoc = 0;
		
		for (PropertiesManager pm : propertiesManagers){
			if (indexFound) return;
			Logger.log("Matching... " + pmName + " : " + pm.getName());
			if (!pm.getName().equalsIgnoreCase(pmName)){
				indexLoc++;
			} else {
				finalIndexLoc = indexLoc;
				indexFound = true;
			}
		}
		
		removePropertiesManager(propertiesManagers.get(finalIndexLoc));
	}
	
	public void setProperties(GameProperties gameProperties){
		this.gameProperties = gameProperties;
		Logger.log("<GamePropertiesManager> Properties file changed.");
	}
	
	public ArcadeManager getManager(){
		return manager;
	}
	
	public GameProperties getProperties(){
		return gameProperties;
	}
	
	public List<PropertiesManager> getPropertiesManagers(){
		return propertiesManagers;
	}

}