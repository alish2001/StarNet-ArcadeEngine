package com.starnetmc.core.util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;

public class ULocation {
	
    public static List<Location> getRegionBlocks(Location l, int radius) {
        World w = l.getWorld();
        int xCoord = (int) l.getX();
        int zCoord = (int) l.getZ();
        int YCoord = (int) l.getY();
 
        List<Location> tempList = new ArrayList<Location>();
        for (int x = -radius; x <= radius; x++) {
        for (int z = -radius; z <= radius; z++) {
        for (int y = -radius; y <= radius; y++) {
        tempList.add(new Location(w, xCoord + x, YCoord + y, zCoord + z));
       }
      }
     }
      return tempList;
    }

}