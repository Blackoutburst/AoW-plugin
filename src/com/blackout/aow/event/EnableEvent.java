package com.blackout.aow.event;

import com.blackout.aow.core.WarriorAction;
import com.blackout.npcapi.utils.SkinLoader;

public class EnableEvent {

	/**
	 * Called when the plugin is loaded,
	 * this pre load warrior skins and start important scheduler
	 */
	public void execute() {
		SkinLoader.loadSkinFromUUID(0, "b82b0a57c0f0435fa0bb63e9475497e3");
		SkinLoader.loadSkinFromUUID(1, "f500906232e54acdbd97e6c8fc523131");
		SkinLoader.loadSkinFromUUID(2, "db2a372876b24b4b9d9d4fa70fa6f2fc");
		
		SkinLoader.loadSkinFromUUID(3, "d09accabcb4c4549bebeab3612bff0b9");
		SkinLoader.loadSkinFromUUID(4, "7305938e473743f0bb093bfcc8f9fc5e");
		SkinLoader.loadSkinFromUUID(5, "369f11812c7d4a7ab72f913df938ee3e");
		WarriorAction.moveAction();
		WarriorAction.fightAction();
	}
	
}
