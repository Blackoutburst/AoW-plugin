package com.blackout.aow.ultimate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.blackout.aow.core.AowPlayer;
import com.blackout.aow.core.Core;
import com.blackout.aow.main.Main;
import com.blackout.aow.nms.NMSParticle;
import com.blackout.aow.warrior.WarriorLogical;

import net.minecraft.server.v1_8_R3.EnumParticle;

public class ArrowShower {

	private List<RainArrow> arrows = new ArrayList<RainArrow>();
	
	private void init(AowPlayer p) {
		for (int i = 0; i < 60; i++) {
			Arrow arrow = p.getPlayer().getLocation().getWorld().spawnArrow(new Location(p.getPlayer().getWorld(), 983, (80 + 2 * i), 1307 + new Random().nextInt(37)), new Vector(0, 0, 0), 0, 0);
			arrow.setVelocity(new Vector(0, 0, ((float)(new Random().nextFloat() * 2) - 1) / 2.0f));
			arrows.add(new RainArrow(arrow, p));
		}
	}
	
	private void arrowParticles(RainArrow a) {
		for (AowPlayer p : Core.aowplayers) {
			for (int i = 0; i < 5; i++) {
				float x = (float) (a.getEntity().getLocation().getX());
				float y = (float) (a.getEntity().getLocation().getY());
				float z = (float) (a.getEntity().getLocation().getZ());
			
				NMSParticle.spawnParticle(p.getPlayer(), EnumParticle.FLAME, x, y, z);
			}
		}
	}
	
	private void hitGround(RainArrow a) {
		a.setDead(true);
		
		for (AowPlayer p : Core.aowplayers) {
			p.getPlayer().playSound(a.getEntity().getLocation(), Sound.ARROW_HIT, 1, 1);
		}
	}
	
	private void hitWarrior(RainArrow a) {
		if (a.owner.getPlayer().getUniqueId().equals(Core.player1.getPlayer().getUniqueId())) {
			for (AowPlayer p : Core.aowplayers) {
				int index = 0;
				for (WarriorLogical w : Core.redWarrior) {
					double distance = Math.sqrt(
							Math.pow(((a.getEntity().getLocation().getX() + 0.5) - w.getPosition().getX() + 0.5), 2) +
							Math.pow(((a.getEntity().getLocation().getY() + 0.5) - w.getPosition().getY() + 0.5), 2) +
							Math.pow(((a.getEntity().getLocation().getZ() + 0.5) - w.getPosition().getZ() + 0.5), 2));
					
					if (distance < 2 && !a.isDead()) {
						w.getOptions().setHealth(w.getOptions().getHealth() - 300);
						p.getRedNPC().get(index).updateLifeBar(p.getPlayer(), w);
						a.setDead(true);
					}
					index++;
				}
			}
		} else {
			for (AowPlayer p : Core.aowplayers) {
				int index = 0;
				for (WarriorLogical w : Core.blueWarrior) {
					double distance = Math.sqrt(
							Math.pow(((a.getEntity().getLocation().getX() + 0.5) - w.getPosition().getX() + 0.5), 2) +
							Math.pow(((a.getEntity().getLocation().getY() + 0.5) - w.getPosition().getY() + 0.5), 2) +
							Math.pow(((a.getEntity().getLocation().getZ() + 0.5) - w.getPosition().getZ() + 0.5), 2));
					
					if (distance < 2 && !a.isDead()) {
						w.getOptions().setHealth(w.getOptions().getHealth() - 300);
						p.getBlueNPC().get(index).updateLifeBar(p.getPlayer(), w);
						a.setDead(true);
					}
					index++;
				}
			}
		}
	}
	
	private void update() {
		for (RainArrow a : arrows) {
			if (a.getEntity().isOnGround() && !a.isDead()) {
				hitGround(a);
			} else {
				hitWarrior(a);
				arrowParticles(a);
			}
		}
		int size = arrows.size();
		for (int i = 0; i < size; i++) {
			RainArrow a = arrows.get(i);
			if (a.isDead()) {
				a.getEntity().remove();
				arrows.remove(a);
				size--;
			}
		}
	}
	
	public void arrowShower(AowPlayer p) {
		init(p);
		
		new BukkitRunnable(){
			@Override
			public void run() {
				update();
				if (arrows.size() == 0) {
					this.cancel();
				}
			}
		}.runTaskTimer(Main.getPlugin(Main.class), 0L, 1L);
	}
}
