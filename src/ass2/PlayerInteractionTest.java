package ass2;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlayerInteractionTest {
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

		Player player = new Player();
		Tile t = gameMap.getTile(4, 4);
		t.addEntity(player);
		game = new GameEngine(gameMap);

	}
	@Test
	void testSwingSwordUp() {
		//System.out.println("swing sword up");
		Sword sword = new Sword();
		Map expectedGameMap = new Map(10);
		Player player = new Player();
		Tile t = gameMap.getTile(4, 4);
		t.addEntity(player);

		Hunter hunter = new Hunter();
		t = gameMap.getTile(4, 3);
		t.addEntity(hunter);
		
		
		
		System.out.println("\n");
		Tile playerLocation = gameMap.getPlayerLocation();
		Tile expectedLocation = expectedGameMap.getPlayerLocation();
		game.movePlayerNorth(map, playerLocation, player);
		
		gameMap.printMap();
		System.out.println("result map^");
		playerLocation = gameMap.getPlayerLocation();
		assertTrue(playerLocation.getX() == expectedLocation.getX());
		assertTrue(playerLocation.getY() == expectedLocation.getY());
	}

}
