package com.starnetmc.core.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.server.v1_8_R3.BiomeBase;
import net.minecraft.server.v1_8_R3.BiomeBase.BiomeMeta;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.EntityTypes;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.starnetmc.core.accounts.AccountManager;
import com.starnetmc.core.database.Databaser;
import com.starnetmc.core.events.NPCRemoveEvent;
import com.starnetmc.core.events.NPCSpawnEvent;
import com.starnetmc.core.modules.Tutorial;
import com.starnetmc.core.modules.manager.Module;
import com.starnetmc.core.modules.manager.ModuleType;
import com.starnetmc.core.npc.NPCPig;
import com.starnetmc.core.npc.NPCSkeleton;
import com.starnetmc.core.npc.NPCSlime;
import com.starnetmc.core.npc.NPCVillager;
import com.starnetmc.core.npc.NPCZombie;
import com.starnetmc.core.npc.command.NPCCommand;

public class NMS extends Module {

	public static LinkedHashMap<String, Location> villagernpcs = new LinkedHashMap<String, Location>();
	public static LinkedHashMap<String, Location> pignpcs = new LinkedHashMap<String, Location>();
	public static LinkedHashMap<String, Location> skeletonnpcs = new LinkedHashMap<String, Location>();
	public static LinkedHashMap<String, Location> slimenpcs = new LinkedHashMap<String, Location>();
	public static LinkedHashMap<String, Location> zombienpcs = new LinkedHashMap<String, Location>();
	
	public static List<String> npcs = new ArrayList<String>();

	public NMS(JavaPlugin plugin) {

		super("NPC Manager", 2.0, ModuleType.SERVER, plugin);
	}

	public static void registerEntity(String name, int id, Class<?> class1,
			Class<? extends EntityInsentient> customClass) {
		try {
			List<Map<?, ?>> dataMaps = new ArrayList<Map<?, ?>>();
			for (Field f : EntityTypes.class.getDeclaredFields()) {
				if (f.getType().getSimpleName()
						.equals(Map.class.getSimpleName())) {
					f.setAccessible(true);
					dataMaps.add((Map<?, ?>) f.get(null));
				}
			}
			if (dataMaps.get(2).containsKey(Integer.valueOf(id))) {
				dataMaps.get(0).remove(name);
				dataMaps.get(2).remove(Integer.valueOf(id));
			}
			Method method = EntityTypes.class.getDeclaredMethod("a",
					new Class[] { Class.class, String.class, Integer.TYPE });
			method.setAccessible(true);
			method.invoke(null,
					new Object[] { customClass, name, Integer.valueOf(id) });
			for (Field f : BiomeBase.class.getDeclaredFields()) {
				if ((f.getType().getSimpleName().equals(BiomeBase.class
						.getSimpleName())) && (f.get(null) != null)) {
					for (Field list : BiomeBase.class.getDeclaredFields()) {
						if (list.getType().getSimpleName()
								.equals(List.class.getSimpleName())) {
							list.setAccessible(true);

							@SuppressWarnings("unchecked")
							List<BiomeMeta> metaList = (List<BiomeMeta>) list
									.get(f.get(null));
							for (BiomeMeta meta : metaList) {
								Field clazz = BiomeMeta.class
										.getDeclaredFields()[0];
								if (clazz.get(meta).equals(class1)) {
									clazz.set(meta, customClass);
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addCommands() {
		addCommand(new NPCCommand(this));
	}

	@Override
	public void enable() {
		try {
			villagernpcs = Databaser.downloadVillagerNPCs();
			pignpcs = Databaser.downloadPigNPCs();
			skeletonnpcs = Databaser.downloadSkeletonNPCs();
			slimenpcs = Databaser.downloadSlimeNPCs();
			zombienpcs = Databaser.downloadZombieNPCs();
		} catch (Exception e) {
			e.printStackTrace();
		}
		

		if (villagernpcs != null) {
			spawnVillagers();
		}
		if (pignpcs != null) {
			spawnPigs();
		}
		if (skeletonnpcs != null) {
			spawnSkeletons();
		}
		if (slimenpcs != null) {
			spawnSlimes();
		}
		if (zombienpcs != null) {
			spawnZombies();
		}

		log("Enabled.");

	}

	@Override
	public void disable() {
		log("Disabled");

	}

	@EventHandler(priority = EventPriority.LOW)
	public void onRemoval(PlayerInteractEntityEvent e) throws Exception {

		e.setCancelled(true);
		Player player = e.getPlayer();
		Entity en = e.getRightClicked();

		if (player.getItemInHand().getType() == Material.BLAZE_ROD) {

			if (AccountManager.getAccount(player).getRank().equals(
					Rank.OWNER)) {

				if (en instanceof Player) {
					return;
				} else {
					en.remove();
					player.sendMessage(F
							.info("NPC", "NPC Removed Successfully"));
				}
				Bukkit.getServer()
						.getPluginManager()
						.callEvent(
								new NPCRemoveEvent((LivingEntity) en, en
										.getCustomName(), en.getLocation()));

			} else {
				return;
			}

		} else {
			return;
		}

	}

	@EventHandler
	public void onRemove(NPCRemoveEvent e) throws Exception {

		Databaser.deleteNPC(e.getName());

	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onInteract(PlayerInteractEntityEvent e) throws Exception {

		Player player = e.getPlayer();
		Entity en = e.getRightClicked();

		if (en instanceof Villager) {

			if (en.getCustomName().equalsIgnoreCase(
					F.GOLD + " Welcome Tutorial (Shard Reward) ")) {
				Tutorial.sendTutorial(player);

			}
			if (en.getCustomName().equalsIgnoreCase(
					F.GOLD + " Minigame Arcade (COMING SOON) ")) {
				player.sendMessage(F.GOLD
						+ F.STRIKE
						+ "-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
				player.sendMessage("   \n");
				player.sendMessage("   \n");
				player.sendMessage(F.boldAqua + "LOVE Minigames? We do too!");
				player.sendMessage(F.boldAqua
						+ "Coming soon, we'll have a minigame arcade!");
				player.sendMessage(F.boldGold + "HOPE TO SEE YOU THERE! :)");
				player.sendMessage(F.RED + "- Ali");
				player.sendMessage("   \n");
				player.sendMessage("   \n");
				player.sendMessage(F.GOLD
						+ F.STRIKE
						+ "-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");

			}
			if (en.getCustomName().equalsIgnoreCase(
					F.GOLD + " Hub Parkour (COMING SOON) ")) {
				player.sendMessage(F.GOLD
						+ F.STRIKE
						+ "-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
				player.sendMessage("   \n");
				player.sendMessage("   \n");
				player.sendMessage("   \n");
				player.sendMessage(F.boldRed + "COMING SOON!");
				player.sendMessage(F.AQUA + "- Spark");
				player.sendMessage("   \n");
				player.sendMessage("   \n");
				player.sendMessage("   \n");
				player.sendMessage(F.GOLD
						+ F.STRIKE
						+ "-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");

			}

		} else {
			return;
		}

	}

	@EventHandler
	public void onHitInteract(EntityDamageByEntityEvent e) throws Exception {

		if (e.getDamager() instanceof Player) {

			Player player = (Player) e.getDamager();
			Entity en = e.getEntity();

			if (en instanceof Villager) {

				if (en.getCustomName().equalsIgnoreCase(
						F.GOLD + " Welcome Tutorial (Shard Reward) ")) {
					Tutorial.sendTutorial(player);

				}
				if (en.getCustomName().equalsIgnoreCase(
						F.GOLD + " Minigame Arcade (COMING SOON) ")) {
					player.sendMessage(F.GOLD
							+ F.STRIKE
							+ "-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
					player.sendMessage("   \n");
					player.sendMessage("   \n");
					player.sendMessage(F.boldAqua
							+ "LOVE Minigames? We do too!");
					player.sendMessage(F.boldAqua
							+ "Coming soon, we'll have a minigame arcade!");
					player.sendMessage(F.boldGold + "HOPE TO SEE YOU THERE! :)");
					player.sendMessage(F.RED + "- Ali");
					player.sendMessage("   \n");
					player.sendMessage("   \n");
					player.sendMessage(F.GOLD
							+ F.STRIKE
							+ "-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");

				}
				if (en.getCustomName().equalsIgnoreCase(
						F.GOLD + " Hub Parkour (COMING SOON) ")) {
					player.sendMessage(F.GOLD
							+ F.STRIKE
							+ "-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
					player.sendMessage("   \n");
					player.sendMessage("   \n");
					player.sendMessage("   \n");
					player.sendMessage(F.boldRed + "COMING SOON!");
					player.sendMessage(F.AQUA + "- Spark");
					player.sendMessage("   \n");
					player.sendMessage("   \n");
					player.sendMessage("   \n");
					player.sendMessage(F.GOLD
							+ F.STRIKE
							+ "-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");

				}

			}

		} else
			return;

	}

	@EventHandler(priority = EventPriority.LOW)
	public void onSpawn(NPCSpawnEvent e) throws Exception {

		Databaser.createNPC(e.getName(), e.getType(), e.getLocation());

	}

	public static void spawnVillagers() {

		for (String s : villagernpcs.keySet()) {

			npcs.add(s);
			
			Villager b = NPCVillager.spawn(villagernpcs.get(s));
			b.setCustomName(ChatColor.GOLD + " " + s);
			b.setCustomNameVisible(true);
		}

	}

	public static void spawnPigs() {

		for (String s : pignpcs.keySet()) {
			npcs.add(s);
			Pig b = NPCPig.spawn(pignpcs.get(s));
			b.setCustomName(ChatColor.GOLD + " " + s);
			b.setCustomNameVisible(true);
		}

	}

	public static void spawnSkeletons() {

		for (String s : skeletonnpcs.keySet()) {
			npcs.add(s);
			Skeleton b = NPCSkeleton.spawn(skeletonnpcs.get(s));
			b.setCustomName(ChatColor.GOLD + " " + s);
			b.setCustomNameVisible(true);
		}

	}

	public static void spawnSlimes() {

		for (String s : slimenpcs.keySet()) {
			npcs.add(s);
			Slime b = NPCSlime.spawn(slimenpcs.get(s));
			b.setCustomName(ChatColor.GOLD + " " + s);
			b.setCustomNameVisible(true);
		}

	}

	public static void spawnZombies() {

		for (String s : zombienpcs.keySet()) {
			npcs.add(s);
			Zombie b = NPCZombie.spawn(zombienpcs.get(s));
			b.setCustomName(ChatColor.GOLD + " " + s);
			b.setCustomNameVisible(true);
		}

	}

}
