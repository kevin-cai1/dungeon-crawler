package application;

 import java.io.IOException;
 import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.AudioClip;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class MenuScene {
	private Stage s;
	private String title;
	private FXMLLoader fxmlLoader;
	private static boolean played = false;
	
	
	public MenuScene(Stage s) {
		this.s = s;
		this.title = "Main Menu";
		this.fxmlLoader = new FXMLLoader(getClass().getResource("Menu.fxml"));
	}
		
	public void display() throws Exception {
		s.setTitle(title);
		fxmlLoader.setController(new MenuController(s));
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
        AudioClip audio = new AudioClip(getClass().getResource("/application/sound/bgm.mp3").toExternalForm());
        if (played == false) {
	        audio.setVolume(0.5f);
	        audio.setCycleCount(50);
	        audio.play();
	        played = true;
        }
	}
}
