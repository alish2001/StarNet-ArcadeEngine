package com.starnetmc.core.commands.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.bukkit.entity.Player;

import com.starnetmc.core.modules.manager.Module;
import com.starnetmc.core.util.Rank;

public class CommandBase<PluginType extends Module> implements ICommand
{
	  private Rank _requiredRank;
	  private List<String> _aliases;
	  protected PluginType Plugin;
	  protected String AliasUsed;
	  protected CommandCenter CommandCenter;
	  
	  public CommandBase(PluginType plugin, Rank requiredRank, String... aliases)
	  {
	    this.Plugin = plugin;
	    this._requiredRank = requiredRank;
	    this._aliases = Arrays.asList(aliases);
	  }
	  
	  public Collection<String> Aliases()
	  {
	    return this._aliases;
	  }
	  
	  public void setAliasUsed(String alias)
	  {
	    this.AliasUsed = alias;
	  }
	  
	  public Rank getRequiredRank()
	  {
	    return this._requiredRank;
	  }
	  
	  public void setCommandCenter(CommandCenter commandCenter)
	  {
	    this.CommandCenter = commandCenter;
	  }

	
	public void execute(Player paramPlayer, String[] paramArrayOfString) {
		
	}
}