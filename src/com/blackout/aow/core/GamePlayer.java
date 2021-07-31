package com.blackout.aow.core;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import com.blackout.holoapi.core.Holo;

public class GamePlayer {

	public enum Age {
		prehistoric,
		medieval
	}
	
	protected Player player;
	protected int gold;
	protected int xp;
	protected List<Warrior> warriors;
	protected List<Warrior> opponents;
	protected Base base;
	protected Holo baseTitle;
	protected Holo opponentBaseTitle;
	protected Board board;
	protected Age age;
	
	public GamePlayer(Player player, Base base, Board board) {
		this.player = player;
		this.gold = 100;
		this.xp = 0;
		this.warriors = new ArrayList<Warrior>();
		this.opponents = new ArrayList<Warrior>();
		this.base = base;
		this.baseTitle = null;
		this.opponentBaseTitle = null;
		this.board = board;
		this.age = Age.prehistoric;
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

	public Holo getBaseTitle() {
		return baseTitle;
	}

	public void setBaseTitle(Holo baseTitle) {
		this.baseTitle = baseTitle;
	}

	public Holo getOpponentBaseTitle() {
		return opponentBaseTitle;
	}

	public void setOpponentBaseTitle(Holo opponentBaseTitle) {
		this.opponentBaseTitle = opponentBaseTitle;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public Age getAge() {
		return age;
	}

	public void setAge(Age age) {
		this.age = age;
	}
}
