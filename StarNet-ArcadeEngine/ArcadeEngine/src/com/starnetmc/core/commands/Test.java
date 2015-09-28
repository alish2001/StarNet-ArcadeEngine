package com.starnetmc.core.commands;

import org.bukkit.entity.Player;

import com.starnetmc.ArcadeCore;
import com.starnetmc.core.commands.util.CommandBase;
import com.starnetmc.core.modules.Chat;
import com.starnetmc.core.util.F;
import com.starnetmc.core.util.Rank;

public class Test extends CommandBase<Chat> {

	public Test(Chat plugin) {
		super(plugin, Rank.VIP, new String[] { "test" });
	}
	
	public void execute(Player player, String[] args) {
		
		player.sendMessage("Testing..");
		player.sendMessage(F.info("Test", "foreverSpec=" + ArcadeCore.getArcadeManager().getPropertiesManager().getProperties().getForeverSpec()));
		return;
	}
	
}