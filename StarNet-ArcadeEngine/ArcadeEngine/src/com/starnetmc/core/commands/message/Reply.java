package com.starnetmc.core.commands.message;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.starnetmc.core.commands.util.CommandBase;
import com.starnetmc.core.modules.Chat;
import com.starnetmc.core.util.F;
import com.starnetmc.core.util.Rank;

public class Reply extends CommandBase<Chat> {

	public Reply(Chat plugin) {
		super(plugin, Rank.DEFAULT, new String[] { "reply", "r" });
		// TODO Auto-generated constructor stub
	}

	public void execute(Player player, String[] args) {

		if (args.length == 0 || args.length < 1) {

			player.sendMessage(F.error("Commands", "Not enough arguments!"));
			return;

		}

		Player rtarget = Message.conversation.get(player);

		if (args.length > 0) {

			if (!Message.conversation.containsKey(player)) {
				player.sendMessage(F.error("Message",
						"You have not messaged anyone recently."));
				return;
			}

			if (!rtarget.isOnline()) {
				player.sendMessage(F.error("Message",
						"That player is no longer online."));
				Message.conversation.remove(player);
				return;
			}

			StringBuilder sb = new StringBuilder();
			for (String arg : args) {
				sb.append(arg + " ");
			}
			String message = sb.toString();

			player.sendMessage(F.boldBlue + "You > " + rtarget.getName() + ": "
					+ F.GOLD + message);
			rtarget.sendMessage(F.boldBlue + player.getName() + " > You: "
					+ F.GOLD + message);
			rtarget.playSound(player.getLocation(), Sound.LEVEL_UP, 10F, 0F);
			return;
		}

	}

}
