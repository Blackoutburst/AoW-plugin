package com.blackout.aow.nms;

import java.lang.reflect.Constructor;

import org.bukkit.entity.Player;


public class NMSEntityEquipment {

	public static void giveItem(Player player, int entityID, int itemID, int slot) {
		try {
			Class<?> packetClass = NMS.getClass("PacketPlayOutEntityEquipment");
			Class<?> itemClass = NMS.getClass("Item");
			Class<?> itemStackClass = NMS.getClass("ItemStack");
		
			Constructor<?> packetConstructor = packetClass.getConstructor(int.class, int.class, itemStackClass);
			Constructor<?> itemStackConstructor = itemStackClass.getConstructor(itemClass);
			
			Object itemStack = itemStackConstructor.newInstance(itemClass.getMethod("getById", int.class).invoke(null, itemID));
			Object packet = packetConstructor.newInstance(entityID, slot, itemStack);
			
			NMS.sendPacket(player, packet);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
