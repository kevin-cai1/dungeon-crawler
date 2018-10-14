package application;

import java.io.IOException;

import ass2.*;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class GameScene {
	private Stage s;
	private String title;
	private FXMLLoader fxmlLoader;
	private final int tileSize = 60;	// **SCALE TILESIZE DEPENDING ON MAP SIZE
	private int mapSize = 10;
	
	public GameScene(Stage s) {
		this.s = s;
		this.title = "Game";
		this.fxmlLoader = new FXMLLoader(getClass().getResource("Game.fxml"));
	}
		
	public void display() throws Exception {
		s.setTitle(title);
		Scene scene = generateGrid();
		Map playMap = generateMap();
		GameEngine gameEngine = new GameEngine(playMap);
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				switch (event.getCode()) {
					case W:		System.out.println("move up") ;	break;
					case S:		System.out.println("move down");break;
					case D:		System.out.println("move right");break;
					case A:		System.out.println("move left");break;
					case Q: 		System.out.println("bomb"); break;
					case E:		System.out.println("Sword"); break;
					case R: 		System.out.println("arrow"); break;
					case ESCAPE:	try {
						goHome();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} break;
				default:
					break;
				}
			}
		});
		s.setScene(scene);
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
        
        Tile[][] map = generateMap().getMap();
        
        GridPane gridPane = new GridPane();
        // for visualizing the different squares:
        //gridPane.setHgap(2);
        //gridPane.setVgap(2);
        //gridPane.setStyle("-fx-background-color: grey;");
        
        // loop that fills in the image
        for (int y = 0 ; y < map.length ; y++) {
            for (int x = 0 ; x < map[y].length ; x++) {
                //ImageView imageView = new ImageView(grid[y][x]);
            	// add a floor to every single tile ^^
            	ImageView floorImage = new ImageView(new Image("application/Sprites/cobble_blood1.png"));
                floorImage.setFitWidth(tileSize);
                floorImage.setFitHeight(tileSize);
                gridPane.add(floorImage, x, y);
                // ^^^
	            for (Entity e: map[x][y].getEntities()) {
	            	ImageView entityImage = new ImageView(setImage(e));
	            	entityImage.setFitWidth(tileSize);
	            	entityImage.setFitHeight(tileSize);
	            	gridPane.add(entityImage, x, y);
	            }
            }
        }
        // ==============================
        Scene scene = new Scene(gridPane);
        scene.setFill(Color.BLACK);
        return scene;
	}
	
	private Image createImage(Color color) {
		WritableImage image = new WritableImage(1, 1);
        image.getPixelWriter().setColor(0, 0, color);
        return image ;
	}
	
	private Image setImage(Entity e) {
		Image image = new Image("application/Sprites/cobble_blood1.png");
		if (e instanceof Player) {
			return new Image("application/Sprites/human_m.png");
		} else if (e instanceof Bomb) {
			return new Image("application/Sprites/bomb_unlit.png");
		} else if (e instanceof Boulder) { 
			return new Image("application/Sprites/dngn_altar_xom2.png");
		} else if (e instanceof InvincibilityPotion) {
			return new Image("application/Sprites/emerald.png");
		} else if (e instanceof HoverPotion) {
			return new Image("application/Sprites/brilliant_blue.png");
		} else if (e instanceof Key) {
			return new Image("application/Sprites/staff02.png");
		} else if (e instanceof Treasure) { // add treasure, win if all collected
			return new Image("application/Sprites/gold_pile.png");
		} else if (e instanceof Arrow) {
			return new Image("application/Sprites/longbow.png");
		} else if (e instanceof Sword) {
			return new Image("application/Sprites/orcish_great_sword.png");
		} else if (e instanceof Enemy) {	// lose if you walk into enemy
			if (e instanceof Hunter) {
				return new Image("application/Sprites/orc_warlord.png");
			} else if (e instanceof Strategist) {
				return new Image("application/Sprites/deep_elf_conjurer.png");
			} else if (e instanceof Hound) {
				return new Image("application/Sprites/hound.png");
			} else if (e instanceof Coward) {
				return new Image("application/Sprites/human_slave.png");
			}
		} else if (e instanceof Pit) {	// lose if you walk into pit
			return new Image("application/Sprites/grate.png");
		} else if (e instanceof Exit) {	// win on exit
			return new Image("application/Sprites/dngn_exit_abyss.png");
		} else if (e instanceof Door) { // condition when player walks into door
			Door door = (Door)e;
			if (door.getStatus() == false) { // closed
				
			} else {
				
			}
			return new Image("application/Sprites/dngn_stone_arch.png");
		} else if (e instanceof Wall) {
			return new Image("application/Sprites/brick_brown-vines3.png");
		}
		return image;
	}
	
	private Map generateMap() {
		Map gameMap = new Map(10);
		Tile t = gameMap.getTile(4, 4);
		t.addEntity(new Player(gameMap.genID()));
		
		t = gameMap.getTile(1, 1);
		t.addEntity(new Hound(gameMap.genID()));
		
		t = gameMap.getTile(1, 2);
		t.addEntity(new Wall(gameMap.genID()));
		
		t = gameMap.getTile(1, 3);
		t.addEntity(new Sword(gameMap.genID()));
		
		t = gameMap.getTile(1, 4);
		t.addEntity(new Exit(gameMap.genID()));
		
		t = gameMap.getTile(1, 5);
		t.addEntity(new Treasure(gameMap.genID()));
		
		t = gameMap.getTile(1, 6);
		t.addEntity(new InvincibilityPotion(gameMap.genID()));
		
		t = gameMap.getTile(1, 7);
		t.addEntity(new Hunter(gameMap.genID()));
		
		t = gameMap.getTile(1, 8);
		t.addEntity(new Strategist(gameMap.genID()));
		
		t = gameMap.getTile(2, 1);
		t.addEntity(new HoverPotion(gameMap.genID()));
		
		t = gameMap.getTile(2, 2);
		t.addEntity(new Coward(gameMap.genID()));
		
		return gameMap;
	}
	
	private void goHome() throws Exception {
		new MenuScene(s).display();
	}
 }