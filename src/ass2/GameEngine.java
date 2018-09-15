package ass2;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;

import javax.naming.TimeLimitExceededException;

import org.junit.jupiter.params.shadow.com.univocity.parsers.common.record.AbstractRecordFactory;

public class GameEngine {
	private Map gameMap;
	private GameState gameState;
	private boolean enemyWinCondition;
	private boolean boulderWinCondition;
	private boolean treasureWinCondition;
	private int arrayLength;
	private ArrayList<Bomb> tickingBombs;
	private boolean invincibility;
	
	public GameEngine(Map map) {
		this.gameMap = map;
		this.gameState = GameState.Menu;
		this.enemyWinCondition = false;
		this.boulderWinCondition = false;
		this.treasureWinCondition = false;
		this.invincibility = false;
		arrayLength = gameMap.getArrayLength();

	}
	
	public GameState getGameState() {
		return gameState;
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
					if (player.checkBomb()) {
						Bomb placedBomb = new Bomb(gameMap,gameMap.genID());
						placedBomb.placeBomb();
						tickingBombs.add(placedBomb);
					}
					break;
				case 'e':
					if (player.checkSword()) {
						swing(aim);
					}
					break;
				case 'r':
					if (player.checkArrow()) {
						Arrow arrow = new Arrow(gameMap.genID(), gameMap);
						arrow.shootArrow(aim);
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
			
			checkPlayerStatus(gameMap, player);
			
			for (Bomb bomb : tickingBombs) { // tick every bomb ,remove when it explodes
				if (bomb.tick() == false) {
					tickingBombs.remove(bomb);
				}
			}
			
			if (invincibility == true) {
				if (player.invincibleTick() == false) { //invincibility tick, false when doesn't tick (no more invincibility)
					invincibility = false;
				}
			}
			
			if (checkWin(player, numTreasures, arrayLength, map) == true) {
				gameState = GameState.Win;
				return gameState;
			}
		}
	}
	
	public boolean movePlayerNorth(Tile[][] map, Tile playerLocation, Player player) {
		Direction playerAction = Direction.NORTH;
		System.out.println("trying to move player");
		if (this.validateMove(player, playerAction) == true) {
			System.out.println("valid move");
			int playerX = playerLocation.getX();
			int playerY = playerLocation.getY();
			Tile affectedTile = map[playerX][playerY-1];
			Tile followingTile = map[playerX][playerY-2];
	
			boolean movePlayer = moveConsequences(player, playerAction, affectedTile,
					followingTile);
			 
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
		for (int i = 0; i < arrayLength; i++) {
			for (int j = 0; j < arrayLength; j++) {
				Tile tile = map[i][j];
				for (Entity e : tile.getEntities()) { // look through every single entity
					if (e instanceof Enemy) { // every enemy that needs to move
						((Enemy) e).getAction(gameMap); // validation on enemy side
					}
				}
			}
		}
	}

	private boolean moveConsequences(Player player, Direction playerAction, Tile affectedTile, Tile followingTile) {
		boolean boulderMove = true;
		Entity pitObject = null;
		boolean movePlayer = true;
		ArrayList<Entity> removeEntities = new ArrayList<>();
		for (Entity e: affectedTile.getEntities()) {
			if (e instanceof Bomb) { //entity is a bomb
				System.out.println("pick up bomb");
				System.out.println("put in inventory:" + player.putInventory((Bomb)e));
				removeEntities.add(e);
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
						removeEntities.add(e); // remove boulder
						followingTile.removeEntity(pitObject); // remove pit
					} else {
						gameMap.makeMove(e, playerAction); //move the boulder the same direction as player
					}
				} else {
					movePlayer = false;
				}
			} else if (e instanceof InvincibilityPotion) {
				player.addInvincibility();
				removeEntities.add(e);
			} else if (e instanceof HoverPotion) {
				player.addHover();
				removeEntities.add(e);
			} else if (e instanceof Key) {
				player.addKey((Key)e);
				removeEntities.add(e);
			} else if (e instanceof Treasure) { // add treasure, win if all collected
				player.addTreasure();
			} else if (e instanceof Arrow) {
				player.putInventory(e);
				removeEntities.add(e);
			} else if (e instanceof Sword) {
				if (player.putInventory(e) == true) {
					removeEntities.add(e);
				}
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
		// remove entities
		for (Entity e: removeEntities) {
			affectedTile.removeEntity(e);
		}
		return movePlayer;
	}
	
	public void swing(Direction direction) {
		Tile player = gameMap.getPlayerLocation();
		ArrayList<Tile> attackedTiles = new ArrayList<Tile>();
		switch (direction) {
			case NORTH:
				if(valueInMap(player.getY() - 2)) {
					int attackedX = player.getX();
					int attackedY = player.getY() - 2;
					attackedTiles.add(gameMap.getTile(attackedX, attackedY));
				}
				for(int i = -1; i <= 1; i++) {
					if(valueInMap(player.getX() + i)) {
						int attackedX = player.getX() + i;
						int attackedY = player.getY() - 1;
						attackedTiles.add(gameMap.getTile(attackedX, attackedY));
					}
				}
				break;
			case SOUTH:
				if(valueInMap(player.getY() + 2)) {
					int attackedX = player.getX();
					int attackedY = player.getY() + 2;
					attackedTiles.add(gameMap.getTile(attackedX, attackedY));
				}
				for(int i = -1; i <= 1; i++) {
					if(valueInMap(player.getX() + i)) {
						int attackedX = player.getX() + i;
						int attackedY = player.getY() + 1;
						attackedTiles.add(gameMap.getTile(attackedX, attackedY));
					}
				}
				break;
			case EAST:
				if(valueInMap(player.getX() + 2)) {
					int attackedX = player.getX() + 2;
					int attackedY = player.getY();
					attackedTiles.add(gameMap.getTile(attackedX, attackedY));
				}
				for(int i = -1; i <= 1; i++) {
					if(valueInMap(player.getY() + i)) {
						int attackedX = player.getX() + 1;
						int attackedY = player.getY() + i;
						attackedTiles.add(gameMap.getTile(attackedX, attackedY));
					}
				}
				break;
			case WEST:
				if(valueInMap(player.getX() - 2)) {
					int attackedX = player.getX() - 2;
					int attackedY = player.getY();
					attackedTiles.add(gameMap.getTile(attackedX, attackedY));
				}
				for(int i = -1; i <= 1; i++) {
					if(valueInMap(player.getY() + i)) {
						int attackedX = player.getX() - 1;
						int attackedY = player.getY() + i;
						attackedTiles.add(gameMap.getTile(attackedX, attackedY));
					}
				}
				break;
		}
		for(Tile t : attackedTiles) {
			for(Entity e : t.getEntities()) {
				if(e instanceof Enemy) {
					t.removeEntity(e);
				}
			}
		}
	}
	
	private boolean valueInMap(int value) {
		if(value < 0 || value >= arrayLength) {
			return false;
		}
		return true;
	}
	
	// This should now work for double boulder and boulder wall. 
	// Should validate entity movements
	public boolean validateMove(Entity entity, Direction move) {
		Tile tile = gameMap.getEntityLocation(entity.getId());
		System.out.println(entity.getId());
		int tileX = tile.getX();
		int tileY = tile.getY();
		Tile[][] entityLocation = gameMap.getMap();
		// Calculate the tile it needs to move to
		switch (move) {
			case NORTH: 
				if ( tileY == 0) {
					return false;
				}
				else {
					for (Entity e2 : entityLocation[tileX][tileY-1].getEntities()) {
						if (e2 instanceof Obstacle) {
							if (e2 instanceof Door) {
								return true;
							}
							if (e2 instanceof Boulder) {
								if (tileY < 2) { 		// account for double boulder or boulder wall
									return false;
								}
								for (Entity e3 : entityLocation[tileX][tileY-2].getEntities()) {
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
				if (tileX == 19) {
					return false;
				}
				else {
					for (Entity e2 : entityLocation[tileX+1][tileY].getEntities()) {
						if (e2 instanceof Obstacle) {
							if (e2 instanceof Door) {
								return true;
							}
							if (e2 instanceof Boulder) {
								if (tileX > 17) {
									return false;
								}
								for (Entity e3 : entityLocation[tileX+2][tileY].getEntities()) {
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
				if (tileY == 19) {
					return false;
				}
				else {
					for (Entity e2 : entityLocation[tileX][tileY+1].getEntities()) {
						if (e2 instanceof Obstacle) {
							if (e2 instanceof Door) {
								return true;
							}
							if (e2 instanceof Boulder) {
								if (tileY > 17) {
									return false;
								}
								for (Entity e3 : entityLocation[tileX][tileY+2].getEntities()) {
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
				if (tileX == 0) {
					return false;
				}
				else {
					for (Entity e2 : entityLocation[tileX-1][tileY].getEntities()) {
						if (e2 instanceof Obstacle) {
							if (e2 instanceof Door) {
								return true;
							}
							if (e2 instanceof Boulder) {
								if (tileX < 2) {
									return false;
								}
								for (Entity e3 : entityLocation[tileX-2][tileY].getEntities()) {
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
		System.out.println("validate move is fked");
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
	
	// returns false on bad player status (dead)
	private boolean checkPlayerStatus(Map map, Player player) {
		Tile playerLocation = map.getPlayerLocation();
		if (player.getInvincibility() == false) {
			for (Entity e : playerLocation.getEntities()) {
				if (e instanceof Enemy) {
					return false;
				}
			}
		}
		return true;
	}
}
