package ass2;


public class Map {
	private Tile[][] map;
	public Map() {
		map = new Tile[50][50];
		// TODO Auto-generated constructor stub
	}
	public Tile getTile(int x, int y) {
		return map[x][y];
	}
	public Tile[][] getMap() {
		return map;
	}
	public Tile getPlayer() {
		return null;
		//TODO returns the player's location
	}
	public void printMap() {
		for (int i = 0; i < 50; i++) {
			for (int j = 0; j < 50; j++) {
				System.out.println(map[i][j].toString());
			}
		}
	}
}
