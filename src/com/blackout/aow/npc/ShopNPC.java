package com.blackout.aow.npc;

import com.blackout.holoapi.core.Holo;
import com.blackout.npcapi.core.NPC;

public class ShopNPC {

	protected NPC npc;
	protected Holo name;
	protected Holo price;
	protected int unitCost;
	
	public ShopNPC(NPC npc, Holo name, Holo price, int unitCost) {
		this.npc = npc;
		this.name = name;
		this.price = price;
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

	public int getUnitCost() {
		return unitCost;
	}

	public void setUnitCost(int unitCost) {
		this.unitCost = unitCost;
	}
	
}
