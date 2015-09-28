package com.starnetmc.core.modules;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import com.starnetmc.core.accounts.AccountManager;
import com.starnetmc.core.gadgets.Gadgets;
import com.starnetmc.core.gadgets.GUI.GadgetGUI;
import com.starnetmc.core.modules.manager.Module;
import com.starnetmc.core.modules.manager.ModuleType;
import com.starnetmc.core.settings.SettingsGUI;
import com.starnetmc.core.util.F;
import com.starnetmc.core.util.ItemFactory;

public class HubInventory extends Module {

	public HubInventory(JavaPlugin plugin) {
		super("Hub Inventory Manager", 0.1, ModuleType.SERVER, plugin);
		isEnabled = true;
	}

	
	public HubInventory() {
		
	}
	
	public static void giveInventory(Player player) {

		ItemStack arcade = ItemFactory.createItem(Material.COMPASS, F.boldGreen
				+ "GAMES", null, false);
		ItemStack gadgets = ItemFactory.createItem(Material.ENDER_CHEST,
				F.boldRed + "GADGETS", null, false);
		ItemStack prefs = ItemFactory.createItem(Material.SLIME_BALL,
				F.boldGold + "SETTINGS", null, false);

		player.getInventory().setItem(0, arcade);
		player.getInventory().setItem(4, gadgets);
		player.getInventory().setItem(8, prefs);

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

	@EventHandler
	public void onClick(PlayerInteractEvent e) throws Exception {

		Player player = e.getPlayer();

		if (isEnabled == true) {

			if (e.getAction() == Action.RIGHT_CLICK_AIR
					|| e.getAction() == Action.RIGHT_CLICK_BLOCK) {

				if (player.getItemInHand().getType() == Material.COMPASS) {
					e.setCancelled(true);
					player.sendMessage(F.info("Arcade", "Coming soon!"));
				} else if (player.getItemInHand().getType() == Material.ENDER_CHEST) {
					e.setCancelled(true);
					
					if(Gadgets.isEnabled == false) {
						player.sendMessage(F.error("Modules", "Gadgets have been disabled by an owner."));
						return;
					}
					
					
					GadgetGUI.openGadgetGUI(player);
				} else if (player.getItemInHand().getType() == Material.SLIME_BALL) {
					e.setCancelled(true);

					if(Settings.isEnabled == false) {
						player.sendMessage(F.error("Modules", "Settings have been disabled by an owner."));
						return;
					}
					
					switch (AccountManager.getAccount(player).getRank()) {

					case ADMIN:
						SettingsGUI.openSettingsAGUI(player);
						break;
					case OWNER:
						SettingsGUI.openSettingsAGUI(player);
						break;
					case DEVELOPER:
						SettingsGUI.openSettingsAGUI(player);
						break;
					case BUILDER:
						SettingsGUI.openSettingsAGUI(player);
						break;
					case YOUTUBE:
						SettingsGUI.openSettingsAGUI(player);
						break;
					default:
						SettingsGUI.openSettingsGUI(player);
						break;

					}
				}

			}
		} else {
			player.sendMessage(F.error("Modules", "Inventory Items have been disabled by an owner."));;
		}

	}

	@EventHandler
	public void giveInv(PlayerJoinEvent e) {
		e.getPlayer().getInventory().clear();
		giveInventory(e.getPlayer());

	}
	
	@EventHandler
	public void removeInv(PlayerQuitEvent e){
		Player p = e.getPlayer();
		
		p.getInventory().remove(Material.COMPASS);
		p.getInventory().remove(Material.SLIME_BALL);
		p.getInventory().remove(Material.ENDER_CHEST);
	}

}
