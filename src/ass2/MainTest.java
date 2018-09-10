package ass2;

import java.util.ArrayList;

public class MainTest {
	public static void main(String[] args) {
		ArrayList<Integer> arrayList = new ArrayList<>();
		Hunter hunter = new Hunter();
		Map map = new Map();
		System.out.println(hunter.move(map));
		System.out.println("hello");
	}

}
