package com.starnetmc.ArcadeEngine.Games.DragonSword;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.entity.EntityPortalEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.starnetmc.ArcadeCore;
import com.starnetmc.ArcadeEngine.Events.CustomPlayerDeathEvent;
import com.starnetmc.ArcadeEngine.Utils.AF;

public class DragonSwordListeners implements Listener {
	
	@EventHandler
	public void swordPickup(PlayerPickupItemEvent e){
		Player p = e.getPlayer();
		
		if (e.getItem().getItemStack().getType() != Material.DIAMOND_SWORD) return;
		if (!(e.getItem().getItemStack().getItemMeta().getDisplayName().equalsIgnoreCase(AF.boldRed + "The Dragon Sword"))) return;
		
		ArcadeCore.getArcadeManager().getKitManager().removeKitItems(p);
		DragonSword.swordWielder = p.getName();
		AF.msgAll(AF.boldYellow + p.getName() + AF.boldPurple + " Has picked up " + AF.boldRed + "The Dragon Sword");
		if (DragonSword.activePortal){
			p.sendMessage(AF.boldYellow + "Dragon: HURRY TO THE PORTAL!");
		}
	}
	
	@EventHandler
	public void deathWhileSword(CustomPlayerDeathEvent e){
		Player p = e.getKilled();
		if (!DragonSword.swordWielder.equalsIgnoreCase(p.getName())) return;
		
		DragonSword.dropDragonSword(e.getKiller().getLocation());
		p.getInventory().remove(Material.DIAMOND_SWORD);
		DragonSword.swordWielder = "No One";
		AF.msgAll(AF.boldRed + "The Dragon Sword has left " + p.getName() + " hands...");
	}
	
	@EventHandler
	public void quitWhileSword(PlayerQuitEvent e){
		Player p = e.getPlayer();
		
		if (!DragonSword.swordWielder.equalsIgnoreCase(p.getName())) return;
		DragonSword.dropDragonSword(p.getLocation());
		p.getInventory().remove(Material.DIAMOND_SWORD);
		DragonSword.swordWielder = "No One";
		AF.msgAll(AF.boldRed + "The Dragon Sword has left " + p.getName() + " hands...");
	}
		
	@EventHandler
	public void stopEntityPortal(EntityPortalEvent e){
		e.setCancelled(true);
	}
	
	@EventHandler
	public void portalize(BlockPhysicsEvent e){
		if (e.getBlock().getType() != Material.PORTAL) return;
		e.setCancelled(true);
	}

}