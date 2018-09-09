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
}
