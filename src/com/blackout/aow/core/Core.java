package com.blackout.aow.core;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.blackout.aow.npc.ShopNPCManager;
import com.blackout.aow.utils.Board;
import com.blackout.aow.warrior.WarriorLogical;
import com.blackout.aow.warrior.WarriorManager;
import com.blackout.holoapi.core.Holo;
import com.blackout.holoapi.utils.HoloManager;

public class Core {

	public static Location spawn = null;
	public static Location spawnSpec = null;
	public static Location spawnP1 = null;
	public static Location spawnP2 = null;
	
	public static AowPlayer player1 = null;
	public static AowPlayer player2 = null;
	
	public static Base blueBase = null;
	public static Base redBase = null;
	
	public static int gameTime = 0;
	public static boolean gameRunning = false;
	
	public static List<AowPlayer> aowplayers = new ArrayList<AowPlayer>();
	
	public static List<WarriorLogical> blueWarrior = new ArrayList<WarriorLogical>();
	public static List<WarriorLogical> redWarrior = new ArrayList<WarriorLogical>();
	
	public static void loadLocations() {
		spawn = new Location(Bukkit.getWorld("world"), 958.5f, 56, 1326.5f, -90, 0);
		spawnP1 = new Location(Bukkit.getWorld("world"), 970.5f, 55, 1311.5f, -90, 0);
		spawnP2 = new Location(Bukkit.getWorld("world"), 970.5f, 55, 1341.5f, -90, 0);
		spawnSpec = new Location(Bukkit.getWorld("world"), 992.5f, 55, 1326.5f, 90, 0);
	}
	
	private static void setPlayers() {
		aowplayers.add(player1);
		aowplayers.add(player2);
		blueBase = new Base(player1, 5000, new Location(Bukkit.getWorld("world"), 983.5f, 54, 1307.5f, 0, 0));
		redBase = new Base(player2, 5000, new Location(Bukkit.getWorld("world"), 983.5f, 54, 1345.5f, 180, 0));
		
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (p != player1.getPlayer() && p != player2.getPlayer()) {
				Board b = new Board(p, "", "");
				AowPlayer spec = new AowPlayer(-1, p, b);
				p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100000, 0, false, false));
				p.setDisplayName("§7§o"+p.getName());
				p.setPlayerListName("§7§o"+p.getName());
				p.teleport(spawnSpec);
				spec.getBoard().addTeam(player1, false);
				spec.getBoard().addTeam(player2, false);
				aowplayers.add(spec);
			}
		}
		player1.getPlayer().teleport(spawnP1);
		player2.getPlayer().teleport(spawnP2);
		player1.getBoard().addTeam(player2, false);
		player2.getBoard().addTeam(player1, false);
		player1.getPlayer().setDisplayName("§9"+player1.getPlayer().getName());
		player2.getPlayer().setDisplayName("§4"+player2.getPlayer().getName());
		player1.getPlayer().setPlayerListName("§9"+player1.getPlayer().getName());
		player2.getPlayer().setPlayerListName("§4"+player2.getPlayer().getName());
	}
	
	private static void resetNameColor() {
		for (AowPlayer p : aowplayers) {
			p.getBoard().addTeam(player1, true);
			p.getBoard().addTeam(player2, true);
			p.getPlayer().setDisplayName(p.getPlayer().getName());
			p.getPlayer().setPlayerListName(p.getPlayer().getName());
		}
	}
	
	private static void setBaseLife() {
		for (AowPlayer p : aowplayers) {
			Holo blueLife = new Holo(UUID.randomUUID(), "§7[§a████████████████████§7]")
			        .setLocation(new Location(Bukkit.getWorld("world"), 977.5f, 55, 1307.5f));
			HoloManager.spawnHolo(blueLife, p.getPlayer());
			Holo redLife = new Holo(UUID.randomUUID(), "§7[§a████████████████████§7]")
			        .setLocation(new Location(Bukkit.getWorld("world"), 977.5f, 55, 1345.5f));
			HoloManager.spawnHolo(redLife, p.getPlayer());
			p.setBlueBaseLife(blueLife);
			p.setRedBaseLife(redLife);
			
			String blue = "§9Blue base health points";
			String red = "§4Red base health points";
			
			if (p == Core.player1) {
				blue = "§9Your base health points";
				red = "§4Opponent base health points";
			}
			
			if (p == Core.player2) {
				blue = "§9Opponent base health points";
				red = "§4Your base health points";
			}
			
			Holo blueName = new Holo(UUID.randomUUID(), blue)
			        .setLocation(new Location(Bukkit.getWorld("world"), 977.5f, 55.3f, 1307.5f));
			HoloManager.spawnHolo(blueName, p.getPlayer());
			Holo redName = new Holo(UUID.randomUUID(), red)
			        .setLocation(new Location(Bukkit.getWorld("world"), 977.5f, 55.3f, 1345.5f));
			HoloManager.spawnHolo(redName, p.getPlayer());
			p.setBlueBaseName(blueName);
			p.setRedBaseName(redName);
		}
	}
	
	public static void startGame() {
		setPlayers();
		setBaseLife();
		gameRunning = true;
		gameTime = 0;
		ShopNPCManager.addNPC(player1);
		ShopNPCManager.addNPC(player2);
	}
	
	public static void endGame() {
		resetNameColor();
		gameRunning = false;
		for (AowPlayer p : aowplayers) {
			p.getPlayer().teleport(spawn);
			p.getPlayer().removePotionEffect(PotionEffectType.INVISIBILITY);
			ShopNPCManager.removeNPC(p.getLeftShop(), p.getPlayer());
			ShopNPCManager.removeNPC(p.getRightShop(), p.getPlayer());
			WarriorManager.clearWarrior(p);
			HoloManager.deleteHolo(p.getPlayer(), p.getBlueBaseLife());
			HoloManager.deleteHolo(p.getPlayer(), p.getRedBaseLife());
			HoloManager.deleteHolo(p.getPlayer(), p.getBlueBaseName());
			HoloManager.deleteHolo(p.getPlayer(), p.getRedBaseName());
		}
		player1 = null;
		player2 = null;
		blueBase = null;
		redBase = null;
		aowplayers.clear();
	}
}
