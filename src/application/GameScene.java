package application;

import ass2.*;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class GameScene {
	private Stage s;
	private String title;
	private Scene scene;
	private FXMLLoader fxmlLoader;
	private final int tileSize = 60;	// **SCALE TILESIZE DEPENDING ON MAP SIZE
	private int mapSize = 10;
	private GameEngine game;
	private boolean playerMoved = false;
	
	final BooleanProperty LPressed = new SimpleBooleanProperty(false);
	final BooleanProperty DPressed = new SimpleBooleanProperty(false);

	final BooleanBinding DAndLPressed = LPressed.and(DPressed);

	
	public GameScene(Stage s, GameEngine game) {
		this.s = s;
		this.title = "Game";
		this.game = game;
		this.fxmlLoader = new FXMLLoader(getClass().getResource("Game.fxml"));
		this.scene = new Scene(generateGrid());
	}
		
	public void display() {
		try {
			handleMove();
		} catch (Exception e) {
			e.printStackTrace();
		}
		s.setTitle(title);	
		s.setScene(scene);
		s.setResizable(false);
		s.sizeToScene();
		Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
		
		s.setX((bounds.getWidth() - s.getWidth())/2);
		s.setY((bounds.getHeight() - s.getHeight())/2);
		s.show();
		
	}
	
	
	public void handleMove() throws Exception {
		DAndLPressed.addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
				// TODO Auto-generated method stub
				game.swing(Direction.EAST);
				
			}
		});
		
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				switch (event.getCode()) {
					case W:
						playerMoved = game.movePlayerNorth();
						
						break;
					case S:		
						playerMoved = game.movePlayerSouth();
						
						break;
					case D:		
						if (LPressed.getValue() == false) {
							playerMoved = game.movePlayerEast();
						}
						DPressed.set(true);
						break;
					case A:		
						playerMoved = game.movePlayerWest();
						
						break;
					case B:		
						//playerMoved = game.placeBomb();
						System.out.println("DROP BOMB");
						break;
					case UP:		
						playerMoved = game.swing(Direction.NORTH);
						
						break;
					case DOWN:		
						playerMoved = game.swing(Direction.SOUTH);
						
						break;
					case LEFT:		
						playerMoved = game.swing(Direction.WEST);
						
						break;
					case RIGHT:		
						playerMoved = game.swing(Direction.EAST);
						
						break;
					case L:		
						LPressed.set(true);
						break;
					case ESCAPE:	
						/*if (game.getGameState() == GameState.Play) {	// player is pausing menu
							game.setGameState(GameState.Paused);
						} else {	// player is resuming
							game.setGameState(GameState.Play);
						}
						pauseGame();*/
						goHome();
						break;
					default:
						break;
				}
				if (game.getGameState().equals(GameState.Win)) {
					winMessage();
				} else if (game.getGameState().equals(GameState.Lose)) {
					
				}
				
				/*if (playerMoved) {
					// move enemies (run or move depending on invincibility)
					if(game.getGameMap().getPlayer().getInvincibility()) {
						game.runEnemies();
					}
					else {
						game.moveEnemies();
					}
				}*/
				
				if (game.checkPlayerStatus() == false) {
					loseMessage();
				}
				
				if (game.tickEffects() == GameState.Lose) {
					loseMessage();
				}
				
				if (game.checkWin() == true) {
					System.out.println("checked win");
					winMessage();
				}
				
				scene.setRoot(generateGrid());
				display();
			}
			
		});
		
		scene.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				switch (event.getCode()) {
				case W:
					
					break;
				case S:		
					
					break;
				case D:		
					DPressed.set(false);
					break;
				case A:		
					
					break;
				case B:		
					
					break;
				case UP:		
					
					break;
				case DOWN:		
					
					break;
				case LEFT:		
					
					break;
				case RIGHT:		
					
					break;
				case L:		
					LPressed.set(false);
					break;
				case ESCAPE:	
					break;
				default:
					break;
			}
				
			}
			
		});
	}
	
	public GridPane generateGrid() {        
        Tile[][] gameMap = game.getGameMap().getMap();
        
        GridPane gridPane = new GridPane();
        // for visualizing the different squares:
        //gridPane.setHgap(2);
        //gridPane.setVgap(2);
        //gridPane.setStyle("-fx-background-color: grey;");
        
        // loop that fills in the image
        for (int y = 0 ; y < gameMap.length ; y++) {
            for (int x = 0 ; x < gameMap[y].length ; x++) {
                //ImageView imageView = new ImageView(grid[y][x]);
            	// add a floor to every single tile ^^
            	ImageView floorImage = new ImageView(new Image("application/Sprites/cobble_blood1.png"));
                floorImage.setFitWidth(tileSize);
                floorImage.setFitHeight(tileSize);
                gridPane.add(floorImage, x, y);
                // ^^^
	            for (Entity e: gameMap[x][y].getEntities()) {
	            	ImageView entityImage = new ImageView(setImage(e));
	            	entityImage.setFitWidth(tileSize);
	            	entityImage.setFitHeight(tileSize);
	            	gridPane.add(entityImage, x, y);
	            }
            }
        }
        // ==============================
        
        return gridPane;
	}
	
	private Image setImage(Entity e) {
		Image image = new Image("application/Sprites/cobble_blood1.png");
		if (e instanceof Player) {
			return new Image("application/Sprites/human_m.png");
		} else if (e instanceof Bomb) {
			/*switch (((Bomb) e).getTimer()) {
			case 4:		// unlit
				
				break;
			case 3:
				
				break;
			case 2:
				
				break;
			case 1:
				
				break;
			*/
			
			return new Image("application/Sprites/bomb_unlit.png");
		} else if (e instanceof Boulder) { 
			return new Image("application/Sprites/boulder.png");
		} else if (e instanceof FloorSwitch) {
			return new Image("application/Sprites/pressure_plate.png");		
		} else if (e instanceof InvincibilityPotion) {
			return new Image("application/Sprites/emerald.png");
		} else if (e instanceof HoverPotion) {
			return new Image("application/Sprites/brilliant_blue.png");
		} else if (e instanceof Key) {
			return new Image("application/Sprites/key.png");
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
			return new Image("application/Sprites/shaft.png");
		} else if (e instanceof Exit) {	// win on exit
			return new Image("application/Sprites/dngn_exit_abyss.png");
		} else if (e instanceof Door) { // condition when player walks into door
			Door door = (Door)e;
			if (door.getStatus() == false) { // closed
				return new Image("application/Sprites/closed_door.png");
			} else {
				return new Image("application/Sprites/open_door.png");
			}
		} else if (e instanceof Wall) {
			return new Image("application/Sprites/brick_brown-vines3.png");
		}
		return image;
	}
	
	
	
	private void pauseGame() {
		if (game.getGameState() == GameState.Paused) {
			// pause the game
		} else {
			// close the window
		}
	}
	
	private void goHome() {
		try {
			new MenuScene(s).display();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void winMessage() {
		System.out.println("YOU WIN");
		goHome();
	}
	
	private void loseMessage() {
		System.out.println("YOU LOSE");
	}
 }
