package com.blackout.aow.warrior;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.blackout.aow.nms.NMSAttachEntity;
import com.blackout.holoapi.core.Holo;
import com.blackout.holoapi.utils.HoloManager;
import com.blackout.npcapi.core.NPC;

public class WarriorVisual {

	protected NPC npc;
	protected Holo lifeBar;
	
	public WarriorVisual(NPC npc, Holo lifeBar) {
		this.npc = npc;
		this.lifeBar = lifeBar;
	}
	
	public void updateLifeBar(Player p, WarriorLogical w) {
		int lifePercent = (int) (w.getOptions().getHealth() * 100 / w.getOptions().getMaxHealth());
		HoloManager.deleteHolo(p, this.lifeBar);
		
		Location loc = w.getPosition().clone();
		loc.setY(loc.getY() + 1.38f);
		
		Holo life = new Holo(UUID.randomUUID(), getLifeBarString(lifePercent))
		        .setLocation(loc);
		HoloManager.spawnHolo(life, p);
		this.lifeBar = life;
		NMSAttachEntity.attach(p, life, this.npc);
	}
	
	private String getLifeBarString(int lifePercent) {
		if (lifePercent <= 10) return "§7[§8||||||||||§7]";
		if (lifePercent <= 20) return "§7[§4|§8|||||||||§7]";
		if (lifePercent <= 30) return "§7[§c||§8||||||||§7]";
		if (lifePercent <= 40) return "§7[§c|||§8|||||||§7]";
		if (lifePercent <= 50) return "§7[§6||||§8||||||§7]";
		if (lifePercent <= 60) return "§7[§6|||||§8|||||§7]";
		if (lifePercent <= 70) return "§7[§e||||||§8||||§7]";
		if (lifePercent <= 80) return "§7[§e|||||||§8|||§7]";
		if (lifePercent <= 90) return "§7[§a||||||||§8||§7]";
		if (lifePercent < 100) return "§7[§a|||||||||§8|§7]";
		return "§7[§a||||||||||§7]";
	}

	public NPC getNpc() {
		return npc;
	}

	public void setNpc(NPC npc) {
		this.npc = npc;
	}

	public Holo getLifeBar() {
		return lifeBar;
	}

	public void setLifeBar(Holo lifeBar) {
		this.lifeBar = lifeBar;
	}
	
}
