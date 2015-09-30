package com.starnetmc.ArcadeEngine.Games.Pacman.Kits.Pacmans;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import com.starnetmc.ArcadeEngine.Managers.Classes.Kit;
import com.starnetmc.ArcadeEngine.Managers.Classes.KitType;
import com.starnetmc.ArcadeEngine.Managers.Classes.Skill;
import com.starnetmc.ArcadeEngine.Utils.AF;

public class KitPMPacman extends Kit {
	
	public KitPMPacman(String kitName, List<String> kitDesc, int kitPrice, Material kitIconMat, List<Skill> skills, List<ItemStack> items, ItemStack[] armor, KitType type) {
		super(kitName, kitDesc, kitPrice, kitIconMat, skills, items, armor, type);
		setName(AF.boldYellow + "Pacman");
		setDesc(Arrays.asList
				(ChatColor.YELLOW + "He loves cookies!", 
				 ChatColor.YELLOW + "He's yellow!",
				 ChatColor.YELLOW + "GIVE it up for PACMAN!"));
		setPrice(0);
		setIconMat(Material.GOLD_BLOCK);
		
		ItemStack helm = new ItemStack(Material.LEATHER_HELMET);
		LeatherArmorMeta helmMeta = (LeatherArmorMeta) helm.getItemMeta();
		helmMeta.setColor(Color.YELLOW);
		helm.setItemMeta(helmMeta);
		
		ItemStack chest = new ItemStack(Material.LEATHER_CHESTPLATE);
		LeatherArmorMeta chestMeta = (LeatherArmorMeta) chest.getItemMeta();
		chestMeta.setColor(Color.YELLOW);
		chest.setItemMeta(chestMeta);
		
		ItemStack leg = new ItemStack(Material.LEATHER_LEGGINGS);
		LeatherArmorMeta legMeta = (LeatherArmorMeta) leg.getItemMeta();
		legMeta.setColor(Color.YELLOW);
		leg.setItemMeta(legMeta);
		
		ItemStack boot = new ItemStack(Material.LEATHER_BOOTS);
		LeatherArmorMeta bootMeta = (LeatherArmorMeta) boot.getItemMeta();
		bootMeta.setColor(Color.YELLOW);
		boot.setItemMeta(bootMeta);
		
		ItemStack[] iA;
		iA = new ItemStack[4];
		iA[0] = boot;
		iA[1] = leg;
		iA[2] = chest;
		iA[3] = helm;
		setArmor(iA);
		setSkills(new ArrayList<Skill>());
		addSkill(new SkillCookieEating(null, null, 0, null, null, null, null));
		setItems(new ArrayList<ItemStack>());
		setType(KitType.NORMAL);
		createIcon();
	}

}