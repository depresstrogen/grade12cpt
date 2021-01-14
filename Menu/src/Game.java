import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

/**
 * This class contains everything that is needed for the actual game and its
 * world such as
 * 	- Keyboard Input
 *  - Player Movement
 *  - Background Movement
 *  - Modifying Player Images
 * 
 * @version January 14, 2021
 * @author Riley Power
 */
public class Game {
	// Coordinates of player
	private double playerX = 1500;
	private double playerY = 1000;
	// Total player angle
	private double playerAngle = 0;
	// X and Y angle of player separated
	private double playerDX;
	private double playerDY;
	// Speed multiplier of car
	private int carSpeed = 15;
	// Global version of car object, not necessary but its easier to use this as a
	// unrotated version of the image
	private Car car;
	BufferedImage carPic;

	synchronized void start(Screen screen) {
		try {
			carPic = ImageIO.read(new File("testcar.png"));
		} catch (Exception e) {

		}
		car = new Car(2, (screen.getHeight() / 2) - (carPic.getHeight() / 2), "Player", carPic);
		// Frame rate of game
		int fps = 60;
		long fpsTime = System.currentTimeMillis();
		// Removes the menu elements
		screen.clearScreen();
		// Paints background and car
		Picture background = new Picture((int) (0 - playerX), (int) (0 - playerY), "background", "testbkg.jpg");
		Car car = new Car((screen.getWidth() / 2) - (carPic.getWidth() / 2),
				(screen.getHeight() / 2) - (carPic.getHeight() / 2), "Player", carPic);
		screen.add(background);
		screen.add(car);
		// Commits the changes to the screen
		screen.repaint();
		while (true) {
			fpsTime = System.currentTimeMillis() + (1000 / fps);
			while (System.currentTimeMillis() < fpsTime) {
				// To do as fast as possible if anything
			}
			keyboardInputs(screen.getKeyboard());
			Picture bkg = (Picture) screen.getScreenElement("background");
			// Shifts background instead of player
			bkg.setX((int) (0 - playerX));
			bkg.setY((int) (0 - playerY));
			// Rotates car
			car.setImage(rotateImage());
			// Applies new background and rotated car to screen
			screen.replace(bkg, screen.getIndex("background"));
			screen.replace(car, screen.getIndex("Player"));
		}
	}

	public BufferedImage rotateImage() {
		BufferedImage img = car.getImage();
		// Applies Rotation to image, pivoting at the center of the image
		AffineTransform rotate = AffineTransform.getRotateInstance(playerAngle, img.getWidth() / 2, img.getWidth() / 2);
		// Makes Filter
		AffineTransformOp filter = new AffineTransformOp(rotate, AffineTransformOp.TYPE_BILINEAR);
		// Applies Filtering to the image so the pixels don't look jumpy
		BufferedImage rotatedImage = filter.filter(img, null);
		return rotatedImage;
	}

	public void keyboardInputs(boolean[] keyboard) {
		boolean left = keyboard['A'];
		boolean right = keyboard['D'];
		boolean up = keyboard['W'];
		boolean down = keyboard['S'];

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

	}
}
