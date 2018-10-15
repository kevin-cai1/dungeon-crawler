package application;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class PauseController {
	private Stage stage;
	
	@FXML
	private ImageView resumeButton;
	
	@FXML
	private ImageView returntomenuButton;
	
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
	public void openOptions() {
		// load scene for design
	}
	
	@FXML
	public void resumeGame() {
		
	}
	@FXML
	public void quitGame() {
		System.exit(1);
	}
}
