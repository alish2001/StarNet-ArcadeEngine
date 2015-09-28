package com.starnetmc.ArcadeEngine.Games.SurvivalGames;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import com.starnetmc.ArcadeCore;
import com.starnetmc.ArcadeEngine.Events.CustomPlayerDeathEvent;
import com.starnetmc.ArcadeEngine.Games.Game;
import com.starnetmc.ArcadeEngine.Games.SurvivalGames.Kits.KitSGPlayer;
import com.starnetmc.ArcadeEngine.Managers.Achievements.Achievement;
import com.starnetmc.ArcadeEngine.Managers.Classes.Kit;
import com.starnetmc.ArcadeEngine.Managers.GameProperties.GameProperties;
import com.starnetmc.ArcadeEngine.Managers.Maps.MapTypes.Map;
import com.starnetmc.ArcadeEngine.Managers.Rewards.Reward;
import com.starnetmc.ArcadeEngine.Managers.Rewards.DefaultRewards.RewardKills;
import com.starnetmc.ArcadeEngine.Managers.Scoreboards.Starboard;
import com.starnetmc.ArcadeEngine.Managers.Scoreboards.StarBoards.AliveAndDeadScoreboard;
import com.starnetmc.ArcadeEngine.Utils.AF;
import com.starnetmc.ArcadeEngine.Utils.Logger;
import com.starnetmc.core.util.ULocation;

public class SurvivalGames extends Game {
	
	private Map map;
	static List<Location> chests = new ArrayList<Location>();
	static int maxChestLootAmnt = 7;
	
	public static String first = "No One";
	public static String second = "No One";
	public static String third = "No One";
	
	public SurvivalGames(List<Listener> gameListeners, int gameTaskID, List<Kit> gameKits, GameProperties gameProperties, Starboard gameScoreboard, List<Reward> gameRewards, List<Achievement> gameAchivements, ArrayList<String> gameDesc, List<String> gameHints) {
		super(gameListeners, gameTaskID, gameKits, gameProperties, gameScoreboard, gameRewards, gameAchivements, gameDesc, gameHints);
		manager = ArcadeCore.getArcadeManager();
		
		setDesc(new ArrayList<String>());
		addDesc("Get as much loot as you can.");
		addDesc("Kill as many people.");
		addDesc("Win");
		
		setHints(new ArrayList<String>());
		addHint("It's better to get people when they don't see you.");
		addHint("Try to be Sneaky-Beaky like.");
		addHint("Don't get rekt.");
		
		GameProperties properties = new GameProperties();
		properties.setMap(manager.getMapper().getActiveMap());
		properties.setPvE(false);
		properties.setDeathItemDrop(true);
		properties.setGroundArrowRemoval(false);
		properties.setItemEat(true);
		properties.setItemDrop(true);
		setProperties(properties);
		
		setScoreboard(new AliveAndDeadScoreboard());
		
		setAchievements(new ArrayList<Achievement>());
		
		setRewards(new ArrayList<Reward>());
		addReward(new RewardKills(null, 0));
	    
		setKits(new ArrayList<Kit>());
		addKit(new KitSGPlayer(null, null, -1, null, null, null, null, null));
		
		setListeners(new ArrayList<Listener>());
		addListener(this);
	}
	
	public void lootifyChests(){
		Random r = new Random();
		
		Logger.log("<SG> Lootifying Chests...");
		
		List<ItemStack> items = new ArrayList<ItemStack>();
		items.add(new ItemStack(Material.WOOD_SWORD));
		items.add(new ItemStack(Material.LEATHER_HELMET));
		items.add(new ItemStack(Material.LEATHER_CHESTPLATE));
		items.add(new ItemStack(Material.LEATHER_LEGGINGS));
		items.add(new ItemStack(Material.LEATHER_BOOTS));
		
		Logger.log("<SG> Locating all chests on the Map...");
		
		for (Location l : ULocation.getRegionBlocks(map.getCenter(), (int) map.getBRad())){
			if (l.getBlock().getType() == Material.CHEST){
				chests.add(l);
				Logger.log("chest mat found and added @ " + l);
			}
		}
		
		Logger.log("<SG> Running chest loot algorithm..");
		
		for (Location c : chests) {
			Chest chest = (Chest) c.getBlock().getState();
			
			for (int i = 1 + r.nextInt(maxChestLootAmnt); i != 0; i--){
				Logger.log("penta loop: round " + i);
			  int itemSpot = r.nextInt(chest.getInventory().getSize());
			  ItemStack item = items.get(r.nextInt(items.size()));
			  
			  if (chest.getInventory().getItem(itemSpot) != null && chest.getInventory().getItem(itemSpot).getType() != Material.AIR) itemSpot--;
			  chest.getInventory().setItem(itemSpot, item);
			  Logger.log("<SG> Item: " + item + " set in chest @ itemspot: " + itemSpot);
			  Logger.log("<SG> Chest @ " + chest.getLocation() + " contents=" + chest.getInventory().getContents());
			}
		}
		
		Logger.log("<SG> Lootified chests.");
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
		
		if (manager.getPlayerManager().getAlivePlayers().size() == 0){
			first = e.getKilled().getName();

			AF.announceFirstSecondThirdWinner(first, second, third);
			stop(false);
		}
    }
    
	@Override
	public void start(){
		map = ArcadeCore.getArcadeManager().getMapper().getActiveMap();
		lootifyChests();
	}
	
	@Override
	public void shutdown(){
		for (Location c : chests){
			Chest chest = (Chest) c.getBlock().getState();
			chest.getInventory().clear();
		}
		
		Logger.log("<SG> Un-Lootified chests.");
	}

}