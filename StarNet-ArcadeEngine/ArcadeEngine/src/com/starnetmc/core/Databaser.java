package com.starnetmc.core;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.starnetmc.core.database.util.MySQL;
import com.starnetmc.core.punish.PunishType;
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
		a.executeUpdate("CREATE TABLE IF NOT EXISTS `Accounts` (`id` INT NOT NULL AUTO_INCREMENT,`Name` varchar(64),`UUID` MEDIUMTEXT, `Shards` INT, `Rank` varchar(32), `firstLogin` DateTime, `lastLogin` DateTime, `totalPlayTime` INT, `tutorial` TINYINT(1),PRIMARY KEY (`id`));");

		Statement p = db.getConnection().createStatement();
		p.executeUpdate("CREATE TABLE IF NOT EXISTS `Punishments` (`id` INT NOT NULL AUTO_INCREMENT, `OffenderUID` MEDIUMTEXT, `PunishType` varchar(32), `PunishReason` MEDIUMTEXT, `Duration` INT,`PunishDate` DateTime, `PunisherUID` MEDIUMTEXT,`RemoverUID` MEDIUMTEXT, `RemoveDate` DateTime,`RemoveReason` MEDIUMTEXT,PRIMARY KEY (`id`));");

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
//

		db.closeConnection();
	}
	
	public static void makePunished(Player punisher, String offender, String reason, String punishType, Integer Duration) throws Exception {
    /* punisher : who apply this punishment
     * offender : who is punished
     * reason: Why is punished
     * punishType : TempMute, Perm Mute, TempBan, PermBan
     * duration : Only for temporary punishType, duration range: 1..43200 (minutes) {1Minute to 30 Days}
     */
		
		if (!db.checkConnection()) {
			db.openConnection();
		}
		
		OfflinePlayer o = UPlayer.getOfflinePlayerFromName(offender);
		String uuid = o.getUniqueId().toString();
		Date date = new Date();
		Statement s = db.getConnection().createStatement();
		s.executeUpdate("INSERT INTO `Punishments` (`Name`,`UUID`,`PunishType`,`PunishReason`,`PunishDate`, `Punisher`) VALUES ('"
				+ o.getName()
				+ "','"
				+ uuid
				+ "','"
				+ punishType
				+ "','"
				+ reason
				+ "','"
				+ date
				+ "','"
				+punisher.getName() 
				+ "');");
		s.close();
		db.closeConnection();
	}

	public static boolean isPuinished(String uuid, PunishType pType) throws Exception {
		
		if (!db.checkConnection()) {
			db.openConnection();
		}
		
		Statement s = db.getConnection().createStatement();
		ResultSet rs = s
				.executeQuery("SELECT * FROM `Punishments` WHERE `UUID`='"+uuid+" AND `PunishType`="+pType+";");

		if(!rs.next() && (pType == PunishType.PERMBAN ||pType == PunishType.PERMMUTE )) {
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
	
	public static boolean isBanned(String uuid)	throws Exception {

		if (!db.checkConnection()) {
			db.openConnection();
		}

		db.closeConnection();
	}

	
	public static boolean hasAccount(String uuid) throws Exception {

		if (!db.checkConnection()) {
			db.openConnection();
		}

		db.closeConnection();
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
		
		db.closeConnection();
	}
	
	public static Rank getRank(String uuid) throws Exception {

		if (!db.checkConnection()) {
			db.openConnection();
		}

        db.closeConnection();
	}

	public static void setRank(String uuid, String rank) throws Exception {

		if (!db.checkConnection()) {
			db.openConnection();
		}

		db.closeConnection();
	}

	public static int getShards(String uuid) throws Exception {

		if (!db.checkConnection()) {
			db.openConnection();
		}

		db.closeConnection();
	}

	public static void setShards(String uuid, int amount) throws Exception {

		if (!db.checkConnection()) {
			db.openConnection();
		}

		db.closeConnection();
	}

	public static void setLastLogin(String uuid) throws Exception {

		if (!db.checkConnection()) {
			db.openConnection();
		}
		
		db.closeConnection();
	}

	public static boolean hasTutorial(String uuid) throws Exception {

		if (!db.checkConnection()) {
			db.openConnection();
		}

		db.closeConnection();
	}

	public static void setHasTutorial(String uuid) throws Exception {

		if (!db.checkConnection()) {
			db.openConnection();
		}

        db.closeConnection();
	}

	public static List<String> downloadFilter() throws Exception {

		if (!db.checkConnection()) {
			db.openConnection();
		}
		db.closeConnection();
	}

	// Start NPC Code
	public static LinkedHashMap<String, Location> downloadVillagerNPCs()throws Exception {

		if (!db.checkConnection()) {
			db.openConnection();
		}

		db.closeConnection();
	}

	public static LinkedHashMap<String, Location> downloadPigNPCs() throws Exception {

		if (!db.checkConnection()) {
			db.openConnection();
		}
		db.closeConnection();
	}

	public static LinkedHashMap<String, Location> downloadSkeletonNPCs() throws Exception {

		if (!db.checkConnection()) {
			db.openConnection();
		}

		db.closeConnection();
	}

	public static LinkedHashMap<String, Location> downloadSlimeNPCs() throws Exception {

		if (!db.checkConnection()) {
			db.openConnection();
		}
		db.closeConnection();
	}

	public static LinkedHashMap<String, Location> downloadZombieNPCs() throws Exception {

		if (!db.checkConnection()) {
			db.openConnection();
		}
		db.closeConnection();
	}

	public static void createNPC(String name, String entitytype, Location loc)throws Exception {

		if (!db.checkConnection()) {
			db.openConnection();
		}
		db.closeConnection();
	}

	public static void deleteNPC(String name) throws Exception {

		if (!db.checkConnection()) {
			db.openConnection();
		}
		db.closeConnection();
	}
}
