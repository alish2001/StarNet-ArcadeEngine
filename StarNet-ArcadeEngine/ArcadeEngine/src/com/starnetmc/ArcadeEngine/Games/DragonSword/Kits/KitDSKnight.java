package com.starnetmc.ArcadeEngine.Games.DragonSword.Kits;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.starnetmc.ArcadeEngine.Managers.Classes.Kit;
import com.starnetmc.ArcadeEngine.Managers.Classes.KitType;
import com.starnetmc.ArcadeEngine.Managers.Classes.Skill;
import com.starnetmc.ArcadeEngine.Managers.Classes.Skills.SkillLightningStrike;
import com.starnetmc.ArcadeEngine.Utils.AF;

public class KitDSKnight extends Kit {
	
	public KitDSKnight(String kitName, List<String> kitDesc, int kitPrice, Material kitIconMat, List<Skill> skills, List<ItemStack> items, ItemStack[] armor, KitType type) {
		super(kitName, kitDesc, kitPrice, kitIconMat, skills, items, armor, type);
		setName(AF.boldWhite + "Knight");
		setDesc(Arrays.asList
				(ChatColor.YELLOW + "A very fine and might", 
				 ChatColor.YELLOW + "young Knight. Soon to",
				 ChatColor.YELLOW + "become the King."));
		setPrice(0);
		setIconMat(Material.IRON_SWORD);
		ItemStack[] iA;
		iA = new ItemStack[4];
		iA[0] = new ItemStack(Material.IRON_BOOTS);
		iA[1] = new ItemStack(Material.IRON_LEGGINGS);;
		iA[2] = new ItemStack(Material.IRON_CHESTPLATE);
		iA[3] = new ItemStack(Material.IRON_HELMET);;
		setArmor(iA);
		setSkills(new ArrayList<Skill>());
		setItems(new ArrayList<ItemStack>());
		addSkill(new SkillLightningStrike(null, null, 0, null, null, null, null));
		setType(KitType.NORMAL);
		for (Skill s : getSkills()){
			if (s != null){
			   addItem(s.getUseItem());
			}
		}
		createIcon();
	}

}