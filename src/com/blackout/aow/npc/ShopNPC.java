package com.blackout.aow.npc;

import com.blackout.holoapi.core.Holo;
import com.blackout.npcapi.core.NPC;

public class ShopNPC {

	protected NPC npc;
	protected Holo name;
	protected Holo price;
	protected Holo hp;
	protected Holo range;
	protected Holo damage;
	protected float unitCost;
	
	public ShopNPC(NPC npc, Holo name, Holo price, Holo hp, Holo range, Holo damage, float unitCost) {
		this.npc = npc;
		this.name = name;
		this.price = price;
		this.hp = hp;
		this.range = range;
		this.damage = damage;
		this.unitCost = unitCost;
	}

	public NPC getNpc() {
		return npc;
	}

	public void setNpc(NPC npc) {
		this.npc = npc;
	}

	public Holo getName() {
		return name;
	}

	public void setName(Holo name) {
		this.name = name;
	}

	public Holo getPrice() {
		return price;
	}

	public void setPrice(Holo price) {
		this.price = price;
	}

	public float getUnitCost() {
		return unitCost;
	}

	public void setUnitCost(float unitCost) {
		this.unitCost = unitCost;
	}

	public Holo getHp() {
		return hp;
	}

	public void setHp(Holo hp) {
		this.hp = hp;
	}

	public Holo getRange() {
		return range;
	}

	public void setRange(Holo range) {
		this.range = range;
	}

	public Holo getDamage() {
		return damage;
	}

	public void setDamage(Holo damage) {
		this.damage = damage;
	}
	
}
