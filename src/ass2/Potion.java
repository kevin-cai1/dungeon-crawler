package ass2;

public abstract class Potion extends Entity {

	private static final long serialVersionUID = 5592611473736843783L;
	private int usage;
	public Potion(int id) {
		super(id);
		usage = 1;
		// TODO Auto-generated constructor stub
	}
	/**
	 * Checks if the potion has been used or not
	 * @return usage of the potion, 1 if unused, 0 if used
	 */
	public int getUsage() {
		return usage;
	}
	/**
	 * changes the potion usage to 0 to signify it has been used
	 */
	public void use() {
		usage = 0;
	}
}
