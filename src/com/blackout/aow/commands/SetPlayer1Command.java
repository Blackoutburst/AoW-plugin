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
import com.blackout.aow.utils.Utils;
import com.blackout.holoapi.core.Holo;

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
		Utils.setNameColor(p, "§9");
		sender.sendMessage(p.getDisplayName()+" §a is now player 1");
		
		Holo lifeBar = BaseUtils.spawnHealthBar(p, true);
		Board board = new Board(p);
		GameUtils.setDefaultScoreboard(board);
		
		Main.player1 = new GamePlayer(p, new Base(1307.5f, lifeBar), board);
		BaseUtils.spawnHealthBarTitle(Main.player1, true);
	}
}
