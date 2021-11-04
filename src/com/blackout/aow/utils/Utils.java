package com.blackout.aow.utils;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.blackout.aow.core.Ages;
import com.blackout.aow.core.AowPlayer;
import com.blackout.aow.core.Core;
import com.blackout.aow.warrior.WarriorManager;

public class Utils {
	
	private static Material[] items = new Material[] {
			Material.STICK,
			Material.LEASH,
			Material.WOOD_SWORD,
			Material.STONE_SWORD,
			Material.BOW,
			Material.IRON_SWORD,
			Material.GOLD_SWORD,
			Material.STONE_HOE,
			Material.GOLD_BARDING,
			Material.FEATHER,
			Material.IRON_BARDING,
			Material.TNT,
			Material.DIAMOND_SWORD,
			Material.IRON_HOE,
			Material.BLAZE_ROD};
	
	
	public static void giveItems(AowPlayer p) {
		Player player = p.getPlayer();
		int unitIndex = p.getAge().ordinal() * 3;
		
		ItemStack item = new ItemStack(Material.BLAZE_POWDER);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("§6Ultimate skill");
		item.setItemMeta(meta);
		player.getInventory().setItem(0, item);
		
		item = new ItemStack(items[unitIndex]);
		meta = item.getItemMeta();
		meta.setDisplayName("§aSpawn a §6"+WarriorManager.names[unitIndex]);
		meta.spigot().setUnbreakable(true);
		item.setItemMeta(meta);
		player.getInventory().setItem(3, item);
		
		item = new ItemStack(items[unitIndex + 1]);
		meta = item.getItemMeta();
		meta.setDisplayName("§aSpawn a §6"+WarriorManager.names[unitIndex + 1]);
		meta.spigot().setUnbreakable(true);
		item.setItemMeta(meta);
		player.getInventory().setItem(4, item);
		
		item = new ItemStack(items[unitIndex + 2]);
		meta = item.getItemMeta();
		meta.setDisplayName("§aSpawn a §6"+WarriorManager.names[unitIndex + 2]);
		meta.spigot().setUnbreakable(true);
		item.setItemMeta(meta);
		player.getInventory().setItem(5, item);
		
		if (p.getAge() != Ages.FUTURISTIC) {
			item = new ItemStack(Material.NETHER_STAR);
			meta = item.getItemMeta();
			meta.setDisplayName("§bEvolve to the next age");
			item.setItemMeta(meta);
		} else {
			item = new ItemStack(Material.AIR);
		}
		player.getInventory().setItem(8, item);
	}
	
	public static AowPlayer getGamePlayer(Player p) {
		if (Core.player1.getPlayer() == p) return Core.player1;
		if (Core.player2.getPlayer() == p) return Core.player2;
		return null;
	}
}
