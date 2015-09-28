package com.starnetmc.core.commands.modulecontrol;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.starnetmc.core.commands.util.CommandBase;
import com.starnetmc.core.events.ModuleStateChangeEvent;
import com.starnetmc.core.gadgets.Gadgets;
import com.starnetmc.core.gamemode.Gamemode;
import com.starnetmc.core.modules.ArcadeBorder;
import com.starnetmc.core.modules.Chat;
import com.starnetmc.core.modules.ChatFilter;
import com.starnetmc.core.modules.DoubleJump;
import com.starnetmc.core.modules.HubInventory;
import com.starnetmc.core.modules.LScoreboard;
import com.starnetmc.core.modules.News;
import com.starnetmc.core.modules.Settings;
import com.starnetmc.core.modules.Teleport;
import com.starnetmc.core.modules.Tutorial;
import com.starnetmc.core.util.F;
import com.starnetmc.core.util.Rank;

public class EnableModule extends CommandBase<Chat> {

	public EnableModule(Chat plugin) {
		super(plugin, Rank.DEVELOPER, new String[] { "enable" });
		// TODO Auto-generated constructor stub
	}

	ArcadeBorder border = new ArcadeBorder();
	ChatFilter cf = new ChatFilter();
	DoubleJump dj = new DoubleJump();
	Gadgets gadgets = new Gadgets();
	LScoreboard sc = new LScoreboard();
	News news = new News();
	Settings set = new Settings();
	Tutorial tut = new Tutorial();
	com.starnetmc.core.modules.Chat c = new com.starnetmc.core.modules.Chat();
	HubInventory h = new HubInventory();
	Gamemode gm = new Gamemode();
	Teleport tp = new Teleport();

	public void execute(Player player, String[] args) {

		Bukkit.getServer().getPluginManager()
				.callEvent(new ModuleStateChangeEvent());

		for(Player allp : Bukkit.getOnlinePlayers()) {
			allp.playSound(allp.getLocation(), Sound.ENDERDRAGON_GROWL, 10F, 1F);
		}
		
		if (args.length == 0) {
			player.sendMessage(F.error("Commands", "Not enough arguments!"));
			return;
		}
		if (args.length > 1) {
			player.sendMessage(F.error("Commands", "Too many arguments!"));
			return;
		}

		String module = args[0];
		switch (module) {
		case "filter":
			if (ChatFilter.isEnabled == false) {
				cf.enable();
				Bukkit.broadcastMessage(F.BOLD + "<SERVER> " + F.boldGreen
						+ "The CHAT FILTER has been enabled by "
						+ player.getName());
			} else {
				player.sendMessage(F.error("Modules",
						"That module is already enabled."));
				return;
			}

			return;

		case "chat":
			if (com.starnetmc.core.modules.Chat.isEnabled == true) {
				c.enable();
				Bukkit.broadcastMessage(F.BOLD + "<SERVER> " + F.boldRed
						+ "CHAT has been enabled by " + player.getName());
			} else {
				player.sendMessage(F.error("Modules",
						"That module is already enabled."));
				return;
			}

			return;

		case "doublejump":
			if (DoubleJump.isEnabled == false) {
				dj.enable();
				Bukkit.broadcastMessage(F.BOLD + "<SERVER> " + F.boldGreen
						+ "DOUBLE JUMP has been enabled by " + player.getName());
			} else {
				player.sendMessage(F.error("Modules",
						"That module is already enabled."));
				return;
			}
			return;
		case "news":
			if (News.isEnabled == false) {
				news.enable();
				Bukkit.broadcastMessage(F.BOLD + "<SERVER> " + F.boldGreen
						+ "The NEWS module has been enabled by "
						+ player.getName());
			} else {
				player.sendMessage(F.error("Modules",
						"That module is already enabled."));
				return;
			}
			return;
		case "settings":
			if (Settings.isEnabled == false) {
				set.enable();
				Bukkit.broadcastMessage(F.BOLD + "<SERVER> " + F.boldGreen
						+ "SETTINGS have been enabled by " + player.getName());
			} else {
				player.sendMessage(F.error("Modules",
						"That module is already enabled."));
				return;
			}
			return;
		case "inventory":
			if (HubInventory.isEnabled == false) {
				h.enable();
				Bukkit.broadcastMessage(F.BOLD + "<SERVER> " + F.boldGreen
						+ "INVENTORY ITEMS have been enabled by "
						+ player.getName());
			} else {
				player.sendMessage(F.error("Modules",
						"That module is already enabled."));
				return;
			}
			return;
		case "gadgets":
			if (Gadgets.isEnabled == false) {
				gadgets.enable();
				Bukkit.broadcastMessage(F.BOLD + "<SERVER> " + F.boldGreen
						+ "GADGETS have been enabled by " + player.getName());
			} else {
				player.sendMessage(F.error("Modules",
						"That module is already enabled."));
				return;
			}
			return;
		case "gamemode":
			if (Gamemode.isEnabled == false) {
				gm.enable();
				Bukkit.broadcastMessage(F.BOLD + "<SERVER> " + F.boldGreen
						+ "GAMEMODE CHANGING has been enabled by "
						+ player.getName());
				return;
			} else {
				player.sendMessage(F.error("Modules",
						"That module is already enabled."));
				return;
			}
		case "tp":
			if (Teleport.isEnabled == false) {
				tp.enable();
				Bukkit.broadcastMessage(F.BOLD + "<SERVER> " + F.boldGreen
						+ "TELEPORTATION has been enabled by "
						+ player.getName());
				return;
			} else {
				player.sendMessage(F.error("Modules",
						"That module is already enabled."));
				return;
			}
		default:
			player.sendMessage(F.error("Modules", "Module not recognized."));
			return;

		}

	}

}
