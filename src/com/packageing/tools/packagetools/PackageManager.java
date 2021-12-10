package com.packageing.tools.packagetools;

import org.bukkit.entity.Player;

import com.packageing.tools.packagetools.entitys.ArmorStand;

import net.minecraft.network.protocol.game.*;
import org.bukkit.craftbukkit.v1_18_R1.entity.CraftPlayer;


public class PackageManager {

	public static void SendSpawnPackage(ArmorStand stand, Player p) {
		PacketPlayOutSpawnEntityLiving spawn = new PacketPlayOutSpawnEntityLiving(stand.getEntity());
//        PacketPlayOutEntityEquipment equip = new PacketPlayOutEntityEquipment(stand.getId(), EnumItemSlot.HEAD, CraftItemStack.asNMSCopy(new ItemStack(m)));
		PacketPlayOutEntityEquipment equip = new PacketPlayOutEntityEquipment(stand.getEntityID(), stand.getEquipmentList());//net.minecraft.world.entity.decoration.EntityArmorStand
		PacketPlayOutEntityMetadata metadata = new PacketPlayOutEntityMetadata(stand.getEntityID(), stand.getEntity().ai(), true);
        ((CraftPlayer)p).getHandle().b.a(spawn);
        ((CraftPlayer)p).getHandle().b.a(equip);
        ((CraftPlayer)p).getHandle().b.a(metadata);
	}
	
	public static void SendDespawnPackage(ArmorStand stand, Player p) {
		PacketPlayOutEntityDestroy destroy = new PacketPlayOutEntityDestroy(stand.getEntityID());
		((CraftPlayer)p).getHandle().b.a(destroy);
	}
	
	public static void SendDespawnPackage(int standID, Player p) {
		//net.minecraft.server.network.PlayerConnection
		PacketPlayOutEntityDestroy destroy = new PacketPlayOutEntityDestroy(standID);
		((CraftPlayer)p).getHandle().b.a(destroy);
	}
	
	public static void SendUpdate(ArmorStand stand, Player p) {
//      PacketPlayOutEntityEquipment equip = new PacketPlayOutEntityEquipment(stand.getId(), EnumItemSlot.HEAD, CraftItemStack.asNMSCopy(new ItemStack(m)));
		PacketPlayOutEntityEquipment equip = new PacketPlayOutEntityEquipment(stand.getEntityID(), stand.getEquipmentList());
		PacketPlayOutEntityMetadata metadata = new PacketPlayOutEntityMetadata(stand.getEntityID(), stand.getEntity().ai(), true);
        ((CraftPlayer)p).getHandle().b.a(equip);
		((CraftPlayer)p).getHandle().b.a(metadata);
	}
	
	public static void SendTeleport(ArmorStand stand, Player p) {
		PacketPlayOutEntityMetadata metadata = new PacketPlayOutEntityMetadata(stand.getEntityID(), stand.getEntity().ai(), true);
        PacketPlayOutEntityTeleport telport = new PacketPlayOutEntityTeleport(stand.getEntity());
		((CraftPlayer)p).getHandle().b.a(metadata);
		((CraftPlayer)p).getHandle().b.a(telport);
	}
	
//	public static void SendBlockChangePackage(Block block, Material m, Player p) {
//		IBlockData blockdata = blockData(m);
//		PacketPlayOutBlockChange changeEvent = new PacketPlayOutBlockChange(blockPosition(block), blockdata);
//		((CraftPlayer)p).getHandle().playerConnection.sendPacket(changeEvent);
//	}
	
//	private static IBlockData blockData(Block b) {
//	    net.minecraft.server.v1_16_R3.World nmsWorld = ((org.bukkit.craftbukkit.v1_16_R3.CraftWorld)b.getWorld()).getHandle().getMinecraftWorld();
//	    BlockPosition bp = new BlockPosition(b.getX(), b.getY(), b.getZ());
//	    return nmsWorld.getType(bp);
//	}
	
//	private static IBlockData blockData(Material m) {
//	    net.minecraft.server.v1_16_R3.Block nmsBlock = CraftMagicNumbers.getBlock(m);
//	    return nmsBlock.getBlockData();
//	}
//	
//	private static BlockPosition blockPosition(Block b) {
//	    BlockPosition bp = new BlockPosition(b.getX(), b.getY(), b.getZ());
//	    return bp;
//	}
	
}