package com.starnetmc.core.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.starnetmc.ArcadeEngine.Utils.AF;
import com.starnetmc.core.commands.message.Message;
import com.starnetmc.core.gadgets.Gadgets;
import com.starnetmc.core.modules.Settings;

public class Quit implements Listener {

	@EventHandler(priority = EventPriority.MONITOR)
	public void onLeave(PlayerQuitEvent e) {
		Player player = e.getPlayer();

		Settings.removeUser(player);
		Gadgets.removeUser(player);
		
		if(Message.conversation.containsKey(player)) {
			Message.conversation.remove(player);
		}
		
		e.setQuitMessage(AF.boldAqua + "<" + AF.boldRed + "-" + AF.boldAqua + ">" + ChatColor.RESET + AF.yellow + " " + player.getName());
	}

}