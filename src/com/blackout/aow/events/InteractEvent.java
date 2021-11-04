package com.blackout.aow.events;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

import com.blackout.aow.warrior.WarriorManager;

public class InteractEvent {

	private void clickItems(Player player) {
		switch (player.getInventory().getHeldItemSlot()) {
			case 0: break;
			case 3: WarriorManager.createNewWarrior("shop0", player); break;	
			case 4: WarriorManager.createNewWarrior("shop1", player); break;
			case 5: WarriorManager.createNewWarrior("shop2", player); break;
			case 8: WarriorManager.createNewWarrior("shop3", player); break;
			default: return;
		}
	}
	
	public void execute(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		
		clickItems(player);
	}
}
