package com.blackout.aow.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.blackout.aow.core.Base;
import com.blackout.aow.core.GamePlayer;
import com.blackout.aow.main.Main;
import com.blackout.aow.utils.BaseUtils;
import com.blackout.aow.utils.Utils;
import com.blackout.holoapi.core.Holo;

public class SetPlayer2Command {

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
		Utils.setNameColor(p, "§4");
		
		sender.sendMessage(p.getDisplayName()+" §a is now player 1"+ args[0]);
		
		Holo lifeBar = BaseUtils.spawnHealthBar(p, false);
		Main.player2 = new GamePlayer(p, new Base(1345.5f, lifeBar));
		BaseUtils.spawnHealthBarTitle(Main.player2, false);
	}
}
