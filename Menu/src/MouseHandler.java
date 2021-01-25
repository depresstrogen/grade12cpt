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
		Thread gameThread;
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

				break;

			case "mainMenu3":
				screen.loadElements("Menu Files/credits.menu");
				break;

			case "mainMenu4":

				break;

			case "subMenu1":
				// Starts the game on a new thread
				screen.loadElements("Menu Files/loading.menu");
				gameThread = new Thread() {
					public void run() {
						game.start(screen, 0);
					}
				};
				gameThread.start();
				break;

			case "subMenu2":
				button = (Button) screen.getLastClickObject();
				screen.loadElements("Menu Files/loadfile.menu");
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

			case "r3Start":
				game.startRace(screen, "Race Files/race3.race");
				inter.hideRaceMenu(screen);
				break;

			case "saveGame":
				inter.showSaveMenu(screen);
				break;

			case "saveMenu1":
				sf.saveFile(game.getSaveStats(), "Save Files/file1.save");
				break;

			case "saveMenu2":
				sf.saveFile(game.getSaveStats(), "Save Files/file2.save");
				break;

			case "saveMenu3":
				sf.saveFile(game.getSaveStats(), "Save Files/file3.save");
				break;

			case "saveMenu4":
				inter.hideSaveMenu(screen);
				break;

			case "loadMenu1":
				screen.loadElements("Menu Files/loading.menu");
				gameThread = new Thread() {
					public void run() {
						game.start(screen, 1);
					}
				};
				gameThread.start();
				break;

			case "loadMenu2":
				screen.loadElements("Menu Files/loading.menu");
				gameThread = new Thread() {
					public void run() {
						game.start(screen, 2);
					}
				};
				gameThread.start();
				break;

			case "loadMenu3":
				screen.loadElements("Menu Files/loading.menu");
				gameThread = new Thread() {
					public void run() {
						game.start(screen, 3);
					}
				};
				gameThread.start();
				break;

			case "loadMenu4":
				screen.loadElements("Menu Files/sub.menu");
				break;

			case "shopMenu":
				inter.showShopMenu(screen, game);
				break;

			case "shopBack":
				inter.hideShopMenu(screen);
				break;

			case "sh1Start":
				game.buyCar(0, screen);
				inter.hideShopMenu(screen);
				break;

			case "sh2Start":
				game.buyCar(1, screen);
				inter.hideShopMenu(screen);
				break;

			case "sh3Start":
				game.buyCar(2, screen);
				inter.hideShopMenu(screen);
				break;

			case "sh4Start":
				game.buyCar(3, screen);
				inter.hideShopMenu(screen);
				break;

			case "creditsMenuExit":
				screen.loadElements("Menu Files/main.menu");
				break;
			}
		}
	}// mouseInputs
}// MouseHandler
