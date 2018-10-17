package ass2;

/**
 * type of obstacle which cannot be moved through or destroyed
 * @author gordon
 *
 */
public class Wall extends Obstacle{
	private static final long serialVersionUID = 6935753532812945094L;

	public Wall(int id) {
		super(id);
	}

	@Override
	public String toString() {
		return "Wall";
	}
}
