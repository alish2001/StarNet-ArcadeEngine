package com.starnetmc.ArcadeEngine.Managers.Classes;

import org.bukkit.entity.Player;

import com.starnetmc.ArcadeCore;
import com.starnetmc.ArcadeEngine.Games.TeamGame.TeamGame;
import com.starnetmc.ArcadeEngine.Managers.Classes.GUI.KitGUI;
import com.starnetmc.ArcadeEngine.Managers.Commands.ArcadeCMDS;
import com.starnetmc.core.commands.util.CommandBase;
import com.starnetmc.core.util.Rank;

public class KitCommand extends CommandBase<ArcadeCMDS> {
	
	public KitCommand(ArcadeCMDS plugin) {
		super(plugin, Rank.DEFAULT, new String[] { "kit", "kitselect", "kitselector"});
	}
	
	public void execute(Player player, String[] args) {
		Player p = player;
		
		if (ArcadeCore.getArcadeManager().getGame().getGClass() instanceof TeamGame){
		      if (((TeamGame) ArcadeCore.getArcadeManager().getGame().getGClass()).getTeamManager().getAllTeams().get(0).getKitExclusivity() 
		    		||((TeamGame) ArcadeCore.getArcadeManager().getGame().getGClass()).getTeamManager().getAllTeams().get(1).getKitExclusivity() 
		    		||((TeamGame) ArcadeCore.getArcadeManager().getGame().getGClass()).getTeamManager().getAllTeams().get(2).getKitExclusivity()
		    		||((TeamGame) ArcadeCore.getArcadeManager().getGame().getGClass()).getTeamManager().getAllTeams().get(3).getKitExclusivity()){
		    	  
				    if (((TeamGame)ArcadeCore.getArcadeManager().getGame().getGClass()).getTeamManager().getAllTeams().size() == 2){
				    	KitGUI.openTwoTeamKitGUI(p);
				    	return;
				    } else if (((TeamGame)ArcadeCore.getArcadeManager().getGame().getGClass()).getTeamManager().getAllTeams().size() == 4){
				    	KitGUI.openFourTeamKitGUI(p);
				    	return;
				    }
				    
		      } else {
		    	  
		    	  KitGUI.openKitGUI(p);
		    	  return;
		      }
				
			} else {
				
			KitGUI.openKitGUI(p);
			return;
			}
		}
}