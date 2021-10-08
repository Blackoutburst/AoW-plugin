package com.blackout.aow.events;

import com.blackout.npcapi.utils.SkinLoader;

public class EnableEvent {

	public void execute() {
		SkinLoader.loadSkinFromUUID(0, "db2a372876b24b4b9d9d4fa70fa6f2fc");
		SkinLoader.loadSkinFromUUID(1, "db2a372876b24b4b9d9d4fa70fa6f2fc");
		SkinLoader.loadSkinFromUUID(2, "b82b0a57c0f0435fa0bb63e9475497e3");
		
		SkinLoader.loadSkinFromUUID(3, "d09accabcb4c4549bebeab3612bff0b9");
		SkinLoader.loadSkinFromUUID(4, "d09accabcb4c4549bebeab3612bff0b9");
		SkinLoader.loadSkinFromUUID(5, "369f11812c7d4a7ab72f913df938ee3e");
	}
}
