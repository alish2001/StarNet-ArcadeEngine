package com.starnetmc.core.npc;

import net.minecraft.server.v1_8_R3.EntityEnderDragon;
import net.minecraft.server.v1_8_R3.World;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEnderDragon;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.entity.EnderDragon;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class NPCDragon extends EntityEnderDragon {

	public NPCDragon(World world) {
		super(world);
	}

	
	@Override
	public void move(double d0, double d1, double d2) {
	}



	@Override
	public void makeSound(String s, float f, float f1) {
		
		this.world.makeSound(this, s, 0F, 0F);
		
	}

	@Override
	public void g(double d0, double d1, double d2) {
		return;
	}

	public static EnderDragon spawn(Location location) {
		World mcWorld = ((CraftWorld) location.getWorld()).getHandle();
		NPCDragon customEntity = new NPCDragon(mcWorld);
		customEntity.setLocation(location.getBlockX(), location.getBlockY(),
				location.getBlockZ(), location.getYaw(), location.getPitch());
		((CraftLivingEntity) customEntity.getBukkitEntity())
				.setRemoveWhenFarAway(false);
		mcWorld.addEntity(customEntity, CreatureSpawnEvent.SpawnReason.CUSTOM);
		
		return (CraftEnderDragon) customEntity.getBukkitEntity();
	}

	
}
