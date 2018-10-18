package application;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoseScreen {
	private Stage stage;
	private String title;
	private FXMLLoader fxmlLoader;
	
	public LoseScreen(Stage stage) {
		this.stage = stage;
		this.title = "Defeat";
		this.fxmlLoader = new FXMLLoader(getClass().getResource("Lose.fxml"));
	}
	
	@FXML
	public void initialize() {
		System.out.println("Initialising defeat screen");
	}
	
	public void display() throws Exception {
		stage.setTitle(title);
		fxmlLoader.setController(new LoseScreenController(stage));
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
