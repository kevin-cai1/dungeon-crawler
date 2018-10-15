package ass2;

public class Key extends Entity{
	private static final long serialVersionUID = 7431080476416073678L;
	//unique identifier for this key
	private KeyEnum unique;
	public Key(KeyEnum unique, int id) {
		super(id);
		this.unique = unique;
	}
	/**
	 * @return unique id of the key to match it with a certain door
	 */
	public KeyEnum getUnique() {
		return unique;
	}

	@Override
	public String toString() {
		return null;
	}
}
