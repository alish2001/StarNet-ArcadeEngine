 package com.starnetmc.core.util;

import java.lang.reflect.Field;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle.EnumTitleAction;
import net.minecraft.server.v1_8_R3.PlayerConnection;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class UtilPacket {
	
	public static void sendTitleBarMessage(Player player,String title, String subtitle) {
		
		 IChatBaseComponent chatTitle = ChatSerializer.a("{\"text\": \"" + title + "\"}");
		    IChatBaseComponent chatSub = ChatSerializer.a("{\"text\": \"" + subtitle + "\"}");
		    
		    PacketPlayOutTitle pt = new PacketPlayOutTitle(EnumTitleAction.TITLE, chatTitle);
		    PacketPlayOutTitle pst = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, chatSub);
		    
		    ((CraftPlayer)player).getHandle().playerConnection.sendPacket(pt);
		    ((CraftPlayer)player).getHandle().playerConnection.sendPacket(pst);
		
	}

	public static void sendWelcomeTitleBarMessage(Player player, String message) {
		
		 IChatBaseComponent chatTitle = ChatSerializer.a("{\"text\": \"§bThe Star Network\"}");
		    IChatBaseComponent chatSub = ChatSerializer.a("{\"text\": \"" + message + "\"}");
		    
		    PacketPlayOutTitle pt = new PacketPlayOutTitle(EnumTitleAction.TITLE, chatTitle);
		    PacketPlayOutTitle pst = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, chatSub);
		    
		    ((CraftPlayer)player).getHandle().playerConnection.sendPacket(pt);
		    ((CraftPlayer)player).getHandle().playerConnection.sendPacket(pst);
		
	}
	
	public static void sendActionBarMessage(Player player, String message) {
		
		IChatBaseComponent cbc = ChatSerializer.a("{\"text\": \"" + message + "\"}");
		
		PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc,(byte) 2);
		
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(ppoc);	
	}
	
    public static void sendTitleBarMessage(Player player, String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        CraftPlayer craftplayer = (CraftPlayer) player;
        PlayerConnection connection = craftplayer.getHandle().playerConnection;
        IChatBaseComponent titleJSON = ChatSerializer.a("{'text': '" + title + "'}");
        IChatBaseComponent subtitleJSON = ChatSerializer.a("{'text': '" + subtitle + "'}");
        PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(EnumTitleAction.TITLE, titleJSON, fadeIn, stay, fadeOut);
        PacketPlayOutTitle subtitlePacket = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, subtitleJSON);
        connection.sendPacket(titlePacket);
        connection.sendPacket(subtitlePacket);
    }

    public static void sendTabHF(Player player, String header, String footer){
           
        CraftPlayer craftplayer = (CraftPlayer) player;
        PlayerConnection connection = craftplayer.getHandle().playerConnection;
        IChatBaseComponent headerJSON = ChatSerializer.a("{\"text\": \"" + header +"\"}");
        IChatBaseComponent footerJSON = ChatSerializer.a("{\"text\": \"" + footer +"\"}");
        PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();
     
        try {
            Field headerField = packet.getClass().getDeclaredField("a");
            headerField.setAccessible(true);
            headerField.set(packet, headerJSON);
            headerField.setAccessible(!headerField.isAccessible());
         
            Field footerField = packet.getClass().getDeclaredField("b");
            footerField.setAccessible(true);
            footerField.set(packet, footerJSON);
            footerField.setAccessible(!footerField.isAccessible());
        } catch (Exception e) {
            e.printStackTrace();
        }
       
        connection.sendPacket(packet);
       
           
    }
}
