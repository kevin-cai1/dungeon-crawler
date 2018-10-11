package application;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DesignScene {
	private Stage s;
	private String title;
	private FXMLLoader fxmlLoader;
	
	public DesignScene(Stage s) {
		this.s = s;
		this.title = "Design";
		this.fxmlLoader = new FXMLLoader(getClass().getResource("Design.fxml"));
	}
		
	public void display() throws Exception {
		s.setTitle(title);
		fxmlLoader.setController(new GameController());
	
		try {
			Parent root = fxmlLoader.load();
			s.setScene(new Scene(root));
			s.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
