/**
 * The class for every Picture to be displayed on screen Think of it like Image
 * except java.awt.Image exists so it has to be named something else to avoid
 * confusion
 * 
 * @version January 14, 2021
 * @author Riley Power
 *
 */
public class Picture extends ScreenElement {
	private static final long serialVersionUID = 4;
	private String imageDir;

	/**
	 * 
	 * @param x         The x coordinate of the item on screen (Top Left)
	 * @param y         The y coordinate of the item on screen (Top Left)
	 * @param id        The id of the picture used to identify it from an ArrayList
	 * @param directory The directory of the image to be displayed
	 */
	public Picture(int x, int y, String id, String directory) {
		super(x, y, id);
		imageDir = directory;
	}//getImage

	/**
	 * @return Returns the directory of the image in the object
	 */
	public String getImage() {
		return imageDir;
	}//getImage
}//Picture
