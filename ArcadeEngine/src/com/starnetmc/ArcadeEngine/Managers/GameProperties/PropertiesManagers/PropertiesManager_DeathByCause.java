package com.starnetmc.ArcadeEngine.Managers.GameProperties.PropertiesManagers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import com.starnetmc.ArcadeCore;
import com.starnetmc.ArcadeEngine.Events.CustomPlayerDeathEvent;
import com.starnetmc.ArcadeEngine.Managers.GameState;
import com.starnetmc.ArcadeEngine.Managers.GameProperties.GamePropertiesManager;
import com.starnetmc.ArcadeEngine.Managers.GameProperties.PropertiesManager;
import com.starnetmc.ArcadeEngine.Managers.Players.PlayerState;
import com.starnetmc.ArcadeEngine.Utils.AF;
import com.starnetmc.ArcadeEngine.Utils.USound;
import com.starnetmc.ArcadeEngine.Utils.UVector;
import com.starnetmc.core.util.Tickifier;
import com.starnetmc.core.util.Tickifier.Time;
import com.starnetmc.core.util.UPlayer;

public class PropertiesManager_DeathByCause extends PropertiesManager {

	public PropertiesManager_DeathByCause(GamePropertiesManager gamePropertiesManager, String propertiesManagerName) {
		super(gamePropertiesManager, "DeathByCause");
	}
	
	public PropertiesManager_DeathByCause(GamePropertiesManager gamePropertiesManager) {
		super(gamePropertiesManager);
		setName("DeathByCause");
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
	
	@EventHandler
	public void onCause(EntityDamageEvent e){
		
		if (!(e.getEntity() instanceof Player)) return;
		
		Player p = (Player) e.getEntity();
		String deathCause = "Nothing";
		
		if (e.getCause() == DamageCause.ENTITY_ATTACK){
			return;
		}
		
		if (e.getCause() == DamageCause.FALL){
		if (getGamePropertiesManager().getProperties().getFallDamage() == false){
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
			if (getGamePropertiesManager().getProperties().getWaterDamage() == false){
				e.setCancelled(true);
				return;
			  } else {
			  deathCause = "The Ocean";
			  }
		}
		
		if (e.getCause() == DamageCause.LAVA){
			if (getGamePropertiesManager().getProperties().getLavaDamage() == false){
				e.setCancelled(true);
				return;
			  } else {
			  deathCause = "Lava";
			  }
		}
		
		if (e.getCause() == DamageCause.FIRE){
			if (getGamePropertiesManager().getProperties().getFireDamage() == false){
				e.setCancelled(true);
				return;
			  } else {
			  deathCause = "Fire";
			  }
		}
		
		if (e.getCause() == DamageCause.FIRE_TICK){
			if (getGamePropertiesManager().getProperties().getFireDamage() == false){
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
			if (getGamePropertiesManager().getProperties().getPoisonDamage() == false){
				e.setCancelled(true);
				return;
			  } else {
			  deathCause = "Poison";
			  }
		}
		
		if (e.getCause() == DamageCause.SUFFOCATION){
			if (getGamePropertiesManager().getProperties().getSufficationDamage() == false){
				e.setCancelled(true);
				return;
			  } else {
			  deathCause = "Suffocation";
			  }
		}
		
		if (e.getCause() == DamageCause.MELTING){
			if (getGamePropertiesManager().getProperties().getLavaDamage() == false){
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
			if (getGamePropertiesManager().getProperties().getVoidDamage() == false){
				e.setCancelled(true);
				if (getGamePropertiesManager().getManager().isState(GameState.INGAME)){
					p.teleport(getGamePropertiesManager().getProperties().getMap().getRandomSpawnPoint());	
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
			if (getGamePropertiesManager().getProperties().getHungerDamage() == false){
				e.setCancelled(true);
				return;
			} else {
			  deathCause = "Starvation";
			}
		}
		
		if (e.getCause() == DamageCause.ENTITY_EXPLOSION){
			if (getGamePropertiesManager().getProperties().getExplosionDamage() == false){
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
		
		//Checks if it is the final blow
		if (rekt.getHealth() - e.getFinalDamage() < 1){
			e.setCancelled(true);
            runDeathSquence(rekt);
			
			AF.broadCastDeath(deathCause, rekt.getName());			
			Bukkit.getServer().getPluginManager().callEvent(new CustomPlayerDeathEvent(rekt, null));
			
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
	
	   @EventHandler
	   public void onHungry(FoodLevelChangeEvent e){
		   
		   if (getGamePropertiesManager().getProperties().getHungry() == false){
			   e.setFoodLevel(20);
			   e.setCancelled(true);
	       }
	   }
	

}