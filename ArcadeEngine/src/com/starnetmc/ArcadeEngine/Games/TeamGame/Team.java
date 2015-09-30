package com.starnetmc.ArcadeEngine.Games.TeamGame;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.starnetmc.ArcadeEngine.Events.TeamChangeEvent;
import com.starnetmc.ArcadeEngine.Managers.Classes.Kit;
import com.starnetmc.ArcadeEngine.Utils.Logger;
import com.starnetmc.core.modules.Chat;
import com.starnetmc.core.util.ItemFactory;

public class Team {
	
	private String teamName;
	private String teamTag;
	private ChatColor teamColor;
	private ItemStack teamIcon;
	private List<Player> teamPlayers;
	private int maxPlayers;
	
	private List<Kit> teamKits;
	private boolean exclusiveKits;
	
	public Team(String teamName, String teamTag, ChatColor teamColor, Material teamIcon, List<Player> teamPlayers, int maxPlayers, List<Kit> teamKits, boolean exclusiveKits){
		this.teamName = teamName;
		this.teamTag = teamTag;
		this.teamColor = teamColor;
		setIcon(teamIcon);
		this.teamPlayers = teamPlayers;
		this.maxPlayers = maxPlayers;
		this.teamKits = teamKits;
		this.exclusiveKits = exclusiveKits;
	}
	
	public void setName(String teamName){
		this.teamName = teamName;
	}
	
	public void setTag(String teamTag){
		this.teamTag = teamTag;
	}
	
	public void setColor(ChatColor teamColor){
		this.teamColor = teamColor;
	}
	
	public void setIcon(Material iconMat){
		this.teamIcon = ItemFactory.createItem(iconMat, getName(true));
	}
	
	public void setTeamPlayers(List<Player> teamPlayers){
		this.teamPlayers = teamPlayers;
	}
	
	public void addTeamPlayer(Player p){
		this.teamPlayers.add(p);
		p.setDisplayName(getTag(true) + " " + p.getDisplayName());
		Bukkit.getServer().getPluginManager().callEvent(new TeamChangeEvent(p, this));
		Logger.log("<TeamManager> Added player: " + p.getName() + " to Team: " + getName(false));
	}
	
	public void removeTeamPlayer(Player p){
		this.teamPlayers.remove(p);
		Chat.setTag(p);
		
		Bukkit.getServer().getPluginManager().callEvent(new TeamChangeEvent(p, new Team("NoTeam", "N.T", ChatColor.RED, null, teamPlayers, 0, null, false)));
		Logger.log("<TeamManager> Removed player: " + p.getName() + " from Team: " + getName(false));
	}
	
	public void setMaxPlayers(int maxPlayers){
		this.maxPlayers = maxPlayers;
	}
	
	public void setTeamKits(List<Kit> teamKits){
		this.teamKits = teamKits;
	}
	
	public void addTeamKit(Kit kit){
		this.teamKits.add(kit);
	}
	
	public void setKitExclusivity(boolean exclusiveKits){
		this.exclusiveKits = exclusiveKits;
	}
	
	public String getName(boolean color){
		if (color){
			return teamColor + "" + ChatColor.BOLD + teamName;
		}
		return teamName;
	}
	
	public String getTag(boolean color){
		if (color){
			return teamColor + "" + ChatColor.BOLD + teamTag;
		}
		return teamTag;
	}
	
	public ChatColor getColor(){
		return teamColor;
	}
	
	public ItemStack getIcon(){
		return teamIcon;
	}
	
	public List<Player> getTeamPlayers(){
		return teamPlayers;
	}
	
	public int getMaxPlayers(){
		return maxPlayers;
	}
	
	public List<Kit> getTeamKits(){
		return teamKits;
	}
	
	public boolean getKitExclusivity(){
		return exclusiveKits;
	}

}