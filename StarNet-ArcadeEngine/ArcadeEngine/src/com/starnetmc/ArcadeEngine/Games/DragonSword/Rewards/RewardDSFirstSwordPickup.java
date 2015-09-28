package com.starnetmc.ArcadeEngine.Games.DragonSword.Rewards;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerPickupItemEvent;

import com.starnetmc.ArcadeCore;
import com.starnetmc.ArcadeEngine.Games.DragonSword.DragonSword;
import com.starnetmc.ArcadeEngine.Managers.Rewards.Reward;
import com.starnetmc.ArcadeEngine.Utils.AF;

public class RewardDSFirstSwordPickup extends Reward {

	public RewardDSFirstSwordPickup(String rewardName, int rewardValue) {
		super("First Person to Pickup the Sword", 20);
	}
	
	@EventHandler
	public void pickupDetector(PlayerPickupItemEvent e){
		if (DragonSword.swordWielder != "No One") return;
		if (e.getItem().getItemStack().getType() != Material.DIAMOND_SWORD) return;
		if (!(e.getItem().getItemStack().getItemMeta().getDisplayName().equalsIgnoreCase(AF.boldRed + "The Dragon Sword"))) return;
		
		ArcadeCore.getArcadeManager().getRewardManager().addReward(e.getPlayer(), this);
	}

}
