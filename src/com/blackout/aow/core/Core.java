package com.blackout.aow.core;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.blackout.aow.main.Main;
import com.blackout.aow.npc.ShopNPCManager;
import com.blackout.aow.utils.Board;

public class Core {

	private static void setPlayers() {
		Main.aowplayers.add(Main.player1);
		Main.aowplayers.add(Main.player2);
		
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (p != Main.player1.getPlayer() && p != Main.player2.getPlayer()) {
				Board b = new Board(p, "", "");
				AowPlayer spec = new AowPlayer(-1, p, b);
				p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100000, 0, false, false));
				p.setDisplayName("§7§o"+p.getName());
				p.setPlayerListName("§7§o"+p.getName());
				p.teleport(Main.spawnSpec);
				spec.getBoard().addTeam(Main.player1, false);
				spec.getBoard().addTeam(Main.player2, false);
				Main.aowplayers.add(spec);
			}
		}
		Main.player1.getPlayer().teleport(Main.spawnP1);
		Main.player2.getPlayer().teleport(Main.spawnP2);
		Main.player1.getBoard().addTeam(Main.player2, false);
		Main.player2.getBoard().addTeam(Main.player1, false);
		Main.player1.getPlayer().setDisplayName("§9"+Main.player1.getPlayer().getName());
		Main.player2.getPlayer().setDisplayName("§4"+Main.player2.getPlayer().getName());
		Main.player1.getPlayer().setPlayerListName("§9"+Main.player1.getPlayer().getName());
		Main.player2.getPlayer().setPlayerListName("§4"+Main.player2.getPlayer().getName());
	}
	
	private static void resetNameColor() {
		for (AowPlayer p : Main.aowplayers) {
			p.getBoard().addTeam(Main.player1, true);
			p.getBoard().addTeam(Main.player2, true);
			p.getPlayer().setDisplayName(p.getPlayer().getName());
			p.getPlayer().setPlayerListName(p.getPlayer().getName());
		}
	}
	
	public static void startGame() {
		setPlayers();
		Main.gameRunning = true;
		Main.gameTime = 0;
		ShopNPCManager.addNPC(Main.player1);
		ShopNPCManager.addNPC(Main.player2);
	}
	
	public static void endGame() {
		resetNameColor();
		Main.gameRunning = false;
		for (AowPlayer p : Main.aowplayers) {
			p.getPlayer().teleport(Main.spawn);
			p.getPlayer().removePotionEffect(PotionEffectType.INVISIBILITY);
			ShopNPCManager.removeNPC(p.getLeftShop(), p.getPlayer());
			ShopNPCManager.removeNPC(p.getRightShop(), p.getPlayer());
			WarriorManager.clearWarrior(p);
		}
		Main.player1 = null;
		Main.player2 = null;
		Main.aowplayers.clear();
	}
}
