import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class SaveFile {
	public void saveFile(ArrayList<Object> objects, String dir) {
		// Writes
		FileReader in;
		FileWriter out;

		BufferedReader readFile;
		BufferedWriter writeFile;
		String lineOfText;
		try {
			out = new FileWriter(dir, false);
			writeFile = new BufferedWriter(out);
			for (int i = 0; i < objects.size(); i++) {
				writeFile.write(Double.toString((Double) (objects.get(i))));
				writeFile.newLine();
			}

			writeFile.close();
			out.close();
		} catch (IOException e) {
			System.err.println("IOExeption: " + e.getMessage());
		}
	}// Write Score

	public void readFile(String dir) {
		FileReader in;
		FileWriter out;
		BufferedReader readFile;
		BufferedWriter writeFile;
		String lineOfText;
		int j = 0;
		try {
			in = new FileReader(dir);
			readFile = new BufferedReader(in);

			readFile.close();
			in.close();
		} catch (IOException e) {
			System.out.println("Problem reading file");
			System.err.println("IOExeption: " + e.getMessage());
		}
	}
}
