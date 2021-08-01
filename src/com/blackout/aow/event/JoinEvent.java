package com.blackout.aow.event;


import org.bukkit.event.player.PlayerJoinEvent;

import com.blackout.aow.core.NPCListener;
import com.blackout.aow.main.Main;
import com.blackout.npcapi.core.PacketInteractListener;

public class JoinEvent {

	/**
	 * Called when a player join, this make sure the player
	 * is full health and will not require food, we teleport
	 * the player to the spawn point and make it able to fly
	 * we also start the Packet Listener making the player
	 * able to interact with NPC
	 * @param event
	 * @see NPC-API for PacketInteractListener
	 */
	public void execute(PlayerJoinEvent event) {
		event.getPlayer().setHealth(20);
		event.getPlayer().setFoodLevel(20);
		event.getPlayer().setSaturation(10000);
		event.getPlayer().teleport(Main.spawn);
		event.getPlayer().setAllowFlight(true);
		PacketInteractListener.init(event.getPlayer(), new NPCListener());
	}
	
}
