import java.awt.Color;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;

/**
 * This class contains everything that is needed for the actual game and its
 * world such as - Keyboard Input - Player Movement - Background Movement -
 * Modifying Player Images - Checkpoint Handling - AI
 * 
 * @version January 23, 2021
 * @author Emma Power
 */
public class Game {
	// Speed multiplier of car
	private int carSpeed = 10;
	// Images used by the game
	private BufferedImage carPic;
	private BufferedImage collision;
	private Image backgroundImage;
	// The height and width of the background layer
	private int bkgWidth;
	private int bkgHeight;
	// How much smaller the displayed image is than the collision map
	private int scaleFactor = 4;
	// Used for when race starts
	private int currentCheckpoint = 0;
	private ArrayList<Checkpoint> checkpoints = new ArrayList<Checkpoint>();
	// Used for the music interface
	private Interface jukebox = new Interface();
	private boolean musicOn = false;
	private boolean raceStarted = false;
	private boolean raceEnded = false;
	private double raceTimer = 0;
	private double timeToBeat = 57;
	private double money = 0;
	private int fps = 60;
	private long timerFadeOut = System.currentTimeMillis() + 5000;
	private boolean[] carsUnlocked = new boolean[4];
	// The player
	private Car car;

	/**
	 * The main game loop, starts the game, loads all files and then deals with
	 * anything to do actually playing the game
	 * 
	 * @param screen The screen to paint everything to
	 * @param file   Which save file to load from
	 */
	synchronized void start(Screen screen, int file) {
		// Load Images
		try {
			carPic = ImageIO.read(new File("Image Files/redcar.png"));
			collision = ImageIO.read(new File("Map Files/collision.png"));
			backgroundImage = ImageIO.read(new File("Map Files/gmap.png"));
		} catch (Exception e) {
			System.err.println(e);
		}
		// Frame rate of game
		int fps = 60;
		long fpsTime = System.currentTimeMillis();
		// Unlocks the first car by default
		carsUnlocked[0] = true;
		// Loads Save
		loadSaveStats(file);
		// Removes the menu elements
		screen.clearScreen();
		// Declares Objects
		car = new Car((screen.getWidth() / 2) - (carPic.getWidth() / 2),
				(screen.getHeight() / 2) - (carPic.getHeight() / 2), 5500, 9420, "Player", carPic, 10);

		Background background = new Background((int) (0 - car.getPlayerX()), (int) (0 - car.getPlayerY()), "background",
				backgroundImage, scaleFactor);
		// Adds images to the screen
		screen.add(background);
		screen.add(car);
		// Declares height and width to speed things up later
		bkgWidth = background.getImage().getWidth(null);
		bkgHeight = background.getImage().getHeight(null);

		Picture miniMap = new Picture(750, 50, "miniMap", "Map Files/minimap.png");
		screen.add(miniMap);
		// Commits the changes to the screen
		screen.repaint();
		// Main game loop
		while (true) {
			// Declare the next frame time
			fpsTime = System.currentTimeMillis() + (1000 / fps);
			while (System.currentTimeMillis() < fpsTime) {
				// To do as fast as possible if anything
				// If nothing here it just waits until the next frame is needed
			}
			// Get inputs and check collision
			keyboardInputs(screen.getKeyboard(), screen, car);
			checkCollision(car);
			// Checks if a checkpoint is hit
			updateCheckpoints(screen, car);
			// Gets the background object from the elements array instead of the local one
			Background bkg = (Background) screen.getScreenElement("background");
			// Shifts background instead of player
			bkg.setX((int) (0 - car.getPlayerX()));
			bkg.setY((int) (0 - car.getPlayerY()));
			// Rotates car
			car.setImage(rotateImage(car));
			timerControl(screen);
			// Applies new background and rotated car to screen
			screen.replace(bkg, screen.getIndex("background"));
			screen.replace(car, screen.getIndex("Player"));
		}
	}// start

	/**
	 * Rotates the car image
	 * 
	 * @param car The car to rotate
	 * @return The rotated version of the image
	 */
	private BufferedImage rotateImage(Car car) {
		BufferedImage img = car.getUnrotatedImage();
		// Applies Rotation to image, pivoting at the center of the image
		AffineTransform rotate = AffineTransform.getRotateInstance(car.getPlayerAngle(), img.getWidth() / 2,
				img.getWidth() / 2);
		// Makes Filter
		AffineTransformOp filter = new AffineTransformOp(rotate, AffineTransformOp.TYPE_BILINEAR);
		// Applies Filtering to the image so the pixels don't look jumpy
		BufferedImage rotatedImage = filter.filter(img, null);
		return rotatedImage;
	}// rotateImage

	/**
	 * Processes keyboard inputs and calculates player movement
	 * 
	 * @param keyboard An array with every possible key, and whether it is pressed
	 *                 or not
	 * @param car      The car to apply the inputs to
	 */
	private void keyboardInputs(boolean[] keyboard, Screen screen, Car car) {
		boolean left = keyboard['A'];
		boolean right = keyboard['D'];
		boolean up = keyboard['W'];
		boolean down = keyboard['S'];
		boolean music = keyboard['T'];
		double playerDX;
		double playerDY;
		// Turn left and right
		if (left) {
			car.setPlayerAngle(car.getPlayerAngle() - 0.1);
		}
		if (right) {
			car.setPlayerAngle(car.getPlayerAngle() + 0.1);
		}

		// Amount to move on X with your current angle
		playerDX = Math.cos(car.getPlayerAngle());
		// Amount to move on X with your current angle
		playerDY = Math.sin(car.getPlayerAngle());

		// Radians are horrible
		if (car.getPlayerAngle() < 0) {
			car.setPlayerAngle(car.getPlayerAngle() + (Math.PI * 2));
		}
		if (car.getPlayerAngle() > Math.PI * 2) {
			car.setPlayerAngle(car.getPlayerAngle() - (Math.PI * 2));
		}

		if (up) {
			// Grade 11 Functions coming in clutch
			// Angle * Multiplier = Coordinate
			car.setPlayerX(car.getPlayerX() + playerDX * carSpeed);
			car.setPlayerY(car.getPlayerY() + playerDY * carSpeed);
		}
		if (down) {
			// Angle * Multiplier = Coordinate
			car.setPlayerX(car.getPlayerX() - playerDX * carSpeed);
			car.setPlayerY(car.getPlayerY() - playerDY * carSpeed);
		}

		// Activates music menu
		if (music && !musicOn) {
			jukebox.startInterface(screen);
			musicOn = true;
			System.out.println((car.getPlayerX() + 420) + " " + (car.getPlayerY() + 260));
		} else if (!music && musicOn) {
			jukebox.hideInterface(screen);
			musicOn = false;
			System.out.println((car.getPlayerX() + 420) + " " + (car.getPlayerY() + 260));
		}

	}// keyboardInputs

	/**
	 * Checks if the car is out of bounds, or in a pink area on the collision map
	 * and adjusts the cars position accordingly
	 * 
	 * @param car The car to check collision on
	 */
	private void checkCollision(Car car) {
		// All walls are this exact RGB value
		int pinkValue = -65281;
		// The offset of the player's X
		int shiftX = 420;
		// The offset of the player's y
		int shiftY = 260;
		// Height / width of the car image
		int imageWidth = car.getImage().getWidth();
		int imageHeight = car.getImage().getHeight();
		// The amount of whitespace on the sides of the car image
		int whiteSpace = 38;
		// Center of image
		double centerX = car.getPlayerX() + shiftX + (imageWidth / 2);
		double centerY = car.getPlayerY() + shiftY + (imageHeight / 2);
		// Y Axis, top and bottom of car
		double playerUp = centerY - (imageHeight / 2) + whiteSpace;
		double playerDown = centerY + (imageHeight / 2) - whiteSpace;
		// X Axis, sides of car
		double playerLeft = centerX - (imageWidth / 2) + whiteSpace;
		double playerRight = centerX + (imageWidth / 2) - whiteSpace;

		// out of screen bounds
		if (playerLeft < 0) {
			playerLeft = 0;
			centerX = playerLeft + (imageWidth / 2) - whiteSpace;
		}
		if (playerRight > bkgWidth * scaleFactor) {
			playerRight = bkgWidth;
			centerX = playerRight - (imageWidth / 2) + whiteSpace;
		}
		if (playerUp < 0) {
			playerUp = 0;
			centerY = playerUp + (imageHeight / 2) - whiteSpace;
		}
		if (playerDown > bkgHeight * scaleFactor) {
			playerDown = bkgHeight;
			centerY = playerDown - (imageHeight / 2) + whiteSpace;
		}

		// Check for OOB position below car
		if (collision.getRGB((int) centerX, (int) playerDown) == pinkValue) {
			boolean collisionLoop = true;
			while (collisionLoop) {
				playerDown--;
				if (collision.getRGB((int) centerX, (int) playerDown) == pinkValue) {

				} else {
					collisionLoop = false;
				}
			}
			centerY = playerDown - (imageHeight / 2) + whiteSpace;
			playerUp = centerY - (imageHeight / 2) + whiteSpace;
		}

		// Check for OOB position above car
		if (collision.getRGB((int) centerX, (int) playerUp) == pinkValue) {
			boolean collisionLoop = true;
			while (collisionLoop) {
				playerUp++;
				if (collision.getRGB((int) centerX, (int) playerUp) == pinkValue) {

				} else {
					collisionLoop = false;
				}
			}
			centerY = playerUp + (imageHeight / 2) - whiteSpace;
			playerDown = centerY + (imageHeight / 2) - whiteSpace;
		}

		// Check for OOB position left of car
		if (collision.getRGB((int) playerLeft, (int) centerY) == pinkValue) {
			boolean collisionLoop = true;
			while (collisionLoop) {
				playerLeft++;
				if (collision.getRGB((int) playerLeft, (int) centerY) == pinkValue) {

				} else {
					collisionLoop = false;
				}
			}
			centerX = playerLeft + (imageWidth / 2) - whiteSpace;
			playerRight = centerX + (imageWidth / 2) - whiteSpace;
		}

		// Check for OOB position right of car
		if (collision.getRGB((int) playerRight, (int) centerY) == pinkValue) {
			boolean collisionLoop = true;
			while (collisionLoop) {
				playerRight--;
				if (collision.getRGB((int) playerRight, (int) centerY) == pinkValue) {

				} else {
					collisionLoop = false;
				}
			}
			centerX = playerRight - (imageWidth / 2) + whiteSpace;
			playerLeft = centerX - (imageWidth / 2) + whiteSpace;
		}

		// Apply to car
		car.setPlayerX(centerX - shiftX - (imageWidth / 2));
		car.setPlayerY(centerY - shiftY - (imageHeight / 2));
	}// checkCollision

	/**
	 * Loads details of the race into memory, and adds the checkpoints to the screen
	 * 
	 * @param screen  The screen which the race will take place
	 * @param replace If it is a new race or if its just replacing the elements
	 *                (useful for adjusting hit boxes on checkpoints)
	 */
	public void startRace(Screen screen, String file) {
		CheckpointFile cpf = new CheckpointFile();
		ArrayList<Checkpoint> checkpoints = cpf.readCheckpoints(file);
		this.checkpoints = checkpoints;
		int raceNum;
		// if a previous race is still in
		for (int i = 0; i < checkpoints.size(); i++) {
			screen.add(checkpoints.get(i));
		}
		int squX = (int) (checkpoints.get(0).getX() / 50) + 750;
		int squY = (int) (checkpoints.get(0).getY() / 50) + 50;
		// next checkpoint on mini map
		Square square = new Square(squX, squY, 4, 4, "chkpoint", Color.YELLOW);
		screen.add(square);
		currentCheckpoint = 0;
		// Gets race time to load from directory name
		raceNum = Integer.parseInt(file.substring(15, file.length() - 5));
		System.out.println(raceNum);
		System.out.println(cpf.timeToBeat(raceNum));
		timeToBeat = cpf.timeToBeat(raceNum);
	}// startRace

	/**
	 * Moves the checkpoint boxes to be in line with the screen, and checks for
	 * collision with the upcoming checkpoint
	 * 
	 * @param screen The screen to draw the checkpoints on
	 * @param car    The car which to check collision with checkpoints on
	 */
	private void updateCheckpoints(Screen screen, Car car) {
		int imageWidth = car.getImage().getWidth();
		int imageHeight = car.getImage().getHeight();
		double centerX = car.getPlayerX() + 420 + (imageWidth / 2);
		double centerY = car.getPlayerY() + 260 + (imageHeight / 2);
		// Space around the car sprite on the image
		// So the car fits in the pixel boundary no matter the rotation
		int whiteSpace = 38;
		// Y Axis, top and bottom of car
		double playerUp = centerY - (imageHeight / 2) + whiteSpace;
		double playerDown = centerY + (imageHeight / 2) - whiteSpace;
		// X Axis, sides of car
		double playerLeft = centerX - (imageWidth / 2) + whiteSpace;
		double playerRight = centerX + (imageWidth / 2) - whiteSpace;
		Checkpoint dummy = new Checkpoint(0, 0, 0, 0, "blankcp", "blank");
		for (int i = currentCheckpoint; i < checkpoints.size(); i++) {
			// Make a exact copy of the current checkpoint
			Checkpoint cpt = new Checkpoint(checkpoints.get(i).getX(), checkpoints.get(i).getY(),
					checkpoints.get(i).getWidth(), checkpoints.get(i).getHeight(), checkpoints.get(i).getID(),
					checkpoints.get(i).getType());
			// So you can't hit a checkpoint out of order
			if (i == currentCheckpoint) {
				// Collision Detection
				// All these booleans here so that they don't go into that if statement making
				// things way too confusing
				boolean centerInCheckpoint = (centerX > cpt.getX() && centerX < (cpt.getX() + cpt.getHeight())
						&& centerY > cpt.getY() && centerY < (cpt.getY() + cpt.getWidth()));

				boolean topInCheckpoint = (playerRight > cpt.getX() && playerRight < (cpt.getX() + cpt.getHeight())
						&& playerUp > cpt.getY() && playerUp < (cpt.getY() + cpt.getWidth()));

				boolean bottomInCheckpoint = (playerRight > cpt.getX() && playerRight < (cpt.getX() + cpt.getHeight())
						&& playerDown > cpt.getY() && playerDown < (cpt.getY() + cpt.getWidth()));

				boolean leftInCheckpoint = (playerLeft > cpt.getX() && playerLeft < (cpt.getX() + cpt.getHeight())
						&& playerUp > cpt.getY() && playerUp < (cpt.getY() + cpt.getWidth()));

				boolean rightInCheckpoint = (playerLeft > cpt.getX() && playerLeft < (cpt.getX() + cpt.getHeight())
						&& playerDown > cpt.getY() && playerDown < (cpt.getY() + cpt.getWidth()));

				if (centerInCheckpoint || topInCheckpoint || bottomInCheckpoint || leftInCheckpoint
						|| rightInCheckpoint) {
					currentCheckpoint++;
					// Replace with dummy value so it doesn't remove the old index
					try {
						screen.replace(dummy, screen.getIndex(cpt.getID()));
					} catch (Exception e) {
						// Non Fatal Error
					}
					cpt = dummy;
					checkpoints.set(i, dummy);
					if (i + 1 < checkpoints.size()) {
						int squX = (int) (checkpoints.get(i + 1).getX() / 50) + 750;
						int squY = (int) (checkpoints.get(i + 1).getY() / 50) + 50;
						Square square = new Square(squX, squY, 4, 4, "chkpoint", Color.YELLOW);
						if (i + 1 == checkpoints.size()) {
							square = new Square(squX, squY, 4, 4, "chkpoint", Color.ORANGE);
						}
						screen.replace(square, screen.getIndex("chkpoint"));
					}
					// Adds timer to screen and starts race
					if (i == 0) {
						Text timer = new Text(20, 20, 20, "" + raceTimer, "t");
						screen.add(timer);
						raceStarted = true;
					}
					if (i + 1 == checkpoints.size()) {
						// Ends Race
						System.out.println("End of race");
						raceEnded = true;
						// Removes yellow from mini map
						screen.replace(dummy, screen.getIndex("chkpoint"));
					}
				}
			}
			// Adjust so it follows the same draw routine as the background
			cpt.setX((int) (0 - car.getPlayerX() + cpt.getX()));
			cpt.setY((int) (0 - car.getPlayerY() + cpt.getY()));

			screen.replace(cpt, screen.getIndex(cpt.getID()));

		}
		screen.repaint();
	}// updateCheckpoints

	/**
	 * Counts up the timer each frame, awards money, keeps the timer on screen for 5
	 * seconds after with your new money total, and then removes the timer and money
	 * count from screen
	 * 
	 * @param screen The screen to draw the time on
	 */
	private void timerControl(Screen screen) {
		if (raceStarted) {
			// Counts timer up
			raceTimer += 1.0 / fps;
			Text timer = new Text(10, 30, 30, "" + ((int) ((int) (raceTimer * 100) / 100.0) / 60) + ":"
					+ (int) (((int) (raceTimer * 100)) % 6000) / 100.0, "t");
			screen.replace(timer, screen.getIndex("t"));
			timerFadeOut = System.currentTimeMillis() + 5000;
		}
		if (raceStarted && raceEnded) {
			// Awards Money
			if (raceTimer > timeToBeat) {
				money += 1000;
			} else {
				money += (int) ((timeToBeat - raceTimer) * 100000 / 1.1) / 100 + 1000;
			}
			Text moneyText = new Text(150, 30, 30, "Total Money: $" + money, "moneyText");
			screen.add(moneyText);

		}
		if (raceEnded && System.currentTimeMillis() < timerFadeOut) {
			raceStarted = false;
			raceTimer = 0;
		}
		if (raceEnded && System.currentTimeMillis() > timerFadeOut) {
			// Removes timer from screen
			raceEnded = false;
			ScreenElement dummy = new ScreenElement(0, 0, "dummy");
			screen.replace(dummy, screen.getIndex("t"));
			screen.replace(dummy, screen.getIndex("moneyText"));
		}
	}// timerControl

	/**
	 * Gets the current amount of money and which cars are unlocked
	 * 
	 * @return an Object ArrayList with the amount of money and which cars are
	 *         unlocked
	 */
	public ArrayList<Object> getSaveStats() {
		ArrayList<Object> stats = new ArrayList<Object>();
		stats.add(money);
		for (int i = 0; i < carsUnlocked.length; i++) {
			stats.add(carsUnlocked[i]);
		}
		return stats;
	}// getSaveStats

	/**
	 * Loads the player's money and which cars are unlocked from file using SaveFile
	 * 
	 * @param file The file number to load from
	 */
	private void loadSaveStats(int file) {
		String dir;
		if (file == 0) {
		} else {
			dir = "Save Files/file" + file + ".save";
			SaveFile sf = new SaveFile();
			ArrayList<Object> stats = sf.readFile(dir);
			money = (Double) stats.get(0);
			for (int i = 0; i < carsUnlocked.length; i++) {
				carsUnlocked[i] = (Boolean) stats.get(i + 1);
			}
			System.out.println(money);
		}
	}// loadSaveStats

	/**
	 * Either loads the car if it has already been purchased, or purchases the car
	 * if one does not already own it
	 * 
	 * @param carNum Which car to purchase
	 * @param screen The screen to place the new car
	 * @return if the transaction was successful
	 */
	public boolean buyCar(int carNum, Screen screen) {
		int car1Price = 10000;
		int car2Price = 33000;
		int car3Price = 100000;
		
		if (carNum == 0) {
			try {
				carPic = ImageIO.read(new File("Image Files/redcar.png"));
			} catch (Exception e) {
				System.err.println(e);
			}
			car = new Car((screen.getWidth() / 2) - (carPic.getWidth() / 2),
					(screen.getHeight() / 2) - (carPic.getHeight() / 2), 5500, 9420, "Player", carPic, 10);
			screen.replace(car, screen.getIndex("Player"));
			screen.repaint();
			carSpeed = car.getSpeed();
			return true;

		}
		
		if (carNum == 1) {
			if (money > car1Price || carsUnlocked[1]) {
				try {
					carPic = ImageIO.read(new File("Image Files/bluecar.png"));
				} catch (Exception e) {
					System.err.println(e);
				}

				car = new Car((screen.getWidth() / 2) - (carPic.getWidth() / 2),
						(screen.getHeight() / 2) - (carPic.getHeight() / 2), 5500, 9420, "Player", carPic, 12);
				screen.replace(car, screen.getIndex("Player"));
				screen.repaint();
				if (!carsUnlocked[1]) {
					money -= car1Price;
					carsUnlocked[1] = true;
				}
				carSpeed = car.getSpeed();
				return true;
			}
		}
		if (carNum == 2) {
			if (money > car2Price || carsUnlocked[2]) {
				try {
					carPic = ImageIO.read(new File("Image Files/whitecar.png"));
				} catch (Exception e) {
					System.err.println(e);
				}
				car = new Car((screen.getWidth() / 2) - (carPic.getWidth() / 2),
						(screen.getHeight() / 2) - (carPic.getHeight() / 2), 5500, 9420, "Player", carPic, 15);
				screen.replace(car, screen.getIndex("Player"));
				screen.repaint();
				if (!carsUnlocked[2]) {
					money -= car2Price;
					carsUnlocked[2] = true;
				}
				carSpeed = car.getSpeed();
				return true;
			}
		}
		if (carNum == 3) {
			if (money > car3Price || carsUnlocked[3]) {
				try {
					carPic = ImageIO.read(new File("Image Files/blackcar.png"));
				} catch (Exception e) {
					System.err.println(e);
				}
				car = new Car((screen.getWidth() / 2) - (carPic.getWidth() / 2),
						(screen.getHeight() / 2) - (carPic.getHeight() / 2), 5500, 9420, "Player", carPic, 20);
				screen.replace(car, screen.getIndex("Player"));
				screen.repaint();
				if (!carsUnlocked[3]) {
					money -= car3Price;
					carsUnlocked[3] = true;
				}
				carSpeed = car.getSpeed();
				return true;
			}
		}
		return false;
	}// buyCar

	/**
	 * Accessor Method for money
	 * 
	 * @return the player's current money
	 */
	public double getMoney() {
		return money;
	}// getMoney

	/**
	 * Returns if the specified car is unlocked or not
	 * 
	 * @param car The index of the car to check
	 * @return if the car has been unlocked or not
	 */
	public boolean isUnlocked(int car) {
		return carsUnlocked[car];
	}// isUnlocked
}// Game