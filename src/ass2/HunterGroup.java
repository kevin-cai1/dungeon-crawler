package ass2;
import java.util.ArrayList;
import java.util.List;


public abstract class HunterGroup extends GetActionHunterGroup{

	private static final long serialVersionUID = -7535511482858588253L;

	public HunterGroup(int id) {
		super(id);
	}
	/**
	 * finds a valid move to make and then moves the Hunter to that tile
	 * @param map
	 */

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		
		stringBuilder.append(this.getClass());
		stringBuilder.append("yes\n");
		return stringBuilder.toString();
	}
}
