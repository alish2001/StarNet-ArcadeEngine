package com.starnetmc.ArcadeEngine.Managers.GameProperties.PropertiesManagers;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ProjectileHitEvent;

import com.starnetmc.ArcadeEngine.Events.GameStateChangeEvent;
import com.starnetmc.ArcadeEngine.Managers.GameState;
import com.starnetmc.ArcadeEngine.Managers.GameProperties.GamePropertiesManager;
import com.starnetmc.ArcadeEngine.Managers.GameProperties.PropertiesManager;

public class PropertiesManager_Environment extends PropertiesManager {

	public PropertiesManager_Environment(GamePropertiesManager gamePropertiesManager, String propertiesManagerName) {
		super(gamePropertiesManager, "Environment");
	}
	
	public PropertiesManager_Environment(GamePropertiesManager gamePropertiesManager) {
		super(gamePropertiesManager);
		setName("Environment");
	}
	
	@EventHandler
	public void weatherify(GameStateChangeEvent e){
		if (e.getChangedState() == GameState.INGAME){
			getGamePropertiesManager().getManager().getMapper().getActiveMap().getCenter().getWorld().setTime(getGamePropertiesManager().getProperties().getDefaultTime());
		}
		
		if (e.getChangedState() == GameState.LOBBY){
			getGamePropertiesManager().getManager().getMapper().getLobby().getWorld().setTime(getGamePropertiesManager().getProperties().getDefaultTime());
		}
	}
	
	@EventHandler
	public void onArrowHit(ProjectileHitEvent e){
		if (e.getEntityType() != EntityType.ARROW) return;
	    if (!getGamePropertiesManager().getProperties().getGroundArrowRemoval()) return;
		      e.getEntity().remove();
    }
	

}