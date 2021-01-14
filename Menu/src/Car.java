import java.awt.image.BufferedImage;;

/**
 * The object for any car on the screen, including the player
 * 
 * @version January 14, 2021
 * @author Riley Power
 */
public class Car extends ScreenElement {
	BufferedImage image;

	/**
	 * 
	 * @param x     The x coordinate of the item on screen (Top Left)
	 * @param y     The y coordinate of the item on screen (Top Left)
	 * @param id    The id of the picture used to identify it from an ArrayList
	 * @param image The image of the car, make sure it has appropriate blank space
	 *              around it so it can be rotated without clipping
	 */
	public Car(int x, int y, String id, BufferedImage image) {
		super(x, y, id);
		this.image = image;
	}

	/**
	 * 
	 * @param image The image to be displayed to represent the car
	 */
	public void setImage(BufferedImage image) {
		this.image = image;
	}

	/**
	 * @return Returns the image to represent the car on screen
	 */
	public BufferedImage getImage() {
		return image;
	}
}
