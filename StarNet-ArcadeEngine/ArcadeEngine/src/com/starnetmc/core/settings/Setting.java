package com.starnetmc.core.settings;

import org.bukkit.entity.Player;

public class Setting {

	boolean chat;
	boolean build;
	boolean vis;
	boolean msg;
	Player player;

	public Setting(Player player, boolean chat, boolean build, boolean vis, boolean msg) {

		this.chat = chat;
		this.build = build;
		this.vis = vis;
		this.msg = msg;
		this.player = player;
	}

	public Player getPlayer() {
		return player;

	}

	public boolean canChat() {

		return chat;
	}

	public void setCanChat(boolean endis) {
		chat = endis;
	}

	public boolean canRecMsg() {
		return msg;
	}
	
	public void setCanRecMsg(boolean endis) {
		msg = endis;
	}
	
	public boolean getBuildMode() {

		return build;
	}

	public void setBuildMode(boolean endis) {
		build = endis;
	}

	public boolean playersVisible() {

		return vis;
	}

	public void setPlayersVisible(boolean endis) {
		vis = endis;
	}
}