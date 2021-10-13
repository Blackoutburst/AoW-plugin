package com.blackout.aow.main;

import org.bukkit.GameMode;
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

	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
		new EnableEvent().execute();
		Core.loadLocations();
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
		
		if (Core.player1 != null && p.getPlayer().getUniqueId() == Core.player1.getPlayer().getUniqueId()) {
			Core.endGame();
		}
		if (Core.player2 != null && p.getPlayer().getUniqueId() == Core.player2.getPlayer().getUniqueId()) {
			Core.endGame();
		}
		Core.aowplayers.remove(p);
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
