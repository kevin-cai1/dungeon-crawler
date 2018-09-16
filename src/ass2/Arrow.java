package ass2;

public class Arrow extends Entity{
	private Map map;
	private Tile arrowPosition;
	public Arrow(int id, Map map) {
		super(id);
		this.map = map;
		this.arrowPosition = map.getPlayerLocation();
	}
	/**
	 * Shoots an arrow in a given direction. It will hit 1 enemy and kill it or hit an Obstacle and do nothing
	 * @param direction direction it is fired in
	 */
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
						tile[arrowX][j].removeEntity(e);	
						return;
					}
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
						return;
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
						return;
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
						return;
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
