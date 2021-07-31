package com.blackout.aow.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;

import com.blackout.aow.main.Main;
import com.blackout.aow.utils.BaseUtils;
import com.blackout.aow.utils.GameUtils;
import com.blackout.aow.utils.PurchaseNPC;
import com.blackout.aow.utils.Utils;

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
				PurchaseNPC.spawnNPC(Main.player1.getPlayer());
				PurchaseNPC.spawnNPC(Main.player2.getPlayer());
				BaseUtils.spawnHealthBarTitle(Main.player1, true);
				BaseUtils.spawnHealthBarTitle(Main.player2, true);
				Main.player1.getPlayer().teleport(Main.spawnP1);
				Main.player2.getPlayer().teleport(Main.spawnP2);
				Utils.setNameColor(Main.player1.getPlayer(), "§9");
				Utils.setNameColor(Main.player2.getPlayer(), "§4");

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
