import java.awt.Color;

/**
 * The class for every Button to be displayed on screen
 * 
 * @version January 23, 2021
 * @author Riley Power
 *
 */
public class Square extends ScreenElement {
	private int width;
	private int height;
	private Color color;

	/**
	 * 
	 * @param x  The x coordinate of the button on screen (Top Left)
	 * @param y  The y coordinate of the button on screen (Top Left)
	 * @param width The width of the button
	 * @param height The height of the button
	 */
	public Square(int x, int y, int width, int height, String id, Color color) {
		super(x, y, id);
		this.width = width;
		this.height = height;
		this.color = color;
	}//Button

	/**
	 * Returns the desired width of the button
	 * 
	 * @return the width of the button
	 */
	public int getWidth() {
		return width;
	}// getWidth

	/**
	 * Returns the desired height of the button
	 * 
	 * @return the height of the button
	 */
	public int getHeight() {
		return height;
	}// getHeight

	/**
	 * Returns the desired color of the button
	 * 
	 * @return the color of the button
	 */
	public Color getColor() {
		return color;
	}// getColor

	/**
	 * Sets the color of the button to the color specified
	 * 
	 * @param color The color to make the button
	 */
	public void setColor(Color color) {
		this.color = color;
	}// setColor
}

