import java.awt.Color;

/**
 * The class for the in game radio / music interface, allows the user to change
 * stations mute radio etc.
 * 
 * @version January 19, 2021
 * @author Riley Power
 *
 */
public class MusicInterface {
	/**
	 * Adds the music interface to the screen
	 * 
	 * @param screen The screen to add the interface to
	 */
	public void startInterface(Screen screen) {
		Button button = new Button(790, 565, 50, 115, Color.RED, "startMusic");
		Text text = new Text(800, 600, 30, "Shuffle", "playText");
		screen.add(button);
		screen.add(text);
	}// startInterface

	/**
	 * Removes the music interface from the screen, but keeps the music playing
	 * 
	 * @param screen The screen which has the interface displayed on it
	 */
	public void hideInterface(Screen screen) {
		ScreenElement dummy = new ScreenElement(0, 0, "dummy");
		screen.replace(dummy, screen.getIndex("startMusic"));
		screen.replace(dummy, screen.getIndex("playText"));
	}// hideInterface
}// MusicInterface
