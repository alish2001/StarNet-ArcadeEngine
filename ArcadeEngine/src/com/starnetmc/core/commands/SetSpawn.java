package com.starnetmc.core.commands;

import org.bukkit.entity.Player;

import com.starnetmc.core.commands.util.CommandBase;
import com.starnetmc.core.modules.Chat;
import com.starnetmc.core.util.F;
import com.starnetmc.core.util.Rank;

public class SetSpawn extends CommandBase<Chat> {

	public SetSpawn(Chat plugin) {
		super(plugin, Rank.ADMIN, new String[] {"setspawn","ss"});
	}

	public void execute(Player player, String[] args) {


		player.getWorld().setSpawnLocation(player.getLocation().getBlockX(),
				(player.getLocation().getBlockY() + 2),
				player.getLocation().getBlockZ());
		player.sendMessage(F.info("World", "Spawn updated."));

	}
}
