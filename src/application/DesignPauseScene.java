package application;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DesignPauseScene {
	private Stage stage;
	private String title;
	private FXMLLoader fxmlLoader;
	
	public DesignPauseScene(Stage stage) {
		this.stage = stage;
		this.title = "Pause Menu";
		this.fxmlLoader = new FXMLLoader(getClass().getResource("Pause.fxml"));
	}
	
	public void display() throws Exception {
		stage.setTitle(title);
		fxmlLoader.setController(new PauseController(stage));
		try {
			Parent root = fxmlLoader.load();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
