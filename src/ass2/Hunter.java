package ass2;

import java.nio.file.ClosedDirectoryStreamException;
import java.util.*;

import org.omg.CORBA.PRIVATE_MEMBER;

/**
 * 
 * @author posocer
 * Have a priority queue for each hunter. Finds the best way to get to the player. If the player is not reachable
 * it will move to the closest tile
 * 
 */
public class Hunter extends Enemy{
	private LinkedList<Tile> queue;
	private Set<Tile> visited;
	private HashMap<Tile, Tile> parent; //child on the left, parent on the right
	public Hunter() {
		queue = new LinkedList<Tile>();
		parent = new HashMap<Tile, Tile>(); //Instead of just doing prev[i] = other value, we need to remove that index first and then put it back in at that index
		visited = new HashSet<Tile>();
	}
	public Direction move(Map map) {
		Tile playerPos = playerCheck(map);
		//If there is a way, then there will be a path in parent
		if(parent.containsKey(playerPos)) {
			Tile temp = playerPos;
			List<Tile> shortest = new ArrayList<>();
			while(temp != null) {
				shortest.add(temp);
				temp = parent.get(temp);
			}
			Collections.reverse(shortest);
		}
		//after we do a pathsearch of some sort we have to clear 
		clear();
		//if there is no path to the player then we search for the closest reachable tile.
		Tile closest = ClosestTile(map, playerPos);
		
		return Direction.NORTH;
	}
	private void clear() {
		parent.clear();
		queue.clear();
		visited.clear();
	}
	private void visitCheck(Tile adjacent, Tile queuePop) {
		if(!visited.contains(adjacent)) {
			visited.add(adjacent);
			for(Entity entity: adjacent.getEntities()) {
				if(!(entity instanceof Obstacle)) {
					queue.offer(adjacent);
					parent.put(adjacent, queuePop);
				}
			}
		}
	}
	/**
	 * in theory what this should do is find the closest tile to the player that the hunter can move to 
	 * @param map
	 * @param dest
	 * @return
	 */
	private Tile ClosestTile(Map map, Tile dest) {
		Tile[][] tileMap = map.getMap();
		double temp;
		double dist = 29; //the maximum distance estimated from a 20x20 map
		Tile tile = map.getEntityLocation(this);//the closest reachable tile initially set to the hunter
		Tile tempTile;
		boolean obstacle = false;
		for(int i = 0; i < 20; i++) { //20 being map size
			for(int j = 0; j < 20; j++) {
				tempTile = tileMap[i][j];
				temp = distCalc(tempTile.getX(),tempTile.getY(),dest.getX(), dest.getY());
				for(Entity e: tempTile.getEntities()) {
					if(e instanceof Obstacle) {
						obstacle = true;
					}
				}
				if(!obstacle && access(map) && temp <= dist) {
					dist = temp;
					tile = tempTile;
				}
				obstacle = false;
			}
		}
		return tile;
	}
		// TODO Auto-generated method stub
	/**
	 * returns true if the map is accessible
	 * @param map
	 * @return
	 */
	private boolean access(Map map) {
		pathCheck(map, curPos, playerPos);
		if(parent.containsKey(playerPos)) {
			clear();
			return true;
		}
		clear();
		return false;
	}
	/**
	 * calls pathCheck to see if there is a way to get to the player
	 * @param map
	 * @return the player's position
	 */
	private Tile playerCheck(Map map) {
		Tile curPos = map.getEntityLocation(this);
		Tile playerPos = map.getPlayerLocation();
		pathCheck(map, curPos, playerPos);
		return playerPos;
	}
	private void pathCheck(Map map, Tile curPos, Tile playerPos) {
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
			if(queuePop.equals(playerPos)) {
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
	private double distCalc(int x1, int y1, int x2, int y2) {
	    double dist;
	    int x = Math.abs(x2-x1);
	    int y = Math.abs(y2-y1);
	    x = x*x;
	    y = y*y;
	    dist = Math.sqrt(x+y);
	    return dist;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Direction getAction() {
		// TODO Auto-generated method stub
		return null;
	}
}
