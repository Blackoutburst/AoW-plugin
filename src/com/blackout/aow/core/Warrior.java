package com.blackout.aow.core;

import org.bukkit.Location;

import com.blackout.npcapi.core.NPC;

public class Warrior {
	
	protected NPC npc;
	protected AowPlayer owner;
	protected WarriorOptions options;
	protected Location position;
	protected boolean dead;
	
	public Warrior(NPC npc, AowPlayer owner, WarriorOptions options, Location position) {
		this.npc = npc;
		this.owner = owner;
		this.options = options;
		this.position = position;
		this.dead = false;
	}

	public NPC getNpc() {
		return npc;
	}

	public void setNpc(NPC npc) {
		this.npc = npc;
	}

	public AowPlayer getOwner() {
		return owner;
	}

	public void setOwner(AowPlayer owner) {
		this.owner = owner;
	}

	public WarriorOptions getOptions() {
		return options;
	}

	public void setOptions(WarriorOptions options) {
		this.options = options;
	}

	public Location getPosition() {
		return position;
	}

	public void setPosition(Location position) {
		this.position = position;
	}

	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}
	
}
