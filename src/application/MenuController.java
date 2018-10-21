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
	@FXML
	public void loadGame() {
		// load scene for game
		LoadScene load = new LoadScene(s);
		try {
			load.display();
			System.out.println("Is this being run constantly");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/*
	@FXML
	public void playGame() {
		// load scene for game
		GameEngine gameEngine = new GameEngine(generateMap());
		GameScene game = new GameScene(s, gameEngine);
		try {
			game.display();
			System.out.println("Is this being run constantly");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}*/
	
	@FXML
	public void designMap() {
		// load scene for design`
		DesignController design = new DesignController(s, new DesignEngine(10)); //arbitrary value
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
		ControlsScene controls = new ControlsScene(s);
		try {
			controls.display();
			System.out.println("Is this being run constantly");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	public void quitGame() {
		System.exit(1);
	}
	
	
}
