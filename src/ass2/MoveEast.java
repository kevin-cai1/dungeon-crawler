package ass2;

public class MoveEast implements PlayerMovement {

	@Override
	public boolean movePlayer(GameEngine gameEngine) {
		Tile[][] map = gameEngine.getGameMap().getMap();
		Tile playerLocation = gameEngine.getGameMap().getPlayerLocation();
		Player player = gameEngine.getGameMap().getPlayer();
		Direction playerAction = Direction.EAST;
		if (gameEngine.validateMove(player, playerAction) == true) {
			int playerX = playerLocation.getX();
			int playerY = playerLocation.getY();
			Tile affectedTile = map[playerX+1][playerY];
			Tile followingTile = null;
			if (playerX+2 < gameEngine.getGameMap().getArrayLength()) {
				followingTile = map[playerX+2][playerY];
			}
			boolean movePlayer = gameEngine.moveConsequences(player, playerAction, affectedTile,
					followingTile);
		
			if (movePlayer == true) {
				gameEngine.getGameMap().movePlayer(playerAction);	
				return true;
			}
		}
		return false;
	}

}
