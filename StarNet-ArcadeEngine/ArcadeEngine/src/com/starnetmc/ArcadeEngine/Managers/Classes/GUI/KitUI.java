package com.starnetmc.ArcadeEngine.Managers.Classes.GUI;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import com.starnetmc.ArcadeCore;
import com.starnetmc.ArcadeEngine.Games.TeamGame.TeamGame;
import com.starnetmc.ArcadeEngine.Managers.Classes.Kit;
import com.starnetmc.ArcadeEngine.Utils.USound;
import com.starnetmc.core.util.F;
	
public class KitUI implements Listener {
	
	@EventHandler
	public void UITheGUI(InventoryClickEvent e){
		Player p = (Player) e.getWhoClicked();
		if (!ChatColor.stripColor(e.getInventory().getName()).equalsIgnoreCase("Kits")) return;
		e.setCancelled(true);
		
			for (Kit k : ArcadeCore.getArcadeManager().getGame().getGClass().getKits()){
				if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase(ChatColor.stripColor(k.getName()))) {
					if (ArcadeCore.getArcadeManager().getKitManager().getUserKits().containsKey(p)){
						ArcadeCore.getArcadeManager().getKitManager().removeKit(p);
					}
					
					ArcadeCore.getArcadeManager().getKitManager().setKit(p, k, true);	
			}
		}
			

	}

	@EventHandler
	public void UITheTeamGUI(InventoryClickEvent e){
		
		if (!ChatColor.stripColor(e.getInventory().getName()).equalsIgnoreCase("Team Kits")) return;
		Player p = (Player) e.getWhoClicked();
		e.setCancelled(true);
		
			for (Kit k : ArcadeCore.getArcadeManager().getGame().getGClass().getKits()){
				
				if (((TeamGame)ArcadeCore.getArcadeManager().getGame().getGClass()).getTeamManager().getPlayersTeam(p).getTeamKits().contains(k)){
				if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase(ChatColor.stripColor(k.getName()))) {
					if (ArcadeCore.getArcadeManager().getKitManager().getUserKits().containsKey(p)){
						ArcadeCore.getArcadeManager().getKitManager().removeKit(p);
					}
					ArcadeCore.getArcadeManager().getKitManager().setKit(p, k, true);	
				}
			} else {
				USound.PSound(p, Sound.ANVIL_BREAK, 2f, 1.25f);
			}
		}
		
	}
	
	@EventHandler
	public void kitSelectorUse(PlayerInteractEvent e){
		Player p = e.getPlayer();
		if (e.getItem() == null) return;
		if (e.getItem().getType() != Material.NETHER_STAR) return;
		if (!e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase(F.boldAqua + "Kits")) return;
		
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