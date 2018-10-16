package application;

import java.util.Set;

import ass2.Arrow;
import ass2.Bomb;
import ass2.Boulder;
import ass2.Coward;
import ass2.DesignEngine;
import ass2.Door;
import ass2.Enemy;
import ass2.Entity;
import ass2.Exit;
import ass2.FloorSwitch;
import ass2.Hound;
import ass2.HoverPotion;
import ass2.Hunter;
import ass2.InvincibilityPotion;
import ass2.Key;
import ass2.Pit;
import ass2.Player;
import ass2.Strategist;
import ass2.Sword;
import ass2.Tile;
import ass2.Treasure;
import ass2.Wall;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
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
