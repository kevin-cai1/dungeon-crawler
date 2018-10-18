package application;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.xml.transform.Source;

import ass2.*;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.input.*;
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
	private int colIndex = -1;
	private int rowIndex = -1;
	private Observer mapObserver;
	private static final DataFormat entityFormat = new DataFormat("ass2.Entity.java");
	public DesignScene(Stage s) {
		this.s = s;
		this.title = "Design";
		this.fxmlLoader = new FXMLLoader(getClass().getResource("Design.fxml"));
		this.designEngine = new DesignEngine(mapSize); //TODO TEMPORARY SET VALUE
		this.tileSize = 85 - 3 * designEngine.getMap().getArrayLength();
		this.gameGrid = new GridPane();
		gameGrid.setStyle("-fx-grid-lines-visible: true");
		mapObserver = new MapObserver(designEngine.getMap());
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
		gameGrid.setOnDragDetected(Event -> {
			Node node = Event.getPickResult().getIntersectedNode();
			colIndex = GridPane.getColumnIndex(node);
			rowIndex = GridPane.getRowIndex(node);
			ArrayList<Entity> entities = designEngine.getMap().getTile(colIndex, rowIndex).getEntities();
			if(!entities.isEmpty()) {
				Dragboard dragboard = gameGrid.startDragAndDrop(TransferMode.ANY);
				ClipboardContent clipboardContent = new ClipboardContent();
				clipboardContent.put(entityFormat,entities.get(entities.size()-1));
				dragboard.setContent(clipboardContent);
			}
			else {
				colIndex = -1;
				rowIndex = -1; //reset it back to not on the grid
			}
		});
		gameGrid.setOnDragDone(Event -> {
			if(colIndex != -1) {
				designEngine.removeTopEntity(colIndex, rowIndex);
				mapObserver.update(gameGrid, tileSize);
			}
		});
		gameGrid.setOnDragOver(event -> {
			Dragboard dragboard = event.getDragboard();
			if(dragboard.hasContent(entityFormat)) {
				event.acceptTransferModes(TransferMode.MOVE);
			}
			event.consume();
		});
		gameGrid.setOnDragDropped(new EventHandler<DragEvent>() {
			public void handle(DragEvent event) {
				Dragboard dragboard = event.getDragboard();
				Node node = event.getPickResult().getIntersectedNode();
				Map map = designEngine.getMap();
				int col = GridPane.getColumnIndex(node);
				int row = GridPane.getRowIndex(node);
				if(designEngine.placeEntity((Entity) dragboard.getContent(entityFormat),col,row)){
					mapObserver.update(gameGrid, tileSize);
				}
				else{
					System.out.println("THATS INVALID");
				}
				event.consume();
			}
		});
		
	}

	public BorderPane generateGrid() {
		BorderPane gameDisplay = new BorderPane();
		mapObserver.update(gameGrid, tileSize);
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
		CheckBox checkBox0 = new CheckBox("Exit");
		checkBox0.setTextFill(Color.GREY);
		CheckBox checkBox1 = new CheckBox("Boulder");
		checkBox1.setTextFill(Color.GREY);
		CheckBox checkBox2 = new CheckBox("Enemy");
		checkBox2.setTextFill(Color.GREY);
		CheckBox checkBox3 = new CheckBox("Treasure");
		checkBox3.setTextFill(Color.GREY);
		toolBar.getChildren().addAll(checkBox0,checkBox1, checkBox2, checkBox3);
		checkBox0.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(newValue) {
					checkBox1.setStyle("-fx-opacity: 0");
					checkBox2.setStyle("-fx-opacity: 0");
					checkBox3.setStyle("-fx-opacity: 0");
					checkBox1.setSelected(false);
					checkBox2.setSelected(false);
					checkBox3.setSelected(false);
				}
				else {
					checkBox1.setStyle("-fx-opacity: 1");
					checkBox2.setStyle("-fx-opacity: 1");
					checkBox3.setStyle("-fx-opacity: 1");
				}
			}
		});
		checkBox1.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(newValue || checkBox2.selectedProperty().get() == true || checkBox3.selectedProperty().get() == true) {
					checkBox0.setStyle("-fx-opacity: 0");
					checkBox0.setSelected(false);
				}
				else {
					checkBox0.setStyle("-fx-opacity: 1");
				}
			}
		});
		checkBox2.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(newValue || checkBox1.selectedProperty().get() == true || checkBox3.selectedProperty().get() == true) {
					checkBox0.setStyle("-fx-opacity: 0");
					checkBox0.setSelected(false);
				}
				else {
					checkBox0.setStyle("-fx-opacity: 1");
				}
			}
		});
		checkBox3.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(newValue || checkBox2.selectedProperty().get() == true || checkBox1.selectedProperty().get() == true) {
					checkBox0.setStyle("-fx-opacity: 0");
					checkBox0.setSelected(false);
				}
				else {
					checkBox0.setStyle("-fx-opacity: 1");
				}
			}
		});
		return toolBar;
	}
	private Entity entityTextToEntity(String name){
		Map map = designEngine.getMap();
		Entity entity = null;
		if (name.equals("Arrow")) {
			entity = new Arrow(map.genID(),map);
		} else if (name.equals("Bomb")) {
			entity = new Bomb(map,map.genID());
		} else if (name.equals("Boulder")) {
			entity = new Boulder(map.genID());
		} else if (name.equals("Coward")) {
			entity = new Coward(map.genID());
		} else if (name.equals("Door")) {
			entity = new Door(KeyEnum.SMALL,map.genID());
		} else if (name.equals("Exit")) {
			entity = new Exit(map.genID());
		} else if (name.equals("Floor Switch")) {
			entity = new FloorSwitch(map.genID());
		} else if (name.equals("Hound")) {
			entity = new Hound(map.genID());
		} else if (name.equals("Hover Potion")) {
			entity = new HoverPotion(map.genID());
		} else if (name.equals("Hunter")) {
			entity = new Hunter(map.genID());
		} else if (name.equals("Invincibility Potion")) {
			entity = new InvincibilityPotion(map.genID());
		} else if (name.equals("Key")) {
			entity = new Key(KeyEnum.SMALL,map.genID());
		} else if (name.equals("Pit")) {
			entity = new Pit(map.genID());
		} else if (name.equals("Player")) {
			entity = new Player(map.genID());
		} else if (name.equals("Strategist")) {
			entity = new Strategist(map.genID());
		} else if (name.equals("Sword")) {
			entity = new Sword(map.genID());
		} else if (name.equals("Treasure")) {
			entity = new Treasure(map.genID());
		} else if (name.equals("Wall")) {
			entity = new Wall(map.genID());
		}
		return entity;
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
					Entity entity = entityTextToEntity(cell.getItem());
					clipboardContent.put(entityFormat,entity);
					dragboard.setContent(clipboardContent);
					dragSource.set(cell);
				}
			});
			return cell;
		});
		
		vBox.getChildren().addAll(heading, listView);
		return vBox;
	}

	public int getTileSize() {
		return tileSize;
	}
}