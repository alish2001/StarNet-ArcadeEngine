package com.starnetmc.ArcadeEngine.Managers.Scoreboards;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.starnetmc.ArcadeCore;
import com.starnetmc.ArcadeEngine.ArcadeManager;
import com.starnetmc.ArcadeEngine.Events.GameStateChangeEvent;
import com.starnetmc.ArcadeEngine.Events.ScoreboardUpdateEvent;
import com.starnetmc.ArcadeEngine.Managers.GameState;
import com.starnetmc.ArcadeEngine.Managers.Scoreboards.StarBoards.ArcadeScoreboard;
import com.starnetmc.ArcadeEngine.Utils.Logger;
import com.starnetmc.core.events.UpdateEvent;
import com.starnetmc.core.util.StarMap;
import com.starnetmc.core.util.UpdateType;

public class ScoreboardManager implements Listener{

	@SuppressWarnings("unused")
	private ArcadeManager manager;
	
	private StarMap<String, Starboard> customScoreboards;
	private Starboard defScoreboard;
	
	public ScoreboardManager(ArcadeManager manager, Starboard currentScoreboard){
		this.manager = manager;
		this.defScoreboard = currentScoreboard;
		this.customScoreboards = new StarMap<String, Starboard>();
		Bukkit.getServer().getPluginManager().registerEvents(this, ArcadeCore.getPlugin());
		
		Logger.log("<ScoreboardManager> ScoreboardManager enabled.");
	}
	
	public ScoreboardManager(ArcadeManager manager){
		this.manager = manager;
		
		if (manager.isState(GameState.LOBBY) || manager.isState(GameState.LOADING)){
			this.defScoreboard = new ArcadeScoreboard(null);
		}
		
		this.customScoreboards = new StarMap<String, Starboard>();
		Bukkit.getServer().getPluginManager().registerEvents(this, ArcadeCore.getPlugin());
		Logger.log("<ScoreboardManager> ScoreboardManager enabled.");
	}
	
	@EventHandler
	public void updateStarBoard(ScoreboardUpdateEvent e){
		Player p = e.getPlayer();
		
		if (customScoreboards.containsKey(p.getName())){
			customScoreboards.get(p.getName()).scoreboarder(p);
			return;
		}
		
		defScoreboard.scoreboarder(p);
	}
	
	@EventHandler
	public void updateStarBoardTaskerino(UpdateEvent e){
		if (e.getType() != UpdateType.SHORT) return;
		
		   for (Player p : Bukkit.getServer().getOnlinePlayers()){
		       Bukkit.getServer().getPluginManager().callEvent(new ScoreboardUpdateEvent(p));
		   }
	}
	
	@EventHandler
	public void scoreboardOnJoin(PlayerJoinEvent e){
		for (Player ap : Bukkit.getServer().getOnlinePlayers()){
		     Bukkit.getPluginManager().callEvent(new ScoreboardUpdateEvent(ap));
		}
	}
	
	@EventHandler
	public void removeCustomOnQuit(PlayerQuitEvent e){
		Player p = e.getPlayer();
		removeCustomScoreboardUser(p);
		for (Player ap : Bukkit.getServer().getOnlinePlayers()){
			if (ap.getName() == p.getName()) return;
		     Bukkit.getPluginManager().callEvent(new ScoreboardUpdateEvent(ap));
		}
	}
	
	@EventHandler
	public void defScoreboarder(GameStateChangeEvent e){
		if (e.getChangedState() != GameState.LOBBY) return;
		setDefaultScoreboard(new ArcadeScoreboard(null));
	}
	
	public void setDefaultScoreboard(Starboard scoreboard){
		this.defScoreboard = scoreboard;
		Logger.log("<ScoreboardManager> Changed default scoreboard to " + scoreboard.getName());
	}
	
	public void setCustomScoreboards(StarMap<String, Starboard> customScoreboards){
		this.customScoreboards = customScoreboards;
	}
	
	public void addCustomScoreboardUser(Player p, Starboard starboard){
		this.customScoreboards.put(p.getName(), starboard);
		Bukkit.getServer().getPluginManager().callEvent(new ScoreboardUpdateEvent(p));
		Logger.log("<ScoreboardManager> Added " + p.getName() + " to custom scoreboard users.");
	}
	
	public void removeCustomScoreboardUser(Player p){
		if (customScoreboards.containsKey(p.getName())){
		this.customScoreboards.remove(p.getName());
		Bukkit.getServer().getPluginManager().callEvent(new ScoreboardUpdateEvent(p));
		Logger.log("<ScoreboardManager> Removed " + p.getName() + " from custom scoreboard users.");
		} else {
			return;
		}
	}
	
	public Starboard getDefaultScoreboard(){
		return defScoreboard;
	}
	
	public StarMap<String, Starboard> getCustomScoreboardUsers(){
		return customScoreboards;
	}
	
}