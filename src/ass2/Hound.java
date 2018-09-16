package ass2;

public class Hound extends HunterGroup{
    public Hound(int id){
        super(id);
    }
	public void shift(Map map, Tile tile) {
		Tile currPos = map.getEntityLocation(this.getId()); //finds the hunter on the map
		currPos.removeEntity(this); //removes it from tis current spot
		Hound hound = this;
		tile.addEntity(hound); //puts it into where it should be
	}
}
