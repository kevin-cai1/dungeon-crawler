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

		player = new Player(gameMap.genID()); //this int that i added is great

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
		assertTrue(playerLocation.getX() == expectedLocation.getX()); // 4
		assertTrue(playerLocation.getY() == expectedLocation.getY()); // 3
	}
	
	@Test
	void testValidMoveDown() {
		System.out.println("move player down");
		Map expectedGameMap = new Map(20);
		Tile[][] expectedMap = expectedGameMap.getMap();

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
				
		Exit exit = new Exit(3);
		Tile exitTile = gameMap.getTile(4, 3); // exit above the player
		exitTile.addEntity(exit);
		gameMap.printMap();
		System.out.println("starting map ^");

		Tile playerLocation = gameMap.getPlayerLocation();

		assertTrue(game.movePlayerNorth(map, playerLocation, player));
		assertTrue(game.getGameState() == GameState.Win);
	}
	
	@Test
	void testPit() { // try to make move into wall, player shouldn't move
		System.out.println("player pit");

				
		Pit pit = new Pit(3);
		Tile pitTile = gameMap.getTile(4, 3); // exit above the player
		pitTile.addEntity(pit);
		gameMap.printMap();
		System.out.println("starting map ^");
		
		Tile playerLocation = gameMap.getPlayerLocation();
		assertTrue(game.movePlayerNorth(map, playerLocation, player));
		assertTrue(game.getGameState() == GameState.Lose);
	}
	
	@Test
	void testSwordInventory() { // try to make move into wall, player shouldn't move
		System.out.println("player pit");

				
		Sword sword = new Sword(3);
		Tile swordTile = gameMap.getTile(4, 3); // exit above the player
		swordTile.addEntity(sword);
		gameMap.printMap();
		System.out.println("starting map ^");
		
		Tile playerLocation = gameMap.getPlayerLocation();
		assertTrue(game.movePlayerNorth(map, playerLocation, player));
		//player.getInventory() get sword item - to be done
		//assertTrue();
	}
	
	@Test
	void testPotionPickup() { // try to make move into wall, player shouldn't move
		System.out.println("player potion");

				
		InvincibilityPotion potion = new InvincibilityPotion(3);
		Tile potionTile = gameMap.getTile(4, 3); // exit above the player
		potionTile.addEntity(potion);
		gameMap.printMap();
		System.out.println("starting map ^");
		
		Tile playerLocation = gameMap.getPlayerLocation();
		assertTrue(game.movePlayerNorth(map, playerLocation, player));
		assertTrue(player.getInvincibility());
	}
	
	@Test
	void testBombPickup() { // try to make move into bomb, player should pick up
		System.out.println("player bomb");
		
		Bomb bomb = new Bomb(gameMap, gameMap.genID());
		Tile bombTile = gameMap.getTile(4, 3); // exit above the player
		bombTile.addEntity(bomb);
		gameMap.printMap();
		System.out.println("starting map ^");
		
		Tile playerLocation = gameMap.getPlayerLocation();
		assertTrue(game.movePlayerNorth(map, playerLocation, player));
		assertTrue(player.checkBomb()); //player has bomb
	}
	
	@Test
	void testMoveBoulder() { // try to make move into bomb, player should pick up
		System.out.println("player boulder");
		
		Boulder boulder = new Boulder(gameMap.genID());
		Tile boulderTile = gameMap.getTile(4, 3); // exit above the player
		boulderTile.addEntity(boulder);
		gameMap.printMap();
		System.out.println("starting map ^");
		
		Tile playerLocation = gameMap.getPlayerLocation();
		assertTrue(game.movePlayerNorth(map, playerLocation, player));
		Tile boulderLocation = gameMap.getEntityLocation(boulder.getId());
		playerLocation = gameMap.getPlayerLocation();
		// check player location
		assertTrue(playerLocation.getX() == 4);
		assertTrue(playerLocation.getY() == 3);
		// check boulder location
		assertTrue(boulderLocation.getX() == 4);
		assertTrue(boulderLocation.getY() == 2);
	}
	
	@Test
	void testTreasurePickup() { // try to make move into treasure, player should pick up
		System.out.println("player treasure");
		
		Treasure treasure = new Treasure(gameMap.genID());
		Tile treasureTile = gameMap.getTile(4, 3); // exit above the player
		treasureTile.addEntity(treasure);
		gameMap.printMap();
		System.out.println("starting map ^");
		
		Tile playerLocation = gameMap.getPlayerLocation();
		assertTrue(game.movePlayerNorth(map, playerLocation, player));
		assertTrue(player.getTreasure() == 1); //player has bomb
	//	assertTrue(game.checkWin(player, 1, gameMap.getArrayLength(), map));
	}

	
	@Test
	void testKeyPickup() {
		System.out.println("Player Picking up key");
		
		Key key = new Key(1, gameMap.genID());
		Tile keyTile = gameMap.getTile(4, 3); // key above player
		keyTile.addEntity(key);
		//gameMap.printMap();
		//System.out.println("starting map ^");
		
		Tile playerLocation = gameMap.getPlayerLocation();
		assertTrue(game.movePlayerNorth(map, playerLocation, player));
		assertTrue(player.hasKey(key));
	}
	
	@Test
	void testSuccessfulKeyUsage() {
		System.out.println("Player using key");
		
		Door door = new Door(1, gameMap.genID());
		Key key = new Key(1, gameMap.genID());
		Tile keyTile = gameMap.getTile(4, 3); // key above player
		Tile doorTile = gameMap.getTile(4, 2); // Door above key
		doorTile.addEntity(door);
		keyTile.addEntity(key);
		//gameMap.printMap();
		//System.out.println("starting map ^");
		
		Tile playerLocation = gameMap.getPlayerLocation();
		assertTrue(game.movePlayerNorth(map, playerLocation, player));
		assertTrue(player.hasKey(key));
		assertTrue(game.movePlayerNorth(map, playerLocation, player));	// check that player can move up into the door
		assertTrue(player.checkKey(door));	// check that player has correct key
		assertTrue(door.getStatus());		// check that the door is open
	}
	
	@Test
	void testUnsuccessfulKeyUsage() {
		System.out.println("Player using wrong key");
		
		Door door = new Door(2, gameMap.genID());
		Key key = new Key(1, gameMap.genID());
		Tile keyTile = gameMap.getTile(4, 3); // key above player
		Tile doorTile = gameMap.getTile(4, 2); // Door above key
		doorTile.addEntity(door);
		keyTile.addEntity(key);
		//gameMap.printMap();
		//System.out.println("starting map ^");
		
		Tile playerLocation = gameMap.getPlayerLocation();
		assertTrue(game.movePlayerNorth(map, playerLocation, player));
		assertTrue(player.hasKey(key));
		assertFalse(game.movePlayerNorth(map, playerLocation, player));	// check that player cant move up into the door
		assertFalse(player.checkKey(door));	// check that player does not have correct key
		assertFalse(door.getStatus());		// check that the door is closed
	}
	
	@Test
	void testDoorNoKey() {
		System.out.println("Player walking into door with no key");
		
		Door door = new Door(1, gameMap.genID());
		Tile doorTile = gameMap.getTile(4, 3); // Door above player
		doorTile.addEntity(door);
		//gameMap.printMap();
		//System.out.println("starting map ^");
		
		Tile playerLocation = gameMap.getPlayerLocation();
		assertFalse(game.movePlayerNorth(map, playerLocation, player)); // check that player can't move up into the door
		assertFalse(player.checkKey(door));	// check that player does not have correct key
		assertFalse(door.getStatus());		// check that the door is closed
	}
	
	@Test
	void testDoubleBoulder() {
		System.out.println("Player failing to push 2 adjacent boulders");
		
		Boulder boulder1 = new Boulder(gameMap.genID());
		Boulder boulder2 = new Boulder(gameMap.genID());
		Tile boulderTile1 = gameMap.getTile(4, 3); // Boulders above player
		Tile boulderTile2 = gameMap.getTile(4, 2);
		boulderTile1.addEntity(boulder1);
		boulderTile2.addEntity(boulder2);
		//gameMap.printMap();
		//System.out.println("starting map ^");
		
		Tile playerLocation = gameMap.getPlayerLocation();
		assertFalse(game.movePlayerNorth(map, playerLocation, player)); // check that player can't push the boulder and move up 
																		// because there is a boulder above the first one
	}
	
	@Test
	void testBoulderWall() {
		System.out.println("Player failing to push boulder into wall");
		
		Boulder boulder = new Boulder(gameMap.genID());
		Tile boulderTile = gameMap.getTile(4,  0);		
		boulderTile.addEntity(boulder); 			// place boulder next to north wall
		
		Tile playerLocation = gameMap.getPlayerLocation();
		assertTrue(game.movePlayerNorth(map, playerLocation, player));
		assertTrue(game.movePlayerNorth(map, playerLocation, player));
		assertTrue(game.movePlayerNorth(map, playerLocation, player));	// after this, player should be at (4,1)
		assertFalse(game.movePlayerNorth(map, playerLocation, player));	// since boulder is at (4, 0), this should fail
	}
	

}
