package com.starnetmc.core.modules;

import org.bukkit.plugin.java.JavaPlugin;

import com.starnetmc.core.commands.TeleportAll;
import com.starnetmc.core.commands.TeleportCommand;
import com.starnetmc.core.modules.manager.Module;
import com.starnetmc.core.modules.manager.ModuleType;

public class Teleport extends Module {

	public Teleport(JavaPlugin plugin) {
		super("Teleport Manager",1.0,ModuleType.SERVER,plugin);
	}
	
	public Teleport() {
		// TODO Auto-generated constructor stub
	}

	public static boolean isEnabled;
	
	public void enable() {
		isEnabled = true;
		log("Enabled.");
	}
	
	public void disable() {
		isEnabled = false;
		log("Disabled.");
	}
	
	public void addCommands() {
		
		addCommand(new TeleportCommand(this));		
		addCommand(new TeleportAll(this));
		
	}
	
}
