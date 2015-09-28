package com.starnetmc.ArcadeEngine.Managers.Maps.MapProperties;

public class MapProperties {
	
	private boolean rain;
	private long time;
	
	public MapProperties(){
		this.rain = false;
		this.time = 0;
	}
	
	public MapProperties(boolean rain, long time){
		this.rain = rain;
		this.time = time;
	}
	
	public void setRain(boolean rain){
		this.rain = rain;
	}
	
	public void setTime(long time){
		this.time = time;
	}
	
	public boolean isRain(){
		return rain;
	}
	
	public long getTime(){
		return time;
	}

}