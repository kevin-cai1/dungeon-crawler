package ass2;

/**
 * allows player to become temporarily 'invincible' so that touching enemies destroys them 
 * enemies run away from the player while this potion is in effect
 * @author gordon
 *
 */
public class InvincibilityPotion extends Potion {
	private static final long serialVersionUID = 1195682391454281854L;
	public InvincibilityPotion(int id) {
		super(id);
	}
	@Override
	public String toString() {
		return null;
	}
}
