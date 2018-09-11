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
			// pit = lose, enemy = lose
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
			
			// Check floor switches
			// iterate through 
			
			
			
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
	

	
}
