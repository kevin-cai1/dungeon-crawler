package application;

import java.awt.Button;

import javafx.fxml.FXML;
import javafx.stage.Stage;

public class LoseScreenController {
	private Stage stage;
	
	@FXML
	private Button returnToMenuButton;
	
	@FXML
	private Button quitButton;
	
	@FXML
	public void returnToMenu() throws Exception {
		MenuScene menuScene = new MenuScene(stage);
		menuScene.display();
	}
	
	@FXML
	public void quitGame() {
		System.exit(1);
	}
	
	public LoseScreenController(Stage stage) {
		this.stage = stage;
	}
}