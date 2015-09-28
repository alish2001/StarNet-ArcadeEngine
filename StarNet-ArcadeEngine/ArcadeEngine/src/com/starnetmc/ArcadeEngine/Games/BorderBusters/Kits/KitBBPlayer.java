package com.starnetmc.ArcadeEngine.Games.BorderBusters.Kits;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.starnetmc.ArcadeEngine.Managers.Classes.Kit;
import com.starnetmc.ArcadeEngine.Managers.Classes.KitType;
import com.starnetmc.ArcadeEngine.Managers.Classes.Skill;

public class KitBBPlayer extends Kit {

	public KitBBPlayer(String kitName, List<String> kitDesc, int kitPrice, Material kitIconMat, List<Skill> skills, List<ItemStack> items, ItemStack[] armor, KitType type) {
		super(kitName, kitDesc, kitPrice, kitIconMat, skills, items, armor, type);
		setName(ChatColor.YELLOW + "" + ChatColor.BOLD + "BBPlayer");
		setDesc(Arrays.asList
				(ChatColor.YELLOW + "A Border Buster player.", 
				 ChatColor.YELLOW + "He cheat's on dem' borders. Hardcore."));
		setPrice(0);
		setIconMat(Material.GLASS);
		ItemStack[] iA;
		iA = new ItemStack[4];
		iA[0] = new ItemStack(Material.AIR);
		iA[1] = new ItemStack(Material.AIR);;
		iA[2] = new ItemStack(Material.AIR);
		iA[3] = new ItemStack(Material.AIR);;
		setArmor(iA);
		setSkills(new ArrayList<Skill>());
		setItems(new ArrayList<ItemStack>());
		setType(KitType.NORMAL);
		for (Skill s : getSkills()){
			if (s != null){
			addItem(s.getUseItem());
			}
		}
		createIcon();
	}
	
}