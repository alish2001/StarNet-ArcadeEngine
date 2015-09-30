package com.starnetmc.ArcadeEngine.Games.Pacman.Kits.Pacmans;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import com.starnetmc.ArcadeEngine.Managers.Classes.CooldownManager;
import com.starnetmc.ArcadeEngine.Managers.Classes.CooldownManager.Time;
import com.starnetmc.ArcadeEngine.Managers.Classes.Skill;
import com.starnetmc.ArcadeEngine.Managers.Classes.SkillActivationMethod;
import com.starnetmc.ArcadeEngine.Utils.AF;

public class SkillCookieEating extends Skill {

	public SkillCookieEating(String skillName, List<String> skillDesc, int rechargeTime, Material useMat, SkillActivationMethod skillA, Listener skillListener, CooldownManager cooldownManager) {
		super(skillName, skillDesc, rechargeTime, useMat, skillA, cooldownManager);
		this.setName(AF.boldYellow + "Cookie Eating");
		this.setDesc(Arrays.asList
				(ChatColor.YELLOW + "You're allowed to eat cookies now!",
				 ChatColor.YELLOW + "Ain't that fun?"));
		this.setUseMat(Material.COOKIE);
		this.setSkillAMethod(SkillActivationMethod.PASSIVE);
		this.setRechargeTime(0);
		this.setCooldownManager(new CooldownManager(getRechargeTime(), Time.SECONDS));
		this.generateUseItem();
	}

	@EventHandler
	public void onSkillUse(PlayerInteractEvent e){
		
		//Add Cookie eating when game mechanics are figured out.
	}
	
}