package ass2;

public class MoveNorth implements PlayerMovement {

	@Override
	public boolean movePlayer(GameEngine gameEngine) {
		Tile[][] map = gameEngine.getGameMap().getMap();
		Tile playerLocation = gameEngine.getGameMap().getPlayerLocation();
		Player player = gameEngine.getGameMap().getPlayer();
		Direction playerAction = Direction.NORTH;
		System.out.println("trying to move player");
		if (gameEngine.validateMove(player, playerAction) == true) {
			System.out.println("valid move");
			int playerX = playerLocation.getX();
			int playerY = playerLocation.getY();
			Tile affectedTile = map[playerX][playerY-1];
			Tile followingTile = null;
			if (playerY-2 >= 0) {
				followingTile = map[playerX][playerY-2];
			}
	
			boolean movePlayer = gameEngine.moveConsequences(player, playerAction, affectedTile,
					followingTile);
			System.out.println("move player:"+movePlayer);

			if (movePlayer == true) {
				gameEngine.getGameMap().movePlayer(playerAction);	
				return true;
			}
		}
		return false;
	}

}
