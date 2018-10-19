package application;

import java.io.IOException;

import ass2.MapSave;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class LoadScene {
	private Stage stage;
	private String title;
	private FXMLLoader fxmlLoader;
	
	public LoadScene(Stage stage) {
		this.stage = stage;
		this.title = "Load";
		this.fxmlLoader = new FXMLLoader(getClass().getResource("Load.fxml"));
	}
	
	public void display() throws Exception {
		stage.setTitle(title);
		fxmlLoader.setController(new LoadController(stage));
		try {
			Parent root = fxmlLoader.load();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
		initEventHandlers();
	}
	
	private void initEventHandlers() {
		Scene s = stage.getScene();
		s.addEventFilter(KeyEvent.KEY_PRESSED,Event -> {
			System.out.println(Event.getCode());
			// TODO Auto-generated method stub
			if(Event.getCode() == KeyCode.ESCAPE) {
				MenuScene menuScene = new MenuScene(stage);
				try {
					menuScene.display();
					System.out.println("Is this being run constantly");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
}
