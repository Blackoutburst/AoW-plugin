package com.blackout.aow.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;

import com.blackout.aow.core.Ages;
import com.blackout.aow.core.AowPlayer;
import com.blackout.aow.core.Core;
import com.blackout.aow.warrior.WarriorManager;

import net.minecraft.server.v1_8_R3.EntityFireworks;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityMetadata;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityStatus;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntity;
import net.minecraft.server.v1_8_R3.PlayerConnection;

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
		
		ItemStack item;
		ItemMeta meta;
		
		if (p.getUltimeDelay() > 0) {
			item = new ItemStack(Material.INK_SACK, p.getUltimeDelay(), (short) 8);
			meta = item.getItemMeta();
			meta.setDisplayName("§7Ultimate skill");
			item.setItemMeta(meta);
			player.getInventory().setItem(0, item);
		} else {
			item = new ItemStack(Material.BLAZE_POWDER);
			meta = item.getItemMeta();
			meta.setDisplayName("§6Ultimate skill");
			item.setItemMeta(meta);
			player.getInventory().setItem(0, item);
		}
		
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
	
	public static void endingFireworks(Location location, Color color) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			PlayerConnection connection = ((CraftPlayer) p).getHandle().playerConnection;
			ItemStack stackFirework = new ItemStack(Material.FIREWORK);
			FireworkMeta fireworkMeta = (FireworkMeta) stackFirework.getItemMeta();
			FireworkEffect effect = FireworkEffect.builder().flicker(false).withColor(color).with(Type.BALL_LARGE).build();
			fireworkMeta.addEffect(effect);
			fireworkMeta.setPower(2);
			stackFirework.setItemMeta(fireworkMeta);
			EntityFireworks firework = new EntityFireworks(((CraftWorld) location.getWorld()).getHandle(), location.getX(), location.getY(), location.getZ(), CraftItemStack.asNMSCopy(stackFirework));
			firework.expectedLifespan = 0;
			connection.sendPacket(new PacketPlayOutSpawnEntity(firework, 76));
			connection.sendPacket(new PacketPlayOutEntityMetadata(firework.getId(), firework.getDataWatcher(), true));
			connection.sendPacket(new PacketPlayOutEntityStatus(firework, (byte) 17));
			connection.sendPacket(new PacketPlayOutEntityDestroy(firework.getId()));
		}
	}
	
	@SuppressWarnings("deprecation")
	public static void loadBase(String fileName) {
		YamlConfiguration file = YamlConfiguration.loadConfiguration(new File("plugins/AOW/"+fileName+".yml"));
		Set<String> blocks = file.getConfigurationSection("blocks").getKeys(false);
		List<BaseBlock> bb = new ArrayList<BaseBlock>();
		
		for (String index : blocks) {
			Material material = Material.getMaterial(file.getString("blocks."+index+".type"));
			int data = file.getInt("blocks."+index+".data");
			int x = file.getInt("blocks."+index+".x");
			int y = file.getInt("blocks."+index+".y");
			int z = file.getInt("blocks."+index+".z");
			
			bb.add(new BaseBlock(material, data, x, y, z));
		}
		
		for (BaseBlock b : bb) {
			Block block = Bukkit.getWorld("world").getBlockAt(new Location(Bukkit.getWorld("world"), b.getX(), b.getY(), b.getZ()));
			block.setType(b.getType());
			block.setData((byte) b.getData());
		}
	}
}
