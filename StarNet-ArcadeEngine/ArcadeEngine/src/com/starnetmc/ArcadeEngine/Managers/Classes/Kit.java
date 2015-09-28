package com.starnetmc.ArcadeEngine.Managers.Classes;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

import com.starnetmc.ArcadeCore;
import com.starnetmc.ArcadeEngine.Utils.AF;
import com.starnetmc.ArcadeEngine.Utils.ItemFactory;
import com.starnetmc.ArcadeEngine.Utils.Logger;

public class Kit {

	private String kitName;
	private List<String> kitDesc;
	private int kitPrice;
	private Material kitIconMat;
	private ItemStack kitIcon;
	private ItemStack[] armor;
	private List<ItemStack> items;
	private List<Skill> skills;
	private KitType type;

	public Kit(String kitName, List<String> kitDesc, int kitPrice, Material kitIconMat, List<Skill> skills, List<ItemStack> items,
			ItemStack[] armor, KitType type) {

		this.kitName = kitName;
		this.kitDesc = kitDesc;
		this.kitPrice = kitPrice;
		this.kitIconMat = kitIconMat;
		this.armor = armor;
		this.items = items;
		this.skills = skills;
		this.type = type;
	}

	public void setup() {
		for (Skill s : skills) {
			if (s != null) {
				Bukkit.getServer().getPluginManager().registerEvents(s, ArcadeCore.getPlugin());
				Logger.log("<KitManager> Skill " + s.getName() + " Has been initialized.");
			}
		}
	}

	public void unRegister() {
		for (Skill s : skills) {
			if (s != null) {
				HandlerList.unregisterAll(s);
				Logger.log("<KitManager> Skill " + s.getName() + " Has been de-initialized.");
			}
		}
	}

	public void createIcon() {

		ArrayList<String> finalIconDesc = new ArrayList<String>();
		finalIconDesc.addAll(kitDesc);

		if (type == KitType.NORMAL || type == null) {
			finalIconDesc.add(0, ChatColor.GREEN + "" + ChatColor.STRIKETHROUGH + "------------------");
		}
		if (type == KitType.PREMIUM) {
			finalIconDesc.add(0, ChatColor.AQUA + "" 	+ ChatColor.STRIKETHROUGH + "------------------");
		}
		finalIconDesc.add(1, "");

		if (skills != null) {
			List<String> skillDescList = new ArrayList<String>();
			skillDescList.add("");
			skillDescList.add(AF.boldAqua + "SKILLS");
			skillDescList.add("");
			for (Skill s : skills) {
				skillDescList.add(AF.boldGray + "- " + s.getName());
			}
			finalIconDesc.addAll(skillDescList);
		}

		if (type == KitType.PREMIUM) {
			finalIconDesc.add(ChatColor.YELLOW + "Price: " + ChatColor.GOLD + kitPrice);
		}

		this.kitIcon = ItemFactory.createItem(kitIconMat, kitName, finalIconDesc, true);
	}

	public void setName(String name) {
		this.kitName = name;
	}

	public void setDesc(List<String> desc) {
		this.kitDesc = desc;
	}

	public void setPrice(int kitPrice) {
		this.kitPrice = kitPrice;
	}

	public void setIconMat(Material iconMat) {
		this.kitIconMat = iconMat;
	}

	public void setArmor(ItemStack[] armor) {
		this.armor = armor;
	}

	public void setItems(List<ItemStack> items) {
		this.items = items;
	}

	public void addItem(ItemStack item) {
		this.items.add(item);
	}

	public void setSkills(List<Skill> skills) {
		this.skills = skills;
	}

	public void addSkill(Skill skill) {
		this.skills.add(skill);
	}

	public void setType(KitType type) {
		this.type = type;
	}

	public String getName() {
		return kitName;
	}

	public List<String> getDesc() {
		return kitDesc;
	}

	public int getPrice() {
		return kitPrice;
	}

	public Material getIconMat() {
		return kitIconMat;
	}

	public ItemStack getIcon() {
		return kitIcon;
	}

	public ItemStack[] getArmor() {
		return armor;
	}

	public List<ItemStack> getItems() {
		return items;
	}

	public List<Skill> getSkills() {
		return skills;
	}

	public KitType getType() {
		return type;
	}

	public boolean hasSkill(Skill skill) {
		if (skills.contains(skill)) {
			return true;
		} else {
			return false;
		}
	}
}