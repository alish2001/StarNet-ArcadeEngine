package com.starnetmc.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.starnetmc.core.accounts.AccountManager;
import com.starnetmc.core.commands.util.CommandBase;
import com.starnetmc.core.database.Databaser;
import com.starnetmc.core.modules.Chat;
import com.starnetmc.core.util.F;
import com.starnetmc.core.util.Rank;
import com.starnetmc.core.util.UPlayer;

public class RankCommand extends CommandBase<Chat> {

	public RankCommand(Chat plugin) {
		super(plugin, Rank.ADMIN, new String[] { "rank", "setrank", "updaterank" });

	}

	public void execute(Player player, String[] args) {

		if (args.length < 2) {
			player.sendMessage(F.error("Commands", "Please specify a player and a rank."));
			return;
		}

		OfflinePlayer target = Bukkit.getPlayer(args[0]);

		try {
			if (!Databaser.hasAccount(target.getPlayer().getUniqueId().toString())) {
				player.sendMessage(F.error("Player Search", "Player not found."));
				return;
			} else {
				Databaser.setRank(target.getPlayer().getUniqueId().toString(), args[1].toUpperCase());
				player.sendMessage(F.info("Rank", "Rank updated."));

				if (target.isOnline()) {
					Player onlineTarget = UPlayer.getOnlinePlayerFromName(target.getName());
					AccountManager.getAccount(onlineTarget).setRank(Rank.getRankFromString(args[1]));
					player.sendMessage(F.info("Rank", "Rank updated."));
					
					onlineTarget.getPlayer().sendMessage(F.info("Rank", "Attention: Your rank has been changed. Please relog."));
					onlineTarget.getPlayer().playSound(onlineTarget.getLocation(), Sound.NOTE_PLING, 7F, 1F);
				}
				
				else {
					
					Databaser.setRank(target.getPlayer().getUniqueId().toString(), args[1].toUpperCase());
					player.sendMessage(F.info("Rank", "Rank updated."));
					return;
				}
				
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
