package com.blackout.aow.utils;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.blackout.holoapi.core.Holo;
import com.blackout.holoapi.utils.HoloManager;
import com.blackout.npcapi.core.NPC;
import com.blackout.npcapi.utils.NPCManager;
import com.blackout.npcapi.utils.SkinLoader;

import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityEquipment;
import net.minecraft.server.v1_8_R3.PlayerConnection;

public class PurchaseNPC {

	public static void spawnNPC(Player p) {
		spawnMelee(p, 1309.5f, "§9");
		spawnMelee(p, 1343.5f, "§4");
		spawnRanged(p, 1311.5f, "§9");
		spawnRanged(p, 1341.5f, "§4");
		spawnSpecial(p, 1313.5f, "§9");
		spawnSpecial(p, 1339.5f, "§4");
	}
	
	private static void spawnMelee(Player p, float Z, String color) {
		NPC npc = new NPC(UUID.randomUUID(), color + "Clubman")
				.setLocation(new Location(Bukkit.getWorld("world"), 973.5f, 54, Z, 90, 0))
				.setSkin(SkinLoader.getSkinById(0))
				.setCapeVisible(false);
		NPCManager.spawnNPC(npc, p);
		
		PlayerConnection connection = ((CraftPlayer) p).getHandle().playerConnection;
		connection.sendPacket(new PacketPlayOutEntityEquipment(npc.getEntityId(), 0, new ItemStack(Item.getById(280))));
		
		Holo price = new Holo(UUID.randomUUID(), "§6Cost §e15 §6gold")
		        .setLocation(new Location(Bukkit.getWorld("world"), 973.5f, 55.1f, Z, 0, 0));
		HoloManager.spawnHolo(price, p);
	}
	
	private static void spawnRanged(Player p, float Z, String color) {
		NPC npc = new NPC(UUID.randomUUID(), color + "Slingshot")
				.setLocation(new Location(Bukkit.getWorld("world"), 973.5f, 54, Z, 90, 0))
				.setSkin(SkinLoader.getSkinById(1))
				.setCapeVisible(false);
		NPCManager.spawnNPC(npc, p);
		
		PlayerConnection connection = ((CraftPlayer) p).getHandle().playerConnection;
		connection.sendPacket(new PacketPlayOutEntityEquipment(npc.getEntityId(), 0, new ItemStack(Item.getById(420))));
		
		Holo price = new Holo(UUID.randomUUID(), "§6Cost §e20 §6gold")
		        .setLocation(new Location(Bukkit.getWorld("world"), 973.5f, 55.1f, Z, 0, 0));
		HoloManager.spawnHolo(price, p);
	}
	
	private static void spawnSpecial(Player p, float Z, String color) {
		NPC npc = new NPC(UUID.randomUUID(), color + "Spearman")
				.setLocation(new Location(Bukkit.getWorld("world"), 973.5f, 54, Z, 90, 0))
				.setSkin(SkinLoader.getSkinById(2))
				.setCapeVisible(false);
		NPCManager.spawnNPC(npc, p);
		
		PlayerConnection connection = ((CraftPlayer) p).getHandle().playerConnection;
		connection.sendPacket(new PacketPlayOutEntityEquipment(npc.getEntityId(), 0, new ItemStack(Item.getById(273))));
		
		Holo price = new Holo(UUID.randomUUID(), "§6Cost §e200 §6gold")
		        .setLocation(new Location(Bukkit.getWorld("world"), 973.5f, 55.1f, Z, 0, 0));
		HoloManager.spawnHolo(price, p);
	}
	
}
