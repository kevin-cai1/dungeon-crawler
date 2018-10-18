package application;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class WinScreen {
	private Stage stage;
	private String title;
	private FXMLLoader fxmlLoader;
	
	public WinScreen(Stage stage) {
		this.stage = stage;
		this.title = "Victory";
		this.fxmlLoader = new FXMLLoader(getClass().getResource("Win.fxml"));
	}
	
	@FXML
	public void initialize() {
		System.out.println("Initialising victory screen");
	}
	
	public void display() throws Exception {
		stage.setTitle(title);
		fxmlLoader.setController(new WinScreenController(stage));
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
