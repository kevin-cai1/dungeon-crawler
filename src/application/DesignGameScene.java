package application;

import ass2.DesignEngine;
import ass2.GameEngine;
import ass2.Map;
import ass2.MapSave;
import javafx.stage.Stage;

public class DesignGameScene extends GameSceneAbstract {

	public DesignGameScene(Stage s, GameEngine game) {
		super(s, game);
		// TODO Auto-generated constructor stub
	}

	@Override
	void pauseGame() {
		goBack();
	}

	@Override
	void displayWinScreen() {
		goBack();

	}
	@Override
	void displayLoseScreen() {
		goBack();
		// TODO Auto-generated method stub

	}

	private void goBack() {
		MapSave mapSave = new MapSave();
		Map map = mapSave.load("temp_design");
		DesignEngine designEngine = new DesignEngine(10);
		designEngine.setMap(map);
		DesignController designScene = new DesignController(s, designEngine);
		designScene.display();
	}
}
