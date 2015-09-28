package com.starnetmc.core.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class Damage implements Listener {

	@EventHandler(priority = EventPriority.MONITOR)
	public void onDamageBattle(EntityDamageByEntityEvent e) {

		e.setDamage(0.0D);
		e.setCancelled(true);

		if (e.getCause() == DamageCause.VOID) {

			Entity en = e.getEntity();
			en.teleport(en.getWorld().getSpawnLocation());

		}

	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onBurn(EntityCombustEvent e) {
		e.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onDamageOther(EntityDamageEvent e) {

		e.setDamage(0.0D);
		e.setCancelled(true);

		if (e.getCause() == DamageCause.VOID) {

			Entity en = e.getEntity();
			en.teleport(en.getWorld().getSpawnLocation());

		}
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onFoodLevelChange(FoodLevelChangeEvent e) {

		e.setFoodLevel(20);
		e.setCancelled(true);

	}

}
