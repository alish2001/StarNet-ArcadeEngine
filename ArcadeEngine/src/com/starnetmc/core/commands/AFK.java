package com.starnetmc.core.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import com.starnetmc.core.commands.util.CommandBase;
import com.starnetmc.core.modules.Chat;
import com.starnetmc.core.util.F;
import com.starnetmc.core.util.Rank;

public class AFK extends CommandBase<Chat> {

	public AFK(Chat plugin) {
		super(plugin, Rank.DEVELOPER, new String[] { "afk" });
	}

	private static List<String> afkplayer = new ArrayList<String>();

	public void execute(Player player, String[] args) {

		if (afkplayer.contains(player.getName())) {
			afkplayer.remove(player.getName());
			player.setGameMode(GameMode.SURVIVAL);
			player.setPlayerListName(player.getDisplayName());

		} else {
			afkplayer.add(player.getName());
			player.setGameMode(GameMode.SPECTATOR);
			player.setFlying(true);
			player.setPlayerListName(F.GRAY + "AFK " + player.getName());

		}
	}

}