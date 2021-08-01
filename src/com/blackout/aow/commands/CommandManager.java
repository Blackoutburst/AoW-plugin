package com.blackout.aow.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class CommandManager {

	
	/**
	 * Manage the different command by name
	 * Run the proper command with the proper arguments
	 * @param sender
	 * @param command
	 * @param label
	 * @param args
	 */
	public void execute(CommandSender sender, Command command, String label, String[] args) {
		switch(command.getName()) {
			case "setplayer1": new SetPlayer1Command().run(sender, args); break;
			case "setplayer2": new SetPlayer2Command().run(sender, args); break;
			case "start": new StartCommand().run(sender); break;
			default: return;
		}
	}
	
}
