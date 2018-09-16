package ass2;

import java.util.ArrayList;

public class Bomb extends Entity{
	private int timer;
	private Map map;
	private Tile bombPosition;
	
	//bomb has map lol
	public Bomb(Map map, int id) {
		super(id);
		this.map = map;
	}
	
	public int getTimer() {
		return timer;
	}
	
	public void placeBomb() {
		this.bombPosition = map.getPlayerLocation();
		this.timer = 3;

	}
	
	//call this maybe
	public boolean tick() {
		if(getTimer() > 0) {
			timer--;
			return true;
		} else{
			explode();
			bombPosition.removeEntity(this);
			return false;
			
		}
	}
	
	private void explode() {
		int bombX = bombPosition.getX();
		int bombY = bombPosition.getY();
		ArrayList<Tile> explosionRadius = new ArrayList<Tile>();
		explosionRadius.add(map.getTile(bombX, bombY));
		if(valueInMap(bombX + 1)) {
			explosionRadius.add(map.getTile(bombX + 1, bombY));
		}
		if(valueInMap(bombX - 1)) {
			explosionRadius.add(map.getTile(bombX - 1, bombY));
		}
		if(valueInMap(bombY + 1)) {
			explosionRadius.add(map.getTile(bombX, bombY + 1));
		}
		if(valueInMap(bombY - 1)) {
			explosionRadius.add(map.getTile(bombX, bombY - 1));
		}
		ArrayList<Entity> removedEntities = new ArrayList<Entity>();
		for(Tile t : explosionRadius) {
			for(Entity e : t.getEntities()) {
				if(e instanceof Enemy || e instanceof Boulder) {/*e is enemy or boulder*/
					removedEntities.add(e);
					//t.removeEntity(e);
				}
			}
		}
		for(Tile t : explosionRadius) {
			for(Entity e : removedEntities) {
				if(t.getEntities().contains(e)) {
					t.removeEntity(e);
				}
			}
		}
	}
	private boolean valueInMap(int value) {
		if(value < 0 || value >= 20) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		/*StringBuilder sb = new StringBuilder();
		sb.append("Bomb Position [x][y]: ");
		sb.append(bombPosition.getX());
		sb.append(" ");
		sb.append(bombPosition.getY());
		sb.append("Bomb Timer: " + timer);
		return sb.toString();*/
		return "Q";
	}
}
