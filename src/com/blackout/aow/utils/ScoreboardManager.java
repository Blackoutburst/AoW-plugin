package com.blackout.aow.utils;

import com.blackout.aow.core.AowPlayer;
import com.blackout.aow.core.Core;

public class ScoreboardManager {
	
	private static String getBaseLifeColor(int lifePercent) {
		if (lifePercent <= 0) return "§8";
		if (lifePercent <= 10) return "§4";
		if (lifePercent <= 15) return "§4";
		if (lifePercent <= 20) return "§4";
		if (lifePercent <= 25) return "§c";
		if (lifePercent <= 30) return "§c";
		if (lifePercent <= 35) return "§c";
		if (lifePercent <= 40) return "§c";
		if (lifePercent <= 45) return "§6";
		if (lifePercent <= 50) return "§6";
		if (lifePercent <= 55) return "§6";
		if (lifePercent <= 60) return "§6";
		if (lifePercent <= 65) return "§e";
		if (lifePercent <= 70) return "§e";
		if (lifePercent <= 75) return "§e";
		if (lifePercent <= 80) return "§e";
		if (lifePercent <= 85) return "§a";
		if (lifePercent <= 90) return "§a";
		if (lifePercent <= 95) return "§a";
		if (lifePercent < 100) return "§a";
		return "§a";
	}
	
	public static void init(AowPlayer p) {
		p.getBoard().setTitle("§6§lAGE OF WAR");
		p.getBoard().set(15, "§8§m--------------------"); 
		p.getBoard().set(14, "§7Game time: 0:00");
		p.getBoard().set(13, " "); 
		p.getBoard().set(12, Core.player1.getPlayer().getDisplayName() + " §9base");
		p.getBoard().set(11, getBaseLifeColor((int) (Core.blueBase.getLife() * 100 / Core.blueBase.getMaxLife()))+Core.blueBase.getLife()+" §7/ §a"+Core.blueBase.getMaxLife()+" ");
		p.getBoard().set(10, "  "); 
		p.getBoard().set(9, Core.player2.getPlayer().getDisplayName() + " §4base");
		p.getBoard().set(8, getBaseLifeColor((int) (Core.redBase.getLife() * 100 / Core.redBase.getMaxLife()))+Core.redBase.getLife()+" §7/ §a"+Core.redBase.getMaxLife());
		p.getBoard().set(7, "   "); 

		if (p == Core.player1) {
			p.getBoard().set(6, "§6Gold: §f"+p.getGold());
			p.getBoard().set(5, "§bXp: §f"+p.getXp());
			p.getBoard().set(4, "    ");
			p.getBoard().set(3, "§enot.hypixel.yet"); 
		} else if (p == Core.player2) {
			p.getBoard().set(6, "§6Gold: §f"+p.getGold());
			p.getBoard().set(5, "§bXp: §f"+p.getXp());
			p.getBoard().set(4, "    ");
			p.getBoard().set(3, "§enot.hypixel.yet"); 
		} else {
			p.getBoard().set(6, "§enot.hypixel.yet"); 
		}
	}
	
	public static void update(AowPlayer p) {
		int minutes = Core.gameTime / 60;
		int seconds = Core.gameTime % 60;
		String time = String.format("%d:%02d", minutes, seconds);
		
		p.getBoard().set(14, "§7Game time: "+time);
		
		p.getBoard().set(11, getBaseLifeColor((int) (Core.blueBase.getLife() * 100 / Core.blueBase.getMaxLife()))+Core.blueBase.getLife()+" §7/ §a"+Core.blueBase.getMaxLife()+" ");
		p.getBoard().set(8, getBaseLifeColor((int) (Core.redBase.getLife() * 100 / Core.redBase.getMaxLife()))+Core.redBase.getLife()+" §7/ §a"+Core.redBase.getMaxLife());
	
		if (p == Core.player1) {
			p.getBoard().set(6, "§6Gold: §f"+p.getGold());
			p.getBoard().set(5, "§bXp: §f"+p.getXp());
		} else if (p == Core.player2) {
			p.getBoard().set(6, "§6Gold: §f"+p.getGold());
			p.getBoard().set(5, "§bXp: §f"+p.getXp());
		}
	}
	
	public static void clear(AowPlayer p) {
		for (int i = 15; i >= 0; i--)
			p.getBoard().remove(i);
	}
}
