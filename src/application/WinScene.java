package application;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.AudioClip;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class WinScene {
	private Stage stage;
	private String title;
	private FXMLLoader fxmlLoader;
	
	public WinScene(Stage stage) {
		this.stage = stage;
		this.title = "Victory";
		this.fxmlLoader = new FXMLLoader(getClass().getResource("Win.fxml"));
	}
	
	
	public void display() throws Exception {
		stage.setTitle(title);
		fxmlLoader.setController(new WinController(stage));
		try {
			Parent root = fxmlLoader.load();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
			stage.setX((bounds.getWidth() - stage.getWidth())/2);
			stage.setY((bounds.getHeight() - stage.getHeight())/2);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
        AudioClip audio = new AudioClip(getClass().getResource("/application/sound/win.wav").toExternalForm());
        audio.play();
	}
}
