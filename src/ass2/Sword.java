package ass2;

public class Sword extends Entity{
	private static final long serialVersionUID = 3366629573301873939L;
	private int durability;
	public Sword(int id) {
		super(id);
		durability = 5;
	}
	/**
	 * @return durability of the sword as an int between 0 and 5
	 */
	public int getDurability() {
		return durability;
	}
	/**
	 * reduces the durability of the sword by 1
	 */
	public void reduceDurability() {
		durability--;
	}
	/**
	 * checks if the swords durability has been reduced to 0
	 * @return true if durability is equal to 0
	 */
	public boolean durabilityZero() {
		return(durability == 0);
	}

	@Override
	public String toString() {
		return "Sword";
	}
	public String imgName() {
		return "sword";
	}
	
	
	
}
