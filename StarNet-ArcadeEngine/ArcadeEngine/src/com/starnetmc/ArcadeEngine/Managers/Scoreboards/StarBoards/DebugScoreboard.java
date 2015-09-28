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
import com.starnetmc.ArcadeEngine.Managers.Scoreboards.Starboard;
import com.starnetmc.ArcadeEngine.Utils.AF;
import com.starnetmc.core.accounts.AccountManager;

public class DebugScoreboard extends Starboard {

	public DebugScoreboard(String name) {
		super("Debug");
	}
	
	public DebugScoreboard() {
		setName("Debug");
	}
	
	@Override
	public void scoreboarder(Player p){
		Scoreboard sb;
		Objective obj;
		Score state;
		Score players;
		Score gametype;
		Score map;
		Score map_var_name;
		Score shards;
		Score kit;
		Score se4;
		Score se3;
		Score se2;
		Score se1;
		
	    ScoreboardManager bm = Bukkit.getScoreboardManager();
	    sb = bm.getNewScoreboard();
	    obj = sb.registerNewObjective("debug", "dummy");
	    
	    obj.setDisplayName(AF.boldRed + "DEBUG");
	    
	    obj.setDisplaySlot(DisplaySlot.SIDEBAR);

	    se1 = obj.getScore(ChatColor.RED.toString() + " ");
	    se2 = obj.getScore(ChatColor.YELLOW.toString() + " ");
	    se3 = obj.getScore(ChatColor.AQUA.toString() + " ");
	    se4 = obj.getScore(ChatColor.GREEN.toString() + " ");

	    players = obj.getScore(AF.boldGold + "Players" + AF.boldWhite + " > " + AF.boldYellow + Bukkit.getServer().getOnlinePlayers().size() + "/" + Bukkit.getServer().getMaxPlayers());
	    
	    state = obj.getScore(AF.boldAqua + "GameState"  + AF.boldWhite + " > " + AF.boldYellow + ArcadeCore.getArcadeManager().getState().toString());
	    gametype = obj.getScore(AF.boldAqua + "GameType" + AF.boldWhite + " > " + AF.boldYellow + ArcadeCore.getArcadeManager().getGame().toString());

	    map = obj.getScore(AF.boldBlue + "Map" + AF.boldWhite + " > " + AF.boldYellow + ArcadeCore.getArcadeManager().getMapper().getActiveMap().getName() + "|#" + ArcadeCore.getArcadeManager().getMapper().getActiveMap().getID());
	    map_var_name = obj.getScore("Map var_name" + AF.boldWhite + " > " + AF.boldYellow + ArcadeCore.getArcadeManager().getMapper().getActiveMap().getVarName());

	    shards = obj.getScore(AF.boldPurple + "Shards" + AF.boldWhite + " > " + AF.yellow + AccountManager.getAccount(p).getShards());

	    if (ArcadeCore.getArcadeManager().getKitManager().getKit(p) == null){
		    kit = obj.getScore(AF.boldYellow + "Kit" + AF.boldWhite + " > " + AF.boldRed + "Null");
	    } else {
		    kit = obj.getScore(AF.boldYellow + "Kit" + AF.boldWhite + " > " + AF.boldRed + ArcadeCore.getArcadeManager().getKitManager().getKit(p).getName());
	    }

	    //DEBUG
	    state.setScore(15); //GameState
	    se4.setScore(14); //
	    
	    gametype.setScore(13); //GameType
	    se1.setScore(12); //
	    
	    map.setScore(11); //MapName + ID
	    map_var_name.setScore(10); //Map var_name
	    se2.setScore(9); //
	    
	    shards.setScore(8); //Shards
	    se3.setScore(7);
	    
	    kit.setScore(6); //Kit
	    
	    se2.setScore(5); //
	    
	    players.setScore(4); // 21/1337
	    
	    p.setScoreboard(sb);
	}
	
}