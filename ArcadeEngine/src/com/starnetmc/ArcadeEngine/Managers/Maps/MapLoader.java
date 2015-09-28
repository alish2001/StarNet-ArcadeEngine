package com.starnetmc.ArcadeEngine.Managers.Maps;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import com.starnetmc.ArcadeCore;
import com.starnetmc.ArcadeEngine.Events.GameStateChangeEvent;
import com.starnetmc.ArcadeEngine.Events.MapLoadEvent;
import com.starnetmc.ArcadeEngine.Events.MapUnLoadEvent;
import com.starnetmc.ArcadeEngine.Managers.GameState;
import com.starnetmc.ArcadeEngine.Utils.Logger;
import com.starnetmc.core.util.Tickifier;
import com.starnetmc.core.util.Tickifier.Time;

public class MapLoader implements Listener {

	public static String mapDir = "/var/Maps/";
	
	@EventHandler
	public void onLoad(MapLoadEvent e){
		String worldName = e.getMapWorldName();
		
		copyWorld(new File(mapDir + worldName),
				  new File(ArcadeCore.getPlugin().getServer().getWorldContainer().getAbsolutePath() + "/" + worldName));
	    loadWorld(worldName);
	    ArcadeCore.getArcadeManager().getMapper().refreshCurrentMap();
	}
	
	@EventHandler
	public void onUnLoad(MapUnLoadEvent e){
		World mapWorld = e.getMapWorld();
		
		unloadWorld(mapWorld);
		MapLoader.deleteWorld(new File(ArcadeCore.getPlugin().getServer().getWorldContainer().getAbsolutePath() + "/" + mapWorld.getName()));
	}
	
	@EventHandler
	public void onStart(GameStateChangeEvent e){
		if (ArcadeCore.getArcadeManager().getMapper().getActiveMap() == null) return;
		if (e.getChangedState() != GameState.LOADING) return;
		if (ArcadeCore.getArcadeManager().getMapper().getActiveMap().getAuthor().equalsIgnoreCase("mapper")) return;
		
		Bukkit.getServer().getPluginManager().callEvent(new MapLoadEvent(ArcadeCore.getArcadeManager().getMapper().getActiveMap().getMapWorldName()));
	}
	
	@EventHandler
	public void onStop(GameStateChangeEvent e){
		if (ArcadeCore.getArcadeManager().getMapper().getActiveMap() == null) return;
		if (e.getChangedState() != GameState.POSTGAME) return;
		if (ArcadeCore.getArcadeManager().getMapper().getActiveMap().getAuthor().equalsIgnoreCase("mapper")) return;
		
	      final World mapWorld = ArcadeCore.getArcadeManager().getMapper().getActiveMap().getCenter().getWorld();
	     
	      new BukkitRunnable() {
			
			@Override
			public void run() {
			      Bukkit.getServer().getPluginManager().callEvent(new MapUnLoadEvent(mapWorld));
			}
		}.runTaskLater(ArcadeCore.getPlugin(), Tickifier.tickify(6, Time.SECONDS));
	}
	
	public static void copyWorld(File sourceLocation, File targetLocation){
		
		try
		{
			Logger.log("<Maps> Copying world files...");
			if (sourceLocation.isDirectory())
			{
				if (!targetLocation.exists())
				{
					targetLocation.mkdir();
				}
				
				String[] children = sourceLocation.list();
				for (int i = 0; i < children.length; i++)
				{
					
					copyWorld(new File(sourceLocation, children[i]), new File(targetLocation, children[i]));
				}
			} 
			else
			{
				
				InputStream in = new FileInputStream(sourceLocation);
				OutputStream out = new FileOutputStream(targetLocation);
				
				byte[] buf = new byte[1024];
				int len;
				
				while ((len = in.read(buf)) > 0)
				{
					out.write(buf, 0, len);
				}
				in.close();
				out.close();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void loadWorld(String world){
			Bukkit.getServer().createWorld(new WorldCreator(world));
			Bukkit.getServer().getWorld(world).setAutoSave(false);
			Logger.log("<Maps> Loaded a world by the name:" + world + " from the maps directory.");
		
	}
	
	public static void unloadWorld(World world){
			Bukkit.getServer().unloadWorld(world, true);
			Logger.log("<Maps> UnLoaded a world by the name:" + world.getName());
	}
	
	public static boolean deleteWorld(File path){
		if (path.exists())
		{
			File files[] = path.listFiles();
			for (int i = 0; i < files.length; i++)
			{
				if (files[i].isDirectory())
				{
					deleteWorld(files[i]);
				}
				else
				{
					files[i].delete();
					Logger.log("<Maps> Deleted " + files[i].getName());
				}
			}
		}
		return (path.delete());
	}
	

}