package com.starnetmc.ArcadeEngine.Managers.Classes.Skills;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.starnetmc.ArcadeCore;
import com.starnetmc.ArcadeEngine.Managers.Classes.CooldownManager;
import com.starnetmc.ArcadeEngine.Managers.Classes.CooldownManager.Time;
import com.starnetmc.ArcadeEngine.Managers.Classes.Skill;
import com.starnetmc.ArcadeEngine.Managers.Classes.SkillActivationMethod;
import com.starnetmc.ArcadeEngine.Utils.AF;

public class SkillMediumAgility extends Skill {
	
	public SkillMediumAgility(String skillName, List<String> skillDesc, int rechargeTime, Material useMat, SkillActivationMethod skillA, Listener skillListener, CooldownManager cooldownManager) {
		super(skillName, skillDesc, rechargeTime, useMat, skillA, cooldownManager);
		this.setName(AF.boldGold + "Medium Agility");
		this.setDesc(Arrays.asList
				(ChatColor.YELLOW + "The user of this kit recieves",
				 ChatColor.YELLOW + "a " + AF.boldGold + "MEDIUM" +  ChatColor.RESET + ChatColor.YELLOW + " level of agility."));
		this.setUseMat(Material.SUGAR);
		this.setSkillAMethod(SkillActivationMethod.PASSIVE);
		this.setRechargeTime(0);
		this.setCooldownManager(new CooldownManager(getRechargeTime(), Time.SECONDS));
		this.generateUseItem();
	}

	@EventHandler
	public void onSkillUse(PlayerMoveEvent e){
	    Player p = e.getPlayer();
		if (!ArcadeCore.getArcadeManager().getKitManager().getKit(p).getSkills().contains(this)) return;
		p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 3, 2));
	}
	

}