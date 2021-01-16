import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

/**
 * Everything to do with drawing the screen, getting mouse movement, and
 * anything to do with the main window is performed in this class
 * 
 * @version January 16, 2021
 * @author Riley Power
 *
 */
public class Screen extends JPanel implements ActionListener, MouseListener {
	private JFrame frame;
	private ArrayList<ScreenElement> elements = new ArrayList<ScreenElement>();
	private ArrayList<Object> objects = new ArrayList<Object>();
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
	/**
	 * Constructs the jFrame, mouse listener and key listener
	 * 
	 * @param height How many pixels tall the window will be
	 * @param width  How many pixels wide the window will be
	 */
	public Screen(int height, int width) {
		Image icon = Toolkit.getDefaultToolkit().getImage("icon.png");

		frame = new JFrame("Screen");
		frame.add(this);
		frame.setSize(height, width);
		frame.setIconImage(icon);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Keyboard Inputs
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
			}
		});
		addMouseListener(this);
	}//Screen

	/**
	 * @param g The canvas to paint every object to
	 */
	public void paint(Graphics g) {
		// Main Paint Loop
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setBackground(backgroundColor);
		// Loops through each item in the ArrayList and paints it depending which object
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
			}

			if (elements.get(i) instanceof Car) {
				Car car = (Car) elements.get(i);
				g2d.drawImage(car.getImage(), car.getX(), car.getY(), null);
			}
			if (elements.get(i) instanceof Background) {
				Background bkg = (Background) elements.get(i);
				g2d.drawImage(bkg.getImage(), bkg.getX(), bkg.getY(), null);
			}
		}
		// Recursion so it paints until the program is stopped (repaint just calls this
		// again)
		repaint();
	}//paint

	/**
	 * 
	 * @param se Adds the ScreenElement to the elements ArrayList
	 */
	public void add(ScreenElement se) {
		elements.add(se);
		repaint();
	}//add

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
	}//replace

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
	}//mouseReleased

	/**
	 * Loops through every element in the array and if the mouseX and mouseY are
	 * inside the ScreenElement it registers a click and sends it to the
	 * MouseHandler
	 * 
	 * @param mouseX the x coordinate you would like to check for a clickable object
	 * @param mouseY the y coordinate you would like to check for a clickable object
	 * @see MouseHandler
	 */
	public void whatClicked(int mouseX, int mouseY) {
		System.out.println(mouseX + " " + mouseY);
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
	}//whatClicked

	/**
	 * Gets the id of the last clicked ScreenElement, and sets newClick to false
	 * 
	 * @return The id of the last clicked ScreenElement
	 */
	public String getLastClick() {
		newClick = false;
		return lastClick;
	}//getLastClick

	/**
	 * 
	 * @return A copy of the object which was last clicked
	 */
	public Object getLastClickObject() {
		return lastClickObject;
	}//getLastClickObject

	/**
	 * 
	 * @return if the most recent click was "real" or not
	 */
	public boolean isNewClick() {
		return newClick;
	}//isNewClick

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
	}//getIndex

	/**
	 * @param id The id to get the ScreenElement of
	 * @return a copy of the ScreenElement at that id
	 */
	public ScreenElement getScreenElement(String id) {
		int index = getIndex(id);
		return elements.get(index);
	}//getScreenElement

	/**
	 * Saves the ArrayList elements to the given directory
	 * 
	 * @param file The directory to save the elements to
	 */
	public void saveElements(String file) {
		ScreenFile io = new ScreenFile();
		io.writeArrayList(elements, file);
	}//saveElements

	/**
	 * Reads the ArrayList elements to from given directory
	 * 
	 * @param file The directory to read the elements from
	 */
	public void loadElements(String file) {
		ScreenFile io = new ScreenFile();
		elements = io.readArrayList(file);
	}//loadElements

	/**
	 * Removes every item from the ArrayList elements, thus making the screen black
	 */
	public void clearScreen() {
		elements.clear();
	}//clearScreen

	/**
	 * Sets the background color of the window
	 * @param color The color to set the background to
	 */
	public void setBackgroundColor(Color color) {
		backgroundColor = color;
		repaint();
	}//setBackgroundColor
	
	/**
	 * 
	 * @return An array of each possible letter, and if it is currently pressed or not
	 */
	public boolean[] getKeyboard() {
		return keyboard;
	}//getKeyboard

	// The following methods are just here to appease MouseListener and
	// ActionListener
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