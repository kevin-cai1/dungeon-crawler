package application;


import ass2.*;

import javafx.fxml.FXML;
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
		
		return gameMap;
	}
	
	
}
