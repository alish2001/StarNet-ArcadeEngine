package com.starnetmc.ArcadeEngine.Managers.GameProperties.PropertiesManagers;

import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

import com.starnetmc.ArcadeEngine.Managers.GameProperties.GamePropertiesManager;
import com.starnetmc.ArcadeEngine.Managers.GameProperties.PropertiesManager;

public class PropertiesManager_Items extends PropertiesManager {

	public PropertiesManager_Items(GamePropertiesManager gamePropertiesManager, String propertiesManagerName) {
		super(gamePropertiesManager, "Items");
	}
	
	public PropertiesManager_Items(GamePropertiesManager gamePropertiesManager) {
		super(gamePropertiesManager);
		setName("Items");
	}
	
	@EventHandler
	public void onItemDrop(PlayerDropItemEvent e){
		
		if (getGamePropertiesManager().getProperties().getItemDrop() == false){
			e.setCancelled(true);
			return;
		}
	}
	
	@EventHandler
	public void onItemPickup(PlayerPickupItemEvent e){
		
		if (getGamePropertiesManager().getProperties().getItemPickup() == false){
			e.setCancelled(true);
			return;
		}
	}
	
	@EventHandler
	public void onItemEat(PlayerItemConsumeEvent e){
		
		if (getGamePropertiesManager().getProperties().getItemEat() == false){
			e.setCancelled(true);
			return;
		}
	}
	
	@EventHandler
	public void onInvModification(InventoryClickEvent e){
		
		if (e.getClickedInventory() != e.getWhoClicked().getInventory()) return;
		if (getGamePropertiesManager().getProperties().getInventoryModification() == false){
			e.setCancelled(true);
			return;
		}
	}	

}