package ass2;

public class MoveSouth implements PlayerMovement {

	@Override
	public boolean movePlayer(GameEngine gameEngine) {
		Map gameMap = gameEngine.getGameMap();
		Tile[][] map = gameMap.getMap();
		Tile playerLocation = gameMap.getPlayerLocation();
		Player player = gameMap.getPlayer();
		Direction playerAction = Direction.SOUTH;
		if (gameEngine.validateMove(player, playerAction) == true) {
			int playerX = playerLocation.getX();
			int playerY = playerLocation.getY();
			Tile affectedTile = map[playerX][playerY+1];
			Tile followingTile = null;
			if (playerY+2 < gameMap.getArrayLength()) {
				followingTile = map[playerX][playerY+2];
			}
			boolean movePlayer = gameEngine.moveConsequences(player, playerAction, affectedTile,
					followingTile);
		
			if (movePlayer == true) {
				gameMap.movePlayer(playerAction);	
				return true;
			}
		}
		return false;
		
	}

}
