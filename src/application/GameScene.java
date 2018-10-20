package application;


import java.util.ArrayList;

import ass2.*;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class GameScene {
	private Stage s;
	private String title;
	private Scene scene;
	private final int tileSize;		// **SCALE TILESIZE DEPENDING ON MAP SIZE
	private int mapSize = 10;
	private GameEngine game;
	private boolean playerMoved = false;
	private String imgPath = "application/Sprites/";
	private ArrayList<KeyCode> prevKeyPress; //stores all the previously pressed keys which have not been unpressed

	
	public GameScene(Stage s, GameEngine game) {
		this.s = s;
		this.title = "Game";
		this.game = game;
		this.tileSize = 85 - 3*game.getGameMap().getArrayLength();	// variable tileSize
		this.scene = new Scene(generateGrid());
		this.prevKeyPress = new ArrayList<>();
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
		
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				playerMoved = false;
				if(!(prevKeyPress.contains(event.getCode()))) {
					switch (event.getCode()) {
						case W:
							if(prevKeyPress.contains(KeyCode.L)) {
								playerMoved = game.swing(Direction.NORTH);
							} else if (prevKeyPress.contains(KeyCode.K)) {
								playerMoved = game.shootBow(Direction.NORTH);
							} else {
								playerMoved = game.movePlayerNorth();

							}
							break;
						case S:		
							if(prevKeyPress.contains(KeyCode.L)) {
								playerMoved = game.swing(Direction.SOUTH);
							} else if (prevKeyPress.contains(KeyCode.K)) {
								playerMoved = game.shootBow(Direction.SOUTH);
							} else {
								playerMoved = game.movePlayerSouth();
							}
							break;
						case D:		
							if(prevKeyPress.contains(KeyCode.L)) {
								playerMoved = game.swing(Direction.EAST);
							} else if (prevKeyPress.contains(KeyCode.K)) {
								playerMoved = game.shootBow(Direction.EAST);
							} else {
								playerMoved = game.movePlayerEast();
							}							
							break;
						case A:		
							if(prevKeyPress.contains(KeyCode.L)) {
								playerMoved = game.swing(Direction.WEST);
							} else if (prevKeyPress.contains(KeyCode.K)) {
								playerMoved = game.shootBow(Direction.WEST);
							} else {
								playerMoved = game.movePlayerWest();
							}						
							break;
						case B:		
							playerMoved = game.placeBomb();
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
							playerMoved = false;
							break;
						case ESCAPE:	
							/*if (game.getGameState() == GameState.Play) {	// player is pausing menu
								game.setGameState(GameState.Paused);
							} else {	// player is resuming
								game.setGameState(GameState.Play);
							}*/
							pauseGame();
							return;
						default:
							break;
					}
					prevKeyPress.add(event.getCode());
				}

				if (game.getGameStateInterface() instanceof Win) {
					displayWinScreen();
					return;
				} else if (game.getGameStateInterface() instanceof Lose) {
					
				}
				
				if (playerMoved) {
					// move enemies (run or move depending on invincibility)
					game.moveEnemies();

				}
				
				if (game.checkPlayerStatus() == false) {
					displayLoseScreen();
					return;
				}
				if (playerMoved) {
					if (game.tickEffects() instanceof Lose) {
						displayLoseScreen();
						return;
					}
				}
				
				if (game.checkGameState() == true) {
					System.out.println("checked win");
					displayWinScreen();
					return;
				}
				
				scene.setRoot(generateGrid());
				display();
			}
			
		});
		
		scene.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				prevKeyPress.remove(event.getCode());
			}
			
		});
	}
	
	public BorderPane generateGrid() {        
        Tile[][] gameMap = game.getGameMap().getMap();
        BorderPane gameDisplay = new BorderPane();
        GridPane gameGrid = new GridPane();
        // for visualizing the different squares:
        //gridPane.setHgap(2);
        //gridPane.setVgap(2);
        //gridPane.setStyle("-fx-background-color: grey;");
        
        // loop that fills in the image
        for (int y = 0 ; y < gameMap.length ; y++) {
            for (int x = 0 ; x < gameMap[y].length ; x++) {
                //ImageView imageView = new ImageView(grid[y][x]);
            	// add a floor to every single tile ^^
            	ImageView floorImage = new ImageView(new Image(imgPath + "floor.png"));
                floorImage.setFitWidth(tileSize);
                floorImage.setFitHeight(tileSize);
                gameGrid.add(floorImage, x, y);
                // ^^^
	            for (Entity e: gameMap[x][y].getEntities()) {
	            	ImageView entityImage = new ImageView(setImage(e));
	            	entityImage.setFitWidth(tileSize);
	            	entityImage.setFitHeight(tileSize);
	            	gameGrid.add(entityImage, x, y);
	            }
            }
        }
        // ==============================
        gameDisplay.setCenter(gameGrid);
      
        gameDisplay.setRight(setSidebar());
        return gameDisplay;
	}
	
	private FlowPane setSidebar() {
		Player player = game.getGameMap().getPlayer();
		Font textFont = Font.font("Goudy Old Style", FontWeight.BOLD ,34);
		Font headingFont = Font.font("Goudy Old Style", FontWeight.BOLD ,44);
		FlowPane sidebar = new FlowPane();
		sidebar.setPrefWrapLength(180);
		sidebar.setStyle("-fx-background-color: #272727;");
		
		Label InventoryLabel = new Label("Inventory");
		InventoryLabel.setFont(headingFont);
		InventoryLabel.setTextFill(Color.GREY);
		
		HBox Sword = new HBox();
		Sword.setStyle("-fx-background-color: #272727;");
		Sword.setSpacing(10);
		ImageView sword = new ImageView(new Image(imgPath + "sword.png"));
		sword.setFitHeight(40);
		sword.setFitWidth(40);
		Label swordCount = new Label();
		swordCount.setText(Integer.toString(player.getSwordUses()) + " uses");
		swordCount.setFont(textFont);
		swordCount.setTextFill(Color.GREY);
		Sword.getChildren().addAll(sword, swordCount);
		
		HBox Bomb = new HBox();
		Bomb.setStyle("-fx-background-color: #272727;");
		Bomb.setSpacing(10);
		ImageView bomb = new ImageView(new Image(imgPath + "bomb.png"));
		bomb.setFitHeight(40);
		bomb.setFitWidth(40);
		Label bombCount = new Label();
		bombCount.setText(Integer.toString(player.getBombsLeft()) + " left");
		bombCount.setFont(textFont);
		bombCount.setTextFill(Color.GREY);
		Bomb.getChildren().addAll(bomb, bombCount);
		
		HBox Arrow = new HBox();
		Arrow.setStyle("-fx-background-color: #272727;");
		Arrow.setSpacing(10);
		ImageView arrow = new ImageView(new Image(imgPath + "arrow.png"));
		arrow.setFitHeight(40);
		arrow.setFitWidth(40);
		Label arrowCount = new Label();
		arrowCount.setText(Integer.toString(player.getArrowsLeft()) + " left");
		arrowCount.setFont(textFont);
		arrowCount.setTextFill(Color.GREY);
		Arrow.getChildren().addAll(arrow, arrowCount);
		
		HBox Treasure = new HBox();
		Treasure.setStyle("-fx-background-color: #272727;");
		Treasure.setSpacing(10);
		ImageView treasure = new ImageView(new Image(imgPath + "treasure.png"));
		treasure.setFitHeight(40);
		treasure.setFitWidth(40);
		Label treasureCount = new Label();
		treasureCount.setText(Integer.toString(player.getTreasure()) + " obtained");
		treasureCount.setFont(textFont);
		treasureCount.setTextFill(Color.GREY);
		Treasure.getChildren().addAll(treasure, treasureCount);
		
		HBox Keys = new HBox();
		Keys.setStyle("-fx-background-color: #272727;");
		Keys.setSpacing(10);
		ImageView key1;
		if (player.hasKey(KeyEnum.SMALL) == true) {
			key1 = new ImageView(new Image(imgPath + "key.png"));
		} else {
			key1 = new ImageView(new Image(imgPath + "blank_key.png"));
		}
		key1.setFitHeight(40);
		key1.setFitWidth(40);
		
		ImageView key2;
		if (player.hasKey(KeyEnum.MEDIUM) == true) {
			key2 = new ImageView(new Image(imgPath + "key1.png"));
		} else {
			key2 = new ImageView(new Image(imgPath + "blank_key.png"));
		}
		key2.setFitHeight(40);
		key2.setFitWidth(40);
		
		ImageView key3;
		if (player.hasKey(KeyEnum.LARGE) == true) {
			key3 = new ImageView(new Image(imgPath + "key2.png"));
		} else {
			key3 = new ImageView(new Image(imgPath + "blank_key.png"));
		}
		key3.setFitHeight(40);
		key3.setFitWidth(40);
		Keys.setAlignment(Pos.CENTER);
		Keys.getChildren().addAll(key1, key2, key3);
		
		HBox Invincibility = new HBox();
		Invincibility.setStyle("-fx-background-color: #272727;");
		Invincibility.setSpacing(10);
		ImageView invincibility = new ImageView(new Image(imgPath + "invincibility_potion.png"));
		invincibility.setFitHeight(40);
		invincibility.setFitWidth(40);
		Label invincibilityCount = new Label();
		invincibilityCount.setText(Integer.toString(player.getInvincibilityTick()) + " turns");
		invincibilityCount.setFont(textFont);
		invincibilityCount.setTextFill(Color.GREY);
		Invincibility.getChildren().addAll(invincibility, invincibilityCount);
		
		HBox Hover = new HBox();
		Hover.setStyle("-fx-background-color: #272727;");
		Hover.setSpacing(10);
		ImageView hover = new ImageView(new Image(imgPath + "hover_potion.png"));
		hover.setFitHeight(40);
		hover.setFitWidth(40);
		Label hoverSet = new Label();
		if (player.getHover() == true) {
			hoverSet.setText("active");
		} else {
			hoverSet.setText("inactive");
		}
		hoverSet.setFont(textFont);
		hoverSet.setTextFill(Color.GREY);
		Hover.getChildren().addAll(hover, hoverSet);
		
		Label WinConditionLabel = new Label("Win Conditions");
		WinConditionLabel.setFont(textFont);
		WinConditionLabel.setTextFill(Color.GREY);
		
		sidebar.getChildren().addAll(InventoryLabel, Sword, Bomb, Arrow, Treasure, Keys, Invincibility, Hover, WinConditionLabel);

		
		if (game.getBoulderWinCondition()) {
			HBox boulderWin = new HBox();
			boulderWin.setStyle("-fx-background-color: #272727;");
			boulderWin.setSpacing(10);
			Label boulders = new Label();
			boulders.setText("Switches: " + game.switchesTriggered() + "/" + game.getNumSwitches());
			boulders.setFont(textFont);
			boulders.setTextFill(Color.GREY);
			boulderWin.getChildren().add(boulders);
			sidebar.getChildren().add(boulderWin);
		}
		
		if (game.getEnemyWinCondition()) {
			HBox enemyWin = new HBox();
			enemyWin.setStyle("-fx-background-color: #272727;");
			enemyWin.setSpacing(10);
			Label enemy = new Label();
			enemy.setText("Enemies: " + game.enemiesKilled() + "/" + game.getNumEnemies());
			enemy.setFont(textFont);
			enemy.setTextFill(Color.GREY);
			enemyWin.getChildren().add(enemy);
			sidebar.getChildren().add(enemyWin);
		}
		
		if (game.getTreasureWinCondition()) {
			HBox treasureWin = new HBox();
			treasureWin.setStyle("-fx-background-color: #272727;");
			treasureWin.setSpacing(10);
			Label treasures = new Label();
			treasures.setText("Treasures: " + player.getTreasure() + "/" + game.getNumberTreasures());
			treasures.setFont(textFont);
			treasures.setTextFill(Color.GREY);
			treasureWin.getChildren().add(treasures);
			sidebar.getChildren().add(treasureWin);
		}
		
		
		return sidebar;
	}
	
	private Image setImage(Entity e) {
		Image image = new Image(imgPath + "floor.png");
		
		if (e instanceof Key) {
			KeyEnum keyID = ((Key) e).getUnique();
			if (keyID == KeyEnum.SMALL) {
				return new Image(imgPath + "key.png");
			} else if (keyID == KeyEnum.MEDIUM) {
				return new Image(imgPath + "key1.png");
			} else if (keyID == KeyEnum.LARGE) {
				return new Image(imgPath + "key2.png");
			}
		} else if (e instanceof Door) { // condition when player walks into door
			Door door = (Door)e;
			if (door.getStatus() == false) { // closed
				KeyEnum doorID = ((Door) e).getUnique();
				if (doorID == KeyEnum.SMALL) {
					return new Image(imgPath + "door.png");
				} else if (doorID == KeyEnum.MEDIUM) {
					return new Image(imgPath + "door1.png");
				} else if (doorID == KeyEnum.LARGE) {
					return new Image(imgPath + "door2.png");
				}
				return new Image(imgPath + "door.png");
			} else {
				return new Image(imgPath + "open_door.png");
			}
		} else if (e instanceof Bomb) { 
			Bomb bomb = (Bomb)e;
			int bombStatus = bomb.getTimer();
			switch (bombStatus) {
			case 3:
				return new Image(imgPath + "bomb_lit_1.png");
			case 2:
				return new Image(imgPath + "bomb_lit_2.png");
			case 1:
				return new Image(imgPath + "bomb_lit_3.png");
			case 0:
				return new Image(imgPath + "bomb_lit_4.png");
			default:
				return new Image(imgPath + "bomb.png");
			}
		} else if (e instanceof Floor == false) {
			return new Image(imgPath + e.imgName() + ".png");
		}
		return image;
	}
	
	private void pauseGame() {
		try {
			MapSave mapSave = new MapSave();
			mapSave.save("temp",game.getGameMap());
			PauseScene pauseScene = new PauseScene(s);
			pauseScene.display();
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}
	
	private void displayWinScreen() {
		try {
			WinScene winScene = new WinScene(s);
			winScene.display();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void displayLoseScreen() {
		try {
			LoseScene loseScene = new LoseScene(s);
			loseScene.display();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
 }