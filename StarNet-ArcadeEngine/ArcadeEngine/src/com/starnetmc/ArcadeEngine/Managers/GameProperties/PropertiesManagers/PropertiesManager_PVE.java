package com.starnetmc.ArcadeEngine.Managers.GameProperties.PropertiesManagers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import com.starnetmc.ArcadeCore;
import com.starnetmc.ArcadeEngine.Events.CustomPlayerDeathEvent;
import com.starnetmc.ArcadeEngine.Managers.GameProperties.GamePropertiesManager;
import com.starnetmc.ArcadeEngine.Managers.GameProperties.PropertiesManager;
import com.starnetmc.ArcadeEngine.Managers.Players.PlayerState;
import com.starnetmc.ArcadeEngine.Utils.AF;
import com.starnetmc.ArcadeEngine.Utils.USound;
import com.starnetmc.ArcadeEngine.Utils.UVector;
import com.starnetmc.core.util.Tickifier;
import com.starnetmc.core.util.Tickifier.Time;
import com.starnetmc.core.util.UPlayer;

public class PropertiesManager_PVE extends PropertiesManager {

	public PropertiesManager_PVE(GamePropertiesManager gamePropertiesManager, String propertiesManagerName) {
		super(gamePropertiesManager, "PVE");
	}
	
	public PropertiesManager_PVE(GamePropertiesManager gamePropertiesManager) {
		super(gamePropertiesManager);
		setName("PVE");
	}
	
	private void runDeathSquence(Player p){
		USound.PSound(p, Sound.HURT_FLESH, 1.25f, 1.5f);
		p.getWorld().playEffect(p.getLocation(), Effect.STEP_SOUND, Material.REDSTONE_WIRE);
		
		List<Item> items = new ArrayList<Item>();
        for(int a1 = 0; a1 < 5; a1++){
            Item i = p.getWorld().dropItem(p.getLocation(), new ItemStack(Material.RED_ROSE));
            i.setVelocity(UVector.generateRandomVector(0.1f, 0.1f, 0.1f));
            items.add(i);
            i.setPickupDelay(Integer.MAX_VALUE);
        }
        
        for (Item i : items){
        	i.remove();
        }
        
        items = null;
        
		if (getGamePropertiesManager().getProperties().getDeathItemDrop()){
			for (ItemStack i : p.getInventory().getContents()){
				if (i == null) continue;
				p.getLocation().getWorld().dropItemNaturally(p.getLocation(), i);
			}
		}
        
		p.setGameMode(GameMode.SPECTATOR);
		getGamePropertiesManager().getManager().getPlayerManager().setPlayerState(p, PlayerState.DEAD);
		p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 1, 2));
		p.setHealth(p.getMaxHealth());
		UPlayer.resetPlayer(p);
		getGamePropertiesManager().getManager().getKitManager().removeKitItems(p);
		p.teleport(getGamePropertiesManager().getProperties().getMap().getSpecSpawn());
	}
	
	private void runRespawnSequence(Player p){
		p.teleport(getGamePropertiesManager().getProperties().getMap().getRandomSpawnPoint());
		p.setGameMode(getGamePropertiesManager().getProperties().getDefaultGameMode());
		getGamePropertiesManager().getManager().getKitManager().setKitItems(p, getGamePropertiesManager().getManager().getKitManager().getKit(p));
		getGamePropertiesManager().getManager().getPlayerManager().setPlayerState(p, PlayerState.ALIVE);
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onEvP(EntityDamageByEntityEvent e){
		//Checks if it's a player and entity
		if (e.getDamager() instanceof Player) return;
		if (!(e.getEntity() instanceof Player)) return; 

		//Checks PVE properties
		if (!getGamePropertiesManager().getProperties().getPvE()){
			e.setCancelled(true);
			return;
		}

		
		Entity reker = e.getDamager();
		final Player rekt = (Player) e.getEntity();
		
		//Checks if it is the final blow
		if (rekt.getHealth() - e.getFinalDamage() < 1){
			e.setCancelled(true);
            runDeathSquence(rekt);
			
			AF.broadCastDeath(reker.getName(), rekt.getName(), reker.getType().getName());			
			Bukkit.getServer().getPluginManager().callEvent(new CustomPlayerDeathEvent(rekt, reker));
			
			//If respawn is not enabled
			if (getGamePropertiesManager().getProperties().getForeverSpec() == true) {
				getGamePropertiesManager().getManager().getKitManager().removeKit(rekt);
				rekt.sendMessage(AF.boldRed + "YOU ARE OUT OF THE GAME!");
				rekt.sendMessage(AF.boldYellow + "Stay until the end to get your rewards!");
				return;
			}
			
			//If respawn is enabled
			rekt.sendMessage(AF.boldYellow + "YOU WILL RESPAWN IN " + AF.boldRed + Tickifier.unTickify(getGamePropertiesManager().getProperties().getRespawnTime(), Time.SECONDS) + AF.boldYellow + " SECONDS!");
		
			
			new BukkitRunnable() {
				
				@Override
				public void run() {
					runRespawnSequence(rekt);
				}
			}.runTaskLater(ArcadeCore.getPlugin(), getGamePropertiesManager().getProperties().getRespawnTime());
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onBowPvE(EntityDamageByEntityEvent e){
		
		//Checks if an arrow is damaging a player
		if (!(e.getDamager() instanceof Arrow)) return;
		if (!(e.getEntity() instanceof Player)) return; 
			
		//Checks if an entity shot the arrow
		Arrow arrow = (Arrow) e.getDamager();
		if (arrow.getShooter() instanceof Player) return;
		
		//Checks PVE properties
		if (!getGamePropertiesManager().getProperties().getPvE()){
			e.setCancelled(true);
			return;
		}
		
		Entity shooter = (Entity) arrow.getShooter();
		final Player shot = (Player) e.getEntity();
		Projectile proj = (Projectile) e.getDamager();
		
		//Checks if it's the final blow
		if (shot.getHealth() - e.getFinalDamage() < 1){
			e.setCancelled(true);
			runDeathSquence(shot);
			
			double projY = proj.getLocation().getY();
			double shotY = shot.getLocation().getY();
			boolean headshot = projY - shotY > getBodyHeight(shot.getType());
			
			AF.broadCastShootingDeath(shooter.getType().getName(), shot.getName(), headshot);	
			Bukkit.getServer().getPluginManager().callEvent(new CustomPlayerDeathEvent(shot, shooter));
			
			//Checks if respawning is enabled
			if (getGamePropertiesManager().getProperties().getForeverSpec() == true){ 
				getGamePropertiesManager().getManager().getKitManager().removeKit(shot);
				shot.sendMessage(AF.boldRed + "YOU ARE OUT OF THE GAME!");
				shot.sendMessage(AF.boldYellow + "Stay until the end to get your rewards!");
				return;
			}
			
			shot.sendMessage(AF.boldYellow + "YOU WILL RESPAWN IN " + AF.boldRed + Tickifier.unTickify(getGamePropertiesManager().getProperties().getRespawnTime(), Time.SECONDS) + AF.boldYellow + " SECONDS!");
		
			
			new BukkitRunnable() {
				
				@Override
				public void run() {
					runRespawnSequence(shot);
				}
			}.runTaskLater(ArcadeCore.getPlugin(), getGamePropertiesManager().getProperties().getRespawnTime());
			
		} else {
			
			if (!getGamePropertiesManager().getProperties().getArrowInBody()){
				UPlayer.removeArrows(shot);
				return;
			}
		}
	}
	
	private double getBodyHeight(EntityType type) {
		 
	switch(type) {
	case CREEPER:
	case ZOMBIE:
	case SKELETON:
	case PLAYER:
	case PIG_ZOMBIE:
	case VILLAGER:
	return 1.35d;
	default:
	return Float.POSITIVE_INFINITY;
	  }
	}
	

}