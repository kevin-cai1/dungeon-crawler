package ass2;

// Controls Game states
public class GameSystem {
	public static void main(String[] args) {
		// Initialise main menu
		while (true) {/* game is running */
			// If mode 1 is selected, launch mode 1
			// If mode 2 is selected, launch mode 2
			
			// Game Mode 1
			// Instantiate game engine
			GameEngine gameEngine = new GameEngine(new Map(20)); //need to change this
			// initialises the map, player controls, checks gamestate
			GameState state = gameEngine.runGame();
			
			// Game Mode 2
			// Instantiate Design engine
			DesignEngine designEngine = new DesignEngine(20);
			
			switch (state) {
			case Win:
				// victory!
				break;
			case Lose:
				// rip
				break;
			case Paused:
				// stahp
				break;
			default:
				break;
			}
		}
	}
}
