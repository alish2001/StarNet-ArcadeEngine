package com.starnetmc.core.util;

import org.bukkit.entity.Player;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.starnetmc.core.Main;

public class UNet {
	
	public static void netKickPlayer(Player p, String playerName, String reason){
		  ByteArrayDataOutput out = ByteStreams.newDataOutput();
		  out.writeUTF("KickPlayer");
		  out.writeUTF(playerName);
		  out.writeUTF(reason);
		  
		 p.sendPluginMessage(Main.getPlugin(), "BungeeCord", out.toByteArray());
	}


}