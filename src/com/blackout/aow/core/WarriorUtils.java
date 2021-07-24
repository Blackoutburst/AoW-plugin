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
import com.blackout.holoapi.core.Holo;
import com.blackout.holoapi.utils.HoloManager;
import com.blackout.npcapi.core.NPC;
import com.blackout.npcapi.utils.NPCManager;

import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.PacketPlayOutAnimation;
import net.minecraft.server.v1_8_R3.PacketPlayOutAttachEntity;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityEquipment;
import net.minecraft.server.v1_8_R3.PlayerConnection;

public class WarriorUtils {

	public static void createNewWarrior(NPC npc, Player p, float Z, float yaw, List<Warrior> playerList) {
		NPC warrior = new NPC(UUID.randomUUID(), "Warrior")
				.setLocation(new Location(Bukkit.getWorld("world"), 983.5f, 54, Z, yaw, 0))
				.setSkin(npc.getSkin())
				.setCapeVisible(false)
				.setNameVisible(false);
		
		Holo lifeBar = new Holo(UUID.randomUUID(), "§a██████████")
		        .setLocation(new Location(Bukkit.getWorld("world"), 983.5f, 54, Z, yaw, 0));
		HoloManager.spawnHolo(lifeBar, p);
		
		new BukkitRunnable() {
			public void run() {
				NPCManager.spawnNPC(warrior, p);
				if (npc.getName().contains("Knight"))
					createKnight(warrior, lifeBar, playerList, p);
				if (npc.getName().contains("Archer"))
					createArcher(warrior, lifeBar, playerList, p);
				if (npc.getName().contains("Berserk"))
					createBerserk(warrior, lifeBar, playerList, p);
			}
		}.runTaskLater(Main.getPlugin(Main.class), 5L);
	}
	
	private static void createKnight(NPC warrior, Holo lifeBar, List<Warrior> playerList, Player p) {
		playerList.add(new Warrior(warrior, lifeBar, 100, 20, 15, 3, 20, WarriorType.Knight));
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(new PacketPlayOutEntityEquipment(warrior.getEntityId(), 0, new ItemStack(Item.getById(267))));
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(new PacketPlayOutAttachEntity(0, lifeBar.getEntity(), warrior.getEntity()));
	}
	
	private static void createArcher(NPC warrior, Holo lifeBar, List<Warrior> playerList, Player p) {
		playerList.add(new Warrior(warrior, lifeBar, 50, 10, 30, 5, 25, WarriorType.Archer));
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(new PacketPlayOutEntityEquipment(warrior.getEntityId(), 0, new ItemStack(Item.getById(261))));
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(new PacketPlayOutAttachEntity(0, lifeBar.getEntity(), warrior.getEntity()));
	}
	
	private static void createBerserk(NPC warrior, Holo lifeBar, List<Warrior> playerList, Player p) {
		playerList.add(new Warrior(warrior, lifeBar, 200, 30, 100, 10, 40, WarriorType.Berserk));
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(new PacketPlayOutEntityEquipment(warrior.getEntityId(), 0, new ItemStack(Item.getById(258))));
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(new PacketPlayOutAttachEntity(0, lifeBar.getEntity(), warrior.getEntity()));
	}
	
	public static void fight(Warrior warrior, Player player, int index, List<Warrior> opponents) {
		if (opponents.size() > 0) {
			Warrior opponent = opponents.get(0);
			double myZ = warrior.getNpc().getLocation().getZ();
			double prevZ = opponent.getNpc().getLocation().getZ();
			
			if (Math.abs(prevZ - myZ) < 2 && warrior.type != WarriorType.Archer) {
				PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
				connection.sendPacket(new PacketPlayOutAnimation(warrior.getNpc().getEntity(), 0));
				connection.sendPacket(new PacketPlayOutAnimation(opponent.getNpc().getEntity(), 1));
				player.playSound(opponent.npc.getLocation(), Sound.HURT_FLESH, 1.0f, 1.0f);
				updateLife(warrior, opponent, player, opponents);
			}
			
			if (Math.abs(prevZ - myZ) < 6 && warrior.type == WarriorType.Archer) {
				PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
				player.playSound(warrior.npc.getLocation(), Sound.SHOOT_ARROW, 1.0f, 1.0f);
				connection.sendPacket(new PacketPlayOutAnimation(warrior.getNpc().getEntity(), 0));
				connection.sendPacket(new PacketPlayOutAnimation(opponent.getNpc().getEntity(), 1));
				player.playSound(opponent.npc.getLocation(), Sound.HURT_FLESH, 1.0f, 1.0f);
				updateLife(warrior, opponent, player, opponents);
			}
		}
	}
	
	private static void updateLife(Warrior warrior, Warrior opponent, Player player, List<Warrior> list) {
		opponent.setLife(opponent.getLife() - warrior.getDamage());
		int lifePercent = (opponent.getLife() * 100 / opponent.getMaxLife());
		
		HoloManager.deleteHolo(player, opponent.lifeBar);
		
		Location newLoc = new Location (opponent.getNpc().getLocation().getWorld(), opponent.getNpc().getLocation().getX(), opponent.getNpc().getLocation().getY(), opponent.getNpc().getLocation().getZ());
		newLoc.setY(newLoc.getY() + 1.4f);
		
		Holo newBar = new Holo(UUID.randomUUID(), getLifeBar(lifePercent))
		        .setLocation(newLoc);
		HoloManager.spawnHolo(newBar, player);
		opponent.setLifeBar(newBar);
		
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutAttachEntity(0, newBar.getEntity(), opponent.getNpc().getEntity()));
	
		if (opponent.getLife() <= 0) {
			HoloManager.deleteHolo(player, opponent.lifeBar);
			list.remove(opponent);
			NPCManager.deleteNPC(player, opponent.getNpc());
		}
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
}
