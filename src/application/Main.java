package application;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
	private Stage primaryStage;

	@Override
	public void start(Stage primaryStage) throws Exception{
		primaryStage.setWidth(600);
		primaryStage.setHeight(400);
		this.primaryStage = primaryStage;
		
		new MenuScene(this.primaryStage).display();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
