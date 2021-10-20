package com.blackout.aow.warrior;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.blackout.aow.core.Ages;
import com.blackout.aow.core.AowPlayer;
import com.blackout.aow.core.Core;
import com.blackout.aow.main.Main;
import com.blackout.aow.nms.NMSAttachEntity;
import com.blackout.aow.nms.NMSEntityEquipment;
import com.blackout.aow.npc.ShopNPCManager;
import com.blackout.holoapi.core.Holo;
import com.blackout.holoapi.utils.HoloManager;
import com.blackout.npcapi.core.NPC;
import com.blackout.npcapi.utils.NPCManager;
import com.blackout.npcapi.utils.SkinLoader;

public class WarriorManager {

	public static int[] items = new int[] {
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
	
	public static int[] ageCost = new int[] {
			1000,
			5000,
			15000,
			30000};
	
	public static String[] names = new String[] {
			"Cave man",
			"Slingshot",
			"Steve",
			"Swordsman",
			"Archer",
			"Knight",
			"Musketeer",
			"Rifleman",
			"Cannoneer",
			"Soldier",
			"Gunner",
			"Bomber",
			"Space soldier",
			"Space gunner",
			"Super soldier"};
	
	//xp |     gold | hp |   range | dmg | cost | combat delay
	public static float[][] unitsStats = new float[][] {
		{  20,   30,    100,  2.0f,  25,   15,    20}, //Cave man
		{  30,   35,     80,  5.0f,  20,   25,    25}, //Slingshot
		{  80,   90,    250,  2.0f,  30,  100,    20}, //Steve
		{ 100,  100,    400,  2.0f,  80,   70,    20}, //Swordsman
		{ 120,  120,    300,  5.0f,  60,   90,    18}, //Archer
		{ 200,  180,    700,  2.0f, 100,  200,    20}, //Knight
		{ 200,  240,    800,  2.0f, 160,  200,    18}, //Musketeer
		{ 240,  280,    600,  5.0f, 120,  260,    16}, //Rifleman
		{ 400,  350,   1000,  5.0f, 400,  400,    30}, //Cannoneer
		{ 500,  400,   1500,  2.0f, 400,  350,    15}, //Soldier
		{ 560,  420,   1000,  6.0f,  80,  450,     5}, //Gunner
		{ 800,  550,   1500,  4.0f, 450,  600,    30}, //Bomber
		{ 800,  600,   2000,  2.0f, 600,  550,    10}, //Space soldier
		{1200,  740,   1600,  7.0f, 200,  700,     5}, //Space gunner
		{2000, 3500,  50000,  2.0f, 700, 5000,     5}, //Super soldier
	};
	
	public static void createNewWarrior(String name, Player p) {
		AowPlayer player = AowPlayer.getFromPlayer(p);
		
		switch(name) {
			case "shop0": createUnits(player, 0); break;
			case "shop1": createUnits(player, 1); break;
			case "shop2": createUnits(player, 2); break;
			case "shop3": evolve(player); break;
			default: return;
		}
	}
	
	private static void createUnits(AowPlayer p, int index) {
		int unitIndex = p.getAge().ordinal() * 3 + index;
		
		if (p.getGold() < unitsStats[unitIndex][5]) {
			p.getPlayer().sendMessage("§cYou don't have enough gold for that!");
			p.getPlayer().playSound(p.getPlayer().getLocation(), Sound.ANVIL_LAND, 1.0f, 1.0f);
			return;
		}
		p.setGold((int)(p.getGold() - unitsStats[unitIndex][5]));
		p.getPlayer().sendMessage("§b"+names[unitIndex]+" §acreated for §6"+(int)(unitsStats[unitIndex][5])+" gold!");
		p.getPlayer().playSound(p.getPlayer().getLocation(), Sound.ORB_PICKUP, 1.0f, 1.0f);
		
		
		for (AowPlayer ap : Core.aowplayers) {
			NPC npc = new NPC(UUID.randomUUID(), names[unitIndex])
					.setCapeVisible(false)
					.setSkin(SkinLoader.getSkinById(unitIndex))
					.setNameVisible(false)
					.setLocation(p.getPlayerID() == 0 ? Core.blueBase.getLocation().clone() : Core.redBase.getLocation().clone());
			
			Location loc = p.getPlayerID() == 0 ? Core.blueBase.getLocation().clone() : Core.redBase.getLocation().clone();
			loc.setY(loc.getY() + 1);
			
			Holo life = new Holo(UUID.randomUUID(), "§7[§a||||||||||§7]")
			        .setLocation(loc);
			HoloManager.spawnHolo(life, ap.getPlayer());
			
			NPCManager.spawnNPC(npc, ap.getPlayer());
			NMSEntityEquipment.giveItem(ap.getPlayer(), npc.getEntityId(), items[unitIndex], 0);
			
			if (names[unitIndex].equals("Musketeer")) 
				NMSEntityEquipment.giveItem(ap.getPlayer(), npc.getEntityId(), 303, 3);
			if (names[unitIndex].equals("Cannoneer")) 
				NMSEntityEquipment.giveItem(ap.getPlayer(), npc.getEntityId(), 307, 3);
			
			NMSAttachEntity.attach(ap.getPlayer(), life, npc);
			WarriorVisual wv = new WarriorVisual(npc, life);
			
			if (p.getPlayerID() == 0) {
				ap.getBlueNPC().add(wv);
			} else {
				ap.getRedNPC().add(wv);
			}
		}
		
		WarriorOptions opt = new WarriorOptions(unitsStats[unitIndex][0], unitsStats[unitIndex][1], 
				unitsStats[unitIndex][2], unitsStats[unitIndex][3], 
				unitsStats[unitIndex][4], unitsStats[unitIndex][5], items[unitIndex], unitsStats[unitIndex][6]);
		
		WarriorLogical w = selectWarriorType(names[unitIndex], p, opt, p.getPlayerID() == 0 ? Core.blueBase.getLocation().clone() : Core.redBase.getLocation().clone());
		
		if (p.getPlayerID() == 0) {
			Core.blueWarrior.add(w);
		} else {
			Core.redWarrior.add(w);
		}
	}
	
	private static WarriorLogical selectWarriorType(String name, AowPlayer p, WarriorOptions opt, Location loc) {
		switch (name) {
			case "Cave man": return (new CaveMan(p, opt, loc.clone()));
			case "Slingshot": return (new Slingshot(p, opt, loc.clone())); 
			case "Steve": return (new Steve(p, opt, loc.clone()));
			case "Swordsman": return (new Swordsman(p, opt, loc.clone())); 
			case "Archer": return (new Archer(p, opt, loc.clone()));
			case "Knight": return (new Knight(p, opt, loc.clone()));
			case "Musketeer": return (new Musketeer(p, opt, loc.clone()));
			case "Rifleman": return (new Rifleman(p, opt, loc.clone()));
			case "Cannoneer": return (new Cannoneer(p, opt, loc.clone()));
			case "Soldier": return (new Soldier(p, opt, loc.clone()));
			case "Gunner": return (new Gunner(p, opt, loc.clone()));
			case "Bomber": return (new Bomber(p, opt, loc.clone()));
			case "Space soldier": return (new SpaceSoldier(p, opt, loc.clone()));
			case "Space gunner": return (new SpaceGunner(p, opt, loc.clone()));
			case "Super soldier": return (new SuperSoldier(p, opt, loc.clone()));
			default: return (null);
		}
	}
	
	private static void evolve(AowPlayer p) {
		int cost = ageCost[p.getAge().ordinal()];
		if (p.getXp() >= cost) {
			if (p.getAge() == Ages.MODERN) p.setAge(Ages.FUTURISTIC);
			if (p.getAge() == Ages.RENAISSANCE) p.setAge(Ages.MODERN);
			if (p.getAge() == Ages.MEDIEVAL) p.setAge(Ages.RENAISSANCE);
			if (p.getAge() == Ages.PREHISTORIC) p.setAge(Ages.MEDIEVAL);
			
			p.getPlayer().playSound(p.getPlayer().getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
			if (p.getPlayerID() == 0) {
				Core.blueBase.setLife(Core.blueBase.getLife() + 5000);
				Core.blueBase.setMaxLife(Core.blueBase.getMaxLife() + 5000);
				Core.blueBase.updateLifeBar(p, p.getBlueBaseLife(), true);
				for (AowPlayer ap : Core.aowplayers) {
					ShopNPCManager.removeNPC(ap.getLeftShop(), ap.getPlayer());
				}
				ShopNPCManager.addNPC(Core.player1);
			} else {
				Core.redBase.setLife(Core.redBase.getLife() + 5000);
				Core.redBase.setMaxLife(Core.redBase.getMaxLife() + 5000);
				Core.redBase.updateLifeBar(p, p.getRedBaseLife(), false);
				for (AowPlayer ap : Core.aowplayers) {
					ShopNPCManager.removeNPC(ap.getRightShop(), ap.getPlayer());
				}
				ShopNPCManager.addNPC(Core.player2);
			}
		}
	}
	
	public static void clearWarrior(AowPlayer p) {
		for(WarriorVisual warrior : p.getBlueNPC()) {
			HoloManager.deleteHolo(p.getPlayer(), warrior.getLifeBar());
			NPCManager.deleteNPC(p.getPlayer(), warrior.getNpc());
		}
		p.getBlueNPC().clear();
		
		for(WarriorVisual warrior : p.getRedNPC()) {
			HoloManager.deleteHolo(p.getPlayer(), warrior.getLifeBar());
			NPCManager.deleteNPC(p.getPlayer(), warrior.getNpc());
		}
		p.getRedNPC().clear();
		Core.blueWarrior.clear();
		Core.redWarrior.clear();
	}
	
	public static void doActions() {
		new BukkitRunnable(){
			@Override
			public void run(){
				if (Core.gameRunning) {
					
					int j = Core.blueWarrior.size();
					for (int i = 0; i < j; i++) {
						WarriorLogical w = Core.blueWarrior.get(i);
						if (w.dead) {
							Core.blueWarrior.remove(i);
							j--;
						}
					}
					
					j = Core.redWarrior.size();
					for (int i = 0; i < j; i++) {
						WarriorLogical w = Core.redWarrior.get(i);
						if (w.dead) {
							Core.redWarrior.remove(i);
							j--;
						}
					}
					
					for (int i = 0; i < Core.blueWarrior.size(); i++) {
						WarriorLogical w = Core.blueWarrior.get(i);
						w.update(i);
					}
					
					for (int i = 0; i < Core.redWarrior.size(); i++) {
						WarriorLogical w = Core.redWarrior.get(i);
						w.update(i);
					}
				}
			}
		}.runTaskTimer(Main.getPlugin(Main.class), 0L, 1L);
	}
}
