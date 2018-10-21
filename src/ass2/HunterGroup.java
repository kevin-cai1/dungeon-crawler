package ass2;


public abstract class HunterGroup extends Enemy{

	private static final long serialVersionUID = -7535511482858588253L;
	public HunterGroup(int id) {
		super(id);
		this.getAction = new GetActionHunterGroup();
		
	}
	/**
	 * finds a valid move to make and then moves the Hunter to that tile
	 * @param map
	 */
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		
		stringBuilder.append(this.getClass());
		stringBuilder.append("yes\n");
		return stringBuilder.toString();
	}
	@Override
	public void setOgAction() {
		// TODO Auto-generated method stub
		getAction = new GetActionHunterGroup();
	}
}
