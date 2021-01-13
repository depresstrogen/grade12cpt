/*
 * @author Riley Power
 * @version January 9, 2021
 */

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Screen extends JPanel implements ActionListener, MouseListener {
	JFrame frame;
	
	
	ArrayList<ScreenElement> elements = new ArrayList<ScreenElement>();
	
	
	ArrayList<Object> objects = new ArrayList<Object>();
	
	String lastClick = "";
	Object lastClickObject;
	boolean newClick = false;
	
	MouseHandler mouse = new MouseHandler();
	
	public Screen(int height, int width) {
		frame = new JFrame("Screen");
		frame.add(this);
		frame.setSize(height, width);
		frame.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {
			}

			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				}
				if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				}
				if (e.getKeyCode() == KeyEvent.VK_UP) {
				}
				if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				}
				if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
				}
			}

			public void keyReleased(KeyEvent e) {
			}
		});
        addMouseListener(this);
        Image icon = Toolkit.getDefaultToolkit().getImage("icon.png");
        frame.setIconImage(icon);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}//Screen
	
	public void paint(Graphics g) {
		// Main Paint Loop
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		// Paints Board
		for (int i = 0; i < elements.size(); i++) {
			if (elements.get(i) instanceof Button) {
				Button button = (Button) elements.get(i);
				g2d.setColor(button.getColor());
				g2d.fillRect(button.getX(), button.getY(),button.getHeight(), button.getWidth());
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
		}
		repaint();
	}//Paint

	public void add(ScreenElement se) {
		elements.add(se);
		repaint();
	}//add
	
	public void replace(ScreenElement se, int index) {
		elements.set(index, se);
		repaint();
	}//replace
	
	public void mouseReleased(MouseEvent e) {
		int mouseX = e.getX();
		int mouseY = e.getY();
		whatClicked(mouseX, mouseY);
	}//mouseReleased

	
	/**
	 * 
	 * @param mouseX the x coordinate you would like to check for a clickable object
	 * @param mouseY the y coordinate you would like to check for a clickable object
	 */
	public void whatClicked(int mouseX, int mouseY) {
		System.out.println(mouseX + " " + mouseY);
		for (int i = 0; i < elements.size(); i++) {
			if (elements.get(i) instanceof Button) {
				Button button = (Button) elements.get(i);
				if(mouseX > button.getX() && mouseX < button.getX() + button.getHeight()
						&& mouseY > button.getY() && mouseY < button.getY() + button.getWidth()) {
						newClick = true;
					lastClick = button.getID();
					lastClickObject = button;
					mouse.mouseInputs(this);
				}
			}
		}
	}//whatClicked
	
	public String getLastClick() {
		newClick = false;
		return lastClick;
	}//getLastClick
	
	public Object getLastClickObject() {
		return lastClickObject;
	}//getLastClickObject
	
	public boolean isNewClick() {
		return newClick;
	}//isNewClick
	
	public int getIndex(String id) {
		int index = -1;
		for (int i = 0; i < elements.size(); i++) {
			ScreenElement element = elements.get(i);
			if(element.getID().equals(id)) {
				index = i;
				i = elements.size();
			}
		}
		return index;
	}//getIndex
	
	public ScreenElement getScreenElement(String id) {
		int index = getIndex(id);
		return elements.get(index);
	}
	
	public void saveElements(String file) {
		ScreenFile io = new ScreenFile();
		io.writeArrayList(elements, file);
	}
	
	public void loadElements(String file) {
		ScreenFile io = new ScreenFile();
		elements = io.readArrayList(file);
	
	}
	
	//The following methods are just here to appease MouseListener and ActionListener
	public void mouseEntered(MouseEvent e) {
	}//mouseEntered
	public void mouseExited(MouseEvent e) {
	}//mouseExited
	public void mouseClicked(MouseEvent e) {
	}//mouseClicked
	public void mousePressed(MouseEvent e) {
	}//mousePressed
	public void actionPerformed(ActionEvent a) {
	}//actionPerformed
	
}// Screen