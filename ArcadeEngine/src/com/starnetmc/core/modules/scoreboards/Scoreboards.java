package com.starnetmc.core.modules.scoreboards;

import java.util.List;
import java.util.Map.Entry;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.starnetmc.core.events.UpdateEvent;
import com.starnetmc.core.modules.manager.Module;
import com.starnetmc.core.modules.manager.ModuleType;
import com.starnetmc.core.util.StarMap;
import com.starnetmc.core.util.UpdateType;

public class Scoreboards extends Module {

	private static StarMap<Player, StarBoard> boards = new StarMap<Player, StarBoard>();

	public Scoreboards(JavaPlugin plugin) {
		super("Scoreboard Manager", 0.5, ModuleType.INFO, plugin);
	}

	public Scoreboards() {
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		Player p = e.getPlayer();
		createScoreboard(p);
	}
	
	@EventHandler
	public void onLeave(PlayerQuitEvent e){
		Player p = e.getPlayer();
		removeScoreboard(p);
	}
	
	@EventHandler
	public void update(UpdateEvent e) {
		if (e.getType() == UpdateType.SECOND) {
			for(Entry<Player, StarBoard> b : boards.entrySet()) {
				if(!b.getKey().isOnline()) {
					boards.remove(b.getKey());
				} else {
					b.getValue().update();
				}
			}
		}
	}

	@Override
	public void enable() {
		isEnabled = true;
		log("Enabled.");
	}

	@Override
	public void addCommands() {
		//Nothing here ;o
	}

	@Override
	public void disable() {
		isEnabled = false;
		log("Disabled.");
	}

	public static boolean isEnabled;
	
	public boolean hasScoreboard(Player p) {
		return boards.containsKey(p);
	}
	
	public void removeScoreboard(Player p) {
		if(hasScoreboard(p)) {
			boards.get(p).resetScores();
			boards.remove(p);
		}
	}
	
	public void updateScoreboard(Player p, List<String> lines) {
		if(hasScoreboard(p)) {
			int current = 1;
			
			for(String s : lines) {
				getScoreboard(p).setLine(current, s);
				
				current++;
			}
			
			getScoreboard(p).update();
		}
	}
	
	public void updateScoreboard(Player p, int line, String replace) {
		if(hasScoreboard(p)) {
			getScoreboard(p).setLine(line, replace);
			
			getScoreboard(p).update();
		}
	}
	
	public void setScoreboardTitle(Player p, String title) {
		if(hasScoreboard(p)) {
			getScoreboard(p).setDisplayName(title);
		}
	}
	
	public void createScoreboard(StarBoard scoreboard, Player p) {
		removeScoreboard(p);
		
		boards.put(p, new HubStarBoard(p, null));
	}
	
	public void createScoreboard(Player p) {
		removeScoreboard(p);
		
		boards.put(p, new HubStarBoard(p, null));
	}
	
	public StarBoard getScoreboard(Player p) {
		return boards.get(p);
	}
}