package com.starnetmc.core.modules;

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
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import com.starnetmc.ArcadeCore;
import com.starnetmc.ArcadeEngine.Events.CustomPlayerDeathEvent;
import com.starnetmc.ArcadeEngine.Managers.GameState;
import com.starnetmc.ArcadeEngine.Managers.Players.PlayerState;
import com.starnetmc.ArcadeEngine.Utils.AF;
import com.starnetmc.ArcadeEngine.Utils.USound;
import com.starnetmc.ArcadeEngine.Utils.UVector;
import com.starnetmc.core.modules.manager.Module;
import com.starnetmc.core.modules.manager.ModuleType;
import com.starnetmc.core.util.F;
import com.starnetmc.core.util.Tickifier;
import com.starnetmc.core.util.Tickifier.Time;
import com.starnetmc.core.util.UPlayer;

public class ArcadeBorder extends Module {

	public ArcadeBorder(JavaPlugin plugin) {
		super("Arcade Border",1.0,ModuleType.SERVER,plugin);
	}
	

	public ArcadeBorder() {
	}


	@Override
	public void enable() {
		isEnabled = true;
		log("enabled.");
	}

	@Override
	public void disable() {
		isEnabled = false;
		log("disabled.");

	}

	public static boolean isEnabled;

	
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
        
		if (ArcadeCore.getArcadeManager().getPropertiesManager().getProperties().getDeathItemDrop()){
			for (ItemStack i : p.getInventory().getContents()){
				if (i == null) return;
				p.getLocation().getWorld().dropItemNaturally(p.getLocation(), i);
			}
		}
        
		p.setGameMode(GameMode.SPECTATOR);
		ArcadeCore.getArcadeManager().getPlayerManager().setPlayerState(p, PlayerState.DEAD);
		p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 1, 2));
		p.setHealth(p.getMaxHealth());
		UPlayer.resetPlayer(p);
		ArcadeCore.getArcadeManager().getPropertiesManager().getManager().getKitManager().removeKitItems(p);
		p.teleport(ArcadeCore.getArcadeManager().getPropertiesManager().getProperties().getMap().getSpecSpawn());
	}
	
	private void runRespawnSequence(Player p){
		p.teleport(ArcadeCore.getArcadeManager().getPropertiesManager().getProperties().getMap().getRandomSpawnPoint());
		p.setGameMode(ArcadeCore.getArcadeManager().getPropertiesManager().getProperties().getDefaultGameMode());
		ArcadeCore.getArcadeManager().getPropertiesManager().getManager().getKitManager().setKitItems(p, ArcadeCore.getArcadeManager().getPropertiesManager().getManager().getKitManager().getKit(p));
		ArcadeCore.getArcadeManager().getPropertiesManager().getManager().getPlayerManager().setPlayerState(p, PlayerState.ALIVE);
	}
	

	@EventHandler(priority = EventPriority.LOW)
	public void onCross(PlayerMoveEvent e) {

		if(isEnabled = true) {
		
		final Player player = e.getPlayer();
		if (ArcadeCore.getArcadeManager().isState(GameState.INGAME)){
			
			double radSquared = ArcadeCore.getArcadeManager().getMapper().getActiveMap().getBRad() * ArcadeCore.getArcadeManager().getMapper().getActiveMap().getBRad();
		if (player.getLocation().distanceSquared(ArcadeCore.getArcadeManager().getMapper().getActiveMap().getCenter()) > radSquared) {
			
			if (player.getHealth() - 2 < 1){
				e.setCancelled(true);
	            runDeathSquence(player);
	            
				AF.broadCastDeath("Border", player.getName());			
				Bukkit.getServer().getPluginManager().callEvent(new CustomPlayerDeathEvent(player, null));
				
				//If respawn is not enabled
				if (ArcadeCore.getArcadeManager().getPropertiesManager().getProperties().getForeverSpec() == true) {
					ArcadeCore.getArcadeManager().getPropertiesManager().getManager().getKitManager().removeKit(player);
					player.sendMessage(AF.boldRed + "YOU ARE OUT OF THE GAME!");
					player.sendMessage(AF.boldYellow + "Stay until the end to get your rewards!");
					return;
				}
				
				//If respawn is enabled
				player.sendMessage(AF.boldYellow + "YOU WILL RESPAWN IN " + AF.boldRed + Tickifier.unTickify(ArcadeCore.getArcadeManager().getPropertiesManager().getProperties().getRespawnTime(), Time.SECONDS) + AF.boldYellow + " SECONDS!");
			
				
				new BukkitRunnable() {
					
					@Override
					public void run() {
						runRespawnSequence(player);
					}
				}.runTaskLater(ArcadeCore.getPlugin(), ArcadeCore.getArcadeManager().getPropertiesManager().getProperties().getRespawnTime());
				
				return;
			}
	            
			player.damage(2);
			player.sendMessage(F.boldRed + "MOVE BACK TO THE GAME AREA!");
			USound.PSound(player, Sound.NOTE_BASS, 1.75f, 1);

		} else {
			return;
		}
		
		} else {
			return;
		}

		} else {
			return;
		}
	}

}
