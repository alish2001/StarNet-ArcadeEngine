package com.starnetmc.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.starnetmc.core.commands.util.CommandBase;
import com.starnetmc.core.modules.Chat;
import com.starnetmc.core.util.F;
import com.starnetmc.core.util.Rank;

public class Broadcast extends CommandBase<Chat> {

	public Broadcast(Chat plugin) {
		super(plugin, Rank.MODERATOR, new String[] { "a", "b", "annc",
				"broadcast", "announce", "say" });
		// TODO Auto-generated constructor stub
	}

	public void execute(Player player, String[] args) {

		if (args.length < 1) {
			player.sendMessage(F.error("Commands", "Not enough arguments!"));
			return;
		} else {
			String annc = "";
			for (String arg : args)
				annc = annc + arg + " ";

			if (annc.length() > 0) {
				annc = annc.substring(0, annc.length() - 1);
			}
			Bukkit.broadcastMessage(F.BOLD + player.getName() + " - "
					+ F.boldAqua + annc);

			for (Player all : Bukkit.getOnlinePlayers()) {
				all.playSound(all.getLocation(), Sound.NOTE_PLING, 8F, 1F);
			}
		}

	}

}
