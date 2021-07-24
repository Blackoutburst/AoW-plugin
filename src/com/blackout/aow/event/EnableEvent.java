package com.blackout.aow.event;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.blackout.aow.main.Main;
import com.blackout.npcapi.core.NPC;
import com.blackout.npcapi.utils.SkinLoader;

import net.minecraft.server.v1_8_R3.PacketPlayOutEntity.PacketPlayOutRelEntityMove;

public class EnableEvent {

	public void execute() {
		SkinLoader.loadSkinFromUUID(0, "d09accabcb4c4549bebeab3612bff0b9");
		SkinLoader.loadSkinFromUUID(1, "7305938e473743f0bb093bfcc8f9fc5e");
		SkinLoader.loadSkinFromUUID(2, "369f11812c7d4a7ab72f913df938ee3e");
		moveNPC();
	}
	
	private void moveNPC() {
		new BukkitRunnable(){
			@Override
			public void run(){
				try {
					moveToRight();
					moveToLeft();
				} catch(Exception e) {}
			}
		}.runTaskTimerAsynchronously(Main.getPlugin(Main.class), 0L, 20L);
	}
	
	private void moveToRight() {
		for (Player player : Bukkit.getOnlinePlayers()) {
		
			for (NPC p : Main.player1NPC) {
				((CraftPlayer)player).getHandle().playerConnection.sendPacket(new PacketPlayOutRelEntityMove(p.getEntityId(), (byte) 0, (byte) 0, (byte)(10), true));
			}
		}
	}
	
	private void moveToLeft() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			for (NPC p : Main.player2NPC) {
				((CraftPlayer)player).getHandle().playerConnection.sendPacket(new PacketPlayOutRelEntityMove(p.getEntityId(), (byte) 0, (byte) 0, (byte)(-10), true));
			}
		}
	}
}
