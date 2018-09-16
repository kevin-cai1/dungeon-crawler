package ass2;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class KillingEnemyTest {
	GameEngine game;
	Map gameMap;
	Tile[][] map;
	Player player;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		gameMap = new Map(10);
		
		map = gameMap.getMap();

		Player player = new Player(gameMap.genID());
		Tile t = gameMap.getTile(4, 4);
		t.addEntity(player);
		game = new GameEngine(gameMap);

	}
	@Test
	void testSwingSwordUp() {
		gameMap.getPlayer().putInventory(new Sword(gameMap.genID()));
		Map expectedGameMap = new Map(10);
		Tile t = expectedGameMap.getTile(4, 4);
		player = new Player(expectedGameMap.genID());
		t.addEntity(player);
		expectedGameMap.getPlayer().putInventory(new Sword(expectedGameMap.genID()));
		Hunter hunter = new Hunter(gameMap.genID());
		t = gameMap.getTile(4, 3);
		t.addEntity(hunter);
		Hound hound = new Hound(gameMap.genID());
		t = gameMap.getTile(4, 3);
		t.addEntity(hound);
		Coward coward = new Coward(gameMap.genID());
		t = gameMap.getTile(4, 3);
		t.addEntity(coward);
		hunter = new Hunter(gameMap.genID());
		t = gameMap.getTile(4, 2);
		t.addEntity(hunter);
		hunter = new Hunter(gameMap.genID());
		t = gameMap.getTile(3, 3);
		t.addEntity(hunter);
		hunter = new Hunter(gameMap.genID());
		t = gameMap.getTile(5, 3);
		t.addEntity(hunter);
		if (gameMap.getPlayer().checkSword()) {
			game.swing(Direction.NORTH);	
		}
		assertTrue(gameMap.getTile(4, 3).getEntities().size() == expectedGameMap.getTile(4, 3).getEntities().size());
		assertTrue(gameMap.getTile(4, 2).getEntities().size() ==  expectedGameMap.getTile(4, 2).getEntities().size());
		assertTrue(gameMap.getTile(3, 3).getEntities().size() ==  expectedGameMap.getTile(3, 3).getEntities().size());
		assertTrue(gameMap.getTile(5, 3).getEntities().size() ==  expectedGameMap.getTile(5, 3).getEntities().size());
	}
	
	@Test
	void testLitBomb() {
		gameMap.getPlayer().putInventory(new Sword(gameMap.genID()));
		Map expectedGameMap = new Map(10);
		Tile t = expectedGameMap.getTile(4, 4);
		player = new Player(expectedGameMap.genID());
		t.addEntity(player);
		expectedGameMap.getPlayer().putInventory(new Sword(expectedGameMap.genID()));
		Hunter hunter = new Hunter(gameMap.genID());
		t = gameMap.getTile(4, 3);
		t.addEntity(hunter);
		Hound hound = new Hound(gameMap.genID());
		t = gameMap.getTile(4, 3);
		t.addEntity(hound);
		Coward coward = new Coward(gameMap.genID());
		t = gameMap.getTile(4, 3);
		t.addEntity(coward);
		hunter = new Hunter(gameMap.genID());
		t = gameMap.getTile(4, 2);
		t.addEntity(hunter);
		hunter = new Hunter(gameMap.genID());
		t = gameMap.getTile(3, 3);
		t.addEntity(hunter);
		hunter = new Hunter(gameMap.genID());
		t = gameMap.getTile(5, 3);
		t.addEntity(hunter);
		if (gameMap.getPlayer().checkSword()) {
			game.swing(Direction.NORTH);	
		}
		
		ArrayList<Bomb> tickingBombs = new ArrayList<Bomb>();
		Bomb placedBomb = new Bomb(gameMap,gameMap.genID());
		placedBomb.placeBomb();
		tickingBombs.add(placedBomb);


		/*for(int i = 3; i > 0; i++) {
			for (Bomb bomb : tickingBombs) {
				System.out.println(bomb.getTimer());
	
				if (bomb.tick() == false) {
					System.out.println(bomb.getTimer());
					tickingBombs.remove(bomb);
				}
			}
		}*/			
			
			
			assertTrue(gameMap.getTile(4, 3).getEntities().size() == expectedGameMap.getTile(4, 3).getEntities().size());
			assertTrue(gameMap.getTile(4, 2).getEntities().size() ==  expectedGameMap.getTile(4, 2).getEntities().size());
			assertTrue(gameMap.getTile(3, 3).getEntities().size() ==  expectedGameMap.getTile(3, 3).getEntities().size());
			assertTrue(gameMap.getTile(5, 3).getEntities().size() ==  expectedGameMap.getTile(5, 3).getEntities().size());
		}
	
	}
