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
	void HunterGroupMovementTest() { //tests if the hunter moves towards the player
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
	void HunterGroupPathTest() { //finds the best path to the player
		Hunter hunter2 = new Hunter(testMap.genID());
		Tile tile3 = testMap.getTile(4, 5);
		tile3.addEntity(hunter2);
		Player player2 = new Player(testMap.genID());
		Tile tile4 = testMap.getTile(9, 5);
		tile4.addEntity(player2);
		hunter2.getAction(testMap);
		assert(testMap.getEntityLocation(hunter2.getId()).getX() == 5);
		assert(testMap.getEntityLocation(hunter2.getId()).getY() == 5);
		hunter2.getAction(testMap);
		assert(testMap.getEntityLocation(hunter2.getId()).getX() == 6);
		assert(testMap.getEntityLocation(hunter2.getId()).getY() == 5);
		hunter2.getAction(testMap);
		assert(testMap.getEntityLocation(hunter2.getId()).getX() == 7);
		assert(testMap.getEntityLocation(hunter2.getId()).getY() == 5);
		hunter2.getAction(testMap);
		assert(testMap.getEntityLocation(hunter2.getId()).getX() == 8);
		assert(testMap.getEntityLocation(hunter2.getId()).getY() == 5);
	}
	@Test
	void EnemyObstacleTest() { //checks that the hunter/ any enemy can avoid obstacles
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
	void EnemyObstaclePathTest() { //checks that the hunter/enemy can find a path which avoids obstacles
		Hunter hunter2 = new Hunter(testMap.genID());
		Tile tile3 = testMap.getTile(4, 5);
		tile3.addEntity(hunter2);
		Wall wall = new Wall(testMap.genID());
		Tile tile = testMap.getTile(5, 5);
		tile.addEntity(wall);
		tile = testMap.getTile(4, 7);
		tile.addEntity(wall);
		tile = testMap.getTile(5, 7);
		tile.addEntity(wall);
		tile = testMap.getTile(5, 3);
		tile.addEntity(wall);
		tile = testMap.getTile(6, 4);
		tile.addEntity(wall);
		tile = testMap.getTile(6, 5);
		tile.addEntity(wall);
		tile = testMap.getTile(7, 6);
		tile.addEntity(wall);
		Player player2 = new Player(testMap.genID());
		Tile tile4 = testMap.getTile(8, 5);
		tile4.addEntity(player2);
		hunter2.getAction(testMap);
		assert(testMap.getEntityLocation(hunter2.getId()).getX() == 4);
		assert(testMap.getEntityLocation(hunter2.getId()).getY() == 6);
		hunter2.getAction(testMap);
		assert(testMap.getEntityLocation(hunter2.getId()).getX() == 5);
		assert(testMap.getEntityLocation(hunter2.getId()).getY() == 6);
		hunter2.getAction(testMap);
		assert(testMap.getEntityLocation(hunter2.getId()).getX() == 6);
		assert(testMap.getEntityLocation(hunter2.getId()).getY() == 6);
		hunter2.getAction(testMap);
		assert(testMap.getEntityLocation(hunter2.getId()).getX() == 6);
		assert(testMap.getEntityLocation(hunter2.getId()).getY() == 7);
		hunter2.getAction(testMap);
		assert(testMap.getEntityLocation(hunter2.getId()).getX() == 7);
		assert(testMap.getEntityLocation(hunter2.getId()).getY() == 7);
		hunter2.getAction(testMap);
		assert(testMap.getEntityLocation(hunter2.getId()).getX() == 8);
		assert(testMap.getEntityLocation(hunter2.getId()).getY() == 7);
		hunter2.getAction(testMap);
		assert(testMap.getEntityLocation(hunter2.getId()).getX() == 8);
		assert(testMap.getEntityLocation(hunter2.getId()).getY() == 6);
		hunter2.getAction(testMap);
		assert(testMap.getEntityLocation(hunter2.getId()).getX() == 8);
		assert(testMap.getEntityLocation(hunter2.getId()).getY() == 5);
	}
	@Test
	void StrategistTestMovement() { //tests that the strategist will try and cut off the player, but not go to the tile the player is on
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
	void CowardMovement3BlocksTest() { //checks that when the coward moves within 3 blocks that it will start running away
		Coward coward = new Coward(testMap.genID());
		Tile tile3 = testMap.getTile(4, 5);
		tile3.addEntity(coward);
		Player player2 = new Player(testMap.genID());
		Tile tile4 = testMap.getTile(9, 5);
		tile4.addEntity(player2);
		coward.getAction(testMap);
		assert(testMap.getEntityLocation(coward.getId()).getX() == 5);
		assert(testMap.getEntityLocation(coward.getId()).getY() == 5);
		coward.getAction(testMap);
		assert(testMap.getEntityLocation(coward.getId()).getX() == 6);
		assert(testMap.getEntityLocation(coward.getId()).getY() == 5);
		coward.getAction(testMap);
		assert(testMap.getEntityLocation(coward.getId()).getX() == 5);
		assert(testMap.getEntityLocation(coward.getId()).getY() == 5);
	}
	@Test
	void CowardRunsAwayTest() { //if the player starts walking towards the coward, the coward will run away
		Coward coward = new Coward(testMap.genID());
		Tile tile3 = testMap.getTile(4, 5);
		tile3.addEntity(coward);
		Player player2 = new Player(testMap.genID());
		Tile tile4 = testMap.getTile(9, 5);
		tile4.addEntity(player2);
		coward.getAction(testMap);
		//System.out.println(testMap.getEntityLocation(coward.getId()).getX()+" "+testMap.getEntityLocation(coward.getId()).getX());
		assert(testMap.getEntityLocation(coward.getId()).getX() == 5);
		assert(testMap.getEntityLocation(coward.getId()).getY() == 5);
		coward.getAction(testMap);
		//System.out.println(testMap.getEntityLocation(coward.getId()).getX()+" "+testMap.getEntityLocation(coward.getId()).getY());
		assert(testMap.getEntityLocation(coward.getId()).getX() == 6);
		assert(testMap.getEntityLocation(coward.getId()).getY() == 5);
		tile4.removeEntity(player2);
		tile4 = testMap.getTile(8, 5);
		tile4.addEntity(player2);
		coward.getAction(testMap);
		assert(testMap.getEntityLocation(coward.getId()).getX() == 5);
		assert(testMap.getEntityLocation(coward.getId()).getY() == 5);
	}
}