package ass2;

import java.util.ArrayList;

public interface MapBuilder {
	Map build();
	MapBuilder addWinConditions(WinCondition winCondition);
	MapBuilder setArrayLength(int arrayLength);
	MapBuilder setMap(Tile[][] map);
	
}
