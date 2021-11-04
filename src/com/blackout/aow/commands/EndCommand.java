package com.blackout.aow.commands;

import org.bukkit.command.CommandSender;

import com.blackout.aow.core.Core;

public class EndCommand {

	public void run(CommandSender sender) {
		if (Core.gameRunning)
			Core.endGame();
		else
			sender.sendMessage("§cThe game is not running!");
	}
}
