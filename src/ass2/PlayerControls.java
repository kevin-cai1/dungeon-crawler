package ass2;
import java.util.Scanner;

public class PlayerControls{
	private String getInput() {
		Scanner sc = new Scanner(System.in);
		return sc.nextLine();
	}
	
	private char getValidInput(){
		String input = getInput();
		while(input.length() != 1 || controlExists(input.charAt(0)) == false) {
			System.out.println("input must be valid");

			input = getInput();
		}
		return input.charAt(0);
	}
	
	private boolean controlExists(char input) {
		if(input == 'w' || input == 'a' || input == 's' || input == 'd' /*|| input == 'k' || input == 'l'*/) {
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
	
	/*private void getAction(char input) {
		switch (input) {
		case 'k':
			System.out.println("Shooting bow ");
			break;
		case 'l':
			System.out.println("Swinging sword ");
			break;
		}
		
	}
	
	private boolean isMovement(char input) {
		if(input == 'w' || input == 'a' || input == 's' || input == 'd') {
			return true;
		}
		return false;
	}*/
}
