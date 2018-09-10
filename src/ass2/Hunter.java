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
	public Direction move(Tile[][] map) {
		Tile curPos = getPosition;
		Tile playerPos = getPlayerPosition;
		queue.add(curPos);
		
		return null;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}
}
