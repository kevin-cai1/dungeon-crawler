package ass2;

public class Key extends Entity{
	//unique identifier for this key
	private int unique;
	public Key(int unique) {
		super(unique);
		this.unique = unique;
	}
	public int getUnique() {
		return unique;
	}

	@Override
	public String toString() {
		return null;
	}
}
