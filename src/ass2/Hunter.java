package ass2;

import java.util.ArrayList;
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
	private PriorityQueue<Direction> instructSet;
	private Set<Tile> visited;
	private static int maxPathLength;
	private java.util.Map<Integer, Integer> parent;
	public Hunter() {
		instructSet = new PriorityQueue<>();
		maxPathLength = 100;
		parent = new Map //Instead of just doing prev[i] = other value, we need to remove that index first and then put it back in at that index
		// TODO Auto-generated constructor stub
	}
	public Direction move(Tile[][] map) {
		
		return null;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}
}
