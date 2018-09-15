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

		Map map = new Map();
		Hunter hunter = null;
		//idk wtf this shit is
		for(Entity e: map.getEntityLocation(55).getEntities()){
			if(e.getId() == 55){
				hunter = (Hunter)e;
			}
		}
/*
		designEngine.save(map, "testmap");
		String fileName = "testmap.txt";
		Map loadedMap = new Map();
		loadedMap = designEngine.load(fileName);
		loadedMap.printMap();
		map.printMap();
*/
		hunter.getAction(map);
		System.out.println("hello");
	}
}
