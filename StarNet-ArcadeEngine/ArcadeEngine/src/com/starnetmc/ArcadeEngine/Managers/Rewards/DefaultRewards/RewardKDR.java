package com.starnetmc.ArcadeEngine.Managers.Rewards.DefaultRewards;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import com.starnetmc.ArcadeCore;
import com.starnetmc.ArcadeEngine.Events.GameStateChangeEvent;
import com.starnetmc.ArcadeEngine.Managers.GameState;
import com.starnetmc.ArcadeEngine.Managers.Rewards.Reward;

public class RewardKDR extends Reward {

	public RewardKDR(String rewardName, int rewardValue) {
		super("Positive KDR", 20);
	}
	
	@EventHandler
	public void roundupKDR(GameStateChangeEvent e){
		if (e.getChangedState() != GameState.POSTGAME) return;
		for (Player p : Bukkit.getServer().getOnlinePlayers()){
			
		double kdr = ArcadeCore.getArcadeManager().getStatManager().getKDR(p);
		  if (kdr > 1.0D){
		  RewardKDR reward = new RewardKDR(null, 0);
		  ArcadeCore.getArcadeManager().getRewardManager().addReward(p, reward);
		  }
		}
	}
}