package com.starnetmc.core;

import net.minecraft.server.v1_8_R3.EntityEnderDragon;
import net.minecraft.server.v1_8_R3.EntityPig;
import net.minecraft.server.v1_8_R3.EntitySkeleton;
import net.minecraft.server.v1_8_R3.EntitySlime;
import net.minecraft.server.v1_8_R3.EntityVillager;
import net.minecraft.server.v1_8_R3.EntityZombie;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.starnetmc.core.accounts.AccountManager;
import com.starnetmc.core.commands.Time;
import com.starnetmc.core.commands.util.CommandCenter;
import com.starnetmc.core.communicator.Communicator;
import com.starnetmc.core.database.DatabaseManager;
import com.starnetmc.core.database.Databaser;
import com.starnetmc.core.gadgets.Gadgets;
import com.starnetmc.core.gadgets.GUI.GadgetsUI;
import com.starnetmc.core.gamemode.Gamemode;
import com.starnetmc.core.gamemode.GamemodeUI;
import com.starnetmc.core.listeners.Block;
import com.starnetmc.core.listeners.BlockCommands;
import com.starnetmc.core.listeners.Damage;
import com.starnetmc.core.listeners.GenericListener;
import com.starnetmc.core.listeners.Join;
import com.starnetmc.core.listeners.Quit;
import com.starnetmc.core.listeners.Weather;
import com.starnetmc.core.modules.Border;
import com.starnetmc.core.modules.Chat;
import com.starnetmc.core.modules.ChatFilter;
import com.starnetmc.core.modules.DoubleJump;
import com.starnetmc.core.modules.HubInventory;
import com.starnetmc.core.modules.LScoreboard;
import com.starnetmc.core.modules.News;
import com.starnetmc.core.modules.Portal;
import com.starnetmc.core.modules.Settings;
import com.starnetmc.core.modules.Teleport;
import com.starnetmc.core.modules.Tutorial;
import com.starnetmc.core.npc.NPCDragon;
import com.starnetmc.core.npc.NPCPig;
import com.starnetmc.core.npc.NPCSkeleton;
import com.starnetmc.core.npc.NPCSlime;
import com.starnetmc.core.npc.NPCVillager;
import com.starnetmc.core.npc.NPCZombie;
import com.starnetmc.core.punish.Punish;
import com.starnetmc.core.util.F;
import com.starnetmc.core.util.MemoryFix;
import com.starnetmc.core.util.NMS;
import com.starnetmc.core.util.Updater;


/**
 * 
 * @author SparkWings Maintained by: alish2001
 *         <p>
 *         <b>This plugin is intended for use by The Star Network ONLY.</b>
 *         </p>
 *         <p>
 *         <b>Unauthorized use or access to this plugin will result in immediate
 *         legal action.</b>
 *         </p>
 *         <p>
 *         Copyright 2015 ©StarNetwork.LTD
 *         </p>
 *
 */

public class Main extends JavaPlugin {

	// Util
	private static Main plugin;
	public Databaser mysql = new Databaser(this);

	@Override
	public void onEnable() {

		plugin = this;
		Bukkit.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

		CommandCenter.init(this);

		// Modules
		new DatabaseManager(this);
		new Communicator(this);
		new AccountManager(this);
		new Chat(this);
		new Tutorial(this);
		new ChatFilter(this);
		new NMS(this);
		new Settings(this);
		new Gadgets(this);
		new Border(this);
		new LScoreboard(this);
		new News(this);
		new DoubleJump(this);
		new HubInventory(this);
		new Gamemode(this);
		new Teleport(this);
		new Portal(this);
		new Punish(this);

		// Registering NPCs
		NMS.registerEntity("Zombie", 54, EntityZombie.class, NPCZombie.class);
		NMS.registerEntity("Skeleton", 51, EntitySkeleton.class, NPCSkeleton.class);
		NMS.registerEntity("Villager", 120, EntityVillager.class, NPCVillager.class);
		NMS.registerEntity("Slime", 55, EntitySlime.class, NPCSlime.class);
		NMS.registerEntity("Pig", 90, EntityPig.class, NPCPig.class);
		NMS.registerEntity("EnderDragon", 63, EntityEnderDragon.class, NPCDragon.class);

		// Other
		getConfig().options().copyDefaults(true);
		registerListeners();
		new MemoryFix(this);
		new Updater(this);
		getLogger().info(F.info("Plugin", "All modules enabled."));
	}

	@Override
	public void onDisable() {

		getLogger().info(F.info("Plugin", "All modules disabled, Plugin shutting down."));

	}

	// registers listeners which are NOT modules
	private void registerListeners() {
		Bukkit.getServer().getPluginManager().registerEvents(new Join(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new Quit(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new Block(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new BlockCommands(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new GadgetsUI(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new GamemodeUI(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new Weather(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new Time(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new Damage(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new GenericListener(), this);
	}

	public static Main getPlugin() {
		return Main.plugin;
	}


}