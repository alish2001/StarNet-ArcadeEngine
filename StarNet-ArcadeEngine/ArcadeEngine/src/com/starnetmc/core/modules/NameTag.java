package com.starnetmc.core.modules;

import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.PacketPlayOutNamedEntitySpawn;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.starnetmc.core.accounts.AccountManager;
import com.starnetmc.core.modules.manager.Module;
import com.starnetmc.core.modules.manager.ModuleType;
import com.starnetmc.core.util.F;
import com.starnetmc.core.util.Rank;

public class NameTag extends Module {

	
	public NameTag(JavaPlugin plugin) {
		super("Nametag Manager",0.0,ModuleType.INFO,plugin);
	}
	
	public NameTag() {
		
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) throws Exception {

		//setNametag(e.getPlayer());
		/*EntityPlayer ep = new EntityPlayer(null, null, makeProfile("", e
				.getPlayer().getUniqueId(), null),
				((CraftPlayer) e.getPlayer()).getHandle().playerInteractManager);*/

	}

	public static void setNametag(Player player) throws Exception {

		String _prevName = player.getName();
		EntityPlayer ep = ((CraftPlayer) player).getHandle();

		if (AccountManager.getAccount(player).getRank() != Rank.DEFAULT){
		    ep.displayName = AccountManager.getAccount(player).getRank().getTag(false) + " " + player.getName();
		    
			} else {
				 player.setDisplayName(F.YELLOW + player.getName());
			}

		for (Player all : Bukkit.getOnlinePlayers()) {

			if (all != player) {

				((CraftPlayer) player).getHandle().playerConnection
						.sendPacket(new PacketPlayOutNamedEntitySpawn(ep));

			}

		}
		ep.displayName = _prevName;

	}

	/*public static GameProfile makeProfile(String name, UUID skinId, UUID npcID) {
		GameProfile profile = new GameProfile(npcID, name);
		if (skinId != null) {
			MinecraftServer nmsServer = ((CraftServer) Bukkit.getServer())
					.getServer();
			GameProfile skin = new GameProfile(skinId, null);
			skin = nmsServer. // Srg =
																		// func_147130_as
			if (skin.getProperties().get("textures") == null
					|| !skin.getProperties().get("textures").isEmpty()) {
				Property textures = skin.getProperties().get("textures")
						.iterator().next();
				profile.getProperties().put("textures", textures);
			}
		}
		return profile;
	}*/


	@Override
	public void enable() {
		isEnabled = true;
		log("Enabled.");

	}

	@Override
	public void disable() {

		isEnabled = false;
		log("Disabled.");

	}

	public static boolean isEnabled;

}