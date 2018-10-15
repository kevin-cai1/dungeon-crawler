package ass2;

public class Play implements GameStateInterface {

	/**
	 * returns true if the game has been won, and changes the gamestate to win
	 */
	@Override
	public boolean checkState(GameEngine gameEngine) {
		boolean boulderWinCondition = gameEngine.getBoulderWinCondition();
		boolean enemyWinCondition = gameEngine.getEnemyWinCondition();
		boolean treasureWinCondition = gameEngine.getTreasureWinCondition();
		int numTreasures = gameEngine.getNumberTreasures();
		Player player = gameEngine.getGameMap().getPlayer();
		Tile[][] map = gameEngine.getGameMap().getMap();
		boolean allSwitches = true;
		boolean allEnemiesDestroyed = true;
		for (int i = 0; i < gameEngine.getGameMap().getArrayLength(); i++) {
			for (int j = 0; j < gameEngine.getGameMap().getArrayLength(); j++) {
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
			//gameState = GameState.Win;
			gameEngine.setGameStateInterface(new Win());
			gameEngine.setGameState(GameState.Win);
		}
		return satisfyWin;
	}

}
