import java.awt.Color;

/**
 * The class which holds all UI elements during the game as loading these from
 * file would not allow the to be accessed very well
 * 
 * @version January 23, 2021
 * @author Riley Power
 *
 */
public class Interface {
	/**
	 * Adds the main interface to the screen
	 * 
	 * @param screen The screen to add the interface to
	 */
	public void startInterface(Screen screen) {
		Button button = new Button(790, 565, 50, 115, Color.RED, "startMusic");
		Text text = new Text(800, 600, 30, "Shuffle", "playText");

		Button findRaces = new Button(40, 565, 50, 180, Color.RED, "raceMenu");
		Text findRaceText = new Text(50, 600, 30, "Find Rallies", "findRaceText");
		screen.add(button);
		screen.add(text);

		screen.add(findRaces);
		screen.add(findRaceText);
	}// startInterface

	/**
	 * Removes the main interface from the screen, but keeps the music playing
	 * 
	 * @param screen The screen which has the interface displayed on it
	 */
	public void hideInterface(Screen screen) {
		ScreenElement dummy = new ScreenElement(0, 0, "dummy");
		screen.replace(dummy, screen.getIndex("startMusic"));
		screen.replace(dummy, screen.getIndex("playText"));
		screen.replace(dummy, screen.getIndex("raceMenu"));
		screen.replace(dummy, screen.getIndex("findRaceText"));
	}// hideInterface

	/**
	 * Shows the race menu on screen
	 * 
	 * @param screen Screen with the race menu active
	 */
	public void showRaceMenu(Screen screen) {
		Picture bg = new Picture(0, 0, "raceMenuBG", "Image Files/bluegreengrad.png");
		Text title = new Text(300, 100, 50, "Avaliable Rallies", "rmTitle");
		Button r1 = new Button(220, 200, 50, 115, Color.RED, "r1Start");
		Button r2 = new Button(620, 200, 50, 115, Color.RED, "r2Start");
		Button r3 = new Button(220, 300, 50, 115, Color.RED, "r3Start");
		Button r4 = new Button(620, 300, 50, 115, Color.RED, "r4Start");
		Text r1Text = new Text(230, 235, 30, "Race 1", "r1Text");
		Text r2Text = new Text(630, 235, 30, "Race 2", "r2Text");
		Text r3Text = new Text(230, 335, 30, "Race 3", "r3Text");
		Text r4Text = new Text(630, 335, 30, "Race 4", "r4Text");
		screen.add(bg);
		screen.add(title);
		screen.add(r1);
		screen.add(r2);
		screen.add(r3);
		screen.add(r4);
		screen.add(r1Text);
		screen.add(r2Text);
		screen.add(r3Text);
		screen.add(r4Text);
	}// showRaceMenu

	/**
	 * Hides the race menu on screen
	 * 
	 * @param screen Screen with the race menu active
	 */
	public void hideRaceMenu(Screen screen) {
		ScreenElement dummy = new ScreenElement(0, 0, "dummy");
		screen.replace(dummy, screen.getIndex("raceMenuBG"));
		screen.replace(dummy, screen.getIndex("rmTitle"));
		screen.replace(dummy, screen.getIndex("r1Start"));
		screen.replace(dummy, screen.getIndex("r2Start"));
		screen.replace(dummy, screen.getIndex("r3Start"));
		screen.replace(dummy, screen.getIndex("r4Start"));
		screen.replace(dummy, screen.getIndex("r1Text"));
		screen.replace(dummy, screen.getIndex("r2Text"));
		screen.replace(dummy, screen.getIndex("r3Text"));
		screen.replace(dummy, screen.getIndex("r4Text"));
	}// hideRaceMenu
}// MusicInterface
