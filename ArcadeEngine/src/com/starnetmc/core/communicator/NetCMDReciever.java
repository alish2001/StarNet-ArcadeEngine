package com.starnetmc.core.communicator;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import com.starnetmc.core.util.Logger;

public class NetCMDReciever implements PluginMessageListener {

	@Override
	public void onPluginMessageReceived(String channel, Player p, byte[] input) {
		if (!channel.equalsIgnoreCase(Communicator.getChannel())) return;
		ByteArrayInputStream bytein = new ByteArrayInputStream(input);
		DataInputStream data = new DataInputStream(bytein);
		String cmd = "";
		
		try {
		     cmd = deSerializeCMD(data.readUTF().toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (cmd.equalsIgnoreCase("end")){
			  Logger.log("<Communicator> Recived shutdown command from StarNetworker.");
			  Bukkit.getServer().shutdown();
		}
	}
	
	private String deSerializeCMD(String serializedCMD){
		String[] data = serializedCMD.split("\\|");
		String cmd = "null";
		if (data[0].equalsIgnoreCase("StarCommand")){
			cmd = data[1];
		}
		
		return cmd;
	}

}