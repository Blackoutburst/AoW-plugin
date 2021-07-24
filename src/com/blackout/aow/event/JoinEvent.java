package com.blackout.aow.event;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.event.player.PlayerJoinEvent;

import com.blackout.aow.core.NPCListener;
import com.blackout.npcapi.core.NPC;
import com.blackout.npcapi.core.PacketInteractListener;
import com.blackout.npcapi.utils.NPCManager;
import com.blackout.npcapi.utils.SkinLoader;

import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityEquipment;
import net.minecraft.server.v1_8_R3.PlayerConnection;

public class JoinEvent {

	public void execute(PlayerJoinEvent event) {
		event.getPlayer().teleport(new Location(Bukkit.getWorld("world"), 958.5f, 55, 1326.5f, -90, 0));
		PacketInteractListener.init(event.getPlayer(), new NPCListener());
		spawnNPC(event);
	}
	
	private void spawnNPC(PlayerJoinEvent event) {
		spawnKnight(event, 1310.5f, "§9");
		spawnKnight(event, 1342.5f, "§4");
		spawnArcher(event, 1312.5f, "§9");
		spawnArcher(event, 1340.5f, "§4");
		spawnMount(event, 1314.5f, "§9");
		spawnMount(event, 1338.5f, "§4");
		
	}
	
	private void spawnKnight(PlayerJoinEvent event, float Z, String color) {
		NPC npc = new NPC(UUID.randomUUID(), color + "Knight")
				.setLocation(new Location(Bukkit.getWorld("world"), 973.5f, 54, Z, 90, 0))
				.setSkin(SkinLoader.getSkinById(0))
				.setCapeVisible(false);
		NPCManager.spawnNPC(npc, event.getPlayer());
		
		PlayerConnection connection = ((CraftPlayer) event.getPlayer()).getHandle().playerConnection;
		connection.sendPacket(new PacketPlayOutEntityEquipment(npc.getEntityId(), 0, new ItemStack(Item.getById(267))));
	}
	
	private void spawnArcher(PlayerJoinEvent event, float Z, String color) {
		NPC npc = new NPC(UUID.randomUUID(), color + "Archer")
				.setLocation(new Location(Bukkit.getWorld("world"), 973.5f, 54, Z, 90, 0))
				.setSkin(SkinLoader.getSkinById(1))
				.setCapeVisible(false);
		NPCManager.spawnNPC(npc, event.getPlayer());
		
		PlayerConnection connection = ((CraftPlayer) event.getPlayer()).getHandle().playerConnection;
		connection.sendPacket(new PacketPlayOutEntityEquipment(npc.getEntityId(), 0, new ItemStack(Item.getById(261))));
	}
	
	private void spawnMount(PlayerJoinEvent event, float Z, String color) {
		NPC npc = new NPC(UUID.randomUUID(), color + "Mount")
				.setLocation(new Location(Bukkit.getWorld("world"), 973.5f, 54, Z, 90, 0))
				.setSkin(SkinLoader.getSkinById(2))
				.setCapeVisible(false);
		NPCManager.spawnNPC(npc, event.getPlayer());
		
		PlayerConnection connection = ((CraftPlayer) event.getPlayer()).getHandle().playerConnection;
		connection.sendPacket(new PacketPlayOutEntityEquipment(npc.getEntityId(), 0, new ItemStack(Item.getById(258))));
	
		/*
		WorldServer s = ((CraftWorld) Bukkit.getWorld("world")).getHandle();
		EntityHorse horseEntity = new EntityHorse(s);
		horseEntity.setLocation(973.5f, 54, Z, 90, 0);
		horseEntity.setTame(true);
		
		connection.sendPacket(new PacketPlayOutAttachEntity(0, npc.getEntity(), horseEntity));
		connection.sendPacket(new PacketPlayOutSpawnEntityLiving(horseEntity));
		connection.sendPacket(new PacketPlayOutEntityHeadRotation(horseEntity, (byte) ((npc.getLocation().getYaw() * 256.0F) / 360.0F)));
		*/
	}
	
}
