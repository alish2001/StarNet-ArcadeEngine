package com.starnetmc.ArcadeEngine.Utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class UMethods {

	//Misc Class used for really cool/useful methods.
	
	/** 
	 * Send fireballs from a player eye level.
	 * @param player
	 * @param amt number of fireballs to shoot (evenly spaced)
	 */
	public static void sendFireballsFromPlayer(Player player,int amt){
	  Location loc=player.getEyeLocation();
	  final double tau=2 * Math.PI;
	  double arc=tau / amt;
	  for (double a=0; a < tau; a+=arc) {
	    Vector dir=new Vector(Math.cos(a),0,Math.sin(a));
	    Location spawn=loc.toVector().add(dir.multiply(2)).toLocation(loc.getWorld(),0.0F,0.0F);
	    Fireball fball=player.getWorld().spawn(spawn,Fireball.class);
	    fball.setShooter(player);
	    fball.setDirection(dir.multiply(10));
	  }
	}
	
	public static void createBeacon(Location location) {
	    int x = location.getBlockX();
	    int y = location.getBlockY() - 30;
	    int z = location.getBlockZ();
	 
	    World world = location.getWorld();
	 
	    world.getBlockAt(x, y, z).setType(Material.BEACON);
	    for (int i = 0; i <= 29; ++i) world.getBlockAt(x, (y + 1) + i, z).setType(Material.GLASS);
	    for (int xPoint = x-1; xPoint <= x+1 ; xPoint++) { 
	        for (int zPoint = z-1 ; zPoint <= z+1; zPoint++) {            
	            world.getBlockAt(xPoint, y-1, zPoint).setType(Material.IRON_BLOCK);
	        }
	    } 
	}
}
