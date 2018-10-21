package ass2;

import java.util.ArrayList;

public class TestMapBuilder {
	private MapBuilder mapBuilder;
	public TestMapBuilder (MapBuilder mapBuilder) {
		this.mapBuilder = mapBuilder;
	}
	public Map Construct() {
		ArrayList<WinCondition> arrayList = new ArrayList<>();
		return mapBuilder.setWinConditions(arrayList).setArrayLengthMap(20).setIdCounter().setMapEntities().build();
	}
	
    public static void main(final String[] arguments) {
    	
        
        DesignEngine designEngine = new DesignEngine(20);
        ArrayList<WinCondition> arrayList = new ArrayList<>();
        Map designMap = designEngine.getMap();
        MapBuilder builder = new MapBuilderImplem();
        designMap = builder.setArrayLengthMap(10).setMapEntities().setWinConditions(arrayList).setIdCounter().build();
        if(designEngine.validateMap(designMap)) System.out.println("success");
        else { System.out.println("u suck"); }
        
        
        /*TestMapBuilder mapBuildDirector = new TestMapBuilder(builder);
        Map newmap = mapBuildDirector.Construct();*/
        
    }
}
