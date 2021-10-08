package com.blackout.aow.core;

public class WarriorOptions {
	
	protected int xpDrop;
	protected int goldDrop;
	protected int health;
	protected int maxHealth;
	protected float range;
	protected int damage;
	protected int cost;
	protected int heldItemID;
	protected int combatDelay;
	
	public WarriorOptions(int xpDrop, int goldDrop, int health, int maxHealth, float range, int damage, int cost,
			int heldItemID, int combatDelay) {
		this.xpDrop = xpDrop;
		this.goldDrop = goldDrop;
		this.health = health;
		this.maxHealth = maxHealth;
		this.range = range;
		this.damage = damage;
		this.cost = cost;
		this.heldItemID = heldItemID;
		this.combatDelay = combatDelay;
	}

	public int getXpDrop() {
		return xpDrop;
	}

	public void setXpDrop(int xpDrop) {
		this.xpDrop = xpDrop;
	}

	public int getGoldDrop() {
		return goldDrop;
	}

	public void setGoldDrop(int goldDrop) {
		this.goldDrop = goldDrop;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}

	public float getRange() {
		return range;
	}

	public void setRange(float range) {
		this.range = range;
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

	public int getHeldItemID() {
		return heldItemID;
	}

	public void setHeldItemID(int heldItemID) {
		this.heldItemID = heldItemID;
	}

	public int getCombatDelay() {
		return combatDelay;
	}

	public void setCombatDelay(int combatDelay) {
		this.combatDelay = combatDelay;
	}
	
}
