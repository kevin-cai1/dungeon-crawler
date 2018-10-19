package ass2;

public class TestMapBuilder {
	private MapBuilder mapBuilder;
	public TestMapBuilder (MapBuilder mapBuilder) {
		this.mapBuilder = mapBuilder;
	}
	public Map Construct() {
		return mapBuilder.setWinConditions(WinCondition.Treasure).setArrayLengthMap(20).setIdCounter().setMapEntities().build();
	}
	
    public static void main(final String[] arguments) {
        MapBuilder builder = new MapBuilderImplem();
        DesignEngine designEngine = new DesignEngine(20);
        Map designMap = designEngine.getMap();
        Player player = new Player(1);
        designMap = builder.setWinConditions(WinCondition.Treasure).setArrayLengthMap(20).setIdCounter().setMapEntities().build();
        if(designEngine.validateMap(designMap)) System.out.println("success");
        else { System.out.println("u suck"); }
        
        
        /*TestMapBuilder mapBuildDirector = new TestMapBuilder(builder);
        Map newmap = mapBuildDirector.Construct();*/
        
    }
}
