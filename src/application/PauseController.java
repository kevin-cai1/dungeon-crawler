package application;

import ass2.DesignEngine;
import ass2.GameEngine;
import ass2.Map;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class PauseController {
	private Stage stage;
	
	@FXML
	private ImageView resumeButton;
	
	@FXML
	private ImageView returnToMenuButton;
	
	@FXML
	private ImageView quitButton;
	
	@FXML
	private ImageView optionsButton;
	
	@FXML
	public void initialize() {
		System.out.println("Initialising pause menu controller");
	}
	
	public PauseController(Stage stage) {
		this.stage = stage;
	}
	
	@FXML
	public void returnToMenu() throws Exception {
		MenuScene menuScene = new MenuScene(stage);
		menuScene.display();
	}
	@FXML
	private void returnToGame() throws Exception{
		// TODO Auto-generated method stub
		DesignEngine designEngine = new DesignEngine(10);
		Map map = designEngine.load("temp.txt");
		GameScene gameScene = new GameScene(stage, new GameEngine(map));
		gameScene.display();
	}
	@FXML
	public void openOptions() {
		// load scene for design
	}
	@FXML
	public void quitGame() {
		System.exit(1);
	}
}
