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
		for (Entity e : tile.getEntities()) {
			if (e instanceof Obstacle) {
				return false; 		// cannot place entities on top of boulders or walls
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
	 * checks what win conditions there are for the map
	 */
	private void setWinConditions() {
		if (exitWinCondition()) {
			this.enemyWinCondition = false;
			this.boulderWinCondition = false;
			this.treasureWinCondition = false;
		} else {
			for (int i = 0; i < 20; i++) {
				for (int j = 0; j < 20; j++) {
					for (Entity e : map.getMap()[i][j].getEntities()) { // look through every single entity
						if (e instanceof Treasure) { // count all the exits
							treasureWinCondition = true;
						}
						if (e instanceof Enemy) {
							enemyWinCondition = true;
						}
						if (e instanceof FloorSwitch) {
							boulderWinCondition = true;
						}
					}
				}
			}
		}
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
	
	public boolean validateMap(Map map) {
		
		int flag = 0; // used to check player exists;
		
		// Iterate through the map, check for exit first
		ArrayList<WinCondition> winConditions = map.getWinConditions();
		
		for (WinCondition winCondition: winConditions) {
			if (winCondition.equals(WinCondition.Exit)) {			// if exit win condition exists
				int exit = 0;
				for (int i = 0; i < 20; i++) {
					for (int j = 0; j < 20; j++) {
						for (Entity e : map.getMap()[i][j].getEntities()) { // look through every single entity
							if (e instanceof Exit) { // if exit exists
								exit = 1;
							}
							if (e instanceof Player) {
								flag = 1;
							}
						}
					}
				}
				if (exit == 0) {
					return false;
				}
			}
			
			if (winCondition.equals(WinCondition.Enemy)) {			// if enemy win condition exists
				int enemy = 0;
				for (int i = 0; i < 20; i++) {
					for (int j = 0; j < 20; j++) {
						for (Entity e : map.getMap()[i][j].getEntities()) { // look through every single entity
							if (e instanceof Enemy) { // if enemy exists
								enemy = 1;
							}
							if (e instanceof Player) {
								flag = 1;
							}
						}
					}
				}
				if (enemy == 0) {
					return false;
				}
				
			}
			if (winCondition.equals(winCondition.Boulder)) {			// if boulder win condition exists
				int boulder = 0;
				for (int i = 0; i < 20; i++) {
					for (int j = 0; j < 20; j++) {
						for (Entity e : map.getMap()[i][j].getEntities()) { // look through every single entity
							if (e instanceof FloorSwitch) { // if floorswitch exists
								boulder = 1;
							}
							if (e instanceof Player) {
								flag = 1;
							}
						}
					}
				}
				if (boulder == 0) {
					return false;
				}
				
			}
			if (winCondition.equals(winCondition.Treasure)) {		// if treasure win condition exists
				int treasure = 0;
				for (int i = 0; i < 20; i++) {
					for (int j = 0; j < 20; j++) {
						for (Entity e : map.getMap()[i][j].getEntities()) { // look through every single entity
							if (e instanceof Treasure) { // if treasure exists
								treasure = 1;
							}
							if (e instanceof Player) {
								flag = 1;
							}
						}
					}
				}
				if (treasure == 0) {
					return false;
				}
			}
		}

		if (flag == 1) {
			return true;
		}
		System.out.println("somehow it got past all the conditions, map might or might not be valid.");
		return false;
	}
}
