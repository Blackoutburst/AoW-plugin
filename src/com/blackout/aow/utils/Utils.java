package com.blackout.aow.utils;

import org.bukkit.entity.Player;

import com.blackout.aow.core.AowPlayer;
import com.blackout.aow.core.Core;

public class Utils {
	
	public static AowPlayer getGamePlayer(Player p) {
		if (Core.player1.getPlayer() == p) return Core.player1;
		if (Core.player2.getPlayer() == p) return Core.player2;
		return null;
	}
}
