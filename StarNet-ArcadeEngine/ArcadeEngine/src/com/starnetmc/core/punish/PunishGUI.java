package com.starnetmc.core.punish;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.starnetmc.core.util.F;
import com.starnetmc.core.util.ItemFactory;
import com.starnetmc.core.util.StarMap;

public class PunishGUI {

	@SuppressWarnings("deprecation")
	public static void showOtherMenu(Player opener, StarMap<Player, Punishment> punish) {

		Inventory main = Bukkit.createInventory(opener, 54, F.underRed
				+ "Punishments");

		List<String> chat1 = Arrays.asList(" ", ChatColor.YELLOW + "Examples:",
				ChatColor.GRAY + "- All after Warnings", ChatColor.GRAY
						+ "- HEY I JUST DID COOL STUFF LOL", ChatColor.GRAY
						+ "- Spamming ", ChatColor.GRAY
						+ "- NooOoOOoooooo(longer than this)!", ChatColor.GRAY
						+ "- klj;sadnmfakssadgsag");

		List<String> chat2 = Arrays
				.asList(" ",
						ChatColor.YELLOW + "Examples:",
						ChatColor.GRAY + "- I'M GOING TO MURDER YOUR FAMILY",
						ChatColor.GRAY + "- DDOS Threats toward Players",
						ChatColor.GRAY
								+ "(randy: randy2 You're getting DDOSed have fun!)",
						ChatColor.GRAY
								+ "- Excessive Language: FUCK YOU BITCH (After Warning)",
						ChatColor.GRAY + "- Message Advertising (/msg randy ",
						ChatColor.GRAY + "Hey Come join badcraft.lol)");

		List<String> chat3 = Arrays
				.asList(" ",
						ChatColor.YELLOW + "Examples:",
						ChatColor.GRAY
								+ "- Advertising (randy: COME JOIN STUPIDROCK.DERP)",
						ChatColor.GRAY + "- DDOS Threats to the server",
						ChatColor.GRAY
								+ "(randy_: I'm going to DDOS this if I don't get admin!)");

		List<String> exploit = Arrays.asList(" ", ChatColor.YELLOW
				+ "Examples:", ChatColor.GRAY + "- All after Warning",
				ChatColor.GRAY + "- Bob Map Exploits", ChatColor.GRAY
						+ "- Randy123 TeamKills on Purpose", ChatColor.GRAY
						+ "- Bob knows something is a bug and abuses it");

		List<String> MOD = Arrays.asList(" ", ChatColor.YELLOW + "Examples:",
				ChatColor.GRAY + "- Damage Indicators", ChatColor.GRAY
						+ "- XRay", ChatColor.GRAY
						+ "- Entity Displaying MiniMaps");

		List<String> Hack = Arrays.asList(" ", ChatColor.YELLOW + "Examples:",
				ChatColor.GRAY + "- Kill Aura", ChatColor.GRAY
						+ "- Fly Hacking", ChatColor.GRAY + "- Spider Climb",
				ChatColor.GRAY + "- Aimbot", ChatColor.GRAY + "- Regen Hacks");

		ItemStack chatd1 = ItemFactory.createItem(Material.BOOK,
				ChatColor.YELLOW + "" + ChatColor.BOLD + "Severity 1 Chat",
				chat1, true);
		ItemStack chatd2 = ItemFactory.createItem(Material.BOOK, ChatColor.GOLD
				+ "" + ChatColor.BOLD + "Severity 2 Chat", chat2, true);
		ItemStack chatd3 = ItemFactory.createItem(Material.BOOK, ChatColor.RED
				+ "" + ChatColor.BOLD + "Severity 3 Chat", chat3, true);

		ItemStack exploitd = ItemFactory.createItem(Material.BOOK,
				ChatColor.YELLOW + "" + ChatColor.BOLD + "Exploitation",
				exploit, true);
		ItemStack MODd = ItemFactory.createItem(Material.BOOK, ChatColor.GOLD
				+ "" + ChatColor.BOLD + "Unapproved Client MOD's", MOD, true);
		ItemStack Hackd = ItemFactory.createItem(Material.BOOK, ChatColor.RED
				+ "" + ChatColor.BOLD + "Hacked Client", Hack, true);

		ItemStack head = ItemFactory.createItem(Material.SKULL_ITEM, punish
				.get(opener).getOffender(), Arrays.asList(ChatColor.WHITE
				+ punish.get(opener).getOffense()), true);

		ItemStack exploiticon = ItemFactory.createItem(Material.TNT,
				ChatColor.YELLOW + "" + ChatColor.BOLD + "Exploitation", null,
				false);
		ItemStack MODicon = ItemFactory.createItem(Material.MAGMA_CREAM,
				ChatColor.GOLD + "" + ChatColor.BOLD + "Unapproved Client MOD's", null, false);
		ItemStack Hackicon = ItemFactory.createItem(Material.REDSTONE,
				ChatColor.RED + "" + ChatColor.BOLD + "Hacked Client", null, false);

		ItemStack chat1icon = ItemFactory.createItem(Material.PAPER,
				ChatColor.YELLOW + "" + ChatColor.BOLD + "Severity 1 Chat",
				chat1, true);
		ItemStack chat2icon = ItemFactory.createItem(Material.ENCHANTED_BOOK,
				ChatColor.GOLD + "" + ChatColor.BOLD + "Severity 2 Chat",
				chat2, true);
		ItemStack chat3icon = ItemFactory.createItem(Material.BOOKSHELF,
				ChatColor.RED + "" + ChatColor.BOLD + "Severity 3 Chat", chat3,
				true);

		ItemStack chatsev1 = new ItemStack(Material.STAINED_GLASS_PANE, 1,
				DyeColor.LIME.getData());
		ItemMeta chatsev1m = chatsev1.getItemMeta();
		chatsev1m.setDisplayName(ChatColor.YELLOW + "Chat " + ChatColor.AQUA
				+ "-" + ChatColor.YELLOW + " Severity 1");
		List<String> c1lore = Arrays.asList(" ", ChatColor.GRAY
				+ "Punishment Time: " + ChatColor.YELLOW + "30 Minutes", " ",
				ChatColor.YELLOW + "> Click To confirm Punishment");
		chatsev1m.setLore(c1lore);
		chatsev1.setItemMeta(chatsev1m);

		ItemStack chatsev2 = new ItemStack(Material.STAINED_GLASS_PANE, 1,
				DyeColor.YELLOW.getData());
		ItemMeta chatsev2m = chatsev2.getItemMeta();
		chatsev2m.setDisplayName(ChatColor.GOLD + "Chat " + ChatColor.AQUA
				+ "-" + ChatColor.GOLD + " Severity 2");
		List<String> c2lore = Arrays.asList(" ", ChatColor.GRAY
				+ "Punishment Time: " + ChatColor.YELLOW + "2 Hours", " ",
				ChatColor.YELLOW + "> Click To confirm Punishment");
		chatsev2m.setLore(c2lore);
		chatsev2.setItemMeta(chatsev2m);

		ItemStack chatsev3 = new ItemStack(Material.STAINED_GLASS_PANE, 1,
				DyeColor.RED.getData());
		ItemMeta chatsev3m = chatsev3.getItemMeta();
		chatsev3m.setDisplayName(ChatColor.RED + "Chat " + ChatColor.AQUA + "-"
				+ ChatColor.RED + " Severity 3");
		List<String> c3lore = Arrays.asList(" ", ChatColor.GRAY
				+ "Punishment Time: " + ChatColor.YELLOW + "Permanent", " ",
				ChatColor.YELLOW + "> Click To confirm Punishment");
		chatsev3m.setLore(c3lore);
		chatsev3.setItemMeta(chatsev3m);

		ItemStack exploitsev = new ItemStack(Material.STAINED_GLASS_PANE, 1,
				DyeColor.LIME.getData());
		ItemMeta exploitsevm = exploitsev.getItemMeta();
		exploitsevm.setDisplayName(ChatColor.GOLD + "Exploitation "
				+ ChatColor.AQUA + "-" + ChatColor.YELLOW + " Exploit Sev.");
		List<String> e1lore = Arrays.asList(" ", ChatColor.GRAY
				+ "Punishment Time: " + ChatColor.YELLOW + "12 Hours", " ",
				ChatColor.YELLOW + "> Click To confirm Punishment");
		exploitsevm.setLore(e1lore);
		exploitsev.setItemMeta(exploitsevm);

		ItemStack Modsev = new ItemStack(Material.STAINED_GLASS_PANE, 1,
				DyeColor.YELLOW.getData());
		ItemMeta Modsevm = Modsev.getItemMeta();
		Modsevm.setDisplayName(ChatColor.GOLD + "Unapproved MODs "
				+ ChatColor.AQUA + "-" + ChatColor.RED + " Unapproved MOD Sev.");
		List<String> e3lore = Arrays.asList(" ", ChatColor.GRAY
				+ "Punishment Time: " + ChatColor.YELLOW + "12 Hours", " ",
				ChatColor.YELLOW + "> Click To confirm Punishment");
		Modsevm.setLore(e3lore);
		Modsev.setItemMeta(Modsevm);

		ItemStack hacksev = new ItemStack(Material.STAINED_GLASS_PANE, 1,
				DyeColor.RED.getData());
		ItemMeta hacksevm = hacksev.getItemMeta();
		hacksevm.setDisplayName(ChatColor.RED + "Hacked Client "
				+ ChatColor.AQUA + "-" + ChatColor.DARK_RED + " Hacking Sev.");
		List<String> h1lore = Arrays.asList(" ", ChatColor.GRAY
				+ "Punishment Time: " + ChatColor.YELLOW + "Permanent", " ",
				ChatColor.YELLOW + "> Click To confirm Punishment");
		hacksevm.setLore(h1lore);
		hacksev.setItemMeta(hacksevm);

		ItemStack warn = ItemFactory.createItem(Material.EMPTY_MAP,
				ChatColor.YELLOW + "Warn", Arrays.asList(" ", ChatColor.GRAY
						+ "- Warning for punishments that need it.", " ",
						ChatColor.YELLOW + "> Click to Warm"), true);
		ItemStack permban = ItemFactory.createItem(
				Material.BARRIER,
				ChatColor.RED + "Permanent Ban",
				Arrays.asList(" ", ChatColor.GRAY + "Punishment Time: "
						+ ChatColor.YELLOW + "Permanent", " ", ChatColor.YELLOW
						+ "Examples: ", ChatColor.GRAY
						+ "- Inappropriate Skins", ChatColor.GRAY
						+ "- Inappropriate Names", ChatColor.GRAY
						+ "- Other Reasons", " ", ChatColor.YELLOW
						+ "> Click To confirm Punishment"), true);
		ItemStack permmute = ItemFactory.createItem(Material.BOOK_AND_QUILL,
				ChatColor.RED + "Permanent Mute", Arrays.asList(" ",
						ChatColor.GRAY + "Punishment Time: " + ChatColor.YELLOW
								+ "Permanent", " ", ChatColor.YELLOW
								+ "Examples: ", ChatColor.GRAY
								+ "- Other Reasons", " ", ChatColor.YELLOW
								+ "> Click To confirm Punishment"), true);

		ItemStack pane = new ItemStack(Material.STAINED_GLASS_PANE, 1,
				DyeColor.BLACK.getData());
		ItemMeta panem = pane.getItemMeta();
		panem.setDisplayName("");
		pane.setItemMeta(panem);

		main.setItem(3, pane);
		main.setItem(4, head);
		main.setItem(5, pane);
		main.setItem(9, pane);
		main.setItem(10, pane);
		main.setItem(11, pane);
		main.setItem(12, pane);
		main.setItem(13, pane);
		main.setItem(14, pane);
		main.setItem(15, pane);
		main.setItem(16, pane);
		main.setItem(17, pane);
		main.setItem(18, chatd1);
		main.setItem(19, chatd2);
		main.setItem(20, chatd3);
		main.setItem(21, pane);
		main.setItem(22, warn);
		main.setItem(23, pane);
		main.setItem(24, Hackd);
		main.setItem(25, MODd);
		main.setItem(26, exploitd);
		main.setItem(27, chatsev1);
		main.setItem(28, chatsev2);
		main.setItem(29, chatsev3);
		main.setItem(30, pane);
		main.setItem(31, permban);
		main.setItem(32, pane);
		main.setItem(33, hacksev);
		main.setItem(34, Modsev);
		main.setItem(35, exploitsev);
		main.setItem(36, chat1icon);
		main.setItem(37, chat2icon);
		main.setItem(38, chat3icon);
		main.setItem(39, pane);
		main.setItem(40, permmute);
		main.setItem(41, pane);
		main.setItem(42, Hackicon);
		main.setItem(43, MODicon);
		main.setItem(44, exploiticon);
		main.setItem(45, pane);
		main.setItem(46, pane);
		main.setItem(47, pane);
		main.setItem(48, pane);
		main.setItem(49, pane);
		main.setItem(50, pane);
		main.setItem(51, pane);
		main.setItem(52, pane);
		main.setItem(53, pane);

		opener.openInventory(main);
	}

	@SuppressWarnings("deprecation")
	public static void showHelperMenu(Player opener,
			StarMap<Player, Punishment> punish) {

		Inventory main = Bukkit.createInventory(opener, 54, F.underRed
				+ "Punishments");

		List<String> chat1 = Arrays.asList(" ", ChatColor.YELLOW + "Examples:",
				ChatColor.GRAY + "- All after Warnings", ChatColor.GRAY
						+ "- HEY I JUST DID COOL STUFF LOL", ChatColor.GRAY
						+ "- Spamming ", ChatColor.GRAY
						+ "- NooOoOOoooooo(longer than this)!", ChatColor.GRAY
						+ "- klj;sadnmfakssadgsag");

		List<String> chat2 = Arrays
				.asList(" ",
						ChatColor.YELLOW + "Examples:",
						ChatColor.GRAY + "- I'M GOING TO MURDER YOUR FAMILY",
						ChatColor.GRAY + "- DDOS Threats toward Players",
						ChatColor.GRAY
								+ "(randy: randy2 You're getting DDOSed have fun!)",
						ChatColor.GRAY
								+ "- Excessive Language: FUCK YOU BITCH (After Warning)",
						ChatColor.GRAY + "- Message Advertising (/msg randy ",
						ChatColor.GRAY + "Hey Come join badcraft.lol)");

		List<String> chat3 = Arrays
				.asList(" ",
						ChatColor.YELLOW + "Examples:",
						ChatColor.GRAY
								+ "- Advertising (randy: COME JOIN STUPIDROCK.DERP)",
						ChatColor.GRAY + "- DDOS Threats to the server",
						ChatColor.GRAY
								+ "(randy_: I'm going to DDOS this if I don't get admin!)");

		List<String> exploit = Arrays.asList(" ", ChatColor.YELLOW
				+ "Examples:", ChatColor.GRAY + "- All after Warning",
				ChatColor.GRAY + "- Bob Map Exploits", ChatColor.GRAY
						+ "- Randy123 TeamKills on Purpose", ChatColor.GRAY
						+ "- Bob knows something is a bug and abuses it");

		List<String> MOD = Arrays.asList(" ", ChatColor.YELLOW + "Examples:",
				ChatColor.GRAY + "- Damage Indicators", ChatColor.GRAY
						+ "- XRay", ChatColor.GRAY
						+ "- Entity Displaying MiniMaps");

		List<String> Hack = Arrays.asList(" ", ChatColor.YELLOW + "Examples:",
				ChatColor.GRAY + "- Kill Aura", ChatColor.GRAY
						+ "- Fly Hacking", ChatColor.GRAY + "- Spider Climb",
				ChatColor.GRAY + "- Aimbot", ChatColor.GRAY + "- Regen Hacks");

		ItemStack chatd1 = ItemFactory.createItem(Material.BOOK,
				ChatColor.YELLOW + "" + ChatColor.BOLD + "Severity 1 Chat",
				chat1, true);
		ItemStack chatd2 = ItemFactory.createItem(Material.BOOK, ChatColor.GOLD
				+ "" + ChatColor.BOLD + "Severity 2 Chat", chat2, true);
		ItemStack chatd3 = ItemFactory.createItem(Material.BOOK, ChatColor.RED
				+ "" + ChatColor.BOLD + "Severity 3 Chat", chat3, true);

		ItemStack exploitd = ItemFactory.createItem(Material.BOOK,
				ChatColor.YELLOW + "" + ChatColor.BOLD + "Exploitation",
				exploit, true);
		ItemStack MODd = ItemFactory.createItem(Material.BOOK,
				ChatColor.GOLD + "" + ChatColor.BOLD + "Unapproved Client MOD's", MOD, true);
		ItemStack Hackd = ItemFactory.createItem(Material.BOOK,
				ChatColor.RED + "" + ChatColor.BOLD + "Hacked Client", Hack, true);

		
		ItemStack head = ItemFactory.createItem(Material.SKULL_ITEM, punish
				.get(opener).getOffender(), Arrays.asList(ChatColor.WHITE
				+ punish.get(opener).getOffense()), true);

		ItemStack exploiticon = ItemFactory.createItem(Material.TNT,
				ChatColor.YELLOW + "" + ChatColor.BOLD + "Exploitation", null,
				false);
		ItemStack MODicon = ItemFactory.createItem(Material.MAGMA_CREAM,
				ChatColor.GOLD + "" + ChatColor.BOLD + "Unapproved Client MOD's", null, false);
		ItemStack Hackicon = ItemFactory.createItem(Material.REDSTONE,
				ChatColor.RED + "" + ChatColor.BOLD + "Hacked Client", null, false);

		ItemStack chat1icon = ItemFactory.createItem(Material.PAPER,
				ChatColor.YELLOW + "" + ChatColor.BOLD + "Severity 1 Chat",
				chat1, true);
		ItemStack chat2icon = ItemFactory.createItem(Material.ENCHANTED_BOOK,
				ChatColor.GOLD + "" + ChatColor.BOLD + "Severity 2 Chat",
				chat2, true);
		ItemStack chat3icon = ItemFactory.createItem(Material.BOOKSHELF,
				ChatColor.RED + "" + ChatColor.BOLD + "Severity 3 Chat", chat3,
				true);

		ItemStack chatsev1 = new ItemStack(Material.STAINED_GLASS_PANE, 1,
				DyeColor.LIME.getData());
		ItemMeta chatsev1m = chatsev1.getItemMeta();
		chatsev1m.setDisplayName(ChatColor.YELLOW + "Chat " + ChatColor.AQUA
				+ "-" + ChatColor.YELLOW + " Severity 1");
		List<String> c1lore = Arrays.asList(" ", ChatColor.GRAY
				+ "Punishment Time: " + ChatColor.YELLOW + "30 Minutes", " ",
				ChatColor.YELLOW + "> Click To confirm Punishment");
		chatsev1m.setLore(c1lore);
		chatsev1.setItemMeta(chatsev1m);

		ItemStack chatsev2 = new ItemStack(Material.STAINED_GLASS_PANE, 1,
				DyeColor.YELLOW.getData());
		ItemMeta chatsev2m = chatsev2.getItemMeta();
		chatsev2m.setDisplayName(ChatColor.GOLD + "Chat " + ChatColor.AQUA
				+ "-" + ChatColor.GOLD + " Severity 2");
		List<String> c2lore = Arrays.asList(" ", ChatColor.GRAY
				+ "Punishment Time: " + ChatColor.YELLOW + "2 Hours", " ",
				ChatColor.YELLOW + "> Click To confirm Punishment");
		chatsev2m.setLore(c2lore);
		chatsev2.setItemMeta(chatsev2m);

		ItemStack chatsev3 = new ItemStack(Material.BARRIER, 1);
		ItemMeta chatsev3m = chatsev3.getItemMeta();
		chatsev3m.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD
				+ "Severity Locked");
		List<String> c3lore = Arrays.asList(" ", ChatColor.RED
				+ "This Severity is locked.", ChatColor.GOLD + "MOD "
				+ ChatColor.RED + "Permission Rank or higher needed.");
		chatsev3m.setLore(c3lore);
		chatsev3.setItemMeta(chatsev3m);

		ItemStack exploitsev = new ItemStack(Material.STAINED_GLASS_PANE, 1,
				DyeColor.LIME.getData());
		ItemMeta exploitsevm = exploitsev.getItemMeta();
		exploitsevm.setDisplayName(ChatColor.GOLD + "Exploitation "
				+ ChatColor.AQUA + "-" + ChatColor.YELLOW + " Exploit Sev.");
		List<String> e1lore = Arrays.asList(" ", ChatColor.GRAY
				+ "Punishment Time: " + ChatColor.YELLOW + "12 Hours", " ",
				ChatColor.YELLOW + "> Click To confirm Punishment");
		exploitsevm.setLore(e1lore);
		exploitsev.setItemMeta(exploitsevm);

		ItemStack Modsev = new ItemStack(Material.STAINED_GLASS_PANE, 1,
				DyeColor.YELLOW.getData());
		ItemMeta Modsevm = Modsev.getItemMeta();
		Modsevm.setDisplayName(ChatColor.GOLD + "Unapproved MODs "
				+ ChatColor.AQUA + "-" + ChatColor.RED + " Unapproved MOD Sev.");
		List<String> e3lore = Arrays.asList(" ", ChatColor.GRAY
				+ "Punishment Time: " + ChatColor.YELLOW + "12 Hours", " ",
				ChatColor.YELLOW + "> Click To confirm Punishment");
		Modsevm.setLore(e3lore);
		Modsev.setItemMeta(Modsevm);

		ItemStack hacksev = new ItemStack(Material.STAINED_GLASS_PANE, 1);
		ItemMeta hacksevm = hacksev.getItemMeta();
		hacksevm.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD
				+ "Severity Locked");
		List<String> h1lore = Arrays.asList(" ", ChatColor.RED
				+ "This Severity is locked.", ChatColor.GOLD + "MOD "
				+ ChatColor.RED + "Permission Rank or higher needed.");
		hacksevm.setLore(h1lore);
		hacksev.setItemMeta(hacksevm);

		ItemStack warn = ItemFactory.createItem(Material.EMPTY_MAP,
				ChatColor.YELLOW + "Warn", Arrays.asList(" ", ChatColor.GRAY
						+ "- Warning for punishments that need it.",
						ChatColor.YELLOW + "> Click to Warn"), true);
		ItemStack permban = ItemFactory.createItem(Material.BARRIER,
				ChatColor.RED + "" + ChatColor.BOLD + "Severity Locked", Arrays
						.asList(" ",
								ChatColor.RED + "This Severity is locked.",
								ChatColor.GOLD + "MOD " + ChatColor.RED
										+ "Permission Rank or higher needed."),
				true);
		ItemStack permmute = ItemFactory.createItem(Material.BARRIER,
				ChatColor.RED + "" + ChatColor.BOLD + "Severity Locked", Arrays
						.asList(" ",
								ChatColor.RED + "This Severity is locked.",
								ChatColor.GOLD + "MOD " + ChatColor.RED
										+ "Permission Rank or higher needed."),
				true);

		ItemStack pane = new ItemStack(Material.STAINED_GLASS_PANE, 1,
				DyeColor.BLACK.getData());
		ItemMeta panem = pane.getItemMeta();
		panem.setDisplayName("");
		pane.setItemMeta(panem);

		main.setItem(3, pane);
		main.setItem(4, head);
		main.setItem(5, pane);
		main.setItem(9, pane);
		main.setItem(10, pane);
		main.setItem(11, pane);
		main.setItem(12, pane);
		main.setItem(13, pane);
		main.setItem(14, pane);
		main.setItem(15, pane);
		main.setItem(16, pane);
		main.setItem(17, pane);
		main.setItem(18, chatd1);
		main.setItem(19, chatd2);
		main.setItem(20, chatd3);
		main.setItem(21, pane);
		main.setItem(22, warn);
		main.setItem(23, pane);
		main.setItem(24, Hackd);
		main.setItem(25, MODd);
		main.setItem(26, exploitd);
		main.setItem(27, chatsev1);
		main.setItem(28, chatsev2);
		main.setItem(29, chatsev3);
		main.setItem(30, pane);
		main.setItem(31, permban);
		main.setItem(32, pane);
		main.setItem(33, hacksev);
		main.setItem(34, Modsev);
		main.setItem(35, exploitsev);
		main.setItem(36, chat1icon);
		main.setItem(37, chat2icon);
		main.setItem(38, chat3icon);
		main.setItem(39, pane);
		main.setItem(40, permmute);
		main.setItem(41, pane);
		main.setItem(42, Hackicon);
		main.setItem(43, MODicon);
		main.setItem(44, exploiticon);
		main.setItem(45, pane);
		main.setItem(46, pane);
		main.setItem(47, pane);
		main.setItem(48, pane);
		main.setItem(49, pane);
		main.setItem(50, pane);
		main.setItem(51, pane);
		main.setItem(52, pane);
		main.setItem(53, pane);

		opener.openInventory(main);
	}

}