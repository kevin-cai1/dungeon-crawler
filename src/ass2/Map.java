package ass2;

import java.io.Serializable;
import java.util.ArrayList;

public class Map implements Serializable {
	private static final long serialVersionUID = 1111111111;
	private Tile[][] map;
	private ArrayList<WinCondition> winConditions;
	private int arrayLength;
	private int idCounter;
	public Map(int arrayLength) {
		winConditions = new ArrayList<>();
		this.arrayLength = arrayLength;
		map = new Tile[arrayLength][arrayLength];
		idCounter = 0;
		for(int i = 0; i < arrayLength; i++) {
			for(int j = 0; j < arrayLength; j++) {
				//Floor floor = new Floor(genID());//great id
				ArrayList<Entity> entities = new ArrayList<>();
				//entities.add(floor);
				map[i][j] = new Tile(entities, i, j);
			}
		}
	}
	/**
	 * returns arrayLength
	 * @return
	 */
	public int getArrayLength() {
		return arrayLength;
	}
	/**
	 * gets a tile from the map
	 * @param x
	 * @param y
	 * @return
	 */
	public Tile getTile(int x, int y) {
		return map[x][y];
	}
	/**
	 * returns the 2D Tile representation of the Map
	 * @return
	 */
	public Tile[][] getMap() {
		return map;
	}
	/**
	 * gets the player Tile
	 * @return
	 */
	public Tile getPlayerLocation() {
		for (int i = 0; i < arrayLength; i++) {
			for (int j = 0; j < arrayLength; j++) {
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
	/**
	 * finds an entities tile based on its id
	 * @param id
	 * @return
	 */
	public Tile getEntityLocation(int id) {
		for (int i = 0; i < arrayLength; i++) {
			for (int j = 0; j < arrayLength; j++) {
				Tile tile = map[i][j];
				for (Entity e: tile.getEntities()) {
					if (id == e.getId()) { //does this really work
						return tile;
					}
				}
			}
		}
		return null;
	}
	/**
	 * get winConditions of a map
	 * @return
	 */
	public ArrayList<WinCondition> getWinConditions() {
		return winConditions;
	}
	
	public void addWinCondition(WinCondition w) {
		winConditions.add(w);
	}
	
	/**
	 * finds the player on the map and returns it as a Player
	 * @return
	 */
	public Player getPlayer() {
		for (int i = 0; i < arrayLength; i++) {
			for (int j = 0; j < arrayLength; j++) {
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
	// move player
	
	public void movePlayer(Direction move) {
		Tile playerLocation = getPlayerLocation();
		Player player = null;
		for (Entity e : playerLocation.getEntities()) {
			if (e instanceof Player) {
				player = (Player)e;
			}
		}
			
		Player playerCopy = player;
		int playerX = playerLocation.getX();
		int playerY = playerLocation.getY();
		Tile nextTile = null;
		switch (move) {
			case NORTH: 
				nextTile = map[playerX][playerY-1];
				break;
			case EAST:
				nextTile = map[playerX+1][playerY];
				break;
			case SOUTH:
				nextTile = map[playerX][playerY+1];
				break;
			case WEST:
				nextTile = map[playerX-1][playerY];
				break;
		}
		playerLocation.removeEntity(player);
		if (nextTile != null) {
			nextTile.addEntity(playerCopy);
		}
	}
		
	// Moves an entity one space in a given direction
	public void makeMove(Entity entity, Direction move) {
		boolean entityFound = false;
		for (int i = 0; i < arrayLength; i++) {
			for (int j = 0; j < arrayLength; j++) {
				Tile tile = map[i][j];
				ArrayList<Entity> removeEntity = new ArrayList<>();
				Tile nextTile = null;
				for (Entity e : tile.getEntities()) {
					if (e.getId() == entity.getId()) {
						entityFound = true;
						Entity EntityCopy = e;
						removeEntity.add(e); // Remove entity from current tile
						// Calculate the tile it needs to move to
						System.out.println("entity location: x:" + i + "y:" + j);

						switch (move) {
							case NORTH: 
								nextTile = map[i][j-1];
								break;
							case EAST:
								nextTile = map[i+1][j];
								break;
							case SOUTH:
								nextTile = map[i][j+1];
								break;
							case WEST:
								nextTile = map[i-1][j];
								break;
						}
						nextTile.addEntity(EntityCopy); // Add entity to new tile
					}
				}
				for (Entity e1: removeEntity) {
					tile.removeEntity(e1);
				}
				if (entityFound == true) {
					return;
				}
			}
		}
	}
	/**
	 * prints out the map to stdout
	 */
	public void printMap() {
		for (int i = 0; i < arrayLength; i++) {
			for (int j = 0; j < arrayLength; j++) {
				System.out.print("["+map[j][i].toString()+"]");
			}
			System.out.println("");
		}
	}
	public int genID() {
		idCounter++;
		return idCounter;
	}
}
