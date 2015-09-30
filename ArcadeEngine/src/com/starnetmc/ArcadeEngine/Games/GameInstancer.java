package com.starnetmc.ArcadeEngine.Games;

import com.starnetmc.ArcadeEngine.Games.BorderBusters.BorderBusters;
import com.starnetmc.ArcadeEngine.Games.DragonSword.DragonSword;
import com.starnetmc.ArcadeEngine.Games.Pacman.Pacman;
import com.starnetmc.ArcadeEngine.Games.Spleef.StarSpleef;
import com.starnetmc.ArcadeEngine.Games.SurvivalGames.SurvivalGames;

public class GameInstancer {
	
	public static StarSpleef forge_SS_GClass(){
		return new StarSpleef(null, -1, null, null, null, null, null, null, null);
	}
	
	public static DragonSword forge_DS_GClass(){
		return new DragonSword(null, -1, null, null, null, null, null, null, null);
	}
	
	public static SurvivalGames forge_SG_GClass(){
		return new SurvivalGames(null, -1, null, null, null, null, null, null, null);
	}
	
	public static BorderBusters forge_BB_GClass(){
		return new BorderBusters(null, -1, null, null, null, null, null, null, null);
	}
	
	public static Pacman forge_PM_GClass(){
		return new Pacman(null, -1, null, null, null, null, null, null, null, null);
	}
}