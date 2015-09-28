package com.starnetmc.core.npc.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Skeleton.SkeletonType;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Zombie;

import com.starnetmc.core.commands.util.CommandBase;
import com.starnetmc.core.events.NPCSpawnEvent;
import com.starnetmc.core.npc.NPCPig;
import com.starnetmc.core.npc.NPCSkeleton;
import com.starnetmc.core.npc.NPCSlime;
import com.starnetmc.core.npc.NPCVillager;
import com.starnetmc.core.npc.NPCZombie;
import com.starnetmc.core.util.F;
import com.starnetmc.core.util.NMS;
import com.starnetmc.core.util.Rank;

public class NPCCommand extends CommandBase<NMS> {

	public NPCCommand(NMS plugin) {
		super(plugin, Rank.DEVELOPER, new String[] { "npc" });
	}

	@Override
	public void execute(Player player, String[] args) {

		String s;
		StringBuilder sb = new StringBuilder("");
		for (int i = 1; i < args.length; i++) {
			sb.append(args[i]).append(" ");
		}
		s = sb.toString();

		Location loc = player.getLocation();
		if (args.length < 2) {
			player.sendMessage(F
					.error("Commands",
							"Invalid Syntax. Please use /npc <skeleton | zombie | zombiebaby | villager | villagerbaby | slime | pig | pigbaby | skeletonwither> <name>"));
			return;
		}

		if (args[0].equalsIgnoreCase("zombie")) {
			Zombie b = NPCZombie.spawn(loc);
			b.setCustomName(ChatColor.GOLD + " " + s);
			b.setCustomNameVisible(true);
			Bukkit.getServer()
					.getPluginManager()
					.callEvent(
							new NPCSpawnEvent(b, s, args[0].toUpperCase(), loc));
		}
		if (args[0].equalsIgnoreCase("skeleton")) {
			Skeleton b = NPCSkeleton.spawn(loc);
			b.setCustomName(ChatColor.GOLD + " " + s);
			b.setCustomNameVisible(true);
			player.sendMessage(F.info("NPC", "NPC Summoned Successfully"));

			Bukkit.getServer()
					.getPluginManager()
					.callEvent(
							new NPCSpawnEvent(b, s, args[0].toUpperCase(), loc));
		}
		if (args[0].equalsIgnoreCase("skeletonwither")) {
			Skeleton b = NPCSkeleton.spawn(loc);
			b.setSkeletonType(SkeletonType.WITHER);
			b.setCustomName(ChatColor.GOLD + " " + s);
			b.setCustomNameVisible(true);
			player.sendMessage(F.info("NPC", "NPC Summoned Successfully"));

			Bukkit.getServer()
					.getPluginManager()
					.callEvent(
							new NPCSpawnEvent(b, s, "SKELETON", loc));
		}
		if (args[0].equalsIgnoreCase("villager")) {
			Villager b = NPCVillager.spawn(loc);
			b.setCustomName(ChatColor.GOLD + " " + s);
			b.setCustomNameVisible(true);
			player.sendMessage(F.info("NPC", "NPC Summoned Successfully"));

			Bukkit.getServer()
					.getPluginManager()
					.callEvent(
							new NPCSpawnEvent(b, s, args[0].toUpperCase(), loc));
		}
		if (args[0].equalsIgnoreCase("zombiebaby")) {

			Zombie b = NPCZombie.spawn(loc);
			b.setBaby(true);
			b.setCustomName(ChatColor.GOLD + " " + s);
			b.setCustomNameVisible(true);
			player.sendMessage(F.info("NPC", "NPC Summoned Successfully"));
			Bukkit.getServer()
					.getPluginManager()
					.callEvent(
							new NPCSpawnEvent(b, s, args[0].toUpperCase(), loc));

		}
		if (args[0].equalsIgnoreCase("villagerbaby")) {

			Villager b = NPCVillager.spawn(loc);
			b.setBaby();
			b.setCustomName(ChatColor.GOLD + " " + s);
			b.setCustomNameVisible(true);
			player.sendMessage(F.info("NPC", "NPC Summoned Successfully"));
			Bukkit.getServer()
					.getPluginManager()
					.callEvent(
							new NPCSpawnEvent(b, s, "VILLAGER", loc));

		}

		if (args[0].equalsIgnoreCase("slime")) {
			Slime b = NPCSlime.spawn(loc);
			b.setCustomName(ChatColor.GOLD + " " + s);
			b.setCustomNameVisible(true);
			player.sendMessage(F.info("NPC", "NPC Summoned Successfully"));
			Bukkit.getServer()
					.getPluginManager()
					.callEvent(
							new NPCSpawnEvent(b, s, args[0].toUpperCase(), loc));
		}
		if (args[0].equalsIgnoreCase("pig")) {
			Pig b = NPCPig.spawn(loc);
			b.setCustomName(ChatColor.GOLD + " " + s);
			b.setCustomNameVisible(true);
			player.sendMessage(F.info("NPC", "NPC Summoned Successfully"));
			Bukkit.getServer()
					.getPluginManager()
					.callEvent(
							new NPCSpawnEvent(b, s, args[0].toUpperCase(), loc));
		}
		if (args[0].equalsIgnoreCase("pigbaby")) {
			Pig b = NPCPig.spawn(loc);
			b.setCustomName(ChatColor.GOLD + " " + s);
			b.setCustomNameVisible(true);
			b.setBaby();
			player.sendMessage(F.info("NPC", "NPC Summoned Successfully"));
			Bukkit.getServer()
					.getPluginManager()
					.callEvent(
							new NPCSpawnEvent(b, s, "PIG", loc));
			return;
		}
	}
}
