package com.starnetmc.ArcadeEngine.Managers.Rewards.DefaultRewards;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import com.starnetmc.ArcadeCore;
import com.starnetmc.ArcadeEngine.Events.GameStateChangeEvent;
import com.starnetmc.ArcadeEngine.Managers.GameState;
import com.starnetmc.ArcadeEngine.Managers.Rewards.Reward;

public class RewardKills extends Reward {

	public RewardKills(String rewardName, int rewardValue) {
		super(" Kills", 10);
	}

	@EventHandler
	public void roundupKills(GameStateChangeEvent e){
		if (e.getChangedState() != GameState.POSTGAME) return;
		for (Player p : Bukkit.getServer().getOnlinePlayers()){
		int multiplier = ArcadeCore.getArcadeManager().getStatManager().getKills(p);
		if (multiplier == 0) return;
		RewardKills reward = new RewardKills(null, 0);
		reward.setValue(getValue() * multiplier);
		reward.setName(multiplier + " Kills");
		ArcadeCore.getArcadeManager().getRewardManager().addReward(p, reward);
		}
	}
	
}