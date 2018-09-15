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
    		Tile tile;
    		double[] dist = new double[4];
    		if(curY-1 >= 0) {
    			tile = map.getTile(curX, curY-1);//North
    			dist[0] = distCalc(playerX, playerY,tile.getX(),tile.getY());
    		}
    		if(curY+1 < 20) {
    			tile = map.getTile(curX, curY+1);//South
    			dist[1] = distCalc(playerX, playerY,tile.getX(),tile.getY());
    		}
    		if(curX+1 < 20) {
    			tile = map.getTile(curX+1, curY);//East
    			dist[2] = distCalc(playerX, playerY,tile.getX(),tile.getY());
    		}
    		if(curX-1 >= 0) {
    			tile = map.getTile(curX-1, curY);//West
    			dist[3] = distCalc(playerX, playerY,tile.getX(),tile.getY());
    		}

    	}
    }

}
