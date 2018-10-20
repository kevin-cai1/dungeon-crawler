package ass2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GetActionHunterGroup implements GetAction, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7774341602137163146L;
	public GetActionHunterGroup() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public void getAction(Map map,Enemy enemy) {
		Tile playerPos = map.getPlayerLocation();
		enemy.toTile(map, playerPos);
		List<Tile> shortest = new ArrayList<>();
		enemy.makeValidPath(map, playerPos, shortest);
		Tile currPos = map.getEntityLocation(enemy.getId()); //finds the hunter on the map
		currPos.removeEntity(enemy); //removes it from tis current spot
		HunterGroup hunterGroup = (HunterGroup) enemy;
		Tile neww = shortest.get(1); //retrieves [1] as [0] is the starting position of the hunter
		neww.addEntity(hunterGroup); //puts it into where it should be
		// TODO Auto-generated method stub

	}
}
