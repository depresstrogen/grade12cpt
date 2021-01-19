/**
 * This class is used to make a checkpoint object, which are used to ensure the
 * player finishes the race correctly by requiring all checkpoints be
 * intersected at a certain order
 * 
 * @version January 18, 2021
 * @author Riley Power
 *
 */
public class Checkpoint extends ScreenElement {
	private int height;
	private int width;
	private String type;

	/**
	 * 
	 * @param x      The x coordinate of the checkpoint on screen (Top Left)
	 * @param y      The y coordinate of the checkpoint on screen (Top Left)
	 * @param width  The width of the checkpoint
	 * @param height The height of the checkpoint
	 * @param id     The id of the checkpoint
	 * @param type   The type of checkpoint that this checkpoint is (i.e. Start, CP,
	 *               Finish)
	 */
	public Checkpoint(int x, int y, int width, int height, String id, String type) {
		super(x, y, id);
		this.width = width;
		this.height = height;
		this.type = type;
	}//Checkpoint

	/**
	 * Returns the desired width of the checkpoint hit box
	 * 
	 * @return the width of the button
	 */
	public int getWidth() {
		return width;
	}//getWidth

	/**
	 * Returns the desired height of the checkpoint hit box
	 * 
	 * @return the height of the button
	 */
	public int getHeight() {
		return height;
	}//getHeight

	/**
	 * Returns the type of checkpoint that this checkpoint is (i.e. Start, CP,
	 * Finish)
	 * 
	 * @return The type of checkpoint
	 */
	public String getType() {
		return type;
	}//getType
}//Checkpoint
