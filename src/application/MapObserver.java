package application;

import ass2.Door;
import ass2.Entity;
import ass2.Floor;
import ass2.Key;
import ass2.Map;
import ass2.Observer;
import ass2.Tile;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class MapObserver implements Observer {
	private Map map;
	private String imgPath = "application/Sprites/";
	
	public  MapObserver(Map map) {
		this.map = map;
		// TODO Auto-generated constructor stub
	}
	@Override
	public void update(GridPane gameGrid, int tileSize) {

		Tile[][] gameMap = map.getMap();
		for (int y = 0; y < gameMap.length; y++) {
			for (int x = 0; x < gameMap[y].length; x++) {
				//ImageView imageView = new ImageView(grid[y][x]);
				// add a floor to every single tile ^^
				ImageView floorImage = new ImageView(new Image(imgPath + "floor.png"));
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
		// TODO Auto-generated method stub

	}
	private Image setImage(Entity e) {
		
		Image image = new Image(imgPath + "floor.png");
		if (e instanceof Door) { // condition when player walks into door
			Door door = (Door)e;
			if (door.getStatus() == false) { // closed
				switch (door.getUnique()) {
					case SMALL:
						return new Image(imgPath + "door.png");
					case MEDIUM:
						return new Image(imgPath + "door1.png");
					case LARGE:
						return new Image(imgPath + "door2.png");
				}
			} 
			else {
				return new Image(imgPath + "open_door.png");
			}
		} else if (e instanceof Key) {
			Key key = (Key)e;
			switch (key.getUnique()) {
				case SMALL:
					return new Image(imgPath + "key.png");
				case MEDIUM:
					return new Image(imgPath + "key1.png");
				case LARGE:
					return new Image(imgPath + "key2.png");
			}
		} else if (e instanceof Floor == false){
			return new Image(imgPath + e.imgName() + ".png");
		}
		return image;
	}
}
