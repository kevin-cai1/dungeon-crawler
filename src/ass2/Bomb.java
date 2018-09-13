package ass2;

import java.util.ArrayList;
import java.util.Set;

public class Bomb extends Entity{
	private int timer;
	private Map map;
	Tile bombPosition;
	
	//bomb has map lol
	public Bomb(Map map) {
		map = this.map;
		bombPosition = map.getPlayerLocation();
		timer = 3;
	}
	
	private int getTimer() {
		return timer;
	}
	//call this maybe
	public void tick() {
		if(getTimer() > 0) {
			timer--;
		} else{
			explode();
			bombPosition.removeEntity(this);
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
		for(Tile t : explosionRadius) {
			for(Entity e : t.getEntities()) {
				if(true) {/*e is enemy or boulder*/
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
		StringBuilder sb = new StringBuilder();
		sb.append("Bomb Position [x][y]: ");
		sb.append(bombPosition.getX());
		sb.append(" ");
		sb.append(bombPosition.getY());
		sb.append("Bomb Timer: " + timer);
		return sb.toString();
	}
}
