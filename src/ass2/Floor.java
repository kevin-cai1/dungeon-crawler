package ass2;

/**
 * base entity on all tiles
 * @author gordon
 *
 */
public class Floor extends Entity {
	public Floor(int id) {
		super(id);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		//return Integer.toHexString(System.identityHashCode(this));
		return ".";
	}
}
