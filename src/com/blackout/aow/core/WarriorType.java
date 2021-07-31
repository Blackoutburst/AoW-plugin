package com.blackout.aow.core;

import com.blackout.holoapi.core.Holo;
import com.blackout.npcapi.core.NPC;

public class WarriorType {
	
	public enum Type {
		Clubman,
		Slingshot,
		Spearman,
		Knight,
		Archer,
		Berserk
	}
	
	//Prehistoric age
	public static Warrior clubman(NPC warrior, Holo lifeBar) {
		return new Warrior(warrior, lifeBar, 100, 20, 15, 5, 20, 1.5f, WarriorType.Type.Clubman, 280);
	}
	
	public static Warrior slingshot(NPC warrior, Holo lifeBar) {
		return new Warrior(warrior, lifeBar, 60, 15, 20, 10, 25, 5.5f, WarriorType.Type.Slingshot, 420);
	}
	
	public static Warrior spearman(NPC warrior, Holo lifeBar) {
		return new Warrior(warrior, lifeBar, 400, 50, 200, 20, 150, 2.0f, WarriorType.Type.Spearman, 273);
	}
	
	//Medieval age
	public static Warrior knight(NPC warrior, Holo lifeBar) {
		return new Warrior(warrior, lifeBar, 450, 50, 250, 40, 260, 2.0f, WarriorType.Type.Knight, 267);
	}
	
	public static Warrior archer(NPC warrior, Holo lifeBar) {
		return new Warrior(warrior, lifeBar, 300, 40, 300, 55, 280, 6.5f, WarriorType.Type.Archer, 261);
	}
	
	public static Warrior berserk(NPC warrior, Holo lifeBar) {
		return new Warrior(warrior, lifeBar, 700, 60, 450, 70, 550, 2.0f, WarriorType.Type.Berserk, 258);
	}
}
