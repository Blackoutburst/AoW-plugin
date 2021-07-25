package com.blackout.aow.event;

import com.blackout.aow.core.WarriorAction;
import com.blackout.npcapi.utils.SkinLoader;

public class EnableEvent {

	public void execute() {
		SkinLoader.loadSkinFromUUID(0, "d09accabcb4c4549bebeab3612bff0b9");
		SkinLoader.loadSkinFromUUID(1, "7305938e473743f0bb093bfcc8f9fc5e");
		SkinLoader.loadSkinFromUUID(2, "369f11812c7d4a7ab72f913df938ee3e");
		WarriorAction.moveNPC();
		WarriorAction.NPCFight();
	}
	
}
