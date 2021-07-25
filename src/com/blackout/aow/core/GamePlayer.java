package com.blackout.aow.core;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

public class GamePlayer {

	protected Player player;
	protected int gold;
	protected int xp;
	protected List<Warrior> warriors;
	protected List<Warrior> opponents;
	protected Base base;
	
	public GamePlayer(Player player, Base base) {
		this.player = player;
		this.gold = 100;
		this.xp = 0;
		this.warriors = new ArrayList<Warrior>();
		this.opponents = new ArrayList<Warrior>();
		this.base = base;
	}
 	
	public Player getPlayer() {
		return player;
	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public int getGold() {
		return gold;
	}
	
	public void setGold(int gold) {
		this.gold = gold;
	}
	
	public int getXp() {
		return xp;
	}
	
	public void setXp(int xp) {
		this.xp = xp;
	}
	
	public List<Warrior> getWarriors() {
		return warriors;
	}
	
	public void setWarriors(List<Warrior> warriors) {
		this.warriors = warriors;
	}

	public List<Warrior> getOpponents() {
		return opponents;
	}

	public void setOpponents(List<Warrior> opponents) {
		this.opponents = opponents;
	}

	public Base getBase() {
		return base;
	}

	public void setBase(Base base) {
		this.base = base;
	}
}
