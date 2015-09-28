package com.starnetmc.core.gadgets;

import java.util.ArrayList;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.starnetmc.core.commands.GadgetCommand;
import com.starnetmc.core.events.PlayerGadgetChangeEvent;
import com.starnetmc.core.gadgets.itemgadgets.GadgetEggShooter;
import com.starnetmc.core.gadgets.itemgadgets.GadgetFireworks;
import com.starnetmc.core.gadgets.itemgadgets.GadgetShardBomb;
import com.starnetmc.core.gadgets.mounts.DragonMount;
import com.starnetmc.core.modules.manager.Module;
import com.starnetmc.core.modules.manager.ModuleType;
import com.starnetmc.core.util.StarMap;

public class Gadgets extends Module {

	private static ArrayList<Gadget> allGadgets = new ArrayList<Gadget>();
	private static StarMap<String, ArrayList<Gadget>> playergadgets = new StarMap<String, ArrayList<Gadget>>();

	public Gadgets(JavaPlugin plugin) {
		super("Gadgets", 0.75, ModuleType.SERVER, plugin);
	}

	public Gadgets() {
		
	}
	
	public static ArrayList<Gadget> getGadgets(Player player) {
		
		return playergadgets.get(player.getUniqueId().toString());
	}
	
	public static ArrayList<Gadget> getAllGadgets(){
		allGadgets = new ArrayList<Gadget>();
		allGadgets.add(new GadgetFireworks(null, null, null, null, null));
		allGadgets.add(new GadgetEggShooter(null, null, null, null, null));
		allGadgets.add(new GadgetShardBomb(null, null, null, null, null));
		allGadgets.add(new DragonMount(null, null, null, null, null));
		return allGadgets;
	}
	
	private void initAllGadgets(){
		for (Gadget g : getAllGadgets()){
			  g.register();
		}
	}
	
	private void deInitAllGadgets(){
		for (Gadget g : getAllGadgets()){
			  g.unRegister();
		}
	}

	public static void createUserGadget(Player player) {

		ArrayList<Gadget> gadgets = new ArrayList<Gadget>();
		getUsers().put(player.getUniqueId().toString(), gadgets);
	}

	public static StarMap<String, ArrayList<Gadget>> getUsers() {
		
		return playergadgets;
	}

	public static void addGadgetToUser(Player p, Gadget g){
		getUsers().get(p.getUniqueId().toString()).add(g);
		if (g.getType() == GadgetType.ITEM){
		   p.getInventory().addItem(g.getItem());
		}
		
		Bukkit.getServer().getPluginManager().callEvent(new PlayerGadgetChangeEvent(p, g, false));
	}
	
	public static void removeGadgetFromUser(Player p, Gadget g){
		if (!hasGadget(p, g)) return;
		
		boolean indexFound = false;
		int indexLoc = 0;
		int finalIndexLoc = 0;
		
		for (Gadget gdgt : getGadgets(p)){
			if (indexFound) return;
			
			if (!ChatColor.stripColor(gdgt.getName()).equalsIgnoreCase(ChatColor.stripColor(g.getName()))){
				indexLoc++;
			} else {
				finalIndexLoc = indexLoc;
				indexFound = true;
			}
		}
		getGadgets(p).remove(finalIndexLoc);
		
		if (g.getType() == GadgetType.ITEM){
		   p.getInventory().remove(g.getItem());
		}
		
		Bukkit.getServer().getPluginManager().callEvent(new PlayerGadgetChangeEvent(p, g, true));
	}
	
	public static void removeUser(Player player) {
		for (Gadget g : getUsers().get(player.getUniqueId().toString())){
			removeGadgetFromUser(player, g);
		}
		
		getUsers().remove(player.getUniqueId().toString());
	}
	
	public static boolean hasGadget(Player p, Gadget g){
		boolean b = false;
		if (getUsers().get(p.getUniqueId().toString()) != null){
		 for (Gadget gdgt : getUsers().get(p.getUniqueId().toString())){
			if (ChatColor.stripColor(gdgt.getName()).equalsIgnoreCase(ChatColor.stripColor(g.getName()))){
				b = true;
			}
		 }
		}
		
		return b;
	}

	@Override
	public void enable() {
		initAllGadgets();
		isEnabled = true;
		log("Enabled.");

	}

	@Override
	public void addCommands() {
		addCommand(new GadgetCommand(this));
	}
	
	@Override
	public void disable() {
		deInitAllGadgets();
		isEnabled = false;
		log("Disabled.");
	}

	public static boolean isEnabled;

}