package com.starnetmc.core.punish;

import org.bukkit.entity.Player;

import com.starnetmc.core.commands.util.CommandBase;
import com.starnetmc.core.database.Databaser;
import com.starnetmc.core.util.F;
import com.starnetmc.core.util.Rank;
import com.starnetmc.core.util.StarMap;
import com.starnetmc.core.util.UPlayer;

public class PunishCommand extends CommandBase<Punish> {

	public static StarMap<Player, Punishment> punish = new StarMap<Player, Punishment>();

	
	public PunishCommand(Punish plugin) {
		super(plugin, Rank.HELPER, new String[] { "punish", "p" });
		// TODO Auto-generated constructor stub
	}

	public void execute(Player player, String[] args) {

		if(args.length < 2) {
			player.sendMessage(F.error("Commands", "Not enough arguments!"));
			return;
		}
		
		String msg = "";
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i < args.length; i++) {
			sb.append(args[i] + " ");
		}
		msg = sb.toString();

		if(!UPlayer.isOnline(args[0])) {
			if(!UPlayer.getOfflinePlayerFromName(args[0]).hasPlayedBefore()) {
				player.sendMessage(F.error("Offline Player Search", "That player has never played before!"));
				return;
			}
			
			punish.put(player, new Punishment(UPlayer.getOfflinePlayerFromName(args[0]).getName(), null, msg, player, false, false, 0L));
			try {
				switch (Databaser.getRank(player.getUniqueId().toString())) {
				case HELPER:
					if(Databaser.getRank(UPlayer.getOfflinePlayerFromName(args[0]).getUniqueId().toString()).has(Rank.MODERATOR)) {
						player.sendMessage(F.error("Permissions", "No permission!"));
						return;
					}
					PunishGUI.showHelperMenu(player, punish);
					break;
					case MODERATOR:
						if(Databaser.getRank(UPlayer.getOfflinePlayerFromName(args[0]).getUniqueId().toString()).has(Rank.ADMIN)) {
							player.sendMessage(F.error("Permissions", "No permission!"));
							return;
						}
						PunishGUI.showHelperMenu(player, punish);
						break;

				default:
					PunishGUI.showOtherMenu(player, punish);
					break;

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else {
			punish.put(player, new Punishment(args[0], null, msg, player, false, false, 0L));
			try {
				switch (Databaser.getRank(player.getUniqueId().toString())) {
				case HELPER:
					if(Databaser.getRank(UPlayer.getOfflinePlayerFromName(args[0]).getUniqueId().toString()).has(Rank.MODERATOR)) {
						player.sendMessage(F.error("Permissions", "No permission!"));
						return;
					}
					PunishGUI.showHelperMenu(player, punish);
					break;
				case MODERATOR:
					if(Databaser.getRank(UPlayer.getOfflinePlayerFromName(args[0]).getUniqueId().toString()).has(Rank.ADMIN)) {
						player.sendMessage(F.error("Permissions", "No permission!"));
						return;
					}
					PunishGUI.showOtherMenu(player, punish);
					break;
				default:
					PunishGUI.showOtherMenu(player, punish);
					break;

				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}

		
	}
}
