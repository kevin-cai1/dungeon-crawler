package ass2;

// Controls Game states
public class GameSystem {
	public static void main(String[] args) {
		// Initialise main menu
		// If mode 1 is selected, launch mode 1
		// If mode 2 is selected, launch mode 2
		
		// Game Mode 1
		// Instantiate game engine
		GameEngine gameEngine = new GameEngine();
		gameEngine.runGame(); // initialises the map, player controls
	}
}
