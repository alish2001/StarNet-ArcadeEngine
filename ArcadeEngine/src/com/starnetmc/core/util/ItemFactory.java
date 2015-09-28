package com.starnetmc.core.util;

import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemFactory {

	public static ItemStack createItem(Material item, String DName, List<String> lore, boolean hasLore){
		ItemStack i = new ItemStack(item);
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(DName);
		if (hasLore == true){
			im.setLore(lore);
			i.setItemMeta(im);
			return i;
		} else {
			i.setItemMeta(im);
			return i;
		}
	}
	
	public static ItemStack createItem(Material item, String DName, List<String> lore, boolean hasLore, int amount){
		ItemStack i = new ItemStack(item, amount);
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(DName);
		if (hasLore == true){
			im.setLore(lore);
			i.setItemMeta(im);
			return i;
		} else {
			i.setItemMeta(im);
			return i;
		}
	}
	
	public static ItemStack createItem(Material item, String DName){
		ItemStack i = new ItemStack(item);
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(DName);
		i.setItemMeta(im);
		return i;
	}
	
	public static ItemStack createItem(Material item, String DName, List<String> lore){
		ItemStack i = new ItemStack(item);
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(DName);
		im.setLore(lore);
		i.setItemMeta(im);
		return i;
	}
	
	public static ItemStack createItem(Material item, String DName, Enchantment ench, int level){
		ItemStack i = new ItemStack(item);
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(DName);
		im.addEnchant(ench, level, true);
		i.setItemMeta(im);
		return i;
	}
	
	public static ItemStack createItem(Material item, String DName, List<String> lore, Enchantment ench, int level){
		ItemStack i = new ItemStack(item);
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(DName);
		im.setLore(lore);
		im.addEnchant(ench, level, true);
		i.setItemMeta(im);
		return i;
	}
	
	public static ItemStack createItem(Material item, String DName, StarMap<Enchantment, Integer> enchs){
		ItemStack i = new ItemStack(item);
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(DName);
		
		for (Entry<Enchantment, Integer> e : enchs.entrySet()){
			im.addEnchant(e.getKey(), e.getValue(), true);	
		}
		
		i.setItemMeta(im);
		return i;
	}
	
	public static ItemStack createItem(Material item, String DName, List<String> lore, StarMap<Enchantment, Integer> enchs){
		ItemStack i = new ItemStack(item);
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(DName);
		im.setLore(lore);
		
		for (Entry<Enchantment, Integer> e : enchs.entrySet()){
			im.addEnchant(e.getKey(), e.getValue(), true);	
		}
		
		i.setItemMeta(im);
		return i;
	}
	
	public static ItemStack createItem(Material item, String DName, List<String> lore, int amount){
		ItemStack i = new ItemStack(item, amount);
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(DName);
		i.setItemMeta(im);
		return i;
	}
	
}