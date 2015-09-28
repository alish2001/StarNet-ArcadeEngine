package com.starnetmc.ArcadeEngine.Managers.GameProperties;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.starnetmc.ArcadeCore;
import com.starnetmc.ArcadeEngine.ArcadeManager;
import com.starnetmc.ArcadeEngine.Managers.GameState;
import com.starnetmc.ArcadeEngine.Managers.Players.PlayerState;
import com.starnetmc.ArcadeEngine.Utils.AF;
import com.starnetmc.ArcadeEngine.Utils.Logger;
import com.starnetmc.core.accounts.AccountManager;
import com.starnetmc.core.util.Rank;

public class LobbyManager implements Listener {
	
	private ArcadeManager manager;
    private GameProperties properties;
	
    public LobbyManager(ArcadeManager manager, GameProperties properties){
		this.manager = manager;
		this.properties = properties;
	}
    
	public void register(){
		Bukkit.getServer().getPluginManager().registerEvents(this, ArcadeCore.getPlugin());
		Logger.log("<Lobby Manager> An instance of a GameManager has been instantiated and registered.");
	}
	
	public void unRegister(){
		HandlerList.unregisterAll(this);
		Logger.log("<Lobby Manager> An instance of a GameManager has been unregistered.");
	}
	
	public void setProperties(GameProperties newProperties){
		Logger.log("<Lobby Manager> Changing lobby properties...");
		//unRegister();
		this.properties = newProperties;
		//register();
		Logger.log("<Lobby Manager> Lobby properties changed.");
	}
	
	public GameProperties getProperties(){
		return properties;
	}
	
	@EventHandler
	public void joinStart(PlayerJoinEvent e){
		
		if (manager.isState(GameState.INGAME) || manager.isState(GameState.LOADING)) return;
		if (Bukkit.getServer().getOnlinePlayers().size() >= properties.getPlayersNeeded()){
			manager.getCounterManager().startGameCountdown();
		} else { return; }
	}
	
	@EventHandler
	public void quitStop(PlayerQuitEvent e){
		
		if (!manager.isState(GameState.INGAME)) return;
		if (Bukkit.getServer().getOnlinePlayers().size() <= 1){
			manager.getGame().getGClass().stop(true);
			return;
		  }
		
		if (Bukkit.getServer().getOnlinePlayers().size() < properties.getPlayersNeeded()){
			manager.getGame().getGClass().stop(true);
			return;
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void joiner(PlayerJoinEvent e){
		
		manager.getPlayerManager().setPlayerState(e.getPlayer(), PlayerState.ALIVE);
		e.getPlayer().teleport(manager.getMapper().getLobby());
		
		if (manager.getState() != GameState.INGAME) return;
		e.getPlayer().setGameMode(GameMode.SPECTATOR);
		manager.getPlayerManager().setPlayerState(e.getPlayer(), PlayerState.SPECTATOR);
		e.getPlayer().teleport(properties.getMap().getSpecSpawn());
	}
	
	@EventHandler
	public void joinPermit(PlayerJoinEvent e){
		if (!(manager.getPlayerManager().getAlivePlayers().size() >= properties.getMaxPlayers())) return;
		Player p = e.getPlayer();
		if (AccountManager.getAccount(p).getRank() != Rank.DEFAULT) return;
		p.kickPlayer(AF.boldAqua + "<StarNet> " + AF.boldRed + "This server has reached Max capacity. Purchase/Earn a Rank to bypass this.");
	}
	
}