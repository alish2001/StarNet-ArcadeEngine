package com.starnetmc.core.commands;

import org.bukkit.entity.Player;

import com.starnetmc.core.accounts.AccountManager;
import com.starnetmc.core.commands.util.CommandBase;
import com.starnetmc.core.modules.Settings;
import com.starnetmc.core.settings.SettingsGUI;
import com.starnetmc.core.util.F;
import com.starnetmc.core.util.Rank;

public class SettingsCommand extends CommandBase<Settings> {

	public SettingsCommand(Settings plugin) {
		super(plugin, Rank.DEFAULT, new String[] { "settings", "setting",
				"prefs", "pref" });

	}

	public void execute(Player player, String[] args) {

		if (Settings.isEnabled == false) {
			player.sendMessage(F.error("Modules",
					"Settings have been disabled by an owner."));
			return;
		} else {

			try {

				switch (AccountManager.getAccount(player).getRank()) {

				case BUILDER:
					SettingsGUI.openSettingsAGUI(player);
					break;
				case ADMIN:
					SettingsGUI.openSettingsAGUI(player);
					break;
				case OWNER:
					SettingsGUI.openSettingsAGUI(player);
					break;
				case DEVELOPER:
					SettingsGUI.openSettingsAGUI(player);
					break;
				default:
					SettingsGUI.openSettingsGUI(player);
					break;

				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}
}