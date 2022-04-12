package com.packageing.tools.packagetools;

import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.packageing.tools.packagetools.entitys.PackageArmorStand;

public class PackageManager {

	public static void SendSpawnPackage(Player p, PackageArmorStand stand) {
		sendHandled(p, stand.getSpawnPackage(), stand.getEquipPackage(), stand.getMetadataPackage());
	}
	
	public static void SendDespawnPackage(Player p, Integer... standIDs) {
		PacketContainer pack = new PacketContainer(PacketType.Play.Server.ENTITY_DESTROY);
		pack.getIntLists().write(0, List.of(standIDs));
		sendHandled(p, pack);
	}
	
	public static void SendEquipment(PackageArmorStand stand, Player p) {
		sendHandled(p, stand.getEquipPackage());
	}

	public static void SendMetadata(Player p, PackageArmorStand stand){
		sendHandled(p, stand.getMetadataPackage());
	}
	
	public static void SendTeleport(Player p, PackageArmorStand stand) {
		PacketContainer packet = new PacketContainer(PacketType.Play.Server.ENTITY_TELEPORT);
		packet.getIntegers().write(0, stand.getEntityID());
		packet.getDoubles().write(0, stand.getLocation().getX());
		packet.getDoubles().write(1, stand.getLocation().getY());
		packet.getDoubles().write(2, stand.getLocation().getZ());
		sendHandled(p, packet);
	}
	
	private static void sendHandled(Player p, PacketContainer c){
		try {
			if(c != null) ProtocolLibrary.getProtocolManager().sendServerPacket(p, c);
		} catch (IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	private static void sendHandled(Player p, PacketContainer... c){
		try {
			for(int i = 0; i < c.length; ++i){
				if(c[i] != null) ProtocolLibrary.getProtocolManager().sendServerPacket(p, c[i]);
			}
		} catch (IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

}