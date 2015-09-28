package com.starnetmc.core.commands.message;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.starnetmc.core.commands.util.CommandBase;
import com.starnetmc.core.modules.Chat;
import com.starnetmc.core.modules.Settings;
import com.starnetmc.core.util.F;
import com.starnetmc.core.util.Rank;
import com.starnetmc.core.util.StarMap;

public class Message extends CommandBase<Chat> {

	public Message(Chat plugin) {
		super(plugin, Rank.DEFAULT, new String[] {"message","msg","w","tell"});
		
	
		
	}

	public static StarMap<Player, Player> conversation = new StarMap<Player, Player>();

	public void execute(Player player, String[] args) {

	
		

			if (args.length == 0 || args.length < 1) {

				player.sendMessage(F.error("Commands", "Not enough arguments!"));
				return;

			}


			Player target = Bukkit.getPlayer(args[0]);

			if (args.length > 1) {

				String msg = "";
				StringBuilder sb = new StringBuilder();
				for (int i = 1; i < args.length; i++) {
					sb.append(args[i] + " ");
				}
				msg = sb.toString();

				if (target == null) {
					player.sendMessage(F.error("Player Search",
							"That player is not online."));
					return;
				}

				if (!target.isOnline()) {
					player.sendMessage(F.error("Player Search",
							"That player is not online."));
					return;
				}

				if (Settings.getPrefs(target).canRecMsg() == false) {
					player.sendMessage(F.error("Message", target.getName()
							+ " has private messaging turned off."));
					return;
				}

				if(target.getName().equals("SparkWings") || target.getName().equals("alish2001") || target.getName().equals("3LectricWolf_")) {
					
					player.sendMessage(F.info("Message", "Please keep in mind that "+target.getName()+" is very busy, and can take a while to respond to you. :)"));
					
				}
				
				player.sendMessage(ChatColor.BLUE + "" + ChatColor.BOLD
						+ "You > " + target.getName() + ": " + ChatColor.GOLD
						+ msg);
				target.sendMessage(ChatColor.BLUE + "" + ChatColor.BOLD
						+ player.getName() + " > You" + ": " + ChatColor.GOLD
						+ msg);
				target.playSound(player.getLocation(), Sound.LEVEL_UP, 10F, 0F);
				conversation.put(player, target);
				return;
			}
			player.sendMessage(F.error("Commands", "Not enough arguments!"));

	}
}
