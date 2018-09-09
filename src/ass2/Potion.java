package ass2;

public abstract class Potion extends Entity {
	private int usage;
	public Potion() {
		usage = 1;
		// TODO Auto-generated constructor stub
	}
	public int getUsage() { //returns if the potion has been used or not
		return usage;
	}
	public void use() {
		usage = 0;
	}
}
