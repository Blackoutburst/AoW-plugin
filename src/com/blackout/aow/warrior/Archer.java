package com.blackout.aow.warrior;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;

import com.blackout.aow.core.AowPlayer;
import com.blackout.aow.core.Core;
import com.blackout.aow.nms.NMSAnimation;

public class Archer extends WarriorLogical {

	public Archer(AowPlayer owner, WarriorOptions options, Location position) {
		super(owner, options, position);
	}

	@Override
	protected void update(int index) {
		fight(index);
		walk(index);
		die(index);
	}

	@Override
	protected void fight(int index) {
		this.getOptions().combatDelay--;
		
		if (this.getOptions().combatDelay <= 0 && canFight(index)) {
			this.getOptions().combatDelay = this.getOptions().maxCombatDelay;
			Bukkit.getWorld("world").playSound(this.position, Sound.SHOOT_ARROW, 1.0f, 1.0f);
			
			WarriorLogical op = this.owner.getPlayerID() == 0 ? Core.redWarrior.get(0) : Core.blueWarrior.get(0);
			op.getOptions().setHealth(op.getOptions().getHealth() - this.options.damage);
			
			for (AowPlayer p : Core.aowplayers) {
				if (this.owner.getPlayerID() == 0) {
					if (p.getBlueNPC().size() <= index) continue;
					NMSAnimation.animation(p.getPlayer(), p.getBlueNPC().get(index).getNpc(), 0);
					NMSAnimation.animation(p.getPlayer(), p.getRedNPC().get(0).getNpc(), 1);
					p.getRedNPC().get(0).updateLifeBar(p.getPlayer(), op);
				} else {
					if (p.getRedNPC().size() <= index) continue;
					NMSAnimation.animation(p.getPlayer(), p.getRedNPC().get(index).getNpc(), 0);
					NMSAnimation.animation(p.getPlayer(), p.getBlueNPC().get(0).getNpc(), 1);
					p.getBlueNPC().get(0).updateLifeBar(p.getPlayer(), op);
				}
			}
			return;
		}
		
		if (this.getOptions().combatDelay <= 0 && canFightBase(index)) {
			this.getOptions().combatDelay = this.getOptions().maxCombatDelay;
			Bukkit.getWorld("world").playSound(this.position, Sound.SHOOT_ARROW, 1.0f, 1.0f);
			
			if (this.owner.getPlayerID() == 0) {
				Core.redBase.setLife((int) (Core.redBase.getLife() - this.options.damage));
				Bukkit.getWorld("world").playSound(Core.redBase.getLocation(), Sound.ZOMBIE_WOODBREAK, 1.0f, 1.0f);
			} else {
				Core.blueBase.setLife((int) (Core.blueBase.getLife() - this.options.damage));
				Bukkit.getWorld("world").playSound(Core.blueBase.getLocation(), Sound.ZOMBIE_WOODBREAK, 1.0f, 1.0f);
			}
			
			for (AowPlayer p : Core.aowplayers) {
				if (this.owner.getPlayerID() == 0) {
					if (p.getBlueNPC().size() <= index) continue;
					Core.redBase.updateLifeBar(p, p.getRedBaseLife(), false);
					NMSAnimation.animation(p.getPlayer(), p.getBlueNPC().get(index).getNpc(), 0);
				} else {
					if (p.getRedNPC().size() <= index) continue;
					Core.blueBase.updateLifeBar(p, p.getBlueBaseLife(), true);
					NMSAnimation.animation(p.getPlayer(), p.getRedNPC().get(index).getNpc(), 0);
				}
			}
		}
	}
}
