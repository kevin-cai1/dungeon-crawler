package ass2;

public class FloorSwitch extends Entity{
	private boolean triggered;
	
	public FloorSwitch(int id) {
		super(id);
		// TODO Auto-generated constructor stub
		this.triggered = false;
	}
	
	public void untriggerSwitch() {
		this.triggered = false;
	}
	
	public void triggerSwitch() {
		this.triggered = true;
	}
	
	public boolean getStatus() {
		return triggered;
	}

	@Override
	public String toString() {
		return null;
	}
}
