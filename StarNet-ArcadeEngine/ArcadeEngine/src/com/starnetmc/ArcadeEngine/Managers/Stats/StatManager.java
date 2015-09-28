package com.starnetmc.ArcadeEngine.Managers.Stats;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.starnetmc.ArcadeCore;
import com.starnetmc.ArcadeEngine.Events.CustomPlayerDeathEvent;
import com.starnetmc.ArcadeEngine.Utils.Logger;
import com.starnetmc.core.util.StarMap;

public class StatManager implements Listener {
	
	private StarMap<String, Integer> kills;
	private StarMap<String, Integer> deaths;
	
	public StatManager(StarMap<String, Integer> kills, StarMap<String, Integer> deaths){
		this.kills = kills;
		this.deaths = deaths;
		Bukkit.getServer().getPluginManager().registerEvents(this, ArcadeCore.getPlugin());
		Logger.log("<StatManager> StatManager Enabled.");
	}
	
	public StatManager(){
		this.kills = new StarMap<String, Integer>();
		this.deaths = new StarMap<String, Integer>();
		Bukkit.getServer().getPluginManager().registerEvents(this, ArcadeCore.getPlugin());
		Logger.log("<StatManager> StatManager Enabled.");
	}
	
	@EventHandler
	public void joinStat(PlayerJoinEvent e){
		Player p = e.getPlayer();
		addPlayer(p);
	}
	
	@EventHandler
	public void quitUnStat(PlayerQuitEvent e){
		Player p = e.getPlayer();
		removePlayer(p);
	}
	
	@EventHandler
	public void onSlaughter(CustomPlayerDeathEvent e){
		Player killed = e.getKilled();		
		addDeath(killed);
		if (!e.entityIsPlayer()) return;
		Player killer = (Player) e.getKiller();
		addKill(killer);
	}
	
	public void reset(){
		this.kills.clear();
		this.deaths .clear();
		
		for (Player p : Bukkit.getServer().getOnlinePlayers()){
			 addPlayer(p);
		}
		
		Logger.log("<StatManager> Stats Reset");
	}
	
	public void setKillsMap(StarMap<String, Integer> kills){
		this.kills = kills;	
	}
	
	public void setKills(Player p, int kills){
		if (this.kills.containsKey(p.getName())) this.kills.remove(p.getName());
		this.kills.put(p.getName(), kills);
	}
	
	public void addKill(Player p){
		if (!this.kills.containsKey(p.getName())) setKills(p, 0);
		this.kills.put(p.getName(), getKills(p) + 1);
	}
	
	public void setDeathsMap(StarMap<String, Integer> deaths){
		this.deaths = deaths;	
	}
	
	public void setDeaths(Player p, int deaths){
		if (this.deaths.containsKey(p.getName())) this.deaths.remove(p.getName());
		this.deaths.put(p.getName(), deaths);
	}
	
	public void addDeath(Player p){
		if (!this.deaths.containsKey(p.getName())) setDeaths(p, 0);
		this.deaths.put(p.getName(), getDeaths(p) + 1);
	}
	
	public void addPlayer(Player p){
		setKills(p, 0);
		setDeaths(p, 0);
		Logger.log("<StatManager> Added a player for StatTraking by the name: " + p.getName());
	}
	
	public void removePlayer(Player p){
		this.kills.remove(p.getName());
		this.deaths.remove(p.getName());
		Logger.log("<StatManager> Removed a player from StatTraking by the name: " + p.getName());
	}
	
	public StarMap<String, Integer> getAllKills(){
		return kills;
	}
	
	public StarMap<String, Integer> getAllDeaths(){
		return deaths;
	}
	
	public int getKills(Player p){
		return kills.get(p.getName());
	}
	
	public int getDeaths(Player p){
		return deaths.get(p.getName());
	}
	
	public double getKDR(Player p){
		int kills = getKills(p);
		int deaths = getDeaths(p);
		
		if (deaths == 0){
			deaths++;
		}
		
		double kdr = kills/deaths;
		return kdr;
	}

}