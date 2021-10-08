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
	
	private static int[] items = new int[] {
		280, //stick
		420, //lead
		268, //wooden sword
		272, //stone sword
		261, //bow
		267, //iron sword
		283, //gold sword
		291, //stone hoe
		418, //gold horse armor
		288, //feather
		417, //iron horse armor
		46, //tnt
		276, //diamond sword
		292, //iron hoe
		369}; //blaze rod
	
	private static int[] costs = new int[] {
		10,
		20,
		50,
		50,
		60,
		100,
		100,
		130,
		180,
		200,
		250,
		300,
		400,
		500,
		1000};
	
	private static int[] ageCost = new int[] {
		1000,
		3000,
		7000,
		10000};
	
	private static String[] names = new String[] {
		"Cave man",
		"Slingshot",
		"Steve",
		"Swordsman",
		"Archer",
		"Musketeer",
		"Rifleman",
		"Cannoneer",
		"Soldier",
		"Gunner",
		"Bomber",
		"Space soldier",
		"Space gunner",
		"Super soldier"};
	
	public static void addNPC(AowPlayer owner) {
		int skinIndex = owner.getAge().ordinal() * 3;
		int locationIndex = owner.getPlayerID() * 4;
		
		for (int i = 0; i < 3 + (owner.getAge().equals(Ages.FUTURISTIC) ? 0 : 1); i++) {
			for (AowPlayer p : Main.aowplayers) {
				NPC npc = new NPC(UUID.randomUUID(), "shop")
						.setCapeVisible(false)
						.setSkin(SkinLoader.getSkinById(skinIndex + i))
						.setNameVisible(false)
						.setLocation(locations[locationIndex + i]);
				NPCManager.spawnNPC(npc, p.getPlayer());
				NMSEntityEquipment.giveItem(p.getPlayer(), npc.getEntityId(), items[skinIndex + i]);
				
				Location loc = locations[locationIndex + i].clone();
				loc.setY(loc.getY() + 1);
				
				Holo name = new Holo(UUID.randomUUID(), i == 3 ? "§bNext age" : "§a"+names[skinIndex + i])
				        .setLocation(loc);
				HoloManager.spawnHolo(name, p.getPlayer());
				
				loc.setY(loc.getY() - 0.20);
				
				Holo cost = new Holo(UUID.randomUUID(), i == 3 ? "§bCost "+ageCost[owner.getAge().ordinal()]+" xp" : "§6Cost "+costs[skinIndex + i]+" gold")
				        .setLocation(loc);
				HoloManager.spawnHolo(cost, p.getPlayer());
				
				ShopNPC shopNPC = new ShopNPC(npc, name, cost, costs[skinIndex + i]);
				
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
