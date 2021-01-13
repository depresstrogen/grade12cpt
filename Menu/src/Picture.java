
public class Picture extends ScreenElement {
	private static final long serialVersionUID = 4; 
	private String imageDir;
	public Picture(int x, int y, String id, String directory) {
		super(x, y, id);
		imageDir = directory;
	}
	
	public String getImage() {
		return imageDir;
	}
}
