package com.starnetmc.core.gamemode;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.starnetmc.core.commands.util.CommandBase;
import com.starnetmc.core.events.GamemodeChangeEvent;
import com.starnetmc.core.util.Rank;

public class GamemodeCommand extends CommandBase<Gamemode> {

	public GamemodeCommand(Gamemode plugin) {
		super(plugin, Rank.ADMIN, new String[] { "gm" });
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(Player player, String[] args) {

		Bukkit.getServer().getPluginManager()
				.callEvent(new GamemodeChangeEvent(player));

		GamemodeUI.openGMGUI(player);

	}

}
