package ass2;

import java.util.ArrayList;

public interface MapBuilder {
	Map build();
	MapBuilder setWinConditions(WinCondition winCondition);
	MapBuilder setArrayLengthMap(int arrayLength);
	MapBuilder setMapEntities();
	MapBuilder setIdCounter();
	
}
