package com.starnetmc.core.accounts;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.starnetmc.ArcadeCore;
import com.starnetmc.core.database.DatabaseManager;
import com.starnetmc.core.database.Databaser;
import com.starnetmc.core.database.OfflineCacher;
import com.starnetmc.core.modules.manager.Module;
import com.starnetmc.core.modules.manager.ModuleType;
import com.starnetmc.core.util.F;
import com.starnetmc.core.util.Logger;
import com.starnetmc.core.util.Rank;
import com.starnetmc.core.util.StarMap;

public class AccountManager extends Module {
	
	public static StarMap<String, Account> accounts = new StarMap<String, Account>();
	public static List<String> staff = new ArrayList<String>();
	
	public static OfflineCacher accountCache = new OfflineCacher(ArcadeCore.getPlugin());
	
	public AccountManager(JavaPlugin plugin) {
		super("Account Manager", 1.0, ModuleType.SERVER, plugin);
	}
	

	public AccountManager() {
	}
	
	@Override
	public void enable() {
		accountCache.register();
		isEnabled = true;
		log("enabled.");
	}

	@Override
	public void disable() {
		accountCache.unRegister();
		isEnabled = false;
		log("disabled.");

	}

	public static boolean isEnabled;
	
	@EventHandler
	public void onPlayerLogin(PlayerJoinEvent e){
		Player p = e.getPlayer();
		
		if (DatabaseManager.isConnected()){
		try {
			if (!Databaser.hasAccount(p.getUniqueId().toString())) {
				Databaser.createAccount(p.getUniqueId().toString(), p.getName());
			}
			
			accounts.put(p.getUniqueId().toString(), new Account(p.getUniqueId().toString(), Databaser.getRank(p.getUniqueId().toString()), Databaser.getShards(p.getUniqueId().toString())));
			Databaser.setLastLogin(p.getUniqueId().toString());
			
			if (Rank.getStaffRanks().contains(getAccount(p).getRank())){
				staff.add(p.getName());
			}
			
			Logger.log("<AccountManager> Account added for player: " + p.getName() + " with UUID: " + p.getUniqueId().toString());
			Logger.log("<AccountManager> Rank for " + p.getName() + " = " + getAccount(p).getRank());
			Logger.log("<AccountManager> Shards for " + p.getName() + " = " + getAccount(p).getShards());
	   	 } catch (Exception ex) {
			ex.printStackTrace();
	 	  }
		
		} else {
			
			Logger.log("<Account Manager> Couldn't fetch account info for player: " + p.getName());
			
			if (accounts.containsKey(p.getUniqueId().toString())){
				Logger.log("<Account Manager> Player: " + p.getName() + " logged in with cached info.");
				return;
			}
			
			if (p.getUniqueId().toString().equals("f5738c50-ca61-44d7-af98-6b188e6285a1")){
				accounts.put(p.getUniqueId().toString(), new Account(p.getUniqueId().toString(), Rank.OWNER, -9000000));
				p.sendMessage(F.star("Hey boss, I uh...I'm currently having trouble getting player info from the database, even yours!"));
				return;
			}
			
			accounts.put(p.getUniqueId().toString(), new Account(p.getUniqueId().toString(), Rank.DEFAULT, -9000000));
			p.sendMessage(F.star("We weren't able to reach our database for your account info at this time, sorry for the inconvenience."));
		}
	}
	
	@EventHandler
	public void onPlayerLogout(PlayerQuitEvent e){
		Player p  = e.getPlayer();
		
		if (DatabaseManager.isConnected()){
		try {
			getAccount(p).update();
			
			if (Rank.getStaffRanks().contains(getAccount(p).getRank())){
				staff.remove(p.getName());
			}
			
			accounts.remove(p.getUniqueId().toString());
		 } catch (Exception ex) {
			ex.printStackTrace();
		 }
	   } else {
		   if (getAccount(p).getShards() > -1){
		     Logger.log("<Account Manager> Player: " + p.getName() + " has logged out and their info has been OfflineCached due to database issues.");
		     return;
		   }
		   
		   accounts.remove(p.getUniqueId().toString());
		   return;
	   }
	}
	
	public static Account getAccount(Player p){
		return accounts.get(p.getUniqueId().toString());
	}
	
	public static StarMap<String, Account> getAccounts(){
		return accounts;
	}
	
	public static List<String> getStaff(){
		return staff;
	}
	
}