package com.blackout.aow.commands;

import org.bukkit.command.CommandSender;

import com.blackout.aow.core.Core;

public class EndCommand {

	public void run(CommandSender sender) {
		Core.endGame();
	}
	
}
