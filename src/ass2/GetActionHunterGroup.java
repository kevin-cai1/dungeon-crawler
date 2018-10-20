package ass2;

import java.util.ArrayList;
import java.util.List;

public abstract class GetActionHunterGroup implements GetAction {

	public void getAction(Map map) {
		Tile playerPos = map.getPlayerLocation();
		toTile(map, playerPos);
		List<Tile> shortest = new ArrayList<>();
		makeValidPath(map, playerPos, shortest);
		Entity entity = getEntity();
		Tile currPos = map.getEntityLocation(entity.getId()); //finds the hunter on the map
		currPos.removeEntity(entity); //removes it from tis current spot
		HunterGroup hunterGroup = (HunterGroup) entity;
		Tile neww = shortest.get(1); //retrieves [1] as [0] is the starting position of the hunter
		neww.addEntity(hunterGroup); //puts it into where it should be
		// TODO Auto-generated method stub

	}
	abstract protected void toTile(Map map, Tile tile);
	abstract protected void makeValidPath(Map map, Tile tile, List<Tile> shortest);
	abstract protected Entity getEntity();
}
