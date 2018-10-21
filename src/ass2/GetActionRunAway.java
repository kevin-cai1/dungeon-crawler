package ass2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

public class GetActionRunAway implements GetAction, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1778473720791496096L;
	public GetActionRunAway() {
		// TODO Auto-generated constructor stub
	}
    /**
     * calculates and performs action for coward based on its proximity to the player
     * @param map
     */

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
		HashMap<Tile,Double> hashMap = new HashMap<>();
		//if all 4 tiles around the enemy are obstacles then dont move
		int obstacleCount = 0;
		if(curY-1 >= 0) {
			tile0 = map.getTile(curX, curY-1);//North
			if(enemy.obstacleCheck(tile0)) {
				obstacleCount++;
			}
			hashMap.put(tile0,enemy.distCalc(playerX, playerY,tile0.getX(),tile0.getY()));
		}
		if(curY+1 < length) {
			tile1 = map.getTile(curX, curY+1);//South
			if(enemy.obstacleCheck(tile1)) {
				obstacleCount++;
			}
			hashMap.put(tile1,enemy.distCalc(playerX, playerY,tile1.getX(),tile1.getY()));
		}
		if(curX+1 < length) {
			tile2 = map.getTile(curX+1, curY);//East
			if(enemy.obstacleCheck(tile2)) {
				obstacleCount++;
			}
			hashMap.put(tile2,enemy.distCalc(playerX, playerY,tile2.getX(),tile2.getY()));
		}
		if(curX-1 >= 0) {
			tile3 = map.getTile(curX-1, curY);//West
			if(enemy.obstacleCheck(tile3)) {
				obstacleCount++;
			}
			hashMap.put(tile3,enemy.distCalc(playerX, playerY,tile3.getX(),tile3.getY()));
		}
		
		//if all 4 tiles contain obstacles
		if(obstacleCount == 4) {
			return;
		}
		//now sort the list
		List<Entry<Tile, Double>> list = new ArrayList<>(hashMap.entrySet());
		list.sort(Entry.comparingByValue());
		Collections.reverse(list);
		LinkedHashMap<Tile,Double> sortedHashMap = new LinkedHashMap<>();
		for( Entry<Tile, Double> entry: list) {
			sortedHashMap.put(entry.getKey(),entry.getValue());
		}
		for(Tile tile: sortedHashMap.keySet()) {
			if(!enemy.obstacleCheck(tile)) {
				enemy.shift(map, tile);
				return;
			}
		}
	}

}
