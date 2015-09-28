package com.starnetmc.ArcadeEngine.Managers.GameProperties;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.starnetmc.ArcadeCore;
import com.starnetmc.ArcadeEngine.ArcadeManager;
import com.starnetmc.ArcadeEngine.Events.CustomPlayerDeathEvent;
import com.starnetmc.ArcadeEngine.Events.GameStateChangeEvent;
import com.starnetmc.ArcadeEngine.Games.TeamGame.TeamGame;
import com.starnetmc.ArcadeEngine.Managers.GameState;
import com.starnetmc.ArcadeEngine.Managers.Players.PlayerState;
import com.starnetmc.ArcadeEngine.Utils.AF;
import com.starnetmc.ArcadeEngine.Utils.Logger;
import com.starnetmc.ArcadeEngine.Utils.USound;
import com.starnetmc.ArcadeEngine.Utils.UVector;
import com.starnetmc.core.util.UPlayer;

public class OldGamePropertiesManager implements Listener {
	
	private ArcadeManager manager;
	
	private boolean startFreeze = false;
	
	private GameProperties properties;
	
	public OldGamePropertiesManager(ArcadeManager manager, GameProperties properties){
		this.manager = manager;
		this.properties = properties;
	}
	
	public void register(){
		Bukkit.getServer().getPluginManager().registerEvents(this, ArcadeCore.getPlugin());
		Logger.log("<Properties Manager> An instance of a GamePropertiesManager has been instantiated and registered.");
	}
	
	public void unRegister(){
		HandlerList.unregisterAll(this);
		Logger.log("<Properties Manager> An instance of a GamePropertiesManager has been unregistered.");
	}
	
	public void setProperties(GameProperties newProperties){
		Logger.log("<Properties Manager> Changing Game Properties...");
		//unRegister();
		this.properties = newProperties;
		//register();
		Logger.log("<Properties Manager> Game Properties changed.");
	}
	
	public void setFreeze(boolean b){
		this.startFreeze = b;
	}
	
	public GameProperties getProperties(){
		return properties;
	}
	
	public boolean getFreeze(){
		return startFreeze;
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
        
		p.setGameMode(GameMode.SPECTATOR);
		manager.getPlayerManager().setPlayerState(p, PlayerState.DEAD);
		p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 1, 2));
		p.setHealth(p.getMaxHealth());
		UPlayer.removeArrows(p);
		manager.getKitManager().removeKitItems(p);
		p.teleport(properties.getMap().getSpecSpawn());
	}
	
	private void runRespawnSequence(Player p){
		p.teleport(properties.getMap().getRandomSpawnPoint());
		p.setGameMode(properties.getDefaultGameMode());
		manager.getKitManager().setKitItems(p, manager.getKitManager().getKit(p));
		manager.getPlayerManager().setPlayerState(p, PlayerState.ALIVE);
	}
	
	@EventHandler
	public void onPvP(EntityDamageByEntityEvent e){
		
		if (!(e.getEntity() instanceof Player)) return; 
		
		if (e.getDamager() instanceof Arrow) {
			
		Arrow arrow = (Arrow) e.getDamager();
		if (!(arrow.getShooter() instanceof Player)) return;
		
		if (properties.getPvP() == false){
			e.setCancelled(true);
			return;
		}
		
		Player shooter = (Player) arrow.getShooter();
		final Player shot = (Player) e.getEntity();
		Projectile proj = (Projectile) e.getDamager();
		
		if (properties.getTeamGame() && properties.getTeamKilling() == false){
			if (((TeamGame) manager.getGame().getGClass()).getTeamManager().getPlayersTeam(shooter) 
			== ((TeamGame) manager.getGame().getGClass()).getTeamManager().getPlayersTeam(shot)){
				e.setCancelled(true);
				return;
			}
		}
		
		if (shot.getHealth() - e.getFinalDamage() < 1){
			e.setCancelled(true);
			if (properties.getForeverSpec() == true){ 
				manager.getKitManager().removeKit(shot);
			}
			
			if (properties.getItemDrop()){
			for (ItemStack item : shot.getInventory()){
				shot.getWorld().dropItemNaturally(shot.getLocation(), item);
			}
			}
			
			runDeathSquence(shot);
			
			double projY = proj.getLocation().getY();
			double shotY = shot.getLocation().getY();
			boolean headshot = projY - shotY > getBodyHeight(shot.getType());
			
			AF.broadCastShootingDeath(shooter.getName(), shot.getName(), headshot);	
			
			Bukkit.getServer().getPluginManager().callEvent(new CustomPlayerDeathEvent(shot, shooter));
			
			if (properties.getForeverSpec() == true) {
				shot.sendMessage(AF.boldRed + "YOU ARE OUT OF THE GAME!");
				shot.sendMessage(AF.boldYellow + "Stay till the end to get your rewards!");
				return;
			}
			
			shot.sendMessage(AF.boldYellow + "YOU WILL RESPAWN IN " + AF.boldRed + properties.getRespawnTime()/20 + AF.boldYellow + " SECONDS!");
		
			
			new BukkitRunnable() {
				
				@Override
				public void run() {
					runRespawnSequence(shot);
				}
			}.runTaskLater(ArcadeCore.getPlugin(), properties.getRespawnTime());
		} else {
			if (!properties.getArrowInBody()){
				UPlayer.removeArrows(shot);
				return;
			}
		}
	}
		
		if (!(e.getDamager() instanceof Player) && !(e.getEntity() instanceof Player)) return; 

		if (properties.getPvP() == false){
			e.setCancelled(true);
			return;
		}

		
		Player reker = (Player) e.getDamager();
		final Player rekt = (Player) e.getEntity();
		
		if (properties.getTeamGame() && properties.getTeamKilling() == false){
			if (((TeamGame) manager.getGame().getGClass()).getTeamManager().getPlayersTeam(reker) 
			== ((TeamGame) manager.getGame().getGClass()).getTeamManager().getPlayersTeam(rekt)){
				e.setCancelled(true);
				return;
			}
		}
		
		if (rekt.getHealth() - e.getFinalDamage() < 1){
			e.setCancelled(true);
            runDeathSquence(rekt);
            
			if (properties.getForeverSpec() == true){ 
				manager.getKitManager().removeKit(rekt);
			}
			
			AF.broadCastDeath(reker.getName(), rekt.getName(), reker.getItemInHand().getItemMeta().getDisplayName());			
			Bukkit.getServer().getPluginManager().callEvent(new CustomPlayerDeathEvent(rekt, null));
			
			if (properties.getForeverSpec() == true) {
				rekt.sendMessage(AF.boldRed + "YOU ARE OUT OF THE GAME!");
				rekt.sendMessage(AF.boldYellow + "Stay till the end to get your rewards!");
				return;
			}
			
			rekt.sendMessage(AF.boldYellow + "YOU WILL RESPAWN IN " + AF.boldRed + properties.getRespawnTime()/20 + AF.boldYellow + " SECONDS!");
		
			
			new BukkitRunnable() {
				
				@Override
				public void run() {
					runRespawnSequence(rekt);
					
				}
			}.runTaskLater(ArcadeCore.getPlugin(), properties.getRespawnTime());
		}
		
		
	}

	@EventHandler
	public void onCause(EntityDamageEvent e){
		
		if (!(e.getEntity() instanceof Player)) return;
		
		Player p = (Player) e.getEntity();
		String deathCause = "";
		
		if (e.getCause() == DamageCause.FALL){
		if (properties.getFallDamage() == false){
			e.setCancelled(true);
			return;
		  } else {
			  deathCause = "The Earth";
		  }
		}
		
		if (e.getCause() == DamageCause.MAGIC){
			  deathCause = "Magic";
		}
		
		if (e.getCause() == DamageCause.DROWNING){
			if (properties.getWaterDamage() == false){
				e.setCancelled(true);
				return;
			  } else {
			  deathCause = "The Ocean";
			  }
		}
		
		if (e.getCause() == DamageCause.LAVA){
			if (properties.getLavaDamage() == false){
				e.setCancelled(true);
				return;
			  } else {
			  deathCause = "Lava";
			  }
		}
		
		if (e.getCause() == DamageCause.FIRE){
			if (properties.getFireDamage() == false){
				e.setCancelled(true);
				return;
			  } else {
			  deathCause = "Fire";
			  }
		}
		
		if (e.getCause() == DamageCause.FIRE_TICK){
			if (properties.getFireDamage() == false){
				e.setCancelled(true);
				return;
			  } else {
			  deathCause = "Fire";
			  }
		}
		
		if (e.getCause() == DamageCause.LIGHTNING){
			  deathCause = "Zeus";
		}
		
		if (e.getCause() == DamageCause.POISON){
			if (properties.getPoisonDamage() == false){
				e.setCancelled(true);
				return;
			  } else {
			  deathCause = "Poison";
			  }
		}
		
		if (e.getCause() == DamageCause.SUFFOCATION){
			if (properties.getSufficationDamage() == false){
				e.setCancelled(true);
				return;
			  } else {
			  deathCause = "Suffocation";
			  }
		}
		
		if (e.getCause() == DamageCause.MELTING){
			if (properties.getLavaDamage() == false){
				e.setCancelled(true);
				return;
			  } else {
			  deathCause = "Lava";
			  }
		}
		
		if (e.getCause() == DamageCause.FALLING_BLOCK){
			  deathCause = "A Falling Block";
		}
		
		if (e.getCause() == DamageCause.SUICIDE){
			  deathCause = "Suicide";
		}
		
		if (e.getCause() == DamageCause.VOID){
			if (properties.getVoidDamage() == false){
				e.setCancelled(true);
				if (manager.isState(GameState.INGAME)){
					p.teleport(properties.getMap().getRandomSpawnPoint());	
				} else {
					p.teleport(p.getWorld().getSpawnLocation());
				}
				return;
			  } else {
			  deathCause = "The Void";
			  }
		}
		
		if (e.getCause() == DamageCause.THORNS){
			  deathCause = "Thorns";
		}
		
		if (e.getCause() == DamageCause.WITHER){
			  deathCause = "The Wither Spirit";
		}
		
		if (e.getCause() == DamageCause.STARVATION){
			if (properties.getHungerDamage() == false){
				e.setCancelled(true);
				return;
			} else {
			  deathCause = "Starvation";
			}
		}
		
		if (e.getCause() == DamageCause.ENTITY_EXPLOSION){
			if (properties.getExplosionDamage() == false){
			  e.setCancelled(true);
			  return;
			} else {
			  deathCause = "An Explosion";
			}
		}
		
		if (e.getCause() == DamageCause.CUSTOM){
			deathCause = "The Anonymuz";
		}
		
		final Player rekt = (Player) e.getEntity();
		
		if (rekt.getHealth() - e.getFinalDamage() < 1){
			e.setCancelled(true);
			if (properties.getForeverSpec() == true){ 
				manager.getKitManager().removeKit(rekt);
			}
			
			if (properties.getItemDrop()){
			for (ItemStack item : rekt.getInventory()){
				rekt.getWorld().dropItemNaturally(rekt.getLocation(), item);
			 }
			}
			
			runDeathSquence(rekt);
			AF.broadCastDeath(deathCause, rekt.getName());			
			Bukkit.getServer().getPluginManager().callEvent(new CustomPlayerDeathEvent(rekt, null));
			
			if (properties.getForeverSpec() == true) {
				rekt.sendMessage(AF.boldRed + "YOU ARE OUT OF THE GAME!");
				rekt.sendMessage(AF.boldYellow + "Stay till the end to get your rewards!");
				return;
			}
			
			rekt.sendMessage(AF.boldYellow + "YOU WILL RESPAWN IN " + AF.boldRed + properties.getRespawnTime()/20 + AF.boldYellow + " SECONDS!");
		
			
			new BukkitRunnable() {
				
				@Override
				public void run() {
					runRespawnSequence(rekt);
				}
			}.runTaskLater(ArcadeCore.getPlugin(), properties.getRespawnTime());
		}
		
	}
	
	@EventHandler
	public void onExplosion(EntityExplodeEvent e){
	
		if (!properties.getBlockExplosion()){
			e.blockList().clear();
		} else {
			
			for (Block b : e.blockList()){
				float x = (float) -1.5 + (float) (Math.random() * ((1.5 - -1.5) + 1));
				float y = (float) -1.5 + (float) (Math.random() * ((2 - -2) + 1));
				float z = (float) -1.5 + (float) (Math.random() * ((1.5 - -1.5) + 1));
				
				@SuppressWarnings("deprecation")
				FallingBlock fB = b.getWorld().spawnFallingBlock(b.getLocation(), b.getType(), b.getData());
				fB.setDropItem(false);
				fB.setVelocity(new Vector(x, y, z));
			}
		}
		
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPvE(EntityDamageByEntityEvent e){
		
		if (e.getDamager() instanceof Arrow) {
			
		if (!(e.getEntity() instanceof Player)) return; 
			
		Arrow arrow = (Arrow) e.getDamager();
		
		if (arrow.getShooter() instanceof Player) return;
		
		if (properties.getPvE() == false){
			e.setCancelled(true);
			return;
		}
		
		Entity shooter = (Entity) arrow.getShooter();
		final Player shot = (Player) e.getEntity();
		Projectile proj = (Projectile) e.getDamager();
		
		if (shot.getHealth() - e.getFinalDamage() < 1){
			e.setCancelled(true);
			if (properties.getForeverSpec() == true){ 
				manager.getKitManager().removeKit(shot);
			}
			
			if (properties.getItemDrop()){
			for (ItemStack item : shot.getInventory()){
				shot.getWorld().dropItemNaturally(shot.getLocation(), item);
			}
			}
			runDeathSquence(shot);
			
			double projY = proj.getLocation().getY();
			double shotY = shot.getLocation().getY();
			boolean headshot = projY - shotY > getBodyHeight(shot.getType());
			
			AF.broadCastShootingDeath(shooter.getType().getName(), shot.getName(), headshot);	
			
			Bukkit.getServer().getPluginManager().callEvent(new CustomPlayerDeathEvent(shot, shooter));
			
			if (properties.getForeverSpec() == true) {
				shot.sendMessage(AF.boldRed + "YOU ARE OUT OF THE GAME!");
				shot.sendMessage(AF.boldYellow + "Stay till the end to get your rewards!");
				return;
			}
			
			shot.sendMessage(AF.boldYellow + "YOU WILL RESPAWN IN " + AF.boldRed + properties.getRespawnTime()/20 + AF.boldYellow + " SECONDS!");
		
			
			new BukkitRunnable() {
				
				@Override
				public void run() {
					runRespawnSequence(shot);
				}
			}.runTaskLater(ArcadeCore.getPlugin(), properties.getRespawnTime());
		} else {
			if (!properties.getArrowInBody()){
				UPlayer.removeArrows(shot);
				return;
			}
		}
	}
		
		if (e.getDamager() instanceof Player && e.getEntity() instanceof Player){
			
			if (properties.getPvE() == false){
				e.setCancelled(true);
				return;
			}
		}
		
		if (!(e.getDamager() instanceof Player) && !(e.getEntity() instanceof Player)) return; 
		
		if (properties.getPvE() == false){
			e.setCancelled(true);
			return;
		}
		
		Entity reker = e.getDamager();
		final Player rekt = (Player) e.getEntity();
		
		if (rekt.getHealth() -  e.getFinalDamage() < 1){
			e.setCancelled(true);
			
			if (properties.getForeverSpec() == true){ 
				manager.getKitManager().removeKit(rekt);
			}
			
			if (properties.getItemDrop()){
				manager.getKitManager().removeKit(rekt);
			for (ItemStack item : rekt.getInventory()){
				rekt.getWorld().dropItemNaturally(rekt.getLocation(), item);
			}
			}
			
			runDeathSquence(rekt);
			AF.broadCastDeath(reker.getType().getName(), rekt.getName());
			
			Bukkit.getServer().getPluginManager().callEvent(new CustomPlayerDeathEvent(rekt, reker));
			
			if (properties.getForeverSpec() == true) {
				rekt.sendMessage(AF.boldRed + "YOU ARE OUT OF THE GAME!");
				rekt.sendMessage(AF.boldYellow + "Stay till the end to get your rewards!");
				return;
			}
			
			   manager.getKitManager().removeKit(rekt);
			   rekt.sendMessage(AF.boldYellow + "YOU WILL RESPAWN IN " + AF.boldRed + properties.getRespawnTime()/20 + AF.boldYellow + " SECONDS");
			
			new BukkitRunnable() {
				
				@Override
				public void run() {
					runRespawnSequence(rekt);
				}
			}.runTaskLater(ArcadeCore.getPlugin(), properties.getRespawnTime());
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
	
	/*@EventHandler
	public void graceStarter(GameStateChangeEvent e){
		
		if (e.getChangedState() != GameState.INGAME) return;
		if (!properties.getGracePeriod()) return;
		
		Logger.log("<Properties Manager> GracePeriod true, starting grace...");
		
		if (properties.getGraceAnnounce()){
			Bukkit.getServer().broadcastMessage(F.boldYellow + "GRACE PERIOD FOR " + properties.getGraceTime()/20 + " SECONDS!");
		}
		
		final boolean prePvP = properties.getPvP();
		final boolean prePvE = properties.getPvE();
		
		properties.setPvP(false);
		properties.setPvE(false);
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				properties.setPvP(prePvP);
				properties.setPvE(prePvE);
				Logger.log("<Properties Manager> Grace period over, PVP&PVE setttings returned to normal.");
				
			}
		}.runTaskLater(ArcadeCore.getPlugin(), properties.getGraceTime());
	}*/
	
	@EventHandler
	public void startFreezer(GameStateChangeEvent e){
		
		if (e.getChangedState() != GameState.INGAME) return;
		startFreeze = true;
	}
	
	@EventHandler
	public void startFreeze(PlayerMoveEvent e){
		
		if (startFreeze != true) return;
		if (!(manager.isState(GameState.INGAME))) return;
		e.setTo(e.getFrom());
	}
	
	
	@EventHandler
	public void onItemDrop(PlayerDropItemEvent e){
		
		if (properties.getItemDrop() == false){
			e.setCancelled(true);
		} else { return; }
	}
	
	@EventHandler
	public void onDeathItemDrop(CustomPlayerDeathEvent e){
		if (properties.getDeathItemDrop() == false){
			return;
		} else {
			for (ItemStack i : e.getKilled().getInventory().getContents()){
				e.getKilled().getLocation().getWorld().dropItemNaturally(e.getKilled().getLocation(), i);
			}
		}
	}
	
	@EventHandler
	public void onItemPickup(PlayerPickupItemEvent e){
		
		if (properties.getItemPickup() == false){
			e.setCancelled(true);
		} else { return; }
	}
	
	@EventHandler
	public void onItemEat(PlayerItemConsumeEvent e){
		
		if (properties.getItemEat() == false){
			e.setCancelled(true);
		} else { return; }
	}
	
	
	@EventHandler
	public void onInvModification(InventoryClickEvent e){
		
		if (e.getClickedInventory() != e.getWhoClicked().getInventory()) return;
		if (properties.getInventoryModification() == false){
			e.setCancelled(true);
		} else {
			return;
		}
		
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e){
		
		if (properties.getBlockBreak() == false) e.setCancelled(true);
		
		if (properties.getBlockBreak() != false) return;
		if (properties.getBlockBreakCustom() == false) return;
		
		boolean breakable = false;
		for (Material m : properties.getCustomBreakBlocks()){
			if (e.getBlock().getType() == m){
				breakable = true;
			}
		}
		
	    if (!breakable) e.setCancelled(true);
	}
	
   @EventHandler
   public void onBlockPlace(BlockPlaceEvent e){
	   
		if (properties.getBlockPlace() == false) e.setCancelled(true);
		
		if (properties.getBlockPlace() != false) return;
		if (properties.getBlockPlaceCustom() == false) return;
		
		boolean placeable = false;
		for (Material m : properties.getCustomPlaceBlocks()){
			if (e.getBlock().getType() == m){
				placeable = true;
			}
		}
		
	    if (!placeable) e.setCancelled(true);
   }
   
   @EventHandler
   public void onHungry(FoodLevelChangeEvent e){
	   
	   if (properties.getHungry() == false){
		   e.setFoodLevel(20);
		   e.setCancelled(true);
	   } else { 
		   return;
	   }
   }
   
   @EventHandler
   public void onArrowHit(ProjectileHitEvent e){
	   if (e.getEntityType() != EntityType.ARROW) return;
	   if (!properties.getGroundArrowRemoval()) return;
	   
	      e.getEntity().remove();
   }
}