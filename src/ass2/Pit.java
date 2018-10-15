package ass2;

/**
 * entity which kills the player if the player walks over it
 * when a boulder is moved over the pit it becomes normal floor
 * @author gordon
 *
 */
public class Pit extends Entity{
	private static final long serialVersionUID = 4446135853250097299L;

	public Pit(int id) {
		super(id);
	}

	@Override
	public String toString() {
		return null;
	}
}
