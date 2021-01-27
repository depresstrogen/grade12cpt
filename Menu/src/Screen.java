import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * Everything to do with drawing the screen, getting mouse movement, and
 * anything to do with the main window is performed in this class
 * 
 * @version January 24, 2021
 * @author Riley Power
 *
 */
public class Screen extends JPanel implements ActionListener, MouseListener {
	private JFrame frame;
	private ArrayList<ScreenElement> elements = new ArrayList<ScreenElement>();
	private boolean[] keyboard = new boolean[255];
	private MouseHandler mouse = new MouseHandler();
	// The id of the ScreenElement that was last clicked
	private String lastClick = "";
	// A copy of the ScreenElement that was last clicked
	private Object lastClickObject;
	// Used so that unless a new click is made the clicking methods are not executed
	private boolean newClick = false;
	// Starts the game but doesn't display it until the start method is called
	private Game game = new Game();
	private Color backgroundColor = Color.WHITE;
	private int lastMouseX = 0;
	private int lastMouseY = 0;

	/**
	 * Constructs the jFrame, mouse listener and key listener
	 * 
	 * @param height How many pixels tall the window will be
	 * @param width  How many pixels wide the window will be
	 */
	public Screen(int height, int width) {
		Image icon = Toolkit.getDefaultToolkit().getImage("Image Files/icon.png");
		// Start JFrame
		frame = new JFrame("Grade 12 CPT | By Riley Power");
		frame.add(this);
		frame.setSize(height, width);
		frame.setIconImage(icon);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Keyboard Inputs
		// Would be in another method but it leeches off JFrame lol
		frame.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {
			}

			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_A) {
					keyboard['A'] = true;
				}
				if (e.getKeyCode() == KeyEvent.VK_D) {
					keyboard['D'] = true;
				}
				if (e.getKeyCode() == KeyEvent.VK_W) {
					keyboard['W'] = true;
				}
				if (e.getKeyCode() == KeyEvent.VK_S) {
					keyboard['S'] = true;
				}
				if (e.getKeyCode() == KeyEvent.VK_R) {
					keyboard['R'] = true;
				}
			}

			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_A) {
					keyboard['A'] = false;
				}
				if (e.getKeyCode() == KeyEvent.VK_D) {
					keyboard['D'] = false;
				}
				if (e.getKeyCode() == KeyEvent.VK_W) {
					keyboard['W'] = false;
				}
				if (e.getKeyCode() == KeyEvent.VK_S) {
					keyboard['S'] = false;
				}
				if (e.getKeyCode() == KeyEvent.VK_R) {
					keyboard['R'] = false;
				}
				if (e.getKeyCode() == KeyEvent.VK_T) {
					keyboard['T'] = !keyboard['T'];
				}
			}
		});
		addMouseListener(this);
	}// Screen

	/**
	 * @param g The canvas to paint every object to
	 */
	public void paint(Graphics g) {
		// Main Paint Loop
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setBackground(backgroundColor);
		// Loops through each item in the ArrayList and paints it depending which object
		try {
			int minimapCarX = 1000;
			int minimapCarY = 1000;
			for (int i = 0; i < elements.size(); i++) {
				if (elements.get(i) instanceof Button) {
					Button button = (Button) elements.get(i);
					g2d.setColor(button.getColor());
					g2d.fillRect(button.getX(), button.getY(), button.getHeight(), button.getWidth());
				}
				if (elements.get(i) instanceof Text) {
					Text text = (Text) elements.get(i);
					g2d.setColor(text.getColor());
					g2d.setFont(new Font(text.getFont(), Font.PLAIN, text.getFontSize()));
					g2d.drawString(text.getText(), text.getX(), text.getY());
				}

				if (elements.get(i) instanceof Picture) {
					Picture picture = (Picture) elements.get(i);
					Image image = Toolkit.getDefaultToolkit().getImage(picture.getImage());
					g2d.drawImage(image, picture.getX(), picture.getY(), null);
					if (picture.getID().equals("miniMap")) {
						g2d.setColor(Color.RED);
						g2d.fillRect(minimapCarX, minimapCarY, 4, 4);
					}
				}

				if (elements.get(i) instanceof Car) {
					Car car = (Car) elements.get(i);
					g2d.drawImage(car.getImage(), car.getX(), car.getY(), null);

					minimapCarX = (int) ((car.getPlayerX() + 420) / 50) + 750;
					minimapCarY = (int) ((car.getPlayerY() + 260) / 50) + 50;

				}
				if (elements.get(i) instanceof Background) {
					Background bkg = (Background) elements.get(i);
					g2d.drawImage(bkg.getImage(), bkg.getX(), bkg.getY(),
							bkg.getImage().getHeight(null) * bkg.getScaleFactor(),
							bkg.getImage().getWidth(null) * bkg.getScaleFactor(), null);

				}
				if (elements.get(i) instanceof Checkpoint) {
					Checkpoint cpt = (Checkpoint) elements.get(i);
					switch (cpt.getType()) {
					case "Start":
						Color green = new Color(0, 255, 64, 128);
						g2d.setColor(green);
						break;
					case "CP":
						Color yellow = new Color(252, 207, 0, 128);
						g2d.setColor(yellow);
						break;
					case "Finish":
						Color red = new Color(255, 0, 0, 128);
						g2d.setColor(red);
						break;
					}
					g2d.fillRect(cpt.getX(), cpt.getY(), cpt.getHeight(), cpt.getWidth());

				}
				if (elements.get(i) instanceof Square) {
					Square square = (Square) elements.get(i);
					g2d.setColor(square.getColor());
					g2d.fillRect(square.getX(), square.getY(), square.getHeight(), square.getWidth());
				}
			}
			// Recursion so it paints until the program is stopped (repaint just calls this
			// again)
			repaint();
		} catch (Exception e) {

		}
	}// paint

	/**
	 * Adds the ScreenElement to the elements ArrayList
	 * 
	 * @param se Adds the ScreenElement to the elements ArrayList
	 */
	public void add(ScreenElement se) {
		elements.add(se);
		repaint();
	}// add

	/**
	 * Replaces the ScreenElement in the elements ArrayList of the index provided
	 * with the ScreenElement provided
	 * 
	 * @param se    The ScreenElement to be inserted
	 * @param index The index to replace with the new ScreenElement
	 * @see whatClicked
	 */
	public void replace(ScreenElement se, int index) {
		elements.set(index, se);
		repaint();
	}// replace

	/**
	 * Gets the x and y of the mouse then calls whatClicked
	 * 
	 * @param e The event which triggers this method
	 * 
	 */
	public void mouseReleased(MouseEvent e) {
		int mouseX = e.getX();
		int mouseY = e.getY();
		whatClicked(mouseX, mouseY);
	}// mouseReleased

	/**
	 * Loops through every element in the array and if the mouseX and mouseY are
	 * inside the ScreenElement it registers a click and sends it to the
	 * MouseHandler
	 * 
	 * @param mouseX the x coordinate you would like to check for a clickable object
	 * @param mouseY the y coordinate you would like to check for a clickable object
	 * @see MouseHandler
	 */
	private void whatClicked(int mouseX, int mouseY) {
		System.out.println(mouseX + " " + mouseY);
		lastMouseX = mouseX;
		lastMouseY = mouseY;
		for (int i = 0; i < elements.size(); i++) {
			if (elements.get(i) instanceof Button) {
				Button button = (Button) elements.get(i);
				if (mouseX > button.getX() && mouseX < button.getX() + button.getHeight() && mouseY > button.getY()
						&& mouseY < button.getY() + button.getWidth()) {
					newClick = true;
					lastClick = button.getID();
					lastClickObject = button;
					mouse.mouseInputs(this, game);
				}
			}
		}
		if (getIndex("miniMap") != -1) {
			System.out.println("miniMap");
			ScreenElement map = elements.get(getIndex("miniMap"));
			if (map.getX() <= mouseX && map.getY() <= mouseY && map.getX() + 200 >= mouseX
					&& map.getY() + 200 >= mouseY) {
				int mapX = mouseX - map.getX();
				int mapY = mouseY - map.getY();
				System.out.println("miniMap" + mapX + "." + +mapY);
			}

		}
	}// whatClicked

	/**
	 * Gets the id of the last clicked ScreenElement, and sets newClick to false
	 * 
	 * @return The id of the last clicked ScreenElement
	 */
	public String getLastClick() {
		newClick = false;
		return lastClick;
	}// getLastClick

	/**
	 * 
	 * @return A copy of the object which was last clicked
	 */
	public Object getLastClickObject() {
		return lastClickObject;
	}// getLastClickObject

	/**
	 * 
	 * @return if the most recent click was "real" or not
	 */
	public boolean isNewClick() {
		return newClick;
	}// isNewClick

	/**
	 * Gets the index in the ArrayList elements of whatever id is given
	 * 
	 * @param id The id to search for in the ArrayList elements
	 * @return the index of the id in the ArrayList elements
	 */
	public int getIndex(String id) {
		int index = -1;
		for (int i = 0; i < elements.size(); i++) {
			ScreenElement element = elements.get(i);
			if (element.getID().equals(id)) {
				index = i;
				i = elements.size();
			}
		}
		return index;
	}// getIndex

	/**
	 * @param id The id to get the ScreenElement of
	 * @return a copy of the ScreenElement at that id
	 */
	public ScreenElement getScreenElement(String id) {
		int index = getIndex(id);
		return elements.get(index);
	}// getScreenElement

	/**
	 * Saves the ArrayList elements to the given directory
	 * 
	 * @param file The directory to save the elements to
	 */
	public void saveElements(String file) {
		ScreenFile io = new ScreenFile();
		io.writeArrayList(elements, file);
	}// saveElements

	/**
	 * Reads the ArrayList elements to from given directory
	 * 
	 * @param file The directory to read the elements from
	 */
	public void loadElements(String file) {
		ScreenFile io = new ScreenFile();
		elements = io.readArrayList(file);
	}// loadElements

	/**
	 * Removes every item from the ArrayList elements, thus making the screen black
	 */
	public void clearScreen() {
		elements.clear();
	}// clearScreen

	/**
	 * Sets the background color of the window
	 * 
	 * @param color The color to set the background to
	 */
	public void setBackgroundColor(Color color) {
		backgroundColor = color;
		repaint();
	}// setBackgroundColor

	/**
	 * Accessor Method for keyboard[][]
	 * 
	 * @return An array of each possible letter, and if it is currently pressed or
	 *         not
	 */
	public boolean[] getKeyboard() {
		return keyboard;
	}// getKeyboard

	/**
	 * Accessor Method for lasyMouseX
	 * 
	 * @return the mouseX of the last click
	 */
	public int getLastMouseX() {
		return lastMouseX;
	}// getLastMouseX

	/**
	 * Accessor Method for lasyMouseY
	 * 
	 * @return the mouseY of the last click
	 */
	public int getLastMouseY() {
		return lastMouseY;
	}// getLastMouseY

	// The following methods are just here to appease MouseListener and
	// ActionListener, they are not used and i dont even know what actionPerformed
	// does lol

	public void mouseEntered(MouseEvent e) {
	}// mouseEntered

	public void mouseExited(MouseEvent e) {
	}// mouseExited

	public void mouseClicked(MouseEvent e) {
	}// mouseClicked

	public void mousePressed(MouseEvent e) {
	}// mousePressed

	public void actionPerformed(ActionEvent a) {
	}// actionPerformed
}// Screen