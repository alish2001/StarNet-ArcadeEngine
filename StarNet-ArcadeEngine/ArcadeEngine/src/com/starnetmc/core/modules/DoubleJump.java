package com.starnetmc.core.modules;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import com.starnetmc.core.modules.manager.Module;
import com.starnetmc.core.modules.manager.ModuleType;

public class DoubleJump extends Module {

	public DoubleJump(JavaPlugin plugin) {
		super("DoubleJump Manager", 1.0, ModuleType.SERVER, plugin);
		// TODO Auto-generated constructor stub
	}

	public DoubleJump() {

	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerToggleFlight(PlayerToggleFlightEvent event) {

		if (event.getPlayer().getGameMode() == GameMode.CREATIVE
				|| event.getPlayer().getGameMode() == GameMode.SPECTATOR) {
			return;
		}

		if (isEnabled == true) {

			Player player = event.getPlayer();
			if (player.getGameMode() == GameMode.SURVIVAL
					|| player.getGameMode() == GameMode.ADVENTURE) {
				player.setFlying(false);
				Location loc = player.getLocation().clone();
				loc.setPitch(0.0F);
				Vector vel = player.getVelocity().clone();

				int strength = 5;

				Vector jump = vel.multiply(0.1D).setY(0.20D * strength);
				Vector look = loc.getDirection().normalize().multiply(1.5D);

				player.setVelocity(jump.add(look));
				player.playSound(player.getLocation(), Sound.ENDERDRAGON_WINGS,
						1, 1);
				player.setAllowFlight(false);

				event.setCancelled(true);
			}
		} else {
			return;
		}
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerMove(PlayerMoveEvent event) {

		if (isEnabled == true) {
			Player player = event.getPlayer();
			if (player.getGameMode() == GameMode.SURVIVAL) {
				if (!player.getAllowFlight()) {
					Location loc = player.getLocation();
					Block block = loc.getBlock().getRelative(BlockFace.DOWN);
					if (block.getType() != Material.AIR) {
						player.setAllowFlight(true);
					}
				}
			}
		} else {
			event.getPlayer().setAllowFlight(false);
			event.getPlayer().setFlying(false);
		}
	}

	@Override
	public void enable() {
		isEnabled = true;
		log("Enabled.");

	}

	@Override
	public void disable() {
		isEnabled = false;
		log("Disabled.");

	}

	public static boolean isEnabled;

}
