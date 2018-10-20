package ass2;

public abstract class GetActionRunAway implements GetAction {
	public GetActionRunAway() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void getAction(Map map, Enemy enemy) {
		// TODO Auto-generated method stub
		Tile playerPos = map.getPlayerLocation();
		Tile curPos = map.getEntityLocation(enemy.getId());
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
			if(enemy.obstacleCheck(tile0)) {
				obstacleCount++;
			}
			dist[0] = enemy.distCalc(playerX, playerY,tile0.getX(),tile0.getY());
		}
		if(curY+1 < length) {
			tile1 = map.getTile(curX, curY+1);//South
			if(enemy.obstacleCheck(tile1)) {
				obstacleCount++;
			}
			dist[1] = enemy.distCalc(playerX, playerY,tile1.getX(),tile1.getY());
		}
		if(curX+1 < length) {
			tile2 = map.getTile(curX+1, curY);//East
			if(enemy.obstacleCheck(tile2)) {
				obstacleCount++;
			}
			dist[2] = enemy.distCalc(playerX, playerY,tile2.getX(),tile2.getY());
		}
		if(curX-1 >= 0) {
			tile3 = map.getTile(curX-1, curY);//West
			if(enemy.obstacleCheck(tile3)) {
				obstacleCount++;
			}
			dist[3] = enemy.distCalc(playerX, playerY,tile3.getX(),tile3.getY());
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
				enemy.shift(map, tile0);
			}
			else if (largest == dist[1]) {
				enemy.shift(map, tile1);
			}
			else if (largest == dist[2]) {
				enemy.shift(map, tile2);
			}
			else if (largest == dist[3]) {
				enemy.shift(map, tile3);
			}
		}
	}

}
