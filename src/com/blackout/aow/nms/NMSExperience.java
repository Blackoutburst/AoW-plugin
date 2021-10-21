package com.blackout.aow.nms;

import java.lang.reflect.Constructor;

import org.bukkit.entity.Player;

public class NMSExperience {

	public static void setXp(Player player, float percent) {
		try {
			Class<?> packetClass = NMS.getClass("PacketPlayOutExperience");
			
			Constructor<?> packetConstructor = packetClass.getConstructor(float.class, int.class, int.class);
			
			Object packet = packetConstructor.newInstance(percent, 0, 0);
			
			NMS.sendPacket(player, packet);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
