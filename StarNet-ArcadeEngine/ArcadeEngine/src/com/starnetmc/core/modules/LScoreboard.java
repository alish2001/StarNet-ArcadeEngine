package com.starnetmc.core.modules;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import com.starnetmc.core.accounts.AccountManager;
import com.starnetmc.core.events.ShardPickupEvent;
import com.starnetmc.core.events.UpdateEvent;
import com.starnetmc.core.modules.manager.Module;
import com.starnetmc.core.modules.manager.ModuleType;
import com.starnetmc.core.util.F;
import com.starnetmc.core.util.UpdateType;

public class LScoreboard extends Module {

	public LScoreboard(JavaPlugin plugin) {

		super("Hub Scoreboard", 1.0, ModuleType.INFO, plugin);

	}

	public LScoreboard() {

	}

	@EventHandler
	public void updateJoin(PlayerJoinEvent e) throws Exception {
		
		updateScoreboard();
	}

	@EventHandler
	public void update(UpdateEvent e) throws Exception {
		if (e.getType() == UpdateType.SHORT) {
			updateScoreboard();
		}
	}

	@EventHandler
	public void updateOnShard(ShardPickupEvent e) throws Exception {
		updateScoreboard();
	}

	private void updateScoreboard() throws Exception {

		for (Player player : Bukkit.getOnlinePlayers()) {

			Scoreboard sb = Bukkit.getScoreboardManager().getNewScoreboard();
			Objective obj = sb.registerNewObjective("info", "dummy");
			obj.setDisplaySlot(DisplaySlot.SIDEBAR);
			obj.setDisplayName(F.boldAqua + "The Star Network");

				Score rank1 = obj.getScore(F.boldYellow + "RANK:");
				rank1.setScore(12);
				Score rank = obj.getScore("> " + AccountManager.getAccount(player).getRank().getTag(false));
				rank.setScore(11);
				
			Score whsp2 = obj.getScore(ChatColor.RESET.toString() + "");
			whsp2.setScore(10);

			Score _shard = obj.getScore(F.boldPurple + "SHARDS:");
			_shard.setScore(9);
			Score com = obj.getScore(F.WHITE + "> " + F.boldYellow
					+ AccountManager.getAccount(player).getShards());
			com.setScore(8);

			Score whsp3 = obj.getScore(ChatColor.RESET.toString() + ""
					+ ChatColor.RESET.toString());
			whsp3.setScore(7);

			Score web = obj.getScore(F.boldYellow + "WEBSITE:");
			web.setScore(6);
			Score weba = obj.getScore(F.WHITE + "> " + F.GREEN
					+ "www.StarNetMC.com");
			weba.setScore(5);

			Score whsp4 = obj.getScore(ChatColor.RESET.toString()
					+ ChatColor.RESET.toString() + ""
					+ ChatColor.RESET.toString());
			whsp4.setScore(4);

			Score whsp5 = obj.getScore(F.boldYellow + "ONLINE STAFF:");
			whsp5.setScore(3);

			Score whsp6 = obj.getScore(F.WHITE + "> " + F.GREEN
					+ AccountManager.getStaff().size());
			whsp6.setScore(2);

			Score end = obj.getScore(F.strikeGreen + "--------------");
			end.setScore(1);

			player.setScoreboard(sb);

		}

	}

	@Override
	public void enable() {
		try {
			updateScoreboard();
		} catch (Exception e) {

		}

		log("Enabled.");

	}

	@Override
	public void disable() {
		try {
			for (Player player : Bukkit.getOnlinePlayers()) {
				player.setScoreboard(Bukkit.getScoreboardManager()
						.getNewScoreboard());
			}
		} catch (Exception e) {

		}
		log("Disabled.");

	}

}
