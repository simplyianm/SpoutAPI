package org.spout.api.material;

import java.io.File;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

import org.spout.api.Spout;
import org.spout.api.io.store.simple.BinaryFileStore;
import org.spout.api.util.StringMap;

public abstract class MaterialRegistry {
	private final static ConcurrentHashMap<String, Material> nameLookup = new ConcurrentHashMap<String, Material>(1000);
	private final static int MAX_SIZE = 1 << 16;
	@SuppressWarnings("unchecked")
	private final static AtomicReference<Material>[] materialLookup = new AtomicReference[MAX_SIZE];
	private static StringMap materialRegistry = null;
	
	static {
		for (int i = 0; i < materialLookup.length; i++) {
			materialLookup[i] = new AtomicReference<Material>();
		}
	}
	
	/**
	 * Sets up the material registry for it's first use. May not be called more than once.
	 */
	public static StringMap setupRegistry() {
		if (materialRegistry == null) {
			File serverItemMap = new File(Spout.getEngine().getWorldFolder(), "server.dat");
			BinaryFileStore store = new BinaryFileStore(serverItemMap);
			if (serverItemMap.exists()) {
				store.load();
			}
			materialRegistry = new StringMap(null, store, 0, Short.MAX_VALUE);
			return materialRegistry;
		} else {
			throw new IllegalStateException("Can not set up material registry twice!");
		}
	}
	
	protected static <T extends Material> T register(T material, int id) {
		if (material.isSubMaterial()) {
			material.getParentMaterial().registerSubMaterial(material);
			nameLookup.put(material.getName().toLowerCase(), material);
			return material;
		} else {
			if (id != Integer.MAX_VALUE && materialRegistry.register(material.getName(), id)){ 
				material.id = (short)id;
			} else {
				material.id = (short)materialRegistry.register(material.getName());
			}
			if (!materialLookup[id].compareAndSet(null, material)) {
				throw new IllegalArgumentException("Another material is already mapped to id: " + material.getId() + "!");
			} else {
				nameLookup.put(material.getName().toLowerCase(), material);
				return material;
			}
		}
	}

	/**
	 * Registers the material in the material lookup service
	 * 
	 * @param material to register
	 */
	public static <T extends Material> T register(T material) {
		return register(material, Integer.MAX_VALUE);
	}

	/**
	 * Gets the material from the given id
	 *
	 * @param id to get
	 * @return material or null if none found
	 */
	public static Material get(short id) {
		if (id < 0 || id >= materialLookup.length) {
			return null;
		}
		return materialLookup[id].get();
	}

	/**
	 * Returns all current materials in the game
	 *
	 * @return an array of all materials
	 */
	public static Material[] values() {
		//TODO: This is wrong, need to count # of registered materials
		HashSet<Material> set = new HashSet<Material>(1000);
		for (int i = 0; i < materialLookup.length; i++) {
			if (materialLookup[i].get() != null) {
				set.add(materialLookup[i].get());
			}
		}
		return set.toArray(new Material[0]);

	}

	/**
	 * Gets the associated material with it's name. Case-insensitive.
	 *
	 * @param name to lookup
	 * @return material, or null if none found
	 */
	public static Material get(String name) {
		return nameLookup.get(name.toLowerCase());
	}
}
