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

public class DisableModule extends CommandBase<Chat> {

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

	public DisableModule(Chat plugin) {
		super(plugin, Rank.DEVELOPER, new String[] { "disable" });
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(Player player, String[] args) {

		Bukkit.getServer().getPluginManager()
		.callEvent(new ModuleStateChangeEvent());
		
		for(Player allp : Bukkit.getOnlinePlayers()) {
			allp.playSound(allp.getLocation(), Sound.ENDERDRAGON_GROWL, 10F, 1F);
		}
		
		
		switch (args[0]) {
		case "filter":
			if (ChatFilter.isEnabled == true) {
				cf.disable();
				Bukkit.broadcastMessage(F.BOLD + "<SERVER> " + F.boldRed
						+ "The CHAT FILTER has been disabled by "
						+ player.getName());
			} else {
				player.sendMessage(F.error("Modules",
						"That module is already disabled."));
				return;
			}

			return;
		case "chat":
			if (com.starnetmc.core.modules.Chat.isEnabled == true) {
				c.disable();
				Bukkit.broadcastMessage(F.BOLD + "<SERVER> " + F.boldRed
						+ "CHAT has been disabled by " + player.getName());
			} else {
				player.sendMessage(F.error("Modules",
						"That module is already disabled."));
				return;
			}

			return;

		case "doublejump":
			if (DoubleJump.isEnabled == true) {
				dj.disable();
				Bukkit.broadcastMessage(F.BOLD + "<SERVER> " + F.boldRed
						+ "DOUBLE JUMP has been disabled by "
						+ player.getName());
			} else {
				player.sendMessage(F.error("Modules",
						"That module is already disabled."));
				return;
			}
			return;
		case "news":
			if (News.isEnabled == true) {
				news.disable();
				Bukkit.broadcastMessage(F.BOLD + "<SERVER> " + F.boldRed
						+ "The NEWS module has been disabled by "
						+ player.getName());
			} else {
				player.sendMessage(F.error("Modules",
						"That module is already disabled."));
				return;
			}
			return;
		case "settings":
			if (Settings.isEnabled == true) {
				set.disable();
				Bukkit.broadcastMessage(F.BOLD + "<SERVER> " + F.boldRed
						+ "SETTINGS have been disabled by " + player.getName());
			} else {
				player.sendMessage(F.error("Modules",
						"That module is already disabled."));
				return;
			}
			return;
		case "gadgets":
			if (Gadgets.isEnabled == true) {
				gadgets.disable();
				Bukkit.broadcastMessage(F.BOLD + "<SERVER> " + F.boldRed
						+ "GADGETS have been disabled by " + player.getName());
			} else {
				player.sendMessage(F.error("Modules",
						"That module is already disabled."));
				return;
			}
			return;
		case "inventory":
			if (HubInventory.isEnabled == true) {
				h.disable();
				Bukkit.broadcastMessage(F.BOLD + "<SERVER> " + F.boldRed
						+ "INVENTORY ITEMS have been disabled by "
						+ player.getName());
			} else {
				player.sendMessage(F.error("Modules",
						"That module is already disabled."));
				return;
			}
			return;
		case "gamemode":
			if (Gamemode.isEnabled == true) {
				gm.disable();
				Bukkit.broadcastMessage(F.BOLD + "<SERVER> " + F.boldRed
						+ "GAMEMODE CHANGING has been disabled by "
						+ player.getName());
				return;
			} else {
				player.sendMessage(F.error("Modules",
						"That module is already disabled."));
				return;
			}
		case "tp":
			if (Teleport.isEnabled == true) {
				tp.disable();
				Bukkit.broadcastMessage(F.BOLD + "<SERVER> " + F.boldRed
						+ "TELEPORTATION has been disabled by "
						+ player.getName());
				return;
			} else {
				player.sendMessage(F.error("Modules",
						"That module is already disabled."));
				return;
			}
		default:
			player.sendMessage(F.error("Modules", "Module not recognized."));
			return;

		}

	}

}
