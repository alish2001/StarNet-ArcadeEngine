package com.starnetmc.ArcadeEngine.Games.DragonSword;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.starnetmc.ArcadeCore;
import com.starnetmc.ArcadeEngine.Games.Game;
import com.starnetmc.ArcadeEngine.Games.DragonSword.Kits.KitDSKnight;
import com.starnetmc.ArcadeEngine.Games.DragonSword.Rewards.RewardDSWinner;
import com.starnetmc.ArcadeEngine.Managers.Achievements.Achievement;
import com.starnetmc.ArcadeEngine.Managers.Classes.Kit;
import com.starnetmc.ArcadeEngine.Managers.GameProperties.GameProperties;
import com.starnetmc.ArcadeEngine.Managers.Maps.MapTypes.DSMap;
import com.starnetmc.ArcadeEngine.Managers.Rewards.Reward;
import com.starnetmc.ArcadeEngine.Managers.Scoreboards.Starboard;
import com.starnetmc.ArcadeEngine.Utils.AF;
import com.starnetmc.ArcadeEngine.Utils.ItemFactory;
import com.starnetmc.ArcadeEngine.Utils.USound;
import com.starnetmc.ArcadeEngine.Utils.UVector;
import com.starnetmc.core.util.StarMap;
import com.starnetmc.core.util.Tickifier;
import com.starnetmc.core.util.Tickifier.Time;
import com.starnetmc.core.util.UPlayer;

public class DragonSword extends Game implements Listener {
	
	private DSMap map;
	public static String swordWielder = "No One";
	static long time = 0;
    static boolean activePortal = false;
    static boolean swordDropped = false;
    static boolean distruction = false;
	
	public DragonSword(List<Listener> gameListeners, int gameTaskID, List<Kit> gameKits, GameProperties gameProperties, Starboard gameScoreboard, List<Reward> gameRewards, List<Achievement> gameAchivements, ArrayList<String> gameDesc, List<String> gameHints) {
		super(gameListeners, gameTaskID, gameKits, gameProperties, gameScoreboard, gameRewards, gameAchivements, gameDesc, gameHints);
		manager = ArcadeCore.getArcadeManager();
		
		setDesc(new ArrayList<String>());
		addDesc("Fight to the DEATH with your opponents");
		addDesc("Take control of the almighty DragonSword");
		addDesc("and Win baby WIN!!!");
		
		setHints(new ArrayList<String>());
		addHint("When you take control of The Dragon Sword, your armor and items melt down from the amazing power.");
		addHint("The only way to get your armor and items back when you have the Sword is by getting killed.");
		addHint("1:20 After the Sword drops, the portal opens. Try to not lose the Sword.");
		addHint("The Dragon Sword has some good knockback power. Use it to send your foes flying!");
		
		setTaskID(-1);
		GameProperties properties = new GameProperties();
		properties.setMap(manager.getMapper().getActiveMap());
		properties.setPlayersNeeded(4);
		properties.setForeverSpec(false);
		properties.setPvE(false);
		properties.setGracePeriod(false);
		setProperties(properties);
	    
		setScoreboard(new DragonSwordScoreboard());
		
		setAchievements(new ArrayList<Achievement>());
		
		setRewards(new ArrayList<Reward>());
		addReward(new RewardDSWinner(null, 0));
		
		setKits(new ArrayList<Kit>());
		addKit(new KitDSKnight(null, null, -1, null, null, null, null, null));
		
		setListeners(new ArrayList<Listener>());
		addListener(this);
		addListener(new DragonSwordListeners());
	}
	
	public static void dropDragonSword(Location dropLoc){
		StarMap<Enchantment, Integer> enchs = new StarMap<Enchantment, Integer>();
		enchs.put(Enchantment.DURABILITY, 10);
		enchs.put(Enchantment.KNOCKBACK,2);
		enchs.put(Enchantment.DAMAGE_ALL, 3);
		
		ItemStack item = ItemFactory.createItem(Material.DIAMOND_SWORD, AF.boldRed + "The Dragon Sword", enchs);
        dropLoc.getWorld().dropItemNaturally(dropLoc, item);
	}
	
	public void forgeSword(){
		//Ritual
		map.getSwordSpawn().getWorld().strikeLightningEffect(map.getSwordSpawn());
		map.getSwordSpawn().getWorld().playEffect(map.getSwordSpawn(), Effect.SMOKE, 60);
		dropDragonSword(map.getSwordSpawn());
		swordDropped = true;
	}
	
	
	public void forgePortal(){
		//Ritual
		
		map.getPortalSpawn().getWorld().strikeLightningEffect(map.getPortalSpawn());
		for (Player p : Bukkit.getServer().getOnlinePlayers()){
			if (p.getLocation().distanceSquared(map.getPortalSpawn()) <= 4*4){
				p.setVelocity(UVector.generateRandomVector(1.0f, 1.5f, 1.0f));
			}
			
		}
		
		map.getPortalSpawn().getWorld().playSound(map.getPortalSpawn(), Sound.ENDERDRAGON_GROWL, 1f, 2f);
		
		//First Portal Layer
		map.getPortalSpawn().getWorld().playEffect(map.getPortalSpawn().add(0, 0, 0), Effect.STEP_SOUND, Material.OBSIDIAN);
		map.getPortalSpawn().getWorld().playEffect(map.getPortalSpawn().add(1, 0, 0), Effect.STEP_SOUND, Material.OBSIDIAN);
		map.getPortalSpawn().getWorld().playEffect(map.getPortalSpawn().add(2, 0, 0), Effect.STEP_SOUND, Material.OBSIDIAN);
		map.getPortalSpawn().getWorld().playEffect(map.getPortalSpawn().add(-1, 0, 0), Effect.STEP_SOUND, Material.OBSIDIAN);
		map.getPortalSpawn().getWorld().playEffect(map.getPortalSpawn().add(-2, 0, 0), Effect.STEP_SOUND, Material.OBSIDIAN);
		
		map.getPortalSpawn().add(0, 0, 0).getBlock().setType(Material.OBSIDIAN);
		map.getPortalSpawn().add(1, 0, 0).getBlock().setType(Material.OBSIDIAN);
		map.getPortalSpawn().add(2, 0, 0).getBlock().setType(Material.OBSIDIAN);
		map.getPortalSpawn().add(-1, 0, 0).getBlock().setType(Material.OBSIDIAN);
		map.getPortalSpawn().add(-2, 0, 0).getBlock().setType(Material.OBSIDIAN);
		
		//Second Portal Layer
		map.getPortalSpawn().getWorld().playEffect(map.getPortalSpawn().add(2, 1, 0), Effect.STEP_SOUND, Material.OBSIDIAN);
		map.getPortalSpawn().getWorld().playEffect(map.getPortalSpawn().add(-2, 1, 0), Effect.STEP_SOUND, Material.OBSIDIAN);
		
		map.getPortalSpawn().add(2, 1, 0).getBlock().setType(Material.OBSIDIAN);
		map.getPortalSpawn().add(-2, 1, 0).getBlock().setType(Material.OBSIDIAN);
		
		//Third Portal Layer
		map.getPortalSpawn().getWorld().playEffect(map.getPortalSpawn().add(2, 2, 0), Effect.STEP_SOUND, Material.OBSIDIAN);
		map.getPortalSpawn().getWorld().playEffect(map.getPortalSpawn().add(-2, 2, 0), Effect.STEP_SOUND, Material.OBSIDIAN);
		
		map.getPortalSpawn().add(2, 2, 0).getBlock().setType(Material.OBSIDIAN);
		map.getPortalSpawn().add(-2, 2, 0).getBlock().setType(Material.OBSIDIAN);
		
		//Fourth Portal Layer
		map.getPortalSpawn().getWorld().playEffect(map.getPortalSpawn().add(2, 3, 0), Effect.STEP_SOUND, Material.OBSIDIAN);
		map.getPortalSpawn().getWorld().playEffect(map.getPortalSpawn().add(-2, 3, 0), Effect.STEP_SOUND, Material.OBSIDIAN);
		
		map.getPortalSpawn().add(2, 3, 0).getBlock().setType(Material.OBSIDIAN);
		map.getPortalSpawn().add(-2, 3, 0).getBlock().setType(Material.OBSIDIAN);
		
		//Fifth Portal Layer
		map.getPortalSpawn().getWorld().playEffect(map.getPortalSpawn().add(2, 4, 0), Effect.STEP_SOUND, Material.OBSIDIAN);
		map.getPortalSpawn().getWorld().playEffect(map.getPortalSpawn().add(-2, 4, 0), Effect.STEP_SOUND, Material.OBSIDIAN);
		
		map.getPortalSpawn().add(2, 4, 0).getBlock().setType(Material.OBSIDIAN);
		map.getPortalSpawn().add(-2, 4, 0).getBlock().setType(Material.OBSIDIAN);
		
		//Sixth Portal Layer
		map.getPortalSpawn().getWorld().playEffect(map.getPortalSpawn().add(2, 5, 0), Effect.STEP_SOUND, Material.OBSIDIAN);
		map.getPortalSpawn().getWorld().playEffect(map.getPortalSpawn().add(-2, 5, 0), Effect.STEP_SOUND, Material.OBSIDIAN);
		
		map.getPortalSpawn().add(2, 5, 0).getBlock().setType(Material.OBSIDIAN);
		map.getPortalSpawn().add(-2, 5, 0).getBlock().setType(Material.OBSIDIAN);
		
		//Seventh Portal Layer
		map.getPortalSpawn().getWorld().playEffect(map.getPortalSpawn().add(0, 6, 0), Effect.STEP_SOUND, Material.OBSIDIAN);
		map.getPortalSpawn().getWorld().playEffect(map.getPortalSpawn().add(1, 6, 0), Effect.STEP_SOUND, Material.OBSIDIAN);
		map.getPortalSpawn().getWorld().playEffect(map.getPortalSpawn().add(2, 6, 0), Effect.STEP_SOUND, Material.OBSIDIAN);
		map.getPortalSpawn().getWorld().playEffect(map.getPortalSpawn().add(-1, 6, 0), Effect.STEP_SOUND, Material.OBSIDIAN);
		map.getPortalSpawn().getWorld().playEffect(map.getPortalSpawn().add(-2, 6, 0), Effect.STEP_SOUND, Material.OBSIDIAN);
		
		map.getPortalSpawn().add(0, 6, 0).getBlock().setType(Material.OBSIDIAN);
		map.getPortalSpawn().add(1, 6, 0).getBlock().setType(Material.OBSIDIAN);
		map.getPortalSpawn().add(2, 6, 0).getBlock().setType(Material.OBSIDIAN);
		map.getPortalSpawn().add(-1, 6, 0).getBlock().setType(Material.OBSIDIAN);
		map.getPortalSpawn().add(-2, 6, 0).getBlock().setType(Material.OBSIDIAN);
	}
    
	
	public void lightPortal(){
		
			for (Player p : Bukkit.getServer().getOnlinePlayers()){
				if (p.getLocation().distanceSquared(map.getPortalSpawn()) <= 4*4){
					p.setVelocity(UVector.generateRandomVector(1.0f, 1.5f, 1.0f));
				}
				
			map.getPortalSpawn().getWorld().strikeLightningEffect(map.getPortalSpawn().add(0, 1, 0));
			
			//First inner portal layer
			map.getPortalSpawn().add(0, 1, 0).getBlock().setType(Material.PORTAL);
			map.getPortalSpawn().add(1, 1, 0).getBlock().setType(Material.PORTAL);
			map.getPortalSpawn().add(-1, 1, 0).getBlock().setType(Material.PORTAL);
			
			map.getPortalSpawn().getWorld().playEffect(map.getPortalSpawn().add(0, 1, 0), Effect.STEP_SOUND, Material.PORTAL);
			map.getPortalSpawn().getWorld().playEffect(map.getPortalSpawn().add(-1, 1, 0), Effect.STEP_SOUND, Material.PORTAL);
			map.getPortalSpawn().getWorld().playEffect(map.getPortalSpawn().add(1, 1, 0), Effect.STEP_SOUND, Material.PORTAL);
			
			//Second inner portal layer
			map.getPortalSpawn().add(0, 2, 0).getBlock().setType(Material.PORTAL);
			map.getPortalSpawn().add(1, 2, 0).getBlock().setType(Material.PORTAL);
			map.getPortalSpawn().add(-1, 2, 0).getBlock().setType(Material.PORTAL);
			
			map.getPortalSpawn().getWorld().playEffect(map.getPortalSpawn().add(0, 2, 0), Effect.STEP_SOUND, Material.PORTAL);
			map.getPortalSpawn().getWorld().playEffect(map.getPortalSpawn().add(-1, 2, 0), Effect.STEP_SOUND, Material.PORTAL);
			map.getPortalSpawn().getWorld().playEffect(map.getPortalSpawn().add(1, 2, 0), Effect.STEP_SOUND, Material.PORTAL);
			
			//Third inner portal layer
			map.getPortalSpawn().add(0, 3, 0).getBlock().setType(Material.PORTAL);
			map.getPortalSpawn().add(1, 3, 0).getBlock().setType(Material.PORTAL);
			map.getPortalSpawn().add(-1, 3, 0).getBlock().setType(Material.PORTAL);
			
			map.getPortalSpawn().getWorld().playEffect(map.getPortalSpawn().add(0, 3, 0), Effect.STEP_SOUND, Material.PORTAL);
			map.getPortalSpawn().getWorld().playEffect(map.getPortalSpawn().add(-1, 3, 0), Effect.STEP_SOUND, Material.PORTAL);
			map.getPortalSpawn().getWorld().playEffect(map.getPortalSpawn().add(1, 3, 0), Effect.STEP_SOUND, Material.PORTAL);
			
			//Fourth inner portal layer
			map.getPortalSpawn().add(0, 4, 0).getBlock().setType(Material.PORTAL);
			map.getPortalSpawn().add(1, 4, 0).getBlock().setType(Material.PORTAL);
			map.getPortalSpawn().add(-1, 4, 0).getBlock().setType(Material.PORTAL);
			map.getPortalSpawn().getWorld().playEffect(map.getPortalSpawn().add(0, 4, 0), Effect.STEP_SOUND, Material.PORTAL);
			map.getPortalSpawn().getWorld().playEffect(map.getPortalSpawn().add(-1, 4, 0), Effect.STEP_SOUND, Material.PORTAL);
			map.getPortalSpawn().getWorld().playEffect(map.getPortalSpawn().add(1, 4, 0), Effect.STEP_SOUND, Material.PORTAL);
			
			//Fifth inner portal layer
			map.getPortalSpawn().add(0, 5, 0).getBlock().setType(Material.PORTAL);
			map.getPortalSpawn().add(1, 5, 0).getBlock().setType(Material.PORTAL);
			map.getPortalSpawn().add(-1, 5, 0).getBlock().setType(Material.PORTAL);
			
			map.getPortalSpawn().getWorld().playEffect(map.getPortalSpawn().add(0, 5, 0), Effect.STEP_SOUND, Material.PORTAL);
			map.getPortalSpawn().getWorld().playEffect(map.getPortalSpawn().add(-1, 5, 0), Effect.STEP_SOUND, Material.PORTAL);
			map.getPortalSpawn().getWorld().playEffect(map.getPortalSpawn().add(1, 5, 0), Effect.STEP_SOUND, Material.PORTAL);
			
			map.getPortalSpawn().getWorld().playSound(map.getPortalSpawn(), Sound.PORTAL, 1, 2);
			
			activePortal = true;
		}
	}
	
	public void portal(){
		map.getPortalSpawn().getBlock().setType(Material.PORTAL);
	}
	
	public void destructionEnd(){
		
		distruction = true;
		
		//Pod Floor
		map.getPortalSpawn().getWorld().getBlockAt(map.getPortalSpawn().add(0, 0, 1)).setType(Material.OBSIDIAN);
		map.getPortalSpawn().getWorld().getBlockAt(map.getPortalSpawn().add(1, 0, 1)).setType(Material.OBSIDIAN);
		map.getPortalSpawn().getWorld().getBlockAt(map.getPortalSpawn().add(-1, 0, 1)).setType(Material.OBSIDIAN);
		map.getPortalSpawn().getWorld().getBlockAt(map.getPortalSpawn().add(0, 0, -1)).setType(Material.OBSIDIAN);
		map.getPortalSpawn().getWorld().getBlockAt(map.getPortalSpawn().add(1, 0, -1)).setType(Material.OBSIDIAN);
		map.getPortalSpawn().getWorld().getBlockAt(map.getPortalSpawn().add(-1, 0, -1)).setType(Material.OBSIDIAN);
		
		map.getPortalSpawn().getWorld().playEffect(map.getPortalSpawn().add(0, 0, 1), Effect.STEP_SOUND, Material.OBSIDIAN);
		map.getPortalSpawn().getWorld().playEffect(map.getPortalSpawn().add(1, 0, 1), Effect.STEP_SOUND, Material.OBSIDIAN);
		map.getPortalSpawn().getWorld().playEffect(map.getPortalSpawn().add(-1, 0, 1), Effect.STEP_SOUND, Material.OBSIDIAN);
		map.getPortalSpawn().getWorld().playEffect(map.getPortalSpawn().add(0, 0, -1), Effect.STEP_SOUND, Material.OBSIDIAN);
		map.getPortalSpawn().getWorld().playEffect(map.getPortalSpawn().add(1, 0, -1), Effect.STEP_SOUND, Material.OBSIDIAN);
		map.getPortalSpawn().getWorld().playEffect(map.getPortalSpawn().add(-1, 0, -1), Effect.STEP_SOUND, Material.OBSIDIAN);
		
		//Pod Ceiling
		map.getPortalSpawn().getWorld().getBlockAt(map.getPortalSpawn().add(0, 4, 1)).setType(Material.OBSIDIAN);
		map.getPortalSpawn().getWorld().getBlockAt(map.getPortalSpawn().add(1, 4, 1)).setType(Material.OBSIDIAN);
		map.getPortalSpawn().getWorld().getBlockAt(map.getPortalSpawn().add(-1, 4, 1)).setType(Material.OBSIDIAN);
		map.getPortalSpawn().getWorld().getBlockAt(map.getPortalSpawn().add(0, 4, -1)).setType(Material.OBSIDIAN);
		map.getPortalSpawn().getWorld().getBlockAt(map.getPortalSpawn().add(1, 4, -1)).setType(Material.OBSIDIAN);
		map.getPortalSpawn().getWorld().getBlockAt(map.getPortalSpawn().add(-1, 4, -1)).setType(Material.OBSIDIAN);
		
		map.getPortalSpawn().getWorld().playEffect(map.getPortalSpawn().add(0, 4, 1), Effect.STEP_SOUND, Material.OBSIDIAN);
		map.getPortalSpawn().getWorld().playEffect(map.getPortalSpawn().add(1, 4, 1), Effect.STEP_SOUND, Material.OBSIDIAN);
		map.getPortalSpawn().getWorld().playEffect(map.getPortalSpawn().add(-1, 4, 1), Effect.STEP_SOUND, Material.OBSIDIAN);
		map.getPortalSpawn().getWorld().playEffect(map.getPortalSpawn().add(0, 4, -1), Effect.STEP_SOUND, Material.OBSIDIAN);
		map.getPortalSpawn().getWorld().playEffect(map.getPortalSpawn().add(1, 4, -1), Effect.STEP_SOUND, Material.OBSIDIAN);
		map.getPortalSpawn().getWorld().playEffect(map.getPortalSpawn().add(-1, 4, -1), Effect.STEP_SOUND, Material.OBSIDIAN);
		
		//Pod Wall
		map.getPortalSpawn().getWorld().getBlockAt(map.getPortalSpawn().add(0, 1, 1)).setType(Material.OBSIDIAN);
		map.getPortalSpawn().getWorld().getBlockAt(map.getPortalSpawn().add(1, 1, 1)).setType(Material.OBSIDIAN);
		map.getPortalSpawn().getWorld().getBlockAt(map.getPortalSpawn().add(-1, 1, 1)).setType(Material.OBSIDIAN);
		map.getPortalSpawn().getWorld().getBlockAt(map.getPortalSpawn().add(0, 1, -1)).setType(Material.OBSIDIAN);
		map.getPortalSpawn().getWorld().getBlockAt(map.getPortalSpawn().add(1, 1, -1)).setType(Material.OBSIDIAN);
		map.getPortalSpawn().getWorld().getBlockAt(map.getPortalSpawn().add(-1, 1, -1)).setType(Material.OBSIDIAN);
		
		map.getPortalSpawn().getWorld().playEffect(map.getPortalSpawn().add(0, 1, 1), Effect.STEP_SOUND, Material.OBSIDIAN);
		map.getPortalSpawn().getWorld().playEffect(map.getPortalSpawn().add(1, 1, 1), Effect.STEP_SOUND, Material.OBSIDIAN);
		map.getPortalSpawn().getWorld().playEffect(map.getPortalSpawn().add(-1, 1, 1), Effect.STEP_SOUND, Material.OBSIDIAN);
		map.getPortalSpawn().getWorld().playEffect(map.getPortalSpawn().add(0, 1, -1), Effect.STEP_SOUND, Material.OBSIDIAN);
		map.getPortalSpawn().getWorld().playEffect(map.getPortalSpawn().add(1, 1, -1), Effect.STEP_SOUND, Material.OBSIDIAN);
		map.getPortalSpawn().getWorld().playEffect(map.getPortalSpawn().add(-1, 1, -1), Effect.STEP_SOUND, Material.OBSIDIAN);
		
		map.getPortalSpawn().getWorld().getBlockAt(map.getPortalSpawn().add(0, 3, 1)).setType(Material.OBSIDIAN);
		map.getPortalSpawn().getWorld().getBlockAt(map.getPortalSpawn().add(1, 3, 1)).setType(Material.OBSIDIAN);
		map.getPortalSpawn().getWorld().getBlockAt(map.getPortalSpawn().add(-1, 3, 1)).setType(Material.OBSIDIAN);
		map.getPortalSpawn().getWorld().getBlockAt(map.getPortalSpawn().add(0, 3, -1)).setType(Material.OBSIDIAN);
		map.getPortalSpawn().getWorld().getBlockAt(map.getPortalSpawn().add(1, 3, -1)).setType(Material.OBSIDIAN);
		map.getPortalSpawn().getWorld().getBlockAt(map.getPortalSpawn().add(-1, 3, -1)).setType(Material.OBSIDIAN);
		
		map.getPortalSpawn().getWorld().playEffect(map.getPortalSpawn().add(0, 3, 1), Effect.STEP_SOUND, Material.OBSIDIAN);
		map.getPortalSpawn().getWorld().playEffect(map.getPortalSpawn().add(1, 3, 1), Effect.STEP_SOUND, Material.OBSIDIAN);
		map.getPortalSpawn().getWorld().playEffect(map.getPortalSpawn().add(-1, 3, 1), Effect.STEP_SOUND, Material.OBSIDIAN);
		map.getPortalSpawn().getWorld().playEffect(map.getPortalSpawn().add(0, 3, -1), Effect.STEP_SOUND, Material.OBSIDIAN);
		map.getPortalSpawn().getWorld().playEffect(map.getPortalSpawn().add(1, 3, -1), Effect.STEP_SOUND, Material.OBSIDIAN);
		map.getPortalSpawn().getWorld().playEffect(map.getPortalSpawn().add(-1, 3, -1), Effect.STEP_SOUND, Material.OBSIDIAN);
		
		map.getPortalSpawn().getWorld().getBlockAt(map.getPortalSpawn().add(0, 2, 1)).setType(Material.GLASS);
		map.getPortalSpawn().getWorld().getBlockAt(map.getPortalSpawn().add(1, 2, 1)).setType(Material.OBSIDIAN);
		map.getPortalSpawn().getWorld().getBlockAt(map.getPortalSpawn().add(-1, 2, 1)).setType(Material.OBSIDIAN);
		map.getPortalSpawn().getWorld().getBlockAt(map.getPortalSpawn().add(0, 2, -1)).setType(Material.GLASS);
		map.getPortalSpawn().getWorld().getBlockAt(map.getPortalSpawn().add(1, 2, -1)).setType(Material.OBSIDIAN);
		map.getPortalSpawn().getWorld().getBlockAt(map.getPortalSpawn().add(-1, 2, -1)).setType(Material.OBSIDIAN);
		
		map.getPortalSpawn().getWorld().playEffect(map.getPortalSpawn().add(0, 3, 1), Effect.STEP_SOUND, Material.GLASS);
		map.getPortalSpawn().getWorld().playEffect(map.getPortalSpawn().add(1, 3, 1), Effect.STEP_SOUND, Material.OBSIDIAN);
		map.getPortalSpawn().getWorld().playEffect(map.getPortalSpawn().add(-1, 3, 1), Effect.STEP_SOUND, Material.OBSIDIAN);
		map.getPortalSpawn().getWorld().playEffect(map.getPortalSpawn().add(0, 3, -1), Effect.STEP_SOUND, Material.GLASS);
		map.getPortalSpawn().getWorld().playEffect(map.getPortalSpawn().add(1, 3, -1), Effect.STEP_SOUND, Material.OBSIDIAN);
		map.getPortalSpawn().getWorld().playEffect(map.getPortalSpawn().add(-1, 3, -1), Effect.STEP_SOUND, Material.OBSIDIAN);
		
		AF.msgAll(AF.boldYellow + "Dragon: " + swordWielder + ", the chosen hero shall now be safe.");
		USound.AllPSound(Sound.NOTE_PLING, 1.25f, 1f);
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				AF.msgAll(AF.boldYellow + "Dragon: Brace yourself foolish mortals.");
				USound.AllPSound(Sound.ENDERDRAGON_GROWL, 1f, 1f);
			}
		}.runTaskLater(ArcadeCore.getPlugin(), 20L);
		
		
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				
				for (Player p : manager.getPlayerManager().getAlivePlayers()){
					if (p.getName() == swordWielder) return;
					p.getLocation().getWorld().strikeLightning(p.getLocation());
				}
				
				for (Location s : map.getSpawns()){
					s.getWorld().strikeLightning(s);
					@SuppressWarnings("deprecation")
					FallingBlock fb = s.getWorld().spawnFallingBlock(s, s.getBlock().getType(), (byte)0);
					s.getBlock().setType(Material.NETHERRACK);
					fb.setDropItem(false);
					fb.setVelocity(new Vector(0, 0.5, 0.1));
					s.add(0, 1, 0).getBlock().setType(Material.FIRE);
				}
				
			}
		}.runTaskLater(ArcadeCore.getPlugin() , Tickifier.tickify(5, Time.SECONDS));
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				
				map.getPortalSpawn().getWorld().getBlockAt(map.getPortalSpawn().add(0, 4, 1)).setType(Material.AIR);
				map.getPortalSpawn().getWorld().getBlockAt(map.getPortalSpawn().add(1, 4, 1)).setType(Material.AIR);
				map.getPortalSpawn().getWorld().getBlockAt(map.getPortalSpawn().add(-1, 4, 1)).setType(Material.AIR);
				map.getPortalSpawn().getWorld().getBlockAt(map.getPortalSpawn().add(0, 4, -1)).setType(Material.AIR);
				map.getPortalSpawn().getWorld().getBlockAt(map.getPortalSpawn().add(1, 4, -1)).setType(Material.AIR);
				map.getPortalSpawn().getWorld().getBlockAt(map.getPortalSpawn().add(-1, 4, -1)).setType(Material.AIR);
				
				map.getPortalSpawn().getWorld().playEffect(map.getPortalSpawn().add(0, 4, 1), Effect.STEP_SOUND, Material.OBSIDIAN);
				map.getPortalSpawn().getWorld().playEffect(map.getPortalSpawn().add(1, 4, 1), Effect.STEP_SOUND, Material.OBSIDIAN);
				map.getPortalSpawn().getWorld().playEffect(map.getPortalSpawn().add(-1, 4, 1), Effect.STEP_SOUND, Material.OBSIDIAN);
				map.getPortalSpawn().getWorld().playEffect(map.getPortalSpawn().add(0, 4, -1), Effect.STEP_SOUND, Material.OBSIDIAN);
				map.getPortalSpawn().getWorld().playEffect(map.getPortalSpawn().add(1, 4, -1), Effect.STEP_SOUND, Material.OBSIDIAN);
				map.getPortalSpawn().getWorld().playEffect(map.getPortalSpawn().add(-1, 4, -1), Effect.STEP_SOUND, Material.OBSIDIAN);
				
				map.getPortalSpawn().getWorld().getBlockAt(map.getPortalSpawn().add(2, 4, 0)).setType(Material.AIR);
				map.getPortalSpawn().getWorld().getBlockAt(map.getPortalSpawn().add(-2, 4, 0)).setType(Material.AIR);
				map.getPortalSpawn().getWorld().getBlockAt(map.getPortalSpawn().add(0, 4, 0)).setType(Material.AIR);
				map.getPortalSpawn().getWorld().getBlockAt(map.getPortalSpawn().add(-1, 4, 0)).setType(Material.AIR);
				map.getPortalSpawn().getWorld().getBlockAt(map.getPortalSpawn().add(1, 4, 0)).setType(Material.AIR);
				
				map.getPortalSpawn().getWorld().playEffect(map.getPortalSpawn().add(2, 4, 0), Effect.STEP_SOUND, Material.OBSIDIAN);
				map.getPortalSpawn().getWorld().playEffect(map.getPortalSpawn().add(-2, 4, 0), Effect.STEP_SOUND, Material.OBSIDIAN);
				map.getPortalSpawn().getWorld().playEffect(map.getPortalSpawn().add(0, 4, 0), Effect.STEP_SOUND, Material.OBSIDIAN);
				map.getPortalSpawn().getWorld().playEffect(map.getPortalSpawn().add(-1, 4, 0), Effect.STEP_SOUND, Material.OBSIDIAN);
				map.getPortalSpawn().getWorld().playEffect(map.getPortalSpawn().add(1, 4, 0), Effect.STEP_SOUND, Material.OBSIDIAN);
				
				for (Player p : manager.getPlayerManager().getAlivePlayers()){
					if (p.getName() == swordWielder) return;
					
					@SuppressWarnings("deprecation")
					FallingBlock fb = p.getWorld().spawnFallingBlock(p.getLocation().add(0, 10, 0), Material.ANVIL, (byte)0);
					fb.setDropItem(false);
				}
				
				USound.PSound(UPlayer.getOnlinePlayerFromName(swordWielder), Sound.EXPLODE, 2.25f, 1.6f);
				UPlayer.getOnlinePlayerFromName(swordWielder).getWorld().playEffect(UPlayer.getOnlinePlayerFromName(swordWielder).getLocation(), Effect.SMOKE, 20);
				UPlayer.getOnlinePlayerFromName(swordWielder).setVelocity(new Vector(0, 5, 0));
				
				AF.announceWinner(swordWielder);
				stop(false);
				
			}
		}.runTaskLater(ArcadeCore.getPlugin(), Tickifier.tickify(10, Time.SECONDS));
		
	}
	
	@EventHandler
	public void endCheck(PlayerPortalEvent e){
		Player p = e.getPlayer();
		e.setCancelled(true);
		if (distruction) return;
		if (swordWielder.equalsIgnoreCase(p.getName())){
			
			AF.announceWinner(swordWielder);
			stop(false);
		} else {
			p.sendMessage(AF.boldRed + "You do not wield The Dragon Sword!");
			p.sendMessage(AF.boldYellow + "Dragon: Go away, you FOOL!");
				if (p.getLocation().distanceSquared(map.getPortalSpawn()) <= 3*3){
					p.setVelocity(UVector.generateRandomVector(0.75f, 1.0f, 0.75f));
			    }
				
			return;
		}
	}
	
	@Override
	public void start(){
		map = (DSMap) manager.getMapper().getActiveMap();
		
		setTaskID(Bukkit.getScheduler().scheduleSyncRepeatingTask(ArcadeCore.getPlugin(), new Runnable() {
			
			@Override
			public void run() {
				
				if (time == Tickifier.tickify(2, Time.MINUTES)){
					AF.msgAll(AF.boldRed + "THE DRAGON SWORD HAS LANDED!");
					USound.AllPSound(Sound.NOTE_PLING, 1.25f, 1f);
					forgeSword();
			}
				
				if (time == Tickifier.tickify(3, Time.MINUTES)){
					AF.msgAll(AF.boldPurple + "THE PORTAL IS OPPENING!");
					USound.AllPSound(Sound.NOTE_PLING, 1.25f, 1f);
					forgePortal();
			}
				
				if (time == Tickifier.tickify(210, Time.SECONDS)){
					AF.msgAll(AF.boldYellow + "THE PORTAL HAS OPENED! TAKE THE SWORD AND RUN!");
					USound.AllPSound(Sound.NOTE_PLING, 1.25f, 1f);
					lightPortal();
			}
				
				
			   time+= 20;
			}
		}, 0L, 20L));
		
	}
	
	@Override
	public void shutdown(){
		if (swordWielder.equalsIgnoreCase("No One")) return;
		Player p = Bukkit.getServer().getPlayer(swordWielder);
		if (p == null) return;
		p.getInventory().remove(Material.DIAMOND_SWORD);
		
		swordWielder = "No One";
	}
	
}