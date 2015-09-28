package com.starnetmc.core.database;

public class DatabaseCreds {
	
	private String dbIP;
	private int dbPort;
	private String dbName;
	
	private String dbUserName;
	private String dbPassword;
	
	public DatabaseCreds(String dbIP, int dbPort, String dbName, String dbUserName, String dbPassword){
		this.dbIP = dbIP;
		this.dbPort = dbPort;
		this.dbName = dbName;
		this.dbUserName = dbUserName;
		this.dbPassword = dbPassword;
	}
	
	public void setIP(String ip){
		this.dbIP = ip;
	}
	
	public void setPort(int port){
		this.dbPort = port;
	}
	
	public void setName(String name){
		this.dbName = name;
	}
	
	public void setUserName(String userName){
		this.dbUserName = userName;
	}
	
	public void setPassword(String password){
		this.dbPassword = password;
	}
	
	public String getIP(){
		return dbIP;
	}
	
	public int getPort(){
		return dbPort;
	}
	
	public String getName(){
		return dbName;
	}
	
	public String getUserName(){
		return dbUserName;
	}
	
	public String getPassword(){
		return dbPassword;
	}
}