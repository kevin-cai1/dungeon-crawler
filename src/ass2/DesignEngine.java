package ass2;

import java.util.ArrayList;

public class DesignEngine {
	private ArrayList<Entity> entityList;
	private Map map;
	
	public DesignEngine(int x, int y) {
		this.entityList = new ArrayList<>();
		this.map = new Map();
		
	}
	
	public void runDesignMode() {
		
	}
	
	public void placeEntity(Entity entity, int x, int y) {
		if (validatePlacement(entity, x, y)) {
			Tile[][] tile = map.getMap();
			entityList.add(entity);
			tile[x][y].addEntity(entity);
		}
	}

	public boolean validatePlacement(Entity entity, int x, int y) {
		if (x > 19 || x < 0 || y > 19 || y < 0) {
			return false;			// cannot place out of bounds
		}
		
		for (Entity e : Tile[x][y]) {
			if (e instanceof Obstacle) {
				return false; 		// cannot place entities on top of boulders or walls
			}
		}
		
		return true;
	}
}
