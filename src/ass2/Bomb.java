package ass2;

import java.util.ArrayList;


public class Bomb extends Entity{
	private static final long serialVersionUID = -381504809806691495L;
	private int timer;
	private Map map;
	private Tile bombPosition;
	
	//bomb has map lol
	public Bomb(Map map, int id) {
		super(id);
		this.map = map;
		this.timer = 4;
	}
	/**
	 * 
	 * @return returns the timer
	 */
	public int getTimer() {
		return timer;
	}
	/**
	 * places a bomb at the players feet
	 */
	public void placeBomb() {
		this.bombPosition = map.getPlayerLocation();
		map.getTile(bombPosition.getX(), bombPosition.getY()).addEntity(this);
		this.timer = 3;

	}
	
	//call this maybe
	/**
	 * ticks down the bomb, if timer is at 0 itll explode
	 * @return true if not exploding false if exploded
	 */
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
	/**
	 * destroys all enemies and boulders in a cross pattern around the bomb
	 */
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
				} else if(e instanceof Player && map.getPlayer().getInvincibility() == false) {
					removedEntities.add(e);
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
	/**
	 * is the value in the map
	 * @param value
	 * @return false if it is not in the map true if it is
	 */
	private boolean valueInMap(int value) {
		if(value < 0 || value >= map.getArrayLength()) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		return "BOMB";
	}
}
