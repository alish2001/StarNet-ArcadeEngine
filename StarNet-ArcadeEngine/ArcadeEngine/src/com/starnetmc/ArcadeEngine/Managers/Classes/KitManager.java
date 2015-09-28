package com.starnetmc.ArcadeEngine.Managers.Classes;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import com.starnetmc.ArcadeCore;
import com.starnetmc.ArcadeEngine.ArcadeManager;
import com.starnetmc.ArcadeEngine.Events.ScoreboardUpdateEvent;
import com.starnetmc.ArcadeEngine.Managers.GameState;
import com.starnetmc.ArcadeEngine.Managers.Classes.GUI.KitUI;
import com.starnetmc.ArcadeEngine.Utils.AF;
import com.starnetmc.ArcadeEngine.Utils.ItemFactory;
import com.starnetmc.ArcadeEngine.Utils.Logger;
import com.starnetmc.ArcadeEngine.Utils.USound;
import com.starnetmc.core.util.F;
import com.starnetmc.core.util.UInv;

public class KitManager implements Listener {
	
	private ArcadeManager manager;
	
	private HashMap<Player, Kit> playerKits;
	
	public KitManager(ArcadeManager manager){
		this.manager = manager;
		playerKits = new HashMap<Player, Kit>();
		Bukkit.getServer().getPluginManager().registerEvents(this, ArcadeCore.getPlugin());
		Bukkit.getServer().getPluginManager().registerEvents(new KitUI(), ArcadeCore.getPlugin());
		Logger.log("<KitManager> KitManager enabled.");
	}

	public void setKit(Player p, Kit kit, boolean info){
		if (playerKits.containsKey(p)){
			removeKit(p);
		}
		playerKits.put(p, kit);
        setKitItems(p, kit);
		if (info){
		      AF.sendKitInfo(p, kit);
		      USound.PSound(p, Sound.ORB_PICKUP, 2.75F, 1.25F);
		    }
		  Bukkit.getPluginManager().callEvent(new ScoreboardUpdateEvent(p));
		}
	
	public void setKitItems(Player p, Kit kit){
		p.getInventory().setArmorContents(kit.getArmor());
		for (ItemStack itms : kit.getItems()){
				p.getInventory().addItem(itms);
			}
	}
	
	public void removeKit(Player p){
		removeKitItems(p);
		playerKits.remove(p);
	    //Bukkit.getPluginManager().callEvent(new ScoreboardUpdateEvent(p));
	}
	
	public void removeKitItems(Player p){
		if (getKit(p) == null 
		    || getKit(p).getItems() == null
			|| getKit(p).getItems().size() == 0 ) return;
		
		for (ItemStack itm : getKit(p).getItems()){
			Material mat = itm.getType();
			p.getInventory().remove(mat);
		}
		
		UInv.removeAllButCertainArmor(p, getKit(p).getArmor());
	}
	
	public void resetKits(boolean shutdown) {
		Iterator<Player> aT = playerKits.keySet().iterator();
		 while(aT.hasNext()){
			 Player p = aT.next();
		    if (getKit(p) != null){
				removeKitItems(p);
			}
		 }	
			playerKits.clear();
			
			for (Player p : Bukkit.getServer().getOnlinePlayers()){
				giveKitSelector(p);
				
				if (!shutdown){
					setKit(p, manager.getGame().getGClass().getDefaultKit(), false);
				}
				
				Bukkit.getPluginManager().callEvent(new ScoreboardUpdateEvent(p));
			}
			
			Logger.log("<KitManager> Kits Reset.");
		}
	
	public Kit getKit(Player p){
		return playerKits.get(p);
	}
	
	public HashMap<Player, Kit> getUserKits(){
		return playerKits;
	}
	
	private void giveKitSelector(Player p){
		  ItemStack kitSelector = ItemFactory.createItem(Material.NETHER_STAR, F.boldAqua + "Kits", Arrays.asList(" ",
				  AF.yellow + "Choose the Kit/Class you", 
				  AF.yellow + "want to play as, in", 
				  AF.yellow + "the current Game!"));
		  
		     p.getInventory().setItem(4, kitSelector);
	}
	
	@EventHandler
	public void kitOnJoin(PlayerJoinEvent e){
		if (manager.isState(GameState.INGAME)) return;
		Player p = e.getPlayer();
	    
		giveKitSelector(p);
		setKit(p, manager.getGame().getGClass().getDefaultKit(), false);
	}
	
	@EventHandler
	public void kitOnQuit(PlayerQuitEvent e){
	  Player p = e.getPlayer();
	  
	  removeKit(p);
	  p.getInventory().clear();
	
	}
}