package ass2;

public class FloorSwitch extends Entity {
	private static final long serialVersionUID = 6928628401147115408L;
	private boolean triggered;
	
	public FloorSwitch(int id) {
		super(id);
		this.triggered = false;
	}
	
	/**
	 * Changes switch trigger status to false (untriggered)
	 */
	public void untriggerSwitch() {
		this.triggered = false;
	}
	
	/**
	 * Changes switch trigger status to true (triggered)
	 */
	public void triggerSwitch() {
		this.triggered = true;
	}
	
	/**
	 * Returns the switch status (whether its triggered or not)
	 * @return triggered, true if triggered, false if not
	 */
	public boolean getStatus() {
		return triggered;
	}

	@Override
	public String toString() {
		return "FloorSwitch";
	}
}
