package ass2;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WinConditionTest {
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
	}

	@Test
	void testTreasureWinConditionTrue() { // collect all treasures
		System.out.println("treasure win condition");
		gameMap.addWinCondition(WinCondition.Treasure);
		game.setWinConditions();

		int numTreasures = 2;
		Treasure treasure1 = new Treasure(gameMap.genID());
		Treasure treasure2 = new Treasure(gameMap.genID());

		Tile treasureTile1 = gameMap.getTile(4, 3); // treasure above the player
		Tile treasureTile2 = gameMap.getTile(3, 3); // treasure above left of player
		treasureTile1.addEntity(treasure1);
		treasureTile2.addEntity(treasure2);
		gameMap.printMap();
		System.out.println("starting map ^");
		
		Tile playerLocation = gameMap.getPlayerLocation();
		assertTrue(game.movePlayerNorth(map, playerLocation, player));
		playerLocation = gameMap.getPlayerLocation();
		assertTrue(game.movePlayerWest(map, playerLocation, player));

		assertTrue(player.getTreasure() == numTreasures); //player has treasures
		System.out.println("check win");
		assertTrue(game.checkWin(player, numTreasures, gameMap.getArrayLength(), map));
	}

	@Test
	void testTreasureWinConditionFalse() { // not all treasures collected
		System.out.println("treasure win condition");
		gameMap.addWinCondition(WinCondition.Treasure);
		game.setWinConditions();

		int numTreasures = 2;
		Treasure treasure1 = new Treasure(gameMap.genID());
		Treasure treasure2 = new Treasure(gameMap.genID());

		Tile treasureTile1 = gameMap.getTile(4, 3); // treasure above the player
		Tile treasureTile2 = gameMap.getTile(3, 3); // treasure above left of player
		treasureTile1.addEntity(treasure1);
		treasureTile2.addEntity(treasure2);
		gameMap.printMap();
		System.out.println("starting map ^");
		
		Tile playerLocation = gameMap.getPlayerLocation();
		assertTrue(game.movePlayerNorth(map, playerLocation, player));

		assertTrue(player.getTreasure() == 1); //player has treasures
		System.out.println("check win");
		assertFalse(game.checkWin(player, numTreasures, gameMap.getArrayLength(), map));
	}
	
	@Test
	void testEnemyWinConditionTrue() { // kill all enemies
		gameMap.addWinCondition(WinCondition.Enemy);
		game.setWinConditions();
		System.out.println("enemy win condition");
		int numTreasures = 0;
		int arrayLength = gameMap.getArrayLength();
		Hunter enemy1 = new Hunter(gameMap.genID());
		Hunter enemy2 = new Hunter(gameMap.genID());
		
		Tile enemyTile1 = gameMap.getTile(4, 3); // enemy above the player
		enemyTile1.addEntity(enemy1);
		
		Tile enemyTile2 = gameMap.getTile(3, 4); // enemy left of the player
		enemyTile1.addEntity(enemy2);
		
		gameMap.printMap();
		System.out.println("starting map ^");
		assertFalse(game.checkWin(player, numTreasures, arrayLength, map));
		
		game.swing(Direction.NORTH);
		game.swing(Direction.WEST);
		assertTrue(game.checkWin(player, numTreasures, gameMap.getArrayLength(), map));
	}
	
	@Test
	void testEnemyWinConditionFalse() { // collect all treasures
		gameMap.addWinCondition(WinCondition.Enemy);
		game.setWinConditions();
		System.out.println("enemy win condition");
		int numTreasures = 0;
		int arrayLength = gameMap.getArrayLength();
		Hunter enemy1 = new Hunter(gameMap.genID());
		Hunter enemy2 = new Hunter(gameMap.genID());
		
		Tile enemyTile1 = gameMap.getTile(4, 3); // enemy above the player
		enemyTile1.addEntity(enemy1);
		
		Tile enemyTile2 = gameMap.getTile(3, 4); // enemy left of the player
		enemyTile1.addEntity(enemy2);
		
		gameMap.printMap();
		System.out.println("starting map ^");
		
		assertFalse(game.checkWin(player, numTreasures, arrayLength, map));
	}
	
	@Test
	void testBoulderWinConditionTrue() { // collect switches triggered
		System.out.println("boulder win condition");
		gameMap.addWinCondition(WinCondition.Boulder);
		game.setWinConditions();

		int numTreasures = 0;
		Boulder boulder1 = new Boulder(gameMap.genID());
		FloorSwitch switch1 = new FloorSwitch(gameMap.genID());
		
		Boulder boulder2 = new Boulder(gameMap.genID());
		FloorSwitch switch2 = new FloorSwitch(gameMap.genID());

		Tile boulderTile1 = gameMap.getTile(4, 3); // treasure above the player
		Tile boulderTile2 = gameMap.getTile(3, 3); // treasure above left of player
		Tile switchTile1 = gameMap.getTile(4, 2); // above boulder1
		Tile switchTile2 = gameMap.getTile(2, 3); // next to boulder2
		boulderTile1.addEntity(boulder1);
		boulderTile2.addEntity(boulder2);
		switchTile1.addEntity(switch1);
		switchTile2.addEntity(switch2);
		gameMap.printMap();
		System.out.println("starting map ^");
		
		Tile playerLocation = gameMap.getPlayerLocation();
		assertTrue(game.movePlayerNorth(map, playerLocation, player));
		assertTrue(switch1.getStatus());
		playerLocation = gameMap.getPlayerLocation();
		assertTrue(game.movePlayerWest(map, playerLocation, player));
		assertTrue(switch2.getStatus());

		System.out.println("check win");
		assertTrue(game.checkWin(player, numTreasures, gameMap.getArrayLength(), map));
	}
	
	@Test
	void testBoulderWinConditionFalse() { // collect switches triggered
		System.out.println("boulder win condition");
		gameMap.addWinCondition(WinCondition.Boulder);
		game.setWinConditions();

		int numTreasures = 0;
		Boulder boulder1 = new Boulder(gameMap.genID());
		FloorSwitch switch1 = new FloorSwitch(gameMap.genID());
		
		Boulder boulder2 = new Boulder(gameMap.genID());
		FloorSwitch switch2 = new FloorSwitch(gameMap.genID());

		Tile boulderTile1 = gameMap.getTile(4, 3); // treasure above the player
		Tile boulderTile2 = gameMap.getTile(3, 3); // treasure above left of player
		Tile switchTile1 = gameMap.getTile(4, 2); // above boulder1
		Tile switchTile2 = gameMap.getTile(2, 3); // next to boulder2
		boulderTile1.addEntity(boulder1);
		boulderTile2.addEntity(boulder2);
		switchTile1.addEntity(switch1);
		switchTile2.addEntity(switch2);
		gameMap.printMap();
		System.out.println("starting map ^");
		
		Tile playerLocation = gameMap.getPlayerLocation();
		assertTrue(game.movePlayerNorth(map, playerLocation, player));
		assertTrue(switch1.getStatus());
		playerLocation = gameMap.getPlayerLocation();
		assertFalse(switch2.getStatus());

		System.out.println("check win");
		assertFalse(game.checkWin(player, numTreasures, gameMap.getArrayLength(), map));
	}
}
