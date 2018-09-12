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
}
