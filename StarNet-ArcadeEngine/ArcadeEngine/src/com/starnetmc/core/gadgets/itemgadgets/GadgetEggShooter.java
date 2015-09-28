package com.starnetmc.core.gadgets.itemgadgets;

import java.util.ArrayList;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;

import com.starnetmc.core.gadgets.Gadget;
import com.starnetmc.core.gadgets.GadgetType;
import com.starnetmc.core.gadgets.Gadgets;
import com.starnetmc.core.util.F;
import com.starnetmc.core.util.USound;

public class GadgetEggShooter extends Gadget {
	
	public GadgetEggShooter(String gadgetName, ArrayList<String> gadgetDiscription, Material gadgetIcon, Material gadgetItem, GadgetType type) {
		super(gadgetName, gadgetDiscription, gadgetIcon, gadgetItem, type);
		
		setName(F.boldWhite + "Egg " + F.boldRed + "Shooter");
		setDesc(new ArrayList<String>());
		addDesc(F.YELLOW + "Mad at someone or just crazy?!");
		addDesc(F.YELLOW + "With this Gadget you can");
		addDesc(F.YELLOW + "Egg people RIGHT in the face!");
		addDesc(F.YELLOW + "To make yourself feel better...");
		
		setIconMat(Material.EGG);
		setItemMat(Material.STICK);
		
		setType(GadgetType.ITEM);
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onEggShoot(PlayerInteractEvent e){
		
		Player player = e.getPlayer();
		
		if (!Gadgets.hasGadget(player, this)) return;
		if (!ChatColor.stripColor(e.getItem().getItemMeta().getDisplayName()).equalsIgnoreCase(ChatColor.stripColor(getName()))) return;
        if (e.getPlayer().getItemInHand().getType() != getItem().getType()) return;
            player.throwEgg();
            USound.PSound(player, Sound.LAVA_POP, 2, 1.75f);
	}

}