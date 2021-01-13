import java.io.Serializable;

public class ScreenElement implements Serializable{
	private static final long serialVersionUID = 4; 
	private int x;
	private int y;
	private String id;
	
	public ScreenElement(int x, int y, String id) {
		this.x = x;
		this.y = y;
		this.id = id;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public String getID() {
		return id;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public void setID(String id) {
		this.id = id;
	}
}
