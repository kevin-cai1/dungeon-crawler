package ass2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Player extends Entity{
	private HashMap<Entity, Integer> inventory;
	private ArrayList<Key> keys;
	private int invincible;
	private boolean floaty;
	private int treasure;
	
	public Player(int id) {
		super(id);
		treasure = 0;
		floaty = false;
		invincible = 0;
		inventory = new HashMap<>();
		keys = new ArrayList<>();
	}
	
	/**
	 * ticks down invincibility duration of the player if invincibility potion is active
	 * @return true if potion has been ticked down, false if remaining duration is already 0
	 */
	public boolean invincibleTick() {
		if(invincible > 0) {
			invincible--;
			return true;
		}
		return false;
	}
	
	/**
	 * increments the players treasure count in their inventory
	 */
	public void addTreasure() {
		treasure++;
	}
	/**
	 * @return returns number of treasure that the player is carrying
	 */
	public int getTreasure() {
		return treasure;
	}
	/**
	 * invinciblility will last for 10 turns
	 */
	public void addInvincibility() {
		invincible = 10;
	}
	/**
	 * returns true if player is invincible
	 * @return
	 */
	public boolean getInvincibility() {
		return (invincible != 0);
	}
	
	public int getInvincibilityTick() {
		return invincible;
	}
	
	/**
	 * adds hovering status to the player
	 */
	public void addHover() {
		floaty = true;
	}
	/**
	 * @return true if player has an active hovering status (hover potion in effect)
	 */
	public boolean getHover() {
		return floaty;
	}
	/**
	 * returns hashmap of players inventory
	 * @return
	 */
	public HashMap<Entity, Integer> getInventory() {
		return inventory;
	}
	
	public int getSwordUses() {
		Set<Entity> set = inventory.keySet();
		for(Entity e: set) {
			if(e instanceof Sword) {
				return ((Sword)e).getDurability();
			}
		}
		return 0;
	}
	
	public int getBombsLeft() {
		int bombCount = 0;
		Set<Entity> set = inventory.keySet();
		for(Entity e: set) {
			if(e instanceof Bomb) {
				bombCount++;
			}
		}
		return bombCount;
	}
	
	/**
	 * adds key to player inventory
	 * @param key key to be added
	 */
	public void addKey(Key key) {
		keys.add(key);
	}
	/**
	 * checkKey checks if the player has the key to open the door. If they do, the door will open
	 * @param door
	 * @return returns true if the player has the correct key for the door the door and has been opened
	 * returns false if the player doesn't have the correct key
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
	 * 
	 * @param entity
	 * @return true if something is put inside inventory, false if it was not successful
	 */
	public boolean putInventory(Entity entity) {
		Set<Entity> set = inventory.keySet();
		for(Entity e: set) {
			if(e instanceof Sword && entity instanceof Sword) {//if a sword exists in the set, don't do anything
				return false;
			}
			if(e.getClass().equals(entity.getClass())) {
				int value = inventory.get(e);
				value++;
				inventory.put(e, value); //should replace
				return true;
			}
		}
		inventory.put(entity, 1);
		return true;
	}
	/**
	 * checkSword will return false if there is no sword in Inventory. If there is a sword in the inventory it will return true and decrease use.
	 * @return
	 */
	public boolean checkSword() {
		Set<Entity> set = inventory.keySet();
		for(Entity e: set) {
			if(e instanceof Sword) {
				decreaseUse(e);
				return true;
			}
		}
		return false;
	}
	public boolean checkArrow() {
		Set<Entity> set = inventory.keySet();
		for(Entity e: set) {
			if(e instanceof Arrow) {
				decreaseUse(e);
				return true;
			}
		}
		return false;
	}
	public boolean checkBomb() {
		Set<Entity> set = inventory.keySet();
		for(Entity e: set) {
			if(e instanceof Bomb) {
				decreaseUse(e);
				return true;
			}
		}
		return false;
	}
	/**
	 * decrease Durability should only accept valid entities such as sword, arrow 
	 * @param entity
	 */
	public void decreaseUse(Entity entity) {
		if(entity instanceof Sword) {
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
						inventory.put(e, value); 
					}
				}
			}
		}
	}
	/**
	 * if the key has the same unique as the one inputed return true. otherwise false
	 * @param key
	 * @return
	 */
	public boolean hasKey(Key key) {
		for(Key key1: keys) {
			if(key.getUnique() == key1.getUnique()) {
				return true;
			}
		}
		return false;
	}
    @Override
    public String toString() {
    	return "+";
    }
}
