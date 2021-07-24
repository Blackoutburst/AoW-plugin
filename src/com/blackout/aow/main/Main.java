package com.blackout.aow.main;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.blackout.aow.core.Warrior;
import com.blackout.aow.event.EnableEvent;
import com.blackout.aow.event.JoinEvent;
import com.blackout.npcapi.core.PacketInteractListener;

public class Main extends JavaPlugin implements Listener {

	public static List<Warrior> player1NPC = new ArrayList<Warrior>();
	public static List<Warrior> player2NPC = new ArrayList<Warrior>();
	
	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
		new EnableEvent().execute();;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		new JoinEvent().execute(event);
	}
	
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent event) {
		PacketInteractListener.remove(event.getPlayer());
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		//event.setCancelled(true);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		return true;
	}
}
