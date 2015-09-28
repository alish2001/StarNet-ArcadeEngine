package com.starnetmc.core.communicator;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.starnetmc.core.modules.manager.Module;
import com.starnetmc.core.modules.manager.ModuleType;

public class Communicator extends Module {
	
	public Communicator(JavaPlugin plugin) {
		super("Communicator", 1.0, ModuleType.SERVER, plugin);
	}
	

	public Communicator() {
	}


	@Override
	public void enable() {
		init("Star");
		isEnabled = true;
		log("Enabled.");
	}

	@Override
	public void disable() {
		isEnabled = false;
		log("Disabled.");

	}

	public static boolean isEnabled;

	
	static String comChannel;
	
	public void init(String _comChannel){
		setChannel(_comChannel);
		Bukkit.getServer().getMessenger().registerOutgoingPluginChannel(getPlugin(), comChannel);
		Bukkit.getServer().getMessenger().registerIncomingPluginChannel(getPlugin(), comChannel, new NetCMDReciever());
	}
	
	@Deprecated
	public void sendCommand(String cmd){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(stream);
        
        try {
			out.writeUTF("StarCommand|" + cmd);
		} catch (IOException e) {
			e.printStackTrace();
		}
 
        //Bukkit.getServer().getPluginManager().sendData(comChannel, stream.toByteArray());
	}
	
	public void setChannel(String _comChannel){
		comChannel = _comChannel;
	}
	
	public static String getChannel(){
		return comChannel;
	}
	

}