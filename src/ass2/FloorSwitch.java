package ass2;

public class FloorSwitch extends Entity{
	private boolean triggered;
	
	public FloorSwitch() {
		// TODO Auto-generated constructor stub
		this.triggered = false;
	}
	
	public void untriggerSwitch() {
		this.triggered = false;
	}
	
	public void triggerSwitch() {
		this.triggered = true;
	}

	@Override
	public String toString() {
		return null;
	}
}
