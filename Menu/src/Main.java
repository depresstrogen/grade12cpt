import java.awt.Color;

/**
 * This is the file that loads the game AKA the client class

 * 
 * @version January 26, 2021
 * @author Emma Power
 *
 */
public class Main {
	/**
	 * Makes a screen object and then does everything else there
	 * 
	 * @param args[] Blank but java requires it
	 */
	public static void main(String[] args) {
		Screen screen = new Screen(1000, 700);
		
		screen.loadElements("Menu Files/main.menu");
		
	}// main
	
	
	
}// Main