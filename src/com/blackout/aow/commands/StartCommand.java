package com.blackout.aow.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;

import com.blackout.aow.main.Main;
import com.blackout.aow.utils.GameUtils;
import com.blackout.aow.utils.Utils;
import com.blackout.holoapi.utils.HoloManager;

public class StartCommand {

	public void run(CommandSender sender) {
		if (Main.player1 == null) {
			sender.sendMessage("§cPlayer 1 is not defined");
			return;
		}
		
		if (Main.player2 == null) {
			sender.sendMessage("§cPlayer 2 is not defined");
			return;
		}
		
		HoloManager.spawnHolo(Main.player2.getBase().getLifeBar(), Main.player1.getPlayer());
		HoloManager.spawnHolo(Main.player1.getBase().getLifeBar(), Main.player2.getPlayer());
		
		scheduleStart();
		teleport();
	}
	
	private void scheduleStart() {
		final int seconds = 5;
		
		for (int i = seconds; i >= 0; i--) {
			final int number = i;
			new BukkitRunnable(){
				@Override
				public void run(){
					Utils.sendTitle(Main.player1.getPlayer(), getCountdownNumber(number), "", 0, 20, 0);
					Utils.sendTitle(Main.player2.getPlayer(), getCountdownNumber(number), "", 0, 20, 0);
				}
			}.runTaskLaterAsynchronously(Main.getPlugin(Main.class), 20L * (seconds - i));
		}
	}
	
	private void teleport() {
		new BukkitRunnable(){
			@Override
			public void run(){
				Main.player1.getPlayer().teleport(Main.spawnP1);
				Main.player2.getPlayer().teleport(Main.spawnP2);
				GameUtils.gameTimer();
			}
		}.runTaskLaterAsynchronously(Main.getPlugin(Main.class), 100L);
	}
	
	private String getCountdownNumber(int index) {
		switch(index) {
			case 5: return "§c5";
			case 4: return "§c4";
			case 3: return "§63";
			case 2: return "§62";
			case 1: return "§a1";
			case 0: return "§cGo!";
			default: return "";
		}
	}
}
