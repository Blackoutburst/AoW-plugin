package com.blackout.aow.core;

import java.util.UUID;

import org.bukkit.Location;

import com.blackout.holoapi.core.Holo;
import com.blackout.holoapi.utils.HoloManager;

public class Base {
	protected AowPlayer owner;
	protected int life;
	protected int maxLife;
	protected Location location;
	
	public Base(AowPlayer owner, int life, Location location) {
		this.owner = owner;
		this.life = life;
		this.maxLife = life;
		this.location = location;
	}

	public void updateLifeBar(AowPlayer p, Holo lifeBar, boolean blue) {
		int lifePercent = (int) (this.life * 100 / this.maxLife);
		Location loc = lifeBar.getLocation().clone();
		
		HoloManager.deleteHolo(p.getPlayer(), lifeBar);
		
		Holo life = new Holo(UUID.randomUUID(), getLifeBarString(lifePercent))
		        .setLocation(loc);
		HoloManager.spawnHolo(life, p.getPlayer());
		if (blue) {
			p.setBlueBaseLife(life);
		} else {
			p.setRedBaseLife(life);
		}
		
		if (this.life <= 0) {
			Core.endGame();
		}
	}
	
	private String getLifeBarString(int lifePercent) {
		if (lifePercent <= 0) return "§7[§8████████████████████§7]";
		if (lifePercent <= 10) return "§7[§4█§8███████████████████§7]";
		if (lifePercent <= 15) return "§7[§4██§8██████████████████§7]";
		if (lifePercent <= 20) return "§7[§4███§8█████████████████§7]";
		if (lifePercent <= 25) return "§7[§c████§8████████████████§7]";
		if (lifePercent <= 30) return "§7[§c█████§8███████████████§7]";
		if (lifePercent <= 35) return "§7[§c██████§8██████████████§7]";
		if (lifePercent <= 40) return "§7[§c███████§8█████████████§7]";
		if (lifePercent <= 45) return "§7[§6████████§8████████████§7]";
		if (lifePercent <= 50) return "§7[§6█████████§8███████████§7]";
		if (lifePercent <= 55) return "§7[§6██████████§8██████████§7]";
		if (lifePercent <= 60) return "§7[§6███████████§8█████████§7]";
		if (lifePercent <= 65) return "§7[§e████████████§8████████§7]";
		if (lifePercent <= 70) return "§7[§e█████████████§8███████§7]";
		if (lifePercent <= 75) return "§7[§e██████████████§8██████§7]";
		if (lifePercent <= 80) return "§7[§e███████████████§8█████§7]";
		if (lifePercent <= 85) return "§7[§a████████████████§8████§7]";
		if (lifePercent <= 90) return "§7[§a█████████████████§8███§7]";
		if (lifePercent <= 95) return "§7[§a██████████████████§8██§7]";
		if (lifePercent < 100) return "§7[§a███████████████████§8█§7]";
		return "§7[§a████████████████████§7]";
	}
	
	public AowPlayer getOwner() {
		return owner;
	}

	public void setOwner(AowPlayer owner) {
		this.owner = owner;
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
		if (this.life < 0) {
			this.life = 0;
		}
	}

	public int getMaxLife() {
		return maxLife;
	}

	public void setMaxLife(int maxLife) {
		this.maxLife = maxLife;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
	
}
