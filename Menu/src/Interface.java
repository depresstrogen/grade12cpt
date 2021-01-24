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
		
		Button save = new Button(40, 40, 50, 115, Color.RED, "saveGame");
		Text saveText = new Text(65, 75, 30, "Save", "saveGameText");
		
		screen.add(button);
		screen.add(text);

		screen.add(findRaces);
		screen.add(findRaceText);
		
		screen.add(save);
		screen.add(saveText);
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
		screen.replace(dummy, screen.getIndex("saveGame"));
		screen.replace(dummy, screen.getIndex("saveGameText"));
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
	
	public void showSaveMenu(Screen screen) {
		Picture bg = new Picture(0, 0, "saveMenuBG", "Image Files/bluegreengrad.png");
		screen.add(bg);
		Button option1 = new Button(0, 250, 70, 600, Color.GREEN, "saveMenu1");
		Button option2 = new Button(0, 350, 70, 600, Color.GREEN, "saveMenu2");
		Button option3 = new Button(0, 450, 70, 600, Color.GREEN, "saveMenu3");
		Button option4 = new Button(0, 550, 70, 600, Color.GREEN, "saveMenu4");

		screen.add(option1);
		screen.add(option2);
		screen.add(option3);
		screen.add(option4);

		Text title = new Text(40, 150, 120, "Save Menu", "saveTitle");
		Text option1Text = new Text(650, 300, 50, "File 1", "sm1Text");
		Text option2Text = new Text(650, 400, 50, "File 2", "sm2Text");
		Text option3Text = new Text(650, 500, 50, "File 3", "sm3Text");
		Text option4Text = new Text(650, 600, 50, "Back", "sm4Text");
		Text watermark = new Text(10, 650, 12, "By - Riley Power", "saveWatermark");

		
		screen.add(title);
		screen.add(option1Text);
		screen.add(option2Text);
		screen.add(option3Text);
		screen.add(option4Text);
		screen.add(watermark);

	}
	
	public void hideSaveMenu(Screen screen) {
		ScreenElement dummy = new ScreenElement(0, 0, "dummy");
		screen.replace(dummy, screen.getIndex("saveMenuBG"));
		screen.replace(dummy, screen.getIndex("saveMenu1"));
		screen.replace(dummy, screen.getIndex("saveMenu2"));
		screen.replace(dummy, screen.getIndex("saveMenu3"));
		screen.replace(dummy, screen.getIndex("saveMenu4"));
		screen.replace(dummy, screen.getIndex("saveTitle"));
		screen.replace(dummy, screen.getIndex("sm1Text"));
		screen.replace(dummy, screen.getIndex("sm2Text"));
		screen.replace(dummy, screen.getIndex("sm3Text"));
		screen.replace(dummy, screen.getIndex("sm4Text"));
		screen.replace(dummy, screen.getIndex("saveWatermark"));
	}
}// MusicInterface
