package com.starnetmc.core.util;

import java.util.Iterator;

import net.minecraft.server.v1_8_R3.IInventory;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.HumanEntity;
import org.bukkit.plugin.java.JavaPlugin;

public class MemoryFix {
	private JavaPlugin _plugin;

	Object tileEntity;
	
	public MemoryFix(JavaPlugin plugin) {
		this._plugin = plugin;

		this._plugin.getServer().getScheduler()
				.scheduleSyncRepeatingTask(this._plugin, new Runnable() {
					public void run() {
						Iterator<?> localIterator2;
						for (Iterator<?> localIterator1 = Bukkit.getWorlds()
								.iterator(); localIterator1.hasNext(); localIterator2
								.hasNext()) {
							World world = (World) localIterator1.next();

							localIterator2 = ((CraftWorld) world).getHandle().tileEntityList
									.iterator();
							if (localIterator2.hasNext()) {
								tileEntity = localIterator2.next();
								continue;
							}
							if ((tileEntity instanceof IInventory)) {
								Iterator<HumanEntity> entityIterator = ((IInventory) tileEntity)
										.getViewers().iterator();
								while (entityIterator.hasNext()) {
									HumanEntity entity = (HumanEntity) entityIterator
											.next();
									if (((entity instanceof CraftPlayer))
											&& (!((CraftPlayer) entity)
													.isOnline())) {
										entityIterator.remove();
									}
								}
							} else {
								return;
							}
						}
					}
				}, 100L, 100L);
	}
}