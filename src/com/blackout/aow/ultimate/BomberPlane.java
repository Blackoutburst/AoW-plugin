package com.blackout.aow.ultimate;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.FallingBlock;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import com.blackout.aow.core.AowPlayer;
import com.blackout.aow.core.Core;
import com.blackout.aow.main.Main;
import com.blackout.aow.nms.NMSParticle;
import com.blackout.aow.warrior.WarriorLogical;

import net.minecraft.server.v1_8_R3.EnumParticle;

public class BomberPlane {

	private List<PlaneBlocks> plane_blocks = new ArrayList<PlaneBlocks>();
	private List<Bomb> bombs = new ArrayList<Bomb>();
	private FallingBlock tail = null;
	
	private void killPlane() {
		for (PlaneBlocks pb : plane_blocks) {
			pb.block.remove();
			pb.support.remove();
		}
		plane_blocks.clear();
		
	}
	
	private void update(boolean player1) {
		for (PlaneBlocks pb : plane_blocks) {
			pb.support.setVelocity(new Vector(0, 0.01, (player1) ? 0.45 : -0.45));
		}
		
		for (Bomb b : bombs) {
			if (b.getEntity().isDead() && !b.isDead()) {
				hitGround(b);
			} else {
				hitWarrior(b);
				bombParticles(b);
			}
		}
		
		int size = bombs.size();
		for (int i = 0; i < size; i++) {
			Bomb b = bombs.get(i);
			if (b.isDead()) {
				bombs.remove(b);
				size--;
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	private void spawnPlane(String team, boolean player1) {
		YamlConfiguration file = YamlConfiguration.loadConfiguration(new File("plugins/AOW/"+team+"_plane.yml"));
		Set<String> blocks = file.getConfigurationSection("blocks").getKeys(false);
		
		for (String index : blocks) {
			Material material = Material.getMaterial(file.getString("blocks."+index+".type"));
			if (material.equals(Material.AIR)) continue;
			
			int data = file.getInt("blocks."+index+".data");
			int x = file.getInt("blocks."+index+".x");
			int y = file.getInt("blocks."+index+".y");
			int z = file.getInt("blocks."+index+".z");
			
			FallingBlock block = Bukkit.getWorld("world").spawnFallingBlock(new Location(Bukkit.getWorld("world"), x, y, z), material, (byte) data);
			ArmorStand support = Bukkit.getWorld("world").spawn(new Location(Bukkit.getWorld("world"), x, y, z), ArmorStand.class); 

			if (material.equals(Material.DISPENSER)) {tail = block;}
			
			support.setCustomNameVisible(false);
			support.setGravity(true);
			support.setVisible(false);
			support.setSmall(true);
			support.setVelocity(new Vector(0, 0.01, (player1) ? 0.45 : -0.45));
			support.setBasePlate(false);
			support.setPassenger(block);
			
			plane_blocks.add(new PlaneBlocks(block, support, y));
		}
	}
	
	private void planeParticles() {
		for (AowPlayer p : Core.aowplayers) {
			float x = (float) (tail.getLocation().getX() + 0.5f);
			float y = (float) (tail.getLocation().getY() + 0.5f);
			float z = (float) (tail.getLocation().getZ() + 0.5f);
			
			NMSParticle.spawnParticle(p.getPlayer(), EnumParticle.CLOUD, x, y, z);
			NMSParticle.spawnParticle(p.getPlayer(), EnumParticle.FIREWORKS_SPARK, x, y, z);
		}
	}
	
	private void bombParticles(Bomb b) {
		for (AowPlayer p : Core.aowplayers) {
			for (int i = 0; i < 5; i++) {
				float x = (float) (b.getEntity().getLocation().getX() + 0.5f + ((new Random().nextFloat() * 2) - 1) * 2);
				float y = (float) (b.getEntity().getLocation().getY() + 0.5f + ((new Random().nextFloat() * 2) - 1) * 2);
				float z = (float) (b.getEntity().getLocation().getZ() + 0.5f + ((new Random().nextFloat() * 2) - 1) * 2);
			
				NMSParticle.spawnParticle(p.getPlayer(), EnumParticle.SMOKE_NORMAL, x, y, z);
			}
			
			float x = (float) (b.getEntity().getLocation().getX() + 0.5f);
			float y = (float) (b.getEntity().getLocation().getY() + 0.5f);
			float z = (float) (b.getEntity().getLocation().getZ() + 0.5f);
		
			NMSParticle.spawnParticle(p.getPlayer(), EnumParticle.CLOUD, x, y, z);
			
		}
	}
	
	private void hitWarrior(Bomb b) {
		if (b.owner.getPlayer().getUniqueId().equals(Core.player1.getPlayer().getUniqueId())) {
			for (AowPlayer p : Core.aowplayers) {
				int index = 0;
				for (WarriorLogical w : Core.redWarrior) {
					double distance = Math.sqrt(
							Math.pow(((b.getEntity().getLocation().getX() + 0.5) - w.getPosition().getX() + 0.5), 2) +
							Math.pow(((b.getEntity().getLocation().getY() + 0.5) - w.getPosition().getY() + 0.5), 2) +
							Math.pow(((b.getEntity().getLocation().getZ() + 0.5) - w.getPosition().getZ() + 0.5), 2));
					
					if (distance < 2 && !b.isDead()) {
						w.getOptions().setHealth(w.getOptions().getHealth() - 500);
						p.getRedNPC().get(index).updateLifeBar(p.getPlayer(), w);
						b.setDead(true);
					}
					index++;
				}
			}
		} else {
			for (AowPlayer p : Core.aowplayers) {
				int index = 0;
				for (WarriorLogical w : Core.blueWarrior) {
					double distance = Math.sqrt(
							Math.pow(((b.getEntity().getLocation().getX() + 0.5) - w.getPosition().getX() + 0.5), 2) +
							Math.pow(((b.getEntity().getLocation().getY() + 0.5) - w.getPosition().getY() + 0.5), 2) +
							Math.pow(((b.getEntity().getLocation().getZ() + 0.5) - w.getPosition().getZ() + 0.5), 2));
					
					if (distance < 2 && !b.isDead()) {
						w.getOptions().setHealth(w.getOptions().getHealth() - 500);
						p.getBlueNPC().get(index).updateLifeBar(p.getPlayer(), w);
						b.setDead(true);
					}
					index++;
				}
			}
		}
	}
	
	private void hitGround(Bomb b) {
		b.setDead(true);
		
		float x = (float) (b.getEntity().getLocation().getX() + 0.5f);
		float y = (float) (b.getEntity().getLocation().getY() + 0.5f);
		float z = (float) (b.getEntity().getLocation().getZ() + 0.5f);
		
		for (AowPlayer p : Core.aowplayers) {
			p.getPlayer().playSound(b.getEntity().getLocation(), Sound.EXPLODE, 2, 1);
			NMSParticle.spawnParticle(p.getPlayer(), EnumParticle.EXPLOSION_HUGE, x, y, z);
		}
	}
	
	@SuppressWarnings("deprecation")
	private void dropBomb(AowPlayer p, boolean player1) {
		FallingBlock bomb = p.getPlayer().getLocation().getWorld().spawnFallingBlock(new Location(p.getPlayer().getWorld(), 983, tail.getLocation().getY(), tail.getLocation().getZ() + ((player1) ? 3 : -3)), Material.TNT, (byte) 0);
		bomb.setVelocity(new Vector(0, 0, 0));
		bomb.setDropItem(false);
		bombs.add(new Bomb(bomb, p));
	}
	
	public void bombarder(AowPlayer p) {
		boolean player1 = p.getPlayer().getUniqueId().equals(Core.player1.getPlayer().getUniqueId()) ? true : false;
		
		spawnPlane((player1) ? "blue" : "red", player1);
		
		BukkitTask task = new BukkitRunnable(){
			int time = 0;
			@Override
			public void run() {
				update(player1);
				planeParticles();
				time++;
				
				if (tail.getLocation().getZ() + ((player1) ? 3 : -3) > 1307 && tail.getLocation().getZ() + ((player1) ? 3 : -3) < 1345 && time % 5 == 0) {
					dropBomb(p, player1); 
				}
			}
		}.runTaskTimer(Main.getPlugin(Main.class), 0L, 1L);
		
		new BukkitRunnable(){
			@Override
			public void run() {
				killPlane();
				bombs.clear();
				task.cancel();
			}
		}.runTaskLater(Main.getPlugin(Main.class), 400L);
	}
}
