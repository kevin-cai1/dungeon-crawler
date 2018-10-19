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

}
