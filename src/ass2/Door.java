package ass2;

public class Door extends Obstacle{
	private int unique;
	private boolean open;
	public Door(int unique, int id) {
		super(id);
		this.unique = unique;
		open = false;
	}
	/**
	 * returns the status of the door (whether its open or closed)
	 * @return true if door is open, false if closed
	 */
	public boolean getStatus() {
		return open;
	}
	/**
	 * opens the door by changing its status to open/true
	 */
	public void openDoor() {
		open = true;
	}
	/**
	 * returns the unique id of the door so it can be matched to a key
	 * @return unique id of the door
	 */
	public int getUnique() {
		return unique;
	}
	@Override
	public String toString() {
		return null;
	}
}
