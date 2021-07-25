package com.blackout.aow.utils;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.NameTagVisibility;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import com.blackout.aow.core.GamePlayer;
import com.blackout.aow.main.Main;

import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle.EnumTitleAction;

public class Utils {

	public static GamePlayer getGamePlayer(Player p) {
		if (Main.player1.getPlayer() == p) return Main.player1;
		if (Main.player2.getPlayer() == p) return Main.player2;
		return null;
	}
	
	public static void setNameColor(Player p, String color) {
		p.setDisplayName(color+p.getName()+"§r");
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
	
	public static void resetNameColor(Player p) {
		p.setDisplayName("§r"+p.getName());
		p.setPlayerListName(p.getDisplayName());
		
		for (Player players: Bukkit.getOnlinePlayers()) {
			Scoreboard scoreboard = players.getScoreboard();
			Team team = null;
			
			if (scoreboard.getTeam(p.getName()) == null) {
				scoreboard.registerNewTeam(p.getName());
			}
			team = scoreboard.getTeam(p.getName());
			team.addEntry(p.getName());
			team.setPrefix("§r");
			team.setNameTagVisibility(NameTagVisibility.ALWAYS);
		}
	}
	
    public static void sendTitle(Player player, String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
        
        connection.sendPacket(new PacketPlayOutTitle(fadeIn, stay, fadeOut));
        connection.sendPacket(new PacketPlayOutTitle(EnumTitleAction.TITLE, ChatSerializer.a("{'text': '" + title + "'}")));
        connection.sendPacket(new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, ChatSerializer.a("{'text': '" + subtitle + "'}")));
    }
	
}
