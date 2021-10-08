package com.blackout.aow.nms;

import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class NMS {

	public static Class<?> getClass(String name) {
		try {
			return Class.forName("net.minecraft.server." + Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3] + "." + name);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void sendPacket(Player player, Object packet) {
		try {
			Object handle = player.getClass().getMethod("getHandle").invoke(player);
			Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
			
			playerConnection.getClass().getMethod("sendPacket", getClass("Packet")).invoke(playerConnection, packet);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void setField(Object edit, String fieldName, Object value) {
		try {
			Field field = edit.getClass().getDeclaredField(fieldName);
			
			field.setAccessible(true);
			field.set(edit, value);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}
