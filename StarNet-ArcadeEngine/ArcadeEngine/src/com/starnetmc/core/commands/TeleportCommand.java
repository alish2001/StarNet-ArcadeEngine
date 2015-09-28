package com.starnetmc.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.starnetmc.core.commands.util.CommandBase;
import com.starnetmc.core.modules.Teleport;
import com.starnetmc.core.util.F;
import com.starnetmc.core.util.Rank;

public class TeleportCommand extends CommandBase<Teleport> {

	public TeleportCommand(Teleport plugin) {
		super(plugin, Rank.MODERATOR, new String[] { "teleport", "tp" });
		// TODO Auto-generated constructor stub
	}

	public void execute(Player player, String[] args) {

		if (Teleport.isEnabled == false) {
			player.sendMessage(F.error("Teleport Manager",
					"Teleportation has been disabled by an owner."));
			return;
		}

		if (args.length < 1) {
			player.sendMessage(F.error("Commands", "Not enough arguments!"));
			return;
		}

		Player teleportee = Bukkit.getPlayer(args[0]);

		if (args[0].equalsIgnoreCase(player.getName()) && args.length == 1) {
			player.sendMessage(F.error("Mirror",
					"You can't teleport to yourself!"));
			return;
		}
		if (args.length == 1) {
			player.teleport(teleportee.getLocation());
			player.sendMessage(F.info("Teleport", "You have teleported to: "
					+ F.GOLD + teleportee.getName()));
			return;
		} else if (args.length == 2) {
			Player teleport1 = Bukkit.getPlayer(args[1]);
			teleportee.teleport(teleport1.getLocation());
			player.sendMessage(F.info("Teleport", "You have teleported "
					+ F.GOLD + teleportee.getName() + F.GREEN + " to " + F.GOLD
					+ teleport1.getName()));

		}

	}
}
