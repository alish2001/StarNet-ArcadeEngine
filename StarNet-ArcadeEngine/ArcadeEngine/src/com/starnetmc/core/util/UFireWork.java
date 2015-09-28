package com.starnetmc.core.util;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

public class UFireWork {

	public static void finishingFW(Location l){
			createFireWork(l, true, true, Type.BALL_LARGE, Color.RED, Color.AQUA, 1);
	}
	
	public static void PointFW(Location sp, boolean trail, boolean flicker, Type t, Color c, Color fadec){
		createFireWork(sp, trail, flicker, t, c, fadec, 0);
	}
	
	public static Firework createFireWork(Location sp, boolean trail, boolean flicker, Type t, Color c, Color fadec, int power){
	       Firework f = (Firework) sp.getWorld().spawn(sp, Firework.class);
	       FireworkMeta fm = f.getFireworkMeta();
	       fm.addEffect(FireworkEffect.builder()
	       		.flicker(flicker)
	       		.trail(trail)
	       		.with(t)
	       		.withColor(c)
	       		.withFade(fadec)
	       		.build());
	       fm.setPower(power);
	       f.setFireworkMeta(fm);
		return f;
	}
}
