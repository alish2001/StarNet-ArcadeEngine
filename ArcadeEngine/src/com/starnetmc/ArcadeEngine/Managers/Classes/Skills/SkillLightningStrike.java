package com.starnetmc.ArcadeEngine.Managers.Classes.Skills;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
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
import com.starnetmc.ArcadeEngine.Utils.USound;
import com.starnetmc.core.util.F;
import com.starnetmc.core.util.ParticleEffect;
import com.starnetmc.core.util.UParticle;
import com.starnetmc.core.util.UPacket;

public class SkillLightningStrike extends Skill {

	public SkillLightningStrike(String skillName, List<String> skillDesc, int rechargeTime, Material useMat, SkillActivationMethod skillA, Listener skillListener, CooldownManager cooldownManager) {
		super(skillName, skillDesc, rechargeTime, useMat, skillA, cooldownManager);
		this.setName(AF.boldAqua + "Lightning Strike");
		this.setDesc(Arrays.asList
				(ChatColor.YELLOW + "Charge your sword with the power",
				 ChatColor.YELLOW + "of Zeus's Lightning to deliver",
				 ChatColor.YELLOW + "a Fatal blow to your opponent!"));
		this.setUseMat(Material.IRON_SWORD);
		this.setSkillAMethod(SkillActivationMethod.RIGHT_CLICK);
		this.setRechargeTime(15);
		this.setCooldownManager(new CooldownManager(getRechargeTime(), Time.SECONDS));
		this.generateUseItem();
	}
	
	@EventHandler
	public void onSkillUse(PlayerInteractEvent e){
			Player p = e.getPlayer();
			if (e.getItem() == null) return;
			if (e.getItem().getType() != getUseMat()) return;
			if (e.getItem().getItemMeta().getDisplayName() != getUseItem().getItemMeta().getDisplayName()) return;
	    	if (!ArcadeCore.getArcadeManager().isState(GameState.INGAME)){
	    		AF.sendSkillInfo(p, this);
	    		return;
	    	}
	    	
			if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK){
			if (!getCooldownManager().isAllowed(p)){
				UPacket.sendActionBarMessage(p, (AF.recharge(getName(), getCooldownManager().getTimeLeft(p, Time.SECONDS) + " Seconds")));
				return;
			}
			
			p.getWorld().strikeLightningEffect(p.getLocation());
			UParticle.ParticleExplosion(ParticleEffect.CLOUD, p.getLocation());
			e.getItem().addEnchantment(Enchantment.DURABILITY, 3);
			USound.PSound(p, Sound.BLAZE_BREATH, 2.5f, 1.25f);
			UPacket.sendActionBarMessage(p, F.boldAqua + "Your sword has been CHARGED!");
			p.sendMessage(AF.skill("Left click to send a Lightning Strike to where you're looking!"));
			rechargeSkill(p);
			return;
			
			} else if (e.getItem().getEnchantments().containsKey(Enchantment.DURABILITY)){
				
				e.getItem().removeEnchantment(Enchantment.DURABILITY);
				Location strikeLoc = p.getTargetBlock((HashSet<Material>) null, 100).getLocation();
				strikeLoc.getWorld().strikeLightning(strikeLoc);
			}
	}
}