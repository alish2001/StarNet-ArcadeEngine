package com.starnetmc.ArcadeEngine.Games.DragonSword;

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

public class DragonSwordScoreboard extends Starboard {

	public DragonSwordScoreboard(String name) {
		super("DragonSword");
	}
	
	public DragonSwordScoreboard(){
		setName("DragonSword");
	}
	
	@Override
	public void scoreboarder(Player p){
		Scoreboard sb;
		Objective obj;
		Score game;
		Score kd;
		Score kdr;
		Score swordInf;
		Score swordTime = null;
		Score swordHolder = null;
		Score portalInf;
		Score portalTime = null;
		Score se;
		Score se2;
		Score se3;
		
	    ScoreboardManager bm = Bukkit.getScoreboardManager();
	    sb = bm.getNewScoreboard();
	    obj = sb.registerNewObjective("DragonSword", "dummy");
	    obj.setDisplayName(AF.boldAqua + "The Star Network");
	    
	    se = obj.getScore(ChatColor.RED.toString() + "");
	    se2 = obj.getScore(ChatColor.LIGHT_PURPLE.toString() + "");
	    se3 = obj.getScore(ChatColor.AQUA.toString() + "");
	    
	    game = obj.getScore(AF.boldWhite + ">  " + AF.boldRed + ArcadeCore.getArcadeManager().getGame().getName());
	    kd = obj.getScore(AF.boldYellow + "Kills " + ArcadeCore.getArcadeManager().getStatManager().getKills(p) + AF.boldAqua + " | " + AF.boldRed + ArcadeCore.getArcadeManager().getStatManager().getDeaths(p) + " Deaths");
	    kdr = obj.getScore(AF.boldRed + "KDR: " + ArcadeCore.getArcadeManager().getStatManager().getKDR(p));
	    
	    /*if (!DragonSword.swordDropped){
		    swordInf = obj.getScore(AF.boldAqua + "The Sword Will Drop In:");
		    if (Tickifier.unTickify(Tickifier.tickify(2, Time.MINUTES) - DragonSword.time, Time.SECONDS) < 60){
			    swordTime = obj.getScore(AF.boldWhite + ">    " + AF.boldYellow + 
			    		Tickifier.unTickify(Tickifier.tickify(2, Time.MINUTES) - DragonSword.time, Time.SECONDS) + " Seconds");
		    } else {
			    swordTime = obj.getScore(AF.boldWhite + ">    " + AF.boldYellow + 
			    		Tickifier.unTickify(Tickifier.tickify(2, Time.MINUTES) - DragonSword.time, Time.MINUTES) + " Minutes");
		    }

	    } else {
	    	swordInf = obj.getScore(AF.boldYellow + "Sword Holder:");
	    	swordHolder = obj.getScore(AF.yellow + DragonSword.swordWielder);
	    }
	    
	    if (!DragonSword.activePortal){
		    portalInf = obj.getScore(AF.boldAqua + "The Sword Will Drop In:");
		    if (Tickifier.unTickify(Tickifier.tickify(200, Time.SECONDS) - DragonSword.time, Time.SECONDS) < 60){
			    portalTime = obj.getScore(AF.boldWhite + ">    " + AF.boldYellow + 
			    		Tickifier.unTickify(Tickifier.tickify(3.5, Time.SECONDS) - DragonSword.time, Time.SECONDS) + " Seconds");
		    } else {
			    portalTime = obj.getScore(AF.boldWhite + ">    " + AF.boldYellow + 
			    		Tickifier.unTickify(Tickifier.tickify(3.5, Time.SECONDS) - DragonSword.time, Time.MINUTES) + " Minutes");
		    }
	    } else {
	    	portalInf = obj.getScore(AF.boldYellow + "The Portal HAS OPENED");
	    }*/
	    //The Star Network
	    game.setScore(15);//> DragonSword
	    se.setScore(14);//
	    
	    kd.setScore(13);//Kills 16 | 10 Deaths
	    kdr.setScore(12);//KDR: 1.2
	    se2.setScore(11);//
	    
	    /*swordInf.setScore(10);//The Sword Will Drop In: | Sword Holder:
	    if (DragonSword.swordDropped){
	    	swordHolder.setScore(9);//>    1.37 Minutes
	    } else {
	    	swordTime.setScore(8);//>    alish2001 Holds the Sword.
	    }
	    se3.setScore(7);//
	    
	    portalInf.setScore(6);//The Portal Opens In:
	    if (!DragonSword.activePortal){
	    	portalTime.setScore(5);//>    1.67 Minutes
	    }*/
	    
	    p.setScoreboard(sb);
	}
}