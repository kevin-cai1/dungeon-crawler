package ass2;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlayerMovementTest {
	GameEngine game;
	Map map;
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		map = new Map(20);
		game = new GameEngine(map);
	}

	@Test
	void test() {
		game.runGame();
		fail("Not yet implemented");
	}

}
