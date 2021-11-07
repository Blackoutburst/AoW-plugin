package com.blackout.aow.ultimate;

import java.util.List;
import java.util.Random;

import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import com.blackout.aow.core.AowPlayer;
import com.blackout.aow.core.Core;
import com.blackout.aow.main.Main;
import com.blackout.aow.nms.NMSParticle;
import com.blackout.aow.warrior.WarriorLogical;

import net.minecraft.server.v1_8_R3.EnumParticle;

public class Heal {

	private void healParticles(WarriorLogical w) {
		for (AowPlayer p : Core.aowplayers) {
			for (int i = 0; i < 2; i++) {
				float x = (float) (w.getPosition().getX() + 0.5f + ((new Random().nextFloat() * 2) - 1));
				float y = (float) (w.getPosition().getY() + 0.5f + ((new Random().nextFloat() * 2) - 1));
				float z = (float) (w.getPosition().getZ() + 0.5f + ((new Random().nextFloat() * 2) - 1));
			
				NMSParticle.spawnParticle(p.getPlayer(), EnumParticle.VILLAGER_HAPPY, x, y, z);
			}
		}
	}
	
	private void update(List<WarriorLogical> warriors, boolean player1) {
		for (AowPlayer p : Core.aowplayers) {
			int index = 0;
			for (WarriorLogical w : warriors) {
				healParticles(w);
				
				if (w.getOptions().getHealth() < w.getOptions().getMaxHealth()) {
					w.getOptions().setHealth(w.getOptions().getHealth() + 6);
					
					if (w.getOptions().getHealth() > w.getOptions().getMaxHealth())
						w.getOptions().setHealth(w.getOptions().getMaxHealth());
					
					if (player1) {
						p.getBlueNPC().get(index).updateLifeBar(p.getPlayer(), w);
					} else {
						p.getRedNPC().get(index).updateLifeBar(p.getPlayer(), w);
					}
				}
				index++;
			}
		}
	}
	
	public void heal(AowPlayer p) {
		boolean player1 = p.getPlayer().getUniqueId().equals(Core.player1.getPlayer().getUniqueId()) ? true : false;
		List<WarriorLogical> warriors = (player1) ? Core.blueWarrior : Core.redWarrior;
		
		
		BukkitTask task = new BukkitRunnable(){
			@Override
			public void run() {
				update(warriors, player1);
			}
		}.runTaskTimer(Main.getPlugin(Main.class), 0L, 1L);
		
		new BukkitRunnable(){
			@Override
			public void run() {
				task.cancel();
			}
		}.runTaskLater(Main.getPlugin(Main.class), 200L);
	}
}
