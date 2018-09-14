package ass2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public abstract class HunterGroup extends Enemy{

	public HunterGroup(int id) {
		super(id);

		// TODO Auto-generated constructor stub
	}
	/**
	 * finds a valid move to make and then moves the Hunter to that tile
	 * @param map
	 */
	@Override
	public void getAction(Map map) {
		Tile playerPos = map.getPlayerLocation();
		toTile(map, playerPos);
		List<Tile> shortest = new ArrayList<>();
		//If there is a way, then there will be a path in parent
		if(parent.containsKey(playerPos)) {
			Tile temp = playerPos;
			while(temp != null) {
				shortest.add(temp);
				temp = parent.get(temp);
			}
			Collections.reverse(shortest);
			//after we do a path search of some sort we have to clear 
			clear();
		}
		else {//if there is no path to the player then we search for the closest reachable tile.
			Tile closest = ClosestTile(map, playerPos);
			toTile(map, closest);
			while(closest != null) {
				shortest.add(closest);
				closest = parent.get(closest);
			}
			Collections.reverse(shortest);
			clear();
		}
		Tile currPos = map.getEntityLocation(this.getId()); //finds the hunter on the map
		currPos.removeEntity(this); //removes it from tis current spot
		HunterGroup hunterGroup = this;
		Tile neww = shortest.get(1); //retrieves [1] as [0] is the starting position of the hunter
		neww.addEntity(hunterGroup); //puts it into where it should be
		
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		
		stringBuilder.append(this.getClass());
		stringBuilder.append("yes\n");
		return stringBuilder.toString();
	}
}
