package ass2;

public class Sword extends Entity{
	private int durability;
	public Sword() {
		durability = 5;
	}
	public int getDurability() {
		return durability;
	}
	public void reduceDurability() {
		durability--;
	}
	public boolean durabilityZero() {
		return(durability == 0);
	}

	@Override
	public String toString() {
		return null;
	}
}
