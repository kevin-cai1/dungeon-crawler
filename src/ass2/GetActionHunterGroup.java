package ass2;

import java.util.ArrayList;
import java.util.List;

public abstract class GetActionHunterGroup extends Enemy implements GetAction {
	private static final long serialVersionUID = -7638866198796053879L;
	public GetActionHunterGroup(int id) {
		super(id);
		// TODO Auto-generated constructor stub
	}
	public void getAction(Map map) {
		Tile playerPos = map.getPlayerLocation();
		toTile(map, playerPos);
		List<Tile> shortest = new ArrayList<>();
		makeValidPath(map, playerPos, shortest);
		Tile currPos = map.getEntityLocation(this.getId()); //finds the hunter on the map
		currPos.removeEntity(this); //removes it from tis current spot
		HunterGroup hunterGroup = (HunterGroup) this;
		Tile neww = shortest.get(1); //retrieves [1] as [0] is the starting position of the hunter
		neww.addEntity(hunterGroup); //puts it into where it should be
		// TODO Auto-generated method stub

	}
}
