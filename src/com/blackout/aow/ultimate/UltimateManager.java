package com.blackout.aow.ultimate;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.blackout.aow.core.AowPlayer;
import com.blackout.aow.utils.Utils;

public class UltimateManager {
	
	@SuppressWarnings("deprecation")
	private static void meteorShower(AowPlayer p) {
		for (int i = 0; i < 20; i++) {
			FallingBlock meteor = p.getPlayer().getLocation().getWorld().spawnFallingBlock(new Location(p.getPlayer().getWorld(), 983, (70 + 2 * i), 1307 + new Random().nextInt(37)), Material.COBBLESTONE, (byte) 0);
			meteor.setVelocity(new Vector(0, 0, ((new Random().nextFloat() * 2) - 1) / 10));
			meteor.setDropItem(false);
		}
	}
	
	public static void runUltimate(Player player) {
		AowPlayer p = AowPlayer.getFromPlayer(player);
		
		if (p.getUltimeDelay() > 0) {
			p.getPlayer().playSound(p.getPlayer().getLocation(), Sound.VILLAGER_NO, 1, 1);
			p.getPlayer().sendMessage("§cYou need to wait another §e"+p.getUltimeDelay()+"s §cbefore usging that");
			return;
		}
		
		switch(p.getAge()) {
			case PREHISTORIC: meteorShower(p); break;
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
