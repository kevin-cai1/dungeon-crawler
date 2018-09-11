package ass2;

import java.util.concurrent.locks.Condition;

public class GameEngine {
	Map gameMap = new Map();
	GameState gameState;
	
	public GameEngine() {
		this.gameMap = gameMap.generateMap();
		this.gameState = GameState.Menu;
	}
	
	public GameState runGame() {
		gameState = GameState.Play;
		// runs the game
		// gets player moves
		// calculates entity moves
		// handles win conditions
		
		// initialise the map
		
		Tile[][] map = gameMap.getMap();
		
		PlayerControls control = new PlayerControls(); //**instantiate control class
		Player player = gameMap.getPlayer();
		Tile playerLocation = gameMap.getPlayerLocation();
		boolean movePlayer = true;
			
		while (/* game not won, user not ded or not quit*/) {
		// take user input (player control @jun)
			Direction playerAction = control.getAction();
			if (this.validateMove(player, playerAction) == true) {
				// change things being affected by the player (e.g. boulders, bombs)
				int playerX = playerLocation.getX();
				int playerY = playerLocation.getY();
				Tile affectedTile;
				switch (playerAction) {
					case NORTH:
						affectedTile = map[playerX][playerY-1];
						break;
					case SOUTH:
						affectedTile = map[playerX][playerY+1];
						break;
					case EAST:
						affectedTile = map[playerX+1][playerY];
						break;
					case WEST:
						affectedTile = map[playerX-1][playerY];
						break;
				}
				for (Entity e: affectedTile.getEntities()) {
					if (e.getClass().equals(new Bomb().getClass())) { //entity is a bomb
						player.putInventory(e);
					} else if (e.getClass().equals(new Boulder().getClass())) {
						
					} else if (e.getClass().equals(new InvincibilityPotion().getClass())) {
						player.addInvincibility();
					} else if (e.getClass().equals(new HoverPotion().getClass())) {
						player.addHover();
					} else if (e.getClass().equals(new Key().getClass())) {
						player.addKey((Key)e);
					} else if (e.getClass().equals(new Treasure().getClass())) {
						player.addTreasure();
					} else if (e.getClass().equals(new Arrow().getClass())) {
						player.putInventory(e);
					} else if (e.getClass().equals(new Sword().getClass())) {
						player.putInventory(e);;
					} else if (e instanceof Enemy) {	// lose if you walk into enemy
						gameState = GameState.Lose;
						return gameState;
					} else if (e.getClass().equals(new Pit().getClass())) {	// lose if you walk into pit
						gameState = GameState.Lose;
						return gameState;
					} else if (e.getClass().equals(new Door().getClass())) {
						Door door = (Door)e;
						if (door.getStatus() == false) { // closed
							if (player.checkKey(door) == false) {
								movePlayer = false;
								
							}
						} 
					}
				}
				if (movePlayer == true) {
					gameMap.makeMove(player, playerAction);	
				}
							
						
				// calculate entity movements
				for (int i = 0; i < 20; i++) {
					for (int j = 0; j < 20; j++) {
						Tile tile = map[i][j];
						for (Entity e : tile.getEntities()) { // look through every single entity
							if (e instanceof Enemy) { // every enemy that needs to move
								Direction action = ((Enemy) e).getAction();
								gameMap.makeMove(e, action);
							}
						}
					}
				}
			
			}

		// check win conditions
			// Player standing on exit = win
			playerLocation = gameMap.getPlayerLocation();
			for (Entity e : playerLocation.getEntities()) {
				if (e instanceof Enemy) {
					return GameState.Lose;
				}
				if (e instanceof Pit) {
					return GameState.Lose;
				}
				if (e instanceof Exit) {
					return GameState.Win;
				}
			}
			
			
			
		}
	}
	
	// This should now work for double boulder and boulder wall. 
	// Should validate entity movements
	public boolean validateMove(Entity entity, Direction move) {
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 20; j++) {
				Tile[][] tile = gameMap.getMap();
				for (Entity e : tile[i][j].getEntities()) {
					if (e.equals(entity)) {
						// Calculate the tile it needs to move to
						switch (move) {
							case NORTH: 
								if (j == 0) {
									return false;
								}
								else {
									for (Entity e2 : tile[i][j-1].getEntities()) {
										if (e2 instanceof Obstacle) {
											if (e2 instanceof Door) {
												return true;
											}
											if (e2 instanceof Boulder) {
												if (j < 2) { 		// account for double boulder or boulder wall
													return false;
												}
												for (Entity e3 : tile[i][j-2].getEntities()) {
													if (e3 instanceof Obstacle) {
														return false;
													}
												}
											}
											return false;
										}
									}
								}
								break;
							case EAST:
								if (i == 19) {
									return false;
								}
								else {
									for (Entity e2 : tile[i+1][j].getEntities()) {
										if (e2 instanceof Obstacle) {
											if (e2 instanceof Door) {
												return true;
											}
											if (e2 instanceof Boulder) {
												if (i > 17) {
													return false;
												}
												for (Entity e3 : tile[i+2][j].getEntities()) {
													if (e3 instanceof Obstacle) {
														return false;
													}
												}
											}
											return false;
										}
									}
								}
								break;
							case SOUTH:
								if (j == 19) {
									return false;
								}
								else {
									for (Entity e2 : tile[i][j+1].getEntities()) {
										if (e2 instanceof Obstacle) {
											if (e2 instanceof Door) {
												return true;
											}
											if (e2 instanceof Boulder) {
												if (j > 17) {
													return false;
												}
												for (Entity e3 : tile[i][j+2]) {
													if (e3 instanceof Obstacle) {
														return false;
													}
												}
											}
											return false;
										}
									}
								}
								break;
							case WEST:
								if (i == 0) {
									return false;
								}
								else {
									for (Entity e2 : tile[i-1][j].getEntities()) {
										if (e2 instanceof Obstacle) {
											if (e2 instanceof Door) {
												return true;
											}
											if (e2 instanceof Boulder) {
												if (i < 2) {
													return false;
												}
												for (Entity e3 : tile[i-2][j].getEntities()) {
													if (e3 instanceof Obstacle) {
														return false;
													}
												}
											}
											return false;
										}
									}
								}
								break;
						}
						return true;
					}
				}
			}
		}
		return true; // It shouldnt reach this one ever, this is just to make compiler happy
	}
	

	
}
