import java.awt.Image;

/**
 * The object for the background or possible foreground
 * 
 * @version January 16, 2021
 * @author Riley Power
 */
public class Background extends ScreenElement {
	Image image;
	int scaleFactor;
	/**
	 * 
	 * @param x     The x coordinate of the background (Top Left)
	 * @param y     The y coordinate of the background (Top Left)
	 * @param id    The id of the background used to identify it from an ArrayList
	 * @param image The image of the background
	 */
	public Background(int x, int y, String id, Image image, int scaleFactor) {
		super(x, y, id);
		this.image = image;
		this.scaleFactor = scaleFactor;
	}//Background

	/**
	 * 
	 * @param image The image to be displayed to represent the car
	 */
	public void setImage(Image image) {
		this.image = image;
	}//setImage

	/**
	 * @return Returns the image to represent the car on screen
	 */
	public Image getImage() {
		return image;
	}//getImage
	
	public int getScaleFactor() {
		return scaleFactor;
	}//getScaleFactor
}//Background