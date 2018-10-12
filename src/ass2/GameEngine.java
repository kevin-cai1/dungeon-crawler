package ass2;

import java.util.ArrayList;

public class GameEngine {
	private Map gameMap;
	private GameState gameState;
	private boolean enemyWinCondition;
	private boolean boulderWinCondition;
	private boolean treasureWinCondition;
	private int arrayLength;
	private ArrayList<Bomb> tickingBombs;
	private boolean invincibility;
	private int numTreasures;
	
	public GameEngine(Map map) {
		this.gameMap = map;
		this.gameState = GameState.Play;
		this.enemyWinCondition = false;
		this.boulderWinCondition = false;
		this.treasureWinCondition = false;
		this.invincibility = false;
		this.arrayLength = gameMap.getArrayLength();
		this.numTreasures = getNumTreasures();
		setWinConditions();
		System.out.println(this.boulderWinCondition + "enemy" + this.enemyWinCondition + "treasure" + this.treasureWinCondition);
	}
	
	/**
	 * returns the gamestate
	 * @return gameState (can be Paused, Win, Play, Quit, Menu, Lose, Design)
	 */
	public GameState getGameState() {
		return gameState;
	}
	
	private int getNumTreasures() {
		int numTreasures = 0;
		for (int i = 0; i < arrayLength; i++) {
			for (int j = 0; j < arrayLength; j++) {
				for (Entity e : gameMap.getMap()[i][j].getEntities()) { // look through every single entity
					if (e instanceof Treasure) { // count all the treasures
						numTreasures++;
					}
				}
			}
		}
		return numTreasures;
	}
	
	public Map getGameMap() {
		return gameMap;
	}
	
	/**
	 * runs the game in 'play' mode (game mode 1)
	 * @return the gameState (can be win, lose, play depending on how the player moved)
	 */
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
		
		
		while (true) { // game not won, user not ded or not quit
			boolean playerMoved = false;
			char action = control.getValidInput();
			if (control.isMovement(action) == true) {				// if the input is movement
				Direction playerAction = control.getMovement(action);
				switch (playerAction) {
					case NORTH:
						playerMoved = movePlayerNorth();
						break;
					case SOUTH:
						playerMoved = movePlayerSouth();
						break;
					case EAST:
						playerMoved = movePlayerEast();
						break;
					case WEST:
						playerMoved = movePlayerWest();
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
				if(player.getInvincibility()) {
					runEnemies(arrayLength, map);
				}
				else {
					moveEnemies(arrayLength, map);
				}
				
			}
			
			checkPlayerStatus();
			
			for (Bomb bomb : tickingBombs) { // tick every bomb ,remove when it explodes
				if (bomb.tick() == false) {
					tickingBombs.remove(bomb);
					if(gameMap.getPlayer() == null) {
						gameState = GameState.Lose;
						return gameState;
					}
				}
			}
			
			if (invincibility == true) {
				if (player.invincibleTick() == false) { //invincibility tick, false when doesn't tick (no more invincibility)
					invincibility = false;
				}
			}
			
			if (checkWin() == true) {
				gameState = GameState.Win;
				return gameState;
			}
		}
	}
	
	public GameState tickEffects() {
		if (tickingBombs != null) {
			for (Bomb bomb : tickingBombs) { // tick every bomb ,remove when it explodes
				if (bomb.tick() == false) {
					tickingBombs.remove(bomb);
					if(gameMap.getPlayer() == null) {
						gameState = GameState.Lose;
						return gameState;
					}
				}
			}
		}
		
		if (invincibility == true) {
			if (gameMap.getPlayer().invincibleTick() == false) { //invincibility tick, false when doesn't tick (no more invincibility)
				invincibility = false;
			}
		}
		return gameState;
	}
	
	/**
	 * moves the player up one tile
	 * calls validateMove and moveConsequences to ensure the move is valid
	 * and the rest of the map and entities perform actions based on the players move
	 * @param map
	 * @param playerLocation initial location of the player
	 * @param player
	 * @return true if the player has been successfully moved up one tile
	 */
	public boolean movePlayerNorth() {
		Tile[][] map = gameMap.getMap();
		Tile playerLocation = gameMap.getPlayerLocation();
		Player player = gameMap.getPlayer();
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
			System.out.println("move player:"+movePlayer);

			if (movePlayer == true) {
				gameMap.movePlayer(playerAction);	
				return true;
			}
		}
		return false;
	}
	
	/**
	 * moves the player down one tile
	 * calls validateMove and moveConsequences to ensure the move is valid
	 * and the rest of the map and entities perform actions based on the players move
	 * @param map
	 * @param playerLocation initial location of the player
	 * @param player
	 * @return true if the player has been successfully moved down one tile
	 */
	public boolean movePlayerSouth() {
		Tile[][] map = gameMap.getMap();
		Tile playerLocation = gameMap.getPlayerLocation();
		Player player = gameMap.getPlayer();
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

	/**
	 * moves the player right one tile
	 * calls validateMove and moveConsequences to ensure the move is valid
	 * and the rest of the map and entities perform actions based on the players move
	 * @param map
	 * @param playerLocation initial location of the player
	 * @param player
	 * @return true if the player has been successfully moved right one tile
	 */
	public boolean movePlayerEast() {
		Tile[][] map = gameMap.getMap();
		Tile playerLocation = gameMap.getPlayerLocation();
		Player player = gameMap.getPlayer();
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
	
	/**
	 * moves the player left one tile
	 * calls validateMove and moveConsequences to ensure the move is valid
	 * and the rest of the map and entities perform actions based on the players move
	 * @param map
	 * @param playerLocation initial location of the player
	 * @param player
	 * @return true if the player has been successfully moved left one tile
	 */
	public boolean movePlayerWest() {
		Tile[][] map = gameMap.getMap();
		Tile playerLocation = gameMap.getPlayerLocation();
		Player player = gameMap.getPlayer();
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
	
	/**
	 * Calls boudlerWincondition, enemyWincondition, and treasureWincondition to check if win conditions have been met
	 * iterates through the map to check if enemies still alive or if switches still untriggered
	 * @param player
	 * @param numTreasures number of treasures on the map
	 * @param arrayLength size of the map
	 * @param map
	 * @return true if player has met win conditions 
	 */
	public boolean checkWin() {
		Player player = gameMap.getPlayer();
		Tile[][] map = gameMap.getMap();
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
						System.out.println("found enemy");
						allEnemiesDestroyed = false;
					}
				}
			}
		}
		
		boolean satisfyWin;
		
		if (boulderWinCondition || enemyWinCondition || treasureWinCondition) {
			satisfyWin = true;
		} else {
			satisfyWin = false;
		}
		if (boulderWinCondition) {
			if (allSwitches != true) {
				satisfyWin = false;
			}
		}
		if (enemyWinCondition) {
			System.out.println("enemy win condition set");
			if (allEnemiesDestroyed != true) {
				satisfyWin = false;
			}
		}
		if (treasureWinCondition) {
			System.out.println("treasure win condition set");
			if (player.getTreasure() != numTreasures) {
				satisfyWin = false;
			}
		}
		if (satisfyWin == true) {
			gameState = GameState.Win;
		}
		return satisfyWin;
	}

	/**
	 * iterates through all entities on the map and calls getAction on enemies to make them move
	 * @param arrayLength map size
	 * @param map
	 */
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
	private void runEnemies(int arrayLength, Tile[][] map) {
		for (int i = 0; i < arrayLength; i++) {
			for (int j = 0; j < arrayLength; j++) {
				Tile tile = map[i][j];
				for (Entity e : tile.getEntities()) { // look through every single entity
					if (e instanceof Enemy) { // every enemy that needs to move
						((Enemy) e).runAway(gameMap); // validation on enemy side
					}
				}
			}
		}
	}

	/**
	 * checks the consequences of the player making a certain move and 
	 * performs actions according to interactions between the players move and affected entities
	 * @param player
	 * @param playerAction direction the player is moving
	 * @param affectedTile the tile the player is moving onto
	 * @param followingTile the tile behind the affected tile in the same direction
	 * @return true if all consequences accounted for and player has been validly moved
	 */
	private boolean moveConsequences(Player player, Direction playerAction, Tile affectedTile, Tile followingTile) {
		boolean boulderMove = true;
		Entity pitObject = null;
		boolean movePlayer = true;
		ArrayList<Entity> removeEntities = new ArrayList<>();
		ArrayList<Entity> moveEntity = new ArrayList<>();
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
					} else if (following instanceof FloorSwitch) {
						((FloorSwitch) following).triggerSwitch();
					}
				}
				for (Entity floorSwitch: affectedTile.getEntities()) { // if there is a floor switch on the same tile as the boulder - boulder moving off floor switch
					if (floorSwitch instanceof FloorSwitch) {
						((FloorSwitch)floorSwitch).untriggerSwitch();
					}
				}
				System.out.println("moving boulder:" + boulderMove);
				System.out.println(pitObject);
				if (boulderMove == true) {
					if (pitObject != null) { // boulder going into pit
						// don't need to move boulder, just delete both boulder and pit - makes normal floor
						removeEntities.add(e); // remove boulder
						followingTile.removeEntity(pitObject); // remove pit
					
					} else {
						moveEntity.add(e); //move the boulder the same direction as player
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
				System.out.println("PLAYER STANDING ON TREASURE");
				player.addTreasure();
				removeEntities.add(e);
				System.out.println("player has: " + player.getTreasure() + "treasures");
				System.out.println("map has: " + numTreasures + "treasures");
			} else if (e instanceof Arrow) {
				player.putInventory(e);
				removeEntities.add(e);
			} else if (e instanceof Sword) {
				if (player.putInventory(e) == true) {
					removeEntities.add(e);
				}
			} else if (e instanceof Enemy) {	// lose if you walk into enemy
				if (player.getInvincibility() == true) { // enemy dies if player walks into them with invincibility
					removeEntities.add(e);
				} else {
					gameState = GameState.Lose;
				}
			} else if (e instanceof Pit) {	// lose if you walk into pit
				if (player.getHover() == false) {
					gameState = GameState.Lose;
				}
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
		for (Entity e: moveEntity) {
			System.out.println("moving " + e.getClass() );
			gameMap.makeMove(e, playerAction);
		}
		return movePlayer;
	}
	
	/**
	 * swings sword in a certain direction, destroying all enemies one tile away in a 3 tile long line perpendicular to the direction of the swing
	 * similar to a T shape 
	 * @param direction direction the sword is swung at
	 */
	public boolean swing(Direction direction) {
		if (gameMap.getPlayer().checkSword() == false) {
			return false;
		}
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
		ArrayList<Entity> removedEntities = new ArrayList<Entity>();
		for(Tile t : attackedTiles) {
			for(Entity e : t.getEntities()) {
				if(e instanceof Enemy) {
					removedEntities.add(e);
				}
			}
		}
		for(Tile t : attackedTiles) {
			for(Entity e : removedEntities) {
				if(t.getEntities().contains(e)) {
					t.removeEntity(e);
				}
			}
		}
		return true;

	}
	
	public boolean placeBomb() {
		if (gameMap.getPlayer().checkBomb()) {
			Bomb placedBomb = new Bomb(gameMap,gameMap.genID());
			placedBomb.placeBomb();
			tickingBombs.add(placedBomb);
			return true;
		}
		return false;
	}

	
	/**
	 * checks if the inputted value or coordinate is within the boundary of the map
	 * @param value coordinate
	 * @return true if its within the map boundary
	 */
	private boolean valueInMap(int value) {
		if(value < 0 || value >= arrayLength) {
			return false;
		}
		return true;
	}
	
	/**
	 * checks if a move valid so entities cannot move into obstacles such as walls and closed doors
	 * checks tiles 1 and 2 units away from the entity in the direction they are trying to move
	 * to ensure that the move is valid (moving into two boulders, walls)
	 * @param entity the entity trying to perform a move
	 * @param move direction the entity is trying to move
	 * @return true if move is valid
	 */
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
								return true;
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
								return true;
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
								return true;
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
								return true;
							}
							return false;
						}
					}
				}
				break;
		}
		return true; 
	}
	
	/**
	 * sets win conditions for the game (can be combination of killing all enemies, collecting all treasure, triggering all switches)
	 */
	public void setWinConditions() {
		ArrayList<WinCondition> conditions = this.gameMap.getWinConditions();
		System.out.println("win conditions" + conditions);
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
	
	/**
	 * checks if there is an enemy on the same tile as the player to see if they have been killed
	 * @param map
	 * @param player
	 * @return true if player is alive
	 */
	public boolean checkPlayerStatus() {
		Player player = gameMap.getPlayer();
		Tile playerLocation = gameMap.getPlayerLocation();
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
