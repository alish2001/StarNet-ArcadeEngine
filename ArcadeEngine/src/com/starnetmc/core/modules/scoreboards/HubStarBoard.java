package com.starnetmc.core.modules.scoreboards;

import org.bukkit.entity.Player;

import com.starnetmc.core.util.F;

public class HubStarBoard extends StarBoard {

	public HubStarBoard(Player player, String displayName) {
		super(player, "HUB");
		setDisplayName(F.boldAqua + "The Star Network");
		/*	board.setDisplayName(F.boldAqua + "The Star Network");
		
		board.setLine(12, F.boldYellow + "RANK:");
		board.setLine(11, "> " + AccountManager.getAccount(p).getRank().getTag(false));
		board.setBlank(10);
		board.setLine(9, F.boldPurple + "SHARDS:");
		board.setLine(8, "> " + F.boldYellow + AccountManager.getAccount(p).getShards());
		board.setBlank(7);
		board.setLine(6, F.boldYellow + "WEBSITE:");
		board.setLine(5, "> " + F.GREEN + "www.StarNetMC.com");
		board.setBlank(4);
		board.setLine(3, F.boldYellow + "ONLINE STAFF:");
		board.setLine(2, "> " + F.GREEN + AccountManager.getStaff().size());
		board.setLine(1, F.strikeGreen + "--------------");*/
		setBlank(14);
		build();
		send();
	}
	
}