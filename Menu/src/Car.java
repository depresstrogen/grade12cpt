import java.awt.image.BufferedImage;;

/**
 * The object for any car on the screen, including the player and AI
 * 
 * @version January 24, 2021
 * @author Emma Power
 */
public class Car extends ScreenElement {
	private BufferedImage image;
	private final BufferedImage unrotatedImage;
	private double playerX;
	private double playerY;
	private double playerAngle;
	private int topSpeed;

	/**
	 * @param x        The x coordinate of the item on screen (Top Left)
	 * @param y        The y coordinate of the item on screen (Top Left)
	 * @param playerX  the x coordinate the player is on the map
	 * @param playerX  the y coordinate the player is on the map
	 * @param id       The id of the picture used to identify it from an ArrayList
	 * @param image    The image of the car, make sure it has appropriate blank
	 *                 space around it so it can be rotated without clipping
	 * @param topSpeed The cars speed in pixels per frame
	 */
	public Car(int x, int y, double playerX, double playerY, String id, BufferedImage image, int topSpeed) {
		super(x, y, id);
		this.image = image;
		unrotatedImage = image;
		this.topSpeed = topSpeed;
	}// Car

	/**
	 * Mutator Method for image
	 * 
	 * @param image The image to be displayed to represent the car
	 */
	public void setImage(BufferedImage image) {
		this.image = image;
	}// setImage

	/**
	 * Mutator Method for playerX
	 * 
	 * @param playerX sets the x coordinate of the player on the map
	 */
	public void setPlayerX(double playerX) {
		this.playerX = playerX;
	}// setPlayerX

	/**
	 * Mutator Method for playerY
	 * 
	 * @param playerY sets the y coordinate of the player on the map
	 */
	public void setPlayerY(double playerY) {
		this.playerY = playerY;
	}// setPlayerY

	/**
	 * Mutator Method for playerAngle
	 * 
	 * @param playerAngle Sets the current angle of the player (in radians)
	 */
	public void setPlayerAngle(double playerAngle) {
		this.playerAngle = playerAngle;
	}// setPlayerAngle

	/**
	 * Accessor Method for image
	 * 
	 * @return Returns the image to represent the car on screen
	 */
	public BufferedImage getImage() {
		return image;
	}// getImage

	/**
	 * Accessor Method for unrotatedImage
	 * 
	 * @return an unaltered version of the image to represent the car
	 */
	public BufferedImage getUnrotatedImage() {
		return unrotatedImage;
	}// getUnrotatedImage

	/**
	 * Accessor Method for playerX
	 * 
	 * @return the player's x coordinate on the map
	 */
	public double getPlayerX() {
		return playerX;
	}// getPlayerX

	/**
	 * Accessor Method for playerY
	 * 
	 * @return the player's y coordinate on the map
	 */
	public double getPlayerY() {
		return playerY;
	}// getPlayerY

	/**
	 * Accessor Method for playerAngle
	 * 
	 * @return the player's current angle
	 */
	public double getPlayerAngle() {
		return playerAngle;
	}// getPlayerAngle

	/**
	 * Accessor Method for topSpeed
	 * 
	 * @return the player's top speed
	 */
	public int getSpeed() {
		return topSpeed;
	}// getSpeed

}// Car
