package application;


import ass2.*;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class MenuController {
	@FXML
	private ImageView playButton;
	
	@FXML
	private ImageView designButton;
	
	@FXML
	private ImageView optionsButton;
	
	@FXML
	private ImageView quitButton;
	
	private Stage s;
	
	public MenuController(Stage s) {
		this.s = s;
	}
	
	@FXML
	public void initialize() {
			
	}
	
	public void playGame() {
		// load scene for game
		GameEngine gameEngine = new GameEngine(generateMap());
		GameScene game = new GameScene(s, gameEngine);
		try {
			game.display();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@FXML
	public void designMap() {

		// load scene for design`
		DesignScene design = new DesignScene(s);
		try {
			design.display();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	public void openOptions() {
		// load scene for design
	}
	
	@FXML
	public void quitGame() {
		System.exit(1);
	}
	
	private Map generateMap() {
		Map gameMap = new Map(10);
		Tile t = gameMap.getTile(4, 4);
		t.addEntity(new Player(gameMap.genID()));
		
		t = gameMap.getTile(1, 1);
		t.addEntity(new Hound(gameMap.genID()));
		
		t = gameMap.getTile(1, 2);
		t.addEntity(new Wall(gameMap.genID()));
		
		t = gameMap.getTile(2, 4);
		t.addEntity(new Wall(gameMap.genID()));
		
		t = gameMap.getTile(4, 2);
		t.addEntity(new Boulder(gameMap.genID()));
		
		t = gameMap.getTile(4, 6);
		t.addEntity(new Wall(gameMap.genID()));
		
		t = gameMap.getTile(1, 3);
		t.addEntity(new Sword(gameMap.genID()));
		
		t = gameMap.getTile(1, 4);
		t.addEntity(new Exit(gameMap.genID()));
		
		t = gameMap.getTile(1, 5);
		t.addEntity(new Treasure(gameMap.genID()));
		
		t = gameMap.getTile(1, 6);
		t.addEntity(new InvincibilityPotion(gameMap.genID()));
		
		t = gameMap.getTile(1, 7);
		t.addEntity(new Hunter(gameMap.genID()));
		
		t = gameMap.getTile(1, 8);
		t.addEntity(new Strategist(gameMap.genID()));
		
		t = gameMap.getTile(2, 1);
		t.addEntity(new HoverPotion(gameMap.genID()));
		
		t = gameMap.getTile(2, 2);
		t.addEntity(new Coward(gameMap.genID()));
		
		t = gameMap.getTile(6, 2);
		t.addEntity(new Door(1, gameMap.genID()));
		
		t = gameMap.getTile(6, 3);
		t.addEntity(new Key(1, gameMap.genID()));
		
		t = gameMap.getTile(6, 4);
		t.addEntity(new Bomb(gameMap,gameMap.genID()));
		
		t = gameMap.getTile(6, 6);
		t.addEntity(new Pit(gameMap.genID()));
		
		t = gameMap.getTile(4, 1);
		t.addEntity(new FloorSwitch(gameMap.genID()));
		gameMap.addWinCondition(WinCondition.Boulder);
		gameMap.addWinCondition(WinCondition.Treasure);
		return gameMap;
	}
	
	private Map generateMap2() {
		Map gameMap = new Map(7);
		Tile t = gameMap.getTile(1, 1);
		t.addEntity(new Player(gameMap.genID()));
		Tile t2;
		for (int i = 0; i < 7; i++) {
			t = gameMap.getTile(0, i);
			t.addEntity(new Wall(gameMap.genID()));
			
			t2 = gameMap.getTile(i, 6);
			t2.addEntity(new Wall(gameMap.genID()));
			
			t2 = gameMap.getTile(i, 0);
			t2.addEntity(new Wall(gameMap.genID()));
			
			t2 = gameMap.getTile(6, i);
			t2.addEntity(new Wall(gameMap.genID()));
			
		}
		
		t2 = gameMap.getTile(2,1);
		t2.addEntity(new Wall(gameMap.genID()));
		
		t2 = gameMap.getTile(2,2);
		t2.addEntity(new Wall(gameMap.genID()));
		
		t2 = gameMap.getTile(2,3);
		t2.addEntity(new Wall(gameMap.genID()));
		
		t2 = gameMap.getTile(2,4);
		t2.addEntity(new Wall(gameMap.genID()));
		
		t2 = gameMap.getTile(3, 3);
		t2.addEntity(new Wall(gameMap.genID()));
		
		t2 = gameMap.getTile(4, 2);
		t2.addEntity(new Wall(gameMap.genID()));
		
		t2 = gameMap.getTile(4, 5);
		t2.addEntity(new Wall(gameMap.genID()));
		
		t2 = gameMap.getTile(5, 5);
		t2.addEntity(new Wall(gameMap.genID()));
		
		t2 = gameMap.getTile(3, 2);
		t2.addEntity(new Exit(gameMap.genID()));
		

		
		return gameMap;
	}
	
	
}
