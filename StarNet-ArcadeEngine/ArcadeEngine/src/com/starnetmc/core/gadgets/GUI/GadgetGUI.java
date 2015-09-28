package com.starnetmc.core.gadgets.GUI;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.starnetmc.core.accounts.AccountManager;
import com.starnetmc.core.gadgets.Gadget;
import com.starnetmc.core.gadgets.GadgetType;
import com.starnetmc.core.gadgets.Gadgets;
import com.starnetmc.core.util.F;
import com.starnetmc.core.util.ItemFactory;
import com.starnetmc.core.util.Rank;
import com.starnetmc.core.util.USound;
import com.starnetmc.core.util.UtilPacket;

public class GadgetGUI {
	
	public static void openGadgetGUI(Player p){
		
		Inventory gui = Bukkit.createInventory(null, 54, F.boldRed + "Gadgets");
		
		ItemStack info;
		List<String> desc;
		
		if (Rank.getStaffRanks().contains(AccountManager.getAccount(p).getRank())){
			
			if (AccountManager.getAccount(p).getRank() == Rank.OWNER || AccountManager.getAccount(p).getRank() == Rank.ADMIN || AccountManager.getAccount(p).getRank() == Rank.DEVELOPER){
				desc = Arrays.asList(" ",
						F.boldYellow + "Rank " + F.boldAqua + "> " + AccountManager.getAccount(p).getRank().getTag(false),
						F.boldPurple + "Shards " + F.boldAqua + "> " + F.boldYellow + AccountManager.getAccount(p).getShards(),
						F.boldYellow + " ",
						F.boldYellow + "Ayy Welcome Boss",
						F.boldYellow + "How you doin' today?");
			} else {
			
			desc = Arrays.asList(" ",
					F.boldYellow + "Rank " + F.boldAqua + "> " + AccountManager.getAccount(p).getRank().getTag(false),
					F.boldPurple + "Shards " + F.boldAqua + "> " + F.boldYellow + AccountManager.getAccount(p).getShards(),
					F.boldYellow + " ",
					F.boldYellow + "Thank you for all of your hardwork :)",
					F.boldYellow + "We really apperichiate having you on the Star Team!",
					F.boldYellow + "-LeaderShip");
			}
			
			info = ItemFactory.createItem(Material.NETHER_STAR, F.boldYellow + "Staff Info | " + p.getName(), desc, true);
			
			}
		
		if (AccountManager.getAccount(p).getRank() == Rank.VIP || AccountManager.getAccount(p).getRank() == Rank.MVP){
		desc = Arrays.asList(" ",
				F.boldYellow + "Rank " + F.boldAqua + "> " + AccountManager.getAccount(p).getRank().getTag(false),
				F.boldPurple + "Shards " + F.boldAqua + "> " + F.boldYellow + AccountManager.getAccount(p).getShards(),
				F.boldYellow + " ",
				F.boldYellow + "Thank you for your kind support :)",
				F.boldYellow + "Enjoy the features you have either unlocked",
				F.boldYellow + "or purchased! <3 to the da REAL MVP's",
				F.boldYellow + "and to da SWIG VIP's <3");
		info = ItemFactory.createItem(Material.NETHER_STAR, F.boldYellow + "Player Info | " + p.getName(), desc, true);
		
		} else {
			
			desc = Arrays.asList(" ",
					F.boldYellow + "Rank " + F.boldAqua + "> " + AccountManager.getAccount(p).getRank().getTag(false),
					F.boldPurple + "Shards " + F.boldAqua + "> " + F.boldYellow + AccountManager.getAccount(p).getShards(),
					F.boldYellow + " ",
					F.boldYellow + "Purchase a Premium Rank",
					F.boldYellow + "@ www.StarNetMC.com/Store",
					F.boldYellow + "or Earn yourself the MVP Rank!",
					F.boldYellow + "do /MVP for more info!");
			
			info = ItemFactory.createItem(Material.NETHER_STAR, F.boldYellow + "Player Info | " + p.getName(), desc, true);
		}
	    
	    ItemStack gadgets = ItemFactory.createItem(Material.BLAZE_ROD, F.boldYellow + "Item Gadgets", null, false);
	    ItemStack morphs = ItemFactory.createItem(Material.DRAGON_EGG, F.boldPurple + "Morphs", null, false);
	    ItemStack mounts = ItemFactory.createItem(Material.SADDLE, F.boldGreen + "Mounts", null, false);
	    ItemStack particles = ItemFactory.createItem(Material.BLAZE_POWDER, F.boldAqua + "Particles", null, false);
	    
	    gui.setItem(13, info);
	    gui.setItem(28, mounts);
	    gui.setItem(30, particles);
	    gui.setItem(32, gadgets);
	    gui.setItem(34, morphs);
		
		p.openInventory(gui);
		USound.PSound(p, Sound.ORB_PICKUP, 7F, 1F);
		UtilPacket.sendActionBarMessage(p, F.boldGreen + "Gadgets GUI opened!");
	}

	public static void openItemGadgetGUI(Player p) {
		
		Inventory gui = Bukkit.createInventory(null, 54, F.boldRed + "Gadgets" + F.boldAqua + " | " + F.boldYellow + "Items");
		
		int descPos = 13;
		int centerPosition = 31;
		int position = centerPosition;
		
		List<String> desc = Arrays.asList(" ",
				F.boldYellow + "These nify little Gadgets",
				F.boldYellow + "are called 'Item' Gadgets.",
				F.boldYellow + "As the name suggests they work",
				F.boldYellow + "with items. The only 'REAL' Gadgets.",
				F.boldYellow + "in here.");
		
		ItemStack descriptor = ItemFactory.createItem(Material.BLAZE_ROD, F.boldYellow + "Item Gadgets", desc, true);
	    ItemStack back = ItemFactory.createItem(Material.ARROW, F.boldYellow + "Back >", null, false);
	    gui.setItem(0, back);
		gui.setItem(descPos, descriptor);
		
		for (Gadget g : Gadgets.getAllGadgets()){
			if (g.getType() == GadgetType.ITEM){
				
				if (position == centerPosition){
					setGadget(p, g, position, gui);
					position++;
					continue;
				}
			    
				if (position > centerPosition){
					
					for (int i = position; gui.getItem(i) != null && gui.getItem(i).getType() != Material.AIR; i--){
						position--;
					}
					
					setGadget(p, g, position, gui);
					continue;
				}
					
				if (position < centerPosition){
					
					for (int i = position; gui.getItem(i) != null && gui.getItem(i).getType() != Material.AIR; i--){
						position++;
					}
					
					setGadget(p, g, position, gui);
					continue;
				}

			}
			
		}
		
		p.openInventory(gui);
		UtilPacket.sendActionBarMessage(p, F.boldGreen + "Item Gadgets GUI opened!");
	}
	
	public static void openMorphGUI(Player p) {
		
		Inventory gui = Bukkit.createInventory(null, 54, F.boldRed + "Gadgets" + F.boldAqua + " | " + F.boldPurple + "Morphs");
		
		int descPos = 13;
		int centerPosition = 31;
		int position = centerPosition;
		
		List<String> desc = Arrays.asList(" ",
				F.boldYellow + "Have you ever gotten bored",
				F.boldYellow + "from your own look?",
				F.boldYellow + "Then these Gadgets are the ones for you!",
				F.boldYellow + "You can change how you",
				F.boldYellow + " look like, at anytime, anyday.");
		
		ItemStack descriptor = ItemFactory.createItem(Material.DRAGON_EGG, F.boldPurple + "Morphs", desc, true);
	    ItemStack back = ItemFactory.createItem(Material.ARROW, F.boldYellow + "Back >", null, false);
	    gui.setItem(0, back);
		gui.setItem(descPos, descriptor);
		
		for (Gadget g : Gadgets.getAllGadgets()){
			if (g.getType() == GadgetType.MORPH){
				
				if (position == centerPosition){
					setGadget(p, g, position, gui);
					position++;
					continue;
				}
			    
				if (position > centerPosition){
					
					for (int i = position; gui.getItem(i) != null && gui.getItem(i).getType() != Material.AIR; i--){
						position--;
					}
					
					setGadget(p, g, position, gui);
					continue;
				}
					
				if (position < centerPosition){
					
					for (int i = position; gui.getItem(i) != null && gui.getItem(i).getType() != Material.AIR; i--){
						position++;
					}
					
					setGadget(p, g, position, gui);
					continue;
				}

			}
			
		}
		
		p.openInventory(gui);
		UtilPacket.sendActionBarMessage(p, F.boldGreen + "Morphs GUI opened!");
	}
	
	public static void openMountGUI(Player p) {
		
		Inventory gui = Bukkit.createInventory(null, 54, F.boldRed + "Gadgets" + F.boldAqua + " | " + F.boldGreen + "Mounts");
		
		int descPos = 13;
		int centerPosition = 31;
		int position = centerPosition;
		
		List<String> desc = Arrays.asList(" ",
				F.boldYellow + "Ever wanted to ride a horse, a cart",
				F.boldYellow + "a pig, or even a chicken?",
				F.boldYellow + "Well you came to the right place!",
				F.boldYellow + "Over here we have all sorts of mounts",
				F.boldYellow + "for you to choose from!");
		
		ItemStack descriptor = ItemFactory.createItem(Material.SADDLE, F.boldGreen + "Mounts", desc, true);
	    ItemStack back = ItemFactory.createItem(Material.ARROW, F.boldYellow + "Back >", null, false);
	    gui.setItem(0, back);
		gui.setItem(descPos, descriptor);
		
		for (Gadget g : Gadgets.getAllGadgets()){
			if (g.getType() == GadgetType.MOUNT){
				
				if (position == centerPosition){
					setGadget(p, g, position, gui);
					position++;
					continue;
				}
			    
				if (position > centerPosition){
					
					for (int i = position; gui.getItem(i) != null && gui.getItem(i).getType() != Material.AIR; i--){
						position--;
					}
					
					setGadget(p, g, position, gui);
					continue;
				}
					
				if (position < centerPosition){
					
					for (int i = position; gui.getItem(i) != null && gui.getItem(i).getType() != Material.AIR; i--){
						position++;
					}
					
					setGadget(p, g, position, gui);
					continue;
				}

			}
			
		}
		
		p.openInventory(gui);
		UtilPacket.sendActionBarMessage(p, F.boldGreen + "Mounts GUI opened!");
	}
	
	public static void openParticleGUI(Player p) {
		
		Inventory gui = Bukkit.createInventory(null, 54, F.boldRed + "Gadgets" + F.boldAqua + " | Particles");
		
		int descPos = 13;
		int centerPosition = 31;
		int position = centerPosition;
		
		List<String> desc = Arrays.asList(" ",
				F.boldYellow + "Have you ever wanted to be fancy?",
				F.boldYellow + "Going to a Minecraft Ball or RaVe?",
				F.boldYellow + "Then this is the place for you!",
				F.boldYellow + "Over here you can choose from a variety",
				F.boldYellow + "of Particle effects that follow you around!",
				F.boldYellow + "And you thought your skin was fancy...");
		
		ItemStack descriptor = ItemFactory.createItem(Material.REDSTONE, F.boldAqua + "Particles", desc, true);
	    ItemStack back = ItemFactory.createItem(Material.ARROW, F.boldYellow + "Back >", null, false);
	    gui.setItem(0, back);
		gui.setItem(descPos, descriptor);
		
		for (Gadget g : Gadgets.getAllGadgets()){
			if (g.getType() == GadgetType.PARTICLE){
				
				if (position == centerPosition){
					setGadget(p, g, position, gui);
					position++;
					continue;
				}
			    
				if (position > centerPosition){
					
					for (int i = position; gui.getItem(i) != null && gui.getItem(i).getType() != Material.AIR; i--){
						position--;
					}
					
					setGadget(p, g, position, gui);
					continue;
				}
					
				if (position < centerPosition){
					
					for (int i = position; gui.getItem(i) != null && gui.getItem(i).getType() != Material.AIR; i--){
						position++;
					}
					
					setGadget(p, g, position, gui);
					continue;
				}

			}
			
		}
		
		p.openInventory(gui);
		UtilPacket.sendActionBarMessage(p, F.boldGreen + "Particles GUI opened!");
	}
	
	private static void setGadget(Player p, Gadget g, int position, Inventory gui){
		
		if (Gadgets.hasGadget(p, g)){
			gui.setItem(position, g.getIcon(true));
		} else {
			gui.setItem(position, g.getIcon(false));
		}
	}
	
}