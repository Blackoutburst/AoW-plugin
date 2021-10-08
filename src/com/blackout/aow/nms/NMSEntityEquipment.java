package com.blackout.aow.nms;

import java.lang.reflect.Constructor;

import org.bukkit.entity.Player;


public class NMSEntityEquipment {

	//new PacketPlayOutEntityEquipment(i, 0, new ItemStack(Item.getById(267)));
	public static void giveItem(Player player, int entityID, int itemID) {
		try {
			Class<?> packetClass = NMS.getClass("PacketPlayOutEntityEquipment");
			Class<?> itemClass = NMS.getClass("Item");
			Class<?> itemStackClass = NMS.getClass("ItemStack");
		
			Constructor<?> packetConstructor = packetClass.getConstructor(int.class, int.class, itemStackClass);
			Constructor<?> itemStackConstructor = itemStackClass.getConstructor(itemClass);
			
			Object itemStack = itemStackConstructor.newInstance(itemClass.getMethod("getById", int.class).invoke(null, itemID));
			Object packet = packetConstructor.newInstance(entityID, 0, itemStack);
			
			NMS.sendPacket(player, packet);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
