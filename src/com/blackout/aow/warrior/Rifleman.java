package com.blackout.aow.warrior;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.blackout.aow.core.AowPlayer;
import com.blackout.aow.nms.NMSAnimation;
import com.blackout.aow.nms.NMSParticle;
import com.blackout.holoapi.core.Holo;
import com.blackout.npcapi.core.NPC;

import net.minecraft.server.v1_8_R3.EnumParticle;

public class Rifleman extends Warrior {

	public Rifleman(NPC npc, AowPlayer owner, Holo lifeBar, WarriorOptions options, Location position) {
		super(npc, owner, lifeBar, options, position);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void update(Player p, int index) {
		walk(p, index);
		fight(p, index);
		die(p);		
	}

	@Override
	protected void fight(Player p, int index) {
		this.getOptions().combatDelay--;
		AowPlayer aowp = AowPlayer.getFromPlayer(p);
		
		if (this.getOptions().combatDelay <= 0 && canFight(index)) {
			this.getOptions().combatDelay = this.getOptions().maxCombatDelay;
			Bukkit.getWorld("world").playSound(this.position, Sound.IRONGOLEM_HIT, 1.0f, 4.0f);
			
			Warrior op = this.owner.getPlayerID() == 0 ? aowp.getOpponent().get(0) : aowp.getWarriors().get(0);
			
			NMSParticle.spawnParticle(p, EnumParticle.EXPLOSION_NORMAL, (float)(op.getPosition().getX()), (float)(op.getPosition().getY()) + 1.5f, (float)(op.getPosition().getZ()));
			
			NMSAnimation.animation(p, op.getNpc(), 1);
			op.getOptions().setHealth(op.getOptions().getHealth() - this.options.damage);
			updateLifeBar(p, op);
		}
	}

}
