package ass2;

public abstract class GetActionRunAway extends Enemy implements GetAction {
	private static final long serialVersionUID = -2616411096900925988L;
	public GetActionRunAway(int id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void getAction(Map map) {
		// TODO Auto-generated method stub
		Tile playerPos = map.getPlayerLocation();
		Tile curPos = map.getEntityLocation(this.getId());
    	int curX = curPos.getX();
    	int curY = curPos.getY();
    	int playerX = playerPos.getX();
    	int playerY = playerPos.getY();
    	int length = map.getArrayLength();
		Tile tile0 = null;
		Tile tile1 = null;
		Tile tile2 = null;
		Tile tile3 = null;
		double[] dist = new double[4];
		//if all 4 tiles around the enemy are obstacles then dont move
		int obstacleCount = 0;
		if(curY-1 >= 0) {
			tile0 = map.getTile(curX, curY-1);//North
			if(obstacleCheck(tile0)) {
				obstacleCount++;
			}
			dist[0] = distCalc(playerX, playerY,tile0.getX(),tile0.getY());
		}
		if(curY+1 < length) {
			tile1 = map.getTile(curX, curY+1);//South
			if(obstacleCheck(tile1)) {
				obstacleCount++;
			}
			dist[1] = distCalc(playerX, playerY,tile1.getX(),tile1.getY());
		}
		if(curX+1 < length) {
			tile2 = map.getTile(curX+1, curY);//East
			if(obstacleCheck(tile2)) {
				obstacleCount++;
			}
			dist[2] = distCalc(playerX, playerY,tile2.getX(),tile2.getY());
		}
		if(curX-1 >= 0) {
			tile3 = map.getTile(curX-1, curY);//West
			if(obstacleCheck(tile3)) {
				obstacleCount++;
			}
			dist[3] = distCalc(playerX, playerY,tile3.getX(),tile3.getY());
		}
		
		//if all 4 tiles contain obstacles
		if(obstacleCount == 4) {
			return;
		}
		//now find the largest out of the 4
		double largest = dist[0];
		for(double e: dist) {
			if(largest < e){
				largest = e;
			}
		}
		for(int i = 0; i < 4; i++) {
			if(largest == dist[0]) {
				shift(map, tile0);
			}
			else if (largest == dist[1]) {
				shift(map, tile1);
			}
			else if (largest == dist[2]) {
				shift(map, tile2);
			}
			else if (largest == dist[3]) {
				shift(map, tile3);
			}
		}
	}

}
