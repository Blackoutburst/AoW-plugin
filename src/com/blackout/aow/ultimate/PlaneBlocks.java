package com.blackout.aow.ultimate;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.FallingBlock;

public class PlaneBlocks {
	FallingBlock block;
	ArmorStand support;
	double originalY;
	
	public PlaneBlocks(FallingBlock block, ArmorStand support, double originalY) {
		this.block = block;
		this.support = support;
		this.originalY = originalY;
	}

	public FallingBlock getBlock() {
		return block;
	}

	public void setBlock(FallingBlock block) {
		this.block = block;
	}

	public ArmorStand getSupport() {
		return support;
	}

	public void setSupport(ArmorStand support) {
		this.support = support;
	}
	
}
