package com.starnetmc.ArcadeEngine.Utils;

import java.util.ArrayList;

import org.bukkit.entity.Player;

public class Logger {

	private static ArrayList<Player> logees = new ArrayList<Player>();
	
	public static void log(String msg){
		String log = "<Logger> " + msg;
		System.out.println(log);
		
		log = AF.yellow + "<Logger> " + msg;
		for (Player p : logees){
			p.sendMessage(log);
		}
	}
	
	public static void addLogee(Player p){
		logees.add(p);
	}
	
	public static void removeLogee(Player p){
		if (logees.contains(p)){
		logees.remove(p);
		}
    }
	
	public static ArrayList<Player> getLogees(){
		return logees;
	}
	
	public static boolean isLogee(Player p){
		
		if (logees.contains(p)){
			return true;
		} else {
			return false;
		}
	}
	
}