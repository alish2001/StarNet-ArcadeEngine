package com.starnetmc.ArcadeEngine.Games.TeamGame;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import com.starnetmc.ArcadeCore;
import com.starnetmc.ArcadeEngine.Games.Game;
import com.starnetmc.ArcadeEngine.Managers.Achievements.Achievement;
import com.starnetmc.ArcadeEngine.Managers.Classes.Kit;
import com.starnetmc.ArcadeEngine.Managers.GameProperties.GameProperties;
import com.starnetmc.ArcadeEngine.Managers.Maps.MapTypes.TeamMaps.FourTeamMap;
import com.starnetmc.ArcadeEngine.Managers.Maps.MapTypes.TeamMaps.TwoTeamMap;
import com.starnetmc.ArcadeEngine.Managers.Rewards.Reward;
import com.starnetmc.ArcadeEngine.Managers.Scoreboards.Starboard;

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
	
	public void setTeamManager(TeamManager gameTeamManager){
		this.gameTeamManager = gameTeamManager;
	}
	
	public TeamManager getTeamManager(){
		return gameTeamManager;
	}
}