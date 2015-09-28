package com.starnetmc.core.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.starnetmc.core.util.ReflectionUtils.PackageType;


/**
 * <b>ParticleEffect Library</b>
 * <p>
 * This library was created by @DarkBlade12 and allows you to display all Minecraft particle effects on a Bukkit server
 * <p>
 * You are welcome to use it, modify it and redistribute it under the following conditions:
 * <ul>
 * <li>Don't claim this class as your own
 * <li>Don't remove this disclaimer
 * </ul>
 * <p>
 * Special thanks:
 * <ul>
 * <li>@microgeek (original idea, names and packet parameters)
 * <li>@ShadyPotato (1.8 names, ids and packet parameters)
 * <li>@RingOfStorms (particle behavior)
 * <li>@Cybermaxke (particle behavior)
 * </ul>
 * <p>
 * <i>It would be nice if you provide credit to me if you use this class in a published project</i>
 * 
 * @author DarkBlade12
 * @version 1.7
 */

public enum ParticleEffect {
	/**
	 * A particle effect which is displayed by exploding tnt and creepers:
	 * <ul>
	 * <li>It looks like a white cloud
	 * <li>The speed value influences the velocity at which the particle flies off
	 * </ul>
	 */
	EXPLOSION_NORMAL("explode", 0, -1, ParticleProperty.DIRECTIONAL),
	/**
	 * A particle effect which is displayed by exploding ghast fireballs and wither skulls:
	 * <ul>
	 * <li>It looks like a gray ball which is fading away
	 * <li>The speed value slightly influences the size of this particle effect
	 * </ul>
	 */
	EXPLOSION_LARGE("largeexplode", 1, -1),
	/**
	 * A particle effect which is displayed by exploding tnt and creepers:
	 * <ul>
	 * <li>It looks like a crowd of gray balls which are fading away
	 * <li>The speed value has no influence on this particle effect
	 * </ul>
	 */
	EXPLOSION_HUGE("hugeexplosion", 2, -1),
	/**
	 * A particle effect which is displayed by launching fireworks:
	 * <ul>
	 * <li>It looks like a white star which is sparkling
	 * <li>The speed value influences the velocity at which the particle flies off
	 * </ul>
	 */
	FIREWORKS_SPARK("fireworksSpark", 3, -1, ParticleProperty.DIRECTIONAL),
	/**
	 * A particle effect which is displayed by swimming entities and arrows in water:
	 * <ul>
	 * <li>It looks like a bubble
	 * <li>The speed value influences the velocity at which the particle flies off
	 * </ul>
	 */
	WATER_BUBBLE("bubble", 4, -1, ParticleProperty.DIRECTIONAL, ParticleProperty.REQUIRES_WATER),
	/**
	 * A particle effect which is displayed by swimming entities and shaking wolves:
	 * <ul>
	 * <li>It looks like a blue drop
	 * <li>The speed value has no influence on this particle effect
	 * </ul>
	 */
	WATER_SPLASH("splash", 5, -1, ParticleProperty.DIRECTIONAL),
	/**
	 * A particle effect which is displayed on water when fishing:
	 * <ul>
	 * <li>It looks like a blue droplet
	 * <li>The speed value influences the velocity at which the particle flies off
	 * </ul>
	 */
	WATER_WAKE("wake", 6, 7, ParticleProperty.DIRECTIONAL),
	/**
	 * A particle effect which is displayed by water:
	 * <ul>
	 * <li>It looks like a tiny blue square
	 * <li>The speed value has no influence on this particle effect
	 * </ul>
	 */
	SUSPENDED("suspended", 7, -1, ParticleProperty.REQUIRES_WATER),
	/**
	 * A particle effect which is displayed by air when close to bedrock and the in the void:
	 * <ul>
	 * <li>It looks like a tiny gray square
	 * <li>The speed value has no influence on this particle effect
	 * </ul>
	 */
	SUSPENDED_DEPTH("depthSuspend", 8, -1, ParticleProperty.DIRECTIONAL),
	/**
	 * A particle effect which is displayed when landing a critical hit and by arrows:
	 * <ul>
	 * <li>It looks like a light brown cross
	 * <li>The speed value influences the velocity at which the particle flies off
	 * </ul>
	 */
	CRIT("crit", 9, -1, ParticleProperty.DIRECTIONAL),
	/**
	 * A particle effect which is displayed when landing a hit with an enchanted weapon:
	 * <ul>
	 * <li>It looks like a cyan star
	 * <li>The speed value influences the velocity at which the particle flies off
	 * </ul>
	 */
	CRIT_MAGIC("magicCrit", 10, -1, ParticleProperty.DIRECTIONAL),
	/**
	 * A particle effect which is displayed by primed tnt, torches, droppers, dispensers, end portals, brewing stands and monster spawners:
	 * <ul>
	 * <li>It looks like a little gray cloud
	 * <li>The speed value influences the velocity at which the particle flies off
	 * </ul>
	 */
	SMOKE_NORMAL("smoke", 11, -1, ParticleProperty.DIRECTIONAL),
	/**
	 * A particle effect which is displayed by fire, minecarts with furnace and blazes:
	 * <ul>
	 * <li>It looks like a large gray cloud
	 * <li>The speed value influences the velocity at which the particle flies off
	 * </ul>
	 */
	SMOKE_LARGE("largesmoke", 12, -1, ParticleProperty.DIRECTIONAL),
	/**
	 * A particle effect which is displayed when splash potions or bottles o' enchanting hit something:
	 * <ul>
	 * <li>It looks like a white swirl
	 * <li>The speed value causes the particle to only move upwards when set to 0
	 * <li>Only the motion on the y-axis can be controlled, the motion on the x- and z-axis are multiplied by 0.1 when setting the values to 0
	 * </ul>
	 */
	SPELL("spell", 13, -1),
	/**
	 * A particle effect which is displayed when instant splash potions hit something:
	 * <ul>
	 * <li>It looks like a white cross
	 * <li>The speed value causes the particle to only move upwards when set to 0
	 * <li>Only the motion on the y-axis can be controlled, the motion on the x- and z-axis are multiplied by 0.1 when setting the values to 0
	 * </ul>
	 */
	SPELL_INSTANT("instantSpell", 14, -1),
	/**
	 * A particle effect which is displayed by entities with active potion effects:
	 * <ul>
	 * <li>It looks like a colored swirl
	 * <li>The speed value causes the particle to be colored black when set to 0
	 * <li>The particle color gets lighter when increasing the speed and darker when decreasing the speed
	 * </ul>
	 */
	SPELL_MOB("mobSpell", 15, -1, ParticleProperty.COLORABLE),
	/**
	 * A particle effect which is displayed by entities with active potion effects applied through a beacon:
	 * <ul>
	 * <li>It looks like a transparent colored swirl
	 * <li>The speed value causes the particle to be always colored black when set to 0
	 * <li>The particle color gets lighter when increasing the speed and darker when decreasing the speed
	 * </ul>
	 */
	SPELL_MOB_AMBIENT("mobSpellAmbient", 16, -1, ParticleProperty.COLORABLE),
	/**
	 * A particle effect which is displayed by witches:
	 * <ul>
	 * <li>It looks like a purple cross
	 * <li>The speed value causes the particle to only move upwards when set to 0
	 * <li>Only the motion on the y-axis can be controlled, the motion on the x- and z-axis are multiplied by 0.1 when setting the values to 0
	 * </ul>
	 */
	SPELL_WITCH("witchMagic", 17, -1),
	/**
	 * A particle effect which is displayed by blocks beneath a water source:
	 * <ul>
	 * <li>It looks like a blue drip
	 * <li>The speed value has no influence on this particle effect
	 * </ul>
	 */
	DRIP_WATER("dripWater", 18, -1),
	/**
	 * A particle effect which is displayed by blocks beneath a lava source:
	 * <ul>
	 * <li>It looks like an orange drip
	 * <li>The speed value has no influence on this particle effect
	 * </ul>
	 */
	DRIP_LAVA("dripLava", 19, -1),
	/**
	 * A particle effect which is displayed when attacking a villager in a village:
	 * <ul>
	 * <li>It looks like a cracked gray heart
	 * <li>The speed value has no influence on this particle effect
	 * </ul>
	 */
	VILLAGER_ANGRY("angryVillager", 20, -1),
	/**
	 * A particle effect which is displayed when using bone meal and trading with a villager in a village:
	 * <ul>
	 * <li>It looks like a green star
	 * <li>The speed value has no influence on this particle effect
	 * </ul>
	 */
	VILLAGER_HAPPY("happyVillager", 21, -1, ParticleProperty.DIRECTIONAL),
	/**
	 * A particle effect which is displayed by mycelium:
	 * <ul>
	 * <li>It looks like a tiny gray square
	 * <li>The speed value has no influence on this particle effect
	 * </ul>
	 */
	TOWN_AURA("townaura", 22, -1, ParticleProperty.DIRECTIONAL),
	/**
	 * A particle effect which is displayed by note blocks:
	 * <ul>
	 * <li>It looks like a colored note
	 * <li>The speed value causes the particle to be colored green when set to 0
	 * </ul>
	 */
	NOTE("note", 23, -1, ParticleProperty.COLORABLE),
	/**
	 * A particle effect which is displayed by nether portals, endermen, ender pearls, eyes of ender, ender chests and dragon eggs:
	 * <ul>
	 * <li>It looks like a purple cloud
	 * <li>The speed value influences the spread of this particle effect
	 * </ul>
	 */
	PORTAL("portal", 24, -1, ParticleProperty.DIRECTIONAL),
	/**
	 * A particle effect which is displayed by enchantment tables which are nearby bookshelves:
	 * <ul>
	 * <li>It looks like a cryptic white letter
	 * <li>The speed value influences the spread of this particle effect
	 * </ul>
	 */
	ENCHANTMENT_TABLE("enchantmenttable", 25, -1, ParticleProperty.DIRECTIONAL),
	/**
	 * A particle effect which is displayed by torches, active furnaces, magma cubes and monster spawners:
	 * <ul>
	 * <li>It looks like a tiny flame
	 * <li>The speed value influences the velocity at which the particle flies off
	 * </ul>
	 */
	FLAME("flame", 26, -1, ParticleProperty.DIRECTIONAL),
	/**
	 * A particle effect which is displayed by lava:
	 * <ul>
	 * <li>It looks like a spark
	 * <li>The speed value has no influence on this particle effect
	 * </ul>
	 */
	LAVA("lava", 27, -1),
	/**
	 * A particle effect which is currently unused:
	 * <ul>
	 * <li>It looks like a transparent gray square
	 * <li>The speed value has no influence on this particle effect
	 * </ul>
	 */
	FOOTSTEP("footstep", 28, -1),
	/**
	 * A particle effect which is displayed when a mob dies:
	 * <ul>
	 * <li>It looks like a large white cloud
	 * <li>The speed value influences the velocity at which the particle flies off
	 * </ul>
	 */
	CLOUD("cloud", 29, -1, ParticleProperty.DIRECTIONAL),
	/**
	 * A particle effect which is displayed by redstone ore, powered redstone, redstone torches and redstone repeaters:
	 * <ul>
	 * <li>It looks like a tiny colored cloud
	 * <li>The speed value causes the particle to be colored red when set to 0
	 * </ul>
	 */
	REDSTONE("reddust", 30, -1, ParticleProperty.COLORABLE),
	/**
	 * A particle effect which is displayed when snowballs hit a block:
	 * <ul>
	 * <li>It looks like a little piece with the snowball texture
	 * <li>The speed value has no influence on this particle effect
	 * </ul>
	 */
	SNOWBALL("snowballpoof", 31, -1),
	/**
	 * A particle effect which is currently unused:
	 * <ul>
	 * <li>It looks like a tiny white cloud
	 * <li>The speed value influences the velocity at which the particle flies off
	 * </ul>
	 */
	SNOW_SHOVEL("snowshovel", 32, -1, ParticleProperty.DIRECTIONAL),
	/**
	 * A particle effect which is displayed by slimes:
	 * <ul>
	 * <li>It looks like a tiny part of the slimeball icon
	 * <li>The speed value has no influence on this particle effect
	 * </ul>
	 */
	SLIME("slime", 33, -1),
	/**
	 * A particle effect which is displayed when breeding and taming animals:
	 * <ul>
	 * <li>It looks like a red heart
	 * <li>The speed value has no influence on this particle effect
	 * </ul>
	 */
	HEART("heart", 34, -1),
	/**
	 * A particle effect which is displayed by barriers:
	 * <ul>
	 * <li>It looks like a red box with a slash through it
	 * <li>The speed value has no influence on this particle effect
	 * </ul>
	 */
	BARRIER("barrier", 35, 8),
	/**
	 * A particle effect which is displayed when breaking a tool or eggs hit a block:
	 * <ul>
	 * <li>It looks like a little piece with an item texture
	 * </ul>
	 */
	ITEM_CRACK("iconcrack", 36, -1, ParticleProperty.DIRECTIONAL, ParticleProperty.REQUIRES_DATA),
	/**
	 * A particle effect which is displayed when breaking blocks or sprinting:
	 * <ul>
	 * <li>It looks like a little piece with a block texture
	 * <li>The speed value has no influence on this particle effect
	 * </ul>
	 */
	BLOCK_CRACK("blockcrack", 37, -1, ParticleProperty.DIRECTIONAL, ParticleProperty.REQUIRES_DATA),
	/**
	 * A particle effect which is displayed when falling:
	 * <ul>
	 * <li>It looks like a little piece with a block texture
	 * </ul>
	 */
	BLOCK_DUST("blockdust", 38, 7, ParticleProperty.DIRECTIONAL, ParticleProperty.REQUIRES_DATA),
	/**
	 * A particle effect which is displayed when rain hits the ground:
	 * <ul>
	 * <li>It looks like a blue droplet
	 * <li>The speed value has no influence on this particle effect
	 * </ul>
	 */
	WATER_DROP("droplet", 39, 8),
	/**
	 * A particle effect which is currently unused:
	 * <ul>
	 * <li>It has no visual effect
	 * </ul>
	 */
	ITEM_TAKE("take", 40, 8),
	/**
	 * A particle effect which is displayed by elder guardians:
	 * <ul>
	 * <li>It looks like the shape of the elder guardian
	 * <li>The speed value has no influence on this particle effect
	 * <li>The offset values have no influence on this particle effect
	 * </ul>
	 */
	MOB_APPEARANCE("mobappearance", 41, 8);

	private static final Map<String, ParticleEffect> NAME_MAP = new HashMap<String, ParticleEffect>();
	private static final Map<Integer, ParticleEffect> ID_MAP = new HashMap<Integer, ParticleEffect>();
	private final String name;
	private final int id;
	private final int requiredVersion;
	private final List<ParticleProperty> properties;

	static {
		for (ParticleEffect effect : values()) {
			NAME_MAP.put(effect.name, effect);
			ID_MAP.put(effect.id, effect);
		}
	}

	
	private ParticleEffect(String name, int id, int requiredVersion, ParticleProperty... properties) {
		this.name = name;
		this.id = id;
		this.requiredVersion = requiredVersion;
		this.properties = Arrays.asList(properties);
	}

	
	public String getName() {
		return name;
	}

	
	public int getId() {
		return id;
	}

	public int getRequiredVersion() {
		return requiredVersion;
	}

	public boolean hasProperty(ParticleProperty property) {
		return properties.contains(property);
	}

	public boolean isSupported() {
		if (requiredVersion == -1) {
			return true;
		}
		return ParticlePacket.getVersion() >= requiredVersion;
	}

	public static ParticleEffect fromName(String name) {
		for (Entry<String, ParticleEffect> entry : NAME_MAP.entrySet()) {
			if (!entry.getKey().equalsIgnoreCase(name)) {
				continue;
			}
			return entry.getValue();
		}
		return null;
	}

	public static ParticleEffect fromId(int id) {
		for (Entry<Integer, ParticleEffect> entry : ID_MAP.entrySet()) {
			if (entry.getKey() != id) {
				continue;
			}
			return entry.getValue();
		}
		return null;
	}

	private static boolean isWater(Location location) {
		Material material = location.getBlock().getType();
		return material == Material.WATER || material == Material.STATIONARY_WATER;
	}

	private static boolean isLongDistance(Location location, List<Player> players) {
		String world = location.getWorld().getName();
		for (Player player : players) {
			Location playerLocation = player.getLocation();
			if (!world.equals(playerLocation.getWorld().getName()) || playerLocation.distanceSquared(location) < 65536) {
				continue;
			}
			return true;
		}
		return false;
	}

	private static boolean isDataCorrect(ParticleEffect effect, ParticleData data) {
		return ((effect == BLOCK_CRACK || effect == BLOCK_DUST) && data instanceof BlockData) || (effect == ITEM_CRACK && data instanceof ItemData);
	}

	private static boolean isColorCorrect(ParticleEffect effect, ParticleColor color) {
		return ((effect == SPELL_MOB || effect == SPELL_MOB_AMBIENT || effect == REDSTONE) && color instanceof OrdinaryColor) || (effect == NOTE && color instanceof NoteColor);
	}

	public void display(float offsetX, float offsetY, float offsetZ, float speed, int amount, Location center, double range) throws ParticleVersionException, ParticleDataException, IllegalArgumentException {
		if (!isSupported()) {
			throw new ParticleVersionException("This particle effect is not supported by your server version");
		}
		if (hasProperty(ParticleProperty.REQUIRES_DATA)) {
			throw new ParticleDataException("This particle effect requires additional data");
		}
		if (hasProperty(ParticleProperty.REQUIRES_WATER) && !isWater(center)) {
			throw new IllegalArgumentException("There is no water at the center location");
		}
		new ParticlePacket(this, offsetX, offsetY, offsetZ, speed, amount, range > 256, null).sendTo(center, range);
	}

	public void display(float offsetX, float offsetY, float offsetZ, float speed, int amount, Location center, List<Player> players) throws ParticleVersionException, ParticleDataException, IllegalArgumentException {
		if (!isSupported()) {
			throw new ParticleVersionException("This particle effect is not supported by your server version");
		}
		if (hasProperty(ParticleProperty.REQUIRES_DATA)) {
			throw new ParticleDataException("This particle effect requires additional data");
		}
		if (hasProperty(ParticleProperty.REQUIRES_WATER) && !isWater(center)) {
			throw new IllegalArgumentException("There is no water at the center location");
		}
		new ParticlePacket(this, offsetX, offsetY, offsetZ, speed, amount, isLongDistance(center, players), null).sendTo(center, players);
	}

	public void display(float offsetX, float offsetY, float offsetZ, float speed, int amount, Location center, Player... players) throws ParticleVersionException, ParticleDataException, IllegalArgumentException {
		display(offsetX, offsetY, offsetZ, speed, amount, center, Arrays.asList(players));
	}

	public void display(Vector direction, float speed, Location center, double range) throws ParticleVersionException, ParticleDataException, IllegalArgumentException {
		if (!isSupported()) {
			throw new ParticleVersionException("This particle effect is not supported by your server version");
		}
		if (hasProperty(ParticleProperty.REQUIRES_DATA)) {
			throw new ParticleDataException("This particle effect requires additional data");
		}
		if (!hasProperty(ParticleProperty.DIRECTIONAL)) {
			throw new IllegalArgumentException("This particle effect is not directional");
		}
		if (hasProperty(ParticleProperty.REQUIRES_WATER) && !isWater(center)) {
			throw new IllegalArgumentException("There is no water at the center location");
		}
		new ParticlePacket(this, direction, speed, range > 256, null).sendTo(center, range);
	}

	public void display(Vector direction, float speed, Location center, List<Player> players) throws ParticleVersionException, ParticleDataException, IllegalArgumentException {
		if (!isSupported()) {
			throw new ParticleVersionException("This particle effect is not supported by your server version");
		}
		if (hasProperty(ParticleProperty.REQUIRES_DATA)) {
			throw new ParticleDataException("This particle effect requires additional data");
		}
		if (!hasProperty(ParticleProperty.DIRECTIONAL)) {
			throw new IllegalArgumentException("This particle effect is not directional");
		}
		if (hasProperty(ParticleProperty.REQUIRES_WATER) && !isWater(center)) {
			throw new IllegalArgumentException("There is no water at the center location");
		}
		new ParticlePacket(this, direction, speed, isLongDistance(center, players), null).sendTo(center, players);
	}

	public void display(Vector direction, float speed, Location center, Player... players) throws ParticleVersionException, ParticleDataException, IllegalArgumentException {
		display(direction, speed, center, Arrays.asList(players));
	}

	public void display(ParticleColor color, Location center, double range) throws ParticleVersionException, ParticleColorException {
		if (!isSupported()) {
			throw new ParticleVersionException("This particle effect is not supported by your server version");
		}
		if (!hasProperty(ParticleProperty.COLORABLE)) {
			throw new ParticleColorException("This particle effect is not colorable");
		}
		if (!isColorCorrect(this, color)) {
			throw new ParticleColorException("The particle color type is incorrect");
		}
		new ParticlePacket(this, color, range > 256).sendTo(center, range);
	}

	public void display(ParticleColor color, Location center, List<Player> players) throws ParticleVersionException, ParticleColorException {
		if (!isSupported()) {
			throw new ParticleVersionException("This particle effect is not supported by your server version");
		}
		if (!hasProperty(ParticleProperty.COLORABLE)) {
			throw new ParticleColorException("This particle effect is not colorable");
		}
		if (!isColorCorrect(this, color)) {
			throw new ParticleColorException("The particle color type is incorrect");
		}
		new ParticlePacket(this, color, isLongDistance(center, players)).sendTo(center, players);
	}

	public void display(ParticleColor color, Location center, Player... players) throws ParticleVersionException, ParticleColorException {
		display(color, center, Arrays.asList(players));
	}

	public void display(ParticleData data, float offsetX, float offsetY, float offsetZ, float speed, int amount, Location center, double range) throws ParticleVersionException, ParticleDataException {
		if (!isSupported()) {
			throw new ParticleVersionException("This particle effect is not supported by your server version");
		}
		if (!hasProperty(ParticleProperty.REQUIRES_DATA)) {
			throw new ParticleDataException("This particle effect does not require additional data");
		}
		if (!isDataCorrect(this, data)) {
			throw new ParticleDataException("The particle data type is incorrect");
		}
		new ParticlePacket(this, offsetX, offsetY, offsetZ, speed, amount, range > 256, data).sendTo(center, range);
	}

	public void display(ParticleData data, float offsetX, float offsetY, float offsetZ, float speed, int amount, Location center, List<Player> players) throws ParticleVersionException, ParticleDataException {
		if (!isSupported()) {
			throw new ParticleVersionException("This particle effect is not supported by your server version");
		}
		if (!hasProperty(ParticleProperty.REQUIRES_DATA)) {
			throw new ParticleDataException("This particle effect does not require additional data");
		}
		if (!isDataCorrect(this, data)) {
			throw new ParticleDataException("The particle data type is incorrect");
		}
		new ParticlePacket(this, offsetX, offsetY, offsetZ, speed, amount, isLongDistance(center, players), data).sendTo(center, players);
	}

	public void display(ParticleData data, float offsetX, float offsetY, float offsetZ, float speed, int amount, Location center, Player... players) throws ParticleVersionException, ParticleDataException {
		display(data, offsetX, offsetY, offsetZ, speed, amount, center, Arrays.asList(players));
	}

	public void display(ParticleData data, Vector direction, float speed, Location center, double range) throws ParticleVersionException, ParticleDataException {
		if (!isSupported()) {
			throw new ParticleVersionException("This particle effect is not supported by your server version");
		}
		if (!hasProperty(ParticleProperty.REQUIRES_DATA)) {
			throw new ParticleDataException("This particle effect does not require additional data");
		}
		if (!isDataCorrect(this, data)) {
			throw new ParticleDataException("The particle data type is incorrect");
		}
		new ParticlePacket(this, direction, speed, range > 256, data).sendTo(center, range);
	}

	public void display(ParticleData data, Vector direction, float speed, Location center, List<Player> players) throws ParticleVersionException, ParticleDataException {
		if (!isSupported()) {
			throw new ParticleVersionException("This particle effect is not supported by your server version");
		}
		if (!hasProperty(ParticleProperty.REQUIRES_DATA)) {
			throw new ParticleDataException("This particle effect does not require additional data");
		}
		if (!isDataCorrect(this, data)) {
			throw new ParticleDataException("The particle data type is incorrect");
		}
		new ParticlePacket(this, direction, speed, isLongDistance(center, players), data).sendTo(center, players);
	}

	public void display(ParticleData data, Vector direction, float speed, Location center, Player... players) throws ParticleVersionException, ParticleDataException {
		display(data, direction, speed, center, Arrays.asList(players));
	}


	public static enum ParticleProperty {
		
		REQUIRES_WATER,
	
		REQUIRES_DATA,
		
		DIRECTIONAL,
	
		COLORABLE;
	}

	public static abstract class ParticleData {
		private final Material material;
		private final byte data;
		private final int[] packetData;

		@SuppressWarnings("deprecation")
		public ParticleData(Material material, byte data) {
			this.material = material;
			this.data = data;
			this.packetData = new int[] { material.getId(), data };
		}

		public Material getMaterial() {
			return material;
		}

		public byte getData() {
			return data;
		}

		public int[] getPacketData() {
			return packetData;
		}

		public String getPacketDataString() {
			return "_" + packetData[0] + "_" + packetData[1];
		}
	}

	public static final class ItemData extends ParticleData {
	
		public ItemData(Material material, byte data) {
			super(material, data);
		}
	}

	public static final class BlockData extends ParticleData {
	
		public BlockData(Material material, byte data) throws IllegalArgumentException {
			super(material, data);
			if (!material.isBlock()) {
				throw new IllegalArgumentException("The material is not a block");
			}
		}
	}

	
	public static abstract class ParticleColor {
	
		public abstract float getValueX();

	
		public abstract float getValueY();

		public abstract float getValueZ();
	}

	public static final class OrdinaryColor extends ParticleColor {
		private final int red;
		private final int green;
		private final int blue;

		public OrdinaryColor(int red, int green, int blue) throws IllegalArgumentException {
			if (red < 0) {
				throw new IllegalArgumentException("The red value is lower than 0");
			}
			if (red > 255) {
				throw new IllegalArgumentException("The red value is higher than 255");
			}
			this.red = red;
			if (green < 0) {
				throw new IllegalArgumentException("The green value is lower than 0");
			}
			if (green > 255) {
				throw new IllegalArgumentException("The green value is higher than 255");
			}
			this.green = green;
			if (blue < 0) {
				throw new IllegalArgumentException("The blue value is lower than 0");
			}
			if (blue > 255) {
				throw new IllegalArgumentException("The blue value is higher than 255");
			}
			this.blue = blue;
		}

		public int getRed() {
			return red;
		}

		public int getGreen() {
			return green;
		}

		public int getBlue() {
			return blue;
		}

		@Override
		public float getValueX() {
			return (float) red / 255F;
		}

		@Override
		public float getValueY() {
			return (float) green / 255F;
		}

		@Override
		public float getValueZ() {
			return (float) blue / 255F;
		}
	}

	public static final class NoteColor extends ParticleColor {
		private final int note;

		public NoteColor(int note) throws IllegalArgumentException {
			if (note < 0) {
				throw new IllegalArgumentException("The note value is lower than 0");
			}
			if (note > 24) {
				throw new IllegalArgumentException("The note value is higher than 24");
			}
			this.note = note;
		}

	
		@Override
		public float getValueX() {
			return (float) note / 24F;
		}

		@Override
		public float getValueY() {
			return 0;
		}

		@Override
		public float getValueZ() {
			return 0;
		}

	}


	private static final class ParticleDataException extends RuntimeException {
		private static final long serialVersionUID = 3203085387160737484L;

		public ParticleDataException(String message) {
			super(message);
		}
	}

	private static final class ParticleColorException extends RuntimeException {
		private static final long serialVersionUID = 3203085387160737484L;

		public ParticleColorException(String message) {
			super(message);
		}
	}

	private static final class ParticleVersionException extends RuntimeException {
		private static final long serialVersionUID = 3203085387160737484L;

		public ParticleVersionException(String message) {
			super(message);
		}
	}

	public static final class ParticlePacket {
		private static int version;
		private static Class<?> enumParticle;
		private static Constructor<?> packetConstructor;
		private static Method getHandle;
		private static Field playerConnection;
		private static Method sendPacket;
		private static boolean initialized;
		private final ParticleEffect effect;
		private final float offsetX;
		private final float offsetY;
		private final float offsetZ;
		private final float speed;
		private final int amount;
		private final boolean longDistance;
		private final ParticleData data;
		private Object packet;

		public ParticlePacket(ParticleEffect effect, float offsetX, float offsetY, float offsetZ, float speed, int amount, boolean longDistance, ParticleData data) throws IllegalArgumentException {
			initialize();
			if (speed < 0) {
				throw new IllegalArgumentException("The speed is lower than 0");
			}
			if (amount < 0) {
				throw new IllegalArgumentException("The amount is lower than 0");
			}
			this.effect = effect;
			this.offsetX = offsetX;
			this.offsetY = offsetY;
			this.offsetZ = offsetZ;
			this.speed = speed;
			this.amount = amount;
			this.longDistance = longDistance;
			this.data = data;
		}

		public ParticlePacket(ParticleEffect effect, Vector direction, float speed, boolean longDistance, ParticleData data) throws IllegalArgumentException {
			this(effect, (float) direction.getX(), (float) direction.getY(), (float) direction.getZ(), speed, 0, longDistance, data);
		}

		public ParticlePacket(ParticleEffect effect, ParticleColor color, boolean longDistance) {
			this(effect, color.getValueX(), color.getValueY(), color.getValueZ(), 1, 0, longDistance, null);
		}

		public static void initialize() throws VersionIncompatibleException {
			if (initialized) {
				return;
			}
			try {
				version = Integer.parseInt(Character.toString(PackageType.getServerVersion().charAt(3)));
				if (version > 7) {
					enumParticle = PackageType.MINECRAFT_SERVER.getClass("EnumParticle");
				}
				Class<?> packetClass = PackageType.MINECRAFT_SERVER.getClass(version < 7 ? "Packet63WorldParticles" : "PacketPlayOutWorldParticles");
				packetConstructor = ReflectionUtils.getConstructor(packetClass);
				getHandle = ReflectionUtils.getMethod("CraftPlayer", PackageType.CRAFTBUKKIT_ENTITY, "getHandle");
				playerConnection = ReflectionUtils.getField("EntityPlayer", PackageType.MINECRAFT_SERVER, false, "playerConnection");
				sendPacket = ReflectionUtils.getMethod(playerConnection.getType(), "sendPacket", PackageType.MINECRAFT_SERVER.getClass("Packet"));
			} catch (Exception exception) {
				throw new VersionIncompatibleException("Your current bukkit version seems to be incompatible with this library", exception);
			}
			initialized = true;
		}

		public static int getVersion() {
			if (!initialized) {
				initialize();
			}
			return version;
		}

		public static boolean isInitialized() {
			return initialized;
		}


		private void initializePacket(Location center) throws PacketInstantiationException {
			if (packet != null) {
				return;
			}
			try {
				packet = packetConstructor.newInstance();
				if (version < 8) {
					String name = effect.getName();
					if (data != null) {
						name += data.getPacketDataString();
					}
					ReflectionUtils.setValue(packet, true, "a", name);
				} else {
					ReflectionUtils.setValue(packet, true, "a", enumParticle.getEnumConstants()[effect.getId()]);
					ReflectionUtils.setValue(packet, true, "j", longDistance);
					if (data != null) {
						ReflectionUtils.setValue(packet, true, "k", data.getPacketData());
					}
				}
				ReflectionUtils.setValue(packet, true, "b", (float) center.getX());
				ReflectionUtils.setValue(packet, true, "c", (float) center.getY());
				ReflectionUtils.setValue(packet, true, "d", (float) center.getZ());
				ReflectionUtils.setValue(packet, true, "e", offsetX);
				ReflectionUtils.setValue(packet, true, "f", offsetY);
				ReflectionUtils.setValue(packet, true, "g", offsetZ);
				ReflectionUtils.setValue(packet, true, "h", speed);
				ReflectionUtils.setValue(packet, true, "i", amount);
			} catch (Exception exception) {
				throw new PacketInstantiationException("Packet instantiation failed", exception);
			}
		}

		public void sendTo(Location center, Player player) throws PacketInstantiationException, PacketSendingException {
			initializePacket(center);
			try {
				sendPacket.invoke(playerConnection.get(getHandle.invoke(player)), packet);
			} catch (Exception exception) {
				throw new PacketSendingException("Failed to send the packet to player '" + player.getName() + "'", exception);
			}
		}

		public void sendTo(Location center, List<Player> players) throws IllegalArgumentException {
			if (players.isEmpty()) {
				throw new IllegalArgumentException("The player list is empty");
			}
			for (Player player : players) {
				sendTo(center, player);
			}
		}

		public void sendTo(Location center, double range) throws IllegalArgumentException {
			if (range < 1) {
				throw new IllegalArgumentException("The range is lower than 1");
			}
			String worldName = center.getWorld().getName();
			double squared = range * range;
			for (Player player : Bukkit.getOnlinePlayers()) {
				if (!player.getWorld().getName().equals(worldName) || player.getLocation().distanceSquared(center) > squared) {
					continue;
				}
				sendTo(center, player);
			}
		}

		private static final class VersionIncompatibleException extends RuntimeException {
			private static final long serialVersionUID = 3203085387160737484L;

			public VersionIncompatibleException(String message, Throwable cause) {
				super(message, cause);
			}
		}

		private static final class PacketInstantiationException extends RuntimeException {
			private static final long serialVersionUID = 3203085387160737484L;

			public PacketInstantiationException(String message, Throwable cause) {
				super(message, cause);
			}
		}

		private static final class PacketSendingException extends RuntimeException {
			private static final long serialVersionUID = 3203085387160737484L;

			public PacketSendingException(String message, Throwable cause) {
				super(message, cause);
			}
		}
	}
}