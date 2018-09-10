package ass2;

import java.util.ArrayList;

public class Tile {
	private ArrayList<Integer> entities; //to replace Integer with Entity abstract class
	private int x; //stores the x position of where you are on the map
	private int y; //stores the y position of where you are on the map
	public Tile(ArrayList<Integer> entities, int x, int y) {
		this.entities = entities;
		this.x = x;
		this.y = y;
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
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < entities.size(); i++) {
			stringBuilder.append(entities.get(i).toString());
		}
		return stringBuilder.toString();
	}
}
