package com.starnetmc.ArcadeEngine.Managers.Classes;

public enum SkillActivationMethod {
	
	RIGHT_CLICK("Right Click"), 
	LEFT_CLICK("Left Click"),
	SHIFT("Shift");
	
	private String iString;
	
	SkillActivationMethod(String iS){
		this.iString = iS;
	}
	
	public String getiString(){
		return iString;
	}
	
	public static String getRegEx(){
		return "(Right Click|Left Click|Shift)";
	}

}