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
		
		
		
		ArrayList<Integer> arrayList = new ArrayList<>();
		Hunter hunter = new Hunter();
		Map map = new Map();
		System.out.println(hunter.move(map));
		System.out.println("hello");
	}
}
