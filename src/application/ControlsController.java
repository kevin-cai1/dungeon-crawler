package application;



import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class ControlsController {
	
	@FXML
	private Label upLabel;
	
	@FXML
	private Label downLabel;
	
	@FXML
	private Label leftLabel;
	
	@FXML
	private Label rightLabel;
	
	@FXML
	private Label bombLabel;
	
	@FXML
	private Label swordLabel;
	
	@FXML
	private Label arrowLabel;
	
	@FXML
	private ToggleButton upButton;
	
	@FXML
	private ToggleButton downButton;
	
	@FXML
	private ToggleButton leftButton;
	
	@FXML
	private ToggleButton rightButton;
	
	@FXML
	private ToggleButton bombButton;
	
	@FXML
	private ToggleButton swordButton;
	
	@FXML
	private ToggleButton arrowButton;
	
	private Stage s;
	
	private Controls controls;
	
	private ToggleGroup toggleGroup = new ToggleGroup();
	
	public ControlsController(Stage s) {
		this.s = s;
		this.controls = Controls.getInstance();
		System.out.println("test");
		
	}
	
	@FXML
	public void initialize() {
		
		upButton.setToggleGroup(toggleGroup);
		downButton.setToggleGroup(toggleGroup);
		leftButton.setToggleGroup(toggleGroup);
		rightButton.setToggleGroup(toggleGroup);
		bombButton.setToggleGroup(toggleGroup);
		swordButton.setToggleGroup(toggleGroup);
		arrowButton.setToggleGroup(toggleGroup);		
		upLabel.setText("Move Up: '" + controls.getUpButton().toString() + "'");
		downLabel.setText("Move Down: '" + controls.getDownButton().toString() + "'");
		leftLabel.setText("Move Left: '" + controls.getLeftButton().toString() + "'");
		rightLabel.setText("Move Right: '" + controls.getRightButton().toString() + "'");
		bombLabel.setText("Drop Bomb: '" + controls.getBombButton().toString() + "'");
		arrowLabel.setText("ToggleArrow: '" + controls.getArrowButton().toString() + "'");
		swordLabel.setText("ToggleSword: '" + controls.getSwordButton().toString() + "'");
		//upButton.setOnAction(value);;
	}
	
	public void setPerformedAction(ToggleButton button) {
		
	}
	
	@FXML
	public void returnToMenu() throws Exception {
		MenuScene menuScene = new MenuScene(s);
		menuScene.display();
	}
	
	@FXML
	public void selectUp() {
		if (upButton.isSelected()) {
			upButton.setText("Press any key");
		} else {
			upButton.setText("Change Button");
		}
	}
	
	@FXML
	public void selectDown() {
		if (downButton.isSelected()) {
			downButton.setText("Press any key");
		} else {
			downButton.setText("Change Button");
		}
	}
	
	@FXML
	public void selectLeft() {
		if (leftButton.isSelected()) {
			leftButton.setText("Press any key");
		} else {
			leftButton.setText("Change Button");
		}
	}
	
	@FXML
	public void selectRight() {
		if (rightButton.isSelected()) {
			rightButton.setText("Press any key");
		} else {
			rightButton.setText("Change Button");
		}
	}
	
	@FXML
	public void selectBomb() {
		if (bombButton.isSelected()) {
			bombButton.setText("Press any key");
		} else {
			bombButton.setText("Change Button");
		}
	}
	
	@FXML
	public void selectArrow() {
		if (arrowButton.isSelected()) {
			arrowButton.setText("Press any key");
		} else {
			arrowButton.setText("Change Button");
		}
	}
	
	@FXML
	public void selectSword() {
		if (swordButton.isSelected()) {
			swordButton.setText("Press any key");
		} else {
			swordButton.setText("Change Button");
		}
	}
	
	
	
	@FXML
	private void handleKeyPress(KeyEvent event) {
		KeyCode key = event.getCode();
		ToggleButton selectedButton = (ToggleButton) toggleGroup.getSelectedToggle();
		if (selectedButton == leftButton) {
			controls.setLeftButton(key);
			leftButton.setSelected(false);
			leftButton.setText("Change Button");
			leftLabel.setText("Move Left: '" + controls.getLeftButton().toString() + "'");
		} else if (selectedButton == rightButton) {
			controls.setRightButton(key);
			rightButton.setSelected(false);
			rightButton.setText("Change Button");
			rightLabel.setText("Move Right: '" + controls.getRightButton().toString() + "'");
		} else if (selectedButton == downButton) {
			controls.setDownButton(key);
			downButton.setSelected(false);
			downButton.setText("Change Button");
			downLabel.setText("Move Down: '" + controls.getDownButton().toString() + "'");
		} else if (selectedButton == upButton) {
			controls.setUpButton(key);
			upButton.setSelected(false);
			upButton.setText("Change Button");
			upLabel.setText("Move Up: '" + controls.getUpButton().toString() + "'");
		} else if (selectedButton == bombButton) {
			controls.setBombButton(key);
			bombButton.setSelected(false);
			bombButton.setText("Change Button");
			bombLabel.setText("Drop Bomb: '" + controls.getBombButton().toString() + "'");
		} else if (selectedButton == swordButton) {
			controls.setSwordButton(key);
			swordButton.setSelected(false);
			swordButton.setText("Change Button");
			swordLabel.setText("Sword Toggle: '" + controls.getSwordButton().toString() + "'");
		} else if (selectedButton == arrowButton) {
			controls.setArrowButton(key);
			arrowButton.setSelected(false);
			arrowButton.setText("Change Button");
			arrowLabel.setText("Arrow Toggle: '" + controls.getArrowButton().toString() + "'");
		}
	}
	
	
}
