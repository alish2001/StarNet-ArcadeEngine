package com.starnetmc.ArcadeEngine.Games;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.starnetmc.ArcadeCore;
import com.starnetmc.ArcadeEngine.ArcadeManager;
import com.starnetmc.ArcadeEngine.Managers.GameState;
import com.starnetmc.ArcadeEngine.Managers.Achievements.Achievement;
import com.starnetmc.ArcadeEngine.Managers.Classes.Kit;
import com.starnetmc.ArcadeEngine.Managers.GameProperties.GameProperties;
import com.starnetmc.ArcadeEngine.Managers.GameProperties.LobbyProperties;
import com.starnetmc.ArcadeEngine.Managers.Players.PlayerState;
import com.starnetmc.ArcadeEngine.Managers.Rewards.Reward;
import com.starnetmc.ArcadeEngine.Managers.Scoreboards.Starboard;
import com.starnetmc.ArcadeEngine.Utils.AF;
import com.starnetmc.ArcadeEngine.Utils.Logger;
import com.starnetmc.ArcadeEngine.Utils.USound;
import com.starnetmc.core.util.F;
import com.starnetmc.core.util.Tickifier;
import com.starnetmc.core.util.Tickifier.Time;
import com.starnetmc.core.util.UPlayer;

public class Game implements Listener {
	
	protected ArcadeManager manager;
	
	private List<Listener> gameListeners;
	private int gameTaskID;
	private List<Kit> gameKits;
	private GameProperties gameProperties;
	private Starboard gameScoreboard;
	private List<Reward> gameRewards;
	private List<Achievement> gameAchievements;
	private ArrayList<String> gameDesc;
	private List<String> gameHints;
	
	public Game(List<Listener> gameListeners, int gameTaskID, List<Kit> gameKits, GameProperties gameProperties, Starboard gameScoreboard, List<Reward> gameRewards, List<Achievement> gameAchievements, ArrayList<String> gameDesc, List<String> gameHints){
		this.manager = ArcadeCore.getArcadeManager();
		this.gameListeners = gameListeners;
		this.gameTaskID = gameTaskID;
		this.gameKits = gameKits;
		this.gameProperties = gameProperties;
		this.gameScoreboard = gameScoreboard;
		this.gameRewards = gameRewards;
		this.gameAchievements = gameAchievements;
		this.gameDesc = gameDesc;
		this.gameHints = gameHints;
	}
	
	public void startUp(){
		
		for (Player p : Bukkit.getServer().getOnlinePlayers()){
			for (ItemStack i : p.getInventory().getContents()){
				if (i != null && i.getType() != Material.AIR && i.hasItemMeta() && ChatColor.stripColor(i.getItemMeta().getDisplayName()).equalsIgnoreCase("Kits")){
					p.getInventory().removeItem(i);
				}
			}
		}
		
		manager.getPropertiesManager().setProperties(gameProperties);
		manager.getPropertiesManager().getProperties().setMap(manager.getMapper().getActiveMap());
		manager.setState(GameState.INGAME);
		manager.getScoreboardManager().setDefaultScoreboard(gameScoreboard);
		registerListeners();
		
		runPlayerSpawnSequence();
		
    	USound.AllPSound(Sound.LEVEL_UP, 1, 1.30f);
		Bukkit.broadcastMessage(AF.boldAqua + ChatColor.STRIKETHROUGH
				+ "=============================================");
		Bukkit.broadcastMessage(ChatColor.AQUA + ">    " + AF.boldYellow + manager.getGame().getName());
		Bukkit.broadcastMessage("   ");
		Bukkit.broadcastMessage("-   " + gameDesc.get(0));
		Bukkit.broadcastMessage("-   " + gameDesc.get(1));
		Bukkit.broadcastMessage("-   " + gameDesc.get(2));
		Bukkit.broadcastMessage("   ");
		Bukkit.broadcastMessage(AF.boldWhite + "Map" + F.boldAqua + " - " + F.boldYellow + manager.getMapper().getActiveMap().getName());
		Bukkit.broadcastMessage(AF.boldWhite + "Created By: " + F.boldYellow + manager.getMapper().getActiveMap().getMaker());
		Bukkit.broadcastMessage(AF.boldAqua + ChatColor.STRIKETHROUGH
				+ "=============================================");
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				start();
			}
		}.runTaskLater(ArcadeCore.getPlugin(), Tickifier.tickify(5, Time.SECONDS));

	}
	
	public void stop(boolean forceStop){
		
		shutdown();
		
		manager.setState(GameState.POSTGAME);
		manager.getPropertiesManager().setProperties(new LobbyProperties());
		
    	for (Player p : Bukkit.getServer().getOnlinePlayers()){
     	   p.setGameMode(GameMode.SPECTATOR);
     	   if (manager.getPlayerManager().isDeadOrSpec(p)){
     		  manager.getPlayerManager().setPlayerState(p, PlayerState.ALIVE);
     	   }
     	   manager.getKitManager().removeKitItems(p);
     	   p.getInventory().clear();
     	}
    	
    	if (forceStop){
    		
    		if (manager.getCounterManager().isGameCountdownInProgress()){
    			manager.getCounterManager().stopGameCountdown();
    		}
    		
    		if (manager.getCounterManager().isPreGameCountdownInProgress()){
    			manager.getCounterManager().stopPreGameCountdown();
    		}
    		
    		AF.announceWinner("No One");
    	}
    	
	     if (gameTaskID != -1){
		       Bukkit.getServer().getScheduler().cancelTask(gameTaskID);
		       Logger.log("<Game> Game task killed.");
		    }
	     
    	manager.getKitManager().resetKits(false);
    	UPlayer.resetPlayers();
    	unRegisterListeners();
    	manager.getStatManager().reset();
    	
    	new BukkitRunnable() {
			
			@Override
			public void run() {
		    	for (Player p : Bukkit.getServer().getOnlinePlayers()){
		    	   p.setGameMode(GameMode.SURVIVAL);
		    	   p.teleport(manager.getMapper().getLobby());
		    	   USound.PSound(p, Sound.LEVEL_UP, 1.25f, 1);
		    	}
				
			}
		}.runTaskLater(ArcadeCore.getPlugin(), Tickifier.tickify(5, Time.SECONDS));
    	
		manager.setState(GameState.LOBBY);
		manager.getMapper().despenseMap();
	}
	
	public void runPlayerSpawnSequence(){
		
		int spawn = 0;
		   for (Player p : manager.getPlayerManager().getAlivePlayers()){
			   p.teleport(manager.getMapper().getActiveMap().getSpawns().get(spawn));
			   
			   spawn++;
			   if (spawn > manager.getMapper().getActiveMap().getSpawns().size()){
				   spawn = 0;
			   }
		   }
	}
	
	public void setListeners(List<Listener> gameListeners){
		this.gameListeners = gameListeners;
	}
	
	public void addListener(Listener listener){
		this.gameListeners.add(listener);
	}
	
	public void setTaskID(int gameTaskID){
		this.gameTaskID = gameTaskID;
	}
	
	public void setKits(List<Kit> gameKits){
		this.gameKits = gameKits;
	}
	
	public void addKit(Kit kit){
		this.gameKits.add(kit);
	}
	
	public void setProperties(GameProperties gameProperties){
		this.gameProperties = gameProperties;
	}
	
	public void setScoreboard(Starboard gameScoreboard){
		this.gameScoreboard = gameScoreboard;
	}
	
	public void setRewards(List<Reward> gameRewards){
		this.gameRewards = gameRewards;
	}
	
	public void addReward(Reward reward){
		this.gameRewards.add(reward);
	}
	
	public void setAchievements(List<Achievement> gameAchievements){
		this.gameAchievements = gameAchievements;
	}
	
	public void addAchievement(Achievement achivement){
		this.gameAchievements.add(achivement);
	}
	
	public void setDesc(ArrayList<String> gameDesc){
		this.gameDesc = gameDesc;
	}
	
	public void addDesc(String desc){
		this.gameDesc.add(desc);
	}
	
	public void setHints(List<String> gameHints){
		this.gameHints = gameHints;
	}
	
	public void addHint(String hint){
		this.gameHints.add(hint);
	}
	
	public List<Listener> getListeners(){
		return this.gameListeners;
	}
	
	public void registerListeners(){
		for (Listener l : gameListeners){
			Bukkit.getServer().getPluginManager().registerEvents(l, ArcadeCore.getPlugin());
		}
	}
	
	public void unRegisterListeners(){
		for (Listener l : gameListeners){
			HandlerList.unregisterAll(l);
		}
	}
	
	public int getTaskID(){
		return gameTaskID;
	}
	
	public List<Kit> getKits(){
		return gameKits;
	}
	
	public void registerKits(){
		for (Kit k : gameKits){
			k.setup();
		}
	}
	
    public Kit getDefaultKit(){
    	return gameKits.get(0);
    }
	
	public GameProperties getProperties(){
		return gameProperties;
	}
	
	public Starboard getScoreboard(){
		return gameScoreboard;
	}
	
	public List<Reward> getRewards(){
		return gameRewards;
	}
	
	public List<Achievement> getAchievements(){
		return gameAchievements;
	}
	
	public ArrayList<String> getDesc(){
		return gameDesc;
	}
	
	public List<String> getHints(){
		return gameHints;
	}

	public void start() {
		
	}
	
	public void shutdown(){
		
	}
}