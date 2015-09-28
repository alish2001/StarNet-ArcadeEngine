package com.starnetmc.core.database;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.starnetmc.core.accounts.Account;
import com.starnetmc.core.accounts.AccountManager;
import com.starnetmc.core.accounts.SimpleAccount;
import com.starnetmc.core.accounts.UAccount;
import com.starnetmc.core.events.DatabaseConnectionStateChangeEvent;
import com.starnetmc.core.events.UpdateEvent;
import com.starnetmc.core.util.Logger;
import com.starnetmc.core.util.Rank;
import com.starnetmc.core.util.StarMap;
import com.starnetmc.core.util.UpdateType;

public class OfflineCacher implements Listener {
	
	private JavaPlugin plugin;
	private ArrayList<String> accountData;
	
	public OfflineCacher(JavaPlugin p){
		this.plugin = p;
		this.accountData = new ArrayList<String>();
	}
	
	public OfflineCacher(JavaPlugin p, StarMap<String, Account> accounts){
		this.plugin = p;
		this.accountData = new ArrayList<String>();
		cacheAccounts(accounts);
	}
	
	public void register(){
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	public void unRegister(){
		HandlerList.unregisterAll(this);
	}
	
	public void updateCache(){
		for (Account a : AccountManager.getAccounts().values()){
			if (!accountData.contains(serializeAccountData(UAccount.getSimpleAccountFromAccount(a)))){
				accountData.add(serializeAccountData(UAccount.getSimpleAccountFromAccount(a)));
			}
		}
		//filterPropAccounts();
	}
	
	public void cache(Account a){
		SimpleAccount sa = UAccount.getSimpleAccountFromAccount(a);
		accountData.add(serializeAccountData(sa));
		Logger.log("<OfflineCacher> Caching account for Offline usage for player UUID: " + sa.getUUID());
	}
	
	public void cache(SimpleAccount a){
		accountData.add(serializeAccountData(a));
		Logger.log("<OfflineCacher> Caching account for Offline usage for player UUID: " + a.getUUID());
	}
	
	public void cacheAccounts(ArrayList<Account> accounts){
		for (Account a : accounts){
			cache(a);
		}
	}
	
	public void cacheAccounts(StarMap<String, Account> accounts){
		for (Account a : accounts.values()){
			cache(a);
		}
	}
	
	public void filterPropAccounts(){
		for (String data : accountData){
			   if (deSerializeAccountData(data).getShards() < -1){
				   String uuid = deSerializeAccountData(data).getUUID();
				   accountData.remove(data);
				   Logger.log("<OfflineCacher> Prop Account with UUID: " + uuid + " has been filtered from the OfflineCache.");
			   }
		}
		
	}
	
	public void pushDBUpdate(){
		filterPropAccounts();
		
		for (String data : accountData){
			 deSerializeAccountData(data).update();
			 accountData.remove(data);
		}
		Logger.log("<OfflineCacher> Database updates has been pushed.");
	}
	
	@EventHandler
	public void onDatabaseConnect(DatabaseConnectionStateChangeEvent e){
		if (!e.isConnected()) return;
		if (accountData.isEmpty()) return;
		pushDBUpdate();
	}
	
	@EventHandler
	public void onDatabaseConnectionLoss(DatabaseConnectionStateChangeEvent e){
		if (e.isConnected()) return;
		if (accountData.isEmpty()){
		    cacheAccounts(AccountManager.getAccounts());
		} else {
			updateCache();
		}
	}
	
	@EventHandler
	public void cacheRefresher(UpdateEvent e){
		if (e.getType() != UpdateType.MINUTE) return;
		if (DatabaseManager.isConnected()) return;
		updateCache();
	}
	
	private String serializeAccountData(SimpleAccount account){
		StringBuilder serializer = new StringBuilder();
		serializer.append(account.getUUID());
		serializer.append("|");
		serializer.append(account.getRank());
		serializer.append("|");
		serializer.append(account.getShards());
		
		String serializedData = serializer.toString();
		return serializedData;
	}
	
	public SimpleAccount deSerializeAccountData(String accountData){
		String[] data = accountData.split("\\|");
		String uuid = data[0];
		Rank rank = Rank.getRankFromString(data[1]);
		int shards = Integer.parseInt(data[2]);
		
		SimpleAccount forgedAccount = new SimpleAccount(uuid, rank, shards);
		return forgedAccount;
	}

}