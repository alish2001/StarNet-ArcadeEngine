package com.starnetmc.core.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class Weather implements Listener {

	@EventHandler(priority = EventPriority.LOW)
	public void onWeatherChange(WeatherChangeEvent e) {
		
		e.setCancelled(true);
		
	}
	
}
