package com.starnetmc.ArcadeEngine.Games;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.starnetmc.ArcadeCore;
import com.starnetmc.ArcadeEngine.Events.ScoreboardUpdateEvent;
import com.starnetmc.ArcadeEngine.Managers.GameState;
import com.starnetmc.ArcadeEngine.Managers.Commands.ArcadeCMDS;
import com.starnetmc.ArcadeEngine.Managers.Counters.GameCountdown;
import com.starnetmc.ArcadeEngine.Utils.AF;
import com.starnetmc.ArcadeEngine.Utils.All;
import com.starnetmc.ArcadeEngine.Utils.USound;
import com.starnetmc.core.commands.util.CommandBase;
import com.starnetmc.core.util.Rank;
import com.starnetmc.core.util.UString;

public class GameCommand extends CommandBase<ArcadeCMDS> {
	
	public GameCommand(ArcadeCMDS plugin) {
		super(plugin, Rank.ADMIN, new String[] { "game", "g" });
	}
	
	public void execute(Player player, String[] args) {
		
		Player p = player;
		
		if (args.length == 0){
			listCMDS(p);
			return;
		}
		
		if (args[0].equalsIgnoreCase("set")){
			
			if (!ArcadeCore.getArcadeManager().isState(GameState.LOBBY)){
				p.sendMessage(AF.game("§cThe current Game must be stopped first."));
				return;
			}
			
			if (args.length < 2){
				listCMDS(p);
				return;
			}
			
			if (!validateGameType(args[1])){
				p.sendMessage(AF.game("Hey that's not a recognizable GameType!"));
				p.sendMessage(AF.game("Here you go..."));
				listGameTypes(p);
				return;
			}
			
			ArcadeCore.getArcadeManager().setGame(ArcadeCore.getArcadeManager().getGame().getGameTypeFromVarName(args[1]));
			AF.broadcast(p.getName() + " Has set the Game to " + ArcadeCore.getArcadeManager().getGame().getName());
			return;
		}
		
		if (args[0].equalsIgnoreCase("start")){
			
			if (ArcadeCore.getArcadeManager().isState(GameState.INGAME) || ArcadeCore.getArcadeManager().isState(GameState.POSTGAME)){
				p.sendMessage(AF.game("§cThe current Game must be stopped first."));
				return;
			}
			
			if (!ArcadeCore.getArcadeManager().getCounterManager().isGameCountdownInProgress()){
			    ArcadeCore.getArcadeManager().getCounterManager().startGameCountdown();
			    AF.broadcast(p.getName() + " Has Started the Game!");
				USound.AllPSound(Sound.NOTE_PLING, 1.25f, 1);
			    return;
			} else {
				GameCountdown.time--;
			    AF.broadcast(p.getName() + " Has Started the Game!");
				for (Player sp : Bukkit.getServer().getOnlinePlayers()){
				  Bukkit.getServer().getPluginManager().callEvent(new ScoreboardUpdateEvent(sp));
				  }
				USound.AllPSound(Sound.NOTE_PLING, 1.25f, 1);
				return;
			 }
		}
		
		if (args[0].equalsIgnoreCase("instantstart")){
			
			if (ArcadeCore.getArcadeManager().isState(GameState.INGAME) || ArcadeCore.getArcadeManager().isState(GameState.POSTGAME)){
				p.sendMessage(AF.game("§cThe current Game must be stopped first."));
				return;
			}
			
			if (!ArcadeCore.getArcadeManager().getCounterManager().isGameCountdownInProgress()){
			    ArcadeCore.getArcadeManager().getCounterManager().startGameCountdown();
			    GameCountdown.time = 5;
			    AF.broadcast(p.getName() + " Has Insta-Started the Game!");
				USound.AllPSound(Sound.NOTE_PLING, 1.25f, 1);
			    return;
			} else {
				p.sendMessage(AF.game("vay de hel u sturt me women i am aleeardy instating shaaart uuup!"));
				return;
			}
		}
		
		if (args[0].equalsIgnoreCase("stop")){
			if (ArcadeCore.getArcadeManager().isState(GameState.INGAME) || ArcadeCore.getArcadeManager().isState(GameState.LOADING)){
				ArcadeCore.getArcadeManager().stopGame(true);
				AF.broadcast(p.getName() + " Has Stopped the Game!");
				return;
			}
			
			if (ArcadeCore.getArcadeManager().isState(GameState.LOBBY) || ArcadeCore.getArcadeManager().isState(GameState.POSTGAME)){
				p.sendMessage(AF.game("§cThere isn't a Game to Stop!"));
				return;
			}
		}
		
		if (args[0].equalsIgnoreCase("list")){
			listGameTypes(p);
			return;
		}
		
	}
	
	private void listCMDS(Player p){
		p.sendMessage(AF.listTop("Games"));
		p.sendMessage(AF.cmdList("/Game Set <Game>", "Sets the current Game.", "§c ADMIN"));
		p.sendMessage(AF.cmdList("/Game Start", "Starts the current Game.", "§c ADMIN"));
		p.sendMessage(AF.cmdList("/Game InstantStart", "Starts the current Game.", "§c ADMIN"));
		p.sendMessage(AF.cmdList("/Game Stop", "Stops the current Game.", "§c ADMIN"));
		p.sendMessage(AF.cmdList("/Game List", "Lists all the available Games.", "§c ADMIN"));
	}
	
	private void listGameTypes(Player p){
		p.sendMessage(AF.listTop("GameTypes"));
		
		for (GameType g : All.getAllGameTypes()){
			p.sendMessage(ChatColor.YELLOW + "" + ChatColor.ITALIC + UString.UppercaseFirstLetter(g.toString()));
		}
	}
	
	private boolean validateGameType(String type){
		boolean k = false;
		
		for (GameType g : All.getAllGameTypes()){
			
			String name = g.toString();
			
			if (type.equalsIgnoreCase(name)){
				k = true;
			}
		}
		
		return k;
	}

}