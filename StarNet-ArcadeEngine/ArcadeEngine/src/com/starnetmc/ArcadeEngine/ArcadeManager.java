package com.starnetmc.ArcadeEngine;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.starnetmc.ArcadeCore;
import com.starnetmc.ArcadeEngine.Events.GameStateChangeEvent;
import com.starnetmc.ArcadeEngine.Events.GameTypeChangeEvent;
import com.starnetmc.ArcadeEngine.Events.ScoreboardUpdateEvent;
import com.starnetmc.ArcadeEngine.Games.GameType;
import com.starnetmc.ArcadeEngine.Managers.GameState;
import com.starnetmc.ArcadeEngine.Managers.Achievements.AchievementManager;
import com.starnetmc.ArcadeEngine.Managers.Classes.Kit;
import com.starnetmc.ArcadeEngine.Managers.Classes.KitManager;
import com.starnetmc.ArcadeEngine.Managers.Counters.CounterManager;
import com.starnetmc.ArcadeEngine.Managers.GameData.GameDataManager;
import com.starnetmc.ArcadeEngine.Managers.GameProperties.GameProperties;
import com.starnetmc.ArcadeEngine.Managers.GameProperties.GamePropertiesManager;
import com.starnetmc.ArcadeEngine.Managers.GameProperties.LobbyManager;
import com.starnetmc.ArcadeEngine.Managers.GameProperties.LobbyProperties;
import com.starnetmc.ArcadeEngine.Managers.GameRotations.GameRotator;
import com.starnetmc.ArcadeEngine.Managers.Maps.Mapper;
import com.starnetmc.ArcadeEngine.Managers.Players.PlayerManager;
import com.starnetmc.ArcadeEngine.Managers.Rewards.RewardManager;
import com.starnetmc.ArcadeEngine.Managers.Scoreboards.ScoreboardManager;
import com.starnetmc.ArcadeEngine.Managers.Stats.StatManager;
import com.starnetmc.ArcadeEngine.Utils.Logger;
import com.starnetmc.ArcadeEngine.Utils.USound;
import com.starnetmc.ArcadeEngine.Utils.UTeleport;

public class ArcadeManager {

	private GameType currentGame;
	private GameState currentState;
	private Mapper mapManager;
	private GamePropertiesManager propertiesManager;
	private LobbyManager lobbyManager;
	private GameRotator gameRotationManager;
	private CounterManager counterManager;
	private KitManager kitManager;
	private PlayerManager playerManager;
	private ScoreboardManager scoreboardManager;
	private StatManager statManager;
	private RewardManager rewardManager;
	private AchievementManager achivementManager;
	private GameDataManager dataManager;
	
	public ArcadeManager(GameType currentGame, GameState currentState, Mapper mapManager, GamePropertiesManager propertiesManager, LobbyManager lobbyManager, GameRotator gameRotationManager, CounterManager counterManager, KitManager kitManager, PlayerManager playerManager, ScoreboardManager scoreboardManager, StatManager statManager, RewardManager rewardManager, AchievementManager achivementManager, GameDataManager dataManager){
		
		this.currentGame = currentGame;
		this.currentState = currentState;
		this.mapManager = mapManager;
		this.propertiesManager = propertiesManager;
		this.lobbyManager = lobbyManager;
		this.gameRotationManager = gameRotationManager;
		this.counterManager = counterManager;
		this.kitManager = kitManager;
		this.playerManager = playerManager;
		this.scoreboardManager = scoreboardManager;
		this.statManager = statManager;
		this.rewardManager = rewardManager;
		this.achivementManager = achivementManager;
		this.dataManager = dataManager;
	}
	
	public ArcadeManager(){
		
	}
	
	public void init(){
		setMapper(new Mapper(this));
		setGame(ArcadeCore.getConfigFile().getString("DefaultGame") != null ? GameType.DRAGONSWORD.getGameTypeFromVarName(ArcadeCore.getConfigFile().getString("DefaultGame")) : GameType.DRAGONSWORD);
		setLobbyManager(currentGame.getGClass().getProperties());
		setGameRotator(new GameRotator(this));
		setState(GameState.LOBBY);
		setPropertiesManager(new LobbyProperties());
		setCounterManager(new CounterManager(30, 5));
		setKitManager(new KitManager(this));
		setPlayerManager(new PlayerManager());
		lobbyManager.register();
		setScoreboardManager(new ScoreboardManager(this));
		setStatManager(new StatManager());
		setRewardManager(new RewardManager(this));
		setAchivementManager(new AchievementManager(this));
		setDataManager(new GameDataManager(this));
	}
	
	public void startGame(){
		counterManager.startGameCountdown();
	}
	
	public void stopGame(boolean forceStop){
		if (isState(GameState.INGAME)){
		currentGame.getGClass().stop(forceStop);
		
		} else {
			
    		if (ArcadeCore.getArcadeManager().getCounterManager().isGameCountdownInProgress()){
    			ArcadeCore.getArcadeManager().getCounterManager().stopGameCountdown();
    		}
    		
    		if (ArcadeCore.getArcadeManager().getCounterManager().isPreGameCountdownInProgress()){
    			ArcadeCore.getArcadeManager().getCounterManager().stopPreGameCountdown();
    		}
    		
    		UTeleport.TpAll2Loc(mapManager.getLobby());
    		setState(GameState.LOBBY);
		}
	}
	
	public void setGame(GameType game){
		
	    if (this.currentGame != null){
	    	this.kitManager.resetKits(false);
	    	
	    	for (Kit k : currentGame.getGClass().getKits()){
	    	     k.unRegister();
	    	}
	    	
	    	this.propertiesManager.setProperties(new LobbyProperties());
	    	this.lobbyManager.setProperties(game.getGClass().getProperties());
	    	this.statManager.reset();
	    }
	    
		this.currentGame = game;
		this.mapManager.despenseMap();
		this.currentGame.getGClass().registerKits();
		setState(GameState.LOBBY);

		USound.AllPSound(Sound.NOTE_PLING, 1, 1.5F);
		UTeleport.TpAll2Loc(mapManager.getLobby());
		
		for (Player p : Bukkit.getServer().getOnlinePlayers()){
			kitManager.setKit(p, currentGame.getGClass().getDefaultKit(), false);
			Bukkit.getServer().getPluginManager().callEvent(new ScoreboardUpdateEvent(p));
		}
		
		Bukkit.getServer().getPluginManager().callEvent(new GameTypeChangeEvent(currentGame));
	}
	
	public void setState(GameState state){
		this.currentState = state;
		Logger.log("GameState changed to " + state);
		Bukkit.getServer().getPluginManager().callEvent(new GameStateChangeEvent(currentState));
		
		for (Player p : Bukkit.getServer().getOnlinePlayers()){
		   Bukkit.getServer().getPluginManager().callEvent(new ScoreboardUpdateEvent(p));
	    }
	}
	
	public void setMapper(Mapper mapManager){
		this.mapManager = mapManager;
	}
	
	public void setPropertiesManager(GamePropertiesManager propertiesManager){
		this.propertiesManager = propertiesManager;
	}
	
	public void setPropertiesManager(GameProperties propertiesManager){
		this.propertiesManager = new GamePropertiesManager(this, propertiesManager);
	}
	
	public void setLobbyManager(LobbyManager lobbyManager){
		this.lobbyManager = lobbyManager;
	}
	
	public void setLobbyManager(GameProperties lobbyManager){
		this.lobbyManager = new LobbyManager(this, lobbyManager);
	}
	
	public void setGameRotator(GameRotator gameRotator){
		this.gameRotationManager = gameRotator;
	}
	
	public void setCounterManager(CounterManager counterManager){
		this.counterManager = counterManager;
	}
	
	public void setKitManager(KitManager kitManager){
		this.kitManager = kitManager;
	}
	
	public void setPlayerManager(PlayerManager playerManager){
		this.playerManager = playerManager;
	}
	
	public void setScoreboardManager(ScoreboardManager scoreboardManager){
		this.scoreboardManager = scoreboardManager;
	}
	
	public void setStatManager(StatManager statManager){
		this.statManager = statManager;
	}
	
	public void setRewardManager(RewardManager rewardManager){
		this.rewardManager = rewardManager;
	}
	
	public void setAchivementManager(AchievementManager achivementManager){
		this.achivementManager = achivementManager;
	}
	
	public void setDataManager(GameDataManager dataManager){
		this.dataManager = dataManager;
	}
	
	public GameType getGame(){
		return currentGame;
	}
	
	public boolean isGame(GameType game){
		if (this.currentGame == game){
			return true;
		} else {
			return false;
		}
	}
	
	public GameState getState(){
		return currentState;
	}
	
	public boolean isState(GameState state){
		return currentState == state;
	}
	
	public Mapper getMapper(){
	    return mapManager;
	}
	
	public GamePropertiesManager getPropertiesManager(){
		return propertiesManager;
	}
	
	public LobbyManager getLobbyManager(){
		return lobbyManager;
	}
	
	public GameRotator getGameRotator(){
		return gameRotationManager;
	}
	
	public CounterManager getCounterManager(){
		return counterManager;
	}
	
	public KitManager getKitManager(){
		return kitManager;
	}
	
	public PlayerManager getPlayerManager(){
		return playerManager;
	}
	
	public ScoreboardManager getScoreboardManager(){
		return scoreboardManager;
	}
	
	public StatManager getStatManager(){
		return statManager;
	}
	
	public RewardManager getRewardManager(){
		return rewardManager;
	}
	
	public AchievementManager getAchivementManager(){
		return achivementManager;
	}
	
	public GameDataManager getDataManager(){
		return dataManager;
	}
	
}