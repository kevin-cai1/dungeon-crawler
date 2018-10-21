package ass2;

import java.util.ArrayList;

public class DesignEngine {
	private Map map;
	public DesignEngine(int arrayLength) {
		ArrayList<WinCondition> arrayList = new ArrayList<>();
        MapBuilder builder = new MapBuilderImplem();
		this.map = builder.setArrayLengthMap(10).setMapEntities().setWinConditions(arrayList).setIdCounter().build();;
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
	public Map getMap() {
		return this.map;
	}
	public void setMap(Map map) {
		this.map = map;
	}
	/**
	 * 
	 * @param map
	 * @return returns true if map is valid
	 */
	public boolean validateMap(Map map) {
		
		// Iterate through the map, check for exit first
		ArrayList<WinCondition> winConditions = map.getWinConditions();
		if(winConditions.isEmpty()) {
			return false;
		}
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
		if (validatePlayer(map)) {
			return true;
		}
		System.out.println("somehow it got past all the conditions, map might or might not be valid.");
		return false;
	}
	public boolean validatePlayer(Map map) {
		boolean player = false;
		int size = map.getArrayLength();
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				for (Entity e : map.getMap()[i][j].getEntities()) { // look through every single entity
					if (e instanceof Player) { // if treasure exists
						player = true;
					}
				}
			}
		}
		return player;
	}
}
