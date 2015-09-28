
package com.starnetmc.core.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.starnetmc.core.database.util.MySQL;
import com.starnetmc.core.punish.Punishment;
import com.starnetmc.core.util.Rank;
import com.starnetmc.core.util.UPlayer;

public class Databaser {

	JavaPlugin main;
    public static MySQL db;

	public Databaser(JavaPlugin main, MySQL database) {
		this.main = main;
		db = database;
	}
	
	public Databaser(JavaPlugin main) {
		this.main = main;
	}

	
	public void setup() throws Exception, SQLException, ClassNotFoundException {
		if (!db.checkConnection()) {
			db.openConnection();
		}

		Statement a = db.getConnection().createStatement();
		a.executeUpdate("CREATE TABLE IF NOT EXISTS `Accounts` (`id` INT NOT NULL AUTO_INCREMENT,`Name` varchar(64),`UUID` MEDIUMTEXT, `Shards` INT, `Rank` varchar(32), `firstLogin` LONG, `lastLogin` LONG, `totalPlayTime` INT, `tutorial` TINYINT(1),PRIMARY KEY (`id`));");

		Statement p = db.getConnection().createStatement();
		p.executeUpdate("CREATE TABLE IF NOT EXISTS `Punishments` (`id` INT NOT NULL AUTO_INCREMENT, `Name` MEDIUMTEXT,`UUID` MEDIUMTEXT, `PunishType` varchar(32), `PunishReason` MEDIUMTEXT,`PunishPerm` BOOL, `Punisher` MEDIUMTEXT, PRIMARY KEY (`id`));");

		Statement sf = db.getConnection().createStatement();
		sf.executeUpdate("CREATE TABLE IF NOT EXISTS `Filter` (`Word` varchar(32));");

		Statement n = db.getConnection().createStatement();
		n.executeUpdate("CREATE TABLE IF NOT EXISTS `NPCManager` (`id` INT NOT NULL AUTO_INCREMENT, `Name` MEDIUMTEXT, `NPCType` varchar(32), `World` varchar(32),`x` DECIMAL, `y` DECIMAL, `z` DECIMAL, PRIMARY KEY (`id`));");

		String alisID = "f5738c50-ca61-44d7-af98-6b188e6285a1";
		
		if (!hasAccount(alisID)){
			createAccount(alisID, "alish2001");
			setRank(alisID, "OWNER");
		}
		
		a.close();
		p.close();
		sf.close();
		n.close();
		db.closeConnection();
	}

	public static void removeAllPunishments(String player) throws Exception {
		
		if (!db.checkConnection()) {
			db.openConnection();
		}

		OfflinePlayer o = UPlayer.getOfflinePlayerFromName(player);
		String uuid = o.getUniqueId().toString();

		Statement  s = db.getConnection().createStatement();
		if(isBanned(uuid)) {
			s.executeUpdate("DELETE FROM `Punishments` WHERE `UUID`='"+uuid+"';");
		}
		else if(isMuted(uuid)) {
			s.executeUpdate("DELETE FROM `Punishments` WHERE `UUID`='"+uuid+"';");
		}
		else if(Punishment._tempBans.containsKey(uuid)) {
			Punishment._tempBans.remove(uuid);
		}
		else if(Punishment._tempMutes.containsKey(uuid)) {
			Punishment._tempMutes.remove(uuid);
		}
		else {
			s.close();
			db.closeConnection();
			return;
		}
		s.close();
		db.closeConnection();
	}
	
	public static void makeMuted(Player punisher, String offender, String reason)
			throws Exception {

		if (!db.checkConnection()) {
			db.openConnection();
		}

		OfflinePlayer o = UPlayer.getOfflinePlayerFromName(offender);
		String uuid = o.getUniqueId().toString();

		Statement s = db.getConnection().createStatement();
		s.executeUpdate("INSERT INTO `Punishments` (`Name`,`UUID`,`PunishType`,`PunishReason`,`PunishPerm`,`Punisher`) VALUES ('"
				+ o.getName()
				+ "','"
				+ uuid
				+ "','MUTE','"
				+ reason
				+ "','1','" + punisher.getName() + "');");
		s.close();
		db.closeConnection();
	}

	public static void makeBanned(Player punisher, String offender,
			String reason) throws Exception {
		if (!db.checkConnection()) {
			db.openConnection();
		}

		OfflinePlayer o = UPlayer.getOfflinePlayerFromName(offender);
		String uuid = o.getUniqueId().toString();

		Statement s = db.getConnection().createStatement();
		s.executeUpdate("INSERT INTO `Punishments` (`Name`,`UUID`,`PunishType`,`PunishReason`,`PunishPerm`,`Punisher`) VALUES ('"
				+ o.getName()
				+ "','"
				+ uuid
				+ "','BAN','"
				+ reason
				+ "','1','" + punisher.getName() + "');");
		s.close();
		db.closeConnection();
	}

	public static boolean isMuted(String uuid)
			throws Exception {

		if (!db.checkConnection()) {
			db.openConnection();
		}

		

		Statement s = db.getConnection().createStatement();
		ResultSet rs = s
				.executeQuery("SELECT * FROM `Punishments` WHERE `UUID`='"+uuid+"' AND `PunishType`='MUTE';");

		if(!rs.next()) {
			rs.close();
			db.closeConnection();
			return false;
		}
		else {
			rs.close();
			db.closeConnection();
			return true;
		}
	}

	public static String getPunishReason(Player player) throws Exception {
		
		if (!db.checkConnection()) {
			db.openConnection();
		}

		String uuid = player.getUniqueId().toString();

		Statement s = db.getConnection().createStatement();
		ResultSet rs = s
				.executeQuery("SELECT * FROM `Punishments` WHERE `UUID`='"+uuid+"';");
		
		if(!rs.next()) {
			rs.close();
			db.closeConnection();
			return null;
		}
		else {
			String reason = rs.getString("PunishReason");
			rs.close();
			db.closeConnection();
			return reason;
		}

	}
	
	public static boolean isBanned(String uuid)
			throws Exception {


		if (!db.checkConnection()) {
			db.openConnection();
		}


		Statement s = db.getConnection().createStatement();
		ResultSet rs = s.executeQuery("SELECT * FROM `Punishments` WHERE `UUID`='"+uuid+"' AND `PunishType`='BAN';");
		
		if(!rs.next()) {
			rs.close();
			db.closeConnection();
			return false;
		}
		else {
			rs.close();
			db.closeConnection();
			return true;
		}


	}

	
	public static boolean hasAccount(String uuid) throws Exception {

		if (!db.checkConnection()) {
			db.openConnection();
		}

		Statement s = db.getConnection().createStatement();
		ResultSet rs = s.executeQuery("SELECT * FROM `Accounts` WHERE `UUID`='"
				+ uuid + "';");
		
		if (!rs.next()) {
			rs.close();
			db.closeConnection();
			return false;
		} else {
			rs.close();
			db.closeConnection();
			return true;
		}

	}

	public static void createAccount(Player player) throws Exception {
		String name = player.getName();
		String uuid = player.getUniqueId().toString();
		
		createAccount(uuid, name);
	}
	
	public static void createAccount(String uuid, String name) throws Exception {

		if (!db.checkConnection()) {
			db.openConnection();
		}
		
		Statement s = db.getConnection().createStatement();
		s.executeUpdate("INSERT INTO `Accounts` (`Name`,`UUID`,`Shards`,`Rank`,`firstLogin`,`lastLogin`,`totalPlayTime`) VALUES ('"
				+ name + "','" + uuid + "','200','DEFAULT',now(),now(),'0');");
		
		s.close();
		db.closeConnection();
	}
	
	public static Rank getRank(String uuid) throws Exception {

		if (!db.checkConnection()) {
			db.openConnection();
		}

		Statement s = db.getConnection().createStatement();
		ResultSet rs = s.executeQuery("SELECT * FROM `Accounts` WHERE UUID='"
				+ uuid + "';");
		
		if (!rs.next()) {
			rs.close();
	        db.closeConnection();
			return Rank.DEFAULT;
		}
		
		Rank rank = Rank.valueOf(rs.getString("Rank"));
		rs.close();
        db.closeConnection();
		return rank;
	}

	public static void setRank(String uuid, String rank) throws Exception {

		if (!db.checkConnection()) {
			db.openConnection();
		}

		Statement s = db.getConnection().createStatement();
		s.executeUpdate("UPDATE `Accounts` SET `Rank`='" + rank
				+ "' WHERE `UUID`='" + uuid + "';");
		s.close();
		db.closeConnection();
	}

	public static int getShards(String uuid) throws Exception {

		if (!db.checkConnection()) {
			db.openConnection();
		}

		Statement s = db.getConnection().createStatement();
		ResultSet rs = s.executeQuery("SELECT * FROM `Accounts` WHERE `UUID`='"
				+ uuid + "';");
		
		if (!rs.next()) {
			rs.close();
			db.closeConnection();
			return 0;
		} else {
			int shards = rs.getInt("Shards");
			rs.close();
			db.closeConnection();
			return shards;
		}

	}

	public static void setShards(String uuid, int amount) throws Exception {

		if (!db.checkConnection()) {
			db.openConnection();
		}

		Statement s = db.getConnection().createStatement();
		s.executeUpdate("UPDATE `Accounts` SET `Shards`='"
				+ (getShards(uuid) + amount) + "' WHERE `UUID`='" + uuid + "';");
		s.close();
		db.closeConnection();
	}

	public static void setLastLogin(String uuid) throws Exception {

		if (!db.checkConnection()) {
			db.openConnection();
		}
		
		Statement s = db.getConnection().createStatement();
		s.executeUpdate("UPDATE `Accounts` SET `lastLogin`='now()' WHERE `UUID`='"
				+ uuid + "';");
		s.close();
		db.closeConnection();
	}

	public static boolean hasTutorial(String uuid) throws Exception {

		if (!db.checkConnection()) {
			db.openConnection();
		}

		Statement s = db.getConnection().createStatement();
		ResultSet rs = s.executeQuery("SELECT * FROM `Accounts` WHERE `UUID`='"
				+ uuid + "'");
		
		if (!rs.next()) {
			rs.close();
			db.closeConnection();
			return false;
		} else {
			boolean tut = rs.getBoolean("tutorial");
			rs.close();
			db.closeConnection();
			return tut;
		}

	}

	public static void setHasTutorial(String uuid) throws Exception {

		if (!db.checkConnection()) {
			db.openConnection();
		}

		Statement s = db.getConnection().createStatement();
		s.executeUpdate("UPDATE `Accounts` SET `tutorial`='1' WHERE `UUID`='"
				+ uuid + "';");
		s.close();
        db.closeConnection();
	}

	public static List<String> downloadFilter() throws Exception {

		if (!db.checkConnection()) {
			db.openConnection();
		}

		List<String> words = new ArrayList<String>();

		Statement s = db.getConnection().createStatement();
		ResultSet rs = s.executeQuery("SELECT * FROM `Filter`");
		
		while (rs.next()) {
			words.add(rs.getString("Word"));
		}
		
		rs.close();
		db.closeConnection();

		return words;

	}

	// Start NPC Code
	public static LinkedHashMap<String, Location> downloadVillagerNPCs()
			throws Exception {

		if (!db.checkConnection()) {
			db.openConnection();
		}

		LinkedHashMap<String, Location> npcs = new LinkedHashMap<String, Location>();

		Statement s = db.getConnection().createStatement();
		ResultSet rs = s
				.executeQuery("SELECT * FROM `NPCManager` WHERE `NPCType`='VILLAGER'");
		
		while (rs.next()) {

			npcs.put(
					rs.getString("Name"),
					new Location(Bukkit.getWorld(rs.getString("World")), rs
							.getInt("x"), rs.getInt("y"), rs.getInt("z")));
		}
		
		rs.close();
		db.closeConnection();

		return npcs;

	}

	public static LinkedHashMap<String, Location> downloadPigNPCs()
			throws Exception {

		if (!db.checkConnection()) {
			db.openConnection();
		}

		LinkedHashMap<String, Location> npcs = new LinkedHashMap<String, Location>();

		Statement s = db.getConnection().createStatement();
		ResultSet rs = s
				.executeQuery("SELECT * FROM `NPCManager` WHERE `NPCType`='PIG'");
		
		while (rs.next()) {

			npcs.put(
					rs.getString("Name"),
					new Location(Bukkit.getWorld(rs.getString("World")), rs
							.getInt("x"), rs.getInt("y"), rs.getInt("z")));
		}
		rs.close();
		db.closeConnection();

		return npcs;

	}

	public static LinkedHashMap<String, Location> downloadSkeletonNPCs()
			throws Exception {

		if (!db.checkConnection()) {
			db.openConnection();
		}

		LinkedHashMap<String, Location> npcs = new LinkedHashMap<String, Location>();

		Statement s = db.getConnection().createStatement();
		ResultSet rs = s
				.executeQuery("SELECT * FROM `NPCManager` WHERE `NPCType`='SKELETON'");
		
		while (rs.next()) {

			npcs.put(
					rs.getString("Name"),
					new Location(Bukkit.getWorld(rs.getString("World")), rs
							.getInt("x"), rs.getInt("y"), rs.getInt("z")));
		}
		rs.close();
		db.closeConnection();

		return npcs;

	}

	public static LinkedHashMap<String, Location> downloadSlimeNPCs()
			throws Exception {

		if (!db.checkConnection()) {
			db.openConnection();
		}

		LinkedHashMap<String, Location> npcs = new LinkedHashMap<String, Location>();

		Statement s = db.getConnection().createStatement();
		ResultSet rs = s
				.executeQuery("SELECT * FROM `NPCManager` WHERE `NPCType`='SLIME'");
		
		while (rs.next()) {

			npcs.put(
					rs.getString("Name"),
					new Location(Bukkit.getWorld(rs.getString("World")), rs
							.getInt("x"), rs.getInt("y"), rs.getInt("z")));
		}
		rs.close();
		db.closeConnection();

		return npcs;

	}

	public static LinkedHashMap<String, Location> downloadZombieNPCs()
			throws Exception {

		if (!db.checkConnection()) {
			db.openConnection();
		}

		LinkedHashMap<String, Location> npcs = new LinkedHashMap<String, Location>();

		Statement s = db.getConnection().createStatement();
		ResultSet rs = s
				.executeQuery("SELECT * FROM `NPCManager` WHERE `NPCType`='ZOMBIE'");
		
		while (rs.next()) {

			npcs.put(
					rs.getString("Name"),
					new Location(Bukkit.getWorld(rs.getString("World")), rs
							.getInt("x"), rs.getInt("y"), rs.getInt("z")));
		}
		
		rs.close();
		db.closeConnection();
		return npcs;

	}

	public static void createNPC(String name, String entitytype, Location loc)
			throws Exception {

		if (!db.checkConnection()) {
			db.openConnection();
		}

		Statement s = db.getConnection().createStatement();
		s.execute("INSERT INTO `NPCManager` (`Name`,`NPCType`,`World`,`x`,`y`,`z`) VALUES ('"
				+ name
				+ "','"
				+ entitytype
				+ "','"
				+ loc.getWorld().getName()
				+ "','"
				+ loc.getX()
				+ "','"
				+ loc.getY()
				+ "','"
				+ loc.getZ()
				+ "');");
		s.close();
		db.closeConnection();
	}

	public static void deleteNPC(String name) throws Exception {

		if (!db.checkConnection()) {
			db.openConnection();
		}

		Statement s = db.getConnection().createStatement();
		s.executeUpdate("DELETE FROM `NPCManager` WHERE `Name`='" + name
				+ " ';");
		s.close();
		db.closeConnection();
	}


}