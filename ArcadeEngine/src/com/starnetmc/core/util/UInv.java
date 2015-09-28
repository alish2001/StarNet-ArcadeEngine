package com.starnetmc.core.util;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class UInv {

	public static void removeAllButCertainArmor(Player p, ItemStack[] removalArray){
		
		   if (p.getInventory().getArmorContents()[0].getType() == removalArray[0].getType()){
			   p.getInventory().setArmorContents(new ItemStack[]{null, p.getInventory().getArmorContents()[1], p.getInventory().getArmorContents()[2], p.getInventory().getArmorContents()[3]});	
		   }
		   
		   if (p.getInventory().getArmorContents()[1].getType() == removalArray[1].getType()){
			   p.getInventory().setArmorContents(new ItemStack[]{p.getInventory().getArmorContents()[0], null, p.getInventory().getArmorContents()[2], p.getInventory().getArmorContents()[3]});	
		   }
		   
		   if (p.getInventory().getArmorContents()[2].getType() == removalArray[2].getType()){
			   p.getInventory().setArmorContents(new ItemStack[]{p.getInventory().getArmorContents()[0], p.getInventory().getArmorContents()[1], null, p.getInventory().getArmorContents()[3]});	
		   }
		   
		   if (p.getInventory().getArmorContents()[3].getType() == removalArray[3].getType()){
			   p.getInventory().setArmorContents(new ItemStack[]{p.getInventory().getArmorContents()[0], p.getInventory().getArmorContents()[1], p.getInventory().getArmorContents()[2], null});	
		   }
	}
	
}