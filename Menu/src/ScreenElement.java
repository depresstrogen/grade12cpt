import java.io.Serializable;

/**
 * The superclass of every ScreenElement
 * 
 * @version January 14 2021
 * @author Riley Power
 *
 */
public class ScreenElement implements Serializable {
	private static final long serialVersionUID = 4;
	private int x;
	private int y;
	private String id;

	/**
	 * ScreenElement Constructor
	 * 
	 * @param x  The x coordinate of the item on screen (Top Left)
	 * @param y  The y coordinate of the item on screen (Top Left)
	 * @param id The id of the picture used to identify it from an ArrayList
	 */
	public ScreenElement(int x, int y, String id) {
		this.x = x;
		this.y = y;
		this.id = id;
	}// ScreenElement

	/**
	 * 
	 * @return The x coordinate of the element
	 */
	public int getX() {
		return x;
	}// getX

	/**
	 * 
	 * @return The y coordinate of the element
	 */
	public int getY() {
		return y;
	}// getY

	/**
	 * 
	 * @return The id of the element
	 */
	public String getID() {
		return id;
	}// getID

	/**
	 * 
	 * @param x The value to set the element's x to
	 */

	public void setX(int x) {
		this.x = x;
	}// setX

	/**
	 * 
	 * @param y The value to set the element's y to
	 */
	public void setY(int y) {
		this.y = y;
	}// setY

	/**
	 * 
	 * @param id The value to set the element's id to
	 */
	public void setID(String id) {
		this.id = id;
	}// setID
}// ScreenElement
