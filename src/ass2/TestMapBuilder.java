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

        TestMapBuilder mapBuildDirector = new TestMapBuilder(builder);

        System.out.println(mapBuildDirector.Construct());
    }
}
