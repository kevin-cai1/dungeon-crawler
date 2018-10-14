package application;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class GameController {
	@FXML
	private GridPane map;
	private Stage stage;
	public GameController(Stage s) {
		this.stage = s;
		// TODO Auto-generated constructor stub
	}
	@FXML
	public void initialize() {
		System.out.println("game controller");
	}
}
