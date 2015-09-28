package com.starnetmc.core.punish;

import java.util.LinkedHashMap;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.starnetmc.core.database.Databaser;
import com.starnetmc.core.modules.manager.Module;
import com.starnetmc.core.modules.manager.ModuleType;
import com.starnetmc.core.util.F;
import com.starnetmc.core.util.UPlayer;

public class Punish extends Module {

	public static LinkedHashMap<String, String> permMutes = new LinkedHashMap<String, String>();
	public static LinkedHashMap<String, String> permBans = new LinkedHashMap<String, String>();

	public Punish(JavaPlugin plugin) {
		super("Punish", 0.1, ModuleType.SERVER, plugin);
	}

	public void enable() {

		log("Enabled.");
	}

	public void disable() {

		log("Disabled.");
	}

	public void addCommands() {
		addCommand(new PunishCommand(this));
	}

	// Begin Listener

	@EventHandler
	public void onMutedChat(AsyncPlayerChatEvent e) {
		try {
			if (Databaser.isMuted(e.getPlayer().getUniqueId().toString())) {
				e.setCancelled(true);
				e.getPlayer().sendMessage(
						F.error("Punishments",
								"Shh... You're muted for \" "
										+ Databaser.getPunishReason(e.getPlayer())+"\""));
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		if (Punishment._tempMutes.containsKey(e.getPlayer().getUniqueId()
				.toString())) {
			e.setCancelled(true);
			e.getPlayer().sendMessage( 
					F.error("Punishments",
							"Shh... You're muted for \" "
									+ Punishment._tempMutes.get(e.getPlayer()
											.getUniqueId().toString())+"\""));
		}
	}

	@EventHandler
	public void onBannedLogin(PlayerJoinEvent e) {
		
		try {
			if (Databaser.isBanned(e.getPlayer().getUniqueId().toString())) {
				e.getPlayer().kickPlayer(
						F.error("Punishments",
								"You're banned for \" "
										+ Databaser.getPunishReason(e.getPlayer())+"\""));
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if (Punishment._tempBans.containsKey(e.getPlayer().getUniqueId()
				.toString())) {
			e.getPlayer().kickPlayer(
					F.error("Punishments",
							"You're banned for \" "
									+ Punishment._tempBans.get(e.getPlayer()
											.getUniqueId().toString())+"\""));
		}
	}

	@EventHandler
	public void onPunishClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();

		if (!e.getInventory().getName()
				.equalsIgnoreCase(F.underRed + "Punishments"))
			return;

		if (e.getCurrentItem() == null)
			return;

		if (e.getCurrentItem().getType() == Material.AIR)
			return;

		if (e.getCurrentItem().getItemMeta().getDisplayName()
				.equalsIgnoreCase(ChatColor.RED + "Permanent Ban")) {
			PunishCommand.punish.get(p).setType(PunishType.PERMBAN);
			PunishCommand.punish.get(p).setPermanent(true);
			try {
				PunishCommand.punish.get(p).setPunished();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			p.closeInventory();
			e.setCancelled(true);

		}

		else if (e.getCurrentItem().getItemMeta().getDisplayName()
				.equalsIgnoreCase(ChatColor.RED + "Permanent Mute")) {
			PunishCommand.punish.get(p).setType(PunishType.PERMMUTE);

			PunishCommand.punish.get(p).setPermanent(true);

			try {
				PunishCommand.punish.get(p).setPunished();
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			p.closeInventory();
			e.setCancelled(true);

		}

		else if (e.getCurrentItem().getItemMeta().getDisplayName()
				.equalsIgnoreCase(ChatColor.YELLOW + "Warn")) {
			UPlayer.getOnlinePlayerFromName(PunishCommand.punish.get(p).getOffender()).sendMessage(F.error("Punishments", "WARNING: You are breaking server rules. Please do not break them again, or face punishment."));
			p.closeInventory();
			e.setCancelled(true);

		}

		else if (e
				.getCurrentItem()
				.getItemMeta()
				.getDisplayName()
				.equalsIgnoreCase(
						ChatColor.YELLOW + "Chat " + ChatColor.AQUA + "-"
								+ ChatColor.YELLOW + " Severity 1")) {
			PunishCommand.punish.get(p).setType(PunishType.TEMPMUTE);

			PunishCommand.punish.get(p).setDuration(36000L);
			try {
				PunishCommand.punish.get(p).setPunished();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			p.closeInventory();
			e.setCancelled(true);

		}

		else if (e
				.getCurrentItem()
				.getItemMeta()
				.getDisplayName()
				.equalsIgnoreCase(
						ChatColor.GOLD + "Chat " + ChatColor.AQUA + "-"
								+ ChatColor.GOLD + " Severity 2")) {
			PunishCommand.punish.get(p).setType(PunishType.TEMPMUTE);

			PunishCommand.punish.get(p).setDuration(144000L);

			try {
				PunishCommand.punish.get(p).setPunished();
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			p.closeInventory();
			e.setCancelled(true);

		} else if (e
				.getCurrentItem()
				.getItemMeta()
				.getDisplayName()
				.equalsIgnoreCase(
						ChatColor.RED + "Chat " + ChatColor.AQUA + "-"
								+ ChatColor.RED + " Severity 3")) {
			PunishCommand.punish.get(p).setType(PunishType.PERMMUTE);

			PunishCommand.punish.get(p).setPermanent(true);
			try {
				PunishCommand.punish.get(p).setPunished();
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			e.setCancelled(true);
			p.closeInventory();
		}

		else if (e
				.getCurrentItem()
				.getItemMeta()
				.getDisplayName()
				.equalsIgnoreCase(
						ChatColor.GOLD + "Exploitation " + ChatColor.AQUA + "-"
								+ ChatColor.YELLOW + " Exploit Sev.")) {

			PunishCommand.punish.get(p).setType(PunishType.TEMPBAN);

			PunishCommand.punish.get(p).setDuration(864000L);
			try {
				PunishCommand.punish.get(p).setPunished();
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			e.setCancelled(true);
			p.closeInventory();

		}

		else if (e
				.getCurrentItem()
				.getItemMeta()
				.getDisplayName()
				.equalsIgnoreCase(
						ChatColor.GOLD + "Unapproved MODs " + ChatColor.AQUA
								+ "-" + ChatColor.RED + " Unapproved MOD Sev.")) {

			PunishCommand.punish.get(p).setType(PunishType.TEMPBAN);

			PunishCommand.punish.get(p).setDuration(864000L);
			try {
				PunishCommand.punish.get(p).setPunished();
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			e.setCancelled(true);
			p.closeInventory();

		} else if (e
				.getCurrentItem()
				.getItemMeta()
				.getDisplayName()
				.equalsIgnoreCase(
						ChatColor.RED + "Hacked Client " + ChatColor.AQUA + "-"
								+ ChatColor.DARK_RED + " Hacking Sev.")) {

			PunishCommand.punish.get(p).setType(PunishType.PERMBAN);
			PunishCommand.punish.get(p).setPermanent(true);
			try {
				PunishCommand.punish.get(p).setPunished();
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			e.setCancelled(true);
			p.closeInventory();
		} 
		else if(e.getCurrentItem().getType() == Material.SKULL_ITEM) {
			
			e.setCancelled(true);
		
			try {
				Databaser.removeAllPunishments(UPlayer.getOfflinePlayerFromName(PunishCommand.punish.get(p).getOffender()).getName());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			p.closeInventory();
		}
		else if(e.getCurrentItem().getType() == Material.STAINED_GLASS_PANE) {
			e.setCancelled(true);
		}
		else {
			e.setCancelled(true);

		}

	}

}
