package com.starnetmc.core.gadgets;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import com.starnetmc.ArcadeCore;
import com.starnetmc.core.util.F;
import com.starnetmc.core.util.ItemFactory;
import com.starnetmc.core.util.Logger;

public class Gadget implements Listener {
	
	private String gadgetName;
	private ArrayList<String> gadgetDiscription;
	private Material gadgetIcon;
	private Material gadgetItem;
	private GadgetType type;
	
	public Gadget(String gadgetName, ArrayList<String> gadgetDiscription, Material gadgetIcon, Material gadgetItem, GadgetType type){
		
		this.gadgetName = gadgetName;
		this.gadgetDiscription = gadgetDiscription;
		this.gadgetIcon = gadgetIcon;
		this.gadgetItem = gadgetItem;
		this.type = type;
	}
	
	public void register(){
		Bukkit.getServer().getPluginManager().registerEvents(this, ArcadeCore.getPlugin());
		Logger.log("<Gadgets> Gadget: " + gadgetName + " has been registered.");
	}
	
	public void unRegister(){
		HandlerList.unregisterAll(this);
		Logger.log("<Gadgets> Gadget: " + gadgetName + " has been Un-Registered.");
	}	
	
	public void setName(String name){
		this.gadgetName = name;
	}
	
	public void setDesc(ArrayList<String> desc){
		this.gadgetDiscription = desc;
	}
	
	public void addDesc(String desc){
		this.gadgetDiscription.add(desc);
	}
	
	public void setIconMat(Material icon){
		this.gadgetIcon = icon;
	}
	
	public void setItemMat(Material item){
		this.gadgetItem = item;
	}
	
	public void setType(GadgetType type){
		this.type = type;
	}
	
	public String getName(){
		
		return gadgetName;
	}
	
	public ArrayList<String> getDisc(boolean enabled){
		
		this.gadgetDiscription.add(0, " ");
		
		if (enabled){
			
			ArrayList<String> disabledDesc = gadgetDiscription;
			
			disabledDesc.add(" ");
			disabledDesc.add(F.boldRed + "Click to Disable!");
			
			return disabledDesc;
			
		} else {
			
			ArrayList<String> enabledDesc = gadgetDiscription;
			
			enabledDesc.add(" ");
			enabledDesc.add(F.boldGreen + "Click to Enable!");
			
			return enabledDesc;
			
		}
	}
	
	public ItemStack getIcon(boolean enabled){
		
		if (enabled){
			return ItemFactory.createItem(gadgetIcon, gadgetName, getDisc(true), true);
		}
		else {
			return ItemFactory.createItem(gadgetIcon, gadgetName, getDisc(false), true);
		}
	}
	
	public ItemStack getItem(){
		
		return ItemFactory.createItem(gadgetItem, gadgetName, null, false);
	}
	
	public GadgetType getType(){
		return type;
	}

}