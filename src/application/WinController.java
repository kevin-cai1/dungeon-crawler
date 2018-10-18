package application;


import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class WinController {
	private Stage stage;
	
	@FXML
	private ImageView returnToMenuButton;
	
	@FXML
	private ImageView quitButton;
	
	@FXML
	public void returnToMenu() throws Exception {
		MenuScene menuScene = new MenuScene(stage);
		menuScene.display();
	}
	
	@FXML
	public void initialize() {
		System.out.println("Initialising victory screen");
	}
	
	@FXML
	public void quitGame() {
		System.exit(1);
	}
	
	public WinController(Stage stage) {
		this.stage = stage;
	}
}
