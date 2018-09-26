package ass2;

/**
 * type of obstacle which cannot be moved through or destroyed
 * @author gordon
 *
 */
public class Wall extends Obstacle{
	public Wall(int id) {
		super(id);
	}

	@Override
	public String toString() {
		return "#";
	}
}
