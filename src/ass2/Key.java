package ass2;

public class Key extends Entity{
	//unique identifier for this key
	private int unique;
	public Key(int unique, int id) {
		super(id);
		this.unique = unique;
	}
	/**
	 * @return unique id of the key to match it with a certain door
	 */
	public int getUnique() {
		return unique;
	}

	@Override
	public String toString() {
		return null;
	}
}
