package com.blackout.aow.event;


import org.bukkit.event.player.PlayerJoinEvent;

import com.blackout.aow.core.NPCListener;
import com.blackout.aow.main.Main;
import com.blackout.npcapi.core.PacketInteractListener;

public class JoinEvent {

	public void execute(PlayerJoinEvent event) {
		event.getPlayer().setHealth(20);
		event.getPlayer().setFoodLevel(20);
		event.getPlayer().setSaturation(10000);
		event.getPlayer().teleport(Main.spawn);
		PacketInteractListener.init(event.getPlayer(), new NPCListener());
	}
	
}
