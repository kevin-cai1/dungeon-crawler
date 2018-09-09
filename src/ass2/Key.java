package ass2;

public class Key extends Entity{
	private int unique; //unique identifier for this key
	public Key(int unique) {
		this.unique = unique;
	}
	public int getUnique() {
		return unique;
	}
}
