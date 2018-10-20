package ass2;

/**
 * used to check win condition if the player reaches the exit
 * @author gordon
 *
 */
public class Exit extends Entity{
	private static final long serialVersionUID = 2049487000383996268L;

	public Exit(int id) {
		super(id);
	}

	@Override
	public String toString() {
		return "Exit";
	}
	public String imgName() {
		return "exit";
	}
}
