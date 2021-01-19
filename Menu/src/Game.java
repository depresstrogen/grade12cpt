import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;

/**
 * This class contains everything that is needed for the actual game and its
 * world such as - Keyboard Input - Player Movement - Background Movement -
 * Modifying Player Images - Checkpoint Handling
 * 
 * @version January 18, 2021
 * @author Riley Power
 */
public class Game {
	// Coordinates of player
	private double playerX = 5500;
	private double playerY = 9420;
	// Total player angle
	private double playerAngle = 0;
	// X and Y angle of player separated
	private double playerDX;
	private double playerDY;
	// Speed multiplier of car
	private int carSpeed = 10;
	// Global version of car object, not necessary but its easier to use this as a
	// unrotated version of the image
	private Car car;
	// Images used by the game
	private BufferedImage carPic;
	private BufferedImage collision;
	private Image backgroundImage;
	// The height and width of the background layer
	private int bkgWidth;
	private int bkgHeight;
	private int scaleFactor = 4;
	private int currentCheckpoint = 0;

	ArrayList<Checkpoint> checkpoints = new ArrayList<Checkpoint>();

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
			backgroundImage = ImageIO.read(new File("Map Files/map.png"));
		} catch (Exception e) {
			System.err.println(e);
		}
		// Frame rate of game
		int fps = 60;
		long fpsTime = System.currentTimeMillis();
		// Removes the menu elements
		screen.clearScreen();
		// Declares Objects
		car = new Car(2, (screen.getHeight() / 2) - (carPic.getHeight() / 2), "Player", carPic);
		Background background = new Background((int) (0 - playerX), (int) (0 - playerY), "background", backgroundImage,
				scaleFactor);
		Car car = new Car((screen.getWidth() / 2) - (carPic.getWidth() / 2),
				(screen.getHeight() / 2) - (carPic.getHeight() / 2), "Player", carPic);
		// Adds images to the screen
		screen.add(background);
		screen.add(car);
		// Declares height and width to speed things up later
		bkgWidth = background.getImage().getWidth(null);
		bkgHeight = background.getImage().getHeight(null);

		startRace(screen, false);
		updateCheckpoints(screen);

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
			keyboardInputs(screen.getKeyboard(), screen);
			checkCollision(car);
			updateCheckpoints(screen);
			// Gets the background object from the elements array instead of the local one
			Background bkg = (Background) screen.getScreenElement("background");
			// Shifts background instead of player
			bkg.setX((int) (0 - playerX));
			bkg.setY((int) (0 - playerY));
			// Rotates car
			car.setImage(rotateImage());
			// Applies new background and rotated car to screen
			screen.replace(bkg, screen.getIndex("background"));
			screen.replace(car, screen.getIndex("Player"));
		}
	}//start

	/**
	 * Rotates the car image
	 * 
	 * @return The rotated version of the image
	 */
	public BufferedImage rotateImage() {
		BufferedImage img = car.getImage();
		// Applies Rotation to image, pivoting at the center of the image
		AffineTransform rotate = AffineTransform.getRotateInstance(playerAngle, img.getWidth() / 2, img.getWidth() / 2);
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
	public void keyboardInputs(boolean[] keyboard, Screen screen) {
		boolean left = keyboard['A'];
		boolean right = keyboard['D'];
		boolean up = keyboard['W'];
		boolean down = keyboard['S'];
		boolean restart = keyboard['R'];
		// Turn left and right
		if (left) {
			playerAngle -= 0.1;
		}
		if (right) {
			playerAngle += 0.1;
		}

		// Amount to move on X with your current angle
		playerDX = Math.cos(playerAngle);
		// Amount to move on X with your current angle
		playerDY = Math.sin(playerAngle);

		// Radians are horrible you gotta make sure its not over 360 degrees
		if (playerAngle < 0) {
			playerAngle += Math.PI * 2;
		}
		if (playerAngle > Math.PI * 2) {
			playerAngle -= Math.PI * 2;
		}

		if (up) {
			// Grade 11 Functions coming in clutch
			// Angle * Multiplier = Coordinate
			playerX += playerDX * carSpeed;
			playerY += playerDY * carSpeed;
		}
		if (down) {
			// Angle * Multiplier = Coordinate
			playerX -= playerDX * carSpeed;
			playerY -= playerDY * carSpeed;
		}

		// Debug checkpoint hitboxes
		if (restart) {
			startRace(screen, true);
		}

	}//keyboardInputs

	/**
	 * Checks if the car is out of bounds, or in a pink area on the collision map
	 * and adjusts the cars position accordingly
	 * 
	 * @param car The car to check collision on
	 */
	public void checkCollision(Car car) {
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
		double centerX = playerX + shiftX + (imageWidth / 2);
		double centerY = playerY + shiftY + (imageHeight / 2);
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
		playerX = centerX - shiftX - (imageWidth / 2);
		playerY = centerY - shiftY - (imageHeight / 2);
	}// checkCollision

	/**
	 * Loads details of the race into memory, and adds the checkpoints to the screen
	 * @param screen The screen which the race will take place
	 * @param replace If it is a new race or if its just replacing the elements (useful for adjusting hit boxes on checkpoints)
	 */
	public void startRace(Screen screen, boolean replace) {
		CheckpointFile cpf = new CheckpointFile();
		ArrayList<Checkpoint> checkpoints = cpf.readCheckpoints();
		this.checkpoints = checkpoints;
		if (!replace) {
			for (int i = 0; i < checkpoints.size(); i++) {
				screen.add(checkpoints.get(i));
			}
		} else {
			for (int i = 0; i < checkpoints.size(); i++) {
				screen.replace(checkpoints.get(i), screen.getIndex(checkpoints.get(i).getID()));
			}
		}
		currentCheckpoint = 0;
	}//startRace

	/**
	 * Moves the checkpoint boxes to be in line with the screen, and checks for
	 * collision with the upcoming checkpoint
	 * 
	 * @param screen The screen to draw the checkpoints on
	 */
	public void updateCheckpoints(Screen screen) {
		int imageWidth = car.getImage().getWidth();
		int imageHeight = car.getImage().getHeight();
		double centerX = playerX + 420 + (imageWidth / 2);
		double centerY = playerY + 260 + (imageHeight / 2);

		for (int i = currentCheckpoint; i < checkpoints.size(); i++) {
			// Make a exact copy of the current checkpoint
			Checkpoint cpt = new Checkpoint(checkpoints.get(i).getX(), checkpoints.get(i).getY(),
					checkpoints.get(i).getWidth(), checkpoints.get(i).getHeight(), checkpoints.get(i).getID(),
					checkpoints.get(i).getType());
			// So you cant hit a checkpoint out of order
			if (i == currentCheckpoint) {
				// Collision Detection
				if (centerX > cpt.getX() && centerX < (cpt.getX() + cpt.getWidth()) && centerY > cpt.getY()
						&& centerY < (cpt.getY() + cpt.getHeight())) {
					currentCheckpoint++;
					// Replace with dummy value so it doesn't remove the old index
					Checkpoint dummy = new Checkpoint(0, 0, 0, 0, "blankcp", "blank");
					screen.replace(dummy, screen.getIndex(cpt.getID()));
					cpt = dummy;
					checkpoints.set(i, dummy);
				}
			}
			// Adjust so it follows the same draw routine as the background
			cpt.setX((int) (0 - playerX + cpt.getX()));
			cpt.setY((int) (0 - playerY + cpt.getY()));
			screen.replace(cpt, screen.getIndex(cpt.getID()));
		}
		screen.repaint();
	}// updateCheckpoints
}// Game