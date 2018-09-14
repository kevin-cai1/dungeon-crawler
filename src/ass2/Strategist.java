package ass2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Strategist extends Enemy{
    public Strategist(int id){
        super(id);
    }

    @Override
    public String toString() {
        return null;
    }
	@Override
	public void getAction(Map map) {
		// TODO Auto-generated method stub
		Tile tile = getGoodTile(map);
		Tile playerPos = map.getPlayerLocation();
		List<Tile> shortest = new ArrayList<>();
		if(tile != null) {
			toTile(map, tile);
			
			Tile temp = tile;
			while(temp != null) {
				shortest.add(temp);
				temp = parent.get(temp);
			}
			Collections.reverse(shortest);
			clear();
		}
		else {
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
		Strategist strategist = this;
		Tile neww = shortest.get(1); //retrieves [1] as [0] is the starting position of the hunter
		neww.addEntity(strategist); //puts it into where it should be
	}
	/**
	 * find a tile close to the player which is safe to walk to. i.e. the four tiles around a player. This should always be possible unless the player is 
	 * hovering and walks over a pit and into a spot where there are walls on all 3 valid sides. in that case, it will just find the closest tile 
	 * to the player which is a valid move.
	 * @return a valid tile near the player, or if none found returns null
	 */
	private Tile getGoodTile(Map map) {
		Tile tile = null;
		Tile playerPos = map.getPlayerLocation();
		int x = playerPos.getX();
		int y = playerPos.getY();
		//North
		for(Entity e: map.getTile(x, y-1).getEntities()) {
			if(!(e instanceof Obstacle)) {
				tile = map.getTile(x, y-1);
				if(access(map,tile)) {
					return tile;
				}	
			}
		}
		//South
		for(Entity e: map.getTile(x, y+1).getEntities()) {
			if(!(e instanceof Obstacle)) {
				tile = map.getTile(x, y+1);
				if(access(map,tile)) {
					return tile;
				}
			}
		}
		//East
		for(Entity e: map.getTile(x+1, y).getEntities()) {
			if(!(e instanceof Obstacle)) {
				tile = map.getTile(x+1, y);
				if(access(map,tile)) {
					return tile;
				}
			}
		}
		//West
		for(Entity e: map.getTile(x-1, y).getEntities()) {
			if(!(e instanceof Obstacle)) {
				tile = map.getTile(x-1, y);
				if(access(map,tile)) {
					return tile;
				}
			}
		}
		return tile;
	}
}
