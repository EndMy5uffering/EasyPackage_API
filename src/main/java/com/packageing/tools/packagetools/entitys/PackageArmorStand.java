package com.packageing.tools.packagetools.entitys;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.Pair;
import com.comphenix.protocol.wrappers.Vector3F;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.comphenix.protocol.wrappers.EnumWrappers.ItemSlot;
import com.comphenix.protocol.wrappers.WrappedDataWatcher.Serializer;

import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class PackageArmorStand extends PackageEntity{

	private Vector bodyRotation = new Vector(0, 0, 0);
	private Vector location = new Vector(0,0,0);
	private Vector headRotation = new Vector(0,0,0);
	private Vector leftArmRotation = new Vector(0,0,0);
	private Vector rightArmRotation = new Vector(0,0,0);
	private Vector leftLegRotation = new Vector(0,0,0);
	private Vector rightLegRotation = new Vector(0,0,0);
	private String customName = "";
	private ItemStack headMaterial = null;
	private ItemStack mainHand = null;
	private ItemStack offHand = null;
	private ItemStack bodyMaterial = null;
	private ItemStack legMaterial = null;
	private ItemStack feetMaterial = null;
	private boolean customNameVisible = false;
	private boolean basePlateHidden = true;
	private boolean isMarker = true;
	private boolean isSmall = true;
	private boolean hasArms = true;
	private final int entityID;
	private UUID uuid = null;
	private int pitch = 0;
	private int yaw = 0;

	public PackageArmorStand(int id) {
        this.entityID = id;
		this.uuid = UUID.randomUUID();
	}

	public static Optional<PackageArmorStand> getPackageArmorStand(){
		try {
			return Optional.of(new PackageArmorStand(PackageEntity.getNewEntityID()));
		} catch (ClassNotFoundException | IllegalArgumentException | IllegalAccessException | PackageingException e) {
			e.printStackTrace();
			return Optional.empty();
		}
	}

	public PacketContainer getSpawnPackage(){
		PacketContainer packet = new PacketContainer(PacketType.Play.Server.SPAWN_ENTITY_LIVING);
		packet.getUUIDs().write(0, this.uuid);
        packet.getIntegers().write(0, this.entityID);
        packet.getIntegers().write(1, 1);
        packet.getDoubles().write(0, this.location.getX());
        packet.getDoubles().write(1, this.location.getY());
        packet.getDoubles().write(2, this.location.getZ());

        packet.getIntegers().write(2, yaw);
		packet.getIntegers().write(3, pitch);

		return packet;
	}

	public PacketContainer getMetadataPackage(){
		PacketContainer c = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.ENTITY_METADATA);
		c.getIntegers().write(0, this.entityID);

		WrappedDataWatcher metadata = new WrappedDataWatcher();
		
		metadata.setObject(getWatcherObject(0, WrappedDataWatcher.Registry.get(Byte.class)), (byte)0x20);

		WrappedChatComponent component = WrappedChatComponent.fromText(this.customName);
		Optional<?> opt = Optional.of(component.getHandle());
		metadata.setObject(getWatcherObject(2, WrappedDataWatcher.Registry.getChatComponentSerializer(true)), opt);
		metadata.setObject(getWatcherObject(3, WrappedDataWatcher.Registry.get(Boolean.class)), this.customNameVisible);
		int mask = 0;
		if(isSmall) mask |= 0x01;
		if(hasArms) mask |= 0x04;
		if(basePlateHidden) mask |= 0x08;
		if(isMarker) mask |= 0x10;
		metadata.setObject(getWatcherObject(15, WrappedDataWatcher.Registry.get(Byte.class)), (byte) (mask));
		WrappedDataWatcher.Serializer serializer = WrappedDataWatcher.Registry.getVectorSerializer();
		metadata.setObject(16, serializer, toV3f(this.getHeadRotation()));
		metadata.setObject(17, serializer, toV3f(this.getBodyRotation()));
		metadata.setObject(18, serializer, toV3f(this.getLeftArmRotation()));
		metadata.setObject(19, serializer, toV3f(this.getRightArmRotation()));
		metadata.setObject(20, serializer, toV3f(this.getLeftLegRotation()));
		metadata.setObject(21, serializer, toV3f(this.getRightLegRotation()));
		c.getWatchableCollectionModifier().write(0, metadata.getWatchableObjects());
		return c;
	}

	private Vector3F toV3f(Vector v){
		return new Vector3F((float)v.getX(), (float)v.getY(), (float)v.getZ());
	}

	private WrappedDataWatcher.WrappedDataWatcherObject getWatcherObject(int index, Serializer s){
		return new WrappedDataWatcher.WrappedDataWatcherObject(index, s);
	}

	public PacketContainer getEquipPackage(){
		PacketContainer c = new PacketContainer(PacketType.Play.Server.ENTITY_EQUIPMENT);
        c.getIntegers().write(0, this.entityID);
		List<Pair<ItemSlot, ItemStack>> slots = getSlotMaterials();
		if(slots.size() == 0) return null;
        c.getSlotStackPairLists().write(0, slots);
		return c;
	}

	public List<Pair<ItemSlot, ItemStack>> getSlotMaterials(){
		List<Pair<ItemSlot,ItemStack>> matList = new ArrayList<>();
		if(this.headMaterial != null) matList.add(new Pair<>(ItemSlot.HEAD, this.headMaterial));
		if(this.bodyMaterial != null) matList.add(new Pair<>(ItemSlot.CHEST, this.bodyMaterial));
		if(this.mainHand != null) matList.add(new Pair<>(ItemSlot.MAINHAND, this.mainHand));
		if(this.offHand != null) matList.add(new Pair<>(ItemSlot.OFFHAND, this.offHand));
		if(this.legMaterial != null) matList.add(new Pair<>(ItemSlot.LEGS, this.legMaterial));
		if(this.feetMaterial != null) matList.add(new Pair<>(ItemSlot.FEET, this.feetMaterial));
		return matList;
	}
	
	public void setHeadRotation(float x, float y, float z) {
		this.headRotation.setX(x);
		this.headRotation.setY(y);
		this.headRotation.setZ(z);
	}
	
	public void setCustomName(String text) {
		this.customName = text;
	}
	
	public void setCustomNameVisible(boolean visible) {
		this.customNameVisible = visible;
	}

	public String getCustomName() {
		return customName;
	}

	public Vector getBodyRotation() {
		return bodyRotation;
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
		return bodyMaterial;
	}

	public void setBodyArmor(ItemStack bodyMaterial) {
		this.bodyMaterial = bodyMaterial;
	}

	public ItemStack getLegArmor() {
		return legMaterial;
	}

	public void setLegArmor(ItemStack legMaterial) {
		this.legMaterial = legMaterial;
	}

	public ItemStack getFeetArmor() {
		return feetMaterial;
	}

	public void setFeetMaterial(ItemStack feetMaterial) {
		this.feetMaterial = feetMaterial;
	}

	public int getEntityID() {
		return entityID;
	}

	public boolean isBasePlateHidden() {
		return basePlateHidden;
	}

	public void setBasePlateHidden(boolean basePlateHidden) {
		this.basePlateHidden = basePlateHidden;
	}

	public boolean isMarker() {
		return isMarker;
	}

	public void setMarker(boolean isMarker) {
		this.isMarker = isMarker;
	}

	public boolean isSmall() {
		return isSmall;
	}

	public void setSmall(boolean isSmall) {
		this.isSmall = isSmall;
	}

	public boolean isHasArms() {
		return hasArms;
	}

	public void setHasArms(boolean hasArms) {
		this.hasArms = hasArms;
	}

	public void setBodyRotation(double x, double y, double z) {
		this.bodyRotation = new Vector(x, y, z);
	}

	public Vector getLocation() {
		return location;
	}

	public void setLocation(double x, double y, double z) {
		this.location = new Vector(x, y, z);
	}

	public void move(double dx, double dy, double dz){
		this.location = this.location.add(new Vector(dx,dy,dz));
	}

	public void move(Vector v){
		move(v.getX(), v.getY(), v.getZ());
	}

	public void rotateArround(Vector point, double theta, double phi){
		rotateArround(point.getX(), point.getY(), point.getZ(), this.location.distance(point), theta, phi);
	}

	private void rotateArround(double x, double y, double z, double r, double theta, double phi){
		Vector v0 = new Vector(Math.cos(phi) * Math.sin(theta), Math.cos(theta) * Math.sin(phi), Math.cos(theta));
		this.location = v0.normalize().multiply(r).add(new Vector(x, y, z));
		/*this.location = new Vector((r * Math.cos(phi) * Math.sin(-theta)) + x,
													(r * Math.cos(-theta) * Math.sin(phi)) + y,
													(r * Math.cos(-theta)) + z);*/

	}

	public void rotateArround(Vector center, Vector axis, double dtheta){
		this.location.subtract(center).rotateAroundAxis(axis, dtheta).add(center);
	}

	public void setLocation(Vector v){
		this.location = v;
	}

	public Vector getHeadRotation() {
		return headRotation;
	}

	public void setHeadRotation(double x, double y, double z) {
		setHeadRotation(new Vector(x, y, z));
	}

	public void setHeadRotation(Vector v) {
		this.headRotation = v;
	}

	public Vector getLeftArmRotation() {
		return leftArmRotation;
	}

	public void setLeftArmRotation(double x, double y, double z) {
		setLeftArmRotation(new Vector(x, y, z));
	}

	public void setLeftArmRotation(Vector v) {
		this.leftArmRotation = v;
	}

	public Vector getRightArmRotation() {
		return rightArmRotation;
	}

	public void setRightArmRotation(double x, double y, double z) {
		setRightArmRotation(new Vector(x, y, z));
	}

	public void setRightArmRotation(Vector v) {
		this.rightArmRotation = v;
	}

	public Vector getLeftLegRotation() {
		return leftLegRotation;
	}

	public void setLeftLegRotation(double x, double y, double z) {
		setLeftLegRotation(new Vector(x, y, z));
	}

	public void setLeftLegRotation(Vector v) {
		this.leftLegRotation = v;
	}

	public Vector getRightLegRotation() {
		return rightLegRotation;
	}

	public void setRightLegRotation(double x, double y, double z) {
		setRightLegRotation(new Vector(x, y, z));
	}

	public void setRightLegRotation(Vector v) {
		this.rightLegRotation = v;
	}

	public int getPitch(){
		return this.pitch;
	}

	public int getYaw(){
		return this.yaw;
	}

	public void setPitch(int pitch){
		this.pitch = pitch;
	}

	public void setYaw(int yaw){
		this.yaw = yaw;
	}

}
