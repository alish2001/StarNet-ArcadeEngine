package com.starnetmc.ArcadeEngine.Games.Spleef;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.starnetmc.ArcadeCore;
import com.starnetmc.ArcadeEngine.Events.CustomPlayerDeathEvent;
import com.starnetmc.ArcadeEngine.Games.Game;
import com.starnetmc.ArcadeEngine.Games.Spleef.Kits.KitSpleefer;
import com.starnetmc.ArcadeEngine.Games.Spleef.Rewards.RewardSpleefWinners;
import com.starnetmc.ArcadeEngine.Managers.Achievements.Achievement;
import com.starnetmc.ArcadeEngine.Managers.Classes.Kit;
import com.starnetmc.ArcadeEngine.Managers.GameProperties.GameProperties;
import com.starnetmc.ArcadeEngine.Managers.Rewards.Reward;
import com.starnetmc.ArcadeEngine.Managers.Rewards.DefaultRewards.RewardTimeAlive;
import com.starnetmc.ArcadeEngine.Managers.Scoreboards.Starboard;
import com.starnetmc.ArcadeEngine.Managers.Scoreboards.StarBoards.AliveAndDeadScoreboard;
import com.starnetmc.ArcadeEngine.Utils.AF;

public class StarSpleef extends Game implements Listener {

	public static String first = "No One";
	public static String second = "No One";
	public static String third = "No One";
	
	public StarSpleef(List<Listener> gameListeners, int gameTaskID, List<Kit> gameKits, GameProperties gameProperties, Starboard gameScoreboard, List<Reward> gameRewards, List<Achievement> gameAchivements, ArrayList<String> gameDesc, List<String> gameHints) {
		super(gameListeners, gameTaskID, gameKits, gameProperties, gameScoreboard, gameRewards, gameAchivements, gameDesc, gameHints);
		manager = ArcadeCore.getArcadeManager();
		
		setDesc(new ArrayList<String>());
		addDesc("Spleef or be Spleefed!");
		addDesc("Destroy blocks under your enemies");
		addDesc("and send them going downward!");
		
		setHints(new ArrayList<String>());
		addHint("It's better to get people when they don't notice you.");
		addHint("Go Sneaky-Beaky like.");
		addHint("Don't get spleefed, pay attention to what's under your feet.");
		
		setTaskID(-1);
		GameProperties properties = new GameProperties();
		properties.setMap(manager.getMapper().getActiveMap());
		properties.setItemPickup(false);
		properties.setPvE(false);
		properties.setPvP(false);
		setProperties(properties);
		
		setScoreboard(new AliveAndDeadScoreboard());
		
		setAchievements(new ArrayList<Achievement>());
		
		setRewards(new ArrayList<Reward>());
		addReward(new RewardTimeAlive(null, 0));
		addReward(new RewardSpleefWinners(null, 0));
	    
		setKits(new ArrayList<Kit>());
		addKit(new KitSpleefer(null, null, -1, null, null, null, null, null));
		
		setListeners(new ArrayList<Listener>());
		addListener(this);
	}
	
	@EventHandler
	public void endCheck(CustomPlayerDeathEvent e){
		if (manager.getPlayerManager().getAlivePlayers().size() == 2){
			third = e.getKilled().getName();
			return;
		}
		
		if (manager.getPlayerManager().getAlivePlayers().size() == 1){
			second = e.getKilled().getName();
			first = manager.getPlayerManager().getAlivePlayers().get(0).getName();
			
			AF.announceFirstSecondThirdWinner(first, second, third);
			stop(false);
		}
	}
	
	@Override
	public void start(){
		first = "No One";
		second = "No One";
		third = "No One";
	}

}