package com.starnetmc.core.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import com.starnetmc.core.modules.Settings;

public class Block implements Listener{

	
	@EventHandler(priority = EventPriority.LOW)
	public void onBreak(BlockBreakEvent e) throws Exception {
		
		Player player = e.getPlayer();
		
		if(Settings.getPrefs(player).getBuildMode() == false) {
			e.setCancelled(true);
		}
		else {
			return;
		}

		
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void onPlace(BlockPlaceEvent e) throws Exception {
		
		Player player = e.getPlayer();
		
		if(Settings.getPrefs(player).getBuildMode() == false) {
			e.setCancelled(true);
		}
		else {
			return;
		}

		
	}
	
	
}
