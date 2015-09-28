package com.starnetmc.core.database;

public class StarDatabaseCreds extends DatabaseCreds {

	public StarDatabaseCreds(String dbIP, int dbPort, String dbName, String dbUserName, String dbPassword) {
		super("server.starnetworkmc.com", 3306, "star_net", "root", "0rbb3db0x1!!");
	}
	
	public StarDatabaseCreds() {
		super("server.starnetworkmc.com", 3306, "star_net", "root", "0rbb3db0x1!!");
	}

}