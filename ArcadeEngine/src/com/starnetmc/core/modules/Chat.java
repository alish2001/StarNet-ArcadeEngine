package com.starnetmc.core.modules;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.starnetmc.core.accounts.AccountManager;
import com.starnetmc.core.commands.AFK;
import com.starnetmc.core.commands.Broadcast;
import com.starnetmc.core.commands.ChatClear;
import com.starnetmc.core.commands.RankCommand;
import com.starnetmc.core.commands.SetSpawn;
import com.starnetmc.core.commands.StaffChat;
import com.starnetmc.core.commands.Test;
import com.starnetmc.core.commands.TimeCommand;
import com.starnetmc.core.commands.message.Message;
import com.starnetmc.core.commands.message.Reply;
import com.starnetmc.core.commands.modulecontrol.DisableModule;
import com.starnetmc.core.commands.modulecontrol.EnableModule;
import com.starnetmc.core.modules.manager.Module;
import com.starnetmc.core.modules.manager.ModuleType;
import com.starnetmc.core.util.F;
import com.starnetmc.core.util.Rank;
import com.starnetmc.core.util.StarMap;

public class Chat extends Module {

	JavaPlugin plugin;

	public Chat(JavaPlugin plugin) {
		super("Chat", 0.5, ModuleType.INFO, plugin);
		this.plugin = plugin;
	}

	public Chat() {

	}

	@Override
	public void enable() {
		isEnabled = true;
		log("Enabled.");

	}

	@Override
	public void addCommands() {
		addCommand(new AFK(this));
		addCommand(new Broadcast(this));
		addCommand(new ChatClear(this));
		addCommand(new StaffChat(this));
		addCommand(new Message(this));
		addCommand(new Reply(this));
		addCommand(new Test(this));
		addCommand(new TimeCommand(this));
		addCommand(new EnableModule(this));
		addCommand(new DisableModule(this));
		addCommand(new RankCommand(this));
		addCommand(new SetSpawn(this));
	}

	@Override
	public void disable() {
		isEnabled = false;
		log("Disabled.");
	}

	public static boolean isEnabled;

	
	public static void setTag(Player player) {

		if (AccountManager.getAccount(player).getRank() != Rank.DEFAULT){
	    player.setDisplayName(AccountManager.getAccount(player).getRank().getTag(false) + " " + F.YELLOW + player.getName());
		} else {
			 player.setDisplayName(F.YELLOW + player.getName());
		}
		player.setPlayerListName(player.getDisplayName());
	}
	
	@EventHandler
	public void silence(AsyncPlayerChatEvent e) {

		if (isEnabled = true) {
			return;
		}
		if (isEnabled = false) {
			e.setCancelled(true);
			e.getPlayer().sendMessage(
					F.error("Chat", "Chat is currently silenced."));
		}

	}

	public static StarMap<String, String> _playerLastMessage = new StarMap<String, String>();

	@EventHandler(priority = EventPriority.LOW)
	public void listenToChat(AsyncPlayerChatEvent e) {
		Player player = e.getPlayer();

		e.setFormat(player.getDisplayName() + F.GRAY + ": " + F.RESET
				+ e.getMessage());

		if (e.getMessage().contains("%s")) {
			e.setMessage(e.getMessage().replace("%s", "s"));
		}

		if (_playerLastMessage.containsKey(player.getName())
				&& _playerLastMessage.get(player.getName()).equalsIgnoreCase(
						e.getMessage())) {

			if (AccountManager.getAccount(player).getRank() == Rank.OWNER) {
				return;
			} else {
				player.sendMessage(F.error("Chat",
						"You can't send the same message twice!"));
				e.setCancelled(true);
			}
		} else {
			_playerLastMessage.put(player.getName(), e.getMessage());
		}

	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void exitConversation(PlayerQuitEvent e){
		Player p = e.getPlayer();
		if (!Message.conversation.containsKey(p)) return;
		Message.conversation.remove(p);
	}
 
}