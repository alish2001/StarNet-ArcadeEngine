package com.starnetmc.ArcadeEngine.Games.BorderBusters;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.WorldBorder;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import com.starnetmc.ArcadeCore;
import com.starnetmc.ArcadeEngine.Events.CustomPlayerDeathEvent;
import com.starnetmc.ArcadeEngine.Games.Game;
import com.starnetmc.ArcadeEngine.Games.BorderBusters.Kits.KitBBPlayer;
import com.starnetmc.ArcadeEngine.Games.BorderBusters.Rewards.RewardBBWinners;
import com.starnetmc.ArcadeEngine.Managers.Achievements.Achievement;
import com.starnetmc.ArcadeEngine.Managers.Classes.Kit;
import com.starnetmc.ArcadeEngine.Managers.GameProperties.GameProperties;
import com.starnetmc.ArcadeEngine.Managers.Maps.MapTypes.BBMap;
import com.starnetmc.ArcadeEngine.Managers.Rewards.Reward;
import com.starnetmc.ArcadeEngine.Managers.Scoreboards.Starboard;
import com.starnetmc.ArcadeEngine.Managers.Scoreboards.StarBoards.AliveAndDeadScoreboard;
import com.starnetmc.ArcadeEngine.Utils.AF;
import com.starnetmc.ArcadeEngine.Utils.Logger;
import com.starnetmc.ArcadeEngine.Utils.UMethods;
import com.starnetmc.ArcadeEngine.Utils.USound;
import com.starnetmc.core.events.UpdateEvent;
import com.starnetmc.core.util.Tickifier;
import com.starnetmc.core.util.Tickifier.Time;
import com.starnetmc.core.util.UpdateType;

public class BorderBusters extends Game {
	
	private static BBMap map;
	private static WorldBorder border;
	private static int currentSafeZoneID;
	private static int borderSize = 5;
	private static int round = 1;
	private static long borderSpeed = 10;
	
	public static String first = "No One";
	public static String second = "No One";
	public static String third = "No One";
	
	public BorderBusters(List<Listener> gameListeners, int gameTaskID, List<Kit> gameKits, GameProperties gameProperties, Starboard gameScoreboard, List<Reward> gameRewards, List<Achievement> gameAchivements, ArrayList<String> gameDesc, List<String> gameHints) {
		super(gameListeners, gameTaskID, gameKits, gameProperties, gameScoreboard, gameRewards, gameAchivements, gameDesc, gameHints);
		manager = ArcadeCore.getArcadeManager();
		
		setDesc(new ArrayList<String>());
		addDesc("Run away from the border into a safe-zone.");
		addDesc("Out run your enemy's.");
		addDesc("Will you be the last one standing?");
		
		setHints(new ArrayList<String>());
		addHint("The border can come as close as 2 blocks to a safe-zone.");
		addHint("The speed of the border closing in gradually increases as rounds pass by.");
		
		setTaskID(-1);
		GameProperties properties = new GameProperties();
		properties.setMap(manager.getMapper().getActiveMap());
		properties.setPlayersNeeded(4);
		properties.setPvE(false);
		properties.setPvP(false);
		properties.setGracePeriod(false);
		setProperties(properties);
	    
		setScoreboard(new AliveAndDeadScoreboard());
		
		setAchievements(new ArrayList<Achievement>());
		
		setRewards(new ArrayList<Reward>());
		addReward(new RewardBBWinners(null, 0));
		
		setKits(new ArrayList<Kit>());
		addKit(new KitBBPlayer(null, null, -1, null, null, null, null, null));
		
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
	
	private void beaconizeSafeZones(){
		Logger.log("<BB> Beaconizing SafeZones.");
		for (Location zone : map.getSafeZones()){
			Logger.log("<BB> Beaconizing SafeZone @ " + zone);
			UMethods.createBeacon(zone);
			disguiseSafeZoneBlock(zone);
		}
	}
	
	private void disguiseSafeZoneBlock(int zoneID){
		Location zone = map.getSafeZones().get(zoneID);
		disguiseSafeZoneBlock(zone);
	}
	
	private void disguiseSafeZoneBlock(Location zone){
		Location ranBlock = new Location(zone.getWorld(), zone.getX() + 1, zone.getY() - 1, zone.getZ() - 1);
		zone.getBlock().setType(ranBlock.getBlock().getType());
	}
	
	private void despenseSafeZoneID(){
		Random r = new Random();
		currentSafeZoneID = r.nextInt(map.getSafeZones().size());
	}
	
	private void callBorder(int zoneID){
		AF.msgAll(AF.boldRed + "Round " + round + "!");
		USound.AllPSound(Sound.NOTE_PLING, 1.75f, 1.25f);
		map.getSafeZones().get(zoneID).getBlock().setType(Material.GLASS);
		border.setCenter(map.getSafeZones().get(zoneID));
		border.setSize(borderSize, borderSpeed);
		borderSpeed--;
		round++;
	}
	
	private void resetBorder(){
		border.setSize(map.getBRad() + 30, 1);
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				border.setCenter(map.getCenter());
			}
		}.runTaskLater(ArcadeCore.getPlugin(), Tickifier.tickify(1, Time.SECONDS));
	}
	
	@EventHandler
	public void onBorderDone(UpdateEvent e){
		if (e.getType() != UpdateType.TICK) return;
		if (border == null) return;
		if (border.getSize() != borderSize) return;
		
		resetBorder();
		new BukkitRunnable() {
			
			@Override
			public void run() {
				disguiseSafeZoneBlock(currentSafeZoneID);
				despenseSafeZoneID();
				callBorder(currentSafeZoneID);
			}
		}.runTaskLater(ArcadeCore.getPlugin(), Tickifier.tickify(1, Time.SECONDS));
		
	}
	
	private void resetValues(){
		first = "No One";
		second = "No One";
		third = "No One";
		round = 1;
		borderSpeed = 10;
	}
	
	@Override
	public void start(){
		map = (BBMap) manager.getMapper().getActiveMap();
		border = map.getCenter().getWorld().getWorldBorder();
		resetValues();
		border.setCenter(map.getCenter());
		border.setSize(map.getBRad());
		border.setDamageAmount(5.0);
		border.setDamageBuffer(0);
		
		beaconizeSafeZones();
		despenseSafeZoneID();
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				AF.msgAll(AF.boldYellow + "Welcome to BORDER BUSTERS!!!");
				USound.AllPSound(Sound.EXPLODE, 2.75f, 1.56f);
				AF.msgAll(AF.boldYellow + "GET READY!");
				USound.AllPSound(Sound.NOTE_PLING, 3f, 1.25f);
			}
		}.runTaskLater(ArcadeCore.getPlugin(), Tickifier.tickify(5 + 2, Time.SECONDS));
		
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				AF.msgAll(AF.boldYellow + "3...");
				USound.AllPSound(Sound.NOTE_PLING, 2.75f, 1.56f);
				
			}
		}.runTaskLater(ArcadeCore.getPlugin(), Tickifier.tickify(5 + 2 + 1, Time.SECONDS));
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				AF.msgAll(AF.boldGold + "2...");
				USound.AllPSound(Sound.NOTE_PLING, 2.75f, 1.56f);
				
			}
		}.runTaskLater(ArcadeCore.getPlugin(), Tickifier.tickify(5 + 2 + 2, Time.SECONDS));
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				AF.msgAll(AF.boldRed + "1...");
				USound.AllPSound(Sound.NOTE_PLING, 2.75f, 1.56f);
				callBorder(currentSafeZoneID);
			}
		}.runTaskLater(ArcadeCore.getPlugin(), Tickifier.tickify(5 + 2 + 3, Time.SECONDS));
	}
	
	@Override
	public void shutdown(){
		resetValues();
	}

}