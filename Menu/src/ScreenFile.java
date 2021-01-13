import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
//So eclipse doesn't give the yellow underline
@SuppressWarnings("unchecked")

public class ScreenFile {
	  public void writeArrayList(ArrayList<ScreenElement> array, String directory) {
			try {
				FileOutputStream fileOut = new FileOutputStream(directory);
				ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
				objectOut.writeObject((ArrayList<ScreenElement>) array);
				objectOut.close();
				fileOut.close();
				for (int i = 0; i < array.size(); i++) {
					ScreenElement element = (ScreenElement) array.get(i);
					
					
					System.out.print(element.getID() + "." + element.getX() + "." + element.getX() + ".");
					if (element instanceof Button) {
						System.out.println(((Button) element).getColor());
					}
					if (element instanceof Text) {
						System.out.println(((Text) element).getFont());
					}
					if (element instanceof Picture) {
						System.out.println(((Picture) element).getImage());
					}
					
					
				}
				System.out.println("ArrayList saved to " + directory);

			} catch (Exception e) {
				System.err.println("IOExeption: " + e.getMessage());
			}
		}//writeArrayList
	  
	  public ArrayList<ScreenElement> readArrayList(String directory) {
		  ArrayList<ScreenElement> array ;
		  try {
				FileInputStream fileIn = new FileInputStream(directory);
				ObjectInputStream objectIn = new ObjectInputStream(fileIn);
				ArrayList<ScreenElement> readObject = (ArrayList<ScreenElement>) objectIn.readObject();
				array = readObject;
				
				System.out.println("ArrayList read from " + directory + " successfully.");
				for (int i = 0; i < array.size(); i++) {
					ScreenElement element = (ScreenElement) array.get(i);
					System.out.print(element.getID() + "." + element.getX() + "." + element.getY() + ".");
					if (element instanceof Button) {
						System.out.println(((Button) element).getColor());
					}
					if (element instanceof Text) {
						System.out.println(((Text) element).getFont());
					}
					if (element instanceof Picture) {
						System.out.println(((Picture) element).getImage());
					}
				}
				
				objectIn.close();
			} catch (Exception e) {
				array = new ArrayList<ScreenElement>();
				System.err.println("IOExeption: " + e.getMessage());
			}
		  return array;
	  }//readArrayList
}
