package application;

import javafx.scene.input.KeyCode;

public class Controls {
	private KeyCode upButton;
	private KeyCode downButton;
	private KeyCode leftButton;
	private KeyCode rightButton;
	private KeyCode dropBomb;
	private KeyCode swordToggle;
	private KeyCode arrowToggle;
	
	private static Controls single_instance = null;
	
	private Controls() {
		// TODO Auto-generated constructor stub
		this.upButton = KeyCode.W;
		this.downButton = KeyCode.S;
		this.leftButton = KeyCode.A;
		this.rightButton = KeyCode.D;
		this.dropBomb = KeyCode.B;
		this.swordToggle = KeyCode.L;
		this.arrowToggle = KeyCode.K;
	}
	
	public static Controls getInstance() {
		if (single_instance == null) {
			single_instance = new Controls();
		}
		return single_instance;
	}
	
	
	public void setUpButton(KeyCode button) {
		this.upButton = button;
	}
	
	public void setDownButton(KeyCode button) {
		this.downButton = button;
	}
	
	public void setLeftButton(KeyCode button) {
		this.leftButton = button;
	}
	
	public void setRightButton(KeyCode button) {
		this.rightButton = button;
	}
	
	public void setBombButton(KeyCode button) {
		this.dropBomb = button;
	}
	
	public void setSwordButton(KeyCode button) {
		this.swordToggle = button;
	}
	
	public void setArrowButton(KeyCode button) {
		this.arrowToggle = button;
	}
	
	public KeyCode getUpButton() {
		return this.upButton;
	}
	
	public KeyCode getDownButton() {
		return this.downButton;
	}
	
	public KeyCode getLeftButton() {
		return this.leftButton;
	}
	
	public KeyCode getRightButton() {
		return this.rightButton;
	}
	
	public KeyCode getBombButton() {
		return this.dropBomb;
	}
	
	public KeyCode getSwordButton() {
		return this.swordToggle;
	}
	
	public KeyCode getArrowButton() {
		return this.arrowToggle;
	}
}
