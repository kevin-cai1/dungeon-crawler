package ass2;

import java.util.concurrent.locks.Condition;

import com.sun.java.swing.plaf.windows.resources.windows_zh_CN;

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
		
		
		PlayerControl control = new PlayerControl(); //**instantiate control class
		Player player = gameMap.getPlayer();
		
		while (/* game not won, user not ded or not quit*/) {
		// take user input (player control @jun)
			Direction playerMove = control.getAction();
			gameMap.makeMove(player, playerMove);
		// calculate entity movements
			//go through every tile on the map, calculate moves of any entities you find
			
			
		
		// make moves
		
		
		// check win condition
			//if (game == won) {
				return GameState.Win;
			//}
		}
	}
	
	private void moveEntities(Tile[][] map) {
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 20; j++) {
				Tile tile = map[i][j];
				for (Entity e : tile.getEntities()) {
					if (e instanceof Enemy) {
						
					}
				}
			}
		}
	}
}
