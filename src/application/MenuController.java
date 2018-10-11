package application;


import ass2.Exit;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class MenuController {
	@FXML
	private ImageView playButton;
	
	@FXML
	private ImageView designButton;
	
	@FXML
	private ImageView optionsButton;
	
	@FXML
	private ImageView quitButton;
	
	private Stage s;
	
	public MenuController(Stage s) {
		this.s = s;
	}
	
	@FXML
	public void initialize() {
			
	}
	
	public void playGame() {
		// load scene for game
		GameScene game = new GameScene(s);
		try {
			game.display();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@FXML
	public void designMap() {

		// load scene for design`
		DesignScene design = new DesignScene(s);
		try {
			design.display();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	public void openOptions() {
		// load scene for design
	}
	
	@FXML
	public void quitGame() {
		System.exit(1);
	}
	
	
}