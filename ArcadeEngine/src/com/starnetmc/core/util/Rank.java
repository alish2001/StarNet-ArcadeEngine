package com.starnetmc.core.util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public enum Rank {

	OWNER(F.boldDR + "OWNER"),
	DEVELOPER(F.boldDP + "DEV"),
	ADMIN(F.boldRed + "ADMIN"),
	BUILDER(F.boldBlue + "BUILDER"),
	MODERATOR(F.boldGold + "MOD"),
	HELPER(F.boldGreen + "HELPER"),
	YOUTUBE(F.boldRed + "YOU" + F.boldWhite + "[TUBE]"),
	MVP(F.boldAqua + "MVP"),
	VIP(F.boldYellow + "VIP"),
	DEFAULT(F.boldWhite + "DEFAULT");
	
	static List<Rank> staffRanks;
	private String tag;
	
	Rank(String tag){
		this.tag = tag;
		initList();
	}
	
	private void initList(){
		staffRanks = new ArrayList<Rank>();
		staffRanks.add(OWNER);
		staffRanks.add(DEVELOPER);
		staffRanks.add(ADMIN);
		staffRanks.add(BUILDER);
		staffRanks.add(MODERATOR);
		staffRanks.add(HELPER);
	}
	
	public String getTag(boolean noColor){
		
		if (noColor){
			return ChatColor.stripColor(tag);
		}
		
		return tag;
	}
	
	public static List<Rank> getStaffRanks(){
		return staffRanks;
	}
	
	public boolean has(Rank rt) {
		return has(null, rt);
	}

	public boolean has(Player player, Rank rt) {

		if (compareTo(rt) <= 0) {
			return true;
		}
		return false;

	}
	
	public static Rank getRankFromString(String rank){
		Rank finalRank = DEFAULT;
		
		for (Rank r : getStaffRanks()){
			if (r.getTag(false).equalsIgnoreCase(rank)){
				finalRank = r;
			}
		}
		
		if (rank.equalsIgnoreCase("youtube")) finalRank = YOUTUBE;
		if (rank.equalsIgnoreCase("mvp")) finalRank = MVP;
		if (rank.equalsIgnoreCase("vip")) finalRank = VIP;
		if (rank.equalsIgnoreCase("default")) finalRank = DEFAULT;
		
		return finalRank;
	}

}
