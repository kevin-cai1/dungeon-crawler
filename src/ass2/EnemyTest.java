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
	void HunterGroupTestMovement() {
		Hunter hunter2 = new Hunter(testMap.genID());
		Tile tile3 = testMap.getTile(4, 5);
		tile3.addEntity(hunter2);
		Player player2 = new Player(testMap.genID());
		Tile tile4 = testMap.getTile(9, 5);
		tile4.addEntity(player2);
		hunter2.getAction(testMap);
		assert(testMap.getEntityLocation(hunter2.getId()).getX() == 5);
		assert(testMap.getEntityLocation(hunter2.getId()).getY() == 5);
	}
	@Test
	void EnemyObstacleTest() {
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
		assert(testMap.getEntityLocation(hunter2.getId()).getX() == 4);
		assert(testMap.getEntityLocation(hunter2.getId()).getY() == 6);
	}
	@Test
	void StrategistTestMovement() {
		Strategist strategist = new Strategist(testMap.genID());
		Tile tile3 = testMap.getTile(4, 5);
		tile3.addEntity(strategist);
		Player player2 = new Player(testMap.genID());
		Tile tile4 = testMap.getTile(9, 5);
		tile4.addEntity(player2);
		strategist.getAction(testMap);
		assert(testMap.getEntityLocation(strategist.getId()).getX() == 5);
		assert(testMap.getEntityLocation(strategist.getId()).getY() == 5);
		strategist.getAction(testMap);
		strategist.getAction(testMap);
		strategist.getAction(testMap);
		strategist.getAction(testMap);
		strategist.getAction(testMap);
		assert(testMap.getEntityLocation(strategist.getId()).getX() == 9);
		assert(testMap.getEntityLocation(strategist.getId()).getY() == 4);
	}
	@Test
	void CowardMovementTest() {
		Coward coward = new Coward(testMap.genID());
		Tile tile3 = testMap.getTile(4, 5);
		tile3.addEntity(coward);
		Player player2 = new Player(testMap.genID());
		Tile tile4 = testMap.getTile(9, 5);
		tile4.addEntity(player2);
		coward.getAction(testMap);
		System.out.println(testMap.getEntityLocation(coward.getId()).getX()+" "+testMap.getEntityLocation(coward.getId()).getX());
		assert(testMap.getEntityLocation(coward.getId()).getX() == 5);
		assert(testMap.getEntityLocation(coward.getId()).getY() == 5);
		coward.getAction(testMap);
		System.out.println(testMap.getEntityLocation(coward.getId()).getX()+" "+testMap.getEntityLocation(coward.getId()).getY());
		assert(testMap.getEntityLocation(coward.getId()).getX() == 6);
		assert(testMap.getEntityLocation(coward.getId()).getY() == 5);
		tile4.removeEntity(player2);
		tile4 = testMap.getTile(8, 5);
		tile4.addEntity(player2);
		coward.getAction(testMap);
		System.out.println(testMap.getEntityLocation(coward.getId()).getX()+" "+testMap.getEntityLocation(coward.getId()).getY());
	}
}