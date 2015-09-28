package com.starnetmc.core.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.help.HelpTopic;

import com.starnetmc.ArcadeCore;
import com.starnetmc.ArcadeEngine.Events.MapUnLoadEvent;
import com.starnetmc.core.accounts.AccountManager;
import com.starnetmc.core.commands.HelpCommand;
import com.starnetmc.core.util.F;
import com.starnetmc.core.util.Rank;

public class BlockCommands implements Listener {

	@EventHandler(priority = EventPriority.HIGH)
	public void blockCommands(PlayerCommandPreprocessEvent e) throws Exception {

		if ((!e.isCancelled())) {
			String command = e.getMessage().split(" ")[0];
			HelpTopic htopic = Bukkit.getServer().getHelpMap()
					.getHelpTopic(command);
			if (htopic == null) {
				
				e.setCancelled(true);
			}
		}

		if (e.getMessage().startsWith("/stop")) {

			e.setCancelled(true);
			if (AccountManager.getAccount(e.getPlayer()).getRank() != Rank.OWNER){
				e.getPlayer().sendMessage(F.error("Permissions", "Much deny permissions, so wow."));
				return;
			}
			
			Bukkit.broadcastMessage(F.error("StarNet", "This server is shutting down."));
			try {
				for (Entity en : e.getPlayer().getWorld().getEntities()) {

					if (en instanceof Player) {
						return;
					}

					en.remove();

				}
				
				Bukkit.getServer().getPluginManager().callEvent(new MapUnLoadEvent(ArcadeCore.getArcadeManager().getMapper().getActiveMap().getCenter().getWorld()));
			} finally {
				Bukkit.getServer().shutdown();
			}

		}

		if (e.getMessage().startsWith("/me")) {
			e.setCancelled(true);
			e.getPlayer().sendMessage(F.error("Permissions", "Much deny permissions, so wow."));
		}

		if (e.getMessage().startsWith("/help")
				|| e.getMessage().startsWith("/?")) {
			e.setCancelled(true);
			HelpCommand.sendHelp(e.getPlayer());
		}

	}

}
