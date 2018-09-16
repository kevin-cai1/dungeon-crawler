package ass2;
import java.util.Scanner;

public class PlayerControls{
	
	public PlayerControls() {	}
	
	/**
	 * gets input from user
	 * @return string that the user has inputted
	 */
	private String getInput() {
		Scanner sc = new Scanner(System.in);
		return sc.nextLine();
	}
	
	/** 
	 * calls getInput to get input from user and checks that the input is valid
	 * if input is invalid, prints "input must be valid" and calls getInput to have the user provide new input to be checked again
	 * @return character the user inputted if its valid
	 */
	public char getValidInput(){
		String input = getInput();
		while(input.length() != 1 || controlExists(input.charAt(0)) == false) {
			System.out.println("input must be valid");

			input = getInput();
		}
		return input.charAt(0);
	}
	
	/**
	 * checks if the inputted character corresponds to a control or action in the game
	 * @param input character input provided by the user
	 * @return true if the input character does correspond to a control in the game
	 */
	private boolean controlExists(char input) {
		if(input == 'w' || input == 'a' || input == 's' || input == 'd' || input == 'q' || input == 'e' || input == 'r') {
			return true;
		}
		return false;
	}
	
	/**
	 * gets the direction the player is trying to move based on given input
	 * @param input 
	 * @return Direction that the player is trying to move, null if input is not a movement command
	 */
	public Direction getMovement(char input) {
		switch (input) {
		case 'w':
			return Direction.NORTH;
		case 'a':
			return Direction.WEST;
		case 's':
			return Direction.SOUTH;
		case 'd':
			return Direction.EAST;
		}
		return null;
	}
	/**
	 * calls getValidInput to collect movement input from the player
	 * @return direction the player is trying to move in
	 */
	public Direction returnMovement() {
		char input = getValidInput();
		return getMovement(input);
	}
	
	/**
	 * gets the weapon that the user is trying to use based on their input
	 * @param input input entered by user corresponding to a certain weapon command
	 */
	private void getWeapon(char input) {
		switch (input) {
		case 'r':
			System.out.println("Shooting bow ");
			break;
		case 'e':
			System.out.println("Swinging sword ");
			break;
		case 'q':
			
		}
		
	}
	
	/**
	 * checks if input corresponds to a movement command
	 * @param input
	 * @return true if input is movement command
	 */
	public boolean isMovement(char input) {
		if(input == 'w' || input == 'a' || input == 's' || input == 'd') {
			return true;
		}
		return false;
	}
}
