package com.starnetmc.core.npc;

import net.minecraft.server.v1_8_R3.EntityVillager;
import net.minecraft.server.v1_8_R3.World;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftVillager;
import org.bukkit.entity.Villager;
import org.bukkit.event.entity.CreatureSpawnEvent;


public class NPCVillager extends EntityVillager {

	public NPCVillager(World world) {
		super(world);
		// TODO Auto-generated constructor stub
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

	public static Villager spawn(Location location) {
		World mcWorld = ((CraftWorld) location.getWorld()).getHandle();
		NPCVillager customEntity = new NPCVillager(mcWorld);
		customEntity.setLocation(location.getX(), location.getY(),
				location.getZ(), location.getYaw(), location.getPitch());
		((CraftLivingEntity) customEntity.getBukkitEntity())
				.setRemoveWhenFarAway(false);
		mcWorld.addEntity(customEntity, CreatureSpawnEvent.SpawnReason.CUSTOM);
		return (CraftVillager) customEntity.getBukkitEntity();
	}
}