package ass2;
import java.util.ArrayList;
import java.util.List;


public abstract class HunterGroup extends Enemy{

	private static final long serialVersionUID = -7535511482858588253L;
	private GetAction getAction;
	public HunterGroup(int id) {
		super(id);
		this.getAction = new GetActionHunterGroup();
		
	}
	/**
	 * finds a valid move to make and then moves the Hunter to that tile
	 * @param map
	 */
	public void getAction(Map map) {
		getAction.getAction(map, this);
	}
	public void setAction(GetAction getAction) {
		this.getAction = getAction;
	}
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		
		stringBuilder.append(this.getClass());
		stringBuilder.append("yes\n");
		return stringBuilder.toString();
	}
}
