package com.blackout.aow.nms;

import java.lang.reflect.Constructor;

import org.bukkit.entity.Player;

public class NMSTitle {
	
	public static void sendTitle(Player player, String title, String subtitle, int fadeIn, int stay, int fadeOut) {
		try {
			Class<?> playOutTitle = NMS.getClass("PacketPlayOutTitle");
			Class<?> enumTitleAction = NMS.getClass("PacketPlayOutTitle").getDeclaredClasses()[0];
			Class<?> chatBaseComponent = NMS.getClass("IChatBaseComponent");
			Class<?> chatSerializer = NMS.getClass("IChatBaseComponent").getDeclaredClasses()[0];
			
			Constructor<?> delayConstructor = playOutTitle.getConstructor(int.class, int.class, int.class);
			Constructor<?> titleConstructor = playOutTitle.getConstructor(enumTitleAction, chatBaseComponent);
			
			Object titleString = chatSerializer.getMethod("a", String.class).invoke(null, "{\"text\": \"" + title + "\"}");
			Object subtitleString = chatSerializer.getMethod("a", String.class).invoke(null, "{\"text\": \"" + subtitle + "\"}");

			Object delayPacket = delayConstructor.newInstance(fadeIn, stay, fadeOut);
			Object titlePacket = titleConstructor.newInstance(enumTitleAction.getField("TITLE").get(null), titleString);
			Object subtitlePacket = titleConstructor.newInstance(enumTitleAction.getField("SUBTITLE").get(null), subtitleString);
			
			NMS.sendPacket(player, delayPacket);
			NMS.sendPacket(player, titlePacket);
			NMS.sendPacket(player, subtitlePacket);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
