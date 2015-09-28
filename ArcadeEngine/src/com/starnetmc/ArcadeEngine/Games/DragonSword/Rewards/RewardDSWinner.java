package com.starnetmc.ArcadeEngine.Games.DragonSword.Rewards;

import org.bukkit.event.EventHandler;

import com.starnetmc.ArcadeCore;
import com.starnetmc.ArcadeEngine.Events.GameStateChangeEvent;
import com.starnetmc.ArcadeEngine.Games.DragonSword.DragonSword;
import com.starnetmc.ArcadeEngine.Managers.GameState;
import com.starnetmc.ArcadeEngine.Managers.Rewards.Reward;
import com.starnetmc.core.util.UPlayer;

public class RewardDSWinner extends Reward {

	public RewardDSWinner(String rewardName, int rewardValue) {
		super("Game Winner", 50);
	}
	
	@EventHandler
	public void winnerAwarder(GameStateChangeEvent e){
		if (e.getChangedState() != GameState.POSTGAME) return;
		if (DragonSword.swordWielder.equalsIgnoreCase("No One")) return;
		ArcadeCore.getArcadeManager().getRewardManager().addReward(UPlayer.getOnlinePlayerFromName(DragonSword.swordWielder), new RewardDSWinner(null, 0));
		
	}

}
