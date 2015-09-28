package com.starnetmc.core.commands;

import org.bukkit.entity.Player;

import com.starnetmc.core.commands.util.CommandBase;
import com.starnetmc.core.modules.Chat;
import com.starnetmc.core.util.Rank;

public class TimeCommand extends CommandBase<Chat> {

	public TimeCommand(Chat plugin) {
		super(plugin, Rank.ADMIN, new String[] {"t"});
	}


	@Override
	public void execute(Player player, String[] args) {
		Time.openTimeGUI(player);
	}

}