package com.blackout.aow.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.NameTagVisibility;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import com.blackout.aow.core.GamePlayer;
import com.blackout.aow.main.Main;

public class Utils {

	public static GamePlayer getGamePlayer(Player p) {
		if (Main.player1.getPlayer() == p) return Main.player1;
		if (Main.player2.getPlayer() == p) return Main.player2;
		return null;
	}
	
	public static void setNameColor(Player p, String color) {
		p.setDisplayName(color+p.getDisplayName()+"§r");
		p.setPlayerListName(p.getDisplayName());
		
		
		for (Player players: Bukkit.getOnlinePlayers()) {
			Scoreboard scoreboard = players.getScoreboard();
			Team team = null;
			
			if (scoreboard.getTeam(p.getName()) == null) {
				scoreboard.registerNewTeam(p.getName());
			}
			team = scoreboard.getTeam(p.getName());
			team.addEntry(p.getName());
			team.setPrefix(color);
			team.setNameTagVisibility(NameTagVisibility.ALWAYS);
		}
	}
	
}
