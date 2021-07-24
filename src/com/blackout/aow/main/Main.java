package com.blackout.aow.main;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.blackout.aow.core.GamePlayer;
import com.blackout.aow.event.EnableEvent;
import com.blackout.aow.event.JoinEvent;
import com.blackout.aow.utils.Utils;
import com.blackout.npcapi.core.PacketInteractListener;

public class Main extends JavaPlugin implements Listener {

	public static GamePlayer player1;
	public static GamePlayer player2;
	
	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
		new EnableEvent().execute();
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
		//event.setCancelled(true);
	}
	
	@Override
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		switch(command.getName()) {
			case "setplayer1":
				if (args.length == 0) {
					sender.sendMessage("§cInvalid usage");
					return true;
				}
				Player p = Bukkit.getPlayer(args[0]);
				if (p == null) {
					sender.sendMessage("§cUnknown player "+ args[0]);
					return true;
				}
				Utils.setNameColor(p, "§9");
				player1 = new GamePlayer(p);
			break;
			case "setplayer2":
				if (args.length == 0) {
					sender.sendMessage("§cInvalid usage");
					return true;
				}
				Player p2 = Bukkit.getPlayer(args[0]);
				if (p2 == null) {
					sender.sendMessage("§cUnknown player "+ args[0]);
					return true;
				}
				Utils.setNameColor(p2, "§4");
				player2 = new GamePlayer(p2);
			break;
			default: return true;
		}
		return true;
	}
}
