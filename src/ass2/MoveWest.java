package ass2;

public class MoveWest implements PlayerMovement {

	@Override
	public boolean movePlayer(GameEngine gameEngine) {
		Map gameMap = gameEngine.getGameMap();
		Tile[][] map = gameMap.getMap();
		Tile playerLocation = gameMap.getPlayerLocation();
		Player player = gameMap.getPlayer();
		Direction playerAction = Direction.WEST;
		if (gameEngine.validateMove(player, playerAction) == true) {
			int playerX = playerLocation.getX();
			int playerY = playerLocation.getY();
			Tile affectedTile = map[playerX-1][playerY];
			Tile followingTile = null;
			if (playerX-2 >= 0) {
				followingTile = map[playerX-2][playerY];
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
