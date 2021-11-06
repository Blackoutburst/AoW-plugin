package com.blackout.aow.ultimate;

import org.bukkit.entity.Arrow;

import com.blackout.aow.core.AowPlayer;

public class RainArrow {
	protected Arrow entity;
	protected AowPlayer owner;
	protected boolean dead;
	
	public RainArrow(Arrow entity, AowPlayer owner) {
		this.entity = entity;
		this.owner = owner;
		this.dead = false;
	}

	public Arrow getEntity() {
		return entity;
	}

	public void setEntity(Arrow entity) {
		this.entity = entity;
	}

	public AowPlayer getOwner() {
		return owner;
	}

	public void setOwner(AowPlayer owner) {
		this.owner = owner;
	}

	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}
}
