package com.blackout.aow.events;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;

import com.blackout.aow.core.Core;
import com.blackout.aow.npc.NPCListener;
import com.blackout.npcapi.core.PacketInteractListener;

public class JoinEvent {

	private void setplayer(Player p) {
		p.setGameMode(GameMode.ADVENTURE);
		p.setAllowFlight(true);
		p.setHealth(p.getMaxHealth());
		p.setSaturation(100000);
		p.teleport(Core.spawn);
	}
	
	public void execute(PlayerJoinEvent event) {
		Player p = event.getPlayer();
		setplayer(p);
		
		PacketInteractListener.init(p, new NPCListener());
	}
}
