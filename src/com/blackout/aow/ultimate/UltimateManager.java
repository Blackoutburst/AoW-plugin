package com.blackout.aow.ultimate;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.blackout.aow.core.AowPlayer;
import com.blackout.aow.utils.Utils;

public class UltimateManager {
	
	public static void runUltimate(Player player) {
		AowPlayer p = AowPlayer.getFromPlayer(player);
		
		if (p.getUltimeDelay() > 0) {
			p.getPlayer().playSound(p.getPlayer().getLocation(), Sound.VILLAGER_NO, 1, 1);
			p.getPlayer().sendMessage("§cYou need to wait another §e"+p.getUltimeDelay()+"s §cbefore usging that");
			return;
		}
		
		switch(p.getAge()) {
			case PREHISTORIC: break;
			case MEDIEVAL: break;
			case RENAISSANCE: break;
			case MODERN: break;
			case FUTURISTIC: break;
			default: break;
		}
		p.setUltimeDelay(60);
		Utils.giveItems(p);
	}
	
}
