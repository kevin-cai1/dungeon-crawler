package ass2;
/**
 * type of enemy which moves toward player until it is 3 tiles away, then starts moving away from the player
 * @author gordon
 *
 */
public class Coward extends Enemy{
	private static final long serialVersionUID = 4394549041466275612L;

	public Coward(int id){
        super(id);
        getAction = new GetActionCoward();
    }
    @Override
    public String toString() {
        return "Coward";
    }

/**
 * Moves the coward to a certain tile
 * @param map
 * @param tile
 */
    @Override
	public void shift(Map map, Tile tile) {
		Tile currPos = map.getEntityLocation(this.getId()); //finds the hunter on the map
		currPos.removeEntity(this); //removes it from its current spot
		Coward coward = this;
		tile.addEntity(coward); //puts it into where it should be
	}
	public String imgName() {
		return "coward";
	}
	@Override
	public void setOgAction() {
		// TODO Auto-generated method stub
		getAction = new GetActionCoward();
	}
	
}
