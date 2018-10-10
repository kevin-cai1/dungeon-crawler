package application;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class GameScene {
	private Stage s;
	private String title;
	private FXMLLoader fxmlLoader;
	private final int tileSize = 60;
	private int mapSize = 10;
	
	public GameScene(Stage s) {
		this.s = s;
		this.title = "Game";
		this.fxmlLoader = new FXMLLoader(getClass().getResource("Game.fxml"));
	}
		
	public void display() throws Exception {
		s.setTitle(title);
		s.setScene(generateGrid());
		s.setResizable(false);
		s.sizeToScene();
		Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
		
		s.setX((bounds.getWidth() - s.getWidth())/2);
		s.setY((bounds.getHeight() - s.getHeight())/2);
		s.show();
		
	}
	
	public Scene generateGrid() {
		Image b = createImage(Color.BLACK);
        Image w = createImage(Color.WHITE);
        Image[][] grid = new Image[mapSize][mapSize];
        for (int i = 0; i < mapSize; i++) {
        	for (int j = 0; j < mapSize; j++) {
        		grid[i][j] = b;
        		
        	}
        }

        GridPane gridPane = new GridPane();
        // for visualizing the different squares:
        gridPane.setHgap(2);
        gridPane.setVgap(2);
        gridPane.setStyle("-fx-background-color: grey;");

        for (int y = 0 ; y < grid.length ; y++) {
            for (int x = 0 ; x < grid[y].length ; x++) {
                //ImageView imageView = new ImageView(grid[y][x]);
            	ImageView imageView = new ImageView(new Image("application/Sprites/cobble_blood1.png"));
                imageView.setFitWidth(tileSize);
                imageView.setFitHeight(tileSize);
                gridPane.add(imageView, x, y);

                if (x == 4 && y == 3) {
                	System.out.println("yes");
	                ImageView secondimage = new ImageView(new Image("application/Sprites/human_m.png"));
	                secondimage.setFitHeight(tileSize);
	                secondimage.setFitWidth(tileSize);
	                gridPane.add(secondimage, x, y);
                }
            }
        }
        Scene scene = new Scene(gridPane);
        scene.setFill(Color.BLACK);
        return scene;
	}
	
	private Image createImage(Color color) {
		WritableImage image = new WritableImage(1, 1);
        image.getPixelWriter().setColor(0, 0, color);
        return image ;
	}
}
