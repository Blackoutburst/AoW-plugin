package com.blackout.aow.core;

import java.util.List;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.blackout.aow.main.Main;
import com.blackout.holoapi.core.Holo;
import com.blackout.holoapi.utils.HoloManager;
import com.blackout.npcapi.core.NPC;
import com.blackout.npcapi.utils.NPCManager;

import net.minecraft.server.v1_8_R3.PacketPlayOutAnimation;
import net.minecraft.server.v1_8_R3.PacketPlayOutAttachEntity;
import net.minecraft.server.v1_8_R3.PlayerConnection;

public class Warrior {

	protected NPC npc;
	protected Holo lifeBar;
	protected int life;
	protected int maxLife;
	protected int damage;
	protected int cost;
	protected int xp;
	protected int gold;
	protected WarriorType type;
	
	
	public Warrior(NPC npc, Holo lifeBar, int life, int damage, int cost, int xp, int gold, WarriorType type) {
		this.npc = npc;
		this.lifeBar = lifeBar;
		this.life = life;
		this.maxLife = life;
		this.damage = damage;
		this.cost = cost;
		this.xp = xp;
		this.gold = gold;
		this.type = type;
	}

	public NPC getNpc() {
		return npc;
	}

	public void setNpc(NPC npc) {
		this.npc = npc;
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public int getXp() {
		return xp;
	}

	public void setXp(int xp) {
		this.xp = xp;
	}

	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}

	public WarriorType getType() {
		return type;
	}

	public void setType(WarriorType type) {
		this.type = type;
	}
	
	public int getMaxLife() {
		return maxLife;
	}
	
	public Holo getLifeBar() {
		return lifeBar;
	}

	public void setLifeBar(Holo lifeBar) {
		this.lifeBar = lifeBar;
	}

	public boolean canWalkRight(int index) {
		if (this.getNpc().getLocation().getZ() > 1345.5f) return false;
		
		double prevZ = 0;
		double myZ = this.getNpc().getLocation().getZ();
		
		if (Main.player2NPC.size() > 0) {
			prevZ = Main.player2NPC.get(0).getNpc().getLocation().getZ();
			if (Math.abs(prevZ - myZ) < 1.5f) {
				return false;
			}
		}
		
		if (index == 0) return true;
		prevZ = Main.player1NPC.get(index - 1).getNpc().getLocation().getZ();
		
		if (Math.abs(prevZ - myZ) < 2) {
			return false;
		}
		
		return true;
	}
	
	public boolean canWalkLeft(int index) {
		if (this.getNpc().getLocation().getZ() < 1307.5f) return false;
		
		double prevZ = 0;
		double myZ = this.getNpc().getLocation().getZ();
		
		if (Main.player1NPC.size() > 0) {
			prevZ = Main.player1NPC.get(0).getNpc().getLocation().getZ();
			if (Math.abs(prevZ - myZ) < 1.5f) {
				return false;
			}
		}
		
		if (index == 0) return true;
		prevZ = Main.player2NPC.get(index - 1).getNpc().getLocation().getZ();
		
		if (Math.abs(prevZ - myZ) < 2) {
			return false;
		}
		
		return true;
	}
	
	public void fightRight(Player player, int index) {
		if (Main.player2NPC.size() > 0) {
			Warrior opponent = Main.player2NPC.get(0);
			double myZ = this.getNpc().getLocation().getZ();
			double prevZ = opponent.getNpc().getLocation().getZ();
			
			if (Math.abs(prevZ - myZ) < 2 && this.type != WarriorType.Archer) {
				PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
				connection.sendPacket(new PacketPlayOutAnimation(this.getNpc().getEntity(), 0));
				connection.sendPacket(new PacketPlayOutAnimation(opponent.getNpc().getEntity(), 1));
				player.playSound(opponent.npc.getLocation(), Sound.HURT_FLESH, 1.0f, 1.0f);
				updateLife(opponent, player, Main.player2NPC);
			}
			
			if (Math.abs(prevZ - myZ) < 6 && this.type == WarriorType.Archer) {
				PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
				player.playSound(this.npc.getLocation(), Sound.SHOOT_ARROW, 1.0f, 1.0f);
				connection.sendPacket(new PacketPlayOutAnimation(this.getNpc().getEntity(), 0));
				connection.sendPacket(new PacketPlayOutAnimation(opponent.getNpc().getEntity(), 1));
				player.playSound(opponent.npc.getLocation(), Sound.HURT_FLESH, 1.0f, 1.0f);
				updateLife(opponent, player, Main.player2NPC);
			}
		}
	}
	
	public void fightLeft(Player player, int index) {
		if (Main.player1NPC.size() > 0) {
			Warrior opponent = Main.player1NPC.get(0);
			double myZ = this.getNpc().getLocation().getZ();
			double prevZ = opponent.getNpc().getLocation().getZ();
			
			if (Math.abs(prevZ - myZ) < 2 && this.type != WarriorType.Archer) {
				PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
				connection.sendPacket(new PacketPlayOutAnimation(this.getNpc().getEntity(), 0));
				connection.sendPacket(new PacketPlayOutAnimation(opponent.getNpc().getEntity(), 1));
				player.playSound(opponent.npc.getLocation(), Sound.HURT_FLESH, 1.0f, 1.0f);
				updateLife(opponent, player, Main.player1NPC);
			}
			
			if (Math.abs(prevZ - myZ) < 6 && this.type == WarriorType.Archer) {
				PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
				player.playSound(this.npc.getLocation(), Sound.SHOOT_ARROW, 1.0f, 1.0f);
				connection.sendPacket(new PacketPlayOutAnimation(this.getNpc().getEntity(), 0));
				connection.sendPacket(new PacketPlayOutAnimation(opponent.getNpc().getEntity(), 1));
				player.playSound(opponent.npc.getLocation(), Sound.HURT_FLESH, 1.0f, 1.0f);
				updateLife(opponent, player, Main.player1NPC);
			}
		}
	}
	
	private void updateLife(Warrior opponent, Player player, List<Warrior> list) {
		opponent.setLife(opponent.getLife() - this.getDamage());
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
	
	private String getLifeBar(int lifePercent) {
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
