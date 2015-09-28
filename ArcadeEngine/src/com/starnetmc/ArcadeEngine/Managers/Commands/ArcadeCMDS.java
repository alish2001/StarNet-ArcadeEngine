package com.starnetmc.ArcadeEngine.Managers.Commands;

import org.bukkit.plugin.java.JavaPlugin;

import com.starnetmc.ArcadeEngine.Games.GameCommand;
import com.starnetmc.ArcadeEngine.Managers.Classes.KitCommand;
import com.starnetmc.ArcadeEngine.Managers.GameProperties.GamePropertiesManagerControlerCommand;
import com.starnetmc.ArcadeEngine.Managers.GameRotations.RotateCommand;
import com.starnetmc.ArcadeEngine.Managers.Maps.MapCommand;
import com.starnetmc.core.modules.manager.Module;
import com.starnetmc.core.modules.manager.ModuleType;

public class ArcadeCMDS extends Module {
	
	public ArcadeCMDS(JavaPlugin plugin) {
		super("Arcade Commands", 1.0, ModuleType.SERVER, plugin);
	}
	

	public ArcadeCMDS() {
	}


	@Override
	public void enable() {
		addCommand(new GameCommand(this));
		addCommand(new MapCommand(this));
		addCommand(new RotateCommand(this));
		addCommand(new WorldCMD(this));
		addCommand(new DebugCMD(this));
		addCommand(new KitCommand(this));
		addCommand(new GamePropertiesManagerControlerCommand(this));
		isEnabled = true;
		log("Registerd.");
	}

	@Override
	public void disable() {
		isEnabled = false;
		log("Un-Registerd.");
	}

	public static boolean isEnabled;

}