package com.starnetmc.core.util;

import org.bukkit.ChatColor;

public class F {

	public static String AQUA = ChatColor.AQUA + "";
	public static String BLACK = ChatColor.BLACK + "";
	public static String BLUE = ChatColor.BLUE + "";
	public static String DARK_AQUA = ChatColor.DARK_AQUA + "";
	public static String DARK_BLUE = ChatColor.DARK_BLUE + "";
	public static String DARK_GRAY = ChatColor.DARK_GRAY + "";
	public static String DARK_GREEN = ChatColor.DARK_GREEN + "";
	public static String DARK_PURPLE = ChatColor.DARK_PURPLE + "";
	public static String DARK_RED = ChatColor.DARK_RED + "";
	public static String GOLD = ChatColor.GOLD + "";
	public static String GRAY = ChatColor.GRAY + "";
	public static String GREEN = ChatColor.GREEN + "";
	public static String LIGHT_PURPLE = ChatColor.LIGHT_PURPLE + "";
	public static String RED = ChatColor.RED + "";
	public static String WHITE = ChatColor.WHITE + "";
	public static String YELLOW = ChatColor.YELLOW + "";
	public static String BOLD = ChatColor.BOLD + "";
	public static String STRIKE = ChatColor.STRIKETHROUGH + "";
	public static String UNDER = ChatColor.UNDERLINE + "";
	public static String RESET = ChatColor.RESET + "";

	public static String boldAqua = AQUA + BOLD + "";
	public static String boldBlack = BLACK + BOLD + "";
	public static String boldBlue = BLUE + BOLD + "";
	public static String boldDA = DARK_AQUA + BOLD + "";
	public static String boldDB = DARK_BLUE + BOLD + "";
	public static String boldDG = DARK_GRAY + BOLD + "";
	public static String boldDGR = DARK_GREEN + BOLD + "";
	public static String boldDP = DARK_PURPLE + BOLD + "";
	public static String boldDR = DARK_RED + BOLD + "";
	public static String boldGold = GOLD + BOLD + "";
	public static String boldGray = GRAY + BOLD + "";
	public static String boldGreen = GREEN + BOLD + "";
	public static String boldPurple = LIGHT_PURPLE + BOLD + "";
	public static String boldRed = RED + BOLD + "";
	public static String boldWhite = WHITE + BOLD + "";
	public static String boldYellow = YELLOW + BOLD + "";

	public static String strikeAqua = AQUA + STRIKE + "";
	public static String strikeBlack = BLACK + STRIKE + "";
	public static String strikeBlue = BLUE + STRIKE + "";
	public static String strikeDA = DARK_AQUA + STRIKE + "";
	public static String strikeDB = DARK_BLUE + STRIKE + "";
	public static String strikeDG = DARK_GRAY + STRIKE + "";
	public static String strikeDGR = DARK_GREEN + STRIKE + "";
	public static String strikeDP = DARK_PURPLE + STRIKE + "";
	public static String strikeDR = DARK_RED + STRIKE + "";
	public static String strikeGold = GOLD + STRIKE + "";
	public static String strikeGray = GRAY + STRIKE + "";
	public static String strikeGreen = GREEN + STRIKE + "";
	public static String strikePurple = LIGHT_PURPLE + STRIKE + "";
	public static String strikeRed = RED + STRIKE + "";
	public static String strikeWhite = WHITE + STRIKE + "";
	public static String strikeYellow = YELLOW + STRIKE + "";

	public static String underAqua = AQUA + UNDER + "";
	public static String underBlack = BLACK + UNDER + "";
	public static String underBlue = BLUE + UNDER + "";
	public static String underDA = DARK_AQUA + UNDER + "";
	public static String underDB = DARK_BLUE + UNDER + "";
	public static String underDG = DARK_GRAY + UNDER + "";
	public static String underDGR = DARK_GREEN + UNDER + "";
	public static String underDP = DARK_PURPLE + UNDER + "";
	public static String underDR = DARK_RED + UNDER + "";
	public static String underGold = GOLD + UNDER + "";
	public static String underGray = GRAY + UNDER + "";
	public static String underGreen = GREEN + UNDER + "";
	public static String underPurple = LIGHT_PURPLE + UNDER + "";
	public static String underRed = RED + UNDER + "";
	public static String underWhite = WHITE + UNDER + "";
	public static String underYellow = YELLOW + UNDER + "";

	public static String info(String module, String message) {

		return BLUE + "<" + module + "> " + GREEN + message;

	}

	public static String error(String module, String message) {

		return BLUE + "<" + module + "> " + RED + message;
	}

	public static String database(String message) {

		return BLUE + "<Database> " + GREEN + message;
	}

	public static String databaseError(String message) {

		return BLUE + "<Database> " + RED + message;
	}

	public static String star(String message) {

		return boldAqua + "< StarNet > " + GREEN + message;
	}

}
