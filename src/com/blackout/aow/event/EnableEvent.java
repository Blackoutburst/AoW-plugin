package com.blackout.aow.event;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.blackout.aow.core.Warrior;
import com.blackout.aow.core.WarriorUtils;
import com.blackout.aow.main.Main;
import com.blackout.npcapi.utils.SkinLoader;

import net.minecraft.server.v1_8_R3.PacketPlayOutEntity.PacketPlayOutRelEntityMove;

public class EnableEvent {

	public void execute() {
		SkinLoader.loadSkinFromUUID(0, "d09accabcb4c4549bebeab3612bff0b9");
		SkinLoader.loadSkinFromUUID(1, "7305938e473743f0bb093bfcc8f9fc5e");
		SkinLoader.loadSkinFromUUID(2, "369f11812c7d4a7ab72f913df938ee3e");
		moveNPC();
		NPCFight();
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
		}.runTaskTimerAsynchronously(Main.getPlugin(Main.class), 0L, 1L);
	}
	
	private void NPCFight() {
		new BukkitRunnable(){
			@Override
			public void run(){
				try {
					fight();
				} catch(Exception e) {}
			}
		}.runTaskTimerAsynchronously(Main.getPlugin(Main.class), 0L, 20L);
	}
	
	private void fight() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			int index = 0;
			for (Warrior p : Main.player1.getWarriors()) {
				WarriorUtils.fight(p, player, index, Main.player2.getWarriors());
				index++;
			}
			
			index = 0;
			for (Warrior p : Main.player2.getWarriors()) {
				WarriorUtils.fight(p, player, index, Main.player1.getWarriors());
				index++;
			}
		}
	}
	
	private void moveToRight() {
		for (Player player : Bukkit.getOnlinePlayers()) {
		
			int index = 0;
			for (Warrior p : Main.player1.getWarriors()) {
				if (p.canWalkRight(index)) {
					((CraftPlayer)player).getHandle().playerConnection.sendPacket(new PacketPlayOutRelEntityMove(p.getNpc().getEntityId(), (byte) 0, (byte) 0, (byte)(5), true));
					p.getNpc().setLocation(new Location(p.getNpc().getLocation().getWorld(), p.getNpc().getLocation().getX(), p.getNpc().getLocation().getY(), p.getNpc().getLocation().getZ() + (0.16f / Bukkit.getOnlinePlayers().size())));
				}
				index++;
			}
		}
	}
	
	private void moveToLeft() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			
			int index = 0;
			for (Warrior p : Main.player2.getWarriors()) {
				if (p.canWalkLeft(index)) {
					((CraftPlayer)player).getHandle().playerConnection.sendPacket(new PacketPlayOutRelEntityMove(p.getNpc().getEntityId(), (byte) 0, (byte) 0, (byte)(-5), true));
					p.getNpc().setLocation(new Location(p.getNpc().getLocation().getWorld(), p.getNpc().getLocation().getX(), p.getNpc().getLocation().getY(), p.getNpc().getLocation().getZ() - (0.16f / Bukkit.getOnlinePlayers().size())));
				}
				index++;
			}
		}
	}
}
