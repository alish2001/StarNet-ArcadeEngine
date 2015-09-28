package com.starnetmc.ArcadeEngine.Managers.Players;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.starnetmc.ArcadeCore;
import com.starnetmc.ArcadeEngine.Events.PlayerStateChangeEvent;
import com.starnetmc.ArcadeEngine.Utils.Logger;

public class PlayerManager implements Listener {

	private HashMap<Player, PlayerState> states;
	
	public PlayerManager(HashMap<Player, PlayerState> states){
		this.states = states;
		Bukkit.getServer().getPluginManager().registerEvents(this, ArcadeCore.getPlugin());
		Logger.log("<PlayerManager> PlayerManager enabled.");
	}
	
	public PlayerManager(){
		this.states = new HashMap<Player, PlayerState>();
		Bukkit.getServer().getPluginManager().registerEvents(this, ArcadeCore.getPlugin());
		for (Player p : Bukkit.getServer().getOnlinePlayers()){
			setPlayerState(p, PlayerState.ALIVE);
		}
		
		Logger.log("<PlayerManager> PlayerManager enabled.");
	}
	
	@EventHandler
	public void managerJoin(PlayerJoinEvent e){
		addPlayer(e.getPlayer());
	}
	
	@EventHandler
	public void managerLeave(PlayerQuitEvent e){
		removePlayer(e.getPlayer());
	}
	
	public void setAllPlayerStates(HashMap<Player, PlayerState> states){
		this.states = states;
	}
	
	public void setPlayerState(Player p, PlayerState state){
		if (!isRegistered(p)){
		    this.states.put(p, state);
		} else {
			states.remove(p);
			states.put(p, state);
		}
		
		Logger.log("<Player Manager> Player State for player: " + p.getName() + " has been changed to " + state.toString());
		Logger.log("Alive players list: " + getAlivePlayers() + " list size=" + getAlivePlayers().size());
		Bukkit.getServer().getPluginManager().callEvent(new PlayerStateChangeEvent(p, state));
	}
	
	public void addPlayer(Player p){
		setPlayerState(p, PlayerState.ALIVE);
		Logger.log("<PlayerManager> Added Player: " + p.getName() + " to PlayerManager.");
	}
	
	public void removePlayer(Player p){
		states.remove(p);
		Logger.log("<PlayerManager> Removed Player: " + p.getName() + " from PlayerManager.");
	}
	
	
	public HashMap<Player, PlayerState> getAllPlayerStates(){
		return states;
	}
	
	public boolean isRegistered(Player p){
		if (states.containsKey(p)){
			return true;
		} else {
			return false;
		}
		
	}
	
	public PlayerState getPlayerState(Player p){
		return states.get(p);
	}
	
	public List<Player> getAlivePlayers(){
		List<Player> alive = new ArrayList<Player>();
		
		for (Player p : Bukkit.getServer().getOnlinePlayers()){
			if (getPlayerState(p) == PlayerState.ALIVE){
				alive.add(p);
			}
		}
		
		return alive;
	}
	
	public boolean isAlive(Player p){
		return getAlivePlayers().contains(p);
	}
	
	public List<Player> getDeadPlayers(){
		List<Player> dead = new ArrayList<Player>();
		
		for (Player p : Bukkit.getServer().getOnlinePlayers()){
			if (getPlayerState(p) == PlayerState.DEAD){
				dead.add(p);
			}
		}
		
		return dead;
	}
	
	public boolean isDead(Player p){
		return getDeadPlayers().contains(p);
	}
	
	public List<Player> getSpecPlayers(){
		List<Player> spec = new ArrayList<Player>();
		
		for (Player p : Bukkit.getServer().getOnlinePlayers()){
			if (getPlayerState(p) == PlayerState.SPECTATOR){
				spec.add(p);
			}
		}
		
		return spec;
	}
	
	public boolean isSpec(Player p){
		return getSpecPlayers().contains(p);
	}
	
	public boolean isDeadOrSpec(Player p){
		return getSpecPlayers().contains(p) || getDeadPlayers().contains(p);
	}
}