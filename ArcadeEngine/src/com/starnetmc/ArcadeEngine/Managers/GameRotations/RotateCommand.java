package com.starnetmc.ArcadeEngine.Managers.GameRotations;

import org.bukkit.entity.Player;

import com.starnetmc.ArcadeCore;
import com.starnetmc.ArcadeEngine.Managers.Commands.ArcadeCMDS;
import com.starnetmc.ArcadeEngine.Utils.AF;
import com.starnetmc.core.commands.util.CommandBase;
import com.starnetmc.core.util.Rank;

public class RotateCommand extends CommandBase<ArcadeCMDS> {

	public RotateCommand(ArcadeCMDS plugin) {
		super(plugin, Rank.ADMIN, new String[] { "rotate", "gamerotate", "shuffle" });
	}
	
	public void execute(Player player, String[] args) {
		Player p = player;
		
		if (args.length == 0){
			ArcadeCore.getArcadeManager().getGameRotator().rotate();
			AF.broadcast(p.getName() + " Has Rotated the Game!");
			return;
		}
		
		if (args[0].equalsIgnoreCase("help")){
			listCMDS(p);
			return;
		}
		
		if (args[0].equalsIgnoreCase("enable")){
			if (args.length == 1){
				ArcadeCore.getArcadeManager().getGameRotator().setActive(true, true);
				p.sendMessage(AF.game("GameRotator has been permanently enabled on this server."));
				return;
				
			} else if (args.length == 2){
				if (args[1].equalsIgnoreCase("-temp") || args[1].equalsIgnoreCase("-t")){
					ArcadeCore.getArcadeManager().getGameRotator().setActive(true);
					p.sendMessage(AF.game("GameRotator has been temporarly enabled on this server."));
					return;
				} else { listCMDS(p); return; }
			}
		}
		
		if (args[0].equalsIgnoreCase("disable")){
			if (args.length == 1){
				ArcadeCore.getArcadeManager().getGameRotator().setActive(false, true);
				p.sendMessage(AF.game("GameRotator has been permanently disabled on this server."));
				return;
				
			} else if (args.length == 2){
				if (args[1].equalsIgnoreCase("-temp") || args[1].equalsIgnoreCase("-t")){
					ArcadeCore.getArcadeManager().getGameRotator().setActive(false);
					p.sendMessage(AF.game("GameRotator has been temporarly disabled on this server."));
					return;
				} else { listCMDS(p); return; }
			}
		}
	}
	
	private void listCMDS(Player p){
		p.sendMessage(AF.listTop("Game Rotation"));
		p.sendMessage(AF.cmdList("/Rotate", "Rotates/Shuffles the game to a new and random one.", "§c ADMIN"));
		p.sendMessage(AF.cmdList("/Rotate Enable [-temp]", "Enables the GameRotator. If the -temp flag is used the change will not be saved in the config.", "§c ADMIN"));
		p.sendMessage(AF.cmdList("/Rotate Disable [-temp]", "Enables the GameRotator. If the -temp flag is used the change will not be saved in the config.", "§c ADMIN"));
		p.sendMessage(AF.cmdList("/Rotate Help", "Display's the Help(this) menu for the Rotate Command.", "§c ADMIN"));
	}

}