package ass2;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public abstract class Enemy extends Entity{
	protected LinkedList<Tile> queue;
	protected Set<Tile> visited;
	protected HashMap<Tile, Tile> parent; //child on the left, parent on the right
	public Enemy(int id) {
		super(id);
		queue = new LinkedList<Tile>();
		parent = new HashMap<Tile, Tile>(); //Instead of just doing prev[i] = other value, we need to remove that index first and then put it back in at that index
		visited = new HashSet<Tile>();
		// TODO Auto-generated constructor stub
	}
	
	public abstract void getAction(Map map);
	/**
	 * clears the map searching
	 */
	public void clear() {
		parent.clear();
		queue.clear();
		visited.clear();
	}
	/**
	 * Checks if a tile has already been visited
	 * @param adjacent
	 * @param queuePop
	 */
	public void visitCheck(Tile adjacent, Tile queuePop) {
		boolean obstacle = false;
		if(!visited.contains(adjacent)) {
			visited.add(adjacent);
			for(Entity entity: adjacent.getEntities()) {
				if((entity instanceof Obstacle)) {
					obstacle = true;
				}
			}
			if(!obstacle){
				if(!queue.contains(adjacent)){
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
	public Tile ClosestTile(Map map, Tile dest) {
		Tile[][] tileMap = map.getMap();
		double temp;
		double dist = 29; //the maximum distance estimated from a 20x20 map
		Tile tile = map.getEntityLocation(this.getId());//the closest reachable tile initially set to the hunter
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
				if(!obstacle && access(map, tempTile) && temp <= dist) {
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
	public boolean access(Map map, Tile position) {
		Tile curPos = map.getEntityLocation(this.getId());
		pathCheck(map, curPos, position);
		if(parent.containsKey(position)) {
			clear();
			return true;
		}
		clear();
		return false;
	}
	/**
	 * calls pathCheck to see if there is a way to get to the tile
	 * @param map
	 * @return the player's position
	 */
	public void toTile(Map map, Tile tile) {
		Tile curPos = map.getEntityLocation(this.getId());
		pathCheck(map, curPos, tile);
	}
	/**
	 * checks if there is a way to get to a certain tile
	 * @param map
	 * @param curPos
	 * @param playerPos
	 */
	public void pathCheck(Map map, Tile curPos, Tile playerPos) {
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
	/**
	 * calculates the distance between two tiles
	 * @param x1 x coordinate of tile 1
	 * @param y1 y coordinate of tile 1
	 * @param x2 x coordinate of tile 2
	 * @param y2 y coordinate of tile 2
	 * @return the distance between two tile as a double
	 */
	public double distCalc(int x1, int y1, int x2, int y2) {
	    double dist;
	    int x = Math.abs(x2-x1);
	    int y = Math.abs(y2-y1);
	    x = x*x;
	    y = y*y;
	    dist = Math.sqrt(x+y);
	    return dist;
	}
	/**
	 * makes a path from the parent nodes and adds it to shortest
	 * @param tile
	 * @param shortest
	 */
	public void makePath(Tile tile, List<Tile> shortest) {
		Tile temp = tile;
		while(temp != null) {
			shortest.add(temp);
			temp = parent.get(temp);
		}
		Collections.reverse(shortest);
		clear();
	}
	/**
	 * make a path either to the tile or to the nearest tile to the tile
	 * @param map
	 * @param tile
	 * @param shortest
	 */
	public void makeValidPath(Map map, Tile tile, List<Tile> shortest) {
		//If there is a way, then there will be a path in parent
		if(parent.containsKey(tile)) {
			makePath(tile, shortest);
		}
		else {//if there is no path to the player then we search for the closest reachable tile.
			Tile closest = ClosestTile(map, tile);
			toTile(map, closest);
			makePath(closest, shortest);
		}
	}
	public void runAway(Map map) {
		Tile playerPos = map.getPlayerLocation();
		Tile curPos = map.getEntityLocation(this.getId());
    	int curX = curPos.getX();
    	int curY = curPos.getY();
    	int playerX = playerPos.getX();
    	int playerY = playerPos.getY();
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
	public abstract void shift(Map map, Tile tile);
}

