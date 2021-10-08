package com.blackout.aow.core;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import com.blackout.aow.main.Main;
import com.blackout.aow.utils.Board;
import com.blackout.npcapi.core.NPC;

public class AowPlayer {
	
	protected int playerID;
	protected Player player;
	protected Board board;
	protected int xp;
	protected int gold;
	protected Ages age;
	
	protected List<NPC> leftShop = new ArrayList<NPC>();
	protected List<NPC> rightShop = new ArrayList<NPC>();
	
	public AowPlayer(int playerID, Player player, Board board) {
		this.playerID = playerID;
		this.player = player;
		this.board = board;
		this.xp = 0;
		this.gold = 100;
		this.age = Ages.PREHISTORIC;
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

	public List<NPC> getLeftShop() {
		return leftShop;
	}

	public void setLeftShop(List<NPC> leftShop) {
		this.leftShop = leftShop;
	}

	public List<NPC> getRightShop() {
		return rightShop;
	}

	public void setRightShop(List<NPC> rightShop) {
		this.rightShop = rightShop;
	}
	
	public static AowPlayer getFromPlayer(Player p) {
		for (AowPlayer ap : Main.aowplayers) {
			if (ap.player.getUniqueId().equals(p.getUniqueId())) {
				return (ap);
			}
		}
		return (null);
	}
}

