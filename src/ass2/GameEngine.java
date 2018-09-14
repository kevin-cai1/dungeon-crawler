package ass2;

import java.util.ArrayList;
import java.util.concurrent.locks.Condition;

public class GameEngine {
	private Map gameMap;
	private GameState gameState;
	private boolean enemyWinCondition;
	private boolean boulderWinCondition;
	private boolean treasureWinCondition;
	private int arrayLength;
	
	public GameEngine(Map map) {
		this.gameMap = map;
		this.gameState = GameState.Menu;
		this.enemyWinCondition = false;
		this.boulderWinCondition = false;
		this.treasureWinCondition = false;
		arrayLength = gameMap.getArrayLength();

	}
	
	public GameState runGame() {
		gameState = GameState.Play;
		setWinConditions();
		// runs the game
		// gets player moves
		// calculates entity moves
		// handles win conditions
		
		Tile[][] map = gameMap.getMap();
		
		PlayerControls control = new PlayerControls(); //**instantiate control class
		Player player = gameMap.getPlayer();
		Tile playerLocation = gameMap.getPlayerLocation();
		
		int numTreasures = 0;
		for (int i = 0; i < arrayLength; i++) {
			for (int j = 0; j < arrayLength; j++) {
				for (Entity e : map[i][j].getEntities()) { // look through every single entity
					if (e instanceof Treasure) { // count all the treasures
						numTreasures++;
					}
				}
			}
		}
		while (true) { // game not won, user not ded or not quit
			
		// take user input (player control @jun)
/*			Direction playerAction = control.returnMovement();
			switch (playerAction) {
				case NORTH:
					movePlayerNorth(map, playerLocation, player);
					break;
				case SOUTH:
					movePlayerSouth(map, playerLocation, player);
					break;
				case EAST:
					movePlayerEast(map, playerLocation, player);
					break;
				case WEST:
					movePlayerWest(map, playerLocation, player);
					break;
			}*/
			boolean playerMoved = false;
			char action = control.getValidInput();
			if (control.isMovement(action) == true) {				// if the input is movement
				Direction playerAction = control.getMovement(action);
				switch (playerAction) {
					case NORTH:
						playerMoved = movePlayerNorth(map, playerLocation, player);
						break;
					case SOUTH:
						playerMoved = movePlayerSouth(map, playerLocation, player);
						break;
					case EAST:
						playerMoved = movePlayerEast(map, playerLocation, player);
						break;
					case WEST:
						playerMoved = movePlayerWest(map, playerLocation, player);
						break;
				}
			}
			else if (control.isMovement(action) == false ) {		// if the input is a weapon
				Direction aim = control.returnMovement();			// take second input to aim the weapon
				switch (action) {									// figure out which weapon is being used
				case 'q':
					// drop bomb at feet
					break;
				case 'e':
					switch (aim) {
					case NORTH:
						// swing sword up
						break;
					case EAST:
						// swing sword right
						break;
					case SOUTH:
						// swing sword down
						break;
					case WEST:
						// swing sword left
						break;
					}
					break;
				case 'r':
					switch (aim) {
					case NORTH:
						// shoot arrow up
						break;
					case EAST:
						// shoot arrow right
						break;
					case SOUTH:
						// shoot arrow down
						break;
					case WEST:
						// shoot arrowleft
						break;
					}
					break;
				}
			}
			
						
			if (gameState.equals(GameState.Win) || gameState.equals(GameState.Lose)) {
				return gameState;
			}
			
			// calculate enemy movements
			if (playerMoved) {
				moveEnemies(arrayLength, map);
			}
			if (checkWin(player, numTreasures, arrayLength, map) == true) {
				gameState = GameState.Win;
				return gameState;
			}
		}
	}
	
	public boolean movePlayerNorth(Tile[][] map, Tile playerLocation, Player player) {
		Direction playerAction = Direction.NORTH;
		if (this.validateMove(player, playerAction) == true) {
			int playerX = playerLocation.getX();
			int playerY = playerLocation.getY();
			Tile affectedTile = map[playerX][playerY-1];
			Tile followingTile = map[playerX][playerY-2];
			
			boolean movePlayer = moveConsequences(player, playerAction, affectedTile,
					followingTile);
			
			System.out.println(movePlayer);
			if (movePlayer == true) {
				gameMap.movePlayer(playerAction);	
				return true;
			}
		}
		return false;
	}
	
	public boolean movePlayerSouth(Tile[][] map, Tile playerLocation, Player player) {
		Direction playerAction = Direction.SOUTH;
		if (this.validateMove(player, playerAction) == true) {
			int playerX = playerLocation.getX();
			int playerY = playerLocation.getY();
			Tile affectedTile = map[playerX][playerY+1];
			Tile followingTile = map[playerX][playerY+2];
			
			boolean movePlayer = moveConsequences(player, playerAction, affectedTile,
					followingTile);
		
			if (movePlayer == true) {
				gameMap.movePlayer(playerAction);	
				return true;
			}
		}
		return false;
		
	}


	public boolean movePlayerEast(Tile[][] map, Tile playerLocation, Player player) {
		Direction playerAction = Direction.EAST;
		if (this.validateMove(player, playerAction) == true) {
			int playerX = playerLocation.getX();
			int playerY = playerLocation.getY();
			Tile affectedTile = map[playerX+1][playerY];
			Tile followingTile = map[playerX+2][playerY];
			
			boolean movePlayer = moveConsequences(player, playerAction, affectedTile,
					followingTile);
		
			if (movePlayer == true) {
				gameMap.movePlayer(playerAction);	
				return true;
			}
		}
		return false;
	
	}
	
	public boolean movePlayerWest(Tile[][] map, Tile playerLocation, Player player) {
		Direction playerAction = Direction.WEST;
		if (this.validateMove(player, playerAction) == true) {
			int playerX = playerLocation.getX();
			int playerY = playerLocation.getY();
			Tile affectedTile = map[playerX-1][playerY];
			Tile followingTile = map[playerX-2][playerY];
			
			boolean movePlayer = moveConsequences(player, playerAction, affectedTile,
					followingTile);
		
			if (movePlayer == true) {
				gameMap.movePlayer(playerAction);	
				return true;
			}
		}
		return false;
		
	}



	private boolean checkWin(Player player, int numTreasures, int arrayLength, Tile[][] map) {
		boolean allSwitches = true;
		boolean allEnemiesDestroyed = true;
		for (int i = 0; i < arrayLength; i++) {
			for (int j = 0; j < arrayLength; j++) {
				Tile tile = map[i][j];
				for (Entity e : tile.getEntities()) { // look through every single entity
					if (e instanceof FloorSwitch) { // every floor switch
						FloorSwitch switch1 = (FloorSwitch)e;
						if (switch1.getStatus() == false) {
							allSwitches = false;
						}
					} else if (e instanceof Enemy) {
						allEnemiesDestroyed = false;
					}
				}
			}
		}
		
		boolean satisfyWin = true;
		
		if (boulderWinCondition) {
			if (allSwitches != true) {
				satisfyWin = false;
			}
		}
		if (enemyWinCondition) {
			if (allEnemiesDestroyed != true) {
				satisfyWin = false;
			}
		}
		if (treasureWinCondition) {
			if (player.getTreasure() != numTreasures) {
				satisfyWin = false;
			}
		}
		
		return satisfyWin;
	}

	private void moveEnemies(int arrayLength, Tile[][] map) {
		boolean moveEnemy = true;
		for (int i = 0; i < arrayLength; i++) {
			for (int j = 0; j < arrayLength; j++) {
				Tile tile = map[i][j];
				for (Entity e : tile.getEntities()) { // look through every single entity
					if (e instanceof Enemy) { // every enemy that needs to move
						int enemyX = tile.getX();
						int enemyY = tile.getY();
						Direction enemyAction = ((Enemy) e).getAction();
						Tile enemyNextTile = null;
						switch (enemyAction) {
						case NORTH:
							enemyNextTile = map[enemyX][enemyY-1];
							break;
						case SOUTH:
							enemyNextTile = map[enemyX][enemyY+1];
							break;
						case EAST:
							enemyNextTile = map[enemyX+1][enemyY];
							break;
						case WEST:
							enemyNextTile = map[enemyX-1][enemyY];
							break;
						}
						
						for (Entity nextTileEntity : enemyNextTile.getEntities()) {
							if (nextTileEntity instanceof Pit) {
								tile.removeEntity(e); // remove enemy from its tile (same as walking into pit and dying)
								moveEnemy = false;
							}
						}
						if (moveEnemy) {
							gameMap.makeMove(e, enemyAction);
						}
						moveEnemy = true;
					}
				}
			}
		}
	}

	private boolean moveConsequences(Player player, Direction playerAction, Tile affectedTile, Tile followingTile) {
		boolean boulderMove = true;
		Entity pitObject = null;
		boolean movePlayer = true;
		for (Entity e: affectedTile.getEntities()) {
			if (e instanceof Bomb) { //entity is a bomb
				player.putInventory(e);
			} else if (e instanceof Boulder) { // valid boulder move checked in validateMove
				// move boulder to next tile
				for (Entity following: followingTile.getEntities()) { // for all entities in the following tile
					if ((following instanceof Obstacle)) {
						boulderMove = false; // do not move boulder
						movePlayer = false; // cannot move player if boulder doesn't move
					}
					if (following instanceof Pit) {
						pitObject = following;
					}
				}
				if (boulderMove == true) {
					if (pitObject != null) { // boulder going into pit
						// don't need to move boulder, just delete both boulder and pit - makes normal floor
						affectedTile.removeEntity(e); // remove boulder
						followingTile.removeEntity(pitObject); // remove pit
					} else {
						gameMap.makeMove(e, playerAction); //move the boulder the same direction as player
					}
				} else {
					movePlayer = false;
				}
			} else if (e instanceof InvincibilityPotion) {
				player.addInvincibility();
			} else if (e instanceof HoverPotion) {
				player.addHover();
			} else if (e instanceof Key) {
				player.addKey((Key)e);
			} else if (e instanceof Treasure) { // add treasure, win if all collected
				player.addTreasure();
			} else if (e instanceof Arrow) {
				player.putInventory(e);
			} else if (e instanceof Sword) {
				player.putInventory(e);;
			} else if (e instanceof Enemy) {	// lose if you walk into enemy
				gameState = GameState.Lose;
			} else if (e instanceof Pit) {	// lose if you walk into pit
				gameState = GameState.Lose;
			} else if (e instanceof Exit) {	// win on exit
				gameState = GameState.Win;
			} else if (e instanceof Door) { // condition when player walks into door
				Door door = (Door)e;
				if (door.getStatus() == false) { // closed
					if (player.checkKey(door) == false) {
						movePlayer = false;
					}
				} 
			}
		}
		return movePlayer;
	}
	
	// This should now work for double boulder and boulder wall. 
	// Should validate entity movements
	public boolean validateMove(Entity entity, Direction move) {
		for (int i = 0; i < arrayLength; i++) {
			for (int j = 0; j < arrayLength; j++) {
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
												for (Entity e3 : tile[i][j+2].getEntities()) {
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
	
	/*private boolean exitWinCondition() {
		for (int i = 0; i < arrayLength; i++) {
			for (int j = 0; j < arrayLength; j++) {
				for (Entity e : this.gameMap.getMap()[i][j].getEntities()) { // look through every single entity
					if (e instanceof Exit) { // count all the treasures
						return true;
					}
				}
			}
		}
		return false;
	}*/
	
	private void setWinConditions() {
		ArrayList<WinCondition> conditions = this.gameMap.getWinConditions();
		if (conditions != null) {
			if (conditions.contains(WinCondition.Boulder)) {
				this.boulderWinCondition = true;
			}
			if (conditions.contains(WinCondition.Enemy)) {
				this.enemyWinCondition = true;
			}
			if (conditions.contains(WinCondition.Treasure)) {
				this.treasureWinCondition = true;
			}
		}
	}
	
}
