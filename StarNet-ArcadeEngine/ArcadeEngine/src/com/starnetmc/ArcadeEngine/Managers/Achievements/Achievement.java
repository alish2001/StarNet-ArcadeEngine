package com.starnetmc.ArcadeEngine.Managers.Achievements;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import com.starnetmc.ArcadeCore;
import com.starnetmc.ArcadeEngine.Utils.Logger;

public class Achievement implements Listener {
	
	private String name;
	private String desc;
	private String reward;
	
	public Achievement(String name, String desc, String reward){
		this.name = name;
		this.desc = name;
		this.reward = name;
	}
	
	public Achievement(){
		name = "Achivement Name Not Found";
		desc = "Achivement Desc Not Found";
		reward = "Achivement Reward Not Found";
	}
	
	public void register(){
		Bukkit.getServer().getPluginManager().registerEvents(this, ArcadeCore.getPlugin());
		Logger.log("<AchivementManager> Listener for Achivement: " + getName() + " has been Registered.");
	}
	
	public void unRegister(){
		HandlerList.unregisterAll(this);
		Logger.log("<AchivementManager> Listener for Achivement: " + getName() + " has been UnRegistered.");
	}
	
	public void giveReward(Player p){
		
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setDesc(String desc){
		this.desc = desc;
	}
	
	public void setReward(String reward){
		this.reward = reward;
	}
	
	public String getName(){
		return name;
	}
	
	public String getDesc(){
		return desc;
	}
	
	public String getReward(){
		return reward;
	}

}