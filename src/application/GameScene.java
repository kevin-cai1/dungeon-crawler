package application;


import ass2.*;
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