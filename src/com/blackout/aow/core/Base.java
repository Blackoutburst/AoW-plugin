package com.blackout.aow.core;

import com.blackout.holoapi.core.Holo;

public class Base {
	protected float Z;
	protected Holo lifeBar;
	protected int life;
	protected int maxLife;
	
	public Base(float Z, Holo lifeBar) {
		this.Z = Z;
		this.lifeBar = lifeBar;
		this.life = 3000;
		this.maxLife = 3000;
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

	public int getMaxLife() {
		return maxLife;
	}

	public void setMaxLife(int maxLife) {
		this.maxLife = maxLife;
	}
}
