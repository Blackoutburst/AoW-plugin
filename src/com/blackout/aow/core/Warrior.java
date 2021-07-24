package com.blackout.aow.core;

import com.blackout.aow.main.Main;
import com.blackout.npcapi.core.NPC;

public class Warrior {

	protected NPC npc;
	protected int life;
	protected int damage;
	protected int cost;
	protected int xp;
	protected int gold;
	protected WarriorType type;
	
	
	public Warrior(NPC npc, int life, int damage, int cost, int xp, int gold, WarriorType type) {
		this.npc = npc;
		this.life = life;
		this.damage = damage;
		this.cost = cost;
		this.xp = xp;
		this.gold = gold;
		this.type = type;
	}

	public NPC getNpc() {
		return npc;
	}

	public void setNpc(NPC npc) {
		this.npc = npc;
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public int getXp() {
		return xp;
	}

	public void setXp(int xp) {
		this.xp = xp;
	}

	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}

	public WarriorType getType() {
		return type;
	}

	public void setType(WarriorType type) {
		this.type = type;
	}
	
	public boolean canWalkRight(int index) {
		if (this.getNpc().getLocation().getZ() > 1345.5f) return false;
		
		double prevZ = 0;
		double myZ = this.getNpc().getLocation().getZ();
		
		if (Main.player2NPC.size() > 0) {
			prevZ = Main.player2NPC.get(0).getNpc().getLocation().getZ();
			if (Math.abs(prevZ - myZ) < 1.5f) {
				return false;
			}
		}
		
		if (index == 0) return true;
		prevZ = Main.player1NPC.get(index - 1).getNpc().getLocation().getZ();
		
		if (Math.abs(prevZ - myZ) < 2) {
			return false;
		}
		
		return true;
	}
	
	public boolean canWalkLeft(int index) {
		if (this.getNpc().getLocation().getZ() < 1307.5f) return false;
		
		double prevZ = 0;
		double myZ = this.getNpc().getLocation().getZ();
		
		if (Main.player1NPC.size() > 0) {
			prevZ = Main.player1NPC.get(0).getNpc().getLocation().getZ();
			if (Math.abs(prevZ - myZ) < 1.5f) {
				return false;
			}
		}
		
		if (index == 0) return true;
		prevZ = Main.player2NPC.get(index - 1).getNpc().getLocation().getZ();
		
		if (Math.abs(prevZ - myZ) < 2) {
			return false;
		}
		
		return true;
	}
	
}
