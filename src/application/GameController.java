package application;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

public class GameController {
	@FXML
	private GridPane map;
	
	@FXML
	public void initialize() {
		System.out.println("game controller");
	}
}
