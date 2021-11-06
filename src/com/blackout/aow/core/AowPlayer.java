package com.blackout.aow.core;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import com.blackout.aow.npc.ShopNPC;
import com.blackout.aow.utils.Board;
import com.blackout.aow.warrior.WarriorVisual;
import com.blackout.holoapi.core.Holo;

public class AowPlayer {
	
	protected int playerID;
	protected Player player;
	protected Board board;
	protected int xp;
	protected int gold;
	protected Ages age;
	protected float spawnUnitsDelay;
	protected float spawnUnitsDelayInit;
	protected Holo blueBaseLife;
	protected Holo redBaseLife;
	protected Holo blueBaseName;
	protected Holo redBaseName;
	protected float ultimeDelay;
	
	protected List<ShopNPC> leftShop = new ArrayList<ShopNPC>();
	protected List<ShopNPC> rightShop = new ArrayList<ShopNPC>();
	protected List<WarriorVisual> blueNPC = new ArrayList<WarriorVisual>();
	protected List<WarriorVisual> redNPC = new ArrayList<WarriorVisual>();
	
	public AowPlayer(int playerID, Player player, Board board) {
		this.playerID = playerID;
		this.player = player;
		this.board = board;
		this.xp = 100000;
		this.gold = 100;
		this.age = Ages.PREHISTORIC;
		this.spawnUnitsDelay = 0;
		this.spawnUnitsDelayInit = 0;
		this.ultimeDelay = 60;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public int getPlayerID() {
		return playerID;
	}

	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}

	public Player getPlayer() {
		return player;
	}

	public AowPlayer setPlayer(Player player) {
		this.player = player;
		return (this);
	}

	public int getXp() {
		return xp;
	}

	public AowPlayer setXp(int xp) {
		this.xp = xp;
		return (this);
	}

	public int getGold() {
		return gold;
	}

	public AowPlayer setGold(int gold) {
		this.gold = gold;
		return (this);
	}

	public Ages getAge() {
		return age;
	}

	public AowPlayer setAge(Ages age) {
		this.age = age;
		return (this);
	}
	
	public float getSpawnUnitsDelay() {
		return spawnUnitsDelay;
	}

	public AowPlayer setSpawnUnitsDelay(float spawnUnits) {
		this.spawnUnitsDelay = spawnUnits;
		return (this);
	}

	public float getSpawnUnitsDelayInit() {
		return spawnUnitsDelayInit;
	}

	public void setSpawnUnitsDelayInit(float spawnUnitsDelayInit) {
		this.spawnUnitsDelayInit = spawnUnitsDelayInit;
	}

	public List<ShopNPC> getLeftShop() {
		return leftShop;
	}

	public void setLeftShop(List<ShopNPC> leftShop) {
		this.leftShop = leftShop;
	}

	public List<ShopNPC> getRightShop() {
		return rightShop;
	}

	public void setRightShop(List<ShopNPC> rightShop) {
		this.rightShop = rightShop;
	}
	
	public List<WarriorVisual> getBlueNPC() {
		return blueNPC;
	}

	public void setBlueNPC(List<WarriorVisual> blueNPC) {
		this.blueNPC = blueNPC;
	}

	public List<WarriorVisual> getRedNPC() {
		return redNPC;
	}

	public void setRedNPC(List<WarriorVisual> redNPC) {
		this.redNPC = redNPC;
	}
	
	public Holo getBlueBaseLife() {
		return blueBaseLife;
	}

	public void setBlueBaseLife(Holo blueBaseLife) {
		this.blueBaseLife = blueBaseLife;
	}

	public Holo getRedBaseLife() {
		return redBaseLife;
	}

	public void setRedBaseLife(Holo redBaseLife) {
		this.redBaseLife = redBaseLife;
	}
	
	public Holo getBlueBaseName() {
		return blueBaseName;
	}

	public void setBlueBaseName(Holo blueBaseName) {
		this.blueBaseName = blueBaseName;
	}

	public Holo getRedBaseName() {
		return redBaseName;
	}

	public void setRedBaseName(Holo redBaseName) {
		this.redBaseName = redBaseName;
	}
	
	public float getUltimeDelay() {
		return ultimeDelay;
	}

	public void setUltimeDelay(float ultimeDelay) {
		this.ultimeDelay = ultimeDelay;
	}

	public static AowPlayer getFromPlayer(Player p) {
		for (AowPlayer ap : Core.aowplayers) {
			if (ap.player.getUniqueId().equals(p.getUniqueId())) {
				return (ap);
			}
		}
		return (null);
	}
}

