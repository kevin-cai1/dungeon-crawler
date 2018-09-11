package ass2;

import java.util.concurrent.locks.Condition;

import com.sun.java.swing.plaf.windows.resources.windows_zh_CN;
import com.sun.media.jfxmedia.events.NewFrameEvent;
import com.sun.org.apache.bcel.internal.generic.NEW;

import sun.security.krb5.internal.util.KerberosFlags;

public class GameEngine {
	
	public GameEngine() {
		
	}
	
	public GameState runGame() {
		// runs the game
		// gets player moves
		// calculates entity moves
		// handles win conditions
		
		// initialise the map
		Map gameMap = new Map();
		gameMap = gameMap.generateMap(); //fills the map with shit
		Tile[][] map = gameMap.getMap();
		
		PlayerControl control = new PlayerControl(); //**instantiate control class
		Player player = gameMap.getPlayer();
		Tile playerLocation = gameMap.getPlayerLocation();
		
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
						
					} else if (e.getClass().equals(new Boulder().getClass())) {
						
						
					} else if (e.getClass().equals(new InvincibilityPotion().getClass())) {
						
						
					} else if (e.getClass().equals(new HoverPotion().getClass())) {
						
						
					} else if (e.getClass().equals(new Key().getClass())) {
						
						
					} else if (e.getClass().equals(new Treasure().getClass())) {
						
						
					}
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
			
			
		
				// make moves
		
		
				// check win condition
				if (/*game == won*/) {
					return GameState.Win;
				}
			}	
		}
	}
	
}
