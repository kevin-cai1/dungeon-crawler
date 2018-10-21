package ass2;

public class Strategist extends Enemy{
	private static final long serialVersionUID = -3475992452173522141L;
	public Strategist(int id){
        super(id);
        this.getAction = new GetActionStrategist();
        
    }

    @Override
    public String toString() {
        return "Strategist";
    }
	/**
	 * find a tile close to the player which is safe to walk to. i.e. the four tiles around a player. This should always be possible unless the player is 
	 * hovering and walks over a pit and into a spot where there are walls on all 3 valid sides. in that case, it will just find the closest tile 
	 * to the player which is a valid move.
	 * @return a valid tile near the player, or if none found returns null
	 */
	public Tile getGoodTile(Map map) {
		Tile tile = null;
		Tile playerPos = map.getPlayerLocation();
		int x = playerPos.getX();
		int y = playerPos.getY();
		//North
		if(y > 0){
			for(Entity e: map.getTile(x, y-1).getEntities()) {
				if(!(e instanceof Obstacle)) {
					tile = map.getTile(x, y-1);
					if(access(map,tile)) {
						return tile;
					}
				}
			}
		}
		//South
		if(y < map.getArrayLength()-1){
			for(Entity e: map.getTile(x, y+1).getEntities()) {
				if(!(e instanceof Obstacle)) {
					tile = map.getTile(x, y+1);
					if(access(map,tile)) {
						return tile;
					}
				}
			}
		}
		//East
		if(x < map.getArrayLength()-1){
			for(Entity e: map.getTile(x+1, y).getEntities()) {
				if(!(e instanceof Obstacle)) {
					tile = map.getTile(x+1, y);
					if(access(map,tile)) {
						return tile;
					}
				}
			}
		}

		//West
		if( x > 0){
			for(Entity e: map.getTile(x-1, y).getEntities()) {
				if(!(e instanceof Obstacle)) {
					tile = map.getTile(x-1, y);
					if(access(map,tile)) {
						return tile;
					}
				}
			}
		}
		return null;
	}
	public void shift(Map map, Tile tile) {
		Tile currPos = map.getEntityLocation(this.getId()); //finds the hunter on the map
		currPos.removeEntity(this); //removes it from tis current spot
		Strategist strategist = this;
		tile.addEntity(strategist); //puts it into where it should be
	}
	public String imgName() {
		return "strategist";
	}

	@Override
	public void setOgAction() {
		getAction = new GetActionStrategist();
		// TODO Auto-generated method stub
		
	}
}
