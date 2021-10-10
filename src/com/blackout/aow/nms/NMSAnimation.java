package com.blackout.aow.nms;

import java.lang.reflect.Constructor;

import org.bukkit.entity.Player;

import com.blackout.npcapi.core.NPC;

public class NMSAnimation {

	public static void animation(Player player, NPC npc, int animation) {
		try {
			Class<?> packetClass = NMS.getClass("PacketPlayOutAnimation");
			Class<?> entityClass = NMS.getClass("Entity");

			Constructor<?> packetConstructor = packetClass.getConstructor(entityClass, int.class);
			
			Object packet = packetConstructor.newInstance(npc.getEntity(), animation);
			
			NMS.sendPacket(player, packet);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
