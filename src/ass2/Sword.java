package ass2;

import java.util.ArrayList;

public class Sword extends Entity{
	private int durability;
	public Sword() {
		durability = 5;
	}
	public int getDurability() {
		return durability;
	}
	public void reduceDurability() {
		durability--;
	}
	public boolean durabilityZero() {
		return(durability == 0);
	}

	@Override
	public String toString() {
		return null;
	}
	
	public void swing(Map map, Direction direction) {
		Tile player = map.getPlayerLocation();
		ArrayList<Tile> attackedTiles = new ArrayList<Tile>();
		switch (direction) {
		case NORTH:
			if(valueInMap(player.getY() - 2)) {
				int attackedX = player.getX();
				int attackedY = player.getY() - 2;
				attackedTiles.add(map.getTile(attackedX, attackedY));
			}
			for(int i = -1; i <= 1; i++) {
				if(valueInMap(player.getX() + i)) {
					int attackedX = player.getX() + i;
					int attackedY = player.getY() - 1;
					attackedTiles.add(map.getTile(attackedX, attackedY));
				}
			}
			break;
		case SOUTH:
			if(valueInMap(player.getY() + 2)) {
				int attackedX = player.getX();
				int attackedY = player.getY() + 2;
				attackedTiles.add(map.getTile(attackedX, attackedY));
			}
			for(int i = -1; i <= 1; i++) {
				if(valueInMap(player.getX() + i)) {
					int attackedX = player.getX() + i;
					int attackedY = player.getY() + 1;
					attackedTiles.add(map.getTile(attackedX, attackedY));
				}
			}
			break;
		case EAST:
			if(valueInMap(player.getX() + 2)) {
				int attackedX = player.getX() + 2;
				int attackedY = player.getY();
				attackedTiles.add(map.getTile(attackedX, attackedY));
			}
			for(int i = -1; i <= 1; i++) {
				if(valueInMap(player.getY() + i)) {
					int attackedX = player.getX() + 1;
					int attackedY = player.getY() + i;
					attackedTiles.add(map.getTile(attackedX, attackedY));
				}
			}
			break;
		case WEST:
			if(valueInMap(player.getX() - 2)) {
				int attackedX = player.getX() - 2;
				int attackedY = player.getY();
				attackedTiles.add(map.getTile(attackedX, attackedY));
			}
			for(int i = -1; i <= 1; i++) {
				if(valueInMap(player.getY() + i)) {
					int attackedX = player.getX() - 1;
					int attackedY = player.getY() + i;
					attackedTiles.add(map.getTile(attackedX, attackedY));
				}
			}
			break;
		}
		for(Tile t : attackedTiles) {
			for(Entity e : t.getEntities()) {
				if(/*e is enemy*/) {
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
}
