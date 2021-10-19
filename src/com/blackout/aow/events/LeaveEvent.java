package com.blackout.aow.events;

import org.bukkit.event.player.PlayerQuitEvent;

import com.blackout.aow.core.AowPlayer;
import com.blackout.aow.core.Core;
import com.blackout.npcapi.core.PacketInteractListener;

public class LeaveEvent {

	public void execute(PlayerQuitEvent event) {
		PacketInteractListener.remove(event.getPlayer());
		event.getPlayer().setDisplayName(event.getPlayer().getName());
		event.getPlayer().setPlayerListName(event.getPlayer().getName());
		AowPlayer p = AowPlayer.getFromPlayer(event.getPlayer());
		
		if (Core.player1 != null && p.getPlayer().getUniqueId() == Core.player1.getPlayer().getUniqueId()) {
			Core.aowplayers.remove(p);
			Core.endGame();
		}
		else if (Core.player2 != null && p.getPlayer().getUniqueId() == Core.player2.getPlayer().getUniqueId()) {
			Core.aowplayers.remove(p);
			Core.endGame();
		} else {
			Core.aowplayers.remove(p);
		}
		
	}
}
