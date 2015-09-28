package com.starnetmc.ArcadeEngine.Managers.GameData;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerListPingEvent;

import com.starnetmc.ArcadeCore;
import com.starnetmc.ArcadeEngine.ArcadeManager;
import com.starnetmc.ArcadeEngine.Events.GameStateChangeEvent;
import com.starnetmc.ArcadeEngine.Events.GameTypeChangeEvent;
import com.starnetmc.ArcadeEngine.Events.MapChangeEvent;
import com.starnetmc.ArcadeEngine.Games.GameType;
import com.starnetmc.ArcadeEngine.Managers.GameState;
import com.starnetmc.ArcadeEngine.Managers.Maps.MapTypes.Map;
import com.starnetmc.ArcadeEngine.Utils.AF;

public class GameDataManager implements Listener {
	
	private ArcadeManager manager;
	
	private String motd;
	
	public GameDataManager(ArcadeManager manager, String motd){
		this.manager = manager;
		this.motd = motd;
		Bukkit.getServer().getPluginManager().registerEvents(this, ArcadeCore.getPlugin());
	}
	
	public GameDataManager(ArcadeManager manager){
		this.manager = manager;
		setMOTD(manager);
		Bukkit.getServer().getPluginManager().registerEvents(this, ArcadeCore.getPlugin());
	}
	
	@EventHandler
	public void onInfoRequest(ServerListPingEvent e){
		e.setMotd(motd);
	}
	
	@EventHandler
	public void onDataChange(GameTypeChangeEvent e){
		setMOTD(manager);
	}
	
	@EventHandler
	public void onDataChange(GameStateChangeEvent e){
		setMOTD(manager);
	}
	
	@EventHandler
	public void onDataChange(MapChangeEvent e){
		setMOTD(manager);
	}
	
	@EventHandler
	public void onDataChange(PlayerJoinEvent e){
		setMOTD(manager);
	}
	
	@EventHandler
	public void onDataChange(PlayerQuitEvent e){
		setMOTD(manager);
	}
	
	public void setMOTD(ArcadeManager manager){
		setMOTD(manager.getGame(), manager.getState(), manager.getMapper().getActiveMap(), manager.getPropertiesManager().getProperties().getMaxPlayers());
	}
	
	public void setMOTD(GameType type, GameState state, Map map, int maxPlayers){
		setMOTD(AF.yellow + "GAME|" + type.toString() + "|" + state.getStatus(true)  + "|" + map.getName() + "|" + maxPlayers);
	}
	
	public void setMOTD(String motd){
		this.motd = motd;
	}
	
	public String getMOTD(){
		return motd;
	}
}