package ass2;

import java.util.ArrayList;

public class DesignEngine {
	private ArrayList<Entity> entityList;
	private Map map;
	private GameState gameState;
	private boolean enemyWinCondition;
	private boolean boulderWinCondition;
	private boolean treasureWinCondition;
	
	public DesignEngine(int x, int y) {
		this.entityList = new ArrayList<>();
		this.map = new Map();
		this.enemyWinCondition = false; //kill me now
		this.boulderWinCondition = false;
		this.treasureWinCondition = false;
	}
	
	public Map runDesignMode() {
		while (gameState = GameState.Design) {
			
			if (/* user tries to place tile*/) {
				placeEntity(entity, x, y);
			}
			
			if (/*user decides to play */) {
				// launch play game mode with current dungeon design
				gameState = GameState.Play;
				break;
			}
		}
	}
	
	public void placeEntity(Entity entity, int x, int y) {
		if (validatePlacement(entity, x, y) == true) {
			Tile tile = map.getTile(x, y);
			entityList.add(entity);
			tile.addEntity(entity);
		}
	}

	public boolean validatePlacement(Entity entity, int x, int y) {
		
		if (x > 19 || x < 0 || y > 19 || y < 0) {
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
	
	private boolean exitWinCondition() {
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 20; j++) {
				for (Entity e : map.getMap()[i][j].getEntities()) { // look through every single entity
					if (e instanceof Exit) { // count all the exits
						return true;
					}
				}
			}
		}
		return false;
	}
	
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
