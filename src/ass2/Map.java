package ass2;

import java.util.ArrayList;

public class Map {
	private Tile[][] map;
	public Map() {
		map = new Tile[20][20];
		//Temporary test stuff
		ArrayList<Entity> entities = new ArrayList<>();
		Arrow arrow = new Arrow();
		entities.add(arrow);
		for(int i = 0; i < 20; i++) {
			for(int j = 0; j < 20; j++) {
				map[i][j] = new Tile(entities, i, j);
			}
		}
	}
	public Tile getTile(int x, int y) {
		return map[x][y];
	}
	public Tile[][] getMap() {
		return map;
	}
	public Tile getPlayerLocation() {
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 20; j++) {
				Tile tile = map[i][j];
				for (Entity e: tile.getEntities()) {
					if (e instanceof Player) {
						return tile;
					}
				}
			}
		}
		return null;
	}
	
	public Player getPlayer() {
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 20; j++) {
				Tile tile = map[i][j];
				for (Entity e: tile.getEntities()) {
					if (e instanceof Player) {
						return (Player)e;
					}
				}
			}
		}
		return null;
	}
	
	// Moves an entity one space in a given direction
	public void makeMove(Entity entity, Direction move) {
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 20; j++) {
				Tile tile = map[i][j];
				for (Entity e : tile.getEntities()) {
					if (e.equals(entity)) {
						Entity EntityCopy = e;
						tile.removeEntity(e); // Remove entity from current tile
						// Calculate the tile it needs to move to
						Tile newtile = map[i][j];
						switch (move) {
							case NORTH: 
								newtile = map[i][j+1];
								break;
							case EAST:
								newtile = map[i+1][j];
								break;
							case SOUTH:
								newtile = map[i][j-1];
								break;
							case WEST:
								newtile = map[i-1][j];
								break;
						}
						newtile.addEntity(EntityCopy); // Add entity to new tile 
					}
				}
			}
		}
	}
	
	public void printMap() {
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 20; j++) {
				System.out.println(map[i][j].toString());
			}
		}
	}
}
