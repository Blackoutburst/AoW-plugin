package com.blackout.aow.nms;

import java.lang.reflect.Constructor;

import org.bukkit.entity.Player;

public class NMSEntityMove {

	public static void move(Player player, int entityID, byte x, byte y, byte z) {
		try {
			Class<?> packetClass = NMS.getClass("PacketPlayOutEntity").getClasses()[1];
			
			Constructor<?> packetConstructor = packetClass.getConstructor(int.class, byte.class, byte.class, byte.class, boolean.class);
			
			Object packet = packetConstructor.newInstance(entityID, x, y, z, true);
			
			NMS.sendPacket(player, packet);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
