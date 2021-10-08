package com.blackout.aow.utils;

import org.bukkit.entity.Player;

import com.blackout.aow.core.AowPlayer;
import com.blackout.aow.main.Main;

public class Utils {
	
	public static AowPlayer getGamePlayer(Player p) {
		if (Main.player1.getPlayer() == p) return Main.player1;
		if (Main.player2.getPlayer() == p) return Main.player2;
		return null;
	}
}
