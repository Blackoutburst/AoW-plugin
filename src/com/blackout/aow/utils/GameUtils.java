package com.blackout.aow.utils;

import org.bukkit.scheduler.BukkitRunnable;

import com.blackout.aow.core.Base;
import com.blackout.aow.core.Board;
import com.blackout.aow.core.GamePlayer;
import com.blackout.aow.core.Warrior;
import com.blackout.aow.main.Main;
import com.blackout.holoapi.utils.HoloManager;
import com.blackout.npcapi.utils.NPCManager;

public class GameUtils {

	/**
	 * Clean game value when a game is over
	 * @param base
	 * @param gp
	 */
	public static void endGame(Base base, GamePlayer gp) {
		Utils.resetNameColor(gp.getPlayer());
		sendTitle(base, gp);
		deleteNPCandHolo(gp);
		GameUtils.updateScoreboard(gp);
		Main.gameRunning = false;
		
		new BukkitRunnable() {
			public void run() {
				gp.getPlayer().teleport(Main.spawn);
			}
		}.runTaskLater(Main.getPlugin(Main.class), 100L);
	}
	
	/**
	 * Send the end game title saying if you won or lose the game
	 * @param base
	 * @param gp
	 */
	private static void sendTitle(Base base, GamePlayer gp) {
		if (base == gp.getBase()) {
			Utils.sendTitle(gp.getPlayer(), "§6Game Ended", "§cYou lose!", 0, 100, 20);
		} else {
			Utils.sendTitle(gp.getPlayer(), "§6Game Ended", "§aYou won!", 0, 100, 20);
		}
	}
	
	/**
	 * Clean all holograms and NPC 
	 * @param gp
	 */
	private static void deleteNPCandHolo(GamePlayer gp) {
		for (Warrior w : gp.getWarriors()) {
			HoloManager.deleteHolo(gp.getPlayer(), w.getLifeBar());
			NPCManager.deleteNPC(gp.getPlayer(), w.getNpc());
			
			HoloManager.deleteHolo(gp.getPlayer(), w.getLifeBar());
			NPCManager.deleteNPC(gp.getPlayer(), w.getNpc());
		}
		
		for (Warrior w : gp.getOpponents()) {
			HoloManager.deleteHolo(gp.getPlayer(), w.getLifeBar());
			NPCManager.deleteNPC(gp.getPlayer(), w.getNpc());
			
			HoloManager.deleteHolo(gp.getPlayer(), w.getLifeBar());
			NPCManager.deleteNPC(gp.getPlayer(), w.getNpc());
		}
		HoloManager.deleteHolo(gp.getPlayer(), Main.player1.getBase().getLifeBar());
		HoloManager.deleteHolo(gp.getPlayer(), Main.player2.getBase().getLifeBar());
		HoloManager.deleteHolo(gp.getPlayer(), gp.getBaseTitle());
		HoloManager.deleteHolo(gp.getPlayer(), gp.getOpponentBaseTitle());
	}
	
	/**
	 * Set the default scoreboard
	 * @param board
	 */
	public static void setDefaultScoreboard(Board board) {
		board.setTitle("§6§lAge of War");
		board.set(9, "§6§m--------------------");
		board.set(8, "Play Time: §a0");
		board.set(7, " ");
		board.set(6, "Gold: §60");
		board.set(5, "Xp: §b0");
		board.set(4, "  ");
		board.set(3, "Base: §a3000/3000");
		board.set(2, "Enemy Base: §a3000/3000");
		board.set(1, "§6§m--------------------§r");
	}
	
	/**
	 * Update the game score board
	 * @param gp
	 */
	public static void updateScoreboard(GamePlayer gp) {
		int minutes = Main.seconds / 60;
		int seconds = Main.seconds % 60;
		String str = String.format("%d:%02d", minutes, seconds);
		
		int baseHP = (gp == Main.player1) ? Main.player1.getBase().getLife() : Main.player2.getBase().getLife();
		int opbaseHP = (gp == Main.player1) ? Main.player2.getBase().getLife() : Main.player1.getBase().getLife();
		
		int baseMaxHP = (gp == Main.player1) ? Main.player1.getBase().getMaxLife() : Main.player2.getBase().getMaxLife();
		int opbaseMaxHP = (gp == Main.player1) ? Main.player2.getBase().getMaxLife() : Main.player1.getBase().getMaxLife();
		
		int lifePercent = (baseHP * 100 / baseMaxHP);
		int oplifePercent = (opbaseHP * 100 / opbaseMaxHP);
		
		
		gp.getBoard().set(8, "Play Time: §a" + str);
		gp.getBoard().set(6, "Gold: §6" + gp.getGold());
		gp.getBoard().set(5, "Xp: §b" + gp.getXp());
		gp.getBoard().set(3, "Base: " + getHealthColor(lifePercent) + baseHP + "§r/§a" + baseMaxHP);
		gp.getBoard().set(2, "Enemy Base: " + getHealthColor(oplifePercent) + opbaseHP + "§r/§a" + opbaseMaxHP);
	}
	
	/**
	 * Return base health color for the score board 
	 * using the current base health percentage
	 * @param lifePercent
	 * @return
	 */
	private static String getHealthColor(int lifePercent) {
		if (lifePercent <= 10) return "§4";
		if (lifePercent <= 25) return "§c";
		if (lifePercent <= 50) return "§6";
		if (lifePercent <= 75) return "§e";
		return "§a";
	}
	
	/**
	 * Increase the game time every second and update the score board
	 */
	public static void gameTimer() {
		Main.gameRunning = true;
		new BukkitRunnable(){
			@Override
			public void run(){
				try {
					if (!Main.gameRunning)
						this.cancel();
					Main.seconds++;
					updateScoreboard(Main.player1);
					updateScoreboard(Main.player2);
				} catch(Exception e) {}
			}
		}.runTaskTimerAsynchronously(Main.getPlugin(Main.class), 0L, 20L);
	}
}
