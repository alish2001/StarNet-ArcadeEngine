package com.starnetmc.ArcadeEngine.Managers.Rewards.DefaultRewards;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;

import com.starnetmc.ArcadeCore;
import com.starnetmc.ArcadeEngine.Events.CustomPlayerDeathEvent;
import com.starnetmc.ArcadeEngine.Events.GameStateChangeEvent;
import com.starnetmc.ArcadeEngine.Managers.GameState;
import com.starnetmc.ArcadeEngine.Managers.Classes.CooldownManager.Time;
import com.starnetmc.ArcadeEngine.Managers.Rewards.Reward;

public class RewardTimeAlive extends Reward {

	private HashMap<String, Long> playersTime;
	
	public RewardTimeAlive(String rewardName, int rewardValue) {
		super("Alive for ", 20);
		playersTime = new HashMap<String, Long>();
	}
	
	@EventHandler
	public void startTimes(GameStateChangeEvent e){
		if (e.getChangedState() != GameState.INGAME) return;
		for (Player p : ArcadeCore.getArcadeManager().getPlayerManager().getAlivePlayers()){
			playersTime.put(p.getName(), System.currentTimeMillis());	
		}
	}
	
	private double getTime(Player p, Time timeFormat){
		if(timeFormat == Time.SECONDS) {
		return (double)(System.currentTimeMillis() - playersTime.get(p.getName())/1000);
		} else {
			return (double)(System.currentTimeMillis() - playersTime.get(p.getName())/1000/timeFormat.getValue());
		}
	}
	
	private void setFinalTime(Player p){
		Time timeFormat = Time.SECONDS;
		if ((System.currentTimeMillis() - playersTime.get(p.getName()) > 60000)){
			timeFormat = Time.MINUTES;
		}
		
		RewardTimeAlive reward = new RewardTimeAlive(null, 0);
		if (timeFormat == Time.SECONDS){
		reward.setName(getName() + Math.round(getTime(p, timeFormat)) + " Seconds");
		  if (Math.round(getTime(p, timeFormat)) < 20){
			  
			  reward.setValue(5);
		  } else if (Math.round(getTime(p, timeFormat)) < 40){
			  
			  reward.setValue(15);
		  } else if (Math.round(getTime(p, timeFormat)) < 60){
			  
			  reward.setValue(20);
		  }
		}
		
		if (timeFormat == Time.MINUTES){
		  reward.setName(getName() + getTime(p, timeFormat) + " Minutes");
		  int valueMultiplier =  (int) Math.round(Math.round(getTime(p, Time.SECONDS)) / 60);
		  reward.setValue(getValue() * valueMultiplier);
		}
		
		ArcadeCore.getArcadeManager().getRewardManager().addReward(p, reward);
		playersTime.remove(p.getName());
		
	}
	
	@EventHandler
	public void onDeath(CustomPlayerDeathEvent e){
		Player p = e.getKilled();
		setFinalTime(p);
	}
	
	@EventHandler
	public void onGameEnd(GameStateChangeEvent e){
		if (e.getChangedState() != GameState.POSTGAME) return;
		for (Player p : ArcadeCore.getArcadeManager().getPlayerManager().getAlivePlayers()){
			setFinalTime(p);
		}
	}
	
	@Override
	public void unRegister(){
		HandlerList.unregisterAll(this);
		playersTime.clear();
	}
	
}