package application;

import ass2.Arrow;
import ass2.Bomb;
import ass2.Boulder;
import ass2.Coward;
import ass2.Door;
import ass2.Enemy;
import ass2.Entity;
import ass2.Exit;
import ass2.FloorSwitch;
import ass2.Hound;
import ass2.HoverPotion;
import ass2.Hunter;
import ass2.InvincibilityPotion;
import ass2.Key;
import ass2.Map;
import ass2.Observer;
import ass2.Pit;
import ass2.Player;
import ass2.Strategist;
import ass2.Sword;
import ass2.Tile;
import ass2.Treasure;
import ass2.Wall;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class MapObserver implements Observer {
	private Map map;
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
		// TODO Auto-generated method stub

	}
	private Image setImage(Entity e) {
		Image image = new Image("application/Sprites/floor.png");
		if (e instanceof Player) {
			return new Image("application/Sprites/player.png");
		} else if (e instanceof Bomb) {
			return new Image("application/Sprites/bomb_unlit.png");
		} else if (e instanceof Boulder) {
			return new Image("application/Sprites/boulder.png");
		} else if (e instanceof FloorSwitch) {
			return new Image("application/Sprites/pressure_plate.png");
		} else if (e instanceof InvincibilityPotion) {
			return new Image("application/Sprites/invincibility_potion.png");
		} else if (e instanceof HoverPotion) {
			return new Image("application/Sprites/hover_potion.png");
		} else if (e instanceof Key) {
			return new Image("application/Sprites/key.png");
		} else if (e instanceof Treasure) { // add treasure, win if all collected
			return new Image("application/Sprites/gold_pile.png");
		} else if (e instanceof Arrow) {
			return new Image("application/Sprites/arrow.png");
		} else if (e instanceof Sword) {
			return new Image("application/Sprites/sword.png");
		} else if (e instanceof Enemy) {	// lose if you walk into enemy
			if (e instanceof Hunter) {
				return new Image("application/Sprites/hunter.png");
			} else if (e instanceof Strategist) {
				return new Image("application/Sprites/strategist.png");
			} else if (e instanceof Hound) {
				return new Image("application/Sprites/hound.png");
			} else if (e instanceof Coward) {
				return new Image("application/Sprites/coward.png");
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
			return new Image("application/Sprites/wall.png");
		}
		return image;
	}
}
