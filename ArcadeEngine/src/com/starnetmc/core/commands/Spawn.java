package com.starnetmc.core.commands;

import org.bukkit.entity.Player;

import com.starnetmc.core.commands.util.CommandBase;
import com.starnetmc.core.modules.Chat;
import com.starnetmc.core.modules.Tutorial;
import com.starnetmc.core.util.F;
import com.starnetmc.core.util.Rank;

public class Spawn extends CommandBase<Chat> {

	public Spawn(Chat plugin) {
		super(plugin, Rank.DEFAULT, new String[] {"spawn","spanw"});
	}

	@Override
	public void execute(Player player, String[] args) {

		
		if (Tutorial.intut.contains(player.getName())) {
			return;
		}

		player.teleport(player.getWorld().getSpawnLocation());
		player.sendMessage(F.info("World", "You were teleported to spawn."));

	}
}
