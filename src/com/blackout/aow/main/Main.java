package com.blackout.aow.main;


import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.blackout.aow.commands.CommandManager;
import com.blackout.aow.core.GamePlayer;
import com.blackout.aow.event.EnableEvent;
import com.blackout.aow.event.JoinEvent;
import com.blackout.npcapi.core.PacketInteractListener;

public class Main extends JavaPlugin implements Listener {

	public static Location spawn;
	public static Location spawnP1;
	public static Location spawnP2;
	
	public static int seconds = 0;
	public static boolean gameRunning = false;
	
	public static GamePlayer player1;
	public static GamePlayer player2;
	
	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
		new EnableEvent().execute();
		spawn = new Location(Bukkit.getWorld("world"), 958.5f, 55, 1326.5f, -90, 0);
		spawnP1 = new Location(Bukkit.getWorld("world"), 970.5f, 55, 1311.5f, -90, 0);
		spawnP2 = new Location(Bukkit.getWorld("world"), 970.5f, 54, 1341.5f, -90, 0);
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		new JoinEvent().execute(event);
		event.getPlayer().setAllowFlight(true);
	}
	
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent event) {
		PacketInteractListener.remove(event.getPlayer());
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		if (!event.getPlayer().getGameMode().equals(GameMode.CREATIVE))
			event.setCancelled(true);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		new CommandManager().execute(sender, command, label, args);
		return true;
	}
}
