package com.starnetmc.ArcadeEngine.Games.Spleef.Kits;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.starnetmc.ArcadeEngine.Managers.Classes.Kit;
import com.starnetmc.ArcadeEngine.Managers.Classes.KitType;
import com.starnetmc.ArcadeEngine.Managers.Classes.Skill;
import com.starnetmc.ArcadeEngine.Managers.Classes.Skills.SkillSpleefer;
import com.starnetmc.ArcadeEngine.Utils.AF;

public class KitSpleefer extends Kit {
	
	public KitSpleefer(String kitName, List<String> kitDesc, int kitPrice, Material kitIconMat, List<Skill> skills, List<ItemStack> items, ItemStack[] armor, KitType type) {
		super(kitName, kitDesc, kitPrice, kitIconMat, skills, items, armor, type);
		setName(AF.boldWhite + "Spleefer");
		setDesc(Arrays.asList
				(ChatColor.YELLOW + "Who know's who he will", 
				 ChatColor.YELLOW + "Spleef next...",
				 ChatColor.YELLOW + "It might be you!"));
		setPrice(0);
		setIconMat(Material.IRON_SPADE);
		ItemStack[] iA;
		iA = new ItemStack[4];
		iA[0] = new ItemStack(Material.AIR);
		iA[1] = new ItemStack(Material.AIR);;
		iA[2] = new ItemStack(Material.AIR);
		iA[3] = new ItemStack(Material.AIR);;
		setArmor(iA);
		setSkills(new ArrayList<Skill>());
		setItems(new ArrayList<ItemStack>());
		addSkill(new SkillSpleefer(null, null, 0, null, null, null, null));
		setType(KitType.NORMAL);
		
		createIcon();
	}

}
