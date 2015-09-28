package com.starnetmc.core.settings;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.starnetmc.core.modules.Settings;
import com.starnetmc.core.util.F;
import com.starnetmc.core.util.ItemFactory;

public class SettingsGUI {

	public static void openSettingsGUI(Player player) {

		Inventory sgui = Bukkit.createInventory(player, 45, F.underRed
				+ "SETTINGS");

		ItemStack chat = new ItemStack(Material.SIGN, 1);
		ItemMeta chatm = chat.getItemMeta();
		chatm.setDisplayName(F.boldGreen + "Chat ENABLED");
		chatm.setLore(Arrays.asList(" ", " ", F.GREEN + "Click to " + F.RED
				+ "DISABLE"));
		chat.setItemMeta(chatm);

		ItemStack chatd = new ItemStack(Material.ANVIL, 1);
		ItemMeta chatdm = chatd.getItemMeta();
		chatdm.setDisplayName(F.boldRed + "Chat DISABLED");
		chatdm.setLore(Arrays.asList(" ", " ", F.RED + "Click to " + F.GREEN
				+ "ENABLE"));
		chatd.setItemMeta(chatdm);

		ItemStack vise = ItemFactory.createItem(
				Material.EYE_OF_ENDER,
				F.boldGreen + "Players ENABLED",
				Arrays.asList(" ", " ", F.GREEN + "Click to " + F.RED
						+ "DISABLE"), true);

		ItemStack visd = ItemFactory.createItem(
				Material.ENDER_PEARL,
				F.boldRed + "Players DISABLED",
				Arrays.asList(" ", " ", F.RED + "Click to " + F.GREEN
						+ "ENABLE"), true);


		ItemStack msge = ItemFactory.createItem(Material.PAPER, F.boldGreen
				+ "Private Messaging ENABLED", Arrays.asList("", "", F.GREEN
				+ "Click to " + F.RED + "DISABLE"), true);
		
		ItemStack msgd = ItemFactory.createItem(Material.PAPER, F.boldRed
						+ "Private Messaging DISABLED", Arrays.asList("", "", F.RED
								+ "Click to " + F.GREEN + "ENABLE"), true);
		
		if (Settings.getPrefs(player).canChat() == true) {

			sgui.setItem(20, chat);

		} else {
			sgui.setItem(20, chatd);
		}

		if (Settings.getPrefs(player).playersVisible() == true) {
			sgui.setItem(22, vise);

		} else {
			sgui.setItem(22, visd);
		}

		if(Settings.getPrefs(player).canRecMsg() == true) {
			sgui.setItem(24, msge);
		}
		else {
			sgui.setItem(24, msgd);
		}
		
		player.openInventory(sgui);

	}

	public static void openSettingsAGUI(Player player) {

		Inventory agui = Bukkit.createInventory(player, 45, F.underRed
				+ "ADMIN SETTINGS");

		ItemStack chat = new ItemStack(Material.SIGN, 1);
		ItemMeta chatm = chat.getItemMeta();
		chatm.setDisplayName(F.boldGreen + "Chat ENABLED");
		chatm.setLore(Arrays.asList(" ", " ", F.GREEN + "Click to " + F.RED
				+ "DISABLE"));
		chat.setItemMeta(chatm);

		ItemStack chatd = new ItemStack(Material.ANVIL, 1);
		ItemMeta chatdm = chatd.getItemMeta();
		chatdm.setDisplayName(F.boldRed + "Chat DISABLED");
		chatdm.setLore(Arrays.asList(" ", " ", F.RED + "Click to " + F.GREEN
				+ "ENABLE"));
		chatd.setItemMeta(chatdm);

		ItemStack vise = ItemFactory.createItem(
				Material.EYE_OF_ENDER,
				F.boldGreen + "Players ENABLED",
				Arrays.asList(" ", " ", F.GREEN + "Click to " + F.RED
						+ "DISABLE"), true);

		ItemStack visd = ItemFactory.createItem(
				Material.ENDER_PEARL,
				F.boldRed + "Players DISABLED",
				Arrays.asList(" ", " ", F.RED + "Click to " + F.GREEN
						+ "ENABLE"), true);

		ItemStack bme = ItemFactory.createItem(
				Material.DIAMOND_PICKAXE,
				F.boldGreen + "Build ENABLED",
				Arrays.asList(" ", " ", F.GREEN + "Click to " + F.RED
						+ "DISABLE"), true);

		ItemStack bmd = ItemFactory.createItem(
				Material.WOOD_PICKAXE,
				F.boldRed + "Build DISABLED",
				Arrays.asList(" ", " ", F.RED + "Click to " + F.GREEN
						+ "ENABLE"), true);

		ItemStack msge = ItemFactory.createItem(Material.PAPER, F.boldGreen
				+ "Private Messaging ENABLED", Arrays.asList("", "", F.GREEN
				+ "Click to " + F.RED + "DISABLE"), true);
		
		ItemStack msgd = ItemFactory.createItem(Material.PAPER, F.boldRed
						+ "Private Messaging DISABLED", Arrays.asList("", "", F.RED
								+ "Click to " + F.GREEN + "ENABLE"), true);

		if (Settings.getPrefs(player).canChat() == true) {

			agui.setItem(19, chat);

		} else {
			agui.setItem(19, chatd);
		}

		if (Settings.getPrefs(player).playersVisible() == true) {
			agui.setItem(21, vise);

		} else {
			agui.setItem(21, visd);
		}

		if (Settings.getPrefs(player).getBuildMode() == true) {

			agui.setItem(23, bme);

		} else {
			agui.setItem(23, bmd);
		}
		
		if(Settings.getPrefs(player).canRecMsg() == true) {
			agui.setItem(25, msge);
		}
		else {
			agui.setItem(25, msgd);
		}

		player.openInventory(agui);

	}

}
