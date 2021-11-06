package com.blackout.aow.ultimate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.FallingBlock;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.blackout.aow.core.AowPlayer;
import com.blackout.aow.core.Core;
import com.blackout.aow.main.Main;
import com.blackout.aow.nms.NMSParticle;

import net.minecraft.server.v1_8_R3.EnumParticle;

public class MeteorShower {

	private List<Meteor> meteors = new ArrayList<Meteor>();
	
	@SuppressWarnings("deprecation")
	private void init(AowPlayer p) {
		for (int i = 0; i < 20; i++) {
			FallingBlock meteor = p.getPlayer().getLocation().getWorld().spawnFallingBlock(new Location(p.getPlayer().getWorld(), 983, (70 + 5 * i), 1307 + new Random().nextInt(37)), Material.COBBLESTONE, (byte) 0);
			meteor.setVelocity(new Vector(0, 0, ((float)(new Random().nextFloat() * 2) - 1) / 2.0f));
			meteor.setDropItem(false);
			meteors.add(new Meteor(meteor, p));
		}
	}
	
	private void meteorParticles(Meteor m) {
		for (AowPlayer p : Core.aowplayers) {
			for (int i = 0; i < 5; i++) {
				float x = (float) (m.getEntity().getLocation().getX() + 0.5f + ((new Random().nextFloat() * 2) - 1) * 2);
				float y = (float) (m.getEntity().getLocation().getY() + 0.5f + ((new Random().nextFloat() * 2) - 1) * 2);
				float z = (float) (m.getEntity().getLocation().getZ() + 0.5f + ((new Random().nextFloat() * 2) - 1) * 2);
			
				NMSParticle.spawnParticle(p.getPlayer(), EnumParticle.FLAME, x, y, z);
			}
			
			float x = (float) (m.getEntity().getLocation().getX() + 0.5f);
			float y = (float) (m.getEntity().getLocation().getY() + 0.5f);
			float z = (float) (m.getEntity().getLocation().getZ() + 0.5f);
		
			NMSParticle.spawnParticle(p.getPlayer(), EnumParticle.EXPLOSION_NORMAL, x, y, z);
		}
	}
	
	private void hitGround(Meteor m) {
		m.setDead(true);
		
		float x = (float) (m.getEntity().getLocation().getX() + 0.5f);
		float y = (float) (m.getEntity().getLocation().getY() + 0.5f);
		float z = (float) (m.getEntity().getLocation().getZ() + 0.5f);
		
		for (AowPlayer p : Core.aowplayers) {
			p.getPlayer().playSound(m.getEntity().getLocation(), Sound.EXPLODE, 1, 1);
			NMSParticle.spawnParticle(p.getPlayer(), EnumParticle.EXPLOSION_HUGE, x, y, z);
		}
	}
	
	private void update() {
		for (Meteor m : meteors) {
			if (m.getEntity().isDead() && !m.isDead()) {
				hitGround(m);
			} else {
				meteorParticles(m);
			}
		}
		int size = meteors.size();
		for (int i = 0; i < size; i++) {
			Meteor m = meteors.get(i);
			if (m.isDead()) {
				meteors.remove(m);
				size--;
			}
		}
	}
	
	public void meteorShower(AowPlayer p) {
		init(p);
		
		new BukkitRunnable(){
			@Override
			public void run() {
				update();
				if (meteors.size() == 0) {
					this.cancel();
				}
			}
		}.runTaskTimer(Main.getPlugin(Main.class), 0L, 1L);
	}
}
