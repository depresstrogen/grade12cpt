import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Reads and writes stats into and from a save file following this format 
 * Line 0 - double money 
 * Lines 1-4 - boolean carsUnlocked[]
 * 
 * @author Riley Power
 * @version January 24, 2021
 *
 */
public class SaveFile {
	/**
	 * Saves whatever is in the objects ArrayList to a file
	 * 
	 * @param objects The ArrayList to save
	 * @param dir     The directory to save them to
	 */
	public void saveFile(ArrayList<Object> objects, String dir) {
		// Writes
		FileWriter out;
		BufferedWriter writeFile;

		try {
			out = new FileWriter(dir, false);
			writeFile = new BufferedWriter(out);
			for (int i = 0; i < objects.size(); i++) {
				if (i == 0) {
					writeFile.write(Double.toString((Double) (objects.get(i))));
				} else {
					writeFile.write(Boolean.toString((Boolean) (objects.get(i))));
				}
				writeFile.newLine();
			}

			writeFile.close();
			out.close();
		} catch (IOException e) {
			System.err.println("IOExeption: " + e.getMessage());
		}
	}// saveFile

	/**
	 * Reads the objects ArrayList from the file
	 * 
	 * @param dir The directory to read it from
	 * @return The ArrayList objects
	 */
	public ArrayList<Object> readFile(String dir) {
		FileReader in;
		FileWriter out;
		BufferedReader readFile;
		BufferedWriter writeFile;
		String lineOfText;
		ArrayList<Object> objects = new ArrayList<Object>();
		int j = 0;
		try {
			in = new FileReader(dir);
			readFile = new BufferedReader(in);
			while ((lineOfText = readFile.readLine()) != null) {
				if (j == 0) {
					objects.add(Double.parseDouble(lineOfText));
				} else {
					objects.add(Boolean.parseBoolean(lineOfText));
				}
				j++;
			}
			readFile.close();
			in.close();
		} catch (IOException e) {
			System.out.println("Problem reading file");
			System.err.println("IOExeption: " + e.getMessage());
		}
		return objects;
	}// readFile
}// SaveFile
