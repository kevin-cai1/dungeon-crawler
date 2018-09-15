package ass2;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EnemyTest {
	private Map testMap;
	@BeforeEach
	void setUp() throws Exception {
		testMap = new Map(20);
	}

	@Test
	void HunterTestMovement() {
		Hunter hunter2 = new Hunter(testMap.genID());
		Tile tile3 = testMap.getTile(4, 5);
		tile3.addEntity(hunter2);
		Player player2 = new Player(testMap.genID());
		Tile tile4 = testMap.getTile(9, 5);
		tile4.addEntity(player2);
		hunter2.getAction(testMap);
		System.out.println(testMap.getEntityLocation(hunter2.getId()).getX());
		assert(testMap.getEntityLocation(hunter2.getId()).getX() == 5);
		assert(testMap.getEntityLocation(hunter2.getId()).getY() == 5);
	}
	@Test
	void HunterObstacleTest() {
		Hunter hunter2 = new Hunter(testMap.genID());
		Tile tile3 = testMap.getTile(4, 5);
		tile3.addEntity(hunter2);
		Wall wall = new Wall(testMap.genID());
		Tile tile = testMap.getTile(5, 5);
		tile.addEntity(wall);
		Player player2 = new Player(testMap.genID());
		Tile tile4 = testMap.getTile(9, 5);
		tile4.addEntity(player2);
		hunter2.getAction(testMap);
		System.out.println(testMap.getEntityLocation(hunter2.getId()).getX());
		assert(testMap.getEntityLocation(hunter2.getId()).getX() == 4);
		assert(testMap.getEntityLocation(hunter2.getId()).getY() == 6);
	}
}
