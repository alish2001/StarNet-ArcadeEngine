package com.starnetmc.core.util;

public class Tickifier {
	
	public static long tickify(long duration, Time timeFormat){
		long tick = duration * timeFormat.getTickValue();
		return tick;
	}
	
	public static long tickify(double duration, Time timeFormat){
		long tick = (long)duration * timeFormat.getTickValue();
		return tick;
	}
	
	public static long unTickify(long tick, Time timeFormat){
		long untickified = tick / timeFormat.getTickValue();
		return untickified;
	}
	
	public static long unTickify(double tick, Time timeFormat){
		long untickified = (long)tick / timeFormat.getTickValue();
		return untickified;
	}
	
	public static enum Time {
		
		SECONDS(20),
		MINUTES(1200),
		HOURS(72000),
		DAYS(1728000);
		
		private long tickValue;
		
		Time(long tickValue) {
			this.tickValue = tickValue;
		}
		
		public long getTickValue() {
			return tickValue;
		}
		
	}
	
}
