package com.starnetmc.core.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.server.ServerListPingEvent;

import com.starnetmc.core.util.F;

public class GenericListener implements Listener {

	@EventHandler
	public void onDrop(PlayerDropItemEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		e.getEntity().setHealth(20.0D);
	}	

	@EventHandler
	public void onPing(ServerListPingEvent e) {
		e.setMotd(F.boldAqua + "Star Network " + F.GRAY + "| " + F.GREEN
				+ "http://starnetmc.com/store \n" + F.boldGold + "COMING SOON!");
	}

	@EventHandler
	public void onCreatureSpawn(CreatureSpawnEvent e) {

		if (e.getSpawnReason() == SpawnReason.EGG) {
			e.setCancelled(true);
		}

	}

	@EventHandler
	public void onNonWhitelistLogin(AsyncPlayerPreLoginEvent e) {

		if (e.getLoginResult() == Result.KICK_WHITELIST) {

			e.disallow(
					Result.KICK_WHITELIST,
					F.error("Server",
							"The server is currently in private mode. Buy VIP to bypass this feature!"));

		}

	}

}
