package com.starnetmc.ArcadeEngine.Managers.Scoreboards.StarBoards;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import com.starnetmc.ArcadeCore;
import com.starnetmc.ArcadeEngine.Managers.Scoreboards.Starboard;
import com.starnetmc.ArcadeEngine.Utils.AF;

public class AliveAndDeadScoreboard extends Starboard {

	public AliveAndDeadScoreboard(String name) {
		super("Alive and Dead");
	}

	public AliveAndDeadScoreboard() {
		setName("Alive and Dead");
	}
	
	@Override
	public void scoreboarder(Player p){
		Scoreboard sb;
		Objective obj;
		Score game;
		Score alive;
		Score dead;
		Score se;
		Score se2;
		
	    ScoreboardManager bm = Bukkit.getScoreboardManager();
	    sb = bm.getNewScoreboard();
	    obj = sb.registerNewObjective("AliveandDead", "dummy");
	    
	    obj.setDisplayName(AF.boldAqua + "The Star Network");
	    se = obj.getScore(ChatColor.GREEN.toString() + "");
	    se2 = obj.getScore(ChatColor.YELLOW.toString() + "");
	    game = obj.getScore(AF.boldWhite + ">  " + AF.boldAqua + ArcadeCore.getArcadeManager().getGame().getName());
	    
	    game.setScore(18);
	    se.setScore(17); //
	    
	    alive = obj.getScore(AF.boldGreen + "ALIVE: " + ArcadeCore.getArcadeManager().getPlayerManager().getAlivePlayers().size());
	    dead = obj.getScore(AF.boldRed + "DEAD: " + ArcadeCore.getArcadeManager().getPlayerManager().getDeadPlayers().size());
	    
	    alive.setScore(16); // ALIVE: 21
	    dead.setScore(15); // DEAD = -21
	    
	    se2.setScore(14); //
	    
	    // Alive Players
	    // Dead Players
	    int place = 13;
	    for (Player ap : ArcadeCore.getArcadeManager().getPlayerManager().getAlivePlayers()){
	    	Score player = obj.getScore(AF.green + ap.getName());
	    	player.setScore(place);
	    	place--;
	    }
	    
	    for (Player ap : ArcadeCore.getArcadeManager().getPlayerManager().getDeadPlayers()){
	    	Score player = obj.getScore(AF.red + ap.getName());
	    	player.setScore(place);
	    	place--;
	    }
	    
	    p.setScoreboard(sb);
	}
	
	

}