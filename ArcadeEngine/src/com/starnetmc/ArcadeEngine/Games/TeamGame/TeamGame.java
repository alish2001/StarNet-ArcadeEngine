package com.starnetmc.ArcadeEngine.Games.TeamGame;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import com.starnetmc.ArcadeCore;
import com.starnetmc.ArcadeEngine.Games.Game;
import com.starnetmc.ArcadeEngine.Managers.GameState;
import com.starnetmc.ArcadeEngine.Managers.Achievements.Achievement;
import com.starnetmc.ArcadeEngine.Managers.Classes.Kit;
import com.starnetmc.ArcadeEngine.Managers.GameProperties.GameProperties;
import com.starnetmc.ArcadeEngine.Managers.GameProperties.LobbyProperties;
import com.starnetmc.ArcadeEngine.Managers.Maps.MapTypes.TeamMaps.FourTeamMap;
import com.starnetmc.ArcadeEngine.Managers.Maps.MapTypes.TeamMaps.TwoTeamMap;
import com.starnetmc.ArcadeEngine.Managers.Players.PlayerState;
import com.starnetmc.ArcadeEngine.Managers.Rewards.Reward;
import com.starnetmc.ArcadeEngine.Managers.Scoreboards.Starboard;
import com.starnetmc.ArcadeEngine.Utils.AF;
import com.starnetmc.ArcadeEngine.Utils.Logger;
import com.starnetmc.ArcadeEngine.Utils.USound;
import com.starnetmc.core.util.Tickifier;
import com.starnetmc.core.util.UPlayer;
import com.starnetmc.core.util.Tickifier.Time;

public class TeamGame extends Game {
	
	private TeamManager gameTeamManager;
	
	public TeamGame(List<Listener> gameListeners, int gameTaskID, List<Kit> gameKits, GameProperties gameProperties, Starboard gameScoreboard, List<Reward> gameRewards, List<Achievement> gameAchivements, ArrayList<String> gameDesc, List<String> gameHints, TeamManager gameTeamManager) {
		super(gameListeners, gameTaskID, gameKits, gameProperties, gameScoreboard, gameRewards, gameAchivements, gameDesc, gameHints);
	    
		this.gameTeamManager = gameTeamManager;
	}
	
	@Override
	public void runPlayerSpawnSequence(){
		
		if (gameTeamManager.getAllTeams().size() == 2){
			
			int spawn = 0;
			   for (Player p : gameTeamManager.getAllTeams().get(0).getTeamPlayers()){
				  p.teleport(((TwoTeamMap) ArcadeCore.getArcadeManager().getMapper().getActiveMap()).getFTSpawns().get(spawn));
				   
				   spawn++;
				   if (spawn > ((TwoTeamMap) ArcadeCore.getArcadeManager().getMapper().getActiveMap()).getFTSpawns().size()){
					   spawn = 0;
				   }
			   }
			   
				spawn = 0;
				   for (Player p : gameTeamManager.getAllTeams().get(1).getTeamPlayers()){
					  p.teleport(((TwoTeamMap) ArcadeCore.getArcadeManager().getMapper().getActiveMap()).getSTSpawns().get(spawn));
					   
					   spawn++;
					   if (spawn > ((TwoTeamMap) ArcadeCore.getArcadeManager().getMapper().getActiveMap()).getSTSpawns().size()){
						   spawn = 0;
					   }
				   }
		}
		
		if (gameTeamManager.getAllTeams().size() == 4){
			
			int spawn = 0;
			   for (Player p : gameTeamManager.getAllTeams().get(0).getTeamPlayers()){
				  p.teleport(((FourTeamMap) ArcadeCore.getArcadeManager().getMapper().getActiveMap()).getFTSpawns().get(spawn));
				   
				   spawn++;
				   if (spawn > ((FourTeamMap) ArcadeCore.getArcadeManager().getMapper().getActiveMap()).getFTSpawns().size()){
					   spawn = 0;
				   }
			   }
			   
				spawn = 0;
				   for (Player p : gameTeamManager.getAllTeams().get(1).getTeamPlayers()){
					  p.teleport(((FourTeamMap) ArcadeCore.getArcadeManager().getMapper().getActiveMap()).getSTSpawns().get(spawn));
					   
					   spawn++;
					   if (spawn > ((FourTeamMap) ArcadeCore.getArcadeManager().getMapper().getActiveMap()).getSTSpawns().size()){
						   spawn = 0;
					   }
				   }
				   
				    spawn = 0;
					   for (Player p : gameTeamManager.getAllTeams().get(2).getTeamPlayers()){
						  p.teleport(((FourTeamMap) ArcadeCore.getArcadeManager().getMapper().getActiveMap()).getTTSpawns().get(spawn));
						   
						   spawn++;
						   if (spawn > ((FourTeamMap) ArcadeCore.getArcadeManager().getMapper().getActiveMap()).getTTSpawns().size()){
							   spawn = 0;
						   }
					   }
					   
						spawn = 0;
						   for (Player p : gameTeamManager.getAllTeams().get(3).getTeamPlayers()){
							  p.teleport(((FourTeamMap) ArcadeCore.getArcadeManager().getMapper().getActiveMap()).getFrTSpawns().get(spawn));
							   
							   spawn++;
							   if (spawn > ((FourTeamMap) ArcadeCore.getArcadeManager().getMapper().getActiveMap()).getFrTSpawns().size()){
								   spawn = 0;
							   }
						   }
		}
	}
	
	@Override
	public void stop(boolean forceStop){
		
		shutdown();
		
		gameTeamManager.unRegister();
		
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
    	
	     if (getTaskID() != -1){
		       Bukkit.getServer().getScheduler().cancelTask(getTaskID());
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
	
	public void setTeamManager(TeamManager gameTeamManager){
		this.gameTeamManager = gameTeamManager;
	}
	
	public TeamManager getTeamManager(){
		return gameTeamManager;
	}
}