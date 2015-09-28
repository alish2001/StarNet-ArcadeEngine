package com.starnetmc.ArcadeEngine.Managers.GameProperties;

import org.bukkit.entity.Player;

import com.starnetmc.ArcadeCore;
import com.starnetmc.ArcadeEngine.Managers.Commands.ArcadeCMDS;
import com.starnetmc.ArcadeEngine.Utils.AF;
import com.starnetmc.core.commands.util.CommandBase;
import com.starnetmc.core.util.F;
import com.starnetmc.core.util.Rank;

public class GamePropertiesManagerControlerCommand extends CommandBase<ArcadeCMDS> {
	
	public GamePropertiesManagerControlerCommand(ArcadeCMDS plugin) {
		super(plugin, Rank.DEVELOPER, new String[] { "gameproperties", "properties", "gp" });
	}
	
	public void execute(Player player, String[] args) {
		Player p = player;
		
		if (args.length == 0){
			listCMDS(p);
			return;
		}
		
		if (args[0].equalsIgnoreCase("ListActiveManagers") || args[0].equalsIgnoreCase("ListActivePropertiesManagersManagers")){
			p.sendMessage(AF.listTop("Active PropertiesManagers"));
			
			for (PropertiesManager manager : ArcadeCore.getArcadeManager().getPropertiesManager().getPropertiesManagers()){
			    p.sendMessage(AF.yellow + "-    " + manager.getName());
			}
			
			return;
		}
		
		if (args[0].equalsIgnoreCase("Reload") || args[0].equalsIgnoreCase("Restart")){
			ArcadeCore.getArcadeManager().getPropertiesManager().loadDefaultPropertiesManagers();
			p.sendMessage(AF.properties("Reloaded with default properties managers."));
			return;
		}
		
		if (args[0].equalsIgnoreCase("UnLoad")){
			if (args.length < 2){
				listCMDS(p);
				return;
			}
			
			if (!validatePropertiesManager(args[1])){
				p.sendMessage(AF.properties("WOOPS! That PropertiesManager is not currently loaded!"));
				p.sendMessage(AF.properties("Here are the currently loaded PropertiesManagers..."));
				p.sendMessage(AF.listTop("Active PropertiesManagers"));
				
				for (PropertiesManager manager : ArcadeCore.getArcadeManager().getPropertiesManager().getPropertiesManagers()){
				    p.sendMessage(AF.yellow + "-    " + manager.getName());
				}
				return;
			}
			
			String pmName = args[1];
			ArcadeCore.getArcadeManager().getPropertiesManager().removePropertiesManager(pmName);
			
			p.sendMessage(AF.properties("Sucessfully Unloaded PropertiesManager: " + pmName));
			return;
		}
		
		if (args[0].equalsIgnoreCase("ListPropertiesFlags") || args[0].equalsIgnoreCase("ListFlags")){
			AF.gPropertiesInfo(ArcadeCore.getArcadeManager().getPropertiesManager().getProperties(), p);
			return;
		}
	}
	
	private void listCMDS(Player p){
		p.sendMessage(AF.listTop("GameProperties Controler"));
		p.sendMessage(AF.cmdList("/Properties ListActiveManagers", "Lists currently enabled PropertiesManagers.", F.boldDP + "DEVELOPER"));
		p.sendMessage(AF.cmdList("/Properties Reload", "Reloads the GamePropertiesManager with the default PropertiesManagers.", F.boldDP + "DEVELOPER"));
		p.sendMessage(AF.cmdList("/Properties UnLoad <PropertiesManager>", "UnLoads a PropertiesManager from GameProperties.", F.boldDP + "DEVELOPER"));
		p.sendMessage(AF.cmdList("/Properties ListPropertiesFlags", "Lists the current Properties file's important flags. Useful for debugging.", F.boldDP + "DEVELOPER"));
	}
	
	
	private boolean validatePropertiesManager(String name){
		boolean k = false;
		
		for (PropertiesManager pm : ArcadeCore.getArcadeManager().getPropertiesManager().getPropertiesManagers()){
			if (name.equalsIgnoreCase(pm.getName())){
				k = true;
			}
		}
		
		return k;
	}

}