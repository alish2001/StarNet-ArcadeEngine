package com.starnetmc.ArcadeEngine.Managers.Commands;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.starnetmc.ArcadeCore;
import com.starnetmc.ArcadeEngine.Managers.Scoreboards.StarBoards.DebugScoreboard;
import com.starnetmc.ArcadeEngine.Utils.AF;
import com.starnetmc.ArcadeEngine.Utils.Logger;
import com.starnetmc.ArcadeEngine.Utils.USound;
import com.starnetmc.core.commands.util.CommandBase;
import com.starnetmc.core.util.F;
import com.starnetmc.core.util.Rank;
import com.starnetmc.core.util.UPlayer;
import com.starnetmc.core.util.UtilPacket;

public class DebugCMD extends CommandBase<ArcadeCMDS> {
	
	public DebugCMD(ArcadeCMDS plugin) {
		super(plugin, Rank.DEVELOPER, new String[] { "debug", "dbg" });
	}
	
	public void execute(Player player, String[] args) {
		Player p = player;
		
		if (args.length < 1){
			listCMDS(p);
			return;
		}
		
		if (args[0].equalsIgnoreCase("board") || args[0].equalsIgnoreCase("scoreboard") || args[0].equalsIgnoreCase("starboard") || args[0].equalsIgnoreCase("b")){
			  
			   if (args.length > 1){
					  if (UPlayer.isOnline(args[1])){
						  Player t = UPlayer.getOnlinePlayerFromName(args[1]);

						
							if (!(ArcadeCore.getArcadeManager().getScoreboardManager().getCustomScoreboardUsers().containsKey(t.getName()))) {
								  
								  ArcadeCore.getArcadeManager().getScoreboardManager().addCustomScoreboardUser(t, new DebugScoreboard());
								  p.sendMessage(AF.debug("Enabled Debug Board for " + t.getName()));
								  t.sendMessage(AF.debug(p.getName() + " Has Enabled the Debug Board for you!"));
								  UtilPacket.sendActionBarMessage(t, AF.boldYellow + "Loading debug board...");
								  USound.PSound(t, Sound.ORB_PICKUP, 2.25f, 1.25f);
								  return;
								  
							   } else {
								   
									  ArcadeCore.getArcadeManager().getScoreboardManager().removeCustomScoreboardUser(t);
									  p.sendMessage(AF.debug("§cDisabled Debug Board for " + t.getName()));
									  t.sendMessage(AF.debug(p.getName() + " Has Disabled the Debug Board for you!"));
									  UtilPacket.sendActionBarMessage(t, AF.boldRed + "Disabling debug board...");
									  USound.PSound(t, Sound.ORB_PICKUP, -2.25f, 1.25f);
									  return;
							   }
			
					  } else {
						  p.sendMessage(F.error("PlayerFinder", args[1] + " is not Online."));
						  return;
					  }
				   }
			
			if (!(ArcadeCore.getArcadeManager().getScoreboardManager().getCustomScoreboardUsers().containsKey(p.getName()))){
				  UtilPacket.sendActionBarMessage(p, AF.boldYellow + "Loading debug board...");
				  ArcadeCore.getArcadeManager().getScoreboardManager().addCustomScoreboardUser(p, new DebugScoreboard());
				  USound.PSound(p, Sound.ORB_PICKUP, 2.25f, 1.25f);
				  return;
			   } else {
					  UtilPacket.sendActionBarMessage(p, AF.boldRed + "Disabling debug board...");
					  ArcadeCore.getArcadeManager().getScoreboardManager().removeCustomScoreboardUser(p);
					  USound.PSound(p, Sound.ORB_PICKUP, -2.25f, 1.25f);
					  return;
			   }
		}
		
		if (args[0].equalsIgnoreCase("logs") || args[0].equalsIgnoreCase("log") || args[0].equalsIgnoreCase("l")){
			
			   if (args.length > 1){
					  if (UPlayer.isOnline(args[1])){
						  Player t = UPlayer.getOnlinePlayerFromName(args[1]);

						
							if (!Logger.isLogee(t)){
								  
								  Logger.addLogee(t);
								  p.sendMessage(AF.debug("Enabled Logs for " + t.getName()));
								  t.sendMessage(AF.debug(p.getName() + " Has Enabled Logs for you!"));
								  UtilPacket.sendActionBarMessage(t, AF.boldYellow + "Logs enabled.");
								  USound.PSound(t, Sound.ORB_PICKUP, 2.25f, 1.25f);
								  return;
							   } else {

									  Logger.removeLogee(t);
								      p.sendMessage(AF.debug("§cDisabled Logs for " + t.getName()));
									  t.sendMessage(AF.debug(p.getName() + " Has Disabled Logs for you!"));
									  UtilPacket.sendActionBarMessage(t, AF.boldRed + "Logs disabled.");
									  USound.PSound(t, Sound.ORB_PICKUP, -2.25f, 1.25f);
									  return;
							   }
			
					  } else {
						  p.sendMessage(F.error("PlayerFinder", args[1] + " is not Online."));
						  return;
					  }
				   }
			
				if (!Logger.isLogee(p)){
					  
					  Logger.addLogee(p);
					  UtilPacket.sendActionBarMessage(p, AF.boldYellow + "Logs enabled.");
					  USound.PSound(p, Sound.ORB_PICKUP, 2.25f, 1.25f);
					  return;
				   } else {
						  Logger.removeLogee(p);
						  UtilPacket.sendActionBarMessage(p, AF.boldRed + "Logs disabled.");
						  USound.PSound(p, Sound.ORB_PICKUP, -2.25f, 1.25f);
						  return;
				   }
	    }
		
		if (args[0].equalsIgnoreCase("full") || args[0].equalsIgnoreCase("all") || args[0].equalsIgnoreCase("f")){
			
			   if (args.length > 1){
					  if (UPlayer.isOnline(args[1])){
						  Player t = UPlayer.getOnlinePlayerFromName(args[1]);

						
							if (!Logger.isLogee(t) && !(ArcadeCore.getArcadeManager().getScoreboardManager().getCustomScoreboardUsers().containsKey(p.getName()))) {
								  
								  ArcadeCore.getArcadeManager().getScoreboardManager().addCustomScoreboardUser(t, new DebugScoreboard());
								  Logger.addLogee(t);
								  p.sendMessage(AF.debug("Enabled Full Debug Mode for " + t.getName()));
								  t.sendMessage(AF.debug(p.getName() + " Has Enabled Full debug mode for you!"));
								  UtilPacket.sendActionBarMessage(t, AF.boldYellow + "Enabling Full debug mode...");
								  USound.PSound(t, Sound.ORB_PICKUP, 2.25f, 1.25f);
								  return;
								  
							   } else if (Logger.isLogee(t) || (ArcadeCore.getArcadeManager().getScoreboardManager().getCustomScoreboardUsers().containsKey(p.getName()))) {
							   
									  ArcadeCore.getArcadeManager().getScoreboardManager().removeCustomScoreboardUser(t);
									  ArcadeCore.getArcadeManager().getScoreboardManager().addCustomScoreboardUser(t, new DebugScoreboard());
									  Logger.removeLogee(t);
									  Logger.addLogee(t);
									  p.sendMessage(AF.debug("Enabled Full Debug Mode for " + t.getName()));
									  t.sendMessage(AF.debug(p.getName() + " Has Enabled Full debug mode for you!"));
									  UtilPacket.sendActionBarMessage(t, AF.boldYellow + "Enabling Full debug mode...");
									  USound.PSound(t, Sound.ORB_PICKUP, 2.25f, 1.25f);
									  return;
								   
							   } else {
								      p.sendMessage(AF.debug("§cDisabled Full Debug Mode for " + t.getName()));
									  t.sendMessage(AF.debug(p.getName() + " Has Disabled Full debug mode for you!"));
									  Logger.removeLogee(t);
									  ArcadeCore.getArcadeManager().getScoreboardManager().removeCustomScoreboardUser(t);
									  UtilPacket.sendActionBarMessage(t, AF.boldRed + "Full debug mode disabled.");
									  USound.PSound(t, Sound.ORB_PICKUP, -2.25f, 1.25f);
									  return;
							   }
			
					  } else {
						  p.sendMessage(F.error("PlayerFinder", args[1] + " is not Online."));
						  return;
					  }
				   }
			
				if (!Logger.isLogee(p) && !(ArcadeCore.getArcadeManager().getScoreboardManager().getCustomScoreboardUsers().containsKey(p.getName()))) {
					  
					  ArcadeCore.getArcadeManager().getScoreboardManager().addCustomScoreboardUser(p, new DebugScoreboard());
					  Logger.addLogee(p);
					  UtilPacket.sendActionBarMessage(p, AF.boldYellow + "Enabling Full debug mode...");
					  USound.PSound(p, Sound.ORB_PICKUP, 2.25f, 1.25f);
					  return;
					  
				   } else if (Logger.isLogee(p) || (ArcadeCore.getArcadeManager().getScoreboardManager().getCustomScoreboardUsers().containsKey(p.getName()))) {
				   
						  ArcadeCore.getArcadeManager().getScoreboardManager().removeCustomScoreboardUser(p);
						  ArcadeCore.getArcadeManager().getScoreboardManager().addCustomScoreboardUser(p, new DebugScoreboard());
						  Logger.removeLogee(p);
						  Logger.addLogee(p);
						  UtilPacket.sendActionBarMessage(p, AF.boldYellow + "Enabling Full debug mode...");
						  USound.PSound(p, Sound.ORB_PICKUP, 2.25f, 1.25f);
						  return;
					   
				   } else {
					      
						  Logger.removeLogee(p);
						  ArcadeCore.getArcadeManager().getScoreboardManager().removeCustomScoreboardUser(p);
						  UtilPacket.sendActionBarMessage(p, AF.boldRed + "Full debug mode disabled.");
						  USound.PSound(p, Sound.ORB_PICKUP, -2.25f, 1.25f);
						  return;
				   }
	    }
	}
	
	private void listCMDS(Player p){
		p.sendMessage(AF.listTop("Debuging"));
		p.sendMessage(AF.cmdList("/Debug Board [Player]", "Enables the Debug Scoreboard for you or the Player specified.", F.boldDP + "DEVELOPER"));
		p.sendMessage(AF.cmdList("/Debug Logs [Player]", "Enables the Debug Logs in Chat for you or the Player specified.", F.boldDP + "DEVELOPER"));
		p.sendMessage(AF.cmdList("/Debug Full [Player]", "Enables the Debug Scoreboard & the Debug Logs for you or the Player specified.", F.boldDP + "DEVELOPER"));
	}

}