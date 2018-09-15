package ass2;

import java.util.ArrayList;

public class MainTest {

	
	public static void main(String[] args) {

		/*MainTest main = new MainTest();
		Map gameMap = new Map();
		PlayerControls pc = new PlayerControls();
		Player p = new Player();
		while(true) {
			pc.getAction();
		}
		//ArrayList<Integer> arrayList = new ArrayList<>();
		*/
		
		
		DesignEngine designEngine = new DesignEngine();
		ArrayList<Integer> arrayList = new ArrayList<>();
		Map testMap = new Map(20);
		Hunter hunter2 = new Hunter(testMap.genID());
		Tile tile3 = testMap.getTile(4, 5);
		tile3.addEntity(hunter2);
		Wall wall = new Wall(testMap.genID());
		Tile tile = testMap.getTile(5, 5);
		tile.addEntity(wall);
		Bomb bomb = new Bomb(testMap,testMap.genID());
		tile.addEntity(bomb);

		Player player2 = new Player(testMap.genID());
		for(Entity e: tile.getEntities()){
			if(e instanceof Bomb){
				if(player2.putInventory(e)){
					System.out.println();
				}
			}
		}

		Tile tile4 = testMap.getTile(9, 5);
		tile4.addEntity(player2);
		hunter2.getAction(testMap);
		System.out.println(testMap.getEntityLocation(hunter2.getId()).getX());
		assert(testMap.getEntityLocation(hunter2.getId()).getX() == 5);
		assert(testMap.getEntityLocation(hunter2.getId()).getY() == 5);
/*
		designEngine.save(map, "testmap");
		String fileName = "testmap.txt";
		Map loadedMap = new Map();
		loadedMap = designEngine.load(fileName);
		loadedMap.printMap();
		map.printMap();
*/
	}
}
