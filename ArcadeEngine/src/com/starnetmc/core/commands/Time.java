package com.starnetmc.core.commands;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.starnetmc.core.util.F;
import com.starnetmc.core.util.ItemFactory;

public class Time implements Listener {

	public static void openTimeGUI(Player player) {

		Inventory gui = Bukkit.createInventory(player, 9, F.underRed
				+ "TIME MANAGEMENT");

		ItemStack night = ItemFactory
				.createItem(
						Material.OBSIDIAN,
						F.boldRed + "NIGHT",
						Arrays.asList("", F.AQUA + "Set the time to:", F.RED
								+ "night"), true);

		ItemStack morning = ItemFactory.createItem(
				Material.REDSTONE_BLOCK,
				F.boldRed + "MORNING",
				Arrays.asList("", F.AQUA + "Set the time to:", F.RED
						+ "morning"), true);

		ItemStack noon = ItemFactory.createItem(Material.GOLD_BLOCK, F.boldRed
				+ "NOON",
				Arrays.asList("", F.AQUA + "Set the time to:", F.RED + "noon"),
				true);

		ItemStack plh = ItemFactory.createItem(Material.STAINED_GLASS_PANE, "",
				null, false);

		gui.setItem(0, plh);
		gui.setItem(1, plh);
		gui.setItem(2, morning);
		gui.setItem(3, plh);
		gui.setItem(4, noon);
		gui.setItem(5, plh);
		gui.setItem(6, night);
		gui.setItem(7, plh);
		gui.setItem(8, plh);

		player.openInventory(gui);

	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onInvClick(InventoryClickEvent e) {

		if (e.getInventory().getName().equalsIgnoreCase(F.underRed+ "TIME MANAGEMENT")) {

			Player player = (Player) e.getWhoClicked();

		e.setCancelled(true);

		if (e.getCurrentItem().getType() == Material.OBSIDIAN) {
			player.getWorld().setTime(15000);
			e.getWhoClicked().closeInventory();
		} else if (e.getCurrentItem().getType() == Material.REDSTONE_BLOCK) {
			player.getWorld().setTime(0);
			e.getWhoClicked().closeInventory();
		} else if (e.getCurrentItem().getType() == Material.GOLD_BLOCK) {
			player.getWorld().setTime(6000);
			e.getWhoClicked().closeInventory();
		} else
			return;

	}
		}
}
