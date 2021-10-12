package com.blackout.aow.warrior;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.blackout.aow.core.Ages;
import com.blackout.aow.core.AowPlayer;
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
			3000,
			7000,
			10000};
	
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
	
	//xpDrop | goldDrop | hp | range | dmg | cost | combat delay
	public static float[][] unitsStats = new float[][] {
		{10, 20, 100, 2.0f, 25, 15, 20}, //Cave man
		{15, 25, 80, 5.0f, 20, 25, 25}, //Slingshot
		{40, 80, 250, 2.0f, 30, 100, 20}, //Steve
		{50, 80, 400, 2.0f, 80, 70, 20}, //Swordsman
		{60, 100, 300, 5.0f, 60, 90, 18}, //Archer
		{100, 200, 700, 2.0f, 100, 200, 20}, //Knight
		{100, 240, 800, 2.0f, 160, 200, 18}, //Musketeer
		{120, 270, 600, 8.0f, 120, 260, 16}, //Rifleman
		{250, 350, 1000, 5.0f, 400, 400, 30}, //Cannoneer
		{250, 400, 1500, 2.0f, 400, 350, 15}, //Soldier
		{280, 440, 1000, 8.0f, 100, 450, 5}, //Gunner
		{400, 550, 1500, 5.0f, 600, 600, 30}, //Bomber
		{400, 600, 2000, 2.0f, 750, 550, 10}, //Space soldier
		{600, 740, 1600, 10.0f, 200, 700, 5}, //Space gunner
		{1000, 2500, 50000, 2.0f, 700, 5000, 5}, //Super soldier
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
		
		
		for (AowPlayer ap : Main.aowplayers) {
			NPC npc = new NPC(UUID.randomUUID(), names[unitIndex])
					.setCapeVisible(false)
					.setSkin(SkinLoader.getSkinById(unitIndex))
					.setNameVisible(false)
					.setLocation(p.getPlayerID() == 0 ? Main.blueBase.getLocation().clone() : Main.redBase.getLocation().clone());
			
			Location loc = p.getPlayerID() == 0 ? Main.blueBase.getLocation().clone() : Main.redBase.getLocation().clone();
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
		
		WarriorLogical w = selectWarriorType(names[unitIndex], p, opt, p.getPlayerID() == 0 ? Main.blueBase.getLocation().clone() : Main.redBase.getLocation().clone());
		
		if (p.getPlayerID() == 0) {
			Main.blueWarrior.add(w);
		} else {
			Main.redWarrior.add(w);
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
				Main.blueBase.setLife(Main.blueBase.getLife() + 5000);
				Main.blueBase.setMaxLife(Main.blueBase.getMaxLife() + 5000);
				Main.blueBase.updateLifeBar(p, p.getBlueBaseLife(), true);
				for (AowPlayer ap : Main.aowplayers) {
					ShopNPCManager.removeNPC(ap.getLeftShop(), ap.getPlayer());
				}
				ShopNPCManager.addNPC(Main.player1);
			} else {
				Main.redBase.setLife(Main.redBase.getLife() + 5000);
				Main.redBase.setMaxLife(Main.redBase.getMaxLife() + 5000);
				Main.redBase.updateLifeBar(p, p.getRedBaseLife(), false);
				for (AowPlayer ap : Main.aowplayers) {
					ShopNPCManager.removeNPC(ap.getRightShop(), ap.getPlayer());
				}
				ShopNPCManager.addNPC(Main.player2);
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
		Main.blueWarrior.clear();
		Main.redWarrior.clear();
	}
	
	public static void doActions() {
		new BukkitRunnable(){
			@Override
			public void run(){
				if (Main.gameRunning) {
					Main.gameTime++;
					
					int j = Main.blueWarrior.size();
					for (int i = 0; i < j; i++) {
						WarriorLogical w = Main.blueWarrior.get(i);
						if (w.dead) {
							Main.blueWarrior.remove(i);
							j--;
						}
					}
					
					j = Main.redWarrior.size();
					for (int i = 0; i < j; i++) {
						WarriorLogical w = Main.redWarrior.get(i);
						if (w.dead) {
							Main.redWarrior.remove(i);
							j--;
						}
					}
					
					for (int i = 0; i < Main.blueWarrior.size(); i++) {
						WarriorLogical w = Main.blueWarrior.get(i);
						w.update(i);
					}
					
					for (int i = 0; i < Main.redWarrior.size(); i++) {
						WarriorLogical w = Main.redWarrior.get(i);
						w.update(i);
					}
				}
			}
		}.runTaskTimer(Main.getPlugin(Main.class), 0L, 1L);
	}
}
