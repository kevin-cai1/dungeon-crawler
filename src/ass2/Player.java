package ass2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Player extends Entity{
	private HashMap<Entity, Integer> inventory;
	public Player() {
		inventory = new HashMap<>();
		// TODO Auto-generated constructor stub
	}
	public HashMap<Entity, Integer> getInventory() {
		return inventory;
	}
	/**
	 * Does not check whether entity is a valid entity. i.e. if sword already exists, it will not handle this case, or if too many arrows or bombs or something
	 * 
	 * @param entity
	 */
	public void putInventory(Entity entity) {
		Set<Entity> set = inventory.keySet();
		for(Entity e: set) {
			if(e.getClass().equals(entity.getClass())) {
				int value = inventory.get(e);
				value++;
				inventory.put(e, value); //in theory it should replace 
			}
		}
	}
	/**
	 * decrease Durability should only accept valid entities such as sword, arrow 
	 * @param entity
	 */
	public void decreaseUse(Entity entity) {
		if(entity.getClass().equals(new Sword().getClass())) {
			Sword sword = (Sword)entity;
			sword.reduceDurability();
			entity = sword;
		}
	}
    @Override
    public String toString() {
        return null;
    }
}
