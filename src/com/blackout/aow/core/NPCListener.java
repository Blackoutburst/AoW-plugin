package com.blackout.aow.core;


import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.blackout.aow.main.Main;
import com.blackout.aow.utils.WarriorUtils;
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
		for (NPC npc : ap.npcs) {
			if (id == npc.getEntityId()) {
				switch(npc.getName()) {
					case "§9Clubman":case "§9Knight": WarriorUtils.createNewWarrior(npc, p, 1307.5f, 0); break;
					case "§9Slingshot":case "§9Archer": WarriorUtils.createNewWarrior(npc, p, 1307.5f, 0); break;
					case "§9Spearman":case "§9Berserk": WarriorUtils.createNewWarrior(npc, p, 1307.5f, 0); break;
					case "§4Clubman":case "§4Knight": WarriorUtils.createNewWarrior(npc, p, 1345.5f, 180); break;
					case "§4Slingshot":case "§4Archer": WarriorUtils.createNewWarrior(npc, p, 1345.5f, 180); break;
					case "§94Spearman":case "§4Berserk": WarriorUtils.createNewWarrior(npc, p, 1345.5f, 180); break;
					default: continue;
				}
			}
		}
		
	}
}
