package com.starnetmc.ArcadeEngine.Managers.Classes.Skills;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.starnetmc.ArcadeCore;
import com.starnetmc.ArcadeEngine.Managers.GameState;
import com.starnetmc.ArcadeEngine.Managers.Classes.CooldownManager;
import com.starnetmc.ArcadeEngine.Managers.Classes.CooldownManager.Time;
import com.starnetmc.ArcadeEngine.Managers.Classes.Skill;
import com.starnetmc.ArcadeEngine.Managers.Classes.SkillActivationMethod;
import com.starnetmc.ArcadeEngine.Utils.AF;

public class SkillSpleefer extends Skill {
	
	public SkillSpleefer(String skillName, List<String> skillDesc, int rechargeTime, Material useMat, SkillActivationMethod skillA, Listener skillListener, CooldownManager cooldownManager) {
		super(skillName, skillDesc, rechargeTime, useMat, skillA, cooldownManager);
		this.setName(AF.boldWhite + "Spleefer");
		this.setDesc(Arrays.asList
				(ChatColor.YELLOW + "The Skill to Spleef",
				 ChatColor.YELLOW + "a block under one's foot."));
		this.setUseMat(Material.NETHER_STAR);
		this.setSkillAMethod(SkillActivationMethod.LEFT_CLICK);
		this.setRechargeTime(0);
		this.setCooldownManager(new CooldownManager(getRechargeTime(), Time.SECONDS));
		this.generateUseItem();
	}
	
	@EventHandler
	public void onSpleef(PlayerInteractEvent e){
		
		if (e.getAction() != Action.LEFT_CLICK_BLOCK) return;
		if (e.getClickedBlock() == null) return;
		
    	if (!ArcadeCore.getArcadeManager().isState(GameState.INGAME)){
    		return;
    	}
    	
    	e.getClickedBlock().breakNaturally(null);
	}

}