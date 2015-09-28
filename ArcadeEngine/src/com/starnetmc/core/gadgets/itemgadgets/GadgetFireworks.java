package com.starnetmc.core.gadgets.itemgadgets;

import java.util.ArrayList;
import java.util.Random;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Color;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;

import com.starnetmc.core.gadgets.Gadget;
import com.starnetmc.core.gadgets.GadgetType;
import com.starnetmc.core.gadgets.Gadgets;
import com.starnetmc.core.util.F;
import com.starnetmc.core.util.UFireWork;

public class GadgetFireworks extends Gadget {

	public GadgetFireworks(String gadgetName, ArrayList<String> gadgetDiscription, Material gadgetIcon, Material gadgetItem, GadgetType type) {
		super(gadgetName, gadgetDiscription, gadgetIcon, gadgetItem, type);
		
		setName(F.boldWhite + "Fireworks");
		setDesc(new ArrayList<String>());
		addDesc(F.YELLOW + "Feel like Celebrating!?");
		addDesc(F.YELLOW + "Then this Gadget is the");
		addDesc(F.YELLOW + "One for you!");
		
		setIconMat(Material.FIREWORK);
		setItemMat(Material.FIREWORK);
		
		setType(GadgetType.ITEM);
	}
	
	@EventHandler
	public void onFireworkUse(PlayerInteractEvent e) {
		
		Player player = e.getPlayer();
		
		if (!Gadgets.hasGadget(player, this)) return;
		if (!ChatColor.stripColor(e.getItem().getItemMeta().getDisplayName()).equalsIgnoreCase(ChatColor.stripColor(getName()))) return;
        if (e.getPlayer().getItemInHand().getType() != getItem().getType()) return;
				e.setCancelled(true);

				Random r = new Random();

				int rt = r.nextInt(5) + 1;
				Type type = Type.BALL;
				if (rt == 1) {
					type = Type.BALL;
				}
				if (rt == 2) {
					type = Type.BALL_LARGE;
				}
				if (rt == 3) {
					type = Type.BURST;
				}
				if (rt == 4) {
					type = Type.STAR;
				}
				if (rt == 5) {
					type = Type.CREEPER;
				}

				int r1i = r.nextInt(5) + 1;
				Color color = Color.RED;

				if (r1i == 1) {
					color = Color.AQUA;
				}
				if (r1i == 2) {
					color = Color.FUCHSIA;
				}
				if (r1i == 3) {
					color = Color.YELLOW;
				}
				if (r1i == 4) {
					color = Color.GREEN;
				}
				if (r1i == 5) {
					color = Color.RED;
				}
				
				UFireWork.createFireWork(player.getLocation(), true, true, type, color, Color.WHITE, 1);
			}
}