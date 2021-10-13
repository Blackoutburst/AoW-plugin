package com.blackout.aow.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.blackout.aow.core.AowPlayer;
import com.blackout.aow.core.Core;
import com.blackout.aow.utils.Board;


public class SetPlayer1Command {

	@SuppressWarnings("deprecation")
	public void run(CommandSender sender, String[] args) {
		if (args.length == 0) {
			sender.sendMessage("§cInvalid usage");
			return;
		}
		
		Player p = Bukkit.getPlayer(args[0]);
		if (p == null) {
			sender.sendMessage("§cUnknown player "+ args[0]);
			return;
		}
		Board board = new Board(p, "§9", "");
		Core.player1 = new AowPlayer(0, p, board);
		sender.sendMessage(p.getDisplayName()+" §a is now player 1");
	}
}
