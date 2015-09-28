package com.starnetmc.core.util;

import org.bukkit.Location;

public class UParticle {
	
	public static void HelixEffect(ParticleEffect type, Location loc) {

		double radius = 5;

		for (double y = 5; y >= 0; y -= 0.007) {
			radius = y / 3;
			double x = radius * Math.cos(3 * y);
			double z = radius * Math.sin(3 * y);

			double y2 = 5 - y;

			Location loc2 = new Location(loc.getWorld(), loc.getX() + x, loc.getY() + y2, loc.getZ() + z);
			type.display(0F, 0F, 0F, 0F, 1, loc2, 2);
		}

		for (double y = 5; y >= 0; y -= 0.007) {
			radius = y / 3;
			double x = -(radius * Math.cos(3 * y));
			double z = -(radius * Math.sin(3 * y));

			double y2 = 5 - y;

			Location loc2 = new Location(loc.getWorld(), loc.getX() + x, loc.getY() + y2, loc.getZ() + z);
			type.display(0F, 0F, 0F, 0F, 1, loc2, 2);
		}
	}
	
    public static void ParticleExplosion(ParticleEffect type, Location loc) {
        double radius = 5;

        for (double y = 5; y >= 0; y -= 0.007) {
                radius = y / 3;
                double x = radius * Math.cos(3 * y);
                double z = radius * Math.sin(3 * y);

                double y2 = 5 - y;

                Location loc2 = new Location(loc.getWorld(), loc.getX() + x,
                                loc.getY() + y2, loc.getZ() + z);
                type.display(0F, 0F, 0F, 1F, 1, loc2, 3);
        }

        for (double y = 5; y >= 0; y -= 0.007) {
                radius = y / 3;
                double x = -(radius * Math.cos(3 * y));
                double z = -(radius * Math.sin(3 * y));

                double y2 = 5 - y;

                Location loc2 = new Location(loc.getWorld(), loc.getX() + x,
                                loc.getY() + y2, loc.getZ() + z);
                type.display(0F, 0F, 0F, 0F, 1, loc2, 2);
        }
}
	

}
