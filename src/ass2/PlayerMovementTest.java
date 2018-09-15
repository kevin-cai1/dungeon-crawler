package ass2;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

class PlayerMovementTest {
	GameEngine game;
	Map gameMap;
	Tile[][] map;
	Player player;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		gameMap = new Map(20);
		//gameMap.addWinCondition(WinCondition.Treasure);
		
		map = gameMap.getMap();

		Player player = new Player(gameMap.genID()); //this int that i added is great
		Tile t = gameMap.getTile(4, 4);
		t.addEntity(player);
		game = new GameEngine(gameMap);

		//player = new Player();
	}

	@Test
	void testValidMoveUp() {
		System.out.println("move player up");
		Map expectedGameMap = new Map(20);
		Tile[][] expectedMap = expectedGameMap.getMap();
		Player player = new Player(gameMap.genID()); //this int is great
		Tile t = expectedGameMap.getTile(4, 3);
		t.addEntity(player);
		expectedGameMap.printMap();
		System.out.println("expected map ^");

		System.out.println("\n");
		
		Tile playerLocation = gameMap.getPlayerLocation();
		game.movePlayerNorth(map, playerLocation, player);
		playerLocation = gameMap.getPlayerLocation();
		Tile expectedLocation = expectedGameMap.getPlayerLocation();
		
		gameMap.printMap();
		System.out.println("result map^");
		playerLocation = gameMap.getPlayerLocation();
		assertTrue(playerLocation.getX() == expectedLocation.getX());
		assertTrue(playerLocation.getY() == expectedLocation.getY());
	}
	
	@Test
	void testValidMoveDown() {
		System.out.println("move player down");
		Map expectedGameMap = new Map(20);
		Tile[][] expectedMap = expectedGameMap.getMap();
		Player player = new Player(gameMap.genID()); //
		Tile t = expectedGameMap.getTile(4, 5);
		t.addEntity(player);
		expectedGameMap.printMap();
		System.out.println("expected map ^");

		System.out.println("\n");
		Tile playerLocation = gameMap.getPlayerLocation();
		Tile expectedLocation = expectedGameMap.getPlayerLocation();
		game.movePlayerSouth(map, playerLocation, player);
		
		gameMap.printMap();
		System.out.println("result map^");
		playerLocation = gameMap.getPlayerLocation();
		assertTrue(playerLocation.getX() == expectedLocation.getX());
		assertTrue(playerLocation.getY() == expectedLocation.getY());
	}
	
	@Test
	void testValidMoveLeft() {
		System.out.println("move player left");
		Map expectedGameMap = new Map(20);
		Tile[][] expectedMap = expectedGameMap.getMap();
		Player player = new Player(gameMap.genID()); //great int
		Tile t = expectedGameMap.getTile(3, 4);
		t.addEntity(player);
		expectedGameMap.printMap();
		System.out.println("expected map ^");

		System.out.println("\n");
		Tile playerLocation = gameMap.getPlayerLocation();
		Tile expectedLocation = expectedGameMap.getPlayerLocation();
		game.movePlayerWest(map, playerLocation, player);
		
		gameMap.printMap();
		System.out.println("result map^");
		playerLocation = gameMap.getPlayerLocation();
		assertTrue(playerLocation.getX() == expectedLocation.getX());
		assertTrue(playerLocation.getY() == expectedLocation.getY());
	}
	
	@Test
	void testValidMoveRight() {
		System.out.println("move player right");
		Map expectedGameMap = new Map(20);
		Tile[][] expectedMap = expectedGameMap.getMap();
		Player player = new Player(gameMap.genID()); //int great
		Tile t = expectedGameMap.getTile(5, 4);
		t.addEntity(player);
		expectedGameMap.printMap();
		System.out.println("expected map ^");

		System.out.println("\n");
		Tile playerLocation = gameMap.getPlayerLocation();
		Tile expectedLocation = expectedGameMap.getPlayerLocation();
		game.movePlayerEast(map, playerLocation, player);
		
		gameMap.printMap();
		System.out.println("result map^");
		playerLocation = gameMap.getPlayerLocation();
		assertTrue(playerLocation.getX() == expectedLocation.getX());
		assertTrue(playerLocation.getY() == expectedLocation.getY());
	}
	
	@Test
	void testInvalidMove() { // try to make move into wall, player shouldn't move
		System.out.println("invalid player move");
		Map expectedGameMap = new Map(20);
		Tile[][] expectedMap = expectedGameMap.getMap();
		Player player = new Player(gameMap.genID()); //great id
		Tile t = expectedGameMap.getTile(4, 4);
		t.addEntity(player);
		Wall expectedWall = new Wall(gameMap.genID()); //great id
		Tile expectedWallTile = expectedGameMap.getTile(4, 3); // wall above the player
		expectedWallTile.addEntity(expectedWall);
		
		expectedGameMap.printMap();
		System.out.println("expected map ^");
		
		Wall wall = new Wall(gameMap.genID());// great id
		Tile wallTile = gameMap.getTile(4, 3); // wall above the player
		wallTile.addEntity(wall);
		gameMap.printMap();
		System.out.println("starting map ^");
		
		Tile playerLocation = gameMap.getPlayerLocation();
		Tile expectedLocation = expectedGameMap.getPlayerLocation();
		assertFalse(game.movePlayerNorth(map, playerLocation, player));
		
		gameMap.printMap();
		System.out.println("result map^");
		playerLocation = gameMap.getPlayerLocation();
		assertTrue(playerLocation.getX() == expectedLocation.getX());
		assertTrue(playerLocation.getY() == expectedLocation.getY());
		
	}
	
	@Test
	void testExit() { // try to make move into wall, player shouldn't move
		System.out.println("player exit");

		Wall wall = new Wall(1);
		Tile wallTile = gameMap.getTile(4, 3); // wall above the player
		wallTile.addEntity(wall);
		gameMap.printMap();
		System.out.println("starting map ^");

		Tile playerLocation = gameMap.getPlayerLocation();
		Tile expectedLocation = map[4][4];
		assertFalse(game.movePlayerNorth(map, playerLocation, player));
		
		gameMap.printMap();
		System.out.println("result map^");
		playerLocation = gameMap.getPlayerLocation();
		assertTrue(playerLocation.getX() == expectedLocation.getX());
		assertTrue(playerLocation.getY() == expectedLocation.getY());
		
	}
	

}
