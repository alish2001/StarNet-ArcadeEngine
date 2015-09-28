package com.starnetmc.core.util;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.starnetmc.core.events.UpdateEvent;

public class Updater extends BukkitRunnable {
	private JavaPlugin _plugin;

	@SuppressWarnings("deprecation")
	public Updater(JavaPlugin plugin) {
		this._plugin = plugin;
		this._plugin.getServer().getScheduler()
				.scheduleSyncRepeatingTask(this._plugin, this, 0L, 100L);
	}

	public void run() {
		UpdateType[] arrayOfUpdateType;
		int j = (arrayOfUpdateType = UpdateType.values()).length;
		for (int i = 0; i < j; i++) {
			UpdateType updateType = arrayOfUpdateType[i];
			if (updateType.elapsed()) {
				this._plugin.getServer().getPluginManager()
						.callEvent(new UpdateEvent(updateType));
			}
		}
	}
}