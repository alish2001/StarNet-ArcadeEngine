package com.starnetmc.ArcadeEngine.Managers.GameProperties.PropertiesManagers;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.util.Vector;

import com.starnetmc.ArcadeEngine.Managers.GameProperties.GamePropertiesManager;
import com.starnetmc.ArcadeEngine.Managers.GameProperties.PropertiesManager;

public class PropertiesManager_Blocks extends PropertiesManager {

	public PropertiesManager_Blocks(GamePropertiesManager gamePropertiesManager, String propertiesManagerName) {
		super(gamePropertiesManager, "Blocks");
	}
	
	public PropertiesManager_Blocks(GamePropertiesManager gamePropertiesManager) {
		super(gamePropertiesManager);
		setName("Blocks");
	}
	
	@EventHandler
	public void onExplosion(EntityExplodeEvent e){
	
		if (getGamePropertiesManager().getProperties().getBlockExplosion() == false){
			if (getGamePropertiesManager().getProperties().getBlockExplosionCustom() == false){
				e.blockList().clear();
			} else {
			
			for (Block b : e.blockList()){
			for (Material m : getGamePropertiesManager().getProperties().getCustomExplosionBlocks()){
				if (b.getType() != m){
					e.blockList().remove(b);
				}
			  }
			}
			
			for (Block b : e.blockList()){
				float x = (float) -1.5 + (float) (Math.random() * ((1.5 - -1.5) + 1));
				float y = (float) -1.5 + (float) (Math.random() * ((2 - -2) + 1));
				float z = (float) -1.5 + (float) (Math.random() * ((1.5 - -1.5) + 1));
				
				@SuppressWarnings("deprecation")
				FallingBlock fB = b.getWorld().spawnFallingBlock(b.getLocation(), b.getType(), b.getData());
				fB.setDropItem(false);
				fB.setVelocity(new Vector(x, y, z));
			}
		}
			
		} else {
			
			for (Block b : e.blockList()){
				float x = (float) -1.5 + (float) (Math.random() * ((1.5 - -1.5) + 1));
				float y = (float) -1.5 + (float) (Math.random() * ((2 - -2) + 1));
				float z = (float) -1.5 + (float) (Math.random() * ((1.5 - -1.5) + 1));
				
				@SuppressWarnings("deprecation")
				FallingBlock fB = b.getWorld().spawnFallingBlock(b.getLocation(), b.getType(), b.getData());
				fB.setDropItem(false);
				fB.setVelocity(new Vector(x, y, z));
			}
		}
		
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e){
		
		if (getGamePropertiesManager().getProperties().getBlockBreak() == false) e.setCancelled(true);
		if (getGamePropertiesManager().getProperties().getBlockBreakCustom() == false) return;
		
		boolean breakable = false;
		for (Material m : getGamePropertiesManager().getProperties().getCustomBreakBlocks()){
			if (e.getBlock().getType() == m){
				breakable = true;
			}
		}
		
	    if (!breakable) e.setCancelled(true);
	}
	
   @EventHandler
   public void onBlockPlace(BlockPlaceEvent e){
	   
		if (getGamePropertiesManager().getProperties().getBlockPlace() == false) e.setCancelled(true);
		if (getGamePropertiesManager().getProperties().getBlockPlaceCustom() == false) return;
		
		boolean placeable = false;
		for (Material m : getGamePropertiesManager().getProperties().getCustomPlaceBlocks()){
			if (e.getBlock().getType() == m){
				placeable = true;
			}
		}
		
	    if (!placeable) e.setCancelled(true);
   }

}
