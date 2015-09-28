package com.starnetmc.ArcadeEngine.Managers.Achievements;

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
import com.starnetmc.core.util.StarMap;
import com.starnetmc.core.util.Tickifier;
import com.starnetmc.core.util.Tickifier.Time;

public class AchievementManager implements Listener {

	private ArcadeManager manager;
	
	private StarMap<String, ArrayList<Achievement>> playerAchievements;
	
	public AchievementManager(ArcadeManager manager, StarMap<String, ArrayList<Achievement>> playerAchievements){
		this.manager = manager;
		this.playerAchievements = playerAchievements;
		Bukkit.getServer().getPluginManager().registerEvents(this, ArcadeCore.getPlugin());
		Logger.log("<AchievementManager> Reward Manager enabled.");
	}
	
	public AchievementManager(ArcadeManager manager){
		this.manager = manager;
		this.playerAchievements = new StarMap<String, ArrayList<Achievement>>();
		Bukkit.getServer().getPluginManager().registerEvents(this, ArcadeCore.getPlugin());
		Logger.log("<AchievementManager> Reward Manager enabled.");
	}
	
	public void giveAchievements(){
		for (Player p : Bukkit.getServer().getOnlinePlayers()){
			if (getAchievements(p).size() == 0) continue;
			
			for (Achievement a : getAchievements(p)) {
		         AF.sendAchievementInfo(p, a);
		         a.giveReward(p);
			}
		}
		
		Logger.log("<AchievementManager> Game achivement rewards have been given out.");
	}
	
	@EventHandler
	public void achivementListAdder(PlayerJoinEvent e){
		Player p = e.getPlayer();
		addPlayerAchievements(p);
	}
	
	@EventHandler
	public void achivementListRemover(PlayerQuitEvent e){
		Player p = e.getPlayer();
		removePlayerAchievements(p);
	}
	
	@EventHandler
	public void giveGameAchievements(GameStateChangeEvent e){
		if (e.getChangedState() != GameState.LOBBY) return;
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				giveAchievements();
				resetAchievements();
			}
		}.runTaskLater(ArcadeCore.getPlugin(), Tickifier.tickify(10, Time.SECONDS));
	}
	
	@EventHandler
	public void startupGameAchievementer(GameStateChangeEvent e){
		if (e.getChangedState() != GameState.INGAME) return;
		for (Achievement a : manager.getGame().getGClass().getAchievements()){
			a.register();
		}
	}
	
	@EventHandler
	public void stopGameAchievementer(GameStateChangeEvent e){
		if (e.getChangedState() != GameState.LOBBY) return;
		for (Achievement a : manager.getGame().getGClass().getAchievements()){
			a.unRegister();
		}
	}
	
	public void setPlayerAchievements(StarMap<String, ArrayList<Achievement>> playerAchievements){
		this.playerAchievements = playerAchievements;
	}
	
	public void resetAchievements(){
		for (Player p : Bukkit.getServer().getOnlinePlayers()){
		     getAchievements(p).clear();
		}
	}
	
	public void addPlayerAchievements(Player p){
		setAchievements(p, new ArrayList<Achievement>());
		Logger.log("<AchievementManager> Added a player for achivement tracking by the name: " + p.getName());
	}
	
	public void removePlayerAchievements(Player p){
		playerAchievements.remove(p.getName());
		Logger.log("<AchievementManager> Removed a player for achivement tracking by the name: " + p.getName());
	}
	
	public void setAchievements(Player p, ArrayList<Achievement> achivements){
		playerAchievements.put(p.getName(), achivements);
	}
	
	public void addAchievement(Player p, Achievement achivement){
		playerAchievements.get(p.getName()).add(achivement);
	}
	
	public StarMap<String, ArrayList<Achievement>> getPlayerAchievements(){
		return playerAchievements;
	}
	
	public ArrayList<Achievement> getAchievements(Player p){
		return playerAchievements.get(p.getName());
	}
	
}