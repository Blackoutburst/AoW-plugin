package com.blackout.aow.core;

import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.blackout.aow.main.Main;
import com.blackout.aow.utils.Utils;
import com.blackout.holoapi.core.Holo;
import com.blackout.holoapi.utils.HoloManager;
import com.blackout.npcapi.core.NPC;
import com.blackout.npcapi.utils.NPCManager;
import com.blackout.npcapi.utils.SkinLoader;

import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.PacketPlayOutAnimation;
import net.minecraft.server.v1_8_R3.PacketPlayOutAttachEntity;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityEquipment;
import net.minecraft.server.v1_8_R3.PlayerConnection;

public class WarriorUtils {

	public static void createNewWarrior(NPC npc, Player p, float Z, float yaw) {
		new BukkitRunnable() {
			public void run() {
					if (npc.getName().contains("Knight"))
						createKnight(Z, yaw, p);
					if (npc.getName().contains("Archer"))
						createArcher(Z, yaw, p);
					if (npc.getName().contains("Berserk"))
						createBerserk(Z, yaw, p);
			}
		}.runTaskLater(Main.getPlugin(Main.class), 5L);
	}
	
	private static void createKnight(float Z, float yaw, Player p) {
		GamePlayer gp = Utils.getGamePlayer(p);
		if (gp.getGold() < 15) {
			p.sendMessage("§cSorry but you don't have enough gold for this");
			p.playSound(p.getLocation(), Sound.ANVIL_LAND, 1.0f, 1.0f);
			return;
		}
		
		p.sendMessage("§aKnight created for 15 gold");
		
		if (gp == Main.player1) {
			sendAddPacket(Z, yaw, Main.player1, Main.player2, WarriorType.Knight, 267);
		} else {
			sendAddPacket(Z, yaw, Main.player2, Main.player1, WarriorType.Knight, 267);
		}
		
	}
	
	private static void createArcher(float Z, float yaw, Player p) {
		GamePlayer gp = Utils.getGamePlayer(p);
		if (gp.getGold() < 30) {
			p.sendMessage("§cSorry but you don't have enough gold for this");
			p.playSound(p.getLocation(), Sound.ANVIL_LAND, 1.0f, 1.0f);
			return;
		}
		
		p.sendMessage("§aArcher created for 30 gold");
		
		if (gp == Main.player1) {
			sendAddPacket(Z, yaw, Main.player1, Main.player2, WarriorType.Archer, 261);
		} else {
			sendAddPacket(Z, yaw, Main.player2, Main.player1, WarriorType.Archer, 261);
		}
	}
	
	private static void createBerserk(float Z, float yaw, Player p) {
		GamePlayer gp = Utils.getGamePlayer(p);
		if (gp.getGold() < 100) {
			p.sendMessage("§cSorry but you don't have enough gold for this");
			p.playSound(p.getLocation(), Sound.ANVIL_LAND, 1.0f, 1.0f);
			return;
		}
		
		p.sendMessage("§aBerserk created for 100 gold");
		
		if (gp == Main.player1) {
			sendAddPacket(Z, yaw, Main.player1, Main.player2, WarriorType.Berserk, 258);
		} else {
			sendAddPacket(Z, yaw, Main.player2, Main.player1, WarriorType.Berserk, 258);
		}
	}
	
	public static void fight(Warrior warrior, Player player, int index, List<Warrior> opponents) {
		if (opponents.size() > 0) {
			Warrior opponent = opponents.get(0);
			double myZ = warrior.getNpc().getLocation().getZ();
			double prevZ = opponent.getNpc().getLocation().getZ();
			
			if (Math.abs(prevZ - myZ) < 2 && warrior.type != WarriorType.Archer) {
				for (Player p : Bukkit.getOnlinePlayers()) {
					PlayerConnection connection = ((CraftPlayer) p).getHandle().playerConnection;
					connection.sendPacket(new PacketPlayOutAnimation(warrior.getNpc().getEntity(), 0));
					connection.sendPacket(new PacketPlayOutAnimation(opponent.getNpc().getEntity(), 1));
					player.playSound(opponent.npc.getLocation(), Sound.HURT_FLESH, 1.0f, 1.0f);
				}
				
				updateLife(warrior, opponent, player, opponents);
			}
			
			if (Math.abs(prevZ - myZ) < 6 && warrior.type == WarriorType.Archer) {
				for (Player p : Bukkit.getOnlinePlayers()) {
					PlayerConnection connection = ((CraftPlayer) p).getHandle().playerConnection;
					p.playSound(warrior.npc.getLocation(), Sound.SHOOT_ARROW, 1.0f, 1.0f);
					connection.sendPacket(new PacketPlayOutAnimation(warrior.getNpc().getEntity(), 0));
					connection.sendPacket(new PacketPlayOutAnimation(opponent.getNpc().getEntity(), 1));
					p.playSound(opponent.npc.getLocation(), Sound.HURT_FLESH, 1.0f, 1.0f);
				}
				updateLife(warrior, opponent, player, opponents);
			}
		}
	}
	
	private static void updateLife(Warrior warrior, Warrior opponent, Player player, List<Warrior> list) {
		opponent.setLife(opponent.getLife() - warrior.getDamage());
		int lifePercent = (opponent.getLife() * 100 / opponent.getMaxLife());
		
		for (Player p : Bukkit.getOnlinePlayers()) {
			HoloManager.deleteHolo(p, opponent.lifeBar);
		}
		
		Location newLoc = new Location (opponent.getNpc().getLocation().getWorld(), opponent.getNpc().getLocation().getX(), opponent.getNpc().getLocation().getY(), opponent.getNpc().getLocation().getZ());
		newLoc.setY(newLoc.getY() + 1.4f);
		
		Holo newBar = new Holo(UUID.randomUUID(), getLifeBar(lifePercent))
		        .setLocation(newLoc);
		
		for (Player p : Bukkit.getOnlinePlayers()) {
			HoloManager.spawnHolo(newBar, p);
		}
		opponent.setLifeBar(newBar);
		
		for (Player p : Bukkit.getOnlinePlayers()) {
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(new PacketPlayOutAttachEntity(0, newBar.getEntity(), opponent.getNpc().getEntity()));
		}
		
		if (opponent.getLife() <= 0)
			death(opponent, player, list);
	}
	
	private static void death(Warrior opponent, Player player, List<Warrior> list) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			HoloManager.deleteHolo(p, opponent.lifeBar);
			NPCManager.deleteNPC(p, opponent.getNpc());
		}
		
		list.remove(opponent);
		GamePlayer gp = Utils.getGamePlayer(player);
		gp.setGold(gp.getGold() + opponent.getGold());
		gp.setXp(gp.getXp() + opponent.getXp());
		player.sendMessage("§aYou earned §6"+opponent.getGold()+" gold §aand §b"+opponent.getXp()+"xp");
	}
	
	private static String getLifeBar(int lifePercent) {
		if (lifePercent <= 10) return "§c██████████";
		if (lifePercent <= 20) return "§a█§c█████████";
		if (lifePercent <= 30) return "§a██§c████████";
		if (lifePercent <= 40) return "§a███§c███████";
		if (lifePercent <= 50) return "§a████§c██████";
		if (lifePercent < 60) return "§a█████§c█████";
		if (lifePercent < 70) return "§a██████§c████";
		if (lifePercent < 80) return "§a███████§c███";
		if (lifePercent < 90) return "§a████████§c██";
		if (lifePercent < 100) return "§a█████████§c█";
		return "§a██████████";
	}
	
	private static void sendAddPacket(float Z, float yaw, GamePlayer player1, GamePlayer player2, WarriorType type, int item) {
		NPC warrior = new NPC(UUID.randomUUID(), "Warrior")
				.setLocation(new Location(Bukkit.getWorld("world"), 983.5f, 54, Z, yaw, 0))
				.setSkin(SkinLoader.getSkinById(type.ordinal()))
				.setCapeVisible(false)
				.setNameVisible(false);
		
		Holo lifeBar = new Holo(UUID.randomUUID(), "§a██████████")
		        .setLocation(new Location(Bukkit.getWorld("world"), 983.5f, 54, Z, yaw, 0));
		
		HoloManager.spawnHolo(lifeBar, player1.getPlayer());
		NPCManager.spawnNPC(warrior, player1.getPlayer());
		
		Warrior w = null;
		if (type == WarriorType.Knight)
			w = knight(warrior, lifeBar);
		if (type == WarriorType.Archer)
			w = archer(warrior, lifeBar);
		if (type == WarriorType.Berserk)
			w = berserk(warrior, lifeBar);
		
		player1.getWarriors().add(w);
		
		((CraftPlayer) player1.getPlayer()).getHandle().playerConnection.sendPacket(new PacketPlayOutEntityEquipment(warrior.getEntityId(), 0, new ItemStack(Item.getById(item))));
		((CraftPlayer) player1.getPlayer()).getHandle().playerConnection.sendPacket(new PacketPlayOutAttachEntity(0, lifeBar.getEntity(), warrior.getEntity()));
		
		warrior = new NPC(UUID.randomUUID(), "Warrior")
				.setLocation(new Location(Bukkit.getWorld("world"), 983.5f, 54, Z, yaw, 0))
				.setSkin(SkinLoader.getSkinById(type.ordinal()))
				.setCapeVisible(false)
				.setNameVisible(false);
		
		lifeBar = new Holo(UUID.randomUUID(), "§a██████████")
		        .setLocation(new Location(Bukkit.getWorld("world"), 983.5f, 54, Z, yaw, 0));
		HoloManager.spawnHolo(lifeBar, player2.getPlayer());
		NPCManager.spawnNPC(warrior, player2.getPlayer());
		
		if (type == WarriorType.Knight)
			w = knight(warrior, lifeBar);
		if (type == WarriorType.Archer)
			w = archer(warrior, lifeBar);
		if (type == WarriorType.Berserk)
			w = berserk(warrior, lifeBar);
		
		player2.getOpponents().add(w);
		((CraftPlayer) player2.getPlayer()).getHandle().playerConnection.sendPacket(new PacketPlayOutEntityEquipment(warrior.getEntityId(), 0, new ItemStack(Item.getById(item))));
		((CraftPlayer) player2.getPlayer()).getHandle().playerConnection.sendPacket(new PacketPlayOutAttachEntity(0, lifeBar.getEntity(), warrior.getEntity()));
	}
	
	private static Warrior knight(NPC warrior, Holo lifeBar) {
		return new Warrior(warrior, lifeBar, 100, 20, 15, 3, 20, WarriorType.Knight);
	}
	
	private static Warrior archer(NPC warrior, Holo lifeBar) {
		return new Warrior(warrior, lifeBar, 50, 10, 30, 5, 25, WarriorType.Archer);
	}
	
	private static Warrior berserk(NPC warrior, Holo lifeBar) {
		return new Warrior(warrior, lifeBar, 200, 30, 100, 10, 40, WarriorType.Berserk);
	}
}
