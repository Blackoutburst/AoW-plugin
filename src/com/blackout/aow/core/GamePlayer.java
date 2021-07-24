package com.blackout.aow.core;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

public class GamePlayer {

	protected Player player;
	protected int gold;
	protected int xp;
	protected List<Warrior> warriors;
	
	public GamePlayer(Player player) {
		this.player = player;
		this.gold = 100;
		this.xp = 0;
		this.warriors = new ArrayList<Warrior>();
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
	
}
