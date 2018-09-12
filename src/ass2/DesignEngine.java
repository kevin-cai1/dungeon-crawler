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
		Tile[][] tile = map.getMap();
		entityList.add(entity);
		tile[x][y].addEntity(entity);
	}

}
