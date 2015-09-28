package com.starnetmc.core.gadgets.itemgadgets;

import java.util.ArrayList;
import java.util.Random;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import com.starnetmc.core.accounts.AccountManager;
import com.starnetmc.core.events.ShardFireEvent;
import com.starnetmc.core.events.ShardPickupEvent;
import com.starnetmc.core.gadgets.Gadget;
import com.starnetmc.core.gadgets.GadgetType;
import com.starnetmc.core.gadgets.Gadgets;
import com.starnetmc.core.util.F;
import com.starnetmc.core.util.ItemFactory;
import com.starnetmc.core.util.ParticleEffect;
import com.starnetmc.core.util.UParticle;

public class GadgetShardBomb extends Gadget {
	
	public GadgetShardBomb(String gadgetName, ArrayList<String> gadgetDiscription, Material gadgetIcon, Material gadgetItem, GadgetType type) {
		super(gadgetName, gadgetDiscription, gadgetIcon, gadgetItem, type);
		
		setName(F.boldPurple + "Shard " + F.boldRed  + "Bomb");
		setDesc(new ArrayList<String>());
		addDesc(F.YELLOW + "Feeling generous or happy?");
		addDesc(F.YELLOW + "Well you can give out some");
		addDesc(F.YELLOW + "Shards to spread the happy vibes!");
		
		setIconMat(Material.NETHER_STAR);
		setItemMat(Material.NETHER_STAR);
		
		setType(GadgetType.ITEM);
	}
	
	@EventHandler
	public void onShardBombUse(PlayerInteractEvent e){
		
		Player player = e.getPlayer();
		if (!Gadgets.hasGadget(player, this)) return;
		if (!ChatColor.stripColor(e.getItem().getItemMeta().getDisplayName()).equalsIgnoreCase(ChatColor.stripColor(getName()))) return;
        if (e.getPlayer().getItemInHand().getType() != getItem().getType()) return;
        
            Bukkit.getServer().getPluginManager().callEvent(new ShardFireEvent(player));
	}
	
	
	@EventHandler
	public void onPickup(PlayerPickupItemEvent e) {
		e.setCancelled(true);
		if (e.getItem().getItemStack().getType() != getItem().getType()) return;
		

			Bukkit.getServer().getPluginManager().callEvent(new ShardPickupEvent(e.getPlayer(), e.getItem()));
	}
	
	@EventHandler
	public void onShardPickup(ShardPickupEvent e) throws Exception {

		Player p = e.getPlayer();
		AccountManager.getAccount(p).addShards(2);
		e.getItem().remove();
	}

	@EventHandler
	public void onFire(ShardFireEvent e) throws Exception {

		Player player = e.getPlayer();

		if ((AccountManager.getAccount(player).getShards() - 20) <= 0) {
			e.setCancelled(true);
			player.sendMessage(F.error("Economy",
					"Insufficient funds to fire gadget."));
		} else {

			ItemStack item = ItemFactory.createItem(Material.NETHER_STAR,
					F.AQUA + "Shard", null, false);
			int rand = new Random().nextInt();

			Vector posxposz = new Vector(rand, 6F, rand).normalize();

			player.getWorld().dropItemNaturally(player.getEyeLocation(), item)
					.setVelocity(posxposz);
			player.getWorld().dropItemNaturally(player.getEyeLocation(), item)
					.setVelocity(posxposz);
			player.getWorld().dropItemNaturally(player.getEyeLocation(), item)
					.setVelocity(posxposz);
			player.getWorld().dropItemNaturally(player.getEyeLocation(), item)
					.setVelocity(posxposz);

			player.getWorld().playSound(player.getLocation(),
					Sound.GHAST_FIREBALL, 10F, 1F);
			player.getWorld().playSound(player.getLocation(), Sound.EXPLODE,
					7F, 1F);
			UParticle.ParticleExplosion(ParticleEffect.EXPLOSION_HUGE, player.getLocation());

			AccountManager.getAccount(player).removeShards(20);
			
		}

	}

}
