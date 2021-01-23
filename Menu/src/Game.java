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
 * @author Riley Power
 */
public class Game {
	// Speed multiplier of car
	private int carSpeed = 10;
	// Global version of car object, not necessary but its easier to use this as a
	// unrotated version of the image
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
	public Car car;

	/**
	 * The main game loop, starts the game, loads all files and then deals with
	 * anything to do actually playing the game
	 * 
	 * @param screen The screen to paint everything to
	 */
	synchronized void start(Screen screen) {
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
		// Removes the menu elements
		screen.clearScreen();
		// Declares Objects
		car = new Car((screen.getWidth() / 2) - (carPic.getWidth() / 2),
				(screen.getHeight() / 2) - (carPic.getHeight() / 2), 5500, 9420, "Player", carPic);
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

			// Applies new background and rotated car to screen
			screen.replace(bkg, screen.getIndex("background"));
			screen.replace(car, screen.getIndex("Player"));
		}
	}// start

	/**
	 * Rotates the car image
	 * 
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
	 */
	private void keyboardInputs(boolean[] keyboard, Screen screen, Car car) {
		boolean left = keyboard['A'];
		boolean right = keyboard['D'];
		boolean up = keyboard['W'];
		boolean down = keyboard['S'];
		boolean restart = keyboard['R'];
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
		} else if (!music && musicOn) {
			jukebox.hideInterface(screen);
			musicOn = false;
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

		int shiftX = 420;
		// 260
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

		// use
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
		// if a previous race is still in
		int j = 0;
		for (int i = 0; i < checkpoints.size(); i++) {
			screen.add(checkpoints.get(i));
		}
		int squX = (int) (checkpoints.get(0).getX() / 50) + 750;
		int squY = (int) (checkpoints.get(0).getY() / 50) + 50;
		Square square = new Square(squX, squY, 4, 4, "chkpoint", Color.YELLOW);
		screen.add(square);
		currentCheckpoint = 0;
	}// startRace

	/**
	 * Moves the checkpoint boxes to be in line with the screen, and checks for
	 * collision with the upcoming checkpoint
	 * 
	 * @param screen The screen to draw the checkpoints on
	 */
	private void updateCheckpoints(Screen screen, Car car) {
		int imageWidth = car.getImage().getWidth();
		int imageHeight = car.getImage().getHeight();
		double centerX = car.getPlayerX() + 420 + (imageWidth / 2);
		double centerY = car.getPlayerY() + 260 + (imageHeight / 2);

		int whiteSpace = 38;
		// Y Axis, top and bottom of car
		double playerUp = centerY - (imageHeight / 2) + whiteSpace;
		double playerDown = centerY + (imageHeight / 2) - whiteSpace;
		// X Axis, sides of car
		double playerLeft = centerX - (imageWidth / 2) + whiteSpace;
		double playerRight = centerX + (imageWidth / 2) - whiteSpace;

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
					Checkpoint dummy = new Checkpoint(0, 0, 0, 0, "blankcp", "blank");
					try {
						screen.replace(dummy, screen.getIndex(cpt.getID()));
					} catch (Exception e) {

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
				}
			}
			// Adjust so it follows the same draw routine as the background
			cpt.setX((int) (0 - car.getPlayerX() + cpt.getX()));
			cpt.setY((int) (0 - car.getPlayerY() + cpt.getY()));

			screen.replace(cpt, screen.getIndex(cpt.getID()));

		}
		screen.repaint();
	}// updateCheckpoints

}// Game