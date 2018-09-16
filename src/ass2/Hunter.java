package ass2;


/**
 * 
 * @author posocer
 * Have a priority queue for each hunter. Finds the best way to get to the player. If the player is not reachable
 * it will move to the closest tile
 * 
 */
public class Hunter extends HunterGroup{
	public Hunter(int id) {
		super(id);
	}
	public void shift(Map map, Tile tile) {
		Tile currPos = map.getEntityLocation(this.getId()); //finds the hunter on the map
		currPos.removeEntity(this); //removes it from tis current spot
		Hunter hunter = this;
		tile.addEntity(hunter); //puts it into where it should be
	}
}
