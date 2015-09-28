package com.starnetmc.core.gadgets.mounts;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.util.Vector;

import com.starnetmc.core.events.PlayerGadgetChangeEvent;
import com.starnetmc.core.gadgets.Gadget;
import com.starnetmc.core.gadgets.GadgetType;
import com.starnetmc.core.gadgets.Gadgets;
import com.starnetmc.core.util.F;
import com.starnetmc.core.util.USound;
import com.starnetmc.core.util.UtilPacket;

public class DragonMount extends Gadget {
	
	public DragonMount(String gadgetName, ArrayList<String> gadgetDiscription, Material gadgetIcon, Material gadgetItem, GadgetType type) {
		super(gadgetName, gadgetDiscription, gadgetIcon, gadgetItem, type);
		
		setName(F.boldPurple + "Dragon Mount");
		setDesc(new ArrayList<String>());
		addDesc(F.YELLOW + "Fly around in this magestic");
		addDesc(F.YELLOW + "Dragon from the mysterious");
		addDesc(F.YELLOW + "World of The end.");
		
		setIconMat(Material.DRAGON_EGG);
		setItemMat(Material.DRAGON_EGG);
		
		setType(GadgetType.MOUNT);
	}
	
	@EventHandler
	public void onGadgetStart(PlayerGadgetChangeEvent e){
		Player p = e.getPlayer();
    	if (!Gadgets.hasGadget(p, this)) return;
		if (e.isRemoved()) return;
		
		EnderDragon dragon = (EnderDragon) p.getWorld().spawn(p.getLocation(), EnderDragon.class);
		dragon.setCustomName(p.getName() + " - Dragon");
		dragon.setCustomNameVisible(true);
		dragon.setPassenger(p);
	}
	
	@EventHandler
	public void onGadgetStop(PlayerGadgetChangeEvent e){
		Player p = e.getPlayer();
		if (!ChatColor.stripColor(e.getGadget().getName()).equalsIgnoreCase(ChatColor.stripColor(getName()))) return;
		if (!e.isRemoved()) return;
        if (p.getVehicle() == null) return;
    	if (!(p.getVehicle() instanceof EnderDragon)) return;
        
        p.getVehicle().remove();
	}
	
	@EventHandler
	public void onDragonExit(PlayerToggleSneakEvent e){
		Player p = e.getPlayer();
    	if (!Gadgets.hasGadget(p, this)) return;
		
		for (Entity ent : p.getWorld().getEntities()){
			if (ent.getType() != EntityType.ENDER_DRAGON) continue;
			if (ent.getCustomName() == null) continue;
			if (!ent.getCustomName().contains("-")) continue;
			String[] dragonData = ent.getCustomName().split("-");
			String dragonOwner = dragonData[0].trim();
			
			if (!dragonOwner.equalsIgnoreCase(p.getName())) continue;
			ent.remove();
		}
    	
		UtilPacket.sendActionBarMessage(p, F.boldRed + "Un-Mounted " + getName() + F.boldGreen + "!");
		USound.PSound(p, Sound.NOTE_PLING, -0.75f, 1);
    	Gadgets.removeGadgetFromUser(p, this);
	}
	
    @EventHandler
    public void PlayerMoveEvent(PlayerMoveEvent e){
    	Player p = e.getPlayer();
    	if (!Gadgets.hasGadget(p, this)) return;
        if (p.getVehicle() == null) return;
        
        Entity ent = p.getVehicle();
        if (!ent.getType().equals(EntityType.ENDER_DRAGON)) return;
        
        Vector vec = p.getLocation().getDirection();
        ent.setVelocity(vec);
        ((CraftEntity) ent).getHandle().setPositionRotation(ent.getLocation().getX(), ent.getLocation().getY(), ent.getLocation().getZ(), p.getLocation().getYaw() - 180, p.getLocation().getPitch());
    }

}