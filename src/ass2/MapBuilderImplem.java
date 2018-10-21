package ass2;

import java.util.ArrayList;

public class MapBuilderImplem implements MapBuilder {
	private Map map;
	
	public MapBuilderImplem() {
		this.map = new Map(10);			// just saying 20 to make compiler happy
		
	}

	@Override
	public Map build() {
		return map;
	}

	@Override
	public MapBuilder setWinConditions(ArrayList<WinCondition> winConditions) {
		for(WinCondition winCondition: winConditions) {
			map.addWinCondition(winCondition);
		}
		return this;
	}

	@Override
	public MapBuilder setArrayLengthMap(int arrayLength) {
		map.setArrayLengthMap(arrayLength);
		return this;
	}

	@Override
	public MapBuilder setMapEntities() {
		map.setMapTiles();
		return this;
	}

	@Override
	public MapBuilder setIdCounter() {
		map.setIdCounter();
		return this;
	}
	
	
}
