package ass2;

import java.util.concurrent.locks.Condition;


public class GameEngine {
	private Map gameMap = new Map();
	private GameState gameState;
	private boolean enemyWinCondition;
	private boolean boulderWinCondition;
	private boolean treasureWinCondition;
	
	public GameEngine() {
		this.gameMap = gameMap.generateMap();
		this.gameState = GameState.Menu;
		this.enemyWinCondition = false;
		this.boulderWinCondition = false;
		this.treasureWinCondition = false;
	}
	
	public GameState runGame() {
		gameState = GameState.Play;
		setWinConditions();
		// runs the game
		// gets player moves
		// calculates entity moves
		// handles win conditions
		
		// initialise the map
		
		Tile[][] map = gameMap.getMap();
		
		PlayerControls control = new PlayerControls(); //**instantiate control class
		Player player = gameMap.getPlayer();
		Tile playerLocation = gameMap.getPlayerLocation();
		int numTreasures = 0;
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 20; j++) {
				for (Entity e : map[i][j].getEntities()) { // look through every single entity
					if (e instanceof Treasure) { // count all the treasures
						numTreasures++;
					}
				}
			}
		}
		
		
		while (true) { // game not won, user not ded or not quit
			boolean movePlayer = true;
			boolean boulderMove = true;
			Entity pitObject = null;
		// take user input (player control @jun)
			Direction playerAction = control.getAction();
			if (this.validateMove(player, playerAction) == true) {
				// change things being affected by the player (e.g. boulders, bombs)
				int playerX = playerLocation.getX();
				int playerY = playerLocation.getY();
				Tile affectedTile;
				Tile followingTile;
				switch (playerAction) {
					case NORTH:
						affectedTile = map[playerX][playerY-1];
						followingTile = map[playerX][playerY-2];
						break;
					case SOUTH:
						affectedTile = map[playerX][playerY+1];
						followingTile = map[playerX][playerY+2];
						break;
					case EAST:
						affectedTile = map[playerX+1][playerY];
						followingTile = map[playerX+2][playerY];
						break;
					case WEST:
						affectedTile = map[playerX-1][playerY];
						followingTile = map[playerX-2][playerY];
						break;
				}
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
						return gameState;
					} else if (e instanceof Pit) {	// lose if you walk into pit
						gameState = GameState.Lose;
						return gameState;
					} else if (e instanceof Exit) {	// win on exit
						gameState = GameState.Win;
						return gameState;					
					} else if (e instanceof Door) { // condition when player walks into door
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
							
				// calculate enemy movements
				boolean moveEnemy = true;
				for (int i = 0; i < 20; i++) {
					for (int j = 0; j < 20; j++) {
						Tile tile = map[i][j];
						for (Entity e : tile.getEntities()) { // look through every single entity
							if (e instanceof Enemy) { // every enemy that needs to move
								int enemyX = tile.getX();
								int enemyY = tile.getY();
								Direction enemyAction = ((Enemy) e).getAction();
								Tile enemyNextTile;
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
							}
						}
					}
				}
			
			}
			// lose conditions:
			/* 	player dies to enemy
			  	player falls in pit
			  	dies to bomb
			*/

			// check win conditions
			// Player standing on exit = win (checked when player moves)
			// Player collects all treasure = win (checked when player collects treasure)
			// Player triggers all floor switches
			// All enemies destroyed
			
			boolean allSwitches = true;
			boolean allEnemiesDestroyed = true;
			for (int i = 0; i < 20; i++) {
				for (int j = 0; j < 20; j++) {
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
			
			if (satisfyWin) {
				gameState = GameState.Win;
				return gameState;
			}
		}
	}
	
	// This function doesn't work for players trying to move boulders. It will see the player
	// Trying to move onto a boulder (obstacle instance) and return false immediately
	// So either needs to be adjusted or separate validation for moving boulders required
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
											return false;
										}
									}
								}
								break;
						}
					}
				}
			}
		}
		return false;
	}
	
	private boolean exitWinCondition() {
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 20; j++) {
				for (Entity e : this.gameMap.getMap()[i][j].getEntities()) { // look through every single entity
					if (e instanceof Exit) { // count all the treasures
						return true;
					}
				}
			}
		}
		return false;
	}
	
	private void setWinConditions() {
		if (exitWinCondition()) {
			this.enemyWinCondition = false;
			this.boulderWinCondition = false;
			this.treasureWinCondition = false;
		} else {
			// win conditions can be determined, one or more of the following
		}
	}
	
}
