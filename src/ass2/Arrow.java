package ass2;

public class Arrow extends Entity{
	private Map map;
	private Tile arrowPosition;
	
	public Arrow(Map map) {
		this.map = map;
		this.arrowPosition = map.getPlayerLocation();
	}

	public void shootArrow(Direction direction) {
		this.arrowPosition = map.getPlayerLocation();
		int arrowX = arrowPosition.getX();
		int arrowY = arrowPosition.getY();
		Tile[][] tile = map.getMap();
		switch (direction) {
		case NORTH:
			for (int j = arrowY; j >= 0; j--) {
				for (Entity e : tile[arrowX][j].getEntities()) {
					if (e instanceof Wall) {
						return; // arrow breaks
					}
					else if (e instanceof Enemy) {
						tile[arrowX][j].removeEntity(e);					}
				}
			}
			break;

		case EAST:
			for (int i = arrowX; i <= 19; i++) {
				for (Entity e : tile[i][arrowY].getEntities()) {
					if (e instanceof Wall) {
						return; // arrow breaks
					}
					else if (e instanceof Enemy) {
						tile[i][arrowY].removeEntity(e);
					}
				}
			}
			break;
		case SOUTH:
			for (int j = arrowY; j <=  19; j++) {
				for (Entity e : tile[arrowX][j].getEntities()) {
					if (e instanceof Wall) {
						return; // arrow breaks
					}
					else if (e instanceof Enemy) {
						tile[arrowX][j].removeEntity(e);
					}
				}
			}
			break;
		case WEST:
			for (int i = arrowX; i >= 0; i--) {
				for (Entity e : tile[i][arrowY].getEntities()) {
					if (e instanceof Wall) {
						return; // arrow breaks
					}
					else if (e instanceof Enemy) {
						tile[i][arrowY].removeEntity(e);
					}
				}
			}
			break;
		} 
	}
	@Override
	public String toString() {
		return null;
	}
}
