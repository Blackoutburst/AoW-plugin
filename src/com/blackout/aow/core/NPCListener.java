package com.blackout.aow.core;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.blackout.aow.main.Main;
import com.blackout.npcapi.core.APlayer;
import com.blackout.npcapi.core.NPC;
import com.blackout.npcapi.core.NPCPacket;
import com.blackout.npcapi.utils.NPCManager;

import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityEquipment;


public class NPCListener implements NPCPacket {

	@Override
	public void onLeftClick(Player p, int id) {
		APlayer ap = APlayer.get(p);
		for (NPC npc : ap.npcs) {
			if (id == npc.getEntityId()) {
				switch(npc.getName()) {
					case "§9Knight": spawnNPC(npc, p, 1307.5f, 0, 1); break;
					case "§9Archer": spawnNPC(npc, p, 1307.5f, 0, 1); break;
					case "§9Berserk": spawnNPC(npc, p, 1307.5f, 0, 1); break;
					case "§4Knight": spawnNPC(npc, p, 1345.5f, 180, 2); break;
					case "§4Archer": spawnNPC(npc, p, 1345.5f, 180, 2); break;
					case "§4Berserk": spawnNPC(npc, p, 1345.5f, 180, 2); break;
					default: continue;
				}
			}
		}
	}

	@Override
	public void onRightClick(Player p, int id) {
		APlayer ap = APlayer.get(p);
		for (NPC npc : ap.npcs) {
			if (id == npc.getEntityId()) {
				switch(npc.getName()) {
					case "§9Knight": spawnNPC(npc, p, 1307.5f, 0, 1); return;
					case "§9Archer": spawnNPC(npc, p, 1307.5f, 0, 1); return;
					case "§9Berserk": spawnNPC(npc, p, 1307.5f, 0, 1); return;
					case "§4Knight": spawnNPC(npc, p, 1345.5f, 180, 2); return;
					case "§4Archer": spawnNPC(npc, p, 1345.5f, 180, 2); return;
					case "§4Berserk": spawnNPC(npc, p, 1345.5f, 180, 2); return;
					default: continue;
				}
			}
		}
	}

	private void spawnNPC(NPC npc, Player p, float Z, float yaw, int playerNumber) {
		NPC warrior = new NPC(UUID.randomUUID(), "§a██████████")
				.setLocation(new Location(Bukkit.getWorld("world"), 983.5f, 54, Z, yaw, 0))
				.setSkin(npc.getSkin())
				.setCapeVisible(false);
		
		new BukkitRunnable() {
			public void run() {
				NPCManager.spawnNPC(warrior, p);
				if (npc.getName().contains("Knight")) {
					if (playerNumber == 1) {
						Main.player1NPC.add(new Warrior(warrior, 100, 20, 15, 3, 20, WarriorType.Knight));
					} else {
						Main.player2NPC.add(new Warrior(warrior, 100, 20, 15, 3, 20, WarriorType.Knight));
					}
					((CraftPlayer) p).getHandle().playerConnection.sendPacket(new PacketPlayOutEntityEquipment(warrior.getEntityId(), 0, new ItemStack(Item.getById(267))));
				}
				if (npc.getName().contains("Archer")) {
					((CraftPlayer) p).getHandle().playerConnection.sendPacket(new PacketPlayOutEntityEquipment(warrior.getEntityId(), 0, new ItemStack(Item.getById(261))));
					if (playerNumber == 1) {
						Main.player1NPC.add(new Warrior(warrior, 50, 10, 30, 5, 25, WarriorType.Archer));
					} else {
						Main.player2NPC.add(new Warrior(warrior, 50, 10, 30, 5, 25, WarriorType.Archer));
					}
				}
				if (npc.getName().contains("Berserk")) {
					((CraftPlayer) p).getHandle().playerConnection.sendPacket(new PacketPlayOutEntityEquipment(warrior.getEntityId(), 0, new ItemStack(Item.getById(258))));
					if (playerNumber == 1) {
						Main.player1NPC.add(new Warrior(warrior, 200, 30, 50, 10, 40, WarriorType.Berserk));
					} else {
						Main.player2NPC.add(new Warrior(warrior, 200, 30, 50, 10, 40, WarriorType.Berserk));
					}
				}
			}
		}.runTaskLater(Main.getPlugin(Main.class), 5L);
	}
}
