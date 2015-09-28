package com.starnetmc.ArcadeEngine.Managers.Maps;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.starnetmc.ArcadeCore;
import com.starnetmc.ArcadeEngine.Games.GameType;
import com.starnetmc.ArcadeEngine.Managers.GameState;
import com.starnetmc.ArcadeEngine.Managers.Commands.ArcadeCMDS;
import com.starnetmc.ArcadeEngine.Managers.Maps.MapProperties.MapProperties;
import com.starnetmc.ArcadeEngine.Managers.Maps.MapTypes.BBMap;
import com.starnetmc.ArcadeEngine.Managers.Maps.MapTypes.DSMap;
import com.starnetmc.ArcadeEngine.Managers.Maps.MapTypes.Map;
import com.starnetmc.ArcadeEngine.Managers.Maps.MapTypes.TeamMaps.FourTeamMap;
import com.starnetmc.ArcadeEngine.Managers.Maps.MapTypes.TeamMaps.TwoTeamMap;
import com.starnetmc.ArcadeEngine.Utils.AF;
import com.starnetmc.ArcadeEngine.Utils.All;
import com.starnetmc.ArcadeEngine.Utils.ErrorCatcher;
import com.starnetmc.core.commands.util.CommandBase;
import com.starnetmc.core.util.F;
import com.starnetmc.core.util.Rank;
import com.starnetmc.core.util.UPlayer;
import com.starnetmc.core.util.UString;

public class MapCommand extends CommandBase<ArcadeCMDS> {
	
	public static HashMap<String, Map> creation = new HashMap<String, Map>();
	
	public MapCommand(ArcadeCMDS plugin) {
		super(plugin, Rank.DEVELOPER, new String[] { "map", "m" });
	}
	
	public void execute(Player player, String[] args) {
		
		Player p = player;
		
		if (args.length == 0){
			listCMDS(p);
			return;
		}
		
		if (args[0].equalsIgnoreCase("create")){
			
			if (args.length <  4){
	               listCMDS(p);
	               return;
			}
			
			if (!validateMapType(args[1])){
				p.sendMessage(AF.map("Woah there..that's not a recognizable MapType!"));
				p.sendMessage(AF.map("Here you go..."));
				listMapTypes(p);
				return;
			}
			
			if (!validateVarName(args[2])){
				p.sendMessage(AF.map("Hey there..that's not a recognizable var_name! So be careful."));
				p.sendMessage(AF.map("Here you go just for refresher's if you didn't mean to put it..."));
				listVarNames(p);
			}
			
			MapType type = MapType.getMapTypeFromString(args[1]);
			String name = UString.forgeArgMessage(args, 3);
			
			if (type == MapType.DEFAULT){
				List<Location> lox = new ArrayList<Location>();
				Map map = new Map(name, null, args[2], lox, null, 0, null, 0, new MapProperties());
				
				if (creation.containsKey(p.getName())){
					creation.remove(p.getName());
				}
				creation.put(p.getName(), map);
				p.sendMessage(AF.map("Map Creation started with the MapType: " + args[1] + " and var_name: " + args[2]));
				return;
			}
			
			if (type == MapType.TEAMMAP_TWO){
				List<Location> lox1 = new ArrayList<Location>();
				List<Location> lox2 = new ArrayList<Location>();
				TwoTeamMap map = new TwoTeamMap(name, null, args[2], null, null, 0, lox1, lox2, null, 0, new MapProperties());
				
				if (creation.containsKey(p.getName())){
					creation.remove(p.getName());
				}
				creation.put(p.getName(), map);
				p.sendMessage(AF.map("Map Creation started with the MapType: " + args[1] + " and var_name: " + args[2]));
				return;
			}
			
			if (type == MapType.TEAMMAP_FOUR){
				List<Location> lox1 = new ArrayList<Location>();
				List<Location> lox2 = new ArrayList<Location>();
				List<Location> lox3 = new ArrayList<Location>();
				List<Location> lox4 = new ArrayList<Location>();
				FourTeamMap map = new FourTeamMap(name, null, args[2], null, null, 0, lox1, lox2, lox3, lox4, null, 0, new MapProperties());
				
				if (creation.containsKey(p.getName())){
					creation.remove(p.getName());
				}
				creation.put(p.getName(), map);
				p.sendMessage(AF.map("Map Creation started with the MapType: " + args[1] + " and var_name: " + args[2]));
				return;
			}
			
			if (type == MapType.DSMAP){
				List<Location> lox = new ArrayList<Location>();
				DSMap map = new DSMap(name, null, args[2], lox, null, null, null, 0, null, 0, new MapProperties());
				
				if (creation.containsKey(p.getName())){
					creation.remove(p.getName());
				}
				
				creation.put(p.getName(), map);
				p.sendMessage(AF.map("Map Creation started with the MapType: " + args[1] + " and var_name: " + args[2]));
				return;
			}
			
			if (type == MapType.BBMAP){
				List<Location> lox = new ArrayList<Location>();
				List<Location> zones = new ArrayList<Location>();
				BBMap map = new BBMap(name, null, args[2], lox, null, zones, 0, null, 0, new MapProperties());
				
				if (creation.containsKey(p.getName())){
					creation.remove(p.getName());
				}
				
				creation.put(p.getName(), map);
				p.sendMessage(AF.map("Map Creation started with the MapType: " + args[1] + " and var_name: " + args[2]));
				return;
			}
			
		}
		
		if (args[0].equalsIgnoreCase("set")){
			
			if (args.length <  2){
	               listCMDS(p);
	               return;
			}
			
			//Default Map setup
			if (args[1].equalsIgnoreCase("Name")){
				
				if (args.length <  3){
		               listCMDS(p);
		               return;
				}
				
				String name = UString.forgeArgMessage(args, 2);
				p.sendMessage(AF.map("Name set as: §e" + name + " §aFor Map:§e " + creation.get(p.getName()).getName()));
				creation.get(p.getName()).setName(name);
				return;
			}
			
			if (args[1].equalsIgnoreCase("Author") || args[1].equalsIgnoreCase("Maker")){
				
				if (args.length <  3){
		               listCMDS(p);
		               return;
				}
				
				String author = UString.forgeArgMessage(args, 2);
				creation.get(p.getName()).setMaker(author);
				p.sendMessage(AF.map("Author set as: §e" + author + " §aFor Map:§e " + creation.get(p.getName()).getName()));
				return;
			}
			
			if (args[1].equalsIgnoreCase("Spec") || args[1].equalsIgnoreCase("SpecSpawn")){
				
				Location specS = p.getLocation();
				creation.get(p.getName()).setSpecSpawn(specS);
				p.sendMessage(AF.map("SpecSpawn set as§e your location" + " §aFor Map:§e " + creation.get(p.getName()).getName()));
				return;
			}
			
			if (args[1].equalsIgnoreCase("Spawn")){
				
				Location spawn = p.getLocation();
				creation.get(p.getName()).addSpawn(spawn);
				p.sendMessage(AF.map("Added Spawn from §eyour location" + " §aTo Map:§e " + creation.get(p.getName()).getName()));
				return;
			}
			
			if (args[1].equalsIgnoreCase("Center") || args[1].equalsIgnoreCase("BorderCenter") || args[1].equalsIgnoreCase("BCenter")){
			
				Location cLoc = p.getLocation();
				creation.get(p.getName()).setCenter(cLoc);
				p.sendMessage(AF.map("Set Map Center from §eyour Location" + " §aFor Map:§e " + creation.get(p.getName()).getName()));
			    return;	
			}
			
			if (args[1].equalsIgnoreCase("BRad") || args[1].equalsIgnoreCase("BorderRadius")){
				
				if (args.length < 3){
					listCMDS(p);
					return;
				}
				
				try{
				double rad = Double.parseDouble(args[2]);
				creation.get(p.getName()).setBRad(rad);
				p.sendMessage(AF.map("Set Border Radius to §e" + rad + " §aFor Map:§e " + creation.get(p.getName()).getName()));
				return;
				} catch (NumberFormatException e){
					p.sendMessage(AF.error("Please use a number."));
					return;
				}
			}
			
			if (args[1].equalsIgnoreCase("var_name")){
				
				if (args.length < 3){
					listCMDS(p);
					return;
				}
				
				creation.get(p.getName()).setVarName(args[2]);
				p.sendMessage(AF.map("Set var_name to §e" + args[2] + " §aFor Map:§e " + creation.get(p.getName()).getName()));
				return;
			}
			
			if (args[1].equalsIgnoreCase("Rain")){
				
				if (args.length < 3){
					listCMDS(p);
					return;
				}
				
				if (args[2].equalsIgnoreCase("true")){
				    creation.get(p.getName()).getProperties().setRain(true);
				    p.sendMessage(AF.map("Set Rain Property to §e" + args[2] + " §aFor Map:§e " + creation.get(p.getName()).getName()));
				    return;
				} else
					
				if (args[2].equalsIgnoreCase("false")){
					creation.get(p.getName()).getProperties().setRain(false);
				    p.sendMessage(AF.map("Set Rain Property to §e" + args[2] + " §aFor Map:§e " + creation.get(p.getName()).getName()));
					return;
				} else {
					p.sendMessage(AF.error("Only a true/false reponse is accepted."));
				}
			}
			
			if (args[1].equalsIgnoreCase("Time")){
				
				if (args.length < 3){
					listCMDS(p);
					return;
				}
				
				try { 
					
					creation.get(p.getName()).getProperties().setTime(Long.parseLong(args[2]));
				    p.sendMessage(AF.map("Set Time Property to §e" + args[2] + " §aFor Map:§e " + creation.get(p.getName()).getName()));
					return;
					
				   } catch(NumberFormatException e) {
					   p.sendMessage(AF.error("Please use an number(long)."));
					   return;
				   }
			}
			
			//TwoTeamMap setup
			if (args[1].equalsIgnoreCase("FirstTSpawn") | args[1].equalsIgnoreCase("1TSpawn")){
				if (!(creation.get(p.getName()) instanceof TwoTeamMap) || !(creation.get(p.getName()) instanceof FourTeamMap)){
					p.sendMessage(AF.map("§cThe MapType for this map is not the TeamMap_Two or TeamMap_Four MapType. Therefore this parameter cannot be used."));
					return;
				}
				
				if (creation.get(p.getName()) instanceof TwoTeamMap){
				  TwoTeamMap map = (TwoTeamMap) creation.get(p.getName());
				  map.addFTSpawn(p.getLocation());
				  p.sendMessage(AF.map("Added TeamSpawn for the first team from §eyour location" + " §aTo Map:§e " + creation.get(p.getName()).getName()));
				  return;
				}
				
				if (creation.get(p.getName()) instanceof FourTeamMap){
				  FourTeamMap map = (FourTeamMap) creation.get(p.getName());
				  map.addFTSpawn(p.getLocation());
				  p.sendMessage(AF.map("Added TeamSpawn for the first team from §eyour location" + " §aTo Map:§e " + creation.get(p.getName()).getName()));
				  return;
			    }
			}
			
			if (args[1].equalsIgnoreCase("SecondTSpawn") || args[1].equalsIgnoreCase("2TSpawn")){
				if (!(creation.get(p.getName()) instanceof TwoTeamMap) || !(creation.get(p.getName()) instanceof FourTeamMap)){
					p.sendMessage(AF.map("§cThe MapType for this map is not the TeamMap_Two or TeamMap_Four MapType. Therefore this parameter cannot be used."));
					return;
				}
				
				if (creation.get(p.getName()) instanceof TwoTeamMap){
					  TwoTeamMap map = (TwoTeamMap) creation.get(p.getName());
					  map.addSTSpawn(p.getLocation());
					  p.sendMessage(AF.map("Added TeamSpawn for the second team from §eyour location" + " §aTo Map:§e " + creation.get(p.getName()).getName()));
					  return;
					}
					
				if (creation.get(p.getName()) instanceof FourTeamMap){
					  FourTeamMap map = (FourTeamMap) creation.get(p.getName());
					  map.addSTSpawn(p.getLocation());
					  p.sendMessage(AF.map("Added TeamSpawn for the second team from §eyour location" + " §aTo Map:§e " + creation.get(p.getName()).getName()));
					  return;
				    }
			}
			
			//FourTeamMap setup
			if (args[1].equalsIgnoreCase("ThirdTSpawn") || args[1].equalsIgnoreCase("3TSpawn")){
				if (!(creation.get(p.getName()) instanceof FourTeamMap)){
					p.sendMessage(AF.map("§cThe MapType for this map is not the TeamMap_Four MapType. Therefore this parameter cannot be used."));
					return;
				}
				
					  FourTeamMap map = (FourTeamMap) creation.get(p.getName());
					  map.addTTSpawn(p.getLocation());
					  p.sendMessage(AF.map("Added TeamSpawn for the third team from §eyour location" + " §aTo Map:§e " + creation.get(p.getName()).getName()));
					  return;
			}
			
			if (args[1].equalsIgnoreCase("FourthTSpawn") || args[1].equalsIgnoreCase("4TSpawn")){
				if (!(creation.get(p.getName()) instanceof FourTeamMap)){
					p.sendMessage(AF.map("§cThe MapType for this map is not the TeamMap_Four MapType. Therefore this parameter cannot be used."));
					return;
				}
				
					  FourTeamMap map = (FourTeamMap) creation.get(p.getName());
					  map.addFrTSpawn(p.getLocation());
					  p.sendMessage(AF.map("Added TeamSpawn for the fourth team from §eyour location" + " §aTo Map:§e " + creation.get(p.getName()).getName()));
					  return;
			}
				
			
			//DSMap setup
			if (args[1].equalsIgnoreCase("DSwordSpawn")){
				if (!(creation.get(p.getName()) instanceof DSMap)){
					p.sendMessage(AF.map("§cThe MapType for this map is not the DSMap MapType. Therefore this parameter cannot be used."));
					return;
				}
				
				DSMap map = (DSMap) creation.get(p.getName());
				Location sLoc = p.getLocation();
				map.setSwordSpawn(sLoc);
				p.sendMessage(AF.map("Set Sword Spawn to §eYour Location §aFor Map:§e " + creation.get(p.getName()).getName()));
				return;
			}
			
			if (args[1].equalsIgnoreCase("DSPortalSpawn")){
				if (!(creation.get(p.getName()) instanceof DSMap)){
					p.sendMessage(AF.map("§cThe MapType for this map is not the DSMap MapType. Therefore this parameter cannot be used."));
					return;
				}
				
				DSMap map = (DSMap) creation.get(p.getName());
				Location pLoc = p.getLocation();
				map.setPortalSpawn(pLoc);
				p.sendMessage(AF.map("Set Portal Spawn to §eYour Location §aFor Map:§e " + creation.get(p.getName()).getName()));
				return;
			}
			
			//BBMap Setup
			if (args[1].equalsIgnoreCase("SafeZone")){
				
				Location zone = p.getLocation();
				BBMap map = (BBMap) creation.get(p.getName());
				map.addSafeZone(zone);
				p.sendMessage(AF.map("Added SafeZone from §eyour location" + " §aTo Map:§e " + creation.get(p.getName()).getName()));
				return;
			}
		}
		
		if (args[0].equalsIgnoreCase("modify")){
			
			if (args.length < 3){
				listCMDS(p);
				return;
			}
			
			Map m;
			if (ArcadeCore.getArcadeManager().getGame().getGameTypeFromVarName(args[1]) != null){
			m = ArcadeCore.getArcadeManager().getMapper().forgeMapFromConfig(ArcadeCore.getArcadeManager().getGame().getGameTypeFromVarName(args[1]), args[2]);
			} else {
				
				 if (args.length < 4){
					  listCMDS(p);
					  return;
				  }
					
					if (!validateMapType(args[3])){
						p.sendMessage(AF.map("Woah there..that's not a recognizable MapType!"));
						p.sendMessage(AF.map("Here you go..."));
						listMapTypes(p);
						return;
					}
					
					m = ArcadeCore.getArcadeManager().getMapper().forgeMapFromConfig(args[1], MapType.getMapTypeFromString(args[3]), args[2]);
			}
			
			creation.put(p.getName(), m);
			p.sendMessage(AF.map("Map §e" + creation.get(p.getName()).getName() + " §aHas been put into editing mode, use /set to modify it."));
			return;
		}
		
		if (args[0].equalsIgnoreCase("finish")){
			
			if (args.length > 1){
				listCMDS(p);
				return;
			}
			
			if (creation.get(p.getName()) == null){
				p.sendMessage(AF.error("You do not have a map in a creation state."));
			}
			
			if (ErrorCatcher.debugMap(creation.get(p.getName())).size() == 0){

				creation.get(p.getName()).register(false);
				p.sendMessage(AF.map("Map §e" + creation.get(p.getName()).getName() + " §aHas been successfully registered."));
				creation.remove(p.getName());
				return;
			} else {
				Iterator<String> eIt = ErrorCatcher.debugMap(creation.get(p.getName())).iterator();
				while(eIt.hasNext()) {
					p.sendMessage(AF.error(eIt.next())); 
				}
			  }
		}
		
		if (args[0].equalsIgnoreCase("finmod")){
			
			if (args.length > 1){
				listCMDS(p);
				return;
			}
			
			if (creation.get(p.getName()) == null){
				p.sendMessage(AF.error("You do not have a map in a modification state."));
			}
			
			if (ErrorCatcher.debugMap(creation.get(p.getName())).size() == 0){

				creation.get(p.getName()).register(true);
				p.sendMessage(AF.map("Map §e" + creation.get(p.getName()).getName() + " §aHas been successfully modified."));
				creation.remove(p.getName());
				return;
			} else {
				Iterator<String> eIt = ErrorCatcher.debugMap(creation.get(p.getName())).iterator();
				while(eIt.hasNext()) {
					p.sendMessage(AF.error(eIt.next())); 
				}
			  }
		}
		
		if (args[0].equalsIgnoreCase("list")){
			
			if (ArcadeCore.getArcadeManager().getGame().getGameTypeFromVarName(args[1]) != null){
			p.sendMessage(AF.listTop("Maps | " + ArcadeCore.getArcadeManager().getGame().getGameTypeFromVarName(args[1]).getName()));
			
			for (Map m : ArcadeCore.getArcadeManager().getMapper().getAllMaps(ArcadeCore.getArcadeManager().getGame().getGameTypeFromVarName(args[1]))){
				p.sendMessage(AF.mapList(m.getID(), m.getName(), m.getMaker()));
			}
			return;
			
		  } else if (args.length < 3){
			  listCMDS(p);
			  return;
		  }
			
			if (!validateMapType(args[2])){
				p.sendMessage(AF.map("Woah there..that's not a recognizable MapType!"));
				p.sendMessage(AF.map("Here you go..."));
				listMapTypes(p);
				return;
			}
			
			p.sendMessage(AF.listTop("Maps | " + args[1]));
			
			for (Map m : ArcadeCore.getArcadeManager().getMapper().getAllMaps(args[1], MapType.getMapTypeFromString(args[2]))){
				p.sendMessage(AF.mapList(m.getID(), m.getName(), m.getMaker()));
			}
			return;
			
		}
		
		if (args[0].equalsIgnoreCase("listmaptypes") || args[0].equalsIgnoreCase("listtypes") || args[0].equalsIgnoreCase("types")){
			listMapTypes(p);
			return;
		}
		
		if (args[0].equalsIgnoreCase("listvarnames") || args[0].equalsIgnoreCase("listvars") || args[0].equalsIgnoreCase("vars")){
			listVarNames(p);
			return;
		}
		
		if (args[0].equalsIgnoreCase("listparameters") || args[0].equalsIgnoreCase("parameters")  || args[0].equalsIgnoreCase("params")){
			
			if (args.length < 2){
	               listCMDS(p);
	               return;
			}
			
			if (!validateMapType(args[1])){
				p.sendMessage(AF.map("Woah there..that's not a recognizable MapType!"));
				p.sendMessage(AF.map("Here you go..."));
				listMapTypes(p);
				return;
			}
			
			listParams(p, args[1]);
			return;
		}
	   if (args[0].equalsIgnoreCase("listguidelines")  || args[0].equalsIgnoreCase("guidelines")){
		   
		   if (args.length > 1){
			  if (UPlayer.isOnline(args[1])){
				  Player t = UPlayer.getOnlinePlayerFromName(args[1]);
				  t.sendMessage(AF.map(p.getName() + " Has listed the Map Guidelines for you!"));
				  listGuidelines(t);
				  p.sendMessage(AF.map("Map Guidelines listed for " + t.getName()));
				  return;
			  } else {
				  p.sendMessage(F.error("PlayerFinder", args[1] + " is not Online."));
				  return;
			  }
		   }
		   
		   listGuidelines(p);
		   return;
	   }
		
	   if (args[0].equalsIgnoreCase("setcurrent")){
		   
		   if (!ArcadeCore.getArcadeManager().isState(GameState.LOBBY)){
			   p.sendMessage(AF.map("Cannot change map while the game is loading/in progress. Stop game to do so."));
			   return;
		   }
		   
			if (args.length < 2){
	               listCMDS(p);
	               return;
			}
		   
		try { 
			
			ArcadeCore.getArcadeManager().getMapper().setMap(p, ArcadeCore.getArcadeManager().getGame(), Integer.parseInt(args[1]));
			return;
			
		   } catch(NumberFormatException e) {
			   p.sendMessage(AF.error("Please use an integer."));
			   return;
		   }
		}
	   
	   if (args[0].equalsIgnoreCase("setcustom")){
		   
		   if (!ArcadeCore.getArcadeManager().isState(GameState.LOBBY)){
			   p.sendMessage(AF.map("Cannot change map while the game is loading/in progress. Stop game to do so."));
			   return;
		   }
		   
			if (args.length < 4){
	               listCMDS(p);
	               return;
			}
		   
		try {
			
			if (!validateMapType(args[2])){
				p.sendMessage(AF.map("Woah there..that's not a recognizable MapType!"));
				p.sendMessage(AF.map("Here you go..."));
				listMapTypes(p);
				return;
			}
			
			ArcadeCore.getArcadeManager().getMapper().setMap(p, args[1], MapType.getMapTypeFromString(args[2]), Integer.parseInt(args[3]));
			return;
			
		   } catch(NumberFormatException e) {
			   p.sendMessage(AF.error("Please use an integer."));
			   return;
		   }
		}
	   
	   if (args[0].equalsIgnoreCase("load")){
		   
		   if (args.length < 2){
			   listCMDS(p);
			   return;
		   }
		   
		   String worldName = args[1];
		   
		   p.sendMessage(AF.map("Attempting to load a Map with the world name: " + worldName + "..."));
		   MapLoader.copyWorld(new File(MapLoader.mapDir + worldName),
				     new File(ArcadeCore.getPlugin().getServer().getWorldContainer().getAbsolutePath() + "/" + worldName));			
		   
		   MapLoader.loadWorld(worldName);
		   p.sendMessage(AF.map("The Map associated to the world name: " + worldName + " has been sucessfully loaded."));
		   return;
	   }
	   
	   if (args[0].equalsIgnoreCase("unload")){
		   
		   if (args.length < 2){
			   listCMDS(p);
			   return;
		   }
		   
		   String worldName = args[1];
		   
		   p.sendMessage(AF.map("Attempting to unload a Map with the world name: " + worldName + "..."));
		   
		   if (Bukkit.getServer().getWorld(args[1]) == null){
			   p.sendMessage(AF.map("No map with the world name: " + worldName + " is currently loaded."));
			   return;
		   }
		   
		   World mapWorld = Bukkit.getServer().getWorld(worldName);
		   
	       MapLoader.unloadWorld(mapWorld);
	       MapLoader.deleteWorld(new File(ArcadeCore.getPlugin().getServer().getWorldContainer().getAbsolutePath() + "/" + mapWorld.getName()));
		   p.sendMessage(AF.map("The Map associated with the world name: " + worldName + " has been sucessfully unloaded."));
		   return;
	   }
			
		
	}
	
	private void listCMDS(Player p){
		p.sendMessage(AF.listTop("Maps"));
		p.sendMessage(AF.cmdList("/Map Create <MapType> <var_name> <MapName>", "Starts Map creation for a certain MapType for a certain var_name", F.boldDP + "DEVELOPER"));
		p.sendMessage(AF.cmdList("/Map Set <Parameters> [Value]", "Used for setting Map parameters", F.boldDP + "DEVELOPER"));
		p.sendMessage(AF.cmdList("/Map Modify <var_name> <MapID> [MapType]", "Modifies an existing Map. *When using a custom var_name specify the MapType", F.boldDP + "DEVELOPER"));
		p.sendMessage(AF.cmdList("/Map Finish", "Finish's Map setup", F.boldDP + "DEVELOPER"));
		p.sendMessage(AF.cmdList("/Map FinMOD", "Finish's Map modification[DO NOT USE /MAP FINISH]", F.boldDP + "DEVELOPER"));
		p.sendMessage(AF.cmdList("/Map List <var_name> [MapType]", "Lists all Maps and their ID's that are associated with the var_name. *When using a custom var_name specify the MapType", F.boldDP + "DEVELOPER"));
		p.sendMessage(AF.cmdList("/Map ListMapTypes", "Lists all the useable MapTypes.", F.boldDP + "DEVELOPER"));
		p.sendMessage(AF.cmdList("/Map ListVarNames", "Lists the var_name's associated with each GameType.", F.boldDP + "DEVELOPER"));
		p.sendMessage(AF.cmdList("/Map ListParameters <MapType>", "Lists all the possible parameter's associated with the MapType.", F.boldDP + "DEVELOPER"));
		p.sendMessage(AF.cmdList("/Map ListGuidelines [Player]", "Lists the guidelines and rules for building/creating Maps for you or the player specified..", F.boldDP + "DEVELOPER"));
		p.sendMessage(AF.cmdList("/Map SetCurrent <MapID>", "Sets the map for the current game with the id given.", F.boldDP + "DEVELOPER"));
		p.sendMessage(AF.cmdList("/Map SetCustom <var_name> <MapType> <MapID>", "Sets a custom map for the custom var_name, MapType, and id given.", F.boldDP + "DEVELOPER"));
		p.sendMessage(AF.cmdList("/Map Load <MapName>", "Loads a Map file from the /Maps/ directory. *SHOULD BE USED WITH CAUTION", F.boldDP + "DEVELOPER"));
		p.sendMessage(AF.cmdList("/Map UnLoad <MapName>", "UnLoads a currently loaded Map file. *SHOULD BE USED WITH CAUTION", F.boldDP + "DEVELOPER"));
	}
	
	private void listMapTypes(Player p){
		p.sendMessage(AF.listTop("MapTypes"));
		for (MapType type : All.getAllMapTypes()){
			p.sendMessage(ChatColor.YELLOW + "" + ChatColor.ITALIC + UString.UppercaseFirstLetter(type.toString()));
		}
	}
	
	private void listVarNames(Player p){
		p.sendMessage(AF.listTop("VarNames"));
		p.sendMessage(AF.boldYellow + "<Format> " + ChatColor.RESET + ChatColor.YELLOW + "GameType|GameTypeEnumName" + AF.gold + " - " + ChatColor.YELLOW + "var_name");
		
		for (Entry<GameType, String> entry : All.getAllVarNames().entrySet()){
			p.sendMessage(ChatColor.YELLOW + entry.getKey().getName() + "|" + entry.getKey().toString() + AF.gold + " - " + ChatColor.YELLOW + entry.getValue());
		}
		
		p.sendMessage(ChatColor.YELLOW + "" + ChatColor.ITALIC + "*Basically the enum name in lowercase");
		p.sendMessage(ChatColor.YELLOW + "" + ChatColor.ITALIC + "Custom var_name's can be used for registration as well.");
		p.sendMessage(AF.boldRed + "PROCEED WITH CAUTION WHEN USING CUSTOM VAR_NAME'S");
	}
	
	private void listGuidelines(Player p){
		p.sendMessage(AF.listTop("Map Creation Guidelines"));
		p.sendMessage(AF.boldAqua + "> " + AF.yellow + "Maps must be created in an empty/void world.");
		p.sendMessage(AF.boldAqua + "> " + AF.yellow + "Maps must also be created at 0,0. Mainly the Y axis.");
		p.sendMessage(AF.boldAqua + "> " + AF.yellow + "After Map building is done. The map should be stored in the Maps directory of the plugin folder.");
		p.sendMessage(AF.boldAqua + "> " + AF.yellow + "Maps files can then be loaded for registration on the Star MapSystem with the World Management System(/World)");
		p.sendMessage(AF.boldAqua + "> " + AF.yellow + "Once Map registration is done. Please unload the world using the World Management System(/World)");
		p.sendMessage(AF.boldAqua + "> " + AF.yellow + "Congratulations! The system will now do everything else automatically such as loading, randomly selecting, and restoring the Maps!");
	}
	
	private void listParams(Player p, String sMapType){
		MapType mapType = MapType.getMapTypeFromString(sMapType);
		
			p.sendMessage(AF.listTop(mapType.toString() + " MapType Params"));
			for (String param : mapType.getParams()){
				p.sendMessage(ChatColor.YELLOW + "- " + param);
		}
	}
	
	private boolean validateMapType(String maptype){
		boolean k = false;
		
		for (MapType type : All.getAllMapTypes()){
			if (maptype.equalsIgnoreCase(type.toString())){
				k = true;
			}
		}
		
		return k;
	}
	
	private boolean validateVarName(String var_name){
		boolean k = false;
		
		for (Entry<GameType, String> entry : All.getAllVarNames().entrySet()){
			if (var_name.trim().equalsIgnoreCase(entry.getValue())){
				k = true;
			}
		}
		
		return k;
	}

}