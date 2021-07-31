package com.blackout.aow.utils;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.blackout.aow.core.GamePlayer;
import com.blackout.aow.core.Warrior;
import com.blackout.aow.core.WarriorType;
import com.blackout.aow.main.Main;
import com.blackout.holoapi.core.Holo;
import com.blackout.holoapi.utils.HoloManager;
import com.blackout.npcapi.core.NPC;
import com.blackout.npcapi.utils.NPCManager;
import com.blackout.npcapi.utils.SkinLoader;

import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.PacketPlayOutAttachEntity;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityEquipment;

public class WarriorUtils {

	public static void createNewWarrior(NPC npc, Player p, float Z, float yaw) {
		new BukkitRunnable() {
			public void run() {
					switch(npc.getName().substring(2)) {
						case "Clubman": purchaseWarrior(Z, yaw, p, "Clubman", WarriorType.Type.Clubman); break;
						case "Slingshot": purchaseWarrior(Z, yaw, p, "Slingshot", WarriorType.Type.Slingshot); break;
						case "Spearman": purchaseWarrior(Z, yaw, p, "Spearman", WarriorType.Type.Spearman); break;
						case "Knight": purchaseWarrior(Z, yaw, p, "Knight", WarriorType.Type.Knight); break;
						case "Archer": purchaseWarrior(Z, yaw, p, "Archer", WarriorType.Type.Archer); break;
						case "Berserk": purchaseWarrior(Z, yaw, p, "Berserk", WarriorType.Type.Berserk); break;
						default: return;
					}
			}
		}.runTaskLater(Main.getPlugin(Main.class), 5L);
	}
	
	private static void purchaseWarrior(float Z, float yaw, Player p, String warriorName, WarriorType.Type type) {
		Warrior w = getWarriorType(type, null, null);
		
		GamePlayer gp = Utils.getGamePlayer(p);
		if (gp.getGold() < w.getCost()) {
			p.sendMessage("§cSorry but you don't have enough gold for this");
			p.playSound(p.getLocation(), Sound.ANVIL_LAND, 1.0f, 1.0f);
			return;
		}
		gp.setGold(gp.getGold() - w.getCost());
		p.sendMessage("§b"+warriorName+" §acreated for §6"+w.getCost()+" §agold");
		p.playSound(p.getLocation(), Sound.NOTE_PLING, 1.0f, 1.0f);
		if (gp == Main.player1) {
			sendAddPacket(Z, yaw, Main.player1, Main.player2, type);
		} else {
			sendAddPacket(Z, yaw, Main.player2, Main.player1, type);
		}
		
	}
	
	public static void updateLife(Warrior warrior, Warrior opponent, GamePlayer gp) {
		opponent.setLife(opponent.getLife() - warrior.getDamage());
		int lifePercent = (opponent.getLife() * 100 / opponent.getMaxLife());
		
		HoloManager.deleteHolo(gp.getPlayer(), opponent.getLifeBar());
		
		Location newLoc = new Location (opponent.getNpc().getLocation().getWorld(), opponent.getNpc().getLocation().getX(), opponent.getNpc().getLocation().getY(), opponent.getNpc().getLocation().getZ());
		newLoc.setY(newLoc.getY() + 1.4f);
		
		Holo newBar = new Holo(UUID.randomUUID(), getLifeBar(lifePercent))
		        .setLocation(newLoc);
		
		HoloManager.spawnHolo(newBar, gp.getPlayer());
		opponent.setLifeBar(newBar);
		
		((CraftPlayer) gp.getPlayer()).getHandle().playerConnection.sendPacket(new PacketPlayOutAttachEntity(0, newBar.getEntity(), opponent.getNpc().getEntity()));
	}
	
	public static void death(GamePlayer gp) {
		Warrior w = gp.getOpponents().get(0);
		if (w.getLife() <= 0)  {
			gp.getOpponents().remove(w);
			HoloManager.deleteHolo(gp.getPlayer(), w.getLifeBar());
			NPCManager.deleteNPC(gp.getPlayer(), w.getNpc());
			
			gp.setGold(gp.getGold() + w.getGold());
			gp.setXp(gp.getXp() + w.getXp());
			gp.getPlayer().sendMessage("§aYou earned §6"+w.getGold()+" gold §aand §b"+w.getXp()+"xp");
		}
	
		w = gp.getWarriors().get(0);
		if (w.getLife() <= 0) {
			gp.getWarriors().remove(w);
			HoloManager.deleteHolo(gp.getPlayer(), w.getLifeBar());
			NPCManager.deleteNPC(gp.getPlayer(), w.getNpc());
		}
	}
	
	private static String getLifeBar(int lifePercent) {
		if (lifePercent <= 10) return "§8██████████";
		if (lifePercent <= 20) return "§4█§8█████████";
		if (lifePercent <= 30) return "§e██§8████████";
		if (lifePercent <= 40) return "§6███§8███████";
		if (lifePercent <= 50) return "§6████§8██████";
		if (lifePercent <= 60) return "§e█████§8█████";
		if (lifePercent <= 70) return "§e██████§8████";
		if (lifePercent <= 80) return "§a███████§8███";
		if (lifePercent <= 90) return "§a████████§8██";
		if (lifePercent < 100) return "§a█████████§8█";
		return "§a██████████";
	}
	
	private static void sendAddPacket(float Z, float yaw, GamePlayer player1, GamePlayer player2, WarriorType.Type type) {
		GameUtils.updateScoreboard(player1);
		
		NPC warrior = new NPC(UUID.randomUUID(), "Warrior")
				.setLocation(new Location(Bukkit.getWorld("world"), 983.5f, 54, Z, yaw, 0))
				.setSkin(SkinLoader.getSkinById(type.ordinal()))
				.setCapeVisible(false)
				.setNameVisible(false);
		
		Holo lifeBar = new Holo(UUID.randomUUID(), "§a██████████")
		        .setLocation(new Location(Bukkit.getWorld("world"), 983.5f, 54, Z, yaw, 0));
		
		HoloManager.spawnHolo(lifeBar, player1.getPlayer());
		NPCManager.spawnNPC(warrior, player1.getPlayer());
		
		Warrior w = getWarriorType(type, warrior, lifeBar);
		
		player1.getWarriors().add(w);
		
		((CraftPlayer) player1.getPlayer()).getHandle().playerConnection.sendPacket(new PacketPlayOutEntityEquipment(warrior.getEntityId(), 0, new ItemStack(Item.getById(w.getHeldItemID()))));
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
		
		w = getWarriorType(type, warrior, lifeBar);
		
		player2.getOpponents().add(w);
		((CraftPlayer) player2.getPlayer()).getHandle().playerConnection.sendPacket(new PacketPlayOutEntityEquipment(warrior.getEntityId(), 0, new ItemStack(Item.getById(w.getHeldItemID()))));
		((CraftPlayer) player2.getPlayer()).getHandle().playerConnection.sendPacket(new PacketPlayOutAttachEntity(0, lifeBar.getEntity(), warrior.getEntity()));
	}
	
	private static Warrior getWarriorType(WarriorType.Type type, NPC warrior, Holo lifeBar) {
		switch(type) {
			case Clubman: return (WarriorType.clubman(warrior, lifeBar));
			case Slingshot: return (WarriorType.slingshot(warrior, lifeBar));
			case Spearman: return (WarriorType.spearman(warrior, lifeBar));
			case Knight: return (WarriorType.knight(warrior, lifeBar));
			case Archer: return (WarriorType.archer(warrior, lifeBar));
			case Berserk: return (WarriorType.berserk(warrior, lifeBar));
			default: return null;
		}
	}
}
