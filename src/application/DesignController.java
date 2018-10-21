package application;

import ass2.DesignEngine;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class DesignController {
	@FXML
	private GridPane map;
	private DesignEngine designEngine;
	private Stage stage;
	private int mapSize = 10;
	private int tileSize;

	public DesignController(Stage s) {
		this.stage = s;
		this.designEngine = new DesignEngine(mapSize); //TODO TEMPORARY SET VALUE
		this.tileSize = 85 - 3 * designEngine.getMap().getArrayLength();
		// TODO Auto-generated constructor stub
	}

	@FXML
	public void initialize() {

	}
}
