package application;


import java.util.ArrayList;

import ass2.*;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class GameScene extends GameSceneAbstract{
	public GameScene(Stage s, GameEngine game) {
		super(s, game);
	}
	@Override
	void pauseGame() {
		try {
			MapSave mapSave = new MapSave();
			mapSave.save("temp",game.getGameMap());
			PauseScene pauseScene = new PauseScene(s);
			pauseScene.display();
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}
	@Override
	void displayWinScreen() {
		try {
			WinScene winScene = new WinScene(s);
			winScene.display();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	void displayLoseScreen() {
		try {
			LoseScene loseScene = new LoseScene(s);
			loseScene.display();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
 }