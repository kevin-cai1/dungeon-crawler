package ass2;

import java.util.ArrayList;
import java.util.List;
/**
 * type of enemy which moves toward player until it is 3 tiles away, then starts moving away from the player
 * @author gordon
 *
 */
public class Coward extends Enemy{
    public Coward(int id){
        super(id);
    }
    @Override
    public String toString() {
        return "coward";
    }
    
    /**
     * calculates and performs action for coward based on its proximity to the player
     * @param map
     */
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
    		runAway(map);
    	}
    }

/**
 * Moves the coward to a certain tile
 * @param map
 * @param tile
 */
	public void shift(Map map, Tile tile) {
		Tile currPos = map.getEntityLocation(this.getId()); //finds the hunter on the map
		currPos.removeEntity(this); //removes it from its current spot
		Coward coward = this;
		tile.addEntity(coward); //puts it into where it should be
	}

}
