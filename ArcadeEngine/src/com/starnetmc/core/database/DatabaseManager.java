package com.starnetmc.core.database;

import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.starnetmc.ArcadeCore;
import com.starnetmc.core.database.util.MySQL;
import com.starnetmc.core.events.DatabaseConnectionStateChangeEvent;
import com.starnetmc.core.events.UpdateEvent;
import com.starnetmc.core.modules.manager.Module;
import com.starnetmc.core.modules.manager.ModuleType;
import com.starnetmc.core.util.Logger;
import com.starnetmc.core.util.Tickifier;
import com.starnetmc.core.util.Tickifier.Time;
import com.starnetmc.core.util.UpdateType;

public class DatabaseManager extends Module {
	
	public DatabaseManager(JavaPlugin plugin) {
		super("Database Manager", 1.0, ModuleType.SERVER, plugin);
	}
	

	public DatabaseManager() {
	}


	@Override
	public void enable() {
		isEnabled = true;
		
		creds = new StarDatabaseCreds();
		connected = false;
		databaser = new Databaser(getPlugin(), new MySQL(getPlugin(), creds.getIP(), creds.getPort(), creds.getName(), creds.getUserName(), creds.getPassword()));
		
		connect();
		log("enabled.");
	}

	@Override
	public void disable() {
		isEnabled = false;
		log("disabled.");

	}

	public static boolean isEnabled;
	
	private static Databaser databaser;
	private static DatabaseCreds creds;
	
	private static boolean connected = false;
	
	public static void connect(){
		
		Logger.log("Attempting Connection with database creds: name: " + creds.getName() + " ip: " + creds.getIP());
		
		  try {
			Databaser.db.openConnection();
			databaser.setup();
			connected = true;
			Bukkit.getServer().getPluginManager().callEvent(new DatabaseConnectionStateChangeEvent(connected));
			Logger.log("<DatabaseManager> Connected to the Database: " + creds.getName() + "!");
			Databaser.db.closeConnection();
			
		} catch (Exception e) {
			connected = false;
			Bukkit.getServer().getPluginManager().callEvent(new DatabaseConnectionStateChangeEvent(connected));
			Logger.log("<DatabaseManager> Unable to connect to the Database: " + creds.getName() + "!");
			Logger.log("<StackTracer> Printing StackTrace...");
			Logger.log("------------------------------------------------------------");
			e.printStackTrace();
			Logger.log("------------------------------------------------------------");
			Logger.log("<DatabaseManager> Will attempt to reconnect in 60 seconds!");
			reConnect();
		}
	}
	
	public static void reConnect(){
		new BukkitRunnable() {
			
			@Override
			public void run() {
				  try {
						Databaser.db.openConnection();
						databaser.setup();
						connected = true;
						Bukkit.getServer().getPluginManager().callEvent(new DatabaseConnectionStateChangeEvent(connected));
						Logger.log("<DatabaseManager> Reconnection SUCESSFUL!");
						Logger.log("<DatabaseManager> Connected to the Database: " + creds.getName() + "!");
						Databaser.db.closeConnection();
						
					} catch (Exception e) {
						Logger.log("<DatabaseManager> Reconnection FAILED!");
						Logger.log("<DatabaseManager> Unable to connect to the Database: " + creds.getName() + "!");
						Logger.log("<StackTracer> Printing StackTrace...");
						Logger.log("------------------------------------------------------------");
						e.printStackTrace();
						Logger.log("------------------------------------------------------------");
						Logger.log("<DatabaseManager> Will attempt to reconnect again in 60 seconds!");
						reConnect();
					}
			}
		}.runTaskLater(ArcadeCore.getPlugin(), Tickifier.tickify(60, Time.SECONDS));
	}
	
	@EventHandler
	public void connectionChecker(UpdateEvent e){
		if (e.getType() != UpdateType.MINUTE) return;
		if (!isConnected()) return;
		
		try {
			if (!Databaser.db.checkConnection()){
				Databaser.db.openConnection();
			}
			
		} catch (SQLException | ClassNotFoundException ex) {
			Logger.log("<DatabaseManager> Connection to the database has been lost.");
			connected = false;
			Bukkit.getServer().getPluginManager().callEvent(new DatabaseConnectionStateChangeEvent(connected));
			Logger.log("<StackTracer> Printing StackTrace...");
			Logger.log("------------------------------------------------------------");
			ex.printStackTrace();
			Logger.log("------------------------------------------------------------");
			Logger.log("<DatabaseManager> Attempting to reconnect in 60 seconds!");
			reConnect();
		}
	}
	
	public static void setCreds(DatabaseCreds newCreds){
		creds = newCreds;
	}
	
	public static DatabaseCreds getCreds(){
		return creds;
	}
	
	public static boolean isConnected(){
		return connected;
	}

}