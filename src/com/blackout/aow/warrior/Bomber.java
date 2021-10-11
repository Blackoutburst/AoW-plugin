package com.blackout.aow.warrior;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;

import com.blackout.aow.core.AowPlayer;
import com.blackout.aow.main.Main;
import com.blackout.aow.nms.NMSAnimation;
import com.blackout.aow.nms.NMSParticle;

import net.minecraft.server.v1_8_R3.EnumParticle;

public class Bomber extends WarriorLogical {

	public Bomber(AowPlayer owner, WarriorOptions options, Location position) {
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
			Bukkit.getWorld("world").playSound(this.position, Sound.EXPLODE, 1.0f, 1.0f);
			
			WarriorLogical op = this.owner.getPlayerID() == 0 ? Main.redWarrior.get(0) : Main.blueWarrior.get(0);
			op.getOptions().setHealth(op.getOptions().getHealth() - this.options.damage);
			
			
			for (AowPlayer p : Main.aowplayers) {
				NMSParticle.spawnParticle(p.getPlayer(), EnumParticle.EXPLOSION_HUGE, (float)(op.getPosition().getX()), (float)(op.getPosition().getY()), (float)(op.getPosition().getZ()));
				if (this.owner.getPlayerID() == 0) {
					NMSAnimation.animation(p.getPlayer(), p.getBlueNPC().get(0).getNpc(), 0);
					NMSAnimation.animation(p.getPlayer(), p.getRedNPC().get(0).getNpc(), 1);
					p.getRedNPC().get(0).updateLifeBar(p.getPlayer(), op);
				} else {
					NMSAnimation.animation(p.getPlayer(), p.getRedNPC().get(0).getNpc(), 0);
					NMSAnimation.animation(p.getPlayer(), p.getBlueNPC().get(0).getNpc(), 1);
					p.getBlueNPC().get(0).updateLifeBar(p.getPlayer(), op);
				}
			}
		}
	}
}
