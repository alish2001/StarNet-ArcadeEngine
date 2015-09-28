package com.starnetmc;

import java.io.File;

import net.minecraft.server.v1_8_R3.EntityEnderDragon;
import net.minecraft.server.v1_8_R3.EntityPig;
import net.minecraft.server.v1_8_R3.EntitySkeleton;
import net.minecraft.server.v1_8_R3.EntitySlime;
import net.minecraft.server.v1_8_R3.EntityVillager;
import net.minecraft.server.v1_8_R3.EntityZombie;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.starnetmc.ArcadeEngine.ArcadeManager;
import com.starnetmc.ArcadeEngine.Events.MapUnLoadEvent;
import com.starnetmc.ArcadeEngine.Managers.Commands.ArcadeCMDS;
import com.starnetmc.ArcadeEngine.Utils.Logger;
import com.starnetmc.core.accounts.AccountManager;
import com.starnetmc.core.commands.Time;
import com.starnetmc.core.commands.util.CommandCenter;
import com.starnetmc.core.database.DatabaseManager;
import com.starnetmc.core.gadgets.GUI.GadgetsUI;
import com.starnetmc.core.gamemode.Gamemode;
import com.starnetmc.core.gamemode.GamemodeUI;
import com.starnetmc.core.listeners.BlockCommands;
import com.starnetmc.core.listeners.Join;
import com.starnetmc.core.listeners.Quit;
import com.starnetmc.core.modules.ArcadeBorder;
import com.starnetmc.core.modules.Chat;
import com.starnetmc.core.modules.ChatFilter;
import com.starnetmc.core.modules.News;
import com.starnetmc.core.modules.Teleport;
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

public class ArcadeCore extends JavaPlugin {
	
	/**
	 * 
	 * @author alish2001
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
	
	private static ArcadeCore arcade;
	private static ArcadeManager arcadeManager;
	
	private static File cfile;
	private static FileConfiguration config;
	
	@Override
	public void onEnable(){
		
		arcade = this;
		
		config = getConfig();
		getConfig().options().copyDefaults(true);
		saveConfig();
        cfile = new File(getDataFolder(), "config.yml");
        
		registerCore();
		arcadeManager = new ArcadeManager();
		arcadeManager.init();
		
		new ArcadeCMDS(this);
		Logger.log("Arcade Enabled.");
	}
	
	public void onDisable(){
		getArcadeManager().getKitManager().resetKits(true);
	    World mapWorld = ArcadeCore.getArcadeManager().getMapper().getActiveMap().getCenter().getWorld();
	    Bukkit.getServer().getPluginManager().callEvent(new MapUnLoadEvent(mapWorld));
		getArcadeManager().getPropertiesManager().unLoadPropertiesManagers();
		Logger.log("Arcade Disabled.");
	}
	
	/*private void debug_init(){
		CommandCenter.init(this);
		
		new DatabaseManager(this);
		new AccountManager(this);
		new Chat(this);
		new NMS(this);
		new MemoryFix(this);
		new Updater(this);
		Bukkit.getServer().getPluginManager().registerEvents(new Join(), this);
	}*/
	
	private void registerCore(){
		getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		
		CommandCenter.init(this);

		// Modules
		new DatabaseManager(this);
		new AccountManager(this);
		new Chat(this);
		new ChatFilter(this);
		new NMS(this);
		new News(this);
		new Gamemode(this);
		new Teleport(this);
		new Punish(this);
		new ArcadeBorder(this);

		// Registering NPCs
		NMS.registerEntity("Zombie", 54, EntityZombie.class, NPCZombie.class);
		NMS.registerEntity("Skeleton", 51, EntitySkeleton.class, NPCSkeleton.class);
		NMS.registerEntity("Villager", 120, EntityVillager.class, NPCVillager.class);
		NMS.registerEntity("Slime", 55, EntitySlime.class, NPCSlime.class);
		NMS.registerEntity("Pig", 90, EntityPig.class, NPCPig.class);
		NMS.registerEntity("EnderDragon", 63, EntityEnderDragon.class, NPCDragon.class);
		
		//Registering Listeners that are not modules
		Bukkit.getServer().getPluginManager().registerEvents(new Join(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new Quit(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new BlockCommands(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new GadgetsUI(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new GamemodeUI(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new Time(), this);
		
		new MemoryFix(this);
		new Updater(this);
		getLogger().info(F.info("Core", "Enabled"));
	}
	
	public static ArcadeManager getArcadeManager(){
		return arcadeManager;
	}
	
	public static ArcadeCore getPlugin(){
		return arcade;
	}
	
	public static FileConfiguration getConfigFile() {
		return config;
	}
	
    public static void saveConfigFile() {
        try {
                config.save(cfile);
        }
        catch (Exception e) { e.printStackTrace(); }
        }
}
