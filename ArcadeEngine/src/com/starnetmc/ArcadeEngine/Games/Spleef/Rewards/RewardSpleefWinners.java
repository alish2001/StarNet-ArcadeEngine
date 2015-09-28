package com.starnetmc.ArcadeEngine.Games.Spleef.Rewards;

import org.bukkit.event.EventHandler;

import com.starnetmc.ArcadeCore;
import com.starnetmc.ArcadeEngine.Events.GameStateChangeEvent;
import com.starnetmc.ArcadeEngine.Games.Spleef.StarSpleef;
import com.starnetmc.ArcadeEngine.Managers.GameState;
import com.starnetmc.ArcadeEngine.Managers.Rewards.Reward;
import com.starnetmc.core.util.UPlayer;

public class RewardSpleefWinners extends Reward {

	public RewardSpleefWinners(String rewardName, int rewardValue) {
		super("1st Place", 50);
	}
	
	@EventHandler
	public void roundupWinners(GameStateChangeEvent e){
		if (e.getChangedState() != GameState.POSTGAME) return;
		
		RewardSpleefWinners firstReward = new RewardSpleefWinners(null, 0);
		RewardSpleefWinners secondReward = new RewardSpleefWinners(null, 0);
		RewardSpleefWinners thirdReward = new RewardSpleefWinners(null, 0);
		
		secondReward.setName("2nd Place");
		secondReward.setValue(30);
		
		thirdReward.setName("3nd Place");
		thirdReward.setValue(20);
		
		if (!StarSpleef.first.equalsIgnoreCase("No One")){
	      	ArcadeCore.getArcadeManager().getRewardManager().addReward(UPlayer.getOnlinePlayerFromName(StarSpleef.first), firstReward);
		}
		
		if (!StarSpleef.second.equalsIgnoreCase("No One")){
		    ArcadeCore.getArcadeManager().getRewardManager().addReward(UPlayer.getOnlinePlayerFromName(StarSpleef.second), secondReward);
		}
		
		if (!StarSpleef.third.equalsIgnoreCase("No One")){
		    ArcadeCore.getArcadeManager().getRewardManager().addReward(UPlayer.getOnlinePlayerFromName(StarSpleef.third), thirdReward);
		}
	}

}