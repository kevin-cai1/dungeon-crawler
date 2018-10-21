package application;

import java.io.File;
import java.util.Optional;

import ass2.GameEngine;
import ass2.Map;
import ass2.MapSave;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class LoadController {
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
	
	public LoadController(Stage stage) {
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
		MapSave mapSave = new MapSave();
		Map map = mapSave.load("temp");
		GameScene gameScene = new GameScene(stage, new GameEngine(map));
		gameScene.display();
	}
	@FXML
	public void presetMap() {
		// load scene for game
		MapSave ms = new MapSave();
		Map map = ms.load("preset_map1");
		GameEngine gameEngine = new GameEngine(map);
		GameScene game = new GameScene(stage, gameEngine);
		try {
			game.display();
			System.out.println("Is this being run constantly");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	private void loadMap() {
		TextInputDialog dialog = new TextInputDialog("Map Name");
		dialog.setTitle("Load Map");
		dialog.setHeaderText("Load Map");
		dialog.setContentText("Please enter the map name:");
		Optional<String> result = dialog.showAndWait();
		if(result.isPresent()) {
			if (new File(result.get()).exists()) {
				MapSave ms = new MapSave();
				Map map = ms.load(result.get());
				GameEngine gameEngine = new GameEngine(map);
				GameScene game = new GameScene(stage, gameEngine);
				try {
					game.display();
					System.out.println("Is this being run constantly");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Warning");
				alert.setHeaderText("File does not exist");
				alert.setContentText("Please enter a valid map file");
				alert.showAndWait();
			}
			// TODO Auto-generated method stub
		}
	}
	
	
}
