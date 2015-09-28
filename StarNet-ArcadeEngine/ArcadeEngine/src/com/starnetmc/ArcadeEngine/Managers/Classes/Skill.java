package com.starnetmc.ArcadeEngine.Managers.Classes;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import com.starnetmc.ArcadeEngine.Utils.ItemFactory;

public class Skill implements Listener {
	
	/* *NOTE*
	 * If you want your skill to be used by any item. or by an item type without a specific name.
	 * Just don't add the check for the name/item.
	 */
	
	private String skillName;
	private ItemStack useItem;
	private int rechargeTime;
	private Material useMat;
	private SkillActivationMethod skillA;
	private List<String> skillDesc;
	private ArrayList<String> cooldowns;
	private CooldownManager cooldownManager;
	
	public Skill(String skillName, List<String> skillDesc, int rechargeTime, Material useMat, SkillActivationMethod skillA, CooldownManager cooldownManager){
		
		this.skillName = skillName;
		this.skillDesc = skillDesc;
		this.rechargeTime = rechargeTime;
		this.skillA = skillA;
		this.useMat = useMat;
		this.cooldowns = new ArrayList<String>();
		this.cooldownManager = cooldownManager;
	}
	
	public void generateUseItem(){
		this.useItem = ItemFactory.createItem(useMat, skillName + " " + ChatColor.AQUA + "" + ChatColor.BOLD + "> " + ChatColor.YELLOW + "" + ChatColor.BOLD + skillA.getiString(), skillDesc, true);
	}

	public void rechargeSkill(Player p){
		cooldownManager.startCooldown(p);
	}
	
	public void setName(String skillName){
		this.skillName = skillName;
	}
	
	public void setDesc(List<String> skillDesc){
		this.skillDesc = skillDesc;
	}
	
	public void setRechargeTime(int time){
		this.rechargeTime = time;
	}
	
	public void setUseMat(Material mat){
		this.useMat = mat;
	}
	
	public void setSkillAMethod(SkillActivationMethod method){
		this.skillA = method;
	}
	
	public void setCooldowns(ArrayList<String> cooldowns){
		this.cooldowns = cooldowns;
	}
	
	public void addCooldown(Player p){
		this.cooldowns.add(p.getName());
	}
	
	public void setCooldownManager(CooldownManager cooldownManager){
		this.cooldownManager = cooldownManager;
	}
	
	public String getName(){
		return skillName;
	}
	
	public List<String> getDesc(){
		return skillDesc;
	}
	
	public int getRechargeTime(){
		return rechargeTime;
	}
	
	public ItemStack getUseItem(){
		return useItem;
	}
	
	public Material getUseMat(){
		return useMat;
	}
	
	public SkillActivationMethod getSkillAMethod(){
		return skillA;
	}
	
	public ArrayList<String> getCooldowns(){
		return cooldowns;
	}
	
	public CooldownManager getCooldownManager(){
		return cooldownManager;
	}
}
