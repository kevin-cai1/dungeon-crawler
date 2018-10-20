package ass2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GetActionStrategist implements GetAction {

	public GetActionStrategist() {
		// TODO Auto-generated constructor stub
	}
    /**
     * calls getGoodTile to move Strategist towards the players next most likely location, or the next closest tile if there is no good tile
     */
	@Override
	public void getAction(Map map, Enemy enemy) {
		// TODO Auto-generated method stub
		Strategist strategist = (Strategist) enemy;
		Tile tile = strategist.getGoodTile(map);
		Tile playerPos = map.getPlayerLocation();
		List<Tile> shortest = new ArrayList<>();
		if(tile != null) {
			strategist.toTile(map, tile);
			
			Tile temp = tile;
			while(temp != null) {
				shortest.add(temp);
				temp = strategist.parent.get(temp);
			}
			Collections.reverse(shortest);
			strategist.clear();
		}
		else {
			Tile closest = strategist.ClosestTile(map, playerPos);
			strategist.toTile(map, closest);
			while(closest != null) {
				shortest.add(closest);
				closest = strategist.parent.get(closest);
			}
			Collections.reverse(shortest);
			strategist.clear();
		}
		Tile currPos = map.getEntityLocation(strategist.getId()); //finds the hunter on the map
		currPos.removeEntity(strategist); //removes it from tis current spot
		Tile neww = shortest.get(1); //retrieves [1] as [0] is the starting position of the hunter
		neww.addEntity(strategist); //puts it into where it should be

	}

}
