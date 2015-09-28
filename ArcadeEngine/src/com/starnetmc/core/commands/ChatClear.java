package com.starnetmc.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.starnetmc.core.commands.util.CommandBase;
import com.starnetmc.core.modules.Chat;
import com.starnetmc.core.util.F;
import com.starnetmc.core.util.Rank;

public class ChatClear extends CommandBase<Chat> {

	

	public ChatClear(Chat plugin) {
		super(plugin, Rank.OWNER, new String[] {"chat","clearchat"});
	}

	public void execute(Player player, String[] args) {
		

			if (args.length == 0) {
				player.sendMessage(F.error("Commands", "Not enough arguments!"));
				
			}

			if (args.length != 2) {
				player.sendMessage(F.error("Commands", "Not enough arguments!"));
				
			}

			if (args[0].equalsIgnoreCase("clear")) {

				if(args[1].equalsIgnoreCase("-n")) {
					clearChat();
					for(Player all : Bukkit.getOnlinePlayers()) {
						all.sendMessage(F.info("Chat", "The chat was cleared by "+player.getName()+"."));
					}
				}
				else if(args[1].equalsIgnoreCase("-a")) {
					clearChat();
					for(Player all : Bukkit.getOnlinePlayers()) {
						all.sendMessage(F.info("Chat", "The chat was cleared."));
					}
				}
				else if(args[1].equalsIgnoreCase("-s")) {
					clearChat();
				}
				else {
					player.sendMessage(F.error("Commands", "Argument not recognized."));
				}

			}
		
	}

	public void clearChat() {

		for (Player all : Bukkit.getOnlinePlayers()) {

			for (int i = 0; i < 150; i++) {
				all.sendMessage("   ");
			}

		}

	}
}
