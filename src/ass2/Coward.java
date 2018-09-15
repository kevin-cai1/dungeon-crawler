package ass2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Coward extends Enemy{
    public Coward(int id){
        super(id);
    }
    @Override
    public String toString() {
        return null;
    }

    @Override
    public void getAction(Map map) {
    	Tile playerPos = map.getPlayerLocation();
    	int playerX = playerPos.getX();
    	int playerY = playerPos.getY();
    	Tile curPos = map.getEntityLocation(this.getId());
    	int curX = curPos.getX();
    	int curY = curPos.getY();
    	int minDistance = 3;
    	List<Tile> shortest = new ArrayList<>();
    	if(distCalc(playerX, playerY, curX, curY) > minDistance) {//if its larger than this threshold itll act like a hunter
    		toTile(map, playerPos);
    		makeValidPath(map, playerPos, shortest);
    		Tile currPos = map.getEntityLocation(this.getId()); //finds the hunter on the map
    		currPos.removeEntity(this); //removes it from tis current spot
    		Enemy enemy = this;
    		Tile neww = shortest.get(1); //retrieves [1] as [0] is the starting position of the hunter
    		neww.addEntity(enemy); //puts it into where it should be
    	}
    	else {// i.e. when its supposed to act like a coward
    		int mapSize = map.getArrayLength();
    		int goAwayX = mapSize-playerX;
    		int goAwayY = mapSize-playerY;
    		Tile goAWay = map.getTile(goAwayX, goAwayY);
    		validPath(map, goAWay, curPos);
    		makeValidPath(map, goAWay, shortest);
    	}
    }


	private void validPath(Map map, Tile tile, Tile curPos) {
		Tile adjacent;
		Tile queuePop;
		queue.add(curPos);
		visited.add(curPos);
		int popPosX;
		int popPosY;
		while(!queue.isEmpty()) {
			queuePop = queue.remove();
			popPosX = queuePop.getX();
			popPosY = queuePop.getY();
			if(queuePop.equals(tile)) {
				break;
			}
			//East Direction
			if(popPosX+1 < 20) {//20 being map size
				adjacent = map.getTile(popPosX+1, popPosY);
				visitCheck(adjacent, queuePop);
			}
			//West Direction
			if(popPosX-1 >= 0) {
				adjacent = map.getTile(popPosX-1, popPosY);
				visitCheck(adjacent, queuePop);
			}
			//South Direction
			if(popPosY+1 < 20) {
				adjacent = map.getTile(popPosX, popPosY+1);
				visitCheck(adjacent, queuePop);
			}
			//North Direction
			if(popPosY-1 >= 0) {
				adjacent = map.getTile(popPosX, popPosY-1);
				visitCheck(adjacent, queuePop);
			}
		}
	}
    @Override
    public void visitCheck(Tile adjacent, Tile queuePop) {
		if(!visited.contains(adjacent)) {
			visited.add(adjacent);
			for(Entity entity: adjacent.getEntities()) {
				if(!(entity instanceof Obstacle) && !(entity instanceof Player)) { //has to additionally check that it doesnt run into the player
					queue.offer(adjacent);
					parent.put(adjacent, queuePop);
				}
			}
		}
    }
}
