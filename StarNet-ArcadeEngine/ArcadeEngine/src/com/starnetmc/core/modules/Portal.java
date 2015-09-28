package com.starnetmc.core.modules;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.starnetmc.core.modules.manager.Module;
import com.starnetmc.core.modules.manager.ModuleType;

public class Portal extends Module {

	public Portal(JavaPlugin plugin) {
		super("Portal",0.1,ModuleType.SERVER,plugin);
	}
	
	public void enable() {
		log("Enabled.");
	}
	
	public void disable() {
		log("Disabled.");
	}
	
	
	@EventHandler
	public void sendToServer(PlayerPortalEvent e) {
		e.setCancelled(true);
	}
	
}
