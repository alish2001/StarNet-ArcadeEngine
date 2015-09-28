package com.starnetmc.ArcadeEngine.Utils;

import org.bukkit.util.Vector;

public class UVector {
	
	public static Vector generateRandomVector(float xBase, float yBase, float zBase){
				float x = (float) -1.0 + (float) (Math.random() * ((1.0 - -1.0) + 1));
				float y = (float) -1.5 + (float) (Math.random() * ((1.25 - -1.25) + 1));
				float z = (float) -1.0 + (float) (Math.random() * ((1.0 - -1.0) + 1));
				
				return new Vector(x, y, z);
	}
}