package ass2;

/**
 * positions itself so that the player is between itself and the hunter to try and corner the player
 * @author gordon
 *
 */
public class Hound extends HunterGroup{
	private static final long serialVersionUID = -6198662982200874264L;
	public Hound(int id){
        super(id);
    }
	public void shift(Map map, Tile tile) {
		Tile currPos = map.getEntityLocation(this.getId()); //finds the hunter on the map
		currPos.removeEntity(this); //removes it from tis current spot
		Hound hound = this;
		tile.addEntity(hound); //puts it into where it should be
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Hound";
	}
	public String imgName() {
		return "hound";
	}
}
