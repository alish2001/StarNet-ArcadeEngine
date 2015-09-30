package com.starnetmc.core.gadgets.GUI;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import com.starnetmc.core.gadgets.Gadget;
import com.starnetmc.core.gadgets.GadgetType;
import com.starnetmc.core.gadgets.Gadgets;
import com.starnetmc.core.util.F;
import com.starnetmc.core.util.USound;
import com.starnetmc.core.util.UPacket;

public class GadgetsUI implements Listener {	
	
	@EventHandler
	public void gadgeteerMain(InventoryClickEvent e) {
		
		if (!ChatColor.stripColor(e.getInventory().getName()).equalsIgnoreCase("Gadgets")) return;
		if (e.getCurrentItem().getType() == null) return;
		if (e.getCurrentItem().getType() == Material.AIR) return;
			e.setCancelled(true);

			Player p = (Player) e.getWhoClicked();
			
			if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Item Gadgets")) {
				GadgetGUI.openItemGadgetGUI(p);
				return;
			}
			
			if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Particles")) {
				GadgetGUI.openParticleGUI(p);
				return;
			}
			
			if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Morphs")) {
				GadgetGUI.openMorphGUI(p);
				return;
			}
			
			if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Mounts")) {
				GadgetGUI.openMountGUI(p);
				return;
			}
			
			USound.PSound(p, Sound.ORB_PICKUP, 7F, 1F);
	}
	
	@EventHandler
	public void gadgeteerItem(InventoryClickEvent e) {
		
		if (!ChatColor.stripColor(e.getInventory().getName()).equalsIgnoreCase("Gadgets | Items")) return;
		if (e.getCurrentItem().getType() == null) return;
		if (e.getCurrentItem().getType() == Material.AIR) return;
			e.setCancelled(true);

			Player p = (Player) e.getWhoClicked();
			
			if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Back >")) {
				GadgetGUI.openGadgetGUI(p);
				USound.PSound(p, Sound.ORB_PICKUP, 7F, 1F);
				return;
			}
			
			for (Gadget g : Gadgets.getAllGadgets()){
			   if (g.getType() == GadgetType.ITEM){
				   
					if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase(ChatColor.stripColor(g.getName()))) {
				   
				   if (Gadgets.hasGadget(p, g)){
					   Gadgets.removeGadgetFromUser(p, g);
					   UPacket.sendActionBarMessage(p, F.boldRed + "UnEquiped " + g.getName() + F.boldRed + "!");
					   USound.PSound(p, Sound.NOTE_PLING, -0.75f, 1);
				   } else if (!Gadgets.hasGadget(p, g)){
					   Gadgets.addGadgetToUser(p, g);
					   UPacket.sendActionBarMessage(p, F.boldGreen + "Equiped " + g.getName() + F.boldGreen + "!");
					   USound.PSound(p, Sound.ORB_PICKUP, 7F, 1F);
				    }
					   
			         p.closeInventory();
				     GadgetGUI.openItemGadgetGUI(p);
				   }
			   }
			}
	}
	
	@EventHandler
	public void gadgeteerParticle(InventoryClickEvent e) {
		
		if (!ChatColor.stripColor(e.getInventory().getName()).equalsIgnoreCase("Gadgets | Particles")) return;
		if (e.getCurrentItem().getType() == null) return;
		if (e.getCurrentItem().getType() == Material.AIR) return;
			e.setCancelled(true);

			Player p = (Player) e.getWhoClicked();
			
			if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Back >")) {
				GadgetGUI.openGadgetGUI(p);
				USound.PSound(p, Sound.ORB_PICKUP, 7F, 1F);
				return;
			}
			
			for (Gadget g : Gadgets.getAllGadgets()){
			   if (g.getType() == GadgetType.PARTICLE){
				   
					if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase(ChatColor.stripColor(g.getName()))) {
				   
				   if (Gadgets.hasGadget(p, g)){
					   Gadgets.removeGadgetFromUser(p, g);
					   UPacket.sendActionBarMessage(p, F.boldRed + "Disabled " + g.getName() + F.boldRed +"!");
					   USound.PSound(p, Sound.NOTE_PLING, -0.75f, 1);
				   } else if (!Gadgets.hasGadget(p, g)){
					   Gadgets.addGadgetToUser(p, g);
					   UPacket.sendActionBarMessage(p, F.boldGreen + "Enabled " + g.getName() + F.boldGreen +"!");
					   USound.PSound(p, Sound.ORB_PICKUP, 7F, 1F);
				   }
				       p.closeInventory();
					   GadgetGUI.openParticleGUI(p);
				   }
			   }
			}
	}
	
	@EventHandler
	public void gadgeteerMorph(InventoryClickEvent e) {
		
		if (!ChatColor.stripColor(e.getInventory().getName()).equalsIgnoreCase("Gadgets | Morphs")) return;
		if (e.getCurrentItem().getType() == null) return;
		if (e.getCurrentItem().getType() == Material.AIR) return;
			e.setCancelled(true);

			Player p = (Player) e.getWhoClicked();
			
			if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Back >")) {
				GadgetGUI.openGadgetGUI(p);
				USound.PSound(p, Sound.ORB_PICKUP, 7F, 1F);
				return;
			}
			
			for (Gadget g : Gadgets.getAllGadgets()){
			   if (g.getType() == GadgetType.MORPH){
				   
					if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase(ChatColor.stripColor(g.getName()))) {
				   
				   if (Gadgets.hasGadget(p, g)){
					   Gadgets.removeGadgetFromUser(p, g);
					   UPacket.sendActionBarMessage(p, F.boldRed + "Morphed out of " + g.getName() + F.boldRed +"!");
					   USound.PSound(p, Sound.NOTE_PLING, -0.75f, 1);
				   } else if (!Gadgets.hasGadget(p, g)){
					   Gadgets.addGadgetToUser(p, g);
					   UPacket.sendActionBarMessage(p, F.boldGreen + "Morphed into " + g.getName() + F.boldGreen +"!");
					   USound.PSound(p, Sound.ORB_PICKUP, 7F, 1F);
				   }
			         p.closeInventory();
				     GadgetGUI.openMorphGUI(p);   
				   }
			   }
			}
	}
	
	@EventHandler
	public void gadgeteerMount(InventoryClickEvent e) {
		
		if (!ChatColor.stripColor(e.getInventory().getName()).equalsIgnoreCase("Gadgets | Mounts")) return;
		if (e.getCurrentItem().getType() == null) return;
		if (e.getCurrentItem().getType() == Material.AIR) return;
			e.setCancelled(true);

			Player p = (Player) e.getWhoClicked();
			
			if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Back >")) {
				GadgetGUI.openGadgetGUI(p);
				USound.PSound(p, Sound.ORB_PICKUP, 7F, 1F);
				return;
			}
			
			for (Gadget g : Gadgets.getAllGadgets()){
			   if (g.getType() == GadgetType.MOUNT){
				   
					if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase(ChatColor.stripColor(g.getName()))) {
				   
				   if (Gadgets.hasGadget(p, g)){
					   Gadgets.removeGadgetFromUser(p, g);
					   UPacket.sendActionBarMessage(p, F.boldRed + "Un-Mounted " + g.getName() + F.boldGreen + "!");
					   USound.PSound(p, Sound.NOTE_PLING, -0.75f, 1);
				   } else if (!Gadgets.hasGadget(p, g)){
					   Gadgets.addGadgetToUser(p, g);
					   UPacket.sendActionBarMessage(p, F.boldGreen + "Mounted " + g.getName() + F.boldGreen + "!");
					   USound.PSound(p, Sound.ORB_PICKUP, 7F, 1F);
				   }
				   
			       p.closeInventory();
				   GadgetGUI.openMountGUI(p);
				 }
			   }
			}
	}

}