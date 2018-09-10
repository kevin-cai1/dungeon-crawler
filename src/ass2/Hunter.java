package ass2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
/**
 * 
 * @author posocer
 * Have a priority queue for each hunter. Finds the best way to get to the player. If the player is not reachable
 * it will move to the closest tile
 * 
 */
public class Hunter extends Entity{
	private PriorityQueue<Tile> queue;
	private List<Direction> instructSet;
	private Set<Tile> visited;
	private HashMap<Tile, Tile> parent; //Parent on the left, child on the right
	public Hunter() {
		queue = new PriorityQueue<Tile>();
		parent = new HashMap<Tile, Tile>(); //Instead of just doing prev[i] = other value, we need to remove that index first and then put it back in at that index
		visited = new HashSet<Tile>();
		instructSet = new ArrayList<Direction>();
	}
	public Direction move(Map map) {
		Tile curPos = getPosition;
		Tile playerPos = getPlayerPosition;
		Tile adjacent;
		Tile queuePop;
		queue.add(curPos);
		int curPosX = curPos.getX();
		int curPosY = curPos.getY();
		while(!queue.isEmpty()) {
			queuePop = queue.remove();
			if(queuePop.equals(playerPos)) {
				break;
			}
			//East Direction
			if(curPosX+1 < 20) {//20 being map size
				adjacent = map.getTile(curPosX+1, curPosY);
				visitCheck(adjacent, queuePop);
			}
			//West Direction
			if(curPosX-1 >= 0) {
				adjacent = map.getTile(curPosX-1, curPosY);
				visitCheck(adjacent, queuePop);
			}
			//North Direction
			if(curPosY+1 < 20) {
				adjacent = map.getTile(curPosX, curPosY+1);
				visitCheck(adjacent, queuePop);
			}
			//South Direction
			if(curPosY-1 >= 0) {
				adjacent = map.getTile(curPosX, curPosY-1);
				visitCheck(adjacent, queuePop);
			}
		}
		if(parent.containsKey(playerPos)) {
			
		}
		return null;
	}
	private void visitCheck(Tile adjacent, Tile queuePop) {
		if(!visited.contains(adjacent)) {
			visited.add(adjacent);
			for(Entity entity: adjacent.getEntities()) {
				if(!(entity instanceof Obstacle)) {
					queue.add(adjacent);
					parent.put(queuePop, adjacent);
				}
			}
		}
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}
}
