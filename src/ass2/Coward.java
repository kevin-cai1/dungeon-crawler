package ass2;

import java.util.ArrayList;
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
    		Tile tile0 = null;
    		Tile tile1 = null;
    		Tile tile2 = null;
    		Tile tile3 = null;
    		double[] dist = new double[4];
    		if(curY-1 >= 0) {
    			tile0 = map.getTile(curX, curY-1);//North
    			dist[0] = distCalc(playerX, playerY,tile0.getX(),tile0.getY());
    		}
    		if(curY+1 < 20) {
    			tile1 = map.getTile(curX, curY+1);//South
    			dist[1] = distCalc(playerX, playerY,tile1.getX(),tile1.getY());
    		}
    		if(curX+1 < 20) {
    			tile2 = map.getTile(curX+1, curY);//East
    			dist[2] = distCalc(playerX, playerY,tile2.getX(),tile2.getY());
    		}
    		if(curX-1 >= 0) {
    			tile3 = map.getTile(curX-1, curY);//West
    			dist[3] = distCalc(playerX, playerY,tile3.getX(),tile3.getY());
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
    				shiftCoward(map, tile0);
    			}
    			else if (largest == dist[1]) {
    				shiftCoward(map, tile1);
				}
    			else if (largest == dist[2]) {
    				shiftCoward(map, tile2);
    			}
    			else if (largest == dist[3]) {
    				shiftCoward(map, tile3);
    			}
    		}
    	}
    }
	private void shiftCoward(Map map, Tile tile) {
		Tile currPos = map.getEntityLocation(this.getId()); //finds the hunter on the map
		currPos.removeEntity(this); //removes it from tis current spot
		Coward coward = this;
		tile.addEntity(coward); //puts it into where it should be
	}

}
