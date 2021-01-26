import java.awt.Color;

/**
 * The class which holds all UI elements during the game as loading these from
 * file would not allow them to be accessed very well, nor would it allow them
 * to use if statements or loops
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
		Button radioOn = new Button(170, 110, 50, 180, Color.RED, "startMusic");
		Text radioText = new Text(195, 145, 30, "Radio On", "playText");

		Button findRaces = new Button(170, 40, 50, 180, Color.RED, "raceMenu");
		Text findRaceText = new Text(185, 75, 30, "Find Rallies", "findRaceText");

		Button save = new Button(40, 40, 50, 115, Color.RED, "saveGame");
		Text saveText = new Text(65, 75, 30, "Save", "saveGameText");

		Button shop = new Button(40, 110, 50, 115, Color.RED, "shopMenu");
		Text shopText = new Text(65, 145, 30, "Shop", "shopButtonText");

		screen.add(radioOn);
		screen.add(radioText);

		screen.add(findRaces);
		screen.add(findRaceText);

		screen.add(save);
		screen.add(saveText);

		screen.add(shop);
		screen.add(shopText);
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
		screen.replace(dummy, screen.getIndex("shopMenu"));
		screen.replace(dummy, screen.getIndex("shopButtonText"));
	}// hideInterface

	/**
	 * Shows the race menu on screen
	 * 
	 * @param screen Screen to show the race menu on
	 */
	public void showRaceMenu(Screen screen) {
		Picture bg = new Picture(0, 0, "raceMenuBG", "Image Files/bluegreengrad.png");
		Text title = new Text(300, 100, 50, "Avaliable Rallies", "rmTitle");
		Button r1 = new Button(260, 200, 50, 115, Color.RED, "r1Start");
		Button r2 = new Button(580, 200, 50, 115, Color.RED, "r2Start");
		Button r3 = new Button(260, 300, 50, 115, Color.RED, "r3Start");
		Button r4 = new Button(580, 300, 50, 115, Color.RED, "r4Start");
		Button r5 = new Button(260, 400, 50, 115, Color.RED, "r5Start");
		Button r6 = new Button(580, 400, 50, 115, Color.RED, "r6Start");
		Text r1Text = new Text(270, 235, 30, "Race 1", "r1Text");
		Text r2Text = new Text(590, 235, 30, "Race 2", "r2Text");
		Text r3Text = new Text(270, 335, 30, "Race 3", "r3Text");
		Text r4Text = new Text(590, 335, 30, "Race 4", "r4Text");
		Text r5Text = new Text(270, 435, 30, "Race 5", "r5Text");
		Text r6Text = new Text(590, 435, 30, "Race 6", "r6Text");
		screen.add(bg);
		screen.add(title);
		screen.add(r1);
		screen.add(r2);
		screen.add(r3);
		screen.add(r4);
		screen.add(r5);
		screen.add(r6);
		screen.add(r1Text);
		screen.add(r2Text);
		screen.add(r3Text);
		screen.add(r4Text);
		screen.add(r5Text);
		screen.add(r6Text);
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
		screen.replace(dummy, screen.getIndex("r5Start"));
		screen.replace(dummy, screen.getIndex("r6Start"));
		screen.replace(dummy, screen.getIndex("r1Text"));
		screen.replace(dummy, screen.getIndex("r2Text"));
		screen.replace(dummy, screen.getIndex("r3Text"));
		screen.replace(dummy, screen.getIndex("r4Text"));
		screen.replace(dummy, screen.getIndex("r5Text"));
		screen.replace(dummy, screen.getIndex("r6Text"));
	}// hideRaceMenu

	/**
	 * Shows the save menu on screen
	 * 
	 * @param screen The screen to show the save menu on
	 */
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
	}// showSaveMenu

	/**
	 * Hides the save menu from the screen
	 * 
	 * @param screen The screen with the save menu active
	 */
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

	/**
	 * Shows the shop menu on screen
	 * 
	 * @param screen The screen to show the menu on
	 * @param game   The game which will be purchasing something
	 */
	public void showShopMenu(Screen screen, Game game) {
		Button r1;
		Button r2;
		Button r3;
		Button r4;

		Text stat1Text;
		Text stat2Text;
		Text stat3Text;
		Text stat4Text;

		Picture bg = new Picture(0, 0, "shopMenuBG", "Image Files/bluegreengrad.png");

		Picture c1 = new Picture(35, 185, "Car1", "Image Files/redcar.png");
		Picture c2 = new Picture(535, 185, "Car2", "Image Files/bluecar.png");
		Picture c3 = new Picture(35, 385, "Car3", "Image Files/whitecar.png");
		Picture c4 = new Picture(535, 385, "Car4", "Image Files/blackcar.png");

		Text title = new Text(275, 100, 50, "Shop", "smTitle");
		Text money = new Text(425, 100, 30, "Available Funds: " + game.getMoney(), "shmTitle");

		Square s1 = new Square(50, 200, 100, 300, "s1", Color.GREEN);
		Square sq1 = new Square(60, 210, 80, 80, "sq1", Color.GRAY);

		Square s2 = new Square(550, 200, 100, 300, "s2", Color.GREEN);
		Square sq2 = new Square(560, 210, 80, 80, "sq2", Color.GRAY);

		Square s3 = new Square(50, 400, 100, 300, "s3", Color.GREEN);
		Square sq3 = new Square(60, 410, 80, 80, "sq3", Color.GRAY);

		Square s4 = new Square(550, 400, 100, 300, "s4", Color.GREEN);
		Square sq4 = new Square(560, 410, 80, 80, "sq4", Color.GRAY);

		if (game.isUnlocked(0)) {
			r1 = new Button(350, 200, 100, 100, Color.RED, "sh1Start");
			stat1Text = new Text(365, 260, 25, "Equip", "stat1Text");
		} else {
			r1 = new Button(350, 200, 100, 100, Color.GREEN, "sh1Start");
			stat1Text = new Text(377, 260, 25, "Buy", "stat1Text");
		}
		if (game.isUnlocked(1)) {
			r2 = new Button(850, 200, 100, 100, Color.RED, "sh2Start");
			stat2Text = new Text(865, 260, 25, "Equip", "stat2Text");
		} else {
			r2 = new Button(850, 200, 100, 100, Color.BLUE, "sh2Start");
			stat2Text = new Text(877, 260, 25, "Buy", "stat2Text");
		}
		if (game.isUnlocked(2)) {
			r3 = new Button(350, 400, 100, 100, Color.RED, "sh3Start");
			stat3Text = new Text(365, 460, 25, "Equip", "stat3Text");
		} else {
			r3 = new Button(350, 400, 100, 100, Color.BLUE, "sh3Start");
			stat3Text = new Text(377, 460, 25, "Buy", "stat3Text");
		}
		if (game.isUnlocked(3)) {
			r4 = new Button(850, 400, 100, 100, Color.RED, "sh4Start");
			stat4Text = new Text(865, 460, 25, "Equip", "stat4Text");
		} else {
			r4 = new Button(850, 400, 100, 100, Color.BLUE, "sh4Start");
			stat4Text = new Text(877, 460, 25, "Buy", "stat4Text");
		}

		Text r1Text = new Text(160, 235, 20, "Your Mom's Civic", "sh1Text");
		Text r2Text = new Text(660, 235, 20, "Probably A Ford", "sh2Text");
		Text r3Text = new Text(160, 435, 20, "Tesla Model S", "sh3Text");
		Text r4Text = new Text(660, 435, 20, "Lamborghini + N2O", "sh4Text");

		Text re1Text = new Text(160, 265, 15, "Top Speed: 100 km/h", "she1Text");
		Text re2Text = new Text(660, 265, 15, "Top Speed: 120 km/h", "she2Text");
		Text re3Text = new Text(160, 465, 15, "Top Speed: 150 km/h", "she3Text");
		Text re4Text = new Text(660, 465, 15, "Top Speed: 200 km/h", "she4Text");

		Text pe1Text = new Text(160, 285, 15, "Price: Free", "pe1Text");
		Text pe2Text = new Text(660, 285, 15, "Price: $10,000", "pe2Text");
		Text pe3Text = new Text(160, 485, 15, "Price: $33,000", "pe3Text");
		Text pe4Text = new Text(660, 485, 15, "Price: $100,000", "pe4Text");

		screen.add(bg);

		screen.add(c1);
		screen.add(c2);
		screen.add(c3);
		screen.add(c4);

		screen.add(title);
		screen.add(money);

		screen.add(s1);
		screen.add(s2);
		screen.add(s3);
		screen.add(s4);

		screen.add(sq1);
		screen.add(sq2);
		screen.add(sq3);
		screen.add(sq4);

		screen.add(r1Text);
		screen.add(r2Text);
		screen.add(r3Text);
		screen.add(r4Text);

		screen.add(re1Text);
		screen.add(re2Text);
		screen.add(re3Text);
		screen.add(re4Text);

		screen.add(pe1Text);
		screen.add(pe2Text);
		screen.add(pe3Text);
		screen.add(pe4Text);

		screen.add(c1);
		screen.add(c2);
		screen.add(c3);
		screen.add(c4);

		screen.add(r1);
		screen.add(r2);
		screen.add(r3);
		screen.add(r4);

		screen.add(stat1Text);
		screen.add(stat2Text);
		screen.add(stat3Text);
		screen.add(stat4Text);

		Button back = new Button(40, 580, 50, 115, Color.RED, "shopBack");
		Text backText = new Text(65, 615, 30, "Back", "shopBackButtonText");

		screen.add(back);
		screen.add(backText);
	}// showRaceMenu

	/**
	 * Hides the shop menu from the screen
	 * 
	 * @param screen The screen with the shop menu open
	 */
	public void hideShopMenu(Screen screen) {
		ScreenElement dummy = new ScreenElement(0, 0, "dummy");
		screen.replace(dummy, screen.getIndex("shopMenuBG"));
		screen.repaint();
		screen.replace(dummy, screen.getIndex("Car1"));
		screen.replace(dummy, screen.getIndex("Car2"));
		screen.replace(dummy, screen.getIndex("Car3"));
		screen.replace(dummy, screen.getIndex("Car4"));
		screen.replace(dummy, screen.getIndex("smTitle"));
		screen.replace(dummy, screen.getIndex("shmTitle"));
		screen.replace(dummy, screen.getIndex("s1"));
		screen.replace(dummy, screen.getIndex("sq1"));
		screen.replace(dummy, screen.getIndex("s2"));
		screen.replace(dummy, screen.getIndex("sq2"));
		screen.replace(dummy, screen.getIndex("s3"));
		screen.replace(dummy, screen.getIndex("sq3"));
		screen.replace(dummy, screen.getIndex("s4"));
		screen.replace(dummy, screen.getIndex("sq4"));
		screen.replace(dummy, screen.getIndex("sh1Start"));
		screen.replace(dummy, screen.getIndex("stat1Text"));
		screen.replace(dummy, screen.getIndex("sh2Start"));
		screen.replace(dummy, screen.getIndex("stat2Text"));
		screen.replace(dummy, screen.getIndex("sh3Start"));
		screen.replace(dummy, screen.getIndex("stat3Text"));
		screen.replace(dummy, screen.getIndex("sh4Start"));
		screen.replace(dummy, screen.getIndex("stat4Text"));
		screen.replace(dummy, screen.getIndex("sh1Text"));
		screen.replace(dummy, screen.getIndex("sh2Text"));
		screen.replace(dummy, screen.getIndex("sh3Text"));
		screen.replace(dummy, screen.getIndex("sh4Text"));
		screen.replace(dummy, screen.getIndex("she1Text"));
		screen.replace(dummy, screen.getIndex("she2Text"));
		screen.replace(dummy, screen.getIndex("she3Text"));
		screen.replace(dummy, screen.getIndex("she4Text"));

		screen.replace(dummy, screen.getIndex("pe1Text"));
		screen.replace(dummy, screen.getIndex("pe2Text"));
		screen.replace(dummy, screen.getIndex("pe3Text"));
		screen.replace(dummy, screen.getIndex("pe4Text"));

		screen.replace(dummy, screen.getIndex("shopBack"));
		screen.replace(dummy, screen.getIndex("shopBackButtonText"));
		screen.repaint();
		screen.replace(dummy, screen.getIndex("Car1"));
		screen.replace(dummy, screen.getIndex("Car2"));
		screen.replace(dummy, screen.getIndex("Car3"));
		screen.replace(dummy, screen.getIndex("Car4"));
	}// hideShopMenu
}// Interface
