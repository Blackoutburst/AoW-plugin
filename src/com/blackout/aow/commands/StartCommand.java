package com.blackout.aow.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.blackout.aow.core.Core;
import com.blackout.aow.main.Main;
import com.blackout.aow.nms.NMSTitle;

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
		
		scheduleStart();
		startGame();
	}
	
	private void scheduleStart() {
		final int seconds = 5;
		
		for (int i = seconds; i >= 0; i--) {
			final int number = i;
			new BukkitRunnable(){
				@Override
				public void run(){
					for (Player p : Bukkit.getOnlinePlayers()) {
						NMSTitle.sendTitle(p, getCountdownNumber(number), "", 0, 20, 0);
					}
				}
			}.runTaskLaterAsynchronously(Main.getPlugin(Main.class), 20L * (seconds - i));
		}
	}
	
	private void startGame() {
		new BukkitRunnable(){
			@Override
			public void run(){
				Core.startGame();
			}
		}.runTaskLater(Main.getPlugin(Main.class), 100L);
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
