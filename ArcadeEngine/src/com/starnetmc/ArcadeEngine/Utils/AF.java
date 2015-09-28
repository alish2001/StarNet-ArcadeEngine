package com.starnetmc.ArcadeEngine.Utils;

import static org.bukkit.ChatColor.AQUA;
import static org.bukkit.ChatColor.BLUE;
import static org.bukkit.ChatColor.BOLD;
import static org.bukkit.ChatColor.GOLD;
import static org.bukkit.ChatColor.GRAY;
import static org.bukkit.ChatColor.GREEN;
import static org.bukkit.ChatColor.LIGHT_PURPLE;
import static org.bukkit.ChatColor.RED;
import static org.bukkit.ChatColor.RESET;
import static org.bukkit.ChatColor.WHITE;
import static org.bukkit.ChatColor.YELLOW;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.starnetmc.ArcadeCore;
import com.starnetmc.ArcadeEngine.Managers.Achievements.Achievement;
import com.starnetmc.ArcadeEngine.Managers.Classes.Kit;
import com.starnetmc.ArcadeEngine.Managers.Classes.Skill;
import com.starnetmc.ArcadeEngine.Managers.GameProperties.GameProperties;
import com.starnetmc.ArcadeEngine.Managers.Rewards.Reward;

public class AF {

	public static String blue = BLUE + "";
	public static String aqua = AQUA + "";
	public static String white  = WHITE + "";
	public static String gold = GOLD + "";
	public static String red = RED + "";
	public static String green = GREEN + "";
	public static String yellow = YELLOW + "";
	public static String purple = LIGHT_PURPLE + "";
	public static String bold = BOLD + "";
	public static String boldBlue = BLUE + "" + BOLD + "";
	public static String boldWhite = WHITE + "" + BOLD + "";
	public static String boldGold = GOLD + "" + BOLD + "";
	public static String boldRed = RED + "" + BOLD + "";
	public static String boldGreen = GREEN + "" + BOLD + "";
    public static String boldYellow = YELLOW + "" + BOLD + "";
	public static String boldAqua = AQUA + "" + BOLD + "";
	public static String boldGray = GRAY + "" + BOLD + "";
	public static String boldPurple = LIGHT_PURPLE + "" + BOLD + "";
    public static String game = boldAqua + "<" + AQUA + "Game" + boldAqua + "> " + RESET + green;
	public static String error = boldWhite + "<" + red + "Error" + boldWhite + "> " + RESET + red;
	public static String announcement = boldAqua + ">" + RESET + WHITE + "« ANNOUNCEMENT »" + boldAqua + "< " + boldYellow;
	public static String broadcast = boldAqua + "<" + RESET + WHITE + "«»" + boldAqua + "> " + boldYellow;
	public static String debug = boldYellow + "<" + gold + "debug" + boldYellow + "> " + aqua;
	public static String map = boldAqua + "<" + green + "Maps" + boldAqua + "> " + green;
	public static String hint = boldAqua + "<" + boldWhite + "HINT" + boldAqua + "> " + aqua;
	public static String kit = boldAqua + "<" + yellow + "Kit" + boldAqua + "> " + green;
	public static String skill = boldAqua + "<" + aqua + "Skill" + boldAqua + "> " + green;
	public static String properties = boldAqua + "<" + yellow + "GamePropertiesManager" + boldAqua + "> " + green;
	
	
	public static void announceFirstSecondThirdWinner(String first, String second, String third){
    	USound.AllPSound(Sound.LEVEL_UP, 1, 1.30f);
		Bukkit.broadcastMessage(boldGreen + ChatColor.STRIKETHROUGH
				+ "=============================================");
		Bukkit.broadcastMessage(ChatColor.AQUA + ">    " + boldYellow + ArcadeCore.getArcadeManager().getGame().getName());
		Bukkit.broadcastMessage("   ");
		Bukkit.broadcastMessage(boldAqua + "1st Place: " + RESET + aqua + first);
		Bukkit.broadcastMessage(boldGreen + "2nd Place: " + RESET + green + second);
		Bukkit.broadcastMessage(boldYellow + "3rd Place: " + RESET + yellow + third);
		Bukkit.broadcastMessage("   ");
		Bukkit.broadcastMessage(boldWhite + "Map" + boldAqua + " - " + boldYellow + ArcadeCore.getArcadeManager().getMapper().getActiveMap().getName());
		Bukkit.broadcastMessage(boldWhite + "Created By: " + boldYellow + ArcadeCore.getArcadeManager().getMapper().getActiveMap().getMaker());
		Bukkit.broadcastMessage(boldGreen + ChatColor.STRIKETHROUGH
				+ "=============================================");
	}
	
	public static void announceWinner(String winner){
    	USound.AllPSound(Sound.LEVEL_UP, 1, 1.30f);
		Bukkit.broadcastMessage(boldGreen + ChatColor.STRIKETHROUGH
				+ "=============================================");
		Bukkit.broadcastMessage(ChatColor.AQUA + ">    " + boldYellow + ArcadeCore.getArcadeManager().getGame().getName());
		Bukkit.broadcastMessage("   ");
		Bukkit.broadcastMessage("   ");
		Bukkit.broadcastMessage(boldYellow + winner + " Won the Game!");
		Bukkit.broadcastMessage("   ");
		Bukkit.broadcastMessage("   ");
		Bukkit.broadcastMessage(boldWhite + "Map" + boldAqua + " - " + boldYellow + ArcadeCore.getArcadeManager().getMapper().getActiveMap().getName());
		Bukkit.broadcastMessage(boldWhite + "Created By: " + boldYellow + ArcadeCore.getArcadeManager().getMapper().getActiveMap().getMaker());
		Bukkit.broadcastMessage(boldGreen + ChatColor.STRIKETHROUGH
				+ "=============================================");
	}
	
	public static void announceWinningTeam(String winner){
    	USound.AllPSound(Sound.LEVEL_UP, 1, 1.30f);
		Bukkit.broadcastMessage(boldGreen + ChatColor.STRIKETHROUGH
				+ "=============================================");
		Bukkit.broadcastMessage(ChatColor.AQUA + ">    " + boldYellow + ArcadeCore.getArcadeManager().getGame().getName());
		Bukkit.broadcastMessage("   ");
		Bukkit.broadcastMessage("   ");
		Bukkit.broadcastMessage(winner + " Won the Game!");
		Bukkit.broadcastMessage("   ");
		Bukkit.broadcastMessage("   ");
		Bukkit.broadcastMessage(boldWhite + "Map" + boldAqua + " - " + boldYellow + ArcadeCore.getArcadeManager().getMapper().getActiveMap().getName());
		Bukkit.broadcastMessage(boldWhite + "Created By: " + boldYellow + ArcadeCore.getArcadeManager().getMapper().getActiveMap().getMaker());
		Bukkit.broadcastMessage(boldGreen + ChatColor.STRIKETHROUGH
				+ "=============================================");
	}
	
	public static void sendRewardsInfo(Player p, ArrayList<Reward> rewards){
		p.sendMessage("   ");
		p.sendMessage("   ");
		p.sendMessage("   ");
		p.sendMessage("   ");
		p.sendMessage("   ");
		p.sendMessage("   ");
		p.sendMessage("   ");
		p.sendMessage("   ");
		p.sendMessage("   ");
		
    	USound.AllPSound(Sound.LEVEL_UP, 1.5f, 1.30f);
		p.sendMessage(boldYellow + ChatColor.STRIKETHROUGH
				+ "=============================================");
		p.sendMessage(AF.boldAqua + "Rewards");
		p.sendMessage("   ");
		for (Reward r : rewards){
			p.sendMessage(yellow + "+" + r.getValue() + green + " | " + yellow + r.getName());
		}
		p.sendMessage("   ");
		p.sendMessage(boldYellow + ChatColor.STRIKETHROUGH
				+ "=============================================");
	}
	
	public static void sendAchievementInfo(Player p, Achievement achivement){
		p.sendMessage("   ");
		p.sendMessage("   ");
		p.sendMessage("   ");
		p.sendMessage("   ");
		p.sendMessage("   ");
		p.sendMessage("   ");
		p.sendMessage("   ");
		p.sendMessage("   ");
		p.sendMessage("   ");
		
    	USound.AllPSound(Sound.LEVEL_UP, 1.5f, 1.30f);
		p.sendMessage(boldGold + ChatColor.MAGIC + "S" + ChatColor.RESET + boldYellow + ChatColor.STRIKETHROUGH
				+ "=============[" + ChatColor.RESET + boldAqua + " ACHIVEMENT GET " + boldYellow + ChatColor.STRIKETHROUGH +  "]==============" + boldGold + ChatColor.MAGIC + "S");
		p.sendMessage(boldYellow + "<" + boldGold + ChatColor.MAGIC + "S" + ChatColor.RESET + boldYellow + ">      " + achivement.getName() + "      <" + boldGold + ChatColor.MAGIC + "S" + ChatColor.RESET + boldYellow + ">");
	}
	
	public static String recharge(String rechargeMName, String time){	
		return boldGold + "<" + yellow + "Recharger" + boldGold + "> " + red + "You cannot use " + ChatColor.YELLOW + rechargeMName + red + " for " + ChatColor.YELLOW + time + ".";
	}
	
	public static String listTop(String listName){
		return AQUA + "" + ChatColor.STRIKETHROUGH + "---------------- " + ChatColor.YELLOW + listName + AQUA + "" + ChatColor.STRIKETHROUGH + " ---------------";
	}
	
	public static String cmdList(String command, String desc, String rank){
		return "§e" + command + " §b- §e" + desc + AQUA + " | " + ChatColor.RESET + ChatColor.BOLD + rank.toUpperCase();
	}
	
	
	public static String error(String message) {
		return error + message;
	}
	
	public static String debug(String msg){
		return debug + msg;
	}
	
	public static void broadCastDeath(String killer, String killed, String weapon){
		Bukkit.getServer().broadcastMessage(boldRed + "<" + red + "Death" + boldRed + "> " + yellow + killed + GRAY + " Killed by " + yellow + killer + GRAY + " with " + yellow + weapon + GRAY + ".");
	}
	
	public static void broadCastShootingDeath(String killer, String killed, boolean headShot){
		
		if (headShot){
			Bukkit.getServer().broadcastMessage(boldRed + "<" + red + "Death" + boldRed + "> " + yellow + killer + GRAY + " got a HEADSHOT on " + yellow + killed + GRAY + " with " + yellow + "a Bow" + GRAY + ".");
		} else {
			Bukkit.getServer().broadcastMessage(boldRed + "<" + red + "Death" + boldRed + "> " + yellow + killed + GRAY + " was Shot by " + yellow + killer + GRAY + " with " + yellow + "a Bow" + GRAY + ".");
		}
	}	
	
	public static void broadCastDeath(String killer, String killed){
		Bukkit.getServer().broadcastMessage(boldRed + "<" + red + "Death" + boldRed + "> " + yellow + killed + GRAY + " Killed by " + yellow + killer + GRAY + ".");
	}
	
	public static void gPropertiesInfo(GameProperties properties, Player p) {
		p.sendMessage(debug("PvP: " + properties.getPvP()));
		p.sendMessage(debug("PvE: " + properties.getPvE()));
		p.sendMessage(debug("FDamage: " + properties.getFallDamage()));
		p.sendMessage(debug("HDamage " + properties.getHungerDamage()));
		p.sendMessage(debug("ForeverSpec " + properties.getForeverSpec()));
	}
	
	public static String kits(String message){
		
		return ChatColor.AQUA + "Kits" + boldAqua + "> " + ChatColor.GREEN + message;
	}
	
	public static void sendKitInfo(Player player, Kit k){
		
		player.sendMessage("   ");
		player.sendMessage("   ");
		player.sendMessage("   ");
		player.sendMessage("   ");
		player.sendMessage("   ");
		player.sendMessage("   ");
		player.sendMessage("   ");
		player.sendMessage("   ");
		player.sendMessage("   ");
		player.sendMessage("   ");
		player.sendMessage("   ");
		player.sendMessage("   ");
		player.sendMessage("   ");
		player.sendMessage("   ");
		player.sendMessage("   ");
		player.sendMessage("   ");
		player.sendMessage("   ");
		player.sendMessage("   ");
		player.sendMessage("   ");
		
		player.sendMessage(AQUA + "" + ChatColor.STRIKETHROUGH
				+ "----" + ChatColor.GREEN + "" + ChatColor.STRIKETHROUGH + "-----" + ChatColor.YELLOW + "" + ChatColor.STRIKETHROUGH +  "---" + "[ " + ChatColor.RESET + k.getName() + ChatColor.STRIKETHROUGH + " ]" + "---" + ChatColor.GREEN + "" + ChatColor.STRIKETHROUGH + "-----" + ChatColor.AQUA + "" + ChatColor.STRIKETHROUGH + "-----");
		player.sendMessage("   \n");
		player.sendMessage(ChatColor.GREEN + "Kit Description");
		for (String s : k.getDesc()){
			player.sendMessage(s);
		}
		player.sendMessage("   \n");
		player.sendMessage(ChatColor.AQUA + "Kit Skills");
		if (k.getSkills() == null){
			player.sendMessage(ChatColor.GRAY + "None");
		} else {
			for (Skill s : k.getSkills()){
				player.sendMessage(AF.boldGray + "- " + s.getName());
			}
		}
		player.sendMessage(AQUA + "" + ChatColor.STRIKETHROUGH
				+ "---------" + ChatColor.GREEN + "" + ChatColor.STRIKETHROUGH + "----------" + ChatColor.YELLOW + "" + ChatColor.STRIKETHROUGH + "--------" + ChatColor.GREEN + "" + ChatColor.STRIKETHROUGH + "---------" + ChatColor.AQUA + "" + ChatColor.STRIKETHROUGH + "---------");
	}
	
	public static void sendSkillInfo(Player player, Skill s){
		
		player.sendMessage("   ");
		player.sendMessage("   ");
		player.sendMessage("   ");
		player.sendMessage("   ");
		player.sendMessage("   ");
		player.sendMessage("   ");
		player.sendMessage("   ");
		player.sendMessage("   ");
		player.sendMessage("   ");
		player.sendMessage("   ");
		player.sendMessage("   ");
		player.sendMessage("   ");
		player.sendMessage("   ");
		player.sendMessage("   ");
		player.sendMessage("   ");
		player.sendMessage("   ");
		player.sendMessage("   ");
		player.sendMessage("   ");
		player.sendMessage("   ");
		
		player.sendMessage(AQUA + "" + ChatColor.STRIKETHROUGH
				+ "----" + ChatColor.GREEN + "" + ChatColor.STRIKETHROUGH + "------" + ChatColor.YELLOW + "" + ChatColor.STRIKETHROUGH +  "---" + "[ " + ChatColor.RESET + s.getName() + ChatColor.STRIKETHROUGH + " ]" + "---" + ChatColor.GREEN + "" + ChatColor.STRIKETHROUGH + "------" + ChatColor.AQUA + "" + ChatColor.STRIKETHROUGH + "----");
		player.sendMessage("   \n");
		player.sendMessage("Skill Description");
		for (String str : s.getDesc()){
			player.sendMessage(str);
		}
		player.sendMessage("   \n");
		player.sendMessage(AQUA + "" + ChatColor.STRIKETHROUGH
				+ "---------" + ChatColor.GREEN + "" + ChatColor.STRIKETHROUGH + "----------" + ChatColor.YELLOW + "" + ChatColor.STRIKETHROUGH + "--------" + ChatColor.GREEN + "" + ChatColor.STRIKETHROUGH + "---------" + ChatColor.AQUA + "" + ChatColor.STRIKETHROUGH + "---------");
	      USound.PSound(player, Sound.ORB_PICKUP, 2.75F, 1.25F);
	}
	
	public static String mapList(int MapID, String MapName, String author){
		return
				boldYellow + "#" + MapID + ChatColor.RESET + green + " | " + yellow + MapName + ChatColor.RESET + green + " | " + yellow + "By: " + author;
		
	}

	public static String game(String message) {
		return game + message;
	}

	public static String map(String message) {

		return map + message;

	}

	public static String announcement(String message) {

		return announcement + message;
	}

	public static void broadcast(String message) {

		Bukkit.getServer().broadcastMessage(broadcast + message);
		USound.AllPSound(Sound.NOTE_PLING, 1.5f, 1.25f);
	}
	
	public static String hint(String message){
		
		return hint + message;
	}
	
	public static String kit(String message){
		
		return kit + message;
	}
	
	public static String skill(String message){
		
		return skill + message;
	}
	
	public static String properties(String message){
		
		return properties + message;
	}
	
	public static void msgAll(String msg){
		
		for (Player p : Bukkit.getOnlinePlayers()){
			p.sendMessage(msg);
		}
	}

}
