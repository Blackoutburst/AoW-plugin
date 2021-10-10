package com.blackout.aow.warrior;

public class WarriorOptions {
	
	protected float xpDrop;
	protected float goldDrop;
	protected float health;
	protected float maxHealth;
	protected float range;
	protected float damage;
	protected float cost;
	protected float heldItemID;
	protected float combatDelay;
	protected float maxCombatDelay;
	
	public WarriorOptions(float xpDrop, float goldDrop, float health, float range, float damage, float cost,
			float heldItemID, float combatDelay) {
		this.xpDrop = xpDrop;
		this.goldDrop = goldDrop;
		this.health = health;
		this.maxHealth = health;
		this.range = range;
		this.damage = damage;
		this.cost = cost;
		this.heldItemID = heldItemID;
		this.combatDelay = combatDelay;
		this.maxCombatDelay = combatDelay;
	}

	public float getXpDrop() {
		return xpDrop;
	}

	public void setXpDrop(float xpDrop) {
		this.xpDrop = xpDrop;
	}

	public float getGoldDrop() {
		return goldDrop;
	}

	public void setGoldDrop(float goldDrop) {
		this.goldDrop = goldDrop;
	}

	public float getHealth() {
		return health;
	}

	public void setHealth(float health) {
		this.health = health;
	}

	public float getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(float maxHealth) {
		this.maxHealth = maxHealth;
	}

	public float getRange() {
		return range;
	}

	public void setRange(float range) {
		this.range = range;
	}

	public float getDamage() {
		return damage;
	}

	public void setDamage(float damage) {
		this.damage = damage;
	}

	public float getCost() {
		return cost;
	}

	public void setCost(float cost) {
		this.cost = cost;
	}

	public float getHeldItemID() {
		return heldItemID;
	}

	public void setHeldItemID(float heldItemID) {
		this.heldItemID = heldItemID;
	}

	public float getCombatDelay() {
		return combatDelay;
	}

	public void setCombatDelay(float combatDelay) {
		this.combatDelay = combatDelay;
	}

	public float getMaxCombatDelay() {
		return maxCombatDelay;
	}

	public void setMaxCombatDelay(float maxCombatDelay) {
		this.maxCombatDelay = maxCombatDelay;
	}
	
}
