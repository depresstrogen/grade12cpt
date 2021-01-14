/**
 * This is the file that loads the game
 * 
 * @version January 14, 2021
 * @author Riley Power
 *
 */
public class Main {
	public static void main(String[] args) {
		Screen screen = new Screen(1000, 700);
		screen.loadElements("main.menu");
	}// Main
}