package com.blackout.aow.ultimate;


import org.bukkit.entity.FallingBlock;

import com.blackout.aow.core.AowPlayer;

public class Meteor {
	protected FallingBlock entity;
	protected AowPlayer owner;
	protected boolean dead;
	
	public Meteor(FallingBlock entity, AowPlayer owner) {
		this.entity = entity;
		this.owner = owner;
		this.dead = false;
	}

	public FallingBlock getEntity() {
		return entity;
	}

	public void setEntity(FallingBlock entity) {
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
