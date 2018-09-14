package ass2;
import java.util.Scanner;

public class PlayerControls{
	
	public PlayerControls() {	}
	
	private String getInput() {
		Scanner sc = new Scanner(System.in);
		return sc.nextLine();
	}
	
	public char getValidInput(){
		String input = getInput();
		while(input.length() != 1 || controlExists(input.charAt(0)) == false) {
			System.out.println("input must be valid");

			input = getInput();
		}
		return input.charAt(0);
	}
	
	private boolean controlExists(char input) {
		if(input == 'w' || input == 'a' || input == 's' || input == 'd' || input == 'q' || input == 'e' || input == 'r') {
			return true;
		}
		return false;
	}
	
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
	
	public Direction returnMovement() {
		char input = getValidInput();
		return getMovement(input);
	}
	
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
	
	public boolean isMovement(char input) {
		if(input == 'w' || input == 'a' || input == 's' || input == 'd') {
			return true;
		}
		return false;
	}
}
