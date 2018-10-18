package ass2;

public class MapBuilderImplem implements MapBuilder {
	private Map map;
	
	public MapBuilderImplem() {
		this.map = new Map(20);			// just saying 20 to make compiler happy
		
	}

	@Override
	public Map build() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MapBuilder addWinConditions(WinCondition winCondition) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MapBuilder setArrayLength(int arrayLength) {
		map.setArrayLength(arrayLength);
		return this;
	}

	@Override
	public MapBuilder setMap(Tile[][] map) {
		this.map.setMap(map);
		return this;
	}
}
