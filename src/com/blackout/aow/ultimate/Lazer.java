package com.blackout.aow.ultimate;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

import com.blackout.aow.core.AowPlayer;
import com.blackout.aow.core.Core;
import com.blackout.aow.main.Main;
import com.blackout.aow.nms.NMSParticle;
import com.blackout.aow.warrior.WarriorLogical;

import net.minecraft.server.v1_8_R3.EnumParticle;

public class Lazer {

	private void particles(Location l) {
		for (AowPlayer p : Core.aowplayers) {
			NMSParticle.spawnParticle(p.getPlayer(), EnumParticle.EXPLOSION_HUGE, (float) l.getX(), (float) l.getY(), (float) l.getZ());
		}
	}
	
	private void spawnLightning(Location l) {
		Bukkit.getWorld("world").strikeLightningEffect(l);
	}
	
	private void update(AowPlayer player, Location l) {
		spawnLightning(l);
		particles(l);
		hitWarrior(player, l);
	}
	
	
	private void hitWarrior(AowPlayer player, Location l) {
		if (player.getPlayer().getUniqueId().equals(Core.player1.getPlayer().getUniqueId())) {
			for (AowPlayer p : Core.aowplayers) {
				int index = 0;
				for (WarriorLogical w : Core.redWarrior) {
					double distance = Math.sqrt(
							Math.pow(((l.getX() + 0.5) - w.getPosition().getX() + 0.5), 2) +
							Math.pow(((l.getY() + 0.5) - w.getPosition().getY() + 0.5), 2) +
							Math.pow(((l.getZ() + 0.5) - w.getPosition().getZ() + 0.5), 2));
					
					if (distance < 4) {
						w.getOptions().setHealth(w.getOptions().getHealth() - 800);
						p.getRedNPC().get(index).updateLifeBar(p.getPlayer(), w);
					}
					index++;
				}
			}
		} else {
			for (AowPlayer p : Core.aowplayers) {
				int index = 0;
				for (WarriorLogical w : Core.blueWarrior) {
					double distance = Math.sqrt(
							Math.pow(((l.getX() + 0.5) - w.getPosition().getX() + 0.5), 2) +
							Math.pow(((l.getY() + 0.5) - w.getPosition().getY() + 0.5), 2) +
							Math.pow(((l.getZ() + 0.5) - w.getPosition().getZ() + 0.5), 2));
					
					if (distance < 4) {
						w.getOptions().setHealth(w.getOptions().getHealth() - 800);
						p.getBlueNPC().get(index).updateLifeBar(p.getPlayer(), w);
					}
					index++;
				}
			}
		}
	}
	
	public void lazer(AowPlayer p) {
		boolean player1 = p.getPlayer().getUniqueId().equals(Core.player1.getPlayer().getUniqueId()) ? true : false;
		
		new BukkitRunnable(){
			int z = (player1) ? 1307 : 1345;
			@Override
			public void run() {
				z += (player1) ? 2 : -2;
				
				if (z < 1307 || z > 1345) {
					this.cancel();
					return;
				}
				
				Location l = new Location(Bukkit.getWorld("world"), 983.5f, 54, z + 0.5f);
				
				update(p, l);
			}
		}.runTaskTimer(Main.getPlugin(Main.class), 0L, 2L);
		
	}
}
