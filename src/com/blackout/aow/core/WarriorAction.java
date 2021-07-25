package com.blackout.aow.core;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.blackout.aow.main.Main;
import com.blackout.aow.utils.BaseUtils;
import com.blackout.aow.utils.GameUtils;
import com.blackout.aow.utils.WarriorUtils;

import net.minecraft.server.v1_8_R3.PacketPlayOutAnimation;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntity.PacketPlayOutRelEntityMove;

public class WarriorAction {

	public static void moveAction() {
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
	
	public static void fightAction() {
		new BukkitRunnable(){
			@Override
			public void run(){
				try {
					fight();
				} catch(Exception e) {}
			}
		}.runTaskTimerAsynchronously(Main.getPlugin(Main.class), 0L, 20L);
	}
	
	private static void fight() {
		int index = 0;
		for (Warrior p : Main.player1.getWarriors()) {
			fight(index, p, Main.player1.getPlayer(), Main.player1, Main.player2);
			index++;
		}
		
		index = 0;
		for (Warrior p : Main.player2.getWarriors()) {
			fight(index, p, Main.player2.getPlayer(), Main.player2, Main.player1);
			index++;
		}
		
		WarriorUtils.death(Main.player1);
		WarriorUtils.death(Main.player2);
		GameUtils.updateScoreboard(Main.player1);
		GameUtils.updateScoreboard(Main.player2);
	}
	
	private static void moveToRight() {
		for (int i = 0; i < Main.player1.getWarriors().size(); i++) {
			Warrior p = Main.player1.getWarriors().get(i);
			if (p.canWalkRight(i)) {
				((CraftPlayer)Main.player1.getPlayer()).getHandle().playerConnection.sendPacket(new PacketPlayOutRelEntityMove(p.getNpc().getEntityId(), (byte) 0, (byte) 0, (byte)(5), true));
				p.getNpc().setLocation(new Location(p.getNpc().getLocation().getWorld(), p.getNpc().getLocation().getX(), p.getNpc().getLocation().getY(), p.getNpc().getLocation().getZ() + 0.16f));
			}
			Warrior o = Main.player2.getOpponents().get(i);
			if (o.canWalkRight(i)) {
				((CraftPlayer)Main.player2.getPlayer()).getHandle().playerConnection.sendPacket(new PacketPlayOutRelEntityMove(o.getNpc().getEntityId(), (byte) 0, (byte) 0, (byte)(5), true));
				o.getNpc().setLocation(new Location(o.getNpc().getLocation().getWorld(), o.getNpc().getLocation().getX(), o.getNpc().getLocation().getY(), o.getNpc().getLocation().getZ() + 0.16f));
			}
		}
	}
	
	private static void moveToLeft() {
		for (int i = 0; i < Main.player2.getWarriors().size(); i++) {
			Warrior p = Main.player2.getWarriors().get(i);
			if (p.canWalkLeft(i)) {
				((CraftPlayer)Main.player2.getPlayer()).getHandle().playerConnection.sendPacket(new PacketPlayOutRelEntityMove(p.getNpc().getEntityId(), (byte) 0, (byte) 0, (byte)(-5), true));
				p.getNpc().setLocation(new Location(p.getNpc().getLocation().getWorld(), p.getNpc().getLocation().getX(), p.getNpc().getLocation().getY(), p.getNpc().getLocation().getZ() - 0.16f));
			}
			Warrior o = Main.player1.getOpponents().get(i);
			if (o.canWalkLeft(i)) {
				((CraftPlayer)Main.player1.getPlayer()).getHandle().playerConnection.sendPacket(new PacketPlayOutRelEntityMove(o.getNpc().getEntityId(), (byte) 0, (byte) 0, (byte)(-5), true));
				o.getNpc().setLocation(new Location(o.getNpc().getLocation().getWorld(), o.getNpc().getLocation().getX(), o.getNpc().getLocation().getY(), o.getNpc().getLocation().getZ() - 0.16f));
			}
		}
	}
	
	private static void fight(int index, Warrior warrior, Player player, GamePlayer gp1, GamePlayer gp2) {
		if (gp1.getOpponents().size() > 0) {
			fightNPC(index, warrior, gp1, gp2);
		} else {
			fightBase(index, warrior, gp1, gp2);
		}
	}
	
	private static void fightBase(int index, Warrior warrior, GamePlayer gp1, GamePlayer gp2) {
		double myZ = warrior.getNpc().getLocation().getZ();
		double prevZ = gp2.getBase().getZ();
		
		if (Math.abs(prevZ - myZ) < 1.5f && warrior.type != WarriorType.Archer) {
			PlayerConnection connection = ((CraftPlayer) gp1.getPlayer()).getHandle().playerConnection;
			connection.sendPacket(new PacketPlayOutAnimation(warrior.getNpc().getEntity(), 0));
			gp1.getPlayer().playSound(warrior.npc.getLocation(), Sound.ZOMBIE_WOODBREAK, 1.0f, 1.0f);
			
			connection = ((CraftPlayer) gp2.getPlayer()).getHandle().playerConnection;
			connection.sendPacket(new PacketPlayOutAnimation(gp2.getOpponents().get(index).getNpc().getEntity(), 0));
			gp2.getPlayer().playSound(gp2.getOpponents().get(index).getNpc().getLocation(), Sound.ZOMBIE_WOODBREAK, 1.0f, 1.0f);
			
			BaseUtils.updateLife(warrior, gp2.getBase(), gp1);
			BaseUtils.updateLife(warrior, gp2.getBase(), gp2);
		}
		
		if (Math.abs(prevZ - myZ) < 5.5f && warrior.type == WarriorType.Archer) {
			PlayerConnection connection = ((CraftPlayer) gp1.getPlayer()).getHandle().playerConnection;
			gp1.getPlayer().playSound(warrior.npc.getLocation(), Sound.SHOOT_ARROW, 1.0f, 1.0f);
			connection.sendPacket(new PacketPlayOutAnimation(warrior.getNpc().getEntity(), 0));
			gp1.getPlayer().playSound(warrior.npc.getLocation(), Sound.ZOMBIE_WOODBREAK, 1.0f, 1.0f);
			
			connection = ((CraftPlayer) gp2.getPlayer()).getHandle().playerConnection;
			gp2.getPlayer().playSound(warrior.npc.getLocation(), Sound.SHOOT_ARROW, 1.0f, 1.0f);
			connection.sendPacket(new PacketPlayOutAnimation(gp2.getOpponents().get(index).getNpc().getEntity(), 0));
			gp2.getPlayer().playSound(gp2.getOpponents().get(index).getNpc().getLocation(), Sound.ZOMBIE_WOODBREAK, 1.0f, 1.0f);
			BaseUtils.updateLife(warrior, gp2.getBase(), gp1);
			BaseUtils.updateLife(warrior, gp2.getBase(), gp2);
		}
	}
	
	private static void fightNPC(int index, Warrior warrior, GamePlayer gp1, GamePlayer gp2) {
		Warrior opponent = gp1.getOpponents().get(0);
		double myZ = warrior.getNpc().getLocation().getZ();
		double prevZ = opponent.getNpc().getLocation().getZ();
		
		if (Math.abs(prevZ - myZ) < 1.5f && warrior.type != WarriorType.Archer) {
			PlayerConnection connection = ((CraftPlayer) gp1.getPlayer()).getHandle().playerConnection;
			connection.sendPacket(new PacketPlayOutAnimation(warrior.getNpc().getEntity(), 0));
			connection.sendPacket(new PacketPlayOutAnimation(opponent.getNpc().getEntity(), 1));
			gp1.getPlayer().playSound(opponent.npc.getLocation(), Sound.HURT_FLESH, 1.0f, 1.0f);
			
			Warrior opp2 = gp2.getWarriors().get(0);
			connection = ((CraftPlayer) gp2.getPlayer()).getHandle().playerConnection;
			connection.sendPacket(new PacketPlayOutAnimation(gp2.getOpponents().get(index).getNpc().getEntity(), 0));
			connection.sendPacket(new PacketPlayOutAnimation(opp2.getNpc().getEntity(), 1));
			gp2.getPlayer().playSound(opponent.npc.getLocation(), Sound.HURT_FLESH, 1.0f, 1.0f);
			
			WarriorUtils.updateLife(warrior, opponent, gp1);
			WarriorUtils.updateLife(gp2.getOpponents().get(index), opp2, gp2);
		}
		
		if (Math.abs(prevZ - myZ) < 5.5f && warrior.type == WarriorType.Archer) {
			PlayerConnection connection = ((CraftPlayer) gp1.getPlayer()).getHandle().playerConnection;
			gp1.getPlayer().playSound(warrior.npc.getLocation(), Sound.SHOOT_ARROW, 1.0f, 1.0f);
			connection.sendPacket(new PacketPlayOutAnimation(warrior.getNpc().getEntity(), 0));
			connection.sendPacket(new PacketPlayOutAnimation(opponent.getNpc().getEntity(), 1));
			gp1.getPlayer().playSound(opponent.npc.getLocation(), Sound.HURT_FLESH, 1.0f, 1.0f);
			
			Warrior opp2 = gp2.getWarriors().get(0);
			connection = ((CraftPlayer) gp2.getPlayer()).getHandle().playerConnection;
			gp2.getPlayer().playSound(warrior.npc.getLocation(), Sound.SHOOT_ARROW, 1.0f, 1.0f);
			connection.sendPacket(new PacketPlayOutAnimation(gp2.getOpponents().get(index).getNpc().getEntity(), 0));
			connection.sendPacket(new PacketPlayOutAnimation(opp2.getNpc().getEntity(), 1));
			gp2.getPlayer().playSound(opponent.npc.getLocation(), Sound.HURT_FLESH, 1.0f, 1.0f);
			
			WarriorUtils.updateLife(warrior, opponent, gp1);
			WarriorUtils.updateLife(gp2.getOpponents().get(index), opp2, gp2);
		}
	}
}
