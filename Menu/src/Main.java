import java.awt.Color;

/**
 * This is the file that loads the game AKA the client class
 * 
 * This program can ONLY be launched in eclipse, it will NOT compile in Dr. Java
 * or if it does there will be unintended glitches
 * 
 * @version January 25, 2021
 * @author Riley Power
 *
 */
public class Main {
	/**
	 * Makes a screen object and then does everything else there
	 * 
	 * @param args Blank but java requires it
	 */
	public static void main(String[] args) {
		Screen screen = new Screen(1000, 700);
		screen.loadElements("Menu Files/main.menu");
	}// main
}// Main	