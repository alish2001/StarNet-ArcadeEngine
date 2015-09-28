package com.starnetmc.core.commands.util;

import java.util.Collection;

import org.bukkit.entity.Player;

import com.starnetmc.core.util.Rank;

public abstract interface ICommand {

	  public abstract void setCommandCenter(CommandCenter paramCommandCenter);
	  
	  public abstract void execute(Player paramPlayer, String[] paramArrayOfString);
	  
	  public abstract Collection<String> Aliases();
	  
	  public abstract void setAliasUsed(String paramString);
	  
	  public abstract Rank getRequiredRank();
}
