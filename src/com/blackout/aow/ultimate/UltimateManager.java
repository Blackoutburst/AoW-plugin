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
			case PREHISTORIC: new MeteorShower().meteorShower(p); break;
			case MEDIEVAL: new ArrowShower().arrowShower(p); break;
			case RENAISSANCE: new Heal().heal(p); break;
			case MODERN: new BomberPlane().bombarder(p); break;
			case FUTURISTIC: new Lazer().lazer(p); break;
			default: break;
		}
		player.playSound(player.getLocation(), Sound.ENDERDRAGON_GROWL, 1, 1);
		p.setUltimeDelay(60);
		Utils.giveItems(p);
	}
	
}
