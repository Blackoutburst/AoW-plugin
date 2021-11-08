package com.blackout.aow.warrior;

import org.bukkit.Location;

import com.blackout.aow.core.AowPlayer;
import com.blackout.aow.core.Core;
import com.blackout.aow.nms.NMSEntityMove;

public abstract class WarriorLogical {
	
	protected AowPlayer owner;
	protected WarriorOptions options;
	protected Location position;
	protected boolean dead;
	
	public WarriorLogical(AowPlayer owner, WarriorOptions options, Location position) {
		this.owner = owner;
		this.options = options;
		this.position = position;
		this.dead = false;
	}
	
	protected abstract void update(int index);
	protected abstract void fight(int index);

	protected void walk(int index) {
		if (this.owner.getPlayerID() == 0) { //Toward right
			if (this.position.getZ() < Core.redBase.getLocation().getZ() && canWalk(index)) {
				this.position.setZ((this.position.getZ() + (5.0f / 32.0f)));
				for (AowPlayer p : Core.aowplayers) {
					if (p.getBlueNPC().size() <= index) continue;
					NMSEntityMove.move(p.getPlayer(), p.getBlueNPC().get(index).getNpc().getEntityId(), (byte)(0), (byte)(0), (byte)(5));
				}
			}
		} else { // Toward left
			if (this.position.getZ() > Core.blueBase.getLocation().getZ() && canWalk(index)) {
				position.setZ(position.getZ() - (5.0f / 32.0f));
				for (AowPlayer p : Core.aowplayers) {
					if (p.getRedNPC().size() <= index) continue;
					NMSEntityMove.move(p.getPlayer(), p.getRedNPC().get(index).getNpc().getEntityId(), (byte)(0), (byte)(0), (byte)(-5));
				}
			}
		}
	}
	
	private boolean canWalk(int index) {
		if (this.dead) return (false);
		if (this.owner.getPlayerID() == 0) {//Toward right
			double frontZ = 0;
			double myZ = this.position.getZ();
			
			if (Core.redWarrior.size() > 0) {
				frontZ = Core.redWarrior.get(0).getPosition().getZ();
				if (frontZ - myZ < 1.5f) {
					return (false);
				}
			}
			
			if (index == 0) return (true);
			frontZ = Core.blueWarrior.get(index - 1).getPosition().getZ();
			
			return !(frontZ - myZ < 1.5f);
		} else { // Toward left
			double frontZ = 0;
			double myZ = this.position.getZ();
			
			if (Core.blueWarrior.size() > 0) {
				frontZ = Core.blueWarrior.get(0).getPosition().getZ();
				if (myZ - frontZ < 1.5f) {
					return (false);
				}
			}
			
			if (index == 0) return (true);
			frontZ = Core.redWarrior.get(index - 1).getPosition().getZ();
		
			return !(myZ - frontZ < 1.5f);
		}
	}
	
	protected boolean canFight(int index) {
		if (this.owner.getPlayerID() == 0) {//Toward right
			double prevZ = 0;
			double myZ = this.position.getZ();
			
			if (Core.redWarrior.size() > 0) {
				prevZ = Core.redWarrior.get(0).getPosition().getZ();
				if (Math.abs(prevZ - myZ) < this.options.range) {
					return (true);
				}
			}
		} else { // Toward left
			double prevZ = 0;
			double myZ = this.position.getZ();
			
			if (Core.blueWarrior.size() > 0) {
				prevZ = Core.blueWarrior.get(0).getPosition().getZ();
				if (Math.abs(prevZ - myZ) < this.options.range) {
					return (true);
				}
			}
		}
		return (false);
	}
	
	protected boolean canFightBase(int index) {
		if (this.owner.getPlayerID() == 0) {//Toward right
			double baseZ = Core.redBase.getLocation().getZ();
			double myZ = this.position.getZ();
			
			if (Core.redWarrior.size() == 0) {
				if (Math.abs(baseZ - myZ) < this.options.range) {
					return (true);
				}
			}
		} else { // Toward left
			double baseZ = Core.blueBase.getLocation().getZ();
			double myZ = this.position.getZ();
			
			if (Core.blueWarrior.size() == 0) {
				if (Math.abs(baseZ - myZ) < this.options.range) {
					return (true);
				}
			}
		}
		return (false);
	}
	
	protected void die(int index) {
		if ((int) (this.options.health) <= 0 && !this.dead) {
			this.dead = true;
			if (this.owner.getPlayerID() == 0) {
				Core.player2.setXp((int) (Core.player2.getXp() + this.options.xpDrop));
				Core.player2.setGold((int) (Core.player2.getGold() + this.options.goldDrop));
				Core.player2.getPlayer().sendMessage("§aYou received §6"+(int) (this.options.goldDrop)+" gold and §b"+(int) (this.options.xpDrop)+" xp§a!");
			} else if (this.owner.getPlayerID() == 1) {
				Core.player1.setXp((int) (Core.player1.getXp() + this.options.xpDrop));
				Core.player1.setGold((int) (Core.player1.getGold() + this.options.goldDrop));
				Core.player1.getPlayer().sendMessage("§aYou received §6"+(int) (this.options.goldDrop)+" gold and §b"+(int) (this.options.xpDrop)+" xp§a!");
			}
		}
	}
	
	public AowPlayer getOwner() {
		return owner;
	}

	public void setOwner(AowPlayer owner) {
		this.owner = owner;
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
