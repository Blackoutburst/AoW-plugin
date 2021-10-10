package com.blackout.aow.warrior;

import java.util.Random;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.blackout.aow.core.AowPlayer;
import com.blackout.aow.main.Main;
import com.blackout.aow.nms.NMSAttachEntity;
import com.blackout.aow.nms.NMSEntityMove;
import com.blackout.aow.nms.NMSParticle;
import com.blackout.holoapi.core.Holo;
import com.blackout.holoapi.utils.HoloManager;
import com.blackout.npcapi.core.NPC;

import net.minecraft.server.v1_8_R3.EnumParticle;

public abstract class Warrior {
	
	protected NPC npc;
	protected AowPlayer owner;
	protected Holo lifeBar;
	protected WarriorOptions options;
	protected Location position;
	protected boolean dead;
	
	public Warrior(NPC npc, AowPlayer owner, Holo lifeBar, WarriorOptions options, Location position) {
		this.npc = npc;
		this.owner = owner;
		this.lifeBar = lifeBar;
		this.options = options;
		this.position = position;
		this.dead = false;
	}
	
	protected abstract void update(Player p, int index);
	protected abstract void fight(Player p, int index);

	protected void updateLifeBar(Player p, Warrior w) {
		int lifePercent = (int) (w.getOptions().getHealth() * 100 / w.getOptions().getMaxHealth());
		HoloManager.deleteHolo(p, w.getLifeBar());
		
		Location loc = w.getPosition().clone();
		loc.setY(loc.getY() + 1.5f);
		
		Holo life = new Holo(UUID.randomUUID(), getLifeBarString(lifePercent))
		        .setLocation(loc);
		HoloManager.spawnHolo(life, p);
		w.setLifeBar(life);
		NMSAttachEntity.attach(p, life, w.npc);
	}
	
	private String getLifeBarString(int lifePercent) {
		if (lifePercent <= 10) return "§8[§c||||||||||§8]";
		if (lifePercent <= 20) return "§8[§a|§c|||||||||§8]";
		if (lifePercent <= 30) return "§8[§a||§c||||||||§8]";
		if (lifePercent <= 40) return "§8[§a|||§c|||||||§8]";
		if (lifePercent <= 50) return "§8[§a||||§c||||||§8]";
		if (lifePercent <= 60) return "§8[§a|||||§c|||||§8]";
		if (lifePercent <= 70) return "§8[§a||||||§c||||§8]";
		if (lifePercent <= 80) return "§8[§a|||||||§c|||§8]";
		if (lifePercent <= 90) return "§8[§a||||||||§c||§8]";
		if (lifePercent < 100) return "§8[§a|||||||||§c|§8]";
		return "§8[§a||||||||||§8]";
	}
	
	protected void walk(Player p, int index) {
		if (this.owner.getPlayerID() == 0) { //Toward right
			if (this.position.getZ() < WarriorManager.player2Base.getZ() && canWalk(index)) {
				NMSEntityMove.move(p, this.npc.getEntityId(), (byte)(0), (byte)(0), (byte)(5));
				this.position.setZ((this.position.getZ() + (5.0f / 32.0f)));
			}
		} else { // Toward left
			if (this.position.getZ() > WarriorManager.player1Base.getZ() && canWalk(index)) {
				NMSEntityMove.move(p, this.npc.getEntityId(), (byte)(0), (byte)(0), (byte)(-5));
				position.setZ(position.getZ() - (5.0f / 32.0f));
			}
		}
	}
	
	private boolean canWalk(int index) {
		if (this.owner.getPlayerID() == 0) {//Toward right
			double prevZ = 0;
			double myZ = this.position.getZ();
			
			if (this.owner.getOpponent().size() > 0) {
				prevZ = this.owner.getOpponent().get(0).getPosition().getZ();
				if (Math.abs(prevZ - myZ) < 1.5f) {
					return (false);
				}
			}
			
			if (index == 0) return (true);
			prevZ = this.owner.getWarriors().get(index - 1).getPosition().getZ();
			
			return (prevZ - myZ > 1.5f);
		} else { // Toward left
			double prevZ = 0;
			double myZ = this.position.getZ();
			
			if (this.owner.getWarriors().size() > 0) {
				prevZ = this.owner.getWarriors().get(0).getPosition().getZ();
				if (Math.abs(prevZ - myZ) < 1.5f) {
					return (false);
				}
			}
			
			if (index == 0) return (true);
			prevZ = this.owner.getOpponent().get(index - 1).getPosition().getZ();
		
			return (myZ - prevZ > 1.5f);
		}
	}
	
	protected boolean canFight(int index) {
		if (this.owner.getPlayerID() == 0) {//Toward right
			double prevZ = 0;
			double myZ = this.position.getZ();
			
			if (this.owner.getOpponent().size() > 0) {
				prevZ = this.owner.getOpponent().get(0).getPosition().getZ();
				if (Math.abs(prevZ - myZ) < this.options.range) {
					return (true);
				}
			}
		} else { // Toward left
			double prevZ = 0;
			double myZ = this.position.getZ();
			
			if (this.owner.getWarriors().size() > 0) {
				prevZ = this.owner.getWarriors().get(0).getPosition().getZ();
				if (Math.abs(prevZ - myZ) < this.options.range) {
					return (true);
				}
			}
		}
		return (false);
	}
	
	protected void die(Player p) {
		if (this.options.health <= 0 && !this.dead) {
			this.dead = true;
			if (this.owner.getPlayerID() == 0 && AowPlayer.getFromPlayer(p) == Main.player2) {
				Main.player2.setXp((int) (Main.player2.getXp() + this.options.xpDrop));
				Main.player2.setGold((int) (Main.player2.getGold() + this.options.goldDrop));
				Main.player2.getPlayer().sendMessage("§aYou received §6"+(int) (this.options.goldDrop)+" gold and §b"+(int) (this.options.xpDrop)+" xp§a!");
			} else if (this.owner.getPlayerID() == 1 && AowPlayer.getFromPlayer(p) == Main.player1) {
				Main.player1.setXp((int) (Main.player1.getXp() + this.options.xpDrop));
				Main.player1.setGold((int) (Main.player1.getGold() + this.options.goldDrop));
				Main.player1.getPlayer().sendMessage("§aYou received §6"+(int) (this.options.goldDrop)+" gold and §b"+(int) (this.options.xpDrop)+" xp§a!");
			}
			for (int i = 0; i < 10; i++) {
				NMSParticle.spawnParticle(p, EnumParticle.CLOUD, (float)(this.position.getX()) + new Random().nextFloat() - 0.5f, (float)(this.position.getY()) + 1.6f - (0.1f * i), (float)(this.position.getZ()) + new Random().nextFloat() - 0.5f);
			}
		}
	}
	
	public NPC getNpc() {
		return npc;
	}

	public void setNpc(NPC npc) {
		this.npc = npc;
	}

	public AowPlayer getOwner() {
		return owner;
	}

	public void setOwner(AowPlayer owner) {
		this.owner = owner;
	}

	public Holo getLifeBar() {
		return lifeBar;
	}

	public void setLifeBar(Holo lifeBar) {
		this.lifeBar = lifeBar;
	}

	public WarriorOptions getOptions() {
		return options;
	}

	public void setOptions(WarriorOptions options) {
		this.options = options;
	}

	public Location getPosition() {
		return position;
	}

	public void setPosition(Location position) {
		this.position = position;
	}

	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}
	
}
