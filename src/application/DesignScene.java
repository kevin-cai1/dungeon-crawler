package application;

import java.awt.Event;
import java.awt.event.MouseAdapter;
import java.io.IOException;

import javax.xml.transform.Source;

import ass2.*;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class DesignScene {
	private Stage s;
	private String title;
	private FXMLLoader fxmlLoader;
	private int mapSize = 10;
	private int tileSize;
	private ListView<String> listView;
	private GridPane gameGrid;
	private DesignEngine designEngine;
	private final ObjectProperty<ListCell<String>> dragSource = new SimpleObjectProperty<>();
	public DesignScene(Stage s) {
		this.s = s;
		this.title = "Design";
		this.fxmlLoader = new FXMLLoader(getClass().getResource("Design.fxml"));
		this.designEngine = new DesignEngine(mapSize); //TODO TEMPORARY SET VALUE
		this.tileSize = 85 - 3 * designEngine.getMap().getArrayLength();
		this.gameGrid = new GridPane();
		gameGrid.setStyle("-fx-grid-lines-visible: true");
	}

	public void display() {
		s.setTitle(title);
		s.setScene(new Scene(generateGrid()));
		s.setResizable(false);
		s.sizeToScene();
		Rectangle2D bounds = Screen.getPrimary().getVisualBounds();

		s.setX((bounds.getWidth() - s.getWidth()) / 2);
		s.setY((bounds.getHeight() - s.getHeight()) / 2);
		s.show();
		/*listView.setOnDragDetected(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				System.out.println(listView.getSelectionModel().getSelectedItem());
				Dragboard dragboard = listView.startDragAndDrop(TransferMode.ANY);
				ClipboardContent content = new ClipboardContent();
				content.putString(listView.getAccessibleText());
				dragboard.setContent(content);
				event.consume();
			}
		});*/
		gameGrid.setOnDragDropped(new EventHandler<DragEvent>() {
			public void handle(DragEvent event) {
				System.out.println("hello");
				if(event.getGestureSource() != gameGrid && event.getDragboard().hasString()) {
					Node source = (Node)event.getSource();
					Integer colIndex = GridPane.getColumnIndex(source);
					Integer rowIndex = GridPane.getRowIndex(source);
					System.out.println("Dropped onto" + colIndex.intValue() + rowIndex.intValue());
				}
			}
		});
		
	}

	public BorderPane generateGrid() {
		Tile[][] gameMap = designEngine.getMap().getMap();
		BorderPane gameDisplay = new BorderPane();
		// for visualizing the different squares:
		//gridPane.setHgap(2);
		//gridPane.setVgap(2);
		//gridPane.setStyle("-fx-background-color: grey;");

		// loop that fills in the image
		for (int y = 0; y < gameMap.length; y++) {
			for (int x = 0; x < gameMap[y].length; x++) {
				//ImageView imageView = new ImageView(grid[y][x]);
				// add a floor to every single tile ^^
				ImageView floorImage = new ImageView(new Image("application/Sprites/floor.png"));
				floorImage.setFitWidth(tileSize);
				floorImage.setFitHeight(tileSize);
				gameGrid.add(floorImage, x, y);
				// ^^^
				for (Entity e : gameMap[x][y].getEntities()) {
					ImageView entityImage = new ImageView(setImage(e));
					entityImage.setFitWidth(tileSize);
					entityImage.setFitHeight(tileSize);
					gameGrid.add(entityImage, x, y);
				}
			}
		}
		// ==============================
		gameDisplay.setCenter(gameGrid);
		gameDisplay.setRight(setDesignBar());
		gameDisplay.setBottom(setWinConditions());
		return gameDisplay;
	}

	private FlowPane setWinConditions() {
		FlowPane toolBar = new FlowPane();
		toolBar.setStyle("-fx-background-color: #1A1A1A");
		Label heading = new Label("Win Conditions");
		heading.setFont(new Font("Impact", 40));
		heading.setTextFill(Color.GREY);
		toolBar.getChildren().add(heading);
		CheckBox checkBox1 = new CheckBox("Boulder");
		checkBox1.setTextFill(Color.GREY);
		CheckBox checkBox2 = new CheckBox("Enemy");
		checkBox2.setTextFill(Color.GREY);
		CheckBox checkBox3 = new CheckBox("Treasure");
		checkBox3.setTextFill(Color.GREY);
		toolBar.getChildren().addAll(checkBox1, checkBox2, checkBox3);
		return toolBar;
	}

	private VBox setDesignBar() {
		VBox vBox = new VBox();
		Label heading = new Label("Entities to choose from");
		heading.setFont(new Font("Impact", 40));
		heading.setTextFill(Color.GREY);
		listView = new ListView<String>();
		ObservableList<String> items = FXCollections.observableArrayList(
				"Arrow", "Bomb", "Boulder", "Coward", "Door", "Exit", "Floor Switch", "Hound", "Hover Potion", "Hunter", "Invincibility Potion", "Key", "Pit", "Player", "Strategist", "Sword", "Treasure", "Wall");
		listView.setItems(items);
		listView.setCellFactory(param -> {
			ListCell<String> cell = new ListCell<String>() {
				private ImageView imageView = new ImageView();
	
				@Override
				public void updateItem(String name, boolean empty) {
					super.updateItem(name, empty);
					if (empty) {
						setText(null);
						setGraphic(null);
					} else {
						if (name.equals("Arrow")) {
							imageView.setImage(new Image("application/Sprites/arrow.png"));
						} else if (name.equals("Bomb")) {
							imageView.setImage(new Image("application/Sprites/bomb_unlit.png"));
						} else if (name.equals("Boulder")) {
							imageView.setImage(new Image("application/Sprites/boulder.png"));
						} else if (name.equals("Coward")) {
							imageView.setImage(new Image("application/Sprites/coward.png"));
						} else if (name.equals("Door")) {
							imageView.setImage(new Image("application/Sprites/closed_door.png"));
						} else if (name.equals("Exit")) {
							imageView.setImage(new Image("application/Sprites/dngn_exit_abyss.png"));
						} else if (name.equals("Floor Switch")) {
							imageView.setImage(new Image("application/Sprites/pressure_plate.png"));
						} else if (name.equals("Hound")) {
							imageView.setImage(new Image("application/Sprites/hound.png"));
						} else if (name.equals("Hover Potion")) {
							imageView.setImage(new Image("application/Sprites/hover_potion.png"));
						} else if (name.equals("Hunter")) {
							imageView.setImage(new Image("application/Sprites/hunter.png"));
						} else if (name.equals("Invincibility Potion")) {
							imageView.setImage(new Image("application/Sprites/invincibility_potion.png"));
						} else if (name.equals("Key")) {
							imageView.setImage(new Image("application/Sprites/key.png"));
						} else if (name.equals("Pit")) {
							imageView.setImage(new Image("application/Sprites/shaft.png"));
						} else if (name.equals("Player")) {
							imageView.setImage(new Image("application/Sprites/player.png"));
						} else if (name.equals("Strategist")) {
							imageView.setImage(new Image("application/Sprites/strategist.png"));
						} else if (name.equals("Sword")) {
							imageView.setImage(new Image("application/Sprites/sword.png"));
						} else if (name.equals("Treasure")) {
							imageView.setImage(new Image("application/Sprites/gold_pile.png"));
						} else if (name.equals("Wall")) {
							imageView.setImage(new Image("application/Sprites/wall.png"));
						}
	
						setText(name);
						setGraphic(imageView);
					}
				}
			};
			cell.setOnDragDetected(Event -> {
				if(!cell.isEmpty()) {
					Dragboard dragboard = cell.startDragAndDrop(TransferMode.ANY);
					ClipboardContent clipboardContent = new ClipboardContent();
					clipboardContent.putString(cell.getItem());
					dragboard.setContent(clipboardContent);
					dragSource.set(cell);
					System.out.println(cell.getItem());
				}
			});
			return cell;
		});
		
		vBox.getChildren().addAll(heading, listView);
		return vBox;
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
			return new Image("application/Sprites/arrow.png");
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
}