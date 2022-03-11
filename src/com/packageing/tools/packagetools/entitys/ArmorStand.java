package com.packageing.tools.packagetools.entitys;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_18_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_18_R2.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import com.mojang.datafixers.util.Pair;

import net.minecraft.core.Vector3f;
import net.minecraft.network.chat.ChatComponentText;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumItemSlot;
import net.minecraft.world.entity.decoration.EntityArmorStand;
import net.minecraft.world.level.World;

public class ArmorStand extends PackageEntity<EntityArmorStand> {

	private float bodyRotation = 0f;
	private double locationX = 0, locationY = 0, locationZ = 0;
	private float headX = 0, headY = 0, headZ = 0;
	private String customName = "";
	private ItemStack headMaterial = null;
	private ItemStack mainHand = null;
	private ItemStack offHand = null;
	private ItemStack bodyArmor = null;
	private ItemStack legArmor = null;
	private ItemStack feetArmor = null;
	private final int entityID;
	
	public ArmorStand(EntityArmorStand entity) {
		super(entity);
		this.entityID = entity.ae();
	}
	
	public static ArmorStand CreateArmorStand(Location location,float headRotationX, float headRotationY, float headRotationZ, float rotBody) {
		World s = ((CraftWorld)location.getWorld()).getHandle();
		EntityArmorStand stand = new EntityArmorStand(EntityTypes.c ,s);
        ArmorStand astand = new ArmorStand(stand);
        astand.bodyRotation = rotBody;
        astand.setLocation(location.getX(), location.getY(), location.getZ());
        astand.setCustomNameVisible(false);
        astand.setHeadRotation(headRotationX, headRotationY, headRotationZ);
        astand.setSmall(false);
        astand.setInvisible(true);
        return astand;
	}
	
	public List<Pair<EnumItemSlot, net.minecraft.world.item.ItemStack>> getEquipmentList() {
		List<Pair<EnumItemSlot, net.minecraft.world.item.ItemStack>> output = new ArrayList<>();
		if(headMaterial != null) output.add(new Pair<>(EnumItemSlot.f, CraftItemStack.asNMSCopy(headMaterial)));
		if(mainHand     != null) output.add(new Pair<>(EnumItemSlot.b, CraftItemStack.asNMSCopy(mainHand)));
		if(offHand      != null) output.add(new Pair<>(EnumItemSlot.c, CraftItemStack.asNMSCopy(offHand)));
		if(bodyArmor    != null) output.add(new Pair<>(EnumItemSlot.d, CraftItemStack.asNMSCopy(bodyArmor)));
		if(legArmor     != null) output.add(new Pair<>(EnumItemSlot.e, CraftItemStack.asNMSCopy(legArmor)));
		if(feetArmor    != null) output.add(new Pair<>(EnumItemSlot.a, CraftItemStack.asNMSCopy(feetArmor)));
		
		if(output.isEmpty())
			output.add(new Pair<>(EnumItemSlot.f, CraftItemStack.asNMSCopy(new ItemStack(Material.AIR))));
		return output;
	}
	
	public void setHeadRotation(float x, float y, float z) {
		this.headX = x;
		this.headY = y;
		this.headZ = z;
		this.getEntity().a(new Vector3f(x,y,z));
	}
	
	public void setLocation(double x, double y, double z) {
		this.locationX = x;
		this.locationY = y;
		this.locationZ = z;
		this.getEntity().a(x, y, z, this.bodyRotation, 0);
	}
	
	public void setCustomName(String text) {
		ChatComponentText standName = new ChatComponentText(text);
        this.getEntity().a(standName);
	}
	
	public void setCustomNameVisible(boolean visible) {
		this.getEntity().n(visible);
	}

	public double getLocationX() {
		return locationX;
	}

	public double getLocationY() {
		return locationY;
	}

	public double getLocationZ() {
		return locationZ;
	}
	
	public Vector getLocationVector() {
		return new Vector(locationX, locationY, locationZ);
	}

	public boolean isSmall() {
		return this.getEntity().n();
	}

	public void setSmall(boolean isSmall) {
		this.getEntity().a(isSmall);
	}

	public boolean hasBasePlate() {
		return this.getEntity().r();
	}

	public void setHasBasePlate(boolean hasBasePlate) {
		this.getEntity().s(hasBasePlate);
	}

	public boolean hasGravity() {
		return this.getEntity().aM();
	}

	public void setHasGravity(boolean hasGravity) {
		this.getEntity().e(hasGravity);
	}

	public boolean isInvisible() {
		return this.getEntity().bU();
	}

	public void setInvisible(boolean isInvisible) {
		this.getEntity().j(isInvisible);
	}

	public float getHeadX() {
		return headX;
	}

	public float getHeadY() {
		return headY;
	}

	public float getHeadZ() {
		return headZ;
	}
	
	public Vector getHeadVector() {
		return new Vector(headX, headY, headZ);
	}
	
	public Vector3f getHeadVector3f() {
		return new Vector3f(headX, headY, headZ);
	}

	public String getCustomName() {
		return customName;
	}

	public float getBodyRotation() {
		return bodyRotation;
	}

	public void setBodyRotation(float bodyRotation) {
		this.bodyRotation = bodyRotation;
		this.setLocation(this.locationX, this.locationY, this.locationZ);
	}

	public ItemStack getHeadMaterial() {
		return headMaterial;
	}

	public void setHeadMaterial(ItemStack headMaterial) {
		this.headMaterial = headMaterial;
	}

	public ItemStack getMainHand() {
		return mainHand;
	}

	public void setMainHand(ItemStack mainHand) {
		this.mainHand = mainHand;
	}

	public ItemStack getOffHand() {
		return offHand;
	}

	public void setOffHand(ItemStack offHand) {
		this.offHand = offHand;
	}

	public ItemStack getBodyArmor() {
		return bodyArmor;
	}

	public void setBodyArmor(ItemStack bodyArmor) {
		this.bodyArmor = bodyArmor;
	}

	public ItemStack getLegArmor() {
		return legArmor;
	}

	public void setLegArmor(ItemStack legArmor) {
		this.legArmor = legArmor;
	}

	public ItemStack getFeetArmor() {
		return feetArmor;
	}

	public void setFeetArmor(ItemStack feetArmor) {
		this.feetArmor = feetArmor;
	}

	public int getEntityID() {
		return entityID;
	}

}
