package application;



import java.awt.event.ActionEvent;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MoveAction;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Toggle;
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
	}
	
	@FXML
	public void returnToMenu() throws Exception {
		MenuScene menuScene = new MenuScene(s);
		menuScene.display();
	}
	
	@FXML
	private void handleKeyPress(KeyEvent event) {
		KeyCode key = event.getCode();
		ToggleButton selectedButton = (ToggleButton) toggleGroup.getSelectedToggle();
		if (selectedButton == leftButton) {
			controls.setLeftButton(key);
			leftLabel.setText("Move Left: '" + controls.getLeftButton().toString() + "'");
		} else if (selectedButton == rightButton) {
			controls.setRightButton(key);
			rightLabel.setText("Move Right: '" + controls.getRightButton().toString() + "'");
		} else if (selectedButton == downButton) {
			controls.setDownButton(key);
			downLabel.setText("Move Down: '" + controls.getDownButton().toString() + "'");
		} else if (selectedButton == upButton) {
			controls.setUpButton(key);
			upLabel.setText("Move Up: '" + controls.getUpButton().toString() + "'");
		} else if (selectedButton == bombButton) {
			controls.setBombButton(key);
			bombLabel.setText("Drop Bomb: '" + controls.getBombButton().toString() + "'");
		} else if (selectedButton == swordButton) {
			controls.setSwordButton(key);
			swordLabel.setText("Sword Toggle: '" + controls.getSwordButton().toString() + "'");
		} else if (selectedButton == arrowButton) {
			controls.setArrowButton(key);
			arrowLabel.setText("Arrow Toggle: '" + controls.getArrowButton().toString() + "'");
		}
	}
	
	
}
