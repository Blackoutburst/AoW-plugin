package com.blackout.aow.nms;

import java.lang.reflect.Constructor;

import org.bukkit.entity.Player;

import com.blackout.holoapi.core.Holo;
import com.blackout.npcapi.core.NPC;


public class NMSAttachEntity {

	public static void attach(Player player, Holo lifebar, NPC npc) {
		try {
			Class<?> packetClass = NMS.getClass("PacketPlayOutAttachEntity");
			Class<?> entityClass = NMS.getClass("Entity");

			Constructor<?> packetConstructor = packetClass.getConstructor(int.class, entityClass, entityClass);
			
			Object packet = packetConstructor.newInstance(0, lifebar.getEntity(), npc.getEntity());
			
			NMS.sendPacket(player, packet);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
