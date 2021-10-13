package com.blackout.aow.warrior;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;

import com.blackout.aow.core.AowPlayer;
import com.blackout.aow.core.Core;
import com.blackout.aow.nms.NMSAnimation;
import com.blackout.aow.nms.NMSParticle;

import net.minecraft.server.v1_8_R3.EnumParticle;

public class Cannoneer extends WarriorLogical {

	public Cannoneer(AowPlayer owner, WarriorOptions options, Location position) {
		super(owner, options, position);
	}

	@Override
	protected void update(int index) {
		fight(index);
		walk(index);
		die();	
	}

	@Override
	protected void fight(int index) {
		this.getOptions().combatDelay--;
		
		if (this.getOptions().combatDelay <= 0 && canFight(index)) {
			this.getOptions().combatDelay = this.getOptions().maxCombatDelay;
			Bukkit.getWorld("world").playSound(this.position, Sound.FIREWORK_LARGE_BLAST, 1.0f, 1.0f);
			
			WarriorLogical op = this.owner.getPlayerID() == 0 ? Core.redWarrior.get(0) : Core.blueWarrior.get(0);
			op.getOptions().setHealth(op.getOptions().getHealth() - this.options.damage);
			
			for (AowPlayer p : Core.aowplayers) {
				NMSParticle.spawnParticle(p.getPlayer(), EnumParticle.EXPLOSION_LARGE, (float)(op.getPosition().getX()), (float)(op.getPosition().getY()), (float)(op.getPosition().getZ()));
				if (this.owner.getPlayerID() == 0) {
					NMSAnimation.animation(p.getPlayer(), p.getBlueNPC().get(index).getNpc(), 0);
					NMSAnimation.animation(p.getPlayer(), p.getRedNPC().get(0).getNpc(), 1);
					p.getRedNPC().get(0).updateLifeBar(p.getPlayer(), op);
				} else {
					NMSAnimation.animation(p.getPlayer(), p.getRedNPC().get(index).getNpc(), 0);
					NMSAnimation.animation(p.getPlayer(), p.getBlueNPC().get(0).getNpc(), 1);
					p.getBlueNPC().get(0).updateLifeBar(p.getPlayer(), op);
				}
			}
		}
		
		if (this.getOptions().combatDelay <= 0 && canFightBase(index)) {
			this.getOptions().combatDelay = this.getOptions().maxCombatDelay;
			Bukkit.getWorld("world").playSound(this.position, Sound.FIREWORK_LARGE_BLAST, 1.0f, 1.0f);
			
			if (this.owner.getPlayerID() == 0) {
				Core.redBase.setLife((int) (Core.redBase.getLife() - this.options.damage));
				Bukkit.getWorld("world").playSound(Core.redBase.getLocation(), Sound.ZOMBIE_WOODBREAK, 1.0f, 1.0f);
			} else {
				Core.blueBase.setLife((int) (Core.blueBase.getLife() - this.options.damage));
				Bukkit.getWorld("world").playSound(Core.blueBase.getLocation(), Sound.ZOMBIE_WOODBREAK, 1.0f, 1.0f);
			}
			
			Location baseLoc = this.owner.getPlayerID() == 0 ? Core.redBase.getLocation() :  Core.blueBase.getLocation(); 
			for (AowPlayer p : Core.aowplayers) {
				NMSParticle.spawnParticle(p.getPlayer(), EnumParticle.EXPLOSION_LARGE, (float)(baseLoc.getX()), (float)(baseLoc.getY()), (float)(baseLoc.getZ()));
				if (this.owner.getPlayerID() == 0) {
					Core.redBase.updateLifeBar(p, p.getRedBaseLife(), false);
					NMSAnimation.animation(p.getPlayer(), p.getBlueNPC().get(index).getNpc(), 0);
				} else {
					Core.blueBase.updateLifeBar(p, p.getBlueBaseLife(), true);
					NMSAnimation.animation(p.getPlayer(), p.getRedNPC().get(index).getNpc(), 0);
				}
			}
		}
	}
}
