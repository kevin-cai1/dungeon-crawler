package ass2;

/**
 * Type of entity which can be collected by the player to win the game
 * @author gordon
 *
 */
public class Treasure extends Entity{
	private static final long serialVersionUID = -5439738928056308280L;

	public Treasure(int id) {
		super(id);
	}

	@Override
	public String toString() {
		return "Treasure";
	}
}
