package com.blackout.aow.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.blackout.aow.core.Base;
import com.blackout.aow.core.Board;
import com.blackout.aow.core.GamePlayer;
import com.blackout.aow.main.Main;
import com.blackout.aow.utils.BaseUtils;
import com.blackout.aow.utils.GameUtils;
import com.blackout.holoapi.core.Holo;
import com.blackout.holoapi.utils.HoloManager;

public class SetPlayer2Command {

	/**
	 * Get the player from the 1st argument
	 * Set this player as game player 2 and generate
	 * important default value such as the player base
	 * the player score board and save all of this in a
	 * new Game Player instance
	 * @param sender
	 * @param args
	 */
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
		
		
		Holo lifeBar = BaseUtils.spawnHealthBar(p, false);
		Board board = new Board(p);
		GameUtils.setDefaultScoreboard(board);
		Main.player2 = new GamePlayer(p, new Base(1345.5f, lifeBar), board);
		HoloManager.hideHolo(p, lifeBar);
		sender.sendMessage(p.getDisplayName()+" §a is now player 2");
	}
}
