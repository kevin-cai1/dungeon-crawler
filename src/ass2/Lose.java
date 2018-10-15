package ass2;

public class Lose implements GameStateInterface {

	/**
	 * returns true if player is dead
	 */
	@Override
	public boolean checkState(GameEngine gameEngine) {
		if (gameEngine.checkPlayerStatus() == false) {
			return true;
		}
		return false;
	}

}
