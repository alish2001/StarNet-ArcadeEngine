package com.starnetmc.core.util;

public class UString {
	
	public static String UppercaseFirstLetter(String string) {
		return string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
		}
	
	public static String forgeArgMessage(String[] args, int startArg) {
		StringBuilder bldr = new StringBuilder();
		for (int i = startArg; i < args.length; i++) {
		if (i != startArg) {
		bldr.append(" ");
		}
		bldr.append(args[i]);
		}
		return bldr.toString();
		}

}
