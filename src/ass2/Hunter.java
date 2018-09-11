package ass2;

import java.util.*;

import javafx.scene.Node;
/**
 * 
 * @author posocer
 * Have a priority queue for each hunter. Finds the best way to get to the player. If the player is not reachable
 * it will move to the closest tile
 * 
 */
public class Hunter extends Enemy{
	private LinkedList<Tile> queue;
	private List<Direction> instructSet;

	private Set<Tile> visited;
	private HashMap<Tile, Tile> parent; //child on the left, parent on the right
	public Hunter() {
		queue = new LinkedList<Tile>();
		parent = new HashMap<Tile, Tile>(); //Instead of just doing prev[i] = other value, we need to remove that index first and then put it back in at that index
		visited = new HashSet<Tile>();
		instructSet = new ArrayList<Direction>();
	}
	public Direction move(Map map) {
		Tile[][] mapMap = map.getMap();
		
		Tile curPos = mapMap[0][1];
		Tile playerPos = mapMap[5][5];
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
			//North Direction
			if(popPosY+1 < 20) {
				adjacent = map.getTile(popPosX, popPosY+1);
				visitCheck(adjacent, queuePop);
			}
			//South Direction
			if(popPosY-1 >= 0) {
				adjacent = map.getTile(popPosX, popPosY-1);
				visitCheck(adjacent, queuePop);
			}
		}
		if(parent.containsKey(playerPos)) {
			Tile temp = playerPos;
			List<Tile> shortest = new ArrayList<>();
			while(temp != null) {
				shortest.add(temp);
				temp = parent.get(temp);
			}
			Collections.reverse(shortest);
		}
		return Direction.NORTH;
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
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}
}
