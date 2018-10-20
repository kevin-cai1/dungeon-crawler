package ass2;

import java.util.ArrayList;
import java.util.List;

public class GetActionCoward implements GetAction {

	public GetActionCoward() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void getAction(Map map, Enemy enemy) {
		// TODO Auto-generated method stub
    	Tile playerPos = map.getPlayerLocation();
    	int playerX = playerPos.getX();
    	int playerY = playerPos.getY();
    	Tile curPos = map.getEntityLocation(enemy.getId());
    	int curX = curPos.getX();
    	int curY = curPos.getY();
    	int minDistance = 3;
    	List<Tile> shortest = new ArrayList<>();
    	if(enemy.distCalc(playerX, playerY, curX, curY) > minDistance) {//if its larger than this threshold itll act like a hunter
    		enemy.toTile(map, playerPos);
    		enemy.makeValidPath(map, playerPos, shortest);
    		Tile currPos = map.getEntityLocation(enemy.getId()); //finds the hunter on the map
    		currPos.removeEntity(enemy); //removes it from tis current spot
    		Tile neww = shortest.get(1); //retrieves [1] as [0] is the starting position of the hunter
    		neww.addEntity(enemy); //puts it into where it should be
    	}
    	else {// i.e. when its supposed to act like a coward
    		GetAction getAction = new GetActionRunAway();
    		getAction.getAction(map, enemy);
    	}
	}

}
