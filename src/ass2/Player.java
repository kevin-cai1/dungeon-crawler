package ass2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Player extends Entity{
	private HashMap<Entity, Integer> inventory;
	private ArrayList<Key> keys;
	private int invincible;
	private boolean floaty;
	private int treasure;
	public Player() {
		treasure = 0;
		floaty = false;
		invincible = 0;
		inventory = new HashMap<>();
		keys = new ArrayList<>();
		// TODO Auto-generated constructor stub
	}
	public void invincibleTick() {
		if(invincible > 0) {
			invincible--;
		}
	}
	public void addTreasure() {
		treasure++;
	}
	public int getTreasure() {
		return treasure;
	}
	/**
	 * invincible will last for 10 turns
	 */
	public void addInviciblility() {
		invincible = 10;
	}
	/**
	 * returns true if player is invincible
	 * @return
	 */
	public boolean getInvincibility() {
		return (invincible != 0);
	}
	public void addHover() {
		floaty = true;
	}
	public boolean getHover() {
		return floaty;
	}
	public HashMap<Entity, Integer> getInventory() {
		return inventory;
	}
	public void addKey(Key key) {
		keys.add(key);
	}
	/**
	 * checkKey checks if the player has the key to open the door. If they do, the door will open
	 * not sure what should happen if the door is already open
	 * @param door
	 * @return returns true if the door is openable.
	 */
	public boolean checkKey(Door door) {
		for(Key key: keys) {
			if(door.getUnique() == key.getUnique()) {
				door.openDoor();
				return true;
			}
		}
		return false;
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
			
			if(sword.durabilityZero()) {
				inventory.remove(sword);
			}
			entity = sword;
		}
		else {
			Set<Entity> set = inventory.keySet();
			for(Entity e: set) {
				if(e.getClass().equals(entity.getClass())) {
					int value = inventory.get(e);
					value--;
					if(value == 0) {
						inventory.remove(e);
					}
					else {
						inventory.put(e, value); //in theory it should replace 
					}
				}
			}
		}
	}
	
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        Set<Entity> set = inventory.keySet();
        for(Entity entity: set) {
        	stringBuilder.append(entity.toString());
        	stringBuilder.append(" ");
        }
        stringBuilder.append(floaty);
        stringBuilder.append(" ");
        stringBuilder.append(invincible);
        return stringBuilder.toString();
    }
}
