package com.blackout.aow.events;

import com.blackout.aow.core.Core;
import com.blackout.aow.warrior.WarriorManager;
import com.blackout.npcapi.utils.SkinLoader;

public class EnableEvent {

	public void execute() {
		SkinLoader.loadSkinFromUUID(0, "db2a372876b24b4b9d9d4fa70fa6f2fc"); //Cave man
		SkinLoader.loadSkinFromUUID(1, "db2a372876b24b4b9d9d4fa70fa6f2fc"); //Slingshot
		SkinLoader.loadSkinFromUUID(2, "8a64d406893744d8810ec5410ac47b8c"); //Steve
		
		SkinLoader.loadSkinFromUUID(3, "d09accabcb4c4549bebeab3612bff0b9"); //Swordsmand
		SkinLoader.loadSkinFromUUID(4, "8ff7507bc9394abd9b9791bc437c0568"); //Archer
		SkinLoader.loadSkinFromUUID(5, "369f11812c7d4a7ab72f913df938ee3e"); //Knight
		
		SkinLoader.loadSkinFromUUID(6, "522093162f2049049efea97703ba0f8f"); //Musketeer
		SkinLoader.loadSkinFromUUID(7, "522093162f2049049efea97703ba0f8f"); //Rifleman
		SkinLoader.loadSkinFromUUID(8, "522093162f2049049efea97703ba0f8f"); //Cannoneer
		
		SkinLoader.loadSkinFromUUID(9, "7d277230f6ac4792b5793bd958eb1409"); //Soldier
		SkinLoader.loadSkinFromUUID(10, "afa84fb38b5840c785cc10a0a19d424d"); //Gunner
		SkinLoader.loadSkinFromUUID(11, "0468cdfb194148fc8e9b821475bffbbc"); //Bomber
		
		SkinLoader.loadSkinFromUUID(12, "63dc6bf1b5af4a3a8d59456d74bc99bb"); //Space soldier
		SkinLoader.loadSkinFromUUID(13, "a2530b4f6ce54c2da8edfdd1f7af4483"); //Space gunner
		SkinLoader.loadSkinFromUUID(14, "7af7cf026d1d419cbbceef32944f812e"); //Super soldier
		
		WarriorManager.doActions();
		Core.update();
	}
}
