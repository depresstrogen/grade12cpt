import java.awt.Color;;
public class Button extends ScreenElement {
	private int width;
	private int height;
	private Color color;
	private String id;
	
	public Button(int x, int y, int width, int height) {
		super(x, y, "Default");
		this.width = width;
		this.height = height;
		color = Color.WHITE;
	}
	public Button(int x, int y, int width, int height, Color color) {
		super(x, y, "Default");
		this.width = width;
		this.height = height;
		this.color = color;

	}
	
	public Button(int x, int y, int width, int height, Color color, String id) {
		super(x, y, id);
		this.width = width;
		this.height = height;
		this.color = color;
		this.id = id;

	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public Color getColor() {
		return color;
	}
	
	public String getID() {
		return id;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
}
