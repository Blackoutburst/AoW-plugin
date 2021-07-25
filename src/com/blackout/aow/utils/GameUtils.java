package com.blackout.aow.utils;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;

import com.blackout.aow.core.Base;
import com.blackout.aow.core.GamePlayer;
import com.blackout.aow.core.Warrior;
import com.blackout.aow.main.Main;
import com.blackout.holoapi.utils.HoloManager;
import com.blackout.npcapi.utils.NPCManager;

import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle.EnumTitleAction;

public class GameUtils {

	public static void endGame(Base base, GamePlayer gp) {
		sendTitle(base, gp);
		deleteNPCandHolo(gp);
		gp.getPlayer().teleport(Main.SPAWN);
	}
	
	private static void sendTitle(Base base, GamePlayer gp) {
		PlayerConnection connection = ((CraftPlayer) gp.getPlayer()).getHandle().playerConnection;
		
		if (base == gp.getBase()) {
			connection.sendPacket(new PacketPlayOutTitle(0, 40, 20));
			connection.sendPacket(new PacketPlayOutTitle(EnumTitleAction.TITLE, ChatSerializer.a("{'text': '§6Game Ended'}")));
			connection.sendPacket(new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, ChatSerializer.a("{'text': '§cYou lose!'}")));
		} else {
			connection.sendPacket(new PacketPlayOutTitle(0, 40, 20));
			connection.sendPacket(new PacketPlayOutTitle(EnumTitleAction.TITLE, ChatSerializer.a("{'text': '§6Game Ended'}")));
			connection.sendPacket(new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, ChatSerializer.a("{'text': '§aYou won!'}")));
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
		HoloManager.deleteHolo(gp.getPlayer(), gp.getBase().getLifeBar());
	}
}
