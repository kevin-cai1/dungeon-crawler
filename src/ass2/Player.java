package ass2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
//need a check if player has sword and bomb getBomb will reduce inventory count of bomb reduce durability of sword as well. need to make sure if sword is dead it is removed
//
//need a get arrow
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
		// TODO Auto-generated constructor stub
	}
	public boolean invincibleTick() {
		if(invincible > 0) {
			invincible--;
			return true;
		}
		return false;
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
				inventory.put(e, value); //in theory it should replace
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
			entity = sword; //just in case
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
       /* StringBuilder stringBuilder = new StringBuilder();
        Set<Entity> set = inventory.keySet();
        for(Entity entity: set) {
        	stringBuilder.append(entity.toString());
        	stringBuilder.append(" ");
        }
        stringBuilder.append(floaty);
        stringBuilder.append(" ");
        stringBuilder.append(invincible);
        return stringBuilder.toString();*/
    	return "+";
    }
}
