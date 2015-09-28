package com.starnetmc.ArcadeEngine.Managers.Classes;

import java.util.HashMap;
import java.util.Set;

import org.bukkit.entity.Player;
 
public class CooldownManager {
 
	private long time;
	private HashMap<String, Long> players;
	
	public CooldownManager(int time, Time type) {
		if(type == Time.SECONDS)
			this.time = time * 1000;
		else
			this.time = time * 1000 * type.getValue();
		this.players = new HashMap<String, Long>();
	}
	
	public CooldownManager(long miliseconds) {
		this.time = miliseconds;
		this.players = new HashMap<String, Long>();
	}
	
	public static enum Time {
		
		SECONDS(0),
		MINUTES(60),
		HOURS(3600),
		DAYS(86400);
		
		private int value;
		
		Time(int value) {
			this.value = value;
		}
		
		public int getValue() {
			return value;
		}
		
	}
	
	public boolean contains(Player player) {
		return contains(player.getName());
	}
	
	public boolean contains(String name) {
		return players.containsKey(name);
	}
	
	public void remove(Player player) {
		remove(player.getName());
	}
	
	public void remove(String name) {
		players.remove(name);
	}
	
	public boolean startCooldown(Player player) {
		long currentTime = System.currentTimeMillis();
		
		if(players.containsKey(player.getName())) {
			if((players.get(player.getName()) + time) > currentTime) return false;
		}
		
		players.put(player.getName(), currentTime);
		
		return true;
	}
	
	public boolean isAllowed(Player player) {
		long currentTime = System.currentTimeMillis();
		
		if(players.containsKey(player.getName())) {
			if((players.get(player.getName()) + time) > currentTime) return false;
		}
		return true;
	}
	
	public double getTimeLeft(Player player, Time type) {
		if(type == Time.SECONDS) {
			return Math.round((double)(((players.get(player.getName()) + time) - System.currentTimeMillis())/1000));
		}
		
		return (int)(((players.get(player.getName()) + time) - System.currentTimeMillis())/1000/type.getValue());
	}
	
	public long getRawTimeLeft(Player player) {
		return (((players.get(player.getName()) + time) - System.currentTimeMillis()));
	}
	
	public Set<String> getList() {
		return players.keySet();
	}
	
}