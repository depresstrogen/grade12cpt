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
	}// Write Score

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
				if(j == 0) {
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
	}
}
