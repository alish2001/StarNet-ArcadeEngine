package com.starnetmc.ArcadeEngine.Managers.Rewards;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import com.starnetmc.ArcadeCore;
import com.starnetmc.ArcadeEngine.Utils.Logger;

public class Reward implements Listener {

	private String rewardName;
	private int rewardValue;
	
	public Reward(String rewardName, int rewardValue){
		this.rewardName = rewardName;
		this.rewardValue = rewardValue;
	}
	
	public Reward(){
		this.rewardName = "Reward Name Not Found";
		this.rewardValue = 0;
	}
	
	public void register(){
		Bukkit.getServer().getPluginManager().registerEvents(this, ArcadeCore.getPlugin());
		Logger.log("<RewardManager> Listener for Reward: " + getName() + " has been Registered.");
	}
	
	public void unRegister(){
		HandlerList.unregisterAll(this);
		Logger.log("<RewardManager> Listener for Reward: " + getName() + " has been UnRegistered.");
	}
	
	public void setName(String rewardName){
		this.rewardName = rewardName;
	}
	
	public void setValue(int rewardValue){
		this.rewardValue = rewardValue;
	}
	
	public String getName(){
		return rewardName;
	}
	
	public int getValue(){
		return rewardValue;
	}
	
}