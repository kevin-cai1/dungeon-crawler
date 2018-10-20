package application;

 import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
 import org.omg.CORBA.PUBLIC_MEMBER;
 import com.sun.glass.events.KeyEvent;
 import javafx.event.Event;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import sun.audio.AudioData;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.audio.ContinuousAudioDataStream;

public class MenuScene {
	private Stage s;
	private String title;
	private FXMLLoader fxmlLoader;
	
	
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
        audio.setVolume(0.5f);
        audio.setCycleCount(50);
        audio.play();
	}
}
