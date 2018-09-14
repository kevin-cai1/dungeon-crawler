package ass2;

import java.util.ArrayList;

public class Map {
	private Tile[][] map;
	private ArrayList<WinCondition> winConditions;
	private int arrayLength;
	
	public Map(int arrayLength) {
		this.arrayLength = arrayLength;
		map = new Tile[arrayLength][arrayLength];
		//Temporary test stuff
		for(int i = 0; i < arrayLength; i++) {
			for(int j = 0; j < arrayLength; j++) {
				Floor floor = new Floor(4);//great id
				ArrayList<Entity> entities = new ArrayList<>();
				entities.add(floor);
				map[i][j] = new Tile(entities, i, j);
			}
		}
		
		
		//System.out.println(t.getEntities());
		//t.removeEntity(floor);
		//t.addEntity(player);
	}
	//Temporary map constructor for testing CURRENTLY SHOULD MAKE A 
	public Map() {
		arrayLength = 20;
		map = new Tile[arrayLength][arrayLength];
		//Temporary test stuff
		ArrayList<Entity> entities = new ArrayList<>();
		Arrow arrow = new Arrow(1,new Map(arrayLength));
		
		ArrayList<Entity> wall = new ArrayList<>();
		Wall wall2 = new Wall(0);
		
		ArrayList<Entity> hunter = new ArrayList<>();
		Hunter hunter2 = new Hunter(55);
		
		ArrayList<Entity> player = new ArrayList<>();
		Player player2 = new Player(4);
		
		player.add(player2);
		hunter.add(hunter2);
		wall.add(wall2);
		entities.add(arrow);
		for(int i = 0; i < arrayLength; i++) {
			for(int j = 0; j < arrayLength; j++) {
				if(j == 0 || i == 0) {
					map[i][j] = new Tile(wall, i, j);
				}
				else if (i == 1 && j == 6) {
					map[i][j] = new Tile(wall, i, j);
				}
				else if (i == 2 && j == 6) {
					map[i][j] = new Tile(wall, i, j);
				}
				else if (i == 3 && j == 6) {
					map[i][j] = new Tile(wall, i, j);
				}
				else if (i == 4 && j == 6) {
					map[i][j] = new Tile(wall, i, j);
				}
				else if (i == 5 && j == 6) {
					map[i][j] = new Tile(wall, i, j);
				}
				else if (i == 5 && j == 1) {
					map[i][j] = new Tile(wall, i, j);
				}
				else if (i == 5 && j == 2) {
					map[i][j] = new Tile(wall, i, j);
				}
				else if (i == 5 && j == 3) {
					map[i][j] = new Tile(wall, i, j);
				}
				else if (i == 5 && j == 4) {
					map[i][j] = new Tile(wall, i, j);
				}
				else if (i == 5 && j == 5) {
					map[i][j] = new Tile(wall, i, j);
				}
				else if (i == 9 && j == 7) {
					map[i][j] = new Tile(player, i, j);
				}
				else if (i == 2 && j == 3) {
					map[i][j] = new Tile(hunter, i, j);
				}
				else {
					map[i][j] = new Tile(entities, i, j);
				}
				
			}
		}
	}
	public int getArrayLength() {
		return arrayLength;
	}
	public Tile getTile(int x, int y) {
		return map[x][y];
	}
	public Tile[][] getMap() {
		return map;
	}
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
	public Tile getEntityLocation(int id) {
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 20; j++) {
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
	
	public ArrayList<WinCondition> getWinConditions() {
		return winConditions;
	}
	
	public void addWinCondition(WinCondition w) {
		winConditions.add(w);
	}
	

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
		for (int i = 0; i < arrayLength; i++) {
			for (int j = 0; j < arrayLength; j++) {
				Tile tile = map[i][j];
				for (Entity e : tile.getEntities()) {
					if (e.equals(entity)) {
						Entity EntityCopy = e;
						tile.removeEntity(e); // Remove entity from current tile
						// Calculate the tile it needs to move to
						switch (move) {
							case NORTH: 
								tile = map[i][j-1];
								break;
							case EAST:
								tile = map[i+1][j];
								break;
							case SOUTH:
								tile = map[i][j+1];
								break;
							case WEST:
								tile = map[i-1][j];
								break;
						}
						tile.addEntity(EntityCopy); // Add entity to new tile
					}
				}
			}
		}
	}
	
	public void printMap() {
		for (int i = 0; i < arrayLength; i++) {
			for (int j = 0; j < arrayLength; j++) {
				System.out.print("["+map[j][i].toString()+"]");
			}
			System.out.println("");
		}
	}
}
