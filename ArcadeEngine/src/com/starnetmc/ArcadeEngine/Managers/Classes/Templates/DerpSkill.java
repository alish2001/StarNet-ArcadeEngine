package com.starnetmc.ArcadeEngine.Managers.Classes.Templates;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.starnetmc.ArcadeEngine.Managers.Classes.CooldownManager;
import com.starnetmc.ArcadeEngine.Managers.Classes.CooldownManager.Time;
import com.starnetmc.ArcadeEngine.Managers.Classes.Skill;
import com.starnetmc.ArcadeEngine.Managers.Classes.SkillActivationMethod;
import com.starnetmc.ArcadeEngine.Utils.AF;
import com.starnetmc.ArcadeEngine.Utils.USound;
import com.starnetmc.core.util.UPacket;

public class DerpSkill extends Skill {

	public DerpSkill(String skillName, List<String> skillDesc, int rechargeTime, Material useMat, SkillActivationMethod skillA, Listener skillListener, CooldownManager cooldownManager) {
		super(skillName, skillDesc, rechargeTime, useMat, skillA, cooldownManager);
		this.setName(AF.boldRed + "Hax");
		this.setDesc(Arrays.asList
				(ChatColor.YELLOW + "This Kit features Kill Aura, Fly,",
				 ChatColor.YELLOW + "Spider HaX and other UberHax"));
		this.setUseMat(Material.APPLE);
		this.setSkillAMethod(SkillActivationMethod.LEFT_CLICK);
		this.setRechargeTime(10);
		this.setCooldownManager(new CooldownManager(getRechargeTime(), Time.SECONDS));
		this.generateUseItem();
	}

	@EventHandler
	public void onSkillUse(PlayerInteractEvent e){
			Player p = e.getPlayer();
			if (e.getAction() != Action.LEFT_CLICK_AIR) return;
			if (e.getItem() == null) return;
			if (e.getItem().getType() != getUseMat()) return;
			if (e.getItem().getItemMeta().getDisplayName() != getUseItem().getItemMeta().getDisplayName()) return;
			if (!getCooldownManager().isAllowed(p)){
				UPacket.sendActionBarMessage(p, (AF.recharge(getName(), getCooldownManager().getTimeLeft(p, Time.SECONDS) + " Seconds")));
				return;
			} else {
				p.setFoodLevel(20);
				USound.PSound(p, Sound.EAT, 2, 1.5F);
				rechargeSkill(p);
				p.sendMessage(AF.error("HaX are not enabled on Star, you sir deserve a cookie."));
			}
	}
	
}