package ass2;

import java.util.ArrayList;

public class GameEngine {
	private Map gameMap;
	private boolean enemyWinCondition;
	private boolean boulderWinCondition;
	private boolean treasureWinCondition;
	private int arrayLength;
	private ArrayList<Bomb> tickingBombs = new ArrayList<Bomb>();
	private int numTreasures;
	private GameStateInterface gameStateInterface;
	private int totalEnemies;
	private int numSwitches;
	
	public GameEngine(Map map) {
		this.gameMap = map;
		this.enemyWinCondition = false;
		this.boulderWinCondition = false;
		this.treasureWinCondition = false;
		this.arrayLength = gameMap.getArrayLength();
		this.gameStateInterface = new Play();
		this.numTreasures = getNumItems()[0];
		this.totalEnemies = getNumItems()[2];
		this.numSwitches = getNumItems()[1];
		setWinConditions();
	}
	
	public GameStateInterface getGameStateInterface() {
		return this.gameStateInterface;
	}
	
	/**
	 * returns if the boulder win condition is set
	 * @return true if boulder win condition is set
	 */
	public boolean getBoulderWinCondition() {
		return this.boulderWinCondition;
	}
	
	/**
	 * returns if the enemy win condition is set
	 * @return true if enemy win condition is set
	 */
	public boolean getEnemyWinCondition() {
		return this.enemyWinCondition;
	}
	
	/**
	 * returns if the treasure win condition is set
	 * @return true if treasure win condition is set
	 */
	public boolean getTreasureWinCondition() {
		return this.treasureWinCondition;
	}
	
	/**
	 * @return total number of treasure on the map
	 */
	public int getNumberTreasures() {
		return this.numTreasures;
	}
	
	/**
	 * @return total number of enemies on the map
	 */
	public int getNumEnemies() {
		return this.totalEnemies;
	}
	
	/**
	 * @return total number of switches on the map
	 */
	public int getNumSwitches() {
		return this.numSwitches;
	}
	
	/**
	 * counts the number of specific entities that exist within the map
	 * iterates through the map making note of each treasure, switch or enemy
	 * used for win condition testing
	 * @return array with count of various items: array[0] for treasure, array[1] for switches, array[2] for enemies
	 */
	private int[] getNumItems() {
		int[] numItems = {0,0,0};	//numItems[0] for treasure, numItems[1] for switches, numItems[2] for enemies
		for (int i = 0; i < arrayLength; i++) {
			for (int j = 0; j < arrayLength; j++) {
				for (Entity e : gameMap.getMap()[i][j].getEntities()) { // look through every single entity
					if (e instanceof Treasure) { // count all the treasures
						numItems[0]++;
					} else if (e instanceof FloorSwitch) {
						numItems[1]++;
					} else if (e instanceof Enemy) {
						numItems[2]++;
					}
				}
			}
		}
		return numItems;
	}
	
	/**
	 * @return Map object associated with this game
	 */
	public Map getGameMap() {
		return gameMap;
	}

	/**
	 * handles the ticking effect of bombs and invincibility
	 * iterates through the list of active bombs, calling its tick() function
	 * existence of bombs is handled independently by each bomb
	 * @return gameState interface representing the updated state of the current game
	 */
	public GameStateInterface tickEffects() {
		ArrayList<Bomb> removedBombs = new ArrayList<>();
		if (tickingBombs != null) {
			for (Bomb bomb : tickingBombs) { // tick every bomb ,remove when it explodes
				if (!bomb.tick()) {
					removedBombs.add(bomb);
				}
			}
			for (Bomb b: removedBombs) {
				tickingBombs.remove(b);
			}
			if (gameMap.getPlayer() == null) {
				gameStateInterface = new Lose();
				return gameStateInterface;
			}
		}
		
		gameMap.getPlayer().invincibleTick();
		
		return gameStateInterface;
	}
	
	/**
	 * moves the player up one tile
	 * calls validateMove and moveConsequences to ensure the move is valid
	 * and the rest of the map and entities perform actions based on the players move
	 * @return true if the player has been successfully moved up one tile
	 */
	public boolean movePlayerNorth() {
		Tile[][] map = gameMap.getMap();
		Tile playerLocation = gameMap.getPlayerLocation();
		Player player = gameMap.getPlayer();
		Direction playerAction = Direction.NORTH;
		System.out.println("trying to move player");
		if (this.validateMove(player, playerAction)) {
			System.out.println("valid move");
			int playerX = playerLocation.getX();
			int playerY = playerLocation.getY();
			Tile affectedTile = map[playerX][playerY-1];
			Tile followingTile = null;
			if (playerY-2 >= 0) {
				followingTile = map[playerX][playerY-2];
			}
	
			boolean movePlayer = moveConsequences(player, playerAction, affectedTile,
					followingTile);
			System.out.println("move player:"+movePlayer);

			if (movePlayer) {
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
	 * @return true if the player has been successfully moved down one tile
	 */
	public boolean movePlayerSouth() {
		Tile[][] map = gameMap.getMap();
		Tile playerLocation = gameMap.getPlayerLocation();
		Player player = gameMap.getPlayer();
		Direction playerAction = Direction.SOUTH;
		if (this.validateMove(player, playerAction)) {
			int playerX = playerLocation.getX();
			int playerY = playerLocation.getY();
			Tile affectedTile = map[playerX][playerY+1];
			Tile followingTile = null;
			if (playerY+2 < arrayLength) {
				followingTile = map[playerX][playerY+2];
			}
			boolean movePlayer = moveConsequences(player, playerAction, affectedTile,
					followingTile);
		
			if (movePlayer) {
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
	 * @return true if the player has been successfully moved right one tile
	 */
	public boolean movePlayerEast() {
		Tile[][] map = gameMap.getMap();
		Tile playerLocation = gameMap.getPlayerLocation();
		Player player = gameMap.getPlayer();
		Direction playerAction = Direction.EAST;
		if (this.validateMove(player, playerAction)) {
			int playerX = playerLocation.getX();
			int playerY = playerLocation.getY();
			Tile affectedTile = map[playerX+1][playerY];
			Tile followingTile = null;
			if (playerX+2 < arrayLength) {
				followingTile = map[playerX+2][playerY];
			}
			boolean movePlayer = moveConsequences(player, playerAction, affectedTile,
					followingTile);
		
			if (movePlayer) {
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
	 * @return true if the player has been successfully moved left one tile
	 */
	public boolean movePlayerWest() {
		Tile[][] map = gameMap.getMap();
		Tile playerLocation = gameMap.getPlayerLocation();
		Player player = gameMap.getPlayer();
		Direction playerAction = Direction.WEST;
		if (this.validateMove(player, playerAction)) {
			int playerX = playerLocation.getX();
			int playerY = playerLocation.getY();
			Tile affectedTile = map[playerX-1][playerY];
			Tile followingTile = null;
			if (playerX-2 >= 0) {
				followingTile = map[playerX-2][playerY];
			}
			
			boolean movePlayer = moveConsequences(player, playerAction, affectedTile,
					followingTile);
			
			if (movePlayer) {
				gameMap.movePlayer(playerAction);	
				return true;
			}
		}
		return false;
		
	}
	
	/**
	 * calculates the number of switches on the map triggered at the time of call
	 * @return integer count of number of switches triggered
	 */
	public int switchesTriggered() {
		int switchesTriggered = 0;
		Tile[][] map = gameMap.getMap();
		for (int i = 0; i < arrayLength; i++) {
			for (int j = 0; j < arrayLength; j++) {
				Tile tile = map[i][j];
				for (Entity e : tile.getEntities()) { // look through every single entity
					if (e instanceof FloorSwitch) { // every floor switch
						FloorSwitch switch1 = (FloorSwitch)e;
						if (switch1.getStatus()) {
							switchesTriggered++;
						}
					}
				}
			}
		}
		return switchesTriggered;
	}
	
	/**
	 * calculates the number of enemies killed by the player at time of call
	 * @return integer count of number of enemies killed
	 */
	public int enemiesKilled() {
		int enemies = 0;
		Tile[][] map = gameMap.getMap();
		for (int i = 0; i < arrayLength; i++) {
			for (int j = 0; j < arrayLength; j++) {
				Tile tile = map[i][j];
				for (Entity e : tile.getEntities()) { // look through every single entity
					if (e instanceof Enemy) { // every enemy
						enemies++;
					}
				}
			}
		}
		return getNumEnemies() - enemies;
	}

	/**
	 * iterates through all entities on the map and calls getAction on enemies to make them move
	 */
	public void moveEnemies() {
		Tile[][] map = gameMap.getMap();
		ArrayList<Enemy> enemies = new ArrayList<>();
		for (int i = 0; i < arrayLength; i++) {
			for (int j = 0; j < arrayLength; j++) {
				Tile tile = map[i][j];
				for (Entity e : tile.getEntities()) { // look through every single entity
					if (e instanceof Enemy) { // every enemy that needs to move
						//((Enemy) e).getAction(gameMap); // validation on enemy side
						enemies.add((Enemy)e);
					}
				}
			}
		}
		if(gameMap.getPlayer().getInvincibility()) {
			for(Enemy enemy: enemies) {
				enemy.setAction(new GetActionRunAway());
			}
		}
		else {
			for(Enemy enemy: enemies) {
				enemy.setOgAction();
			}
		}
		for (Enemy enemy: enemies) {
			enemy.getAction(gameMap);
		}
	}

	/**
	 * checks the consequences of the player making a certain move and 
	 * performs actions according to interactions between the players move and affected entities
	 * @param player the player playing the game
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
				if (boulderMove) {
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
				if (player.addKey((Key)e)) {
					removeEntities.add(e);
				}
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
				if (player.putInventory(e)) {
					removeEntities.add(e);
				}
			} else if (e instanceof Enemy) {	// lose if you walk into enemy
				if (player.getInvincibility()) { // enemy dies if player walks into them with invincibility
					removeEntities.add(e);
				} else {
					gameStateInterface = new Lose();
				}
			} else if (e instanceof Pit) {	// lose if you walk into pit
				if (!player.getHover()) {
					gameStateInterface = new Lose();
				}
			} else if (e instanceof Exit) {	// win on exit
				gameStateInterface = new Win();
			} else if (e instanceof Door) { // condition when player walks into door
				Door door = (Door)e;
				if (!door.getStatus()) { // closed
					if (!player.checkKey(door)) {
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
			System.out.println("hello");
			return false;
		}
		Tile player = gameMap.getPlayerLocation();
		ArrayList<Tile> attackedTiles = new ArrayList<Tile>();
		int tileInFrontX = player.getX();
		int tileInFrontY = player.getY();
		boolean tileInFrontWall = false;
		switch (direction) {
			case NORTH:
				tileInFrontY -= 1;
				if(valueInMap(player.getY() - 2) && valueInMap(tileInFrontY)) {
					for(Entity e : gameMap.getTile(tileInFrontX, tileInFrontY).getEntities()) {
						if(e instanceof Obstacle) {
							tileInFrontWall = true;
						}
					}
					if(tileInFrontWall == false) {
						int attackedX = player.getX();
						int attackedY = player.getY() - 2;
						attackedTiles.add(gameMap.getTile(attackedX, attackedY));
					}

				}
				for(int i = -1; i <= 1; i++) {	
					if(valueInMap(player.getX() + i) && valueInMap(player.getY() - 1)) {
						int attackedX = player.getX() + i;
						int attackedY = player.getY() - 1;
						attackedTiles.add(gameMap.getTile(attackedX, attackedY));
					}
					
				}
				
				break;
			case SOUTH:
				tileInFrontY += 1;
				if(valueInMap(player.getY() + 2) && valueInMap(tileInFrontY)) {
					for(Entity e : gameMap.getTile(tileInFrontX, tileInFrontY).getEntities()) {
						if(e instanceof Obstacle) {
							tileInFrontWall = true;
						}
					}
					if(tileInFrontWall == false) {
						int attackedX = player.getX();
						int attackedY = player.getY() + 2;
						attackedTiles.add(gameMap.getTile(attackedX, attackedY));
					}
				}
				for(int i = -1; i <= 1; i++) {
					if(valueInMap(player.getX() + i) && valueInMap(player.getY() + 1)) {
						int attackedX = player.getX() + i;
						int attackedY = player.getY() + 1;
						attackedTiles.add(gameMap.getTile(attackedX, attackedY));
					}
				}
				break;
			case EAST:
				tileInFrontX += 1;
				if(valueInMap(player.getX() + 2) && valueInMap(tileInFrontX)) {
					for(Entity e : gameMap.getTile(tileInFrontX, tileInFrontY).getEntities()) {
						if(e instanceof Obstacle) {
							tileInFrontWall = true;
						}
					}
					if(tileInFrontWall == false) {
						int attackedX = player.getX() + 2;
						int attackedY = player.getY();
						attackedTiles.add(gameMap.getTile(attackedX, attackedY));
					}

				}
				for(int i = -1; i <= 1; i++) {
					if(valueInMap(player.getY() + i) && valueInMap(player.getX() + 1)) {
						int attackedX = player.getX() + 1;
						int attackedY = player.getY() + i;
						attackedTiles.add(gameMap.getTile(attackedX, attackedY));
					}
				}
				break;
			case WEST:
				tileInFrontX -= 1;
				if(valueInMap(player.getX() - 2) && valueInMap(tileInFrontX)) {
					for(Entity e : gameMap.getTile(tileInFrontX, tileInFrontY).getEntities()) {
						if(e instanceof Obstacle) {
							tileInFrontWall = true;
						}
					}
					if(tileInFrontWall == false) {
						int attackedX = player.getX() - 2;
						int attackedY = player.getY();
						attackedTiles.add(gameMap.getTile(attackedX, attackedY));
					}

				}
				for(int i = -1; i <= 1; i++) {
					if(valueInMap(player.getY() + i) && valueInMap(player.getX() - 1)) {
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
	
	public boolean shootBow(Direction direction) {
		if (gameMap.getPlayer().checkArrow()) {
			Arrow arrow = new Arrow(gameMap.genID(), gameMap);
			arrow.shootArrow(direction);
			return true;
		}
		return false;
	}
	
	public boolean placeBomb() {
		if (gameMap.getPlayer().checkBomb()) {
			Bomb placedBomb = new Bomb(gameMap,gameMap.genID());
			System.out.println(placedBomb.getClass());
			placedBomb.placeBomb();
			System.out.println(placedBomb.getClass());
			System.out.println();
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
	private boolean validateMove(Entity entity, Direction move) {
		System.out.println("array length: " + arrayLength);
		Tile tile = gameMap.getEntityLocation(entity.getId());
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
									} else if (e3 instanceof Enemy) {
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
				if (tileX == arrayLength-1) {
					return false;
				}
				else {
					for (Entity e2 : entityLocation[tileX+1][tileY].getEntities()) {
						if (e2 instanceof Obstacle) {
							if (e2 instanceof Door) {
								return true;
							}
							if (e2 instanceof Boulder) {
								if (tileX > arrayLength-3) {
									return false;
								}
								for (Entity e3 : entityLocation[tileX+2][tileY].getEntities()) {
									if (e3 instanceof Obstacle) {
										return false;
									} else if (e3 instanceof Enemy) {
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
				if (tileY == arrayLength-1) {
					return false;
				}
				else {
					for (Entity e2 : entityLocation[tileX][tileY+1].getEntities()) {
						if (e2 instanceof Obstacle) {
							if (e2 instanceof Door) {
								return true;
							}
							if (e2 instanceof Boulder) {
								if (tileY > arrayLength-3) {
									return false;
								}
								for (Entity e3 : entityLocation[tileX][tileY+2].getEntities()) {
									if (e3 instanceof Obstacle) {
										return false;
									} else if (e3 instanceof Enemy) {
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
									} else if (e3 instanceof Enemy) {
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
	private void setWinConditions() {
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
	
	public void setGameStateInterface(GameStateInterface gameStateInterface) {
		this.gameStateInterface = gameStateInterface;
	}
	
	// Checks win conditions to see if you have won. returns true if you won, returns false otherwise
	// what it checks dependso n t
	// Also sets the gameStateInterface depending on the win conditions
	// E.g. if there is an exit condition but your playerstatus == false, gamestate gets set to lose
	public boolean checkGameState() {
		return gameStateInterface.checkState(this);
	}
	
	
}
