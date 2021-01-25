import java.awt.Color;

/**
 * The class for every square to be displayed on screen
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
	 * @param x      The x coordinate of the square on screen (Top Left)
	 * @param y      The y coordinate of the square on screen (Top Left)
	 * @param width  The width of the square
	 * @param height The height of the square
	 */
	public Square(int x, int y, int width, int height, String id, Color color) {
		super(x, y, id);
		this.width = width;
		this.height = height;
		this.color = color;
	}// Square

	/**
	 * Accessor Method for width
	 * 
	 * @return the width of the square
	 */
	public int getWidth() {
		return width;
	}// getWidth

	/**
	 * Accessor Method for height
	 * 
	 * @return the height of the square
	 */
	public int getHeight() {
		return height;
	}// getHeight

	/**
	 * Accessor Method for color
	 * 
	 * @return the color of the square
	 */
	public Color getColor() {
		return color;
	}// getColor

	/**
	 * Mutator Method for color
	 * 
	 * @param color The color to make the square
	 */
	public void setColor(Color color) {
		this.color = color;
	}// setColor
}// Square
