package com.blackout.aow.utils;

import org.bukkit.Material;

public class BaseBlock {
	protected Material type;
	protected int data;
	protected int x;
	protected int y;
	protected int z;
	
	public BaseBlock(Material type, int data, int x, int y, int z) {
		this.type = type;
		this.data = data;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Material getType() {
		return type;
	}

	public int getData() {
		return data;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getZ() {
		return z;
	}
}
