package com.blackout.aow.npc;

import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.blackout.aow.core.Ages;
import com.blackout.aow.core.AowPlayer;
import com.blackout.aow.main.Main;
import com.blackout.aow.nms.NMSEntityEquipment;
import com.blackout.aow.warrior.WarriorManager;
import com.blackout.holoapi.core.Holo;
import com.blackout.holoapi.utils.HoloManager;
import com.blackout.npcapi.core.NPC;
import com.blackout.npcapi.utils.NPCManager;
import com.blackout.npcapi.utils.SkinLoader;

public class ShopNPCManager {
	
	private static Location[] locations = new Location[] {
		new Location(Bukkit.getWorld("world"), 973.5, 54, 1309.5, 90, 0),
		new Location(Bukkit.getWorld("world"), 973.5, 54, 1311.5, 90, 0),
		new Location(Bukkit.getWorld("world"), 973.5, 54, 1313.5, 90, 0),
		new Location(Bukkit.getWorld("world"), 973.5, 54, 1315.5, 90, 0),
		new Location(Bukkit.getWorld("world"), 973.5, 54, 1343.5, 90, 0),
		new Location(Bukkit.getWorld("world"), 973.5, 54, 1341.5, 90, 0),
		new Location(Bukkit.getWorld("world"), 973.5, 54, 1339.5, 90, 0),
		new Location(Bukkit.getWorld("world"), 973.5, 54, 1337.5, 90, 0)};
	
	public static void addNPC(AowPlayer owner) {
		int skinIndex = owner.getAge().ordinal() * 3;
		int locationIndex = owner.getPlayerID() * 4;
		
		for (int i = 0; i < 3 + (owner.getAge().equals(Ages.FUTURISTIC) ? 0 : 1); i++) {
			for (AowPlayer p : Main.aowplayers) {
				NPC npc = new NPC(UUID.randomUUID(), "shop"+i)
						.setCapeVisible(false)
						.setSkin(SkinLoader.getSkinById(skinIndex + i))
						.setNameVisible(false)
						.setLocation(locations[locationIndex + i]);
				NPCManager.spawnNPC(npc, p.getPlayer());
				NMSEntityEquipment.giveItem(p.getPlayer(), npc.getEntityId(), WarriorManager.items[skinIndex + i], 0);
				if (skinIndex + i == 6) 
					NMSEntityEquipment.giveItem(p.getPlayer(), npc.getEntityId(), 303, 3);
				if (skinIndex + i == 8) 
					NMSEntityEquipment.giveItem(p.getPlayer(), npc.getEntityId(), 307, 3);
				
				Location loc = locations[locationIndex + i].clone();
				loc.setY(loc.getY() + 1);
				
				Holo name = new Holo(UUID.randomUUID(), i == 3 ? "§bNext age" : "§a"+WarriorManager.names[skinIndex + i])
				        .setLocation(loc);
				HoloManager.spawnHolo(name, p.getPlayer());
				
				loc.setY(loc.getY() - 0.20);
				
				Holo cost = new Holo(UUID.randomUUID(), i == 3 ? "§bCost "+WarriorManager.ageCost[owner.getAge().ordinal()]+" xp" : "§6Cost "+(int) (WarriorManager.unitsStats[skinIndex + i][5])+" gold")
				        .setLocation(loc);
				HoloManager.spawnHolo(cost, p.getPlayer());
				
				ShopNPC shopNPC = new ShopNPC(npc, name, cost, WarriorManager.unitsStats[skinIndex + i][5]);
				
				if (owner.getPlayerID() == 0) {
					p.getLeftShop().add(shopNPC);
				} else {
					p.getRightShop().add(shopNPC);
				}
			}
		}
	}
	
	public static void removeNPC(List<ShopNPC> npcs, Player p) {
		for (ShopNPC npc : npcs) {
			NPCManager.deleteNPC(p, npc.getNpc());
			HoloManager.deleteHolo(p, npc.getName());
			HoloManager.deleteHolo(p, npc.getPrice());
		}
	}
}
