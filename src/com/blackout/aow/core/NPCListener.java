package com.blackout.aow.core;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.blackout.aow.main.Main;
import com.blackout.npcapi.core.APlayer;
import com.blackout.npcapi.core.NPC;
import com.blackout.npcapi.core.NPCPacket;
import com.blackout.npcapi.utils.NPCManager;


public class NPCListener implements NPCPacket {

	@Override
	public void onLeftClick(Player p, int id) {
		APlayer ap = APlayer.get(p);
		for (NPC npc : ap.npcs) {
			if (id == npc.getEntityId()) {
				switch(npc.getName()) {
					case "§9Knight": spawnLeftNPC(npc, p); break;
					case "§9Archer": spawnLeftNPC(npc, p); break;
					case "§9Mount": spawnLeftNPC(npc, p); break;
					case "§4Knight": spawnRightNPC(npc, p); break;
					case "§4Archer": spawnRightNPC(npc, p); break;
					case "§4Mount": spawnRightNPC(npc, p); break;
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
				case "§9Knight": spawnLeftNPC(npc, p); break;
				case "§9Archer": spawnLeftNPC(npc, p); break;
				case "§9Mount": spawnLeftNPC(npc, p); break;
				case "§4Knight": spawnRightNPC(npc, p); break;
				case "§4Archer": spawnRightNPC(npc, p); break;
				case "§4Mount": spawnRightNPC(npc, p); break;
				default: continue;
			}
			}
		}
	}

	private void spawnLeftNPC(NPC npc, Player p) {
		NPC warrior = new NPC(UUID.randomUUID(), "§a██████████")
				.setLocation(new Location(Bukkit.getWorld("world"), 983.5f, 54, 1307.5f, 0, 0))
				.setSkin(npc.getSkin())
				.setCapeVisible(false);
		
		new BukkitRunnable() {
			public void run() {
				NPCManager.spawnNPC(warrior, p);
				Main.player1NPC.add(warrior);
			}
		}.runTaskLater(Main.getPlugin(Main.class), 5L);
	}
	
	private void spawnRightNPC(NPC npc, Player p) {
		NPC warrior = new NPC(UUID.randomUUID(), "§a██████████")
				.setLocation(new Location(Bukkit.getWorld("world"), 983.5f, 54, 1345.5f, 180, 0))
				.setSkin(npc.getSkin())
				.setCapeVisible(false);
		
		new BukkitRunnable() {
			public void run() {
				NPCManager.spawnNPC(warrior, p);
				Main.player2NPC.add(warrior);
			}
		}.runTaskLater(Main.getPlugin(Main.class), 5L);
	}
	
}
