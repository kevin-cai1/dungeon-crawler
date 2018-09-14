package ass2;

public class Door extends Obstacle{
	private int unique;
	private boolean open;
	public Door(int unique, int id) {
		super(id);
		this.unique = unique;
		open = false;
		// TODO Auto-generated constructor stub
	}
	public boolean getStatus() {
		return open;
	}
	public void openDoor() {
		open = true;
	}
	public int getUnique() {
		return unique;
	}
	@Override
	public String toString() {
		return null;
	}
}
