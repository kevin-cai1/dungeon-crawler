package ass2;

import java.io.*;
import java.util.ArrayList;

public class DesignEngine {
	private Map map;
	private GameState gameState;
	private boolean enemyWinCondition;
	private boolean boulderWinCondition;
	private boolean treasureWinCondition;
	
	public DesignEngine(int arrayLength) {
		this.map = new Map(arrayLength);
		this.enemyWinCondition = false; //kill me now
		this.boulderWinCondition = false;
		this.treasureWinCondition = false;
	}
	/**
	 * tries to place an entity at the specified position
	 * @param entity
	 * @param x
	 * @param y
	 */
	public boolean placeEntity(Entity entity, int x, int y) {
		if (validatePlacement(entity, x, y)) {
			Tile tile = map.getTile(x, y);
			tile.addEntity(entity);
			return true;
		}
		return false;
	}
	public void removeTopEntity(int x, int y) {
		Tile tile = map.getTile(x, y);
		ArrayList<Entity> entities = tile.getEntities();
		entities.remove(entities.size()-1);

	}
	/**
	 * validates whether a position is valid on the map. i.e. entities cannot be placed on top of obstacles
	 * @param entity
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean validatePlacement(Entity entity, int x, int y) {
		
		if (x > map.getArrayLength()-1 || x < 0 || y > map.getArrayLength()-1 || y < 0) {
			return false;			// cannot place out of bounds
		}
		Tile tile = map.getTile(x, y);
		//if entity is obstacle or exit can only place on empty things
		if(entity instanceof Obstacle || entity instanceof Exit) {
			if(tile.getEntities().isEmpty()) {
				return true;
			}
			return false;
		}

		for (Entity e : tile.getEntities()) {
			if (e instanceof Obstacle) {
				return false; 		// cannot place entities on top of boulders or walls
			}
			if (e instanceof Exit) {
				//cannot palce entities onto exits
				return false;
			}
		}
		return true;
	}
	/**
	 * checks if the win condition is exit
	 * @return
	 */
	private boolean exitWinCondition() {
		for (int i = 0; i < map.getArrayLength()-1; i++) {
			for (int j = 0; j < map.getArrayLength()-1; j++) {
				for (Entity e : map.getMap()[i][j].getEntities()) { // look through every single entity
					if (e instanceof Exit) { // count all the exits
						return true;
					}
				}
			}
		}
		return false;
	}
	public Map getMap() {
		return this.map;
	}
	public void setMap(Map map) {
		this.map = map;
	}
	/**
	 * 
	 * @param fileName
	 * @return map read from file
	 */
	public Map load(String fileName) {
		Map loadedMap = new Map(20);
		try {
			FileInputStream fi = new FileInputStream(new File(fileName));
			ObjectInputStream oi = new ObjectInputStream(fi);
			loadedMap = (Map) oi.readObject();
			oi.close();
			fi.close();
			return loadedMap;
		} catch (ClassNotFoundException e) {
			System.out.println("Class not found");
		} catch (IOException e) {
			System.out.println("Error initializing stream");
		}	
		return loadedMap;
	}
	/**
	 * 
	 * @param map
	 * @return returns true if map is valid
	 */
	public boolean validateMap(Map map) {
		
		boolean player = false; // used to check player exists;
		
		// Iterate through the map, check for exit first
		ArrayList<WinCondition> winConditions = map.getWinConditions();
		int size = map.getArrayLength();
		for (WinCondition winCondition: winConditions) {
			if (winCondition.equals(WinCondition.Exit)) {			// if exit win condition exists
				boolean exit = false;
				for (int i = 0; i < size; i++) {
					for (int j = 0; j < size; j++) {
						for (Entity e : map.getTile(i, j).getEntities()) { // look through every single entity
							if (e instanceof Exit) { // if exit exists
								exit = true;
							}
						}
					}
				}
				if (!exit) {
					return false;
				}
			}
			
			if (winCondition.equals(WinCondition.Enemy)) {			// if enemy win condition exists
				boolean enemy = false;
				for (int i = 0; i < size; i++) {
					for (int j = 0; j < size; j++) {
						for (Entity e : map.getTile(i,j).getEntities()) { // look through every single entity
							if (e instanceof Enemy) { // if enemy exists
								enemy = true;
							}
						}
					}
				}
				if (!enemy) {
					return false;
				}
				
			}
			//this needs both boulder and switch
			if (winCondition.equals(WinCondition.Boulder)) {			// if boulder win condition exists
				boolean boulder = false;
				boolean switche = false;
				for (int i = 0; i < size; i++) {
					for (int j = 0; j < size; j++) {
						for (Entity e : map.getMap()[i][j].getEntities()) { // look through every single entity
							if (e instanceof FloorSwitch) { // if floorswitch exists
								switche = true;
							}
							if (e instanceof Boulder) {
								boulder = true;
							}
						}
					}
				}
				if (!(boulder & switche)) {
					return false;
				}
				
			}
			if (winCondition.equals(WinCondition.Treasure)) {		// if treasure win condition exists
				boolean treasure = false;
				for (int i = 0; i < size; i++) {
					for (int j = 0; j < size; j++) {
						for (Entity e : map.getMap()[i][j].getEntities()) { // look through every single entity
							if (e instanceof Treasure) { // if treasure exists
								treasure = true;
							}
						}
					}
				}
				if (!treasure) {
					return false;
				}
			}
		}
		boolean door = false;
		boolean key = false;
		ArrayList<Door> doors = new ArrayList<>();
		ArrayList<Key> keys = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				for (Entity e : map.getMap()[i][j].getEntities()) { // look through every single entity
					if (e instanceof Door) { // if treasure exists
						doors.add((Door) e);
					}
					if(e instanceof Key) {
						keys.add((Key) e);
					}
					if (e instanceof Player) {
						player = true;
					}
				}
			}
		}
		for(Door door2: doors) {
			KeyEnum keyEnum = door2.getUnique();
			for(Key key2: keys) {
				if(key2.getUnique() == keyEnum) {
					door = true;
				}
			}
			if(!door) {
				return false;
			}
			door = false;
		}
		//Validate that doors match key
		if (player) {
			return true;
		}
		System.out.println("somehow it got past all the conditions, map might or might not be valid.");
		return false;
	}
}
