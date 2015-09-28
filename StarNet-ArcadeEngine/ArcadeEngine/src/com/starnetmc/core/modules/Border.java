package com.starnetmc.core.modules;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.starnetmc.core.modules.manager.Module;
import com.starnetmc.core.modules.manager.ModuleType;

public class Border extends Module {

	public Border(JavaPlugin plugin) {
		super("World Border",1.0,ModuleType.SERVER,plugin);
	}
	

	public Border() {
		// TODO Auto-generated constructor stub
	}


	@Override
	public void enable() {
		isEnabled = true;
		log("enabled.");
	}

	@Override
	public void disable() {
		isEnabled = false;
		log("disabled.");

	}

	public static boolean isEnabled;

	

	@EventHandler(priority = EventPriority.LOW)
	public void onCross(PlayerMoveEvent e) {

		if(isEnabled = true) {
		
		Player player = e.getPlayer();
		Location _spawn = player.getWorld().getSpawnLocation();
		
		double range = 150;
		double rangeSquared = range*range;
		if (player.getLocation().distanceSquared(_spawn) > rangeSquared) {
			player.teleport(_spawn);

		} else {
			return;
		}

		}
		else {
			return;
		}
	}

}
