import java.awt.Color;
import java.util.Stack;

/**
 * Parses every mouse click to avoid clogging the Screen class with all the
 * possible cases
 * 
 * @version January 23, 2020
 * @author Riley Power
 *
 */

public class MouseHandler {

	/**
	 * Process the latest mouse input at the time it is called It currently checks
	 * for - Button Clicks This method is very flexible, and you have to have a case
	 * for every action that is planned to be implemented also like every possible
	 * clickable object is here so...
	 * 
	 * @param screen The screen which contains
	 */
	

	public void mouseInputs(Screen screen, Game game) {
		SaveFile sf = new SaveFile();
		Interface inter = new Interface();
		Button button;
		Text text;
		if (screen.isNewClick()) {
			String lastClick = screen.getLastClick();
			switch (lastClick) {

			case "mainMenu1":
				button = (Button) screen.getLastClickObject();
				screen.loadElements("Menu Files/sub.menu");
				break;

			case "mainMenu2":
				button = (Button) screen.getLastClickObject();
				text = (Text) screen.getScreenElement("mm2Text");
				if (button.getColor().equals(Color.RED)) {
					button.setColor(Color.ORANGE);
					text.setText("Orange");
				} else {
					button.setColor(Color.RED);
					text.setText("Red");
				}
				screen.replace(button, screen.getIndex(lastClick));
				screen.replace(text, screen.getIndex("mm2Text"));
				break;

			case "mainMenu3":
				button = (Button) screen.getLastClickObject();
				text = (Text) screen.getScreenElement("mm3Text");
				if (button.getColor().equals(Color.RED)) {
					button.setColor(Color.PINK);
					text.setText("Pink");
				} else {
					button.setColor(Color.RED);
					text.setText("Red");
				}
				screen.replace(button, screen.getIndex(lastClick));
				screen.replace(text, screen.getIndex("mm3Text"));
				break;

			case "mainMenu4":
				button = (Button) screen.getLastClickObject();
				text = (Text) screen.getScreenElement("mm4Text");
				if (button.getColor().equals(Color.RED)) {
					button.setColor(Color.CYAN);
					text.setText("Cyan");
				} else {
					button.setColor(Color.RED);
					text.setText("Red");
				}
				screen.replace(button, screen.getIndex(lastClick));
				screen.replace(text, screen.getIndex("mm4Text"));
				break;

			case "subMenu1":
				// Starts the game on a new thread
				screen.loadElements("Menu Files/loading.menu");
				Thread gameThread = new Thread() {
					public void run() {
						game.start(screen);
						;
					}
				};
				gameThread.start();
				break;

			case "subMenu4":
				button = (Button) screen.getLastClickObject();
				screen.loadElements("Menu Files/main.menu");
				break;

			case "startMusic":
				MusicPlayer player = new MusicPlayer();
				Thread musicThread = new Thread() {
					public void run() {
						player.shuffle();

					}
				};
				musicThread.start();
				break;
			case "raceMenu":

				inter.showRaceMenu(screen);
				break;

			case "r1Start":
				game.startRace(screen, "Race Files/race1.race");
				inter.hideRaceMenu(screen);
				break;

			case "r2Start":
				game.startRace(screen, "Race Files/race2.race");
				inter.hideRaceMenu(screen);
				break;

			case "saveGame":
				inter.showSaveMenu(screen);
				break;
				
			case "saveMenu1":
				sf.saveFile(game.getSaveStats(), "Save Files/file1.save");
				break;
			case "saveMenu4":
				inter.hideSaveMenu(screen);
				break;
			}
		

		}
	}// mouseInputs
}// MouseHandler
