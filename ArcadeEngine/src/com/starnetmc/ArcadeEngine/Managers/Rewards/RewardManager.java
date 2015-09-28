package com.starnetmc.ArcadeEngine.Managers.Rewards;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.starnetmc.ArcadeCore;
import com.starnetmc.ArcadeEngine.ArcadeManager;
import com.starnetmc.ArcadeEngine.Events.GameStateChangeEvent;
import com.starnetmc.ArcadeEngine.Managers.GameState;
import com.starnetmc.ArcadeEngine.Utils.AF;
import com.starnetmc.ArcadeEngine.Utils.Logger;
import com.starnetmc.core.accounts.AccountManager;
import com.starnetmc.core.util.StarMap;
import com.starnetmc.core.util.Tickifier;
import com.starnetmc.core.util.Tickifier.Time;

public class RewardManager implements Listener {
	
	private ArcadeManager manager;
	
	private StarMap<String, ArrayList<Reward>> playerRewards;
	
	public RewardManager(ArcadeManager manager, StarMap<String, ArrayList<Reward>> playerRewards){
		this.manager = manager;
		this.playerRewards = playerRewards;
		Bukkit.getServer().getPluginManager().registerEvents(this, ArcadeCore.getPlugin());
		Logger.log("<RewardManager> Reward Manager enabled.");
	}
	
	public RewardManager(ArcadeManager manager){
		this.manager = manager;
		this.playerRewards = new StarMap<String, ArrayList<Reward>>();
		Bukkit.getServer().getPluginManager().registerEvents(this, ArcadeCore.getPlugin());
		Logger.log("<RewardManager> Reward Manager enabled.");
	}
	
	public void giveRewards(){
		for (Player p : Bukkit.getServer().getOnlinePlayers()){
			for (Reward r : getRewards(p)){
				AccountManager.getAccount(p).addShards(r.getValue());
			}	
			
			if (getRewards(p).size() == 0) continue;
			AF.sendRewardsInfo(p, getRewards(p));
		}
		
		Logger.log("<RewardsManager> Game rewards have been given out.");
	}
	
	@EventHandler
	public void rewardListAdder(PlayerJoinEvent e){
		Player p = e.getPlayer();
		addPlayerRewards(p);
	}
	
	@EventHandler
	public void rewardListRemover(PlayerQuitEvent e){
		Player p = e.getPlayer();
		removePlayerRewards(p);
	}
	
	@EventHandler
	public void giveGameRewards(GameStateChangeEvent e){
		if (e.getChangedState() != GameState.LOBBY) return;
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				giveRewards();
				resetRewards();
			}
		}.runTaskLater(ArcadeCore.getPlugin(), Tickifier.tickify(10, Time.SECONDS));
	}
	
	@EventHandler
	public void startupGameRewarder(GameStateChangeEvent e){
		if (e.getChangedState() != GameState.INGAME) return;
		for (Reward r : manager.getGame().getGClass().getRewards()){
			r.register();
		}
	}
	
	@EventHandler
	public void stopGameRewarder(GameStateChangeEvent e){
		if (e.getChangedState() != GameState.LOBBY) return;
		for (Reward r : manager.getGame().getGClass().getRewards()){
			r.unRegister();
		}
	}
	
	public void setPlayerRewards(StarMap<String, ArrayList<Reward>> playerRewards){
		this.playerRewards = playerRewards;
	}
	
	public void resetRewards(){
		for (Player p : Bukkit.getServer().getOnlinePlayers()){
		     getRewards(p).clear();
		}
	}
	
	public void addPlayerRewards(Player p){
		setRewards(p, new ArrayList<Reward>());
		Logger.log("<RewardsManager> Added a player for rewards tracking by the name: " + p.getName());
	}
	
	public void removePlayerRewards(Player p){
		getPlayerRewards().remove(p.getName());
		Logger.log("<RewardsManager> Removed a player for rewards tracking by the name: " + p.getName());
	}
	
	public void setRewards(Player p, ArrayList<Reward> rewards){
		playerRewards.put(p.getName(), rewards);
	}
	
	public void addReward(Player p, Reward reward){
		playerRewards.get(p.getName()).add(reward);
	}
	
	public StarMap<String, ArrayList<Reward>> getPlayerRewards(){
		return playerRewards;
	}
	
	public ArrayList<Reward> getRewards(Player p){
		return playerRewards.get(p.getName());
	}

}