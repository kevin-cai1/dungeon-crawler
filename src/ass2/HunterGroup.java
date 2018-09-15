package ass2;

import java.util.ArrayList;
import java.util.List;


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
		makeValidPath(map, playerPos, shortest);
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
