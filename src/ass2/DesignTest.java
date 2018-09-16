package ass2;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DesignTest {
	DesignEngine designEngine;
	@BeforeEach
	void setUp() throws Exception {
		designEngine = new DesignEngine(20);
	}

	@Test
	void testValidPlaceEntity() {
		Map map = designEngine.getMap();
		Strategist strategist = new Strategist(map.genID());
		designEngine.placeEntity(strategist, 5, 5);
		Tile tile = map.getTile(5, 5);
		assert(tile.getEntities().size() == 2);
	}
	@Test
	void testInvalidPlaceEntity() {
		Map map = designEngine.getMap();
		Strategist strategist = new Strategist(map.genID());
		Wall wall = new Wall(map.genID());
		designEngine.placeEntity(wall, 5, 5);
		designEngine.placeEntity(strategist, 5, 5);
		Tile tile = map.getTile(5, 5);
		assert(tile.getEntities().size() == 2);
	}
}
