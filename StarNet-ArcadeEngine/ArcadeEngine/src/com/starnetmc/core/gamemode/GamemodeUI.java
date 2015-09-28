package com.starnetmc.core.gamemode;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.starnetmc.core.util.F;
import com.starnetmc.core.util.ItemFactory;

public class GamemodeUI implements Listener {

	public static void openGMGUI(Player player) {

		Inventory gui = Bukkit.createInventory(player, 9, F.underRed
				+ "Gamemode Management");

		ItemStack surv = ItemFactory.createItem(
				Material.WOOD_SWORD,
				F.boldRed + "SURVIVAL",
				Arrays.asList("", F.AQUA + "Set gamemode to: ", F.RED
						+ "survival"), true);
		ItemStack crea = ItemFactory.createItem(
				Material.DIAMOND_PICKAXE,
				F.boldRed + "CREATIVE",
				Arrays.asList("", F.AQUA + "Set gamemode to: ", F.RED
						+ "creative"), true);
		ItemStack adv = ItemFactory.createItem(
				Material.REDSTONE,
				F.boldRed + "ADVENTURE",
				Arrays.asList("", F.AQUA + "Set gamemode to: ", F.RED
						+ "adventure"), true);
		ItemStack spec = ItemFactory.createItem(
				Material.GLASS,
				F.boldRed + "SPECTATOR",
				Arrays.asList("", F.AQUA + "Set gamemode to: ", F.RED
						+ "spectator"), true);

		ItemStack plh = ItemFactory.createItem(Material.STAINED_GLASS_PANE, "",
				null, false);

		gui.setItem(0, plh);
		gui.setItem(1, surv);
		gui.setItem(2, plh);
		gui.setItem(3, crea);
		gui.setItem(4, plh);
		gui.setItem(5, adv);
		gui.setItem(6, plh);
		gui.setItem(7, spec);
		gui.setItem(8, plh);

		player.openInventory(gui);

	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onInvClick(InventoryClickEvent e) {

		if (e.getInventory().getName().equalsIgnoreCase(F.underRed + "Gamemode Management")) {
			

		e.setCancelled(true);
		
		Player player = (Player) e.getWhoClicked();
		

		player.playSound(player.getLocation(), Sound.ORB_PICKUP, 7F, 1F);
		
		if (e.getCurrentItem().getType() == Material.WOOD_SWORD) {

			player.setGameMode(GameMode.SURVIVAL);
			player.closeInventory();
			player.sendMessage(F.info("Gamemode",
					"Your gamemode has been changed."));

		}
		else if (e.getCurrentItem().getType() == Material.DIAMOND_PICKAXE) {

			player.setGameMode(GameMode.CREATIVE);
			player.closeInventory();
			player.sendMessage(F.info("Gamemode",
					"Your gamemode has been changed."));

		}
		else if (e.getCurrentItem().getType() == Material.REDSTONE) {

			player.setGameMode(GameMode.ADVENTURE);
			player.closeInventory();
			player.sendMessage(F.info("Gamemode",
					"Your gamemode has been changed."));

		}
		else if (e.getCurrentItem().getType() == Material.GLASS) {

			player.setGameMode(GameMode.SPECTATOR);
			player.closeInventory();
			player.sendMessage(F.info("Gamemode",
					"Your gamemode has been changed."));

		}
		else {
			return;
		}

	}
	}
	

}
