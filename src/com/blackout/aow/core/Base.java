package com.blackout.aow.core;

import com.blackout.holoapi.core.Holo;

public class Base {
	protected float Z;
	protected Holo lifeBar;
	protected int life;
	
	public Base(float Z, Holo lifeBar) {
		this.Z = Z;
		this.lifeBar = lifeBar;
		this.life = 10000;
	}

	public float getZ() {
		return Z;
	}

	public void setZ(float z) {
		Z = z;
	}

	public Holo getLifeBar() {
		return lifeBar;
	}

	public void setLifeBar(Holo lifeBar) {
		this.lifeBar = lifeBar;
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}
	
}
