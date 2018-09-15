package ass2;

public class Sword extends Entity{
	private int durability;
	public Sword(int id) {
		super(id);
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
		StringBuilder sb = new StringBuilder();
		sb.append("Sword durability: ");
		sb.append(getDurability());
		return sb.toString();
	}
	
	
	
	
}
