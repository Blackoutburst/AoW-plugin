package com.blackout.aow.core;


import org.bukkit.entity.Player;

import com.blackout.aow.main.Main;
import com.blackout.npcapi.core.APlayer;
import com.blackout.npcapi.core.NPC;
import com.blackout.npcapi.core.NPCPacket;


public class NPCListener implements NPCPacket {

	@Override
	public void onLeftClick(Player p, int id) {
		APlayer ap = APlayer.get(p);
		for (NPC npc : ap.npcs) {
			if (id == npc.getEntityId()) {
				switch(npc.getName()) {
					case "§9Knight": WarriorUtils.createNewWarrior(npc, p, 1307.5f, 0, Main.player1NPC); break;
					case "§9Archer": WarriorUtils.createNewWarrior(npc, p, 1307.5f, 0, Main.player1NPC); break;
					case "§9Berserk": WarriorUtils.createNewWarrior(npc, p, 1307.5f, 0, Main.player1NPC); break;
					case "§4Knight": WarriorUtils.createNewWarrior(npc, p, 1345.5f, 180, Main.player2NPC); break;
					case "§4Archer": WarriorUtils.createNewWarrior(npc, p, 1345.5f, 180, Main.player2NPC); break;
					case "§4Berserk": WarriorUtils.createNewWarrior(npc, p, 1345.5f, 180, Main.player2NPC); break;
					default: continue;
				}
			}
		}
	}

	@Override
	public void onRightClick(Player p, int id) {
		APlayer ap = APlayer.get(p);
		for (NPC npc : ap.npcs) {
			if (id == npc.getEntityId()) {
				switch(npc.getName()) {
					case "§9Knight": WarriorUtils.createNewWarrior(npc, p, 1307.5f, 0, Main.player1NPC); return;
					case "§9Archer": WarriorUtils.createNewWarrior(npc, p, 1307.5f, 0, Main.player1NPC); return;
					case "§9Berserk": WarriorUtils.createNewWarrior(npc, p, 1307.5f, 0, Main.player1NPC); return;
					case "§4Knight": WarriorUtils.createNewWarrior(npc, p, 1345.5f, 180, Main.player2NPC); return;
					case "§4Archer": WarriorUtils.createNewWarrior(npc, p, 1345.5f, 180, Main.player2NPC); return;
					case "§4Berserk": WarriorUtils.createNewWarrior(npc, p, 1345.5f, 180, Main.player2NPC); return;
					default: continue;
				}
			}
		}
	}
}
