import java.awt.Color;
public class Main {

	static Screen screen;
	public static void main(String[] args) {
		int fps = 60;
		long fpsTime = System.currentTimeMillis();
		
		screen = new Screen(1000, 700);

		screen.loadElements("main.menu");
		
		MouseHandler mouse = new MouseHandler();
		while (true) {
			fpsTime = System.currentTimeMillis() + (1000 / fps);
			while (System.currentTimeMillis() < fpsTime) {
				// To do as fast as possible
			}
			// To do every frame
		}
	}// Main
}