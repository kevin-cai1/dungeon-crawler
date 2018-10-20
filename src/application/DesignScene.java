package application;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Optional;

import javax.xml.transform.Source;

import org.junit.experimental.theories.Theories;

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
import javafx.scene.control.Alert.AlertType;
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
	private Scene scene;
	private int tileSize;
	private ListView<String> listView;
	private GridPane gameGrid;
	private DesignEngine designEngine;
	private final ObjectProperty<ListCell<String>> dragSource = new SimpleObjectProperty<>();
	private int colIndex = -1;
	private int rowIndex = -1;
	private Observer mapObserver;
	private WinObserver winObserver;
	private static final DataFormat entityFormat = new DataFormat("ass2.Entity.java");
	private String imgPath = "application/Sprites/";
	public DesignScene(Stage s, DesignEngine designEngine) {
		this.s = s;
		this.title = "Design";
		this.fxmlLoader = new FXMLLoader(getClass().getResource("Design.fxml"));
		this.designEngine = designEngine;
		this.tileSize = 85 - 3 * designEngine.getMap().getArrayLength();
		this.gameGrid = new GridPane();
		gameGrid.setStyle("-fx-grid-lines-visible: true");
		mapObserver = new MapObserver(designEngine.getMap());
		winObserver = new GameWinObserver(designEngine.getMap());
	}
	/**
	 * drag handles the drag and drop in the scene
	 */
	private void initEventHandlers() {
		scene.addEventFilter(KeyEvent.KEY_PRESSED,Event -> {
			System.out.println(Event.getCode());
			// TODO Auto-generated method stub
			if(Event.getCode() == KeyCode.ESCAPE) {
				try {
					MapSave mapSave = new MapSave();
					mapSave.save("temp_design",designEngine.getMap());
					DesignPauseScene pauseScene = new DesignPauseScene(s);
					pauseScene.display();
				}
				catch(Exception e) {
					e.printStackTrace();
				}
			}
		});
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
				Entity entity = (Entity) dragboard.getContent(entityFormat);
				if(designEngine.placeEntity(entity,col,row)){
					
				}
				else{
					colIndex=-1;
					rowIndex=-1;
				}
				mapObserver.update(gameGrid, tileSize);
				event.consume();
			}
		});
	}
	public void display() {
		s.setTitle(title);
		this.scene = new Scene(generateSpace());
		s.setScene(scene);
		s.setResizable(false);
		s.sizeToScene();
		Rectangle2D bounds = Screen.getPrimary().getVisualBounds();

		s.setX((bounds.getWidth() - s.getWidth()) / 2);
		s.setY((bounds.getHeight() - s.getHeight()) / 2);
		s.show();
		initEventHandlers();
	}
	public BorderPane generateSpace() {
		BorderPane gameDisplay = new BorderPane();
		mapObserver.update(gameGrid, tileSize);
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
				winObserver.update(WinCondition.Exit);
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
				winObserver.update(WinCondition.Boulder);
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
				winObserver.update(WinCondition.Enemy);
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
				winObserver.update(WinCondition.Treasure);
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
		} else if (name.equals("Door1")) {
			entity = new Door(KeyEnum.MEDIUM, map.genID());
		} else if(name.equals("Door2")) {
			entity = new Door(KeyEnum.LARGE, map.genID());
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
		} else if (name.equals("Key1")) {
			entity = new Key(KeyEnum.MEDIUM, map.genID());
		} else if (name.equals("Key2")) {
			entity = new Key(KeyEnum.LARGE, map.genID());
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
		ImageView save = new ImageView(new Image(imgPath + "save_button.png"));
		save.setOnMouseEntered(Event -> {
			save.setImage(new Image(imgPath + "save_selected.png"));
		});
		save.setOnMouseExited(Event -> {
			save.setImage(new Image(imgPath + "save_button.png"));
		});
		ImageView clear = new ImageView(new Image(imgPath + "clear_button.png"));
		clear.setOnMouseEntered(Event -> {
			clear.setImage(new Image(imgPath + "clear_selected.png"));
		});
		clear.setOnMouseExited(Event -> {
			clear.setImage(new Image(imgPath + "clear_button.png"));
		});
		Label heading = new Label("Entities to choose from");
		heading.setFont(new Font("Impact", 40));
		heading.setTextFill(Color.GREY);
		listView = new ListView<String>();
		ObservableList<String> items = FXCollections.observableArrayList(
				"Arrow", "Bomb", "Boulder", "Coward", "Door","Door1","Door2", "Exit", "Floor Switch", "Hound", "Hover Potion", "Hunter", "Invincibility Potion", "Key","Key1","Key2", "Pit", "Player", "Strategist", "Sword", "Treasure", "Wall");
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
						//changes name to lowercase, replaces spaces with underscore to match filename
						String imgName = name.replaceAll(" ", "_").toLowerCase();
						imageView.setImage(new Image(imgPath + imgName + ".png"));
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
					boolean valid = true;
					if(entity instanceof Door) {
						Door door = (Door)entity;
						Map map = designEngine.getMap();
						for(int i = 0; i < map.getArrayLength(); i++) {
							for(int j = 0; j < map.getArrayLength(); j++) {
								for(Entity entity1: map.getTile(i, j).getEntities()) {
									if(entity1 instanceof Door) {
										Door door1 = (Door)entity1;
										if(door1.getUnique() == door.getUnique()) {
											Alert alert = new Alert(AlertType.WARNING);
											alert.setTitle("Warning");
											alert.setHeaderText("Door is invalid");
											alert.setContentText("You have already placed a door of this type on the map.");
											alert.showAndWait();
											valid = false;
										}
									}
								}
							}
						}
					}
					if(entity instanceof Key) {
						Key key = (Key)entity;
						Map map = designEngine.getMap();
						for(int i = 0; i < map.getArrayLength(); i++) {
							for(int j = 0; j < map.getArrayLength(); j++) {
								for(Entity entity1: map.getTile(i, j).getEntities()) {
									if(entity1 instanceof Key) {
										Key key1 = (Key)entity1;
										if(key1.getUnique() == key.getUnique()) {
											Alert alert = new Alert(AlertType.WARNING);
											alert.setTitle("Warning");
											alert.setHeaderText("Key is invalid");
											alert.setContentText("You have already placed a key of this type on the map.");
											alert.showAndWait();
											valid = false;
										}
									}
								}
							}
						}
					}
					if(valid) {
						clipboardContent.put(entityFormat,entity);
						dragboard.setContent(clipboardContent);
						dragSource.set(cell);
					}
				}
			});
			return cell;
		});
		/**
		 * temporary saving
		 */
		save.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if(!designEngine.validateMap(designEngine.getMap())) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Warning");
					alert.setHeaderText("Your map isn't valid. This could be because that you haven't added in a player or that you haven't added in your win conditions yet or maybe your doors don't have keys");
					alert.setContentText("Please fix this before trying to save again.");
					alert.showAndWait();
				}
				else {
					TextInputDialog dialog = new TextInputDialog("Map Name");
					dialog.setTitle("Map Save");
					dialog.setHeaderText("Save Your Map");
					dialog.setContentText("Please enter the map name:");
					Optional<String> result = dialog.showAndWait();
					if(result.isPresent()) {
						MapSave mapSave = new MapSave();
						// TODO Auto-generated method stub
						mapSave.save(result.get(),designEngine.getMap());
					}
				}
			}
		});
		clear.setOnMouseClicked(Event -> {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirm clear");
			alert.setHeaderText("Clear the map");
			alert.setContentText("Are you sure you want to continue?");
			Optional<ButtonType> result = alert.showAndWait();
			if(result.get() == ButtonType.OK) {
				designEngine.getMap().setMapTiles(); //clears the map?
				mapObserver.update(gameGrid, tileSize);
			}
		});
		vBox.getChildren().addAll(heading, listView, save, clear);
		
		return vBox;
	}

	public int getTileSize() {
		return tileSize;
	}
}