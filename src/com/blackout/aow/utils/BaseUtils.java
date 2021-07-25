package com.blackout.aow.utils;


import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.blackout.aow.core.Base;
import com.blackout.aow.core.GamePlayer;
import com.blackout.aow.core.Warrior;
import com.blackout.aow.main.Main;
import com.blackout.holoapi.core.Holo;
import com.blackout.holoapi.utils.HoloManager;

public class BaseUtils {

	public static void updateLife(Warrior warrior, Base base, GamePlayer gp) {
		base.setLife(base.getLife() - warrior.getDamage());
		int lifePercent = (base.getLife() * 100 / base.getMaxLife());
		
		Holo lifeBar = base.getLifeBar();
		lifeBar.getEntity().setCustomName(getLifeBar(lifePercent));
		HoloManager.hideHolo(gp.getPlayer(), lifeBar);
		HoloManager.reloadHolo(gp.getPlayer(), lifeBar);
		if (base.getLife() <= 0) {
			killBase(base, gp);
		}
	}
	
	private static void killBase(Base base, GamePlayer gp) {
		GameUtils.endGame(base, gp);
		
		Main.player1 = null;
		Main.player2 = null;
	}
	
	private static String getLifeBar(int lifePercent) {
		if (lifePercent <= 5) return "§c████████████████████";
		if (lifePercent <= 10) return "§a█§c███████████████████";
		if (lifePercent <= 15) return "§a██§c██████████████████";
		if (lifePercent <= 20) return "§a███§c█████████████████";
		if (lifePercent <= 25) return "§a████§c████████████████";
		if (lifePercent <= 30) return "§a█████§c███████████████";
		if (lifePercent <= 35) return "§a██████§c██████████████";
		if (lifePercent <= 40) return "§a███████§c█████████████";
		if (lifePercent <= 45) return "§a████████§c████████████";
		if (lifePercent <= 50) return "§a█████████§c███████████";
		if (lifePercent <= 55) return "§a██████████§c██████████";
		if (lifePercent <= 60) return "§a███████████§c█████████";
		if (lifePercent <= 65) return "§a████████████§c████████";
		if (lifePercent <= 70) return "§a█████████████§c███████";
		if (lifePercent <= 75) return "§a██████████████§c██████";
		if (lifePercent <= 80) return "§a███████████████§c█████";
		if (lifePercent <= 85) return "§a████████████████§c████";
		if (lifePercent <= 90) return "§a█████████████████§c███";
		if (lifePercent <= 95) return "§a██████████████████§c██";
		if (lifePercent < 100) return "§a███████████████████§c█";
		return "§a████████████████████";
	}
	
	public static Holo spawnHealthBar(Player p, boolean left) {
		Holo leftLifeBar = new Holo(UUID.randomUUID(), "§a████████████████████")
				.setLocation(new Location(Bukkit.getWorld("world"), 978.5f, 55.7f, 1307.5f, 0, 0));
		HoloManager.spawnHolo(leftLifeBar, p);
		
		Holo rightLifeBar = new Holo(UUID.randomUUID(), "§a████████████████████")
				.setLocation(new Location(Bukkit.getWorld("world"), 978.5f, 55.7f, 1345.5f, 0, 0));
		HoloManager.spawnHolo(rightLifeBar, p);
		
		return (left ? leftLifeBar : rightLifeBar);
	}
	
	public static void spawnHealthBarTitle(GamePlayer p, boolean left) {
		String str = left ? "§9Your base health" : "§9Enemy base health";
		
		Holo lifeBar = new Holo(UUID.randomUUID(), str)
				.setLocation(new Location(Bukkit.getWorld("world"), 978.5f, 56, 1307.5f, 0, 0));
		HoloManager.spawnHolo(lifeBar, p.getPlayer());
		if (left) {
			p.setBaseTitle(lifeBar);
		} else {
			p.setOpponentBaseTitle(lifeBar);
		}
		
		str = left ? "§4Enemy base health" : "§4Your base health";
		
		lifeBar = new Holo(UUID.randomUUID(), str)
				.setLocation(new Location(Bukkit.getWorld("world"), 978.5f, 56, 1345.5f, 0, 0));
		HoloManager.spawnHolo(lifeBar, p.getPlayer());
		if (left) {
			p.setOpponentBaseTitle(lifeBar);
		} else {
			p.setBaseTitle(lifeBar);
		}
	}
}
