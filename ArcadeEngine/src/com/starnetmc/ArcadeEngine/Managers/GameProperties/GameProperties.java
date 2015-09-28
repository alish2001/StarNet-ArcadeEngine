package com.starnetmc.ArcadeEngine.Managers.GameProperties;

import java.util.ArrayList;

import org.bukkit.GameMode;
import org.bukkit.Material;

import com.starnetmc.ArcadeEngine.Managers.Maps.MapTypes.Map;

public class GameProperties {

	private int playersNeeded;
	private int maxPlayers;
	private GameMode defaultGameMode;
	private long defaultTime;
	
	private boolean teamGame;
	private boolean teamKilling;

	private boolean PvP;
	private boolean PvE;
	private boolean fallDamage;
	private boolean gracePeriod;
	private long graceTime;
	private boolean announceGrace;

	private boolean itemPickup;
	private boolean itemDrop;
	private boolean itemEat;
	private boolean dropItemsWhenDead;
	private boolean inventoryModification;
	
	private boolean groundArrowRemoval;
	private boolean arrowInBody;

	private boolean blockBreak;
	private boolean customBlockBreak;
	private ArrayList<Material> breakableBlocks;
	private boolean blockPlace;
	private boolean customBlockPlace;
	private ArrayList<Material> placeableBlocks;
	private boolean blockExplosion;
	private boolean customBlockExplosion;
	private ArrayList<Material> explodableBlocks;

	private boolean explosionDamage;
	private boolean waterDamage;
	private boolean fireDamage;
	private boolean lavaDamage;
	private boolean voidDamage;
	private boolean sufficationDamage;
	private boolean poisonDamage;

	private boolean hungry;
	private boolean hungerDamage;

	private boolean foreverSpec;
	private long respawnTime;

	private Map gameMap;

	public GameProperties() {

		this.playersNeeded = 2;
		this.maxPlayers = 10;
		this.defaultGameMode = GameMode.SURVIVAL;
		this.defaultTime = 0;
		this.teamGame = false;
		this.teamKilling = false;
		this.PvP = true;
		this.PvE = true;
		this.fallDamage = true;
		this.gracePeriod = false;
		this.graceTime = 5 * 20;
		this.announceGrace = false;
		this.itemPickup = true;
		this.itemDrop = false;
		this.itemEat = false;
		this.dropItemsWhenDead = false;
		this.inventoryModification = false;
		this.blockBreak = false;
		this.customBlockBreak = false;
		this.blockPlace = false;
		this.blockExplosion = false;
		this.customBlockExplosion = false;
		this.groundArrowRemoval = true;
		this.arrowInBody = false;
		this.explosionDamage = true;
		this.waterDamage = true;
		this.fireDamage = true;
		this.lavaDamage = true;
		this.voidDamage = true;
		this.sufficationDamage = true;
		this.poisonDamage = true;
		this.hungry = false;
		this.hungerDamage = true;
		this.foreverSpec = true;
		this.respawnTime = 10 * 20;
		this.gameMap = null;
	}

	public void setPvP(boolean b) {
		this.PvP = b;
	}

	public void setPvE(boolean b) {
		this.PvE = b;
	}

	public void setFallDamage(boolean b) {
		this.fallDamage = b;
	}

	public void setGracePeriod(boolean b) {
		this.gracePeriod = b;
	}

	public void setGraceTime(long time) {
		this.graceTime = time + 100;
	}

	public void setGraceAnnouncement(boolean b) {
		this.announceGrace = b;
	}

	public void setItemPickup(boolean b) {
		this.itemPickup = b;
	}

	public void setItemDrop(boolean b) {
		this.itemDrop = b;
	}

	public void setItemEat(boolean itemEat) {
		this.itemEat = itemEat;
	}

	public void setDeathItemDrop(boolean b) {
		this.dropItemsWhenDead = b;
	}

	public void setInventoryModification(boolean b) {
		this.inventoryModification = b;
	}

	public void setBlockBreak(boolean b) {
		this.blockBreak = b;
	}

	public void setCustomBreak(boolean b) {
		this.customBlockBreak = b;
	}

	public void setCustomBreakBlocks(ArrayList<Material> blocks) {
		this.breakableBlocks = blocks;
	}

	public void setBlockPlace(boolean b) {
		this.blockPlace = b;
	}
	
	public void setCustomPlace(boolean b) {
		this.customBlockPlace = b;
	}

	public void setCustomPlaceBlocks(ArrayList<Material> blocks) {
		this.placeableBlocks = blocks;
	}
	
	public void setBlockExplosion(boolean b) {
		this.blockExplosion = b;
	}
	
	public void setCustomBlockExplosion(boolean b) {
		this.customBlockExplosion = b;
	}
	
	public void setCustomExplosionBlocks(ArrayList<Material> blocks) {
		this.explodableBlocks = blocks;
	}
	
	public void setGroundArrowRemoval(boolean b){
		this.groundArrowRemoval = b;
	}
	
	public void setArrowInBody(boolean b){
		this.arrowInBody = b;
	}

	public void setExplosionDamage(boolean b) {
		this.explosionDamage = b;
	}

	public void setWaterDamage(boolean b) {
		this.waterDamage = b;
	}

	public void setFireDamage(boolean b) {
		this.fireDamage = b;
	}

	public void setLavaDamage(boolean b) {
		this.lavaDamage = b;
	}

	public void setVoidDamage(boolean b) {
		this.voidDamage = b;
	}
	
	public void setSufficationDamage(boolean b){
		this.sufficationDamage = b;
	}
	
	public void setPoisonDamage(boolean b){
		this.poisonDamage = b;
	}

	public void setHungry(boolean hungry) {
		this.hungry = hungry;
	}

	public void setHungerDamage(boolean b) {
		this.hungerDamage = b;
	}

	public void setForeverSpec(boolean b) {
		this.foreverSpec = b;
	}

	public void setRespawnTime(long time) {
		this.respawnTime = time;
	}

	public void setMap(Map gameMap) {
		this.gameMap = gameMap;
	}

	public void setPlayersNeeded(int playersNeeded) {
		this.playersNeeded = playersNeeded;
	}
	
	public void setMaxPlayers(int maxPlayers){
		this.maxPlayers = maxPlayers;
	}
	
	public void setDefaultGameMode(GameMode defaultGameMode){
		this.defaultGameMode = defaultGameMode;
	}

	public void setDefaultTime(long defaultTime){
		this.defaultTime = defaultTime;
	}
	
	public void setTeamGame(boolean b) {
		this.teamGame = b;
	}
	
	public void setTeamKilling(boolean b){
		this.teamKilling = b;
	}

	public boolean getPvP() {
		return PvP;
	}

	public boolean getPvE() {
		return PvE;
	}

	public boolean getFallDamage() {
		return fallDamage;
	}

	public boolean getGracePeriod() {
		return gracePeriod;
	}

	public long getGraceTime() {
		return graceTime;
	}

	public boolean getGraceAnnounce() {
		return announceGrace;
	}

	public boolean getItemPickup() {
		return itemPickup;
	}

	public boolean getItemDrop() {
		return itemDrop;
	}

	public boolean getItemEat() {
		return itemEat;
	}

	public boolean getDeathItemDrop() {
		return dropItemsWhenDead;
	}

	public boolean getInventoryModification() {
		return inventoryModification;
	}

	public boolean getBlockBreak() {
		return blockBreak;
	}

	public boolean getBlockBreakCustom() {
		return customBlockBreak;
	}

	public ArrayList<Material> getCustomBreakBlocks() {
		return breakableBlocks;
	}

	public boolean getBlockPlace() {
		return blockPlace;
	}
	
	public boolean getBlockPlaceCustom() {
		return customBlockPlace;
	}

	public ArrayList<Material> getCustomPlaceBlocks() {
		return placeableBlocks;
	}
	
	public boolean getBlockExplosion() {
		return blockExplosion;
	}
	
	public boolean getBlockExplosionCustom() {
		return customBlockExplosion;
	}
	
	public ArrayList<Material> getCustomExplosionBlocks(){
		return explodableBlocks;
	}
	
	public boolean getGroundArrowRemoval(){
		return groundArrowRemoval;
	}
	
	public boolean getArrowInBody(){
		return arrowInBody;
	}

	public boolean getExplosionDamage() {
		return explosionDamage;
	}

	public boolean getWaterDamage() {
		return waterDamage;
	}

	public boolean getFireDamage() {
		return fireDamage;
	}

	public boolean getLavaDamage() {
		return lavaDamage;
	}

	public boolean getVoidDamage() {
		return voidDamage;
	}
	
	public boolean getSufficationDamage(){
		return sufficationDamage;
	}
	
	public boolean getPoisonDamage(){
		return poisonDamage;
	}

	public boolean getHungry() {
		return hungry;
	}

	public boolean getHungerDamage() {
		return hungerDamage;
	}

	public boolean getForeverSpec() {
		return foreverSpec;
	}

	public long getRespawnTime() {
		return respawnTime;
	}

	public Map getMap() {
		return gameMap;
	}

	public int getPlayersNeeded() {
		return playersNeeded;
	}
	
	public int getMaxPlayers(){
		return maxPlayers;
	}
	
	public GameMode getDefaultGameMode(){
		return defaultGameMode;
	}
	
	public long getDefaultTime(){
		return defaultTime;
	}

	public boolean getTeamGame() {
		return teamGame;
	}
	
	public boolean getTeamKilling(){
		return teamKilling;
	}

}