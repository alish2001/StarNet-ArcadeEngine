package com.starnetmc.ArcadeEngine.Games.TeamGame;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.starnetmc.ArcadeCore;
import com.starnetmc.ArcadeEngine.ArcadeManager;
import com.starnetmc.ArcadeEngine.Events.GameStateChangeEvent;
import com.starnetmc.ArcadeEngine.Managers.GameState;
import com.starnetmc.ArcadeEngine.Utils.Logger;

public class TeamManager implements Listener {
	
	private ArcadeManager manager;
	private List<Team> teams;
	
	public TeamManager(ArcadeManager manager, List<Team> teams){
		this.teams = teams;
		this.manager = manager;
		Bukkit.getServer().getPluginManager().registerEvents(this, ArcadeCore.getPlugin());
	}
	
	public TeamManager(ArcadeManager manager){
		this.teams = new ArrayList<Team>();
		this.manager = manager;
		Bukkit.getServer().getPluginManager().registerEvents(this, ArcadeCore.getPlugin());
	}
	
	public void init(){
		for (Player p : manager.getPlayerManager().getAlivePlayers()){
		if (teams.size() == 2){
			Team t1 = teams.get(0);
			Team t2 = teams.get(1);
			
			if (t1.getTeamPlayers().size() == t1.getMaxPlayers()){
				t2.addTeamPlayer(p);
				
			} else if (t2.getTeamPlayers().size() == t2.getMaxPlayers()){
				t1.addTeamPlayer(p);
				
			} else if (t1.getTeamPlayers().size() > t2.getTeamPlayers().size()){
				t2.addTeamPlayer(p);
				
			} else if (t2.getTeamPlayers().size() > t1.getTeamPlayers().size()){
				t1.addTeamPlayer(p);
			}
			
			Logger.log("<TeamManager> Added player to team using 2-Team setup.");
			return;
		}
		
		if (teams.size() == 4){
			Team t1 = teams.get(0);
			Team t2 = teams.get(1);
			Team t3 = teams.get(2);
			Team t4 = teams.get(3);
			
			if (t1.getTeamPlayers().size() == t1.getMaxPlayers()){
				t2.addTeamPlayer(p);
				
			} else if (t2.getTeamPlayers().size() == t2.getMaxPlayers()){
				t1.addTeamPlayer(p);
				
			} else if (t3.getTeamPlayers().size() == t3.getMaxPlayers()){
				t2.addTeamPlayer(p);
				
			} else if (t4.getTeamPlayers().size() == t4.getMaxPlayers()){
				t3.addTeamPlayer(p);
			} else {
				
			Team smallestTeam = t1;
			
		    for (Team team : teams) {
		        if (team.getTeamPlayers().size() < smallestTeam.getTeamPlayers().size()) {
		            smallestTeam = team;
		        }
		    }
		    
		    smallestTeam.addTeamPlayer(p);
			}
		  }
		}
	}
	
	public void reset(){
		teams.clear();
		init();
		Logger.log("<TeamManager> Teams reset.");
	}
	
	@EventHandler
	public void resetOnEnd(GameStateChangeEvent e){
		if (e.getChangedState() != GameState.LOBBY) return;
		reset();
	}
	
	@EventHandler
	public void teamJoiner(PlayerJoinEvent e){
		Player p = e.getPlayer();
		
		if (teams.size() == 2){
			Team t1 = teams.get(0);
			Team t2 = teams.get(1);
			
			if (t1.getTeamPlayers().size() == t1.getMaxPlayers()){
				t2.addTeamPlayer(p);
				
			} else if (t2.getTeamPlayers().size() == t2.getMaxPlayers()){
				t1.addTeamPlayer(p);
				
			} else if (t1.getTeamPlayers().size() > t2.getTeamPlayers().size()){
				t2.addTeamPlayer(p);
				
			} else if (t2.getTeamPlayers().size() > t1.getTeamPlayers().size()){
				t1.addTeamPlayer(p);
			}
			
			Logger.log("<TeamManager> Added player to team using 2-Team setup.");
			return;
		}
		
		if (teams.size() == 4){
			Team t1 = teams.get(0);
			Team t2 = teams.get(1);
			Team t3 = teams.get(2);
			Team t4 = teams.get(3);
			
			if (t1.getTeamPlayers().size() == t1.getMaxPlayers()){
				t2.addTeamPlayer(p);
				
			} else if (t2.getTeamPlayers().size() == t2.getMaxPlayers()){
				t1.addTeamPlayer(p);
				
			} else if (t3.getTeamPlayers().size() == t3.getMaxPlayers()){
				t2.addTeamPlayer(p);
				
			} else if (t4.getTeamPlayers().size() == t4.getMaxPlayers()){
				t3.addTeamPlayer(p);
			} else {
				
			Team smallestTeam = t1;
			
		    for (Team team : teams) {
		        if (team.getTeamPlayers().size() < smallestTeam.getTeamPlayers().size()) {
		            smallestTeam = team;
		        }
		    }
		    
		    smallestTeam.addTeamPlayer(p);
			}
			
			Logger.log("<TeamManager> Added player to team using 4-Team setup.");
			return;
		}
		
		Logger.log("<TeamManager> Tried to add player to a team but team size was not equal to 2 or 4.");
	}
	
	@EventHandler
	public void teamRemover(PlayerQuitEvent e){
		Player p = e.getPlayer();
		if (getPlayersTeam(p) != null){
		getPlayersTeam(p).removeTeamPlayer(p);
		}
	}
	
	public void setAllTeams(List<Team> teams){
		this.teams = teams;
	}
	
	public void addTeam(Team team){
		this.teams.add(team);
	}
	
	public List<Team> getAllTeams(){
		return teams;
	}
	
	public Team getPlayersTeam(Player p){
		Team team = null;
		
		for (Team t : teams){
			if (t.getTeamPlayers().contains(p)){
				team = t;
			}
		}
		
		return team;
	}
	
}