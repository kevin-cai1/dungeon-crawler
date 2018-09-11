package ass2;

import java.awt.print.Printable;
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
		if(input == 'w' || input == 'a' || input == 's' || input == 'd' || input == 'k' || input == 'l') {
			return true;
		}
		return false;
	}
	
	public void getMovement(char input) {
		switch (input) {
		case 'w':
			y++;
			System.out.print(Direction.NORTH);
			System.out.print(y + ",");
			System.out.println(x);
			break;
		case 'a':
			System.out.println(Direction.WEST);
			break;
		case 's':
			System.out.println(Direction.SOUTH);
			break;
		case 'd':
			System.out.println(Direction.EAST);
			break;
		}
	}
	public void getAction(char input) {
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
	}
	static int x, y = 0;
	public void getAction() {
		char input = getValidInput();
		if (isMovement(input)) {
			getMovement(input);
		}else {
			getAction(input);
			while(isMovement(input) == false) {
				input = getValidInput();
			}
			getMovement(input);
		}
	}
}
