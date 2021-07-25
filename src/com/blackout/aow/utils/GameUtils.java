package com.blackout.aow.utils;

import com.blackout.aow.core.Base;
import com.blackout.aow.core.GamePlayer;
import com.blackout.aow.core.Warrior;
import com.blackout.aow.main.Main;
import com.blackout.holoapi.core.APlayer;
import com.blackout.holoapi.core.Holo;
import com.blackout.holoapi.utils.HoloManager;
import com.blackout.npcapi.utils.NPCManager;

public class GameUtils {

	public static void endGame(Base base, GamePlayer gp) {
		Utils.resetNameColor(gp.getPlayer());
		sendTitle(base, gp);
		deleteNPCandHolo(gp);
		gp.getPlayer().teleport(Main.spawn);
	}
	
	private static void sendTitle(Base base, GamePlayer gp) {
		if (base == gp.getBase()) {
			Utils.sendTitle(gp.getPlayer(), "§6Game Ended", "§cYou lose!", 0, 100, 20);
		} else {
			Utils.sendTitle(gp.getPlayer(), "§6Game Ended", "§aYou won!", 0, 100, 20);
		}
	}
	
	private static void deleteNPCandHolo(GamePlayer gp) {
		for (Warrior w : gp.getWarriors()) {
			HoloManager.deleteHolo(gp.getPlayer(), w.getLifeBar());
			NPCManager.deleteNPC(gp.getPlayer(), w.getNpc());
			
			HoloManager.deleteHolo(gp.getPlayer(), w.getLifeBar());
			NPCManager.deleteNPC(gp.getPlayer(), w.getNpc());
		}
		
		for (Warrior w : gp.getOpponents()) {
			HoloManager.deleteHolo(gp.getPlayer(), w.getLifeBar());
			NPCManager.deleteNPC(gp.getPlayer(), w.getNpc());
			
			HoloManager.deleteHolo(gp.getPlayer(), w.getLifeBar());
			NPCManager.deleteNPC(gp.getPlayer(), w.getNpc());
		}
		HoloManager.deleteHolo(gp.getPlayer(), Main.player1.getBase().getLifeBar());
		HoloManager.deleteHolo(gp.getPlayer(), Main.player2.getBase().getLifeBar());
		HoloManager.deleteHolo(gp.getPlayer(), gp.getBaseTitle());
		HoloManager.deleteHolo(gp.getPlayer(), gp.getOpponentBaseTitle());
		
		APlayer ap = APlayer.get(gp.getPlayer());
		for (int i = 0; i < ap.holos.size(); i++) {
			Holo h = ap.holos.get(i);
			if (h.getName().contains("████████████████████"))
				HoloManager.deleteHolo(gp.getPlayer(), h);
		}
		
	}
}
