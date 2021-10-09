package com.blackout.aow.main;


import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.blackout.aow.commands.CommandManager;
import com.blackout.aow.core.AowPlayer;
import com.blackout.aow.core.Core;
import com.blackout.aow.events.EnableEvent;
import com.blackout.aow.events.JoinEvent;
import com.blackout.npcapi.core.PacketInteractListener;

public class Main extends JavaPlugin implements Listener {

	public static Location spawn;
	public static Location spawnSpec;
	public static Location spawnP1;
	public static Location spawnP2;
	
	public static AowPlayer player1;
	public static AowPlayer player2;
	
	public static int gameTime = 0;
	public static boolean gameRunning = false;
	
	public static List<AowPlayer> aowplayers = new ArrayList<AowPlayer>();
	
	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
		new EnableEvent().execute();
		spawn = new Location(Bukkit.getWorld("world"), 958.5f, 56, 1326.5f, -90, 0);
		spawnP1 = new Location(Bukkit.getWorld("world"), 970.5f, 55, 1311.5f, -90, 0);
		spawnP2 = new Location(Bukkit.getWorld("world"), 970.5f, 55, 1341.5f, -90, 0);
		spawnSpec = new Location(Bukkit.getWorld("world"), 992.5f, 55, 1326.5f, 90, 0);
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		new JoinEvent().execute(event);
	}
	
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent event) {
		PacketInteractListener.remove(event.getPlayer());
		event.getPlayer().setDisplayName(event.getPlayer().getName());
		event.getPlayer().setPlayerListName(event.getPlayer().getName());
		AowPlayer p = AowPlayer.getFromPlayer(event.getPlayer());
		if (p.getPlayer().getUniqueId() ==  Main.player1.getPlayer().getUniqueId() ||
				p.getPlayer().getUniqueId() ==  Main.player2.getPlayer().getUniqueId()) {
			aowplayers.remove(p);
			Core.endGame();
		} else {
			aowplayers.remove(p);
		}
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		if (!event.getPlayer().getGameMode().equals(GameMode.CREATIVE))
			event.setCancelled(true);
	}
	
	@EventHandler
	public void onItemDrop(PlayerDropItemEvent event) {
		if (!event.getPlayer().getGameMode().equals(GameMode.CREATIVE))
			event.setCancelled(true);
	}
	
	@EventHandler
	public void onEntityDamage(EntityDamageEvent event) {
		event.setCancelled(true);
	}	
	
	@EventHandler
	public void onWeatherChange(WeatherChangeEvent event) {
		event.setCancelled(event.toWeatherState());
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		new CommandManager().execute(sender, command, label, args);
		return true;
	}
}
