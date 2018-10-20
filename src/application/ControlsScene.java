package application;

 import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class ControlsScene {
	private Stage s;
	private String title;
	private FXMLLoader fxmlLoader;
	
	public ControlsScene(Stage s) {
		this.s = s;
		this.title = "Change controls";
		this.fxmlLoader = new FXMLLoader(getClass().getResource("Controls.fxml"));
	}
		
	public void display() throws Exception {
		s.setTitle(title);
		fxmlLoader.setController(new ControlsController(s));
		try {
			Parent root = fxmlLoader.load();
			Scene scene = new Scene(root);
			s.setScene(scene);
			Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
			
			s.setX((bounds.getWidth() - s.getWidth())/2);
			s.setY((bounds.getHeight() - s.getHeight())/2);
			s.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
