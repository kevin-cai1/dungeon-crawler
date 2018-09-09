package ass2;

import java.util.ArrayList;

public class Tile {
	private ArrayList<Integer> entities; //to replace Integer with Entity abstract class
	public Tile(ArrayList<Integer> entities) {
		this.entities = entities;
		// TODO Auto-generated constructor stub
	}
	public ArrayList<Integer> getEntities() {
		return entities;
	}
	public void addEntity(int entity) {//change from int to Entity
		entities.add(entity);
	}
	public void removeEntity(int entity) {//change from int to Entity
		entities.remove(entity);
	}
}
