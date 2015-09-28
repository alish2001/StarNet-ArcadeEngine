package com.starnetmc.ArcadeEngine.Managers.Classes.GUI;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.starnetmc.ArcadeCore;
import com.starnetmc.ArcadeEngine.Games.TeamGame.TeamGame;
import com.starnetmc.ArcadeEngine.Managers.Classes.Kit;
import com.starnetmc.ArcadeEngine.Managers.Classes.KitType;
import com.starnetmc.ArcadeEngine.Utils.ItemFactory;
import com.starnetmc.ArcadeEngine.Utils.USound;

public class KitGUI {
	
	public static void openKitGUI(Player p){
		
		Inventory kgui = Bukkit.createInventory(null, 27, ChatColor.GREEN + "" + ChatColor.BOLD + "Kits");
		
		ItemStack nKit = ItemFactory.createItem(Material.EMERALD, ChatColor.GREEN + "" + ChatColor.BOLD + "Normal Kits >", Arrays.asList(
				ChatColor.WHITE + "" + ChatColor.STRIKETHROUGH + "------------------",
				"", ChatColor.YELLOW + "These are kits that can be used normally",
				ChatColor.YELLOW + "without the need to purchase them",
				ChatColor.YELLOW + "with " + ChatColor.LIGHT_PURPLE + "Shards.",
				"", ChatColor.WHITE + "" + ChatColor.STRIKETHROUGH + "------------------"), true);
				
		ItemStack pKit = ItemFactory.createItem(Material.DIAMOND, ChatColor.AQUA + "" + ChatColor.BOLD + "Premium Kits >", Arrays.asList(
				ChatColor.WHITE + "" + ChatColor.STRIKETHROUGH + "------------------",
				"", ChatColor.YELLOW + "These kits can be purchased",
				ChatColor.YELLOW + "with " + ChatColor.LIGHT_PURPLE + "Shards.",
				ChatColor.AQUA + "Star's" + ChatColor.YELLOW + " in-game currency that you can get",
				ChatColor.YELLOW + "by playing games!",
				"", ChatColor.WHITE + "" + ChatColor.STRIKETHROUGH + "------------------"), true);
		
		
		kgui.setItem(0, nKit);
		kgui.setItem(9, pKit);
		int nKitSorter = 1;
		int pKitSorter = 10;
		
			for (Kit k : ArcadeCore.getArcadeManager().getGame().getGClass().getKits()){
				if (k.getType() == KitType.NORMAL){
					kgui.setItem(nKitSorter, k.getIcon());
					nKitSorter++;
				}
				if (k.getType() == KitType.PREMIUM){
					kgui.setItem(pKitSorter, k.getIcon());
					pKitSorter++;
				}
			}
		
		USound.PSound(p, Sound.NOTE_PLING, 3f, 1.75f);
		p.openInventory(kgui);
	}
	
	//Plz fix u ver fastin //LELELELELLE //I'm talking to myself ';-;'
	public static void openTwoTeamKitGUI(Player p){
		
		Inventory kgui = Bukkit.createInventory(null, 27, ChatColor.GREEN + "" + ChatColor.BOLD + "Team Kits");
		
		ItemStack nKit = ItemFactory.createItem(Material.EMERALD, ChatColor.GREEN + "" + ChatColor.BOLD + "Normal Kits >", Arrays.asList(
				ChatColor.WHITE + "" + ChatColor.STRIKETHROUGH + "------------------",
				"", ChatColor.YELLOW + "These are kits that can be used normally",
				ChatColor.YELLOW + "without the need to purchase them",
				ChatColor.YELLOW + "with " + ChatColor.LIGHT_PURPLE + "Shards.",
				"", ChatColor.WHITE + "" + ChatColor.STRIKETHROUGH + "------------------"), true);
				
		ItemStack pKit = ItemFactory.createItem(Material.DIAMOND, ChatColor.AQUA + "" + ChatColor.BOLD + "Premium Kits >", Arrays.asList(
				ChatColor.WHITE + "" + ChatColor.STRIKETHROUGH + "------------------",
				"", ChatColor.YELLOW + "These kits can be purchased",
				ChatColor.YELLOW + "with " + ChatColor.LIGHT_PURPLE + "Shards.",
				ChatColor.AQUA + "Star's" + ChatColor.YELLOW + " in-game currency that you can get",
				ChatColor.YELLOW + "by playing games!",
				"", ChatColor.WHITE + "" + ChatColor.STRIKETHROUGH + "------------------"), true);
		
		//TeamIcon setup
		String firstTeamNamePlain = ((TeamGame) ArcadeCore.getArcadeManager().getGame().getGClass()).getTeamManager().getAllTeams().get(0).getName(false);
		String firstTeamNameColor = ((TeamGame) ArcadeCore.getArcadeManager().getGame().getGClass()).getTeamManager().getAllTeams().get(0).getName(true);
		Material firstTeamIconMat = ((TeamGame) ArcadeCore.getArcadeManager().getGame().getGClass()).getTeamManager().getAllTeams().get(0).getIcon().getType();
		
		String secondTeamNamePlain = ((TeamGame) ArcadeCore.getArcadeManager().getGame().getGClass()).getTeamManager().getAllTeams().get(1).getName(false);
		String secondTeamNameColor = ((TeamGame) ArcadeCore.getArcadeManager().getGame().getGClass()).getTeamManager().getAllTeams().get(1).getName(true);
		Material secondTeamIconMat = ((TeamGame) ArcadeCore.getArcadeManager().getGame().getGClass()).getTeamManager().getAllTeams().get(1).getIcon().getType();
		
		List<String> firstTeamDesc = Arrays.asList(" ",
				 "These Kits can only be used",
				 "by " + firstTeamNamePlain);
		
		List<String> secondTeamDesc = Arrays.asList(" ",
				 "These Kits can only be used",
				 "by " + secondTeamNamePlain);
		
		ItemStack firstTeamIcon = ItemFactory.createItem(firstTeamIconMat, firstTeamNameColor + "'s Kits", firstTeamDesc);
		ItemStack secondTeamIcon = ItemFactory.createItem(secondTeamIconMat, secondTeamNameColor + "'s Kits", secondTeamDesc);
		
		kgui.setItem(10, firstTeamIcon);
		kgui.setItem(28, secondTeamIcon);
		
		kgui.setItem(11, nKit);
		kgui.setItem(20, pKit);
		kgui.setItem(29, nKit);
		kgui.setItem(39, pKit);
		
		int firstNKitSorter = 12;
		int firstPKitSorter = 22;
		int secondNKitSorter = 30;
		int secondPKitSorter = 40;
		
			for (Kit k : ((TeamGame) ArcadeCore.getArcadeManager().getGame().getGClass()).getTeamManager().getAllTeams().get(0).getTeamKits()){
				if (k.getType() == KitType.NORMAL){
					kgui.setItem(firstNKitSorter, k.getIcon());
					firstNKitSorter++;
				}
				if (k.getType() == KitType.PREMIUM){
					kgui.setItem(firstPKitSorter, k.getIcon());
					firstPKitSorter++;
				}
			}
			
			for (Kit k : ((TeamGame) ArcadeCore.getArcadeManager().getGame().getGClass()).getTeamManager().getAllTeams().get(1).getTeamKits()){
				if (k.getType() == KitType.NORMAL){
					kgui.setItem(secondNKitSorter, k.getIcon());
					secondNKitSorter++;
				}
				if (k.getType() == KitType.PREMIUM){
					kgui.setItem(secondPKitSorter, k.getIcon());
					secondPKitSorter++;
				}
			}
		
		USound.PSound(p, Sound.NOTE_PLING, 3f, 1.75f);
		p.openInventory(kgui);
	}
	
	public static void openFourTeamKitGUI(Player p){
		
		Inventory kgui = Bukkit.createInventory(null, 27, ChatColor.GREEN + "" + ChatColor.BOLD + "Team Kits");
		
		ItemStack nKit = ItemFactory.createItem(Material.EMERALD, ChatColor.GREEN + "" + ChatColor.BOLD + "Normal Kits >", Arrays.asList(
				ChatColor.WHITE + "" + ChatColor.STRIKETHROUGH + "------------------",
				"", ChatColor.YELLOW + "These are kits that can be used normally",
				ChatColor.YELLOW + "without the need to purchase them",
				ChatColor.YELLOW + "with " + ChatColor.LIGHT_PURPLE + "Shards.",
				"", ChatColor.WHITE + "" + ChatColor.STRIKETHROUGH + "------------------"), true);
				
		ItemStack pKit = ItemFactory.createItem(Material.DIAMOND, ChatColor.AQUA + "" + ChatColor.BOLD + "Premium Kits >", Arrays.asList(
				ChatColor.WHITE + "" + ChatColor.STRIKETHROUGH + "------------------",
				"", ChatColor.YELLOW + "These kits can be purchased",
				ChatColor.YELLOW + "with " + ChatColor.LIGHT_PURPLE + "Shards.",
				ChatColor.AQUA + "Star's" + ChatColor.YELLOW + " in-game currency that you can get",
				ChatColor.YELLOW + "by playing games!",
				"", ChatColor.WHITE + "" + ChatColor.STRIKETHROUGH + "------------------"), true);
		
		//TeamIcon setup
		String firstTeamNamePlain = ((TeamGame) ArcadeCore.getArcadeManager().getGame().getGClass()).getTeamManager().getAllTeams().get(0).getName(false);
		String firstTeamNameColor = ((TeamGame) ArcadeCore.getArcadeManager().getGame().getGClass()).getTeamManager().getAllTeams().get(0).getName(true);
		Material firstTeamIconMat = ((TeamGame) ArcadeCore.getArcadeManager().getGame().getGClass()).getTeamManager().getAllTeams().get(0).getIcon().getType();
		
		String secondTeamNamePlain = ((TeamGame) ArcadeCore.getArcadeManager().getGame().getGClass()).getTeamManager().getAllTeams().get(1).getName(false);
		String secondTeamNameColor = ((TeamGame) ArcadeCore.getArcadeManager().getGame().getGClass()).getTeamManager().getAllTeams().get(1).getName(true);
		Material secondTeamIconMat = ((TeamGame) ArcadeCore.getArcadeManager().getGame().getGClass()).getTeamManager().getAllTeams().get(1).getIcon().getType();
		
		String thirdTeamNamePlain = ((TeamGame) ArcadeCore.getArcadeManager().getGame().getGClass()).getTeamManager().getAllTeams().get(2).getName(false);
		String thirdTeamNameColor = ((TeamGame) ArcadeCore.getArcadeManager().getGame().getGClass()).getTeamManager().getAllTeams().get(2).getName(true);
		Material thirdTeamIconMat = ((TeamGame) ArcadeCore.getArcadeManager().getGame().getGClass()).getTeamManager().getAllTeams().get(2).getIcon().getType();
		
		String fourthTeamNamePlain = ((TeamGame) ArcadeCore.getArcadeManager().getGame().getGClass()).getTeamManager().getAllTeams().get(3).getName(false);
		String fourthTeamNameColor = ((TeamGame) ArcadeCore.getArcadeManager().getGame().getGClass()).getTeamManager().getAllTeams().get(3).getName(true);
		Material fourthTeamIconMat = ((TeamGame) ArcadeCore.getArcadeManager().getGame().getGClass()).getTeamManager().getAllTeams().get(3).getIcon().getType();
		
		List<String> firstTeamDesc = Arrays.asList(" ",
				 "These Kits can only be used",
				 "by " + firstTeamNamePlain);
		
		List<String> secondTeamDesc = Arrays.asList(" ",
				 "These Kits can only be used",
				 "by " + secondTeamNamePlain);
		
		List<String> thirdTeamDesc = Arrays.asList(" ",
				 "These Kits can only be used",
				 "by " + thirdTeamNamePlain);
		
		List<String> fourthTeamDesc = Arrays.asList(" ",
				 "These Kits can only be used",
				 "by " + fourthTeamNamePlain);
		
		ItemStack firstTeamIcon = ItemFactory.createItem(firstTeamIconMat, firstTeamNameColor + "'s Kits", firstTeamDesc);
		ItemStack secondTeamIcon = ItemFactory.createItem(secondTeamIconMat, secondTeamNameColor + "'s Kits", secondTeamDesc);
		ItemStack thirdTeamIcon = ItemFactory.createItem(thirdTeamIconMat, thirdTeamNameColor + "'s Kits", thirdTeamDesc);
		ItemStack fourthTeamIcon = ItemFactory.createItem(fourthTeamIconMat, fourthTeamNameColor + "'s Kits", fourthTeamDesc);
		
		kgui.setItem(3, firstTeamIcon);
		kgui.setItem(5, secondTeamIcon);
		kgui.setItem(46, thirdTeamIcon);
		kgui.setItem(52, fourthTeamIcon);
		
		kgui.setItem(12, nKit);
		kgui.setItem(11, pKit);
		kgui.setItem(15, nKit);
		kgui.setItem(14, pKit);
		kgui.setItem(37, nKit);
		kgui.setItem(36, pKit);
		kgui.setItem(43, nKit);
		kgui.setItem(44, pKit);
		
		int firstNKitSorter = 21;
		int firstPKitSorter = 20;
		int secondNKitSorter = 25;
		int secondPKitSorter = 24;
		int thirdNKitSorter = 28;
		int thirdPKitSorter = 27;
		int fourthNKitSorter = 33;
		int fourthPKitSorter = 34;
		
			for (Kit k : ((TeamGame) ArcadeCore.getArcadeManager().getGame().getGClass()).getTeamManager().getAllTeams().get(0).getTeamKits()){
				if (k.getType() == KitType.NORMAL){
					kgui.setItem(firstNKitSorter, k.getIcon());
					firstNKitSorter = firstNKitSorter + 9;
				}
				
				if (k.getType() == KitType.PREMIUM){
					kgui.setItem(firstPKitSorter, k.getIcon());
					firstPKitSorter = firstPKitSorter + 9;
				}
			}
			
			for (Kit k : ((TeamGame) ArcadeCore.getArcadeManager().getGame().getGClass()).getTeamManager().getAllTeams().get(1).getTeamKits()){
				if (k.getType() == KitType.NORMAL){
					kgui.setItem(secondNKitSorter, k.getIcon());
					secondNKitSorter = secondNKitSorter + 10;
				}
				
				if (k.getType() == KitType.PREMIUM){
					kgui.setItem(secondPKitSorter, k.getIcon());
					secondPKitSorter = secondPKitSorter + 10;
				}
			}
			
			for (Kit k : ((TeamGame) ArcadeCore.getArcadeManager().getGame().getGClass()).getTeamManager().getAllTeams().get(2).getTeamKits()){
				if (k.getType() == KitType.NORMAL){
					kgui.setItem(thirdNKitSorter, k.getIcon());
					thirdNKitSorter = thirdNKitSorter - 9;
				}
				
				if (k.getType() == KitType.PREMIUM){
					kgui.setItem(thirdPKitSorter, k.getIcon());
					thirdPKitSorter = thirdPKitSorter - 9;
				}
			}
			
			for (Kit k : ((TeamGame) ArcadeCore.getArcadeManager().getGame().getGClass()).getTeamManager().getAllTeams().get(3).getTeamKits()){
				if (k.getType() == KitType.NORMAL){
					kgui.setItem(fourthNKitSorter, k.getIcon());
					fourthNKitSorter = fourthNKitSorter - 10;
				}
				
				if (k.getType() == KitType.PREMIUM){
					kgui.setItem(fourthPKitSorter, k.getIcon());
					fourthPKitSorter = fourthPKitSorter - 10;
				}
		
		USound.PSound(p, Sound.NOTE_PLING, 3f, 1.75f);
		p.openInventory(kgui);
	  }
	}
}