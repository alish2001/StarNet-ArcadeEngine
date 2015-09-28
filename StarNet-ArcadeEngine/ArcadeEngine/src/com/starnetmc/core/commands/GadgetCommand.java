package com.starnetmc.core.commands;

import org.bukkit.entity.Player;

import com.starnetmc.core.commands.util.CommandBase;
import com.starnetmc.core.gadgets.Gadgets;
import com.starnetmc.core.gadgets.GUI.GadgetGUI;
import com.starnetmc.core.util.Rank;

public class GadgetCommand extends CommandBase<Gadgets> {

	public GadgetCommand(Gadgets plugin) {
		super(plugin, Rank.DEFAULT, new String[] { "gadgets", "gadget" });
	}

	@Override
	public void execute(Player player, String[] args) {

		GadgetGUI.openGadgetGUI(player);

	}

}
