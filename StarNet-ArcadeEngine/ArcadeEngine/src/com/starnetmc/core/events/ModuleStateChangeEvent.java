package com.starnetmc.core.events;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.starnetmc.core.modules.manager.Module;
import com.starnetmc.core.modules.manager.ModuleType;

public class ModuleStateChangeEvent extends Event implements Cancellable {

	private static HandlerList handlers = new HandlerList();
	private Module module;
	private ModuleType mt;

	public ModuleStateChangeEvent(Module module, String name, ModuleType mt) {
		this.module = module;
		this.mt = mt;

	}

	public ModuleStateChangeEvent(Module module, ModuleType mt) {
		this.module = module;
		this.mt = mt;

	}

	public ModuleStateChangeEvent() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public HandlerList getHandlers() {
		// TODO Auto-generated method stub
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	@Override
	public boolean isCancelled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setCancelled(boolean arg0) {
		// TODO Auto-generated method stub

	}

	public Module getModule() {
		return module;
	}

	public ModuleType getModuleType() {
		return mt;
	}


}
