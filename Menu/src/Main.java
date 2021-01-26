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
		
		Button option1 = new Button(0, 250, 70, 600, Color.RED, "mainMenu1");
		Button option2 = new Button(0, 350, 70, 600, Color.RED, "mainMenu2");
		Button option3 = new Button(0, 450, 70, 600, Color.RED, "mainMenu3");
		Button option4 = new Button(0, 550, 70, 600, Color.RED, "mainMenu4");
		screen.add(option1);
		screen.add(option2);
		screen.add(option3);
		screen.add(option4);

		Text title = new Text(40, 150, 100, "Grade 12 CPT", "title");
		Text option1Text = new Text(650, 300, 50, "Play Game", "mm1Text");
		Text option2Text = new Text(650, 400, 50, "How To Play", "mm2Text");
		Text option3Text = new Text(650, 500, 50, "Credits", "mm3Text");
		Text option4Text = new Text(650, 600, 50, "Exit", "mm4Text");
		Text watermark = new Text(10, 650, 12, "By - Riley Power", "title");

		screen.add(title);
		screen.add(option1Text);
		screen.add(option2Text);
		screen.add(option3Text);
		screen.add(option4Text);
		screen.add(watermark);

		Picture penguin = new Picture(750, 60, "pengu", "Image Files/penguinDance.gif");
		screen.add(penguin);
		
		screen.saveElements("Menu Files/main.menu");
		
		//screen.loadElements("Menu Files/main.menu");
	}// main
}// Main