package com.starnetmc.ArcadeEngine.Games.Pacman;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import com.starnetmc.ArcadeCore;
import com.starnetmc.ArcadeEngine.Games.Pacman.Kits.Ghosts.KitPMSpeedyGhost;
import com.starnetmc.ArcadeEngine.Games.TeamGame.Team;
import com.starnetmc.ArcadeEngine.Games.TeamGame.TeamGame;
import com.starnetmc.ArcadeEngine.Games.TeamGame.TeamManager;
import com.starnetmc.ArcadeEngine.Managers.Achievements.Achievement;
import com.starnetmc.ArcadeEngine.Managers.Classes.Kit;
import com.starnetmc.ArcadeEngine.Managers.GameProperties.GameProperties;
import com.starnetmc.ArcadeEngine.Managers.Rewards.Reward;
import com.starnetmc.ArcadeEngine.Managers.Scoreboards.Starboard;

public class Pacman extends TeamGame {
	
	public Pacman(List<Listener> gameListeners, int gameTaskID, List<Kit> gameKits, GameProperties gameProperties, Starboard gameScoreboard, List<Reward> gameRewards, List<Achievement> gameAchivements, ArrayList<String> gameDesc, List<String> gameHints, TeamManager gameTeamManager) {
		super(gameListeners, gameTaskID, gameKits, gameProperties, gameScoreboard, gameRewards, gameAchivements, gameDesc, gameHints, gameTeamManager);
		manager = ArcadeCore.getArcadeManager();
		
		ArrayList<Kit> pacmanKits = new ArrayList<Kit>();
		Team pacmans = new Team("Pacmans", "PACMAN", ChatColor.YELLOW, Material.GOLD_BLOCK, new ArrayList<Player>(), 8, pacmanKits, true);
		
		ArrayList<Kit> ghostKits = new ArrayList<Kit>();
		ghostKits.add(new KitPMSpeedyGhost(null, null, 0, null, null, null, null, null));
		Team ghosts = new Team("Ghosts", "GHOST", ChatColor.AQUA, Material.GHAST_TEAR, new ArrayList<Player>(), 12, ghostKits, true);
		
		ArrayList<Team> teams = new ArrayList<Team>();
		teams.add(pacmans);
		teams.add(ghosts);
		
		setTeamManager(new TeamManager(manager, teams));
		
		setDesc(new ArrayList<String>());
		addDesc("If you're a Pacman GET ALL THE COOKIES");
		addDesc("If you're a Ghost GET ALL THE PACMAN");
		addDesc("Now. GO AND KILL EACHOTHER!");
		
		setHints(new ArrayList<String>());
		addHint("Have you seriously not played pacman before?");
		addHint("You should ask your dad about the rules of Pacman, I'm too lazy to tell you.");
		
		setTaskID(-1);
		GameProperties properties = new GameProperties();
		properties.setMap(manager.getMapper().getActiveMap());
		properties.setPlayersNeeded(5);
		properties.setPvE(false);
		properties.setGracePeriod(false);
		properties.setTeamGame(true);
		properties.setItemPickup(false);
		setProperties(properties);
		
		setAchievements(new ArrayList<Achievement>());
		
		setRewards(new ArrayList<Reward>());
		
		setKits(new ArrayList<Kit>());
		
		setListeners(new ArrayList<Listener>());
		addListener(this);
	}

}