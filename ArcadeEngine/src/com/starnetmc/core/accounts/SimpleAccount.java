package com.starnetmc.core.accounts;

import com.starnetmc.core.database.Databaser;
import com.starnetmc.core.util.Rank;

public class SimpleAccount {
	
	private String playerUUID;
	private Rank rank;
	private int shards;
	
	public SimpleAccount(String playerUUID, Rank rank, int shards){
		this.playerUUID = playerUUID;
		this.rank = rank;
		this.shards = shards;
	}
	
	public void update(){
		try {
			updateRank();
			updateShards();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updateRank() throws Exception {
		if (Databaser.getRank(playerUUID) == rank) return;
		Databaser.setRank(playerUUID, rank.toString());
	}
	
	public void updateShards() throws Exception {
		int additionShards = shards - Databaser.getShards(playerUUID);
		Databaser.setShards(playerUUID, additionShards);
	}	
	
	public void setRank(Rank rank){
		this.rank = rank;
	}
	
	public void setShards(int shards){
	    this.shards = shards;
	}
	
	public void addShards(int shards){
    	this.shards = this.shards + shards;
    }
	
	public void removeShards(int shards){
	    this.shards = this.shards - shards;
	}
	
	public Rank getRank(){
		return rank;
	}
	
	public int getShards(){
		return shards;
	}
	
	public String getUUID(){
		return playerUUID;
	}
	
}