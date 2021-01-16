import java.awt.image.BufferedImage;

/**
 * The object for the background or possible foreground
 * 
 * @version January 16, 2021
 * @author Riley Power
 */
public class Background extends ScreenElement {
	BufferedImage image;

	/**
	 * 
	 * @param x     The x coordinate of the background (Top Left)
	 * @param y     The y coordinate of the background (Top Left)
	 * @param id    The id of the background used to identify it from an ArrayList
	 * @param image The image of the background
	 */
	public Background(int x, int y, String id, BufferedImage image) {
		super(x, y, id);
		this.image = image;
	}//Background

	/**
	 * 
	 * @param image The image to be displayed to represent the car
	 */
	public void setImage(BufferedImage image) {
		this.image = image;
	}//setImage

	/**
	 * @return Returns the image to represent the car on screen
	 */
	public BufferedImage getImage() {
		return image;
	}//getImage
}//Background