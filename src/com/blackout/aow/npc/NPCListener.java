package com.blackout.aow.npc;


import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.blackout.aow.core.WarriorManager;
import com.blackout.aow.main.Main;
import com.blackout.npcapi.core.APlayer;
import com.blackout.npcapi.core.NPC;
import com.blackout.npcapi.core.NPCPacket;


public class NPCListener implements NPCPacket {

	private boolean canUse = true;
	
	@Override
	public void onLeftClick(Player p, int id) {
		runAction(p, id);
	}

	@Override
	public void onRightClick(Player p, int id) {
		if (canUse) {
			runAction(p, id);
			canUse = false;
		}
		
		// Avoid double trigger
		new BukkitRunnable() {
			public void run() {
				canUse = true;
			}
		}.runTaskLater(Main.getPlugin(Main.class), 1L);
	}
	
	private void runAction(Player p, int id) {
		APlayer ap = APlayer.get(p);
		
		if (p.getPlayer().getUniqueId() ==  Main.player1.getPlayer().getUniqueId() ||
				p.getPlayer().getUniqueId() ==  Main.player2.getPlayer().getUniqueId()) {
			for (NPC npc : ap.npcs) {
				if (id == npc.getEntityId()) {
					new BukkitRunnable() {
						public void run() {
							WarriorManager.createNewWarrior(npc.getName(), p);
						}
					}.runTaskLater(Main.getPlugin(Main.class), 1L);
				}
			}
		}
	}
}
