package com.starnetmc.ArcadeEngine.Managers.Scoreboards.StarBoards;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import com.starnetmc.ArcadeCore;
import com.starnetmc.ArcadeEngine.Managers.GameState;
import com.starnetmc.ArcadeEngine.Managers.Counters.GameCountdown;
import com.starnetmc.ArcadeEngine.Managers.Scoreboards.Starboard;
import com.starnetmc.ArcadeEngine.Utils.AF;
import com.starnetmc.core.accounts.AccountManager;

public class ArcadeScoreboard extends Starboard {

	public ArcadeScoreboard(String name) {
		super("Arcade");
	}
	
	public ArcadeScoreboard() {
		setName("Arcade");
	}
	
	@Override
	public void scoreboarder(Player p){
		Scoreboard sb;
		Objective obj;
		Score state;
		Score players;
		Score game;
		Score crntgame;
		Score map;
		Score crntmap;
		Score shard;
		Score crntshards;
		Score kit;
		Score crntkit;
		Score se5;
		Score se4;
		Score se3;
		Score se2;
		Score se1;
		
	    ScoreboardManager bm = Bukkit.getScoreboardManager();
	    sb = bm.getNewScoreboard();
	    obj = sb.registerNewObjective("Starcade", "dummy");
	    
	    obj.setDisplayName(AF.boldAqua + "The Star Network");
	    
	    if (ArcadeCore.getArcadeManager().isState(GameState.LOADING)){
		    state = obj.getScore(AF.boldWhite + ">    " + AF.boldYellow + GameState.LOADING.getStatus(true) + " in " + GameCountdown.time);
	    } else {
	    	state = obj.getScore(AF.boldWhite + ">    " + ArcadeCore.getArcadeManager().getState().getStatus(true));
	    }
	    
	    obj.setDisplaySlot(DisplaySlot.SIDEBAR);

	    se1 = obj.getScore(ChatColor.RED.toString() + " ");
	    se2 = obj.getScore(ChatColor.YELLOW.toString() + " ");
	    se3 = obj.getScore(ChatColor.AQUA.toString() + " ");
	    se4 = obj.getScore(ChatColor.GREEN.toString() + " ");
	    se5 = obj.getScore(ChatColor.LIGHT_PURPLE.toString() + " ");

	    players = obj.getScore(AF.boldGold + "Players" + AF.boldWhite + " > " + AF.boldYellow + Bukkit.getServer().getOnlinePlayers().size() + "/" + Bukkit.getServer().getMaxPlayers());
	    
	    game = obj.getScore(AF.boldAqua + "Game");
	    crntgame = obj.getScore(AF.boldWhite + ">  " + AF.boldYellow + ArcadeCore.getArcadeManager().getGame().getName());

	    map = obj.getScore(AF.boldBlue + "Map");
	    crntmap = obj.getScore(AF.boldWhite + ">  " + ArcadeCore.getArcadeManager().getMapper().getActiveMap().getName());

	    shard = obj.getScore(AF.boldPurple + "Shards");
	    crntshards = obj.getScore(AF.boldWhite + ">  " + AF.yellow + AccountManager.getAccount(p).getShards());

	    kit = obj.getScore(AF.boldYellow + "Kit");
	    if (ArcadeCore.getArcadeManager().getKitManager().getKit(p) == null){
		    crntkit = obj.getScore(AF.red + "No Kit");
	    } else {
	        crntkit = obj.getScore(ArcadeCore.getArcadeManager().getKitManager().getKit(p).getName());
	    }

	    //The Star Network
	    state.setScore(15); //GameState
	    
	    se5.setScore(14); //
	    
	    players.setScore(13); // 21/1337
	    
	    se4.setScore(12); //
	    
	    game.setScore(11); //Game
	    crntgame.setScore(10); //GameName

	    se1.setScore(9); //
	    
	    map.setScore(8); //Map
	    crntmap.setScore(7); //MapName

	    se2.setScore(6); //
	    
	    shard.setScore(5); //Shards
	    crntshards.setScore(4); //1337
	    
	    se3.setScore(3);
	    
	    kit.setScore(2); //Kit
	    crntkit.setScore(1); //KitName
	    
	    p.setScoreboard(sb);
	}

}
