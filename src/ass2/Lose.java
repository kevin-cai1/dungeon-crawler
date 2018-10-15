package ass2;

public class Lose implements GameStateInterface {

	@Override
	public boolean checkState(GameEngine gameEngine) {
		if (gameEngine.getGameMap())
		return false;
	}

}
