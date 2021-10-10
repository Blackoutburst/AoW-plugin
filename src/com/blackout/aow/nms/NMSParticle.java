package com.blackout.aow.nms;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import net.minecraft.server.v1_8_R3.PlayerConnection;

public class NMSParticle {

	// Not in reflection cause my brain smh
	public static void spawnParticle(Player player, EnumParticle particle, float x, float y, float z) {
		PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
		connection.sendPacket(new PacketPlayOutWorldParticles(particle, true, x, y, z, 0, 0, 0, 0, 1, null));
	}
	
}
