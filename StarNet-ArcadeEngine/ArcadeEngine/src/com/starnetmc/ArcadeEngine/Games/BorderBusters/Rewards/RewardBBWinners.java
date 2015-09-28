package com.starnetmc.ArcadeEngine.Games.BorderBusters.Rewards;

import org.bukkit.event.EventHandler;

import com.starnetmc.ArcadeCore;
import com.starnetmc.ArcadeEngine.Events.GameStateChangeEvent;
import com.starnetmc.ArcadeEngine.Games.BorderBusters.BorderBusters;
import com.starnetmc.ArcadeEngine.Games.Spleef.StarSpleef;
import com.starnetmc.ArcadeEngine.Managers.GameState;
import com.starnetmc.ArcadeEngine.Managers.Rewards.Reward;
import com.starnetmc.core.util.UPlayer;

public class RewardBBWinners extends Reward {

	public RewardBBWinners(String rewardName, int rewardValue) {
		super("1st Place", 50);
	}
	
	@EventHandler
	public void roundupWinners(GameStateChangeEvent e){
		if (e.getChangedState() != GameState.POSTGAME) return;
		
		RewardBBWinners firstReward = new RewardBBWinners(null, 0);
		RewardBBWinners secondReward = new RewardBBWinners(null, 0);
		RewardBBWinners thirdReward = new RewardBBWinners(null, 0);
		
		secondReward.setName("2nd Place");
		secondReward.setValue(30);
		
		thirdReward.setName("3nd Place");
		thirdReward.setValue(20);
		
		if (!BorderBusters.first.equalsIgnoreCase("No One")){
	      	ArcadeCore.getArcadeManager().getRewardManager().addReward(UPlayer.getOnlinePlayerFromName(StarSpleef.first), firstReward);
		}
		
		if (!BorderBusters.second.equalsIgnoreCase("No One")){
		    ArcadeCore.getArcadeManager().getRewardManager().addReward(UPlayer.getOnlinePlayerFromName(StarSpleef.second), secondReward);
		}
		
		if (!BorderBusters.third.equalsIgnoreCase("No One")){
		    ArcadeCore.getArcadeManager().getRewardManager().addReward(UPlayer.getOnlinePlayerFromName(StarSpleef.third), thirdReward);
		}
	}
	
}