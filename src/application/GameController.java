package application;


import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class GameController {
	
	@FXML
	private Stage stage;
	
	public GameController(Stage stage) {
		this.stage = stage;
	}
	
	@FXML
	private GridPane map;
	
	@FXML
	public void initialize() {
		System.out.println("game controller");
	}
	
	@FXML
	public void handleKeyPress(KeyEvent key) {
		if (key.getCode().isArrowKey()) {
			System.out.println("WTF");
		}
	}
}
