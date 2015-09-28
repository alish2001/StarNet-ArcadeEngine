package com.starnetmc.core.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.starnetmc.ArcadeCore;

public class ParticleUtil {

	public static List<Location> getCircle(Location center, int radius,
			float precision) {
		List<Location> ret = new ArrayList<Location>();
		for (float i = precision; i < 2 * Math.PI; i += precision) {
			ret.add(new Location(center.getWorld(), Math.cos(i) * radius
					+ center.getX(), center.getY(), Math.sin(i) * radius
					+ center.getZ()));
		}
		return ret;
	}

	@Deprecated
	/**
	 * 
	 * @param Nerfed until further notice
	 */
	public static void spawnRings(Player player) {

		final Iterator<Location> o = getCircle(player.getLocation(), 2, 0.1F)
				.iterator();

		while (o.hasNext()) {
			Bukkit.getScheduler().scheduleSyncRepeatingTask(ArcadeCore.getPlugin(),
					new BukkitRunnable() {

						@Override
						public void run() {

							ParticleEffect.FLAME.display(0F, 0F, 0F, 0, 2, o.next()	, 2);

						}

					}, 0L, 20L);
		}

	}

}
