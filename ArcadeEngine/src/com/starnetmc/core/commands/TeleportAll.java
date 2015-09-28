package com.starnetmc.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.starnetmc.core.commands.util.CommandBase;
import com.starnetmc.core.modules.Teleport;
import com.starnetmc.core.util.F;
import com.starnetmc.core.util.Rank;

public class TeleportAll extends CommandBase<Teleport> {

	public TeleportAll(Teleport plugin) {
		super(plugin, Rank.ADMIN, new String[] { "tpall", "teleportall" });
	}

	public void execute(Player player, String[] args) {

		if (Teleport.isEnabled == false) {
			player.sendMessage(F.error("Teleport Manager",
					"Teleportation has been disabled by an owner."));
			return;
		}

		player.sendMessage(F.info("Teleport", "Teleporting all players..."));
		for (Player all : Bukkit.getOnlinePlayers()) {

			all.teleport(player.getLocation());
			all.sendMessage(F.info("Teleport", "You have been teleported to: "
					+ player.getName()));
		}

	}

}