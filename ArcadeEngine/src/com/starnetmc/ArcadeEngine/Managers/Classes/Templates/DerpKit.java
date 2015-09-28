package com.starnetmc.ArcadeEngine.Managers.Classes.Templates;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.starnetmc.ArcadeEngine.Managers.Classes.Kit;
import com.starnetmc.ArcadeEngine.Managers.Classes.KitType;
import com.starnetmc.ArcadeEngine.Managers.Classes.Skill;

public class DerpKit extends Kit {
	
	public DerpKit(String kitName, List<String> kitDesc, int kitPrice, Material kitIconMat, List<Skill> skills, List<ItemStack> items, ItemStack[] armor, KitType type) {
		super(kitName, kitDesc, kitPrice, kitIconMat, skills, items, armor, type);
		setName(ChatColor.YELLOW + "" + ChatColor.BOLD + "DerpMaster");
		setDesc(Arrays.asList
				(ChatColor.YELLOW + "Potato Swag", 
				 ChatColor.YELLOW + "with extra derp"));
		setPrice(0);
		setIconMat(Material.BAKED_POTATO);
		ItemStack[] iA;
		iA = new ItemStack[4];
		iA[0] = new ItemStack(Material.AIR);
		iA[1] = new ItemStack(Material.AIR);;
		iA[2] = new ItemStack(Material.GOLD_CHESTPLATE);
		iA[3] = new ItemStack(Material.AIR);;
		setArmor(iA);
		setSkills(new ArrayList<Skill>());
		setItems(new ArrayList<ItemStack>());
		addSkill(new DerpSkill(null, null, 0, null, null, null, null));
		setType(KitType.NORMAL);
		for (Skill s : getSkills()){
			if (s != null){
			addItem(s.getUseItem());
			}
		}
		createIcon();
	}
}
