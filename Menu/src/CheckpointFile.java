import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class loads checkpoints from a .race file The file structure is as
 * follows <code>(Checkpoint Type) x1 y1 x2 y2 </code> as an example
 * <code> Start 100 100 400 400 </code> And loads the times to beat for them
 * 
 * @version January 23, 2021
 * @author Emma Power
 *
 */
public class CheckpointFile {
	/**
	 * Loads a .race file into a Checkpoint array list and returns it
	 * 
	 * @param dir The directory to read the checkpoints from
	 * @return An array with every checkpoint in the specified race
	 */
	public ArrayList<Checkpoint> readCheckpoints(String dir) {
		FileReader in;
		BufferedReader readFile;
		String lineOfText;
		ArrayList<Checkpoint> checkpoints = new ArrayList<Checkpoint>();
		int x1 = 0;
		int x2 = 0;
		int y1 = 0;
		int y2 = 0;
		String type = "";
		File checkpointFile = new File(dir);
		try {
			in = new FileReader(checkpointFile);
			readFile = new BufferedReader(in);
			int i = 0;
			// Runs until there is nothing left in the file
			while ((lineOfText = readFile.readLine()) != null) {
				int j = 0;
				int stringIndex = -1;
				// Parse each line
				do {
					lineOfText = lineOfText.substring(stringIndex + 1);
					stringIndex = lineOfText.indexOf(' ');
					// Each value has to be in a certain variable and this was the best way i could
					// do it
					switch (j) {
					case 0:
						type = lineOfText.substring(0, stringIndex);
						break;
					case 1:
						x1 = Integer.parseInt(lineOfText.substring(0, stringIndex));
						break;
					case 2:
						y1 = Integer.parseInt(lineOfText.substring(0, stringIndex));
						break;
					case 3:
						x2 = Integer.parseInt(lineOfText.substring(0, stringIndex));
						break;
					case 4:
						y2 = Integer.parseInt(lineOfText.substring(0));
						break;
					}
					j++;
				} while (j < 5);
				Checkpoint tempCheckpoint = new Checkpoint(x1, y1, y2 - y1, x2 - x1, "CP" + i, type);
				checkpoints.add(tempCheckpoint);
				i++;
			}
			// Memory leaks not pog
			readFile.close();
			in.close();
		} catch (IOException e) {
			// Errors not pog
			System.out.println("Problem reading file");
			System.err.println("IOExeption: " + e.getMessage());
		}
		return checkpoints;
	}// readCheckpoints

	/**
	 * Returns the time required (in seconds) to get bonus money from completing a
	 * race
	 * 
	 * @param race which race # to look for
	 * @return the maximum time to get bonus money from the race
	 */
	public double timeToBeat(int race) {
		FileReader in;
		BufferedReader readFile;
		String lineOfText;
		File timesFile = new File("Race Files/timestobeat.txt");
		try {
			in = new FileReader(timesFile);
			readFile = new BufferedReader(in);
			// Runs until there is nothing left in the file
			int i = 1;
			while ((lineOfText = readFile.readLine()) != null) {
				if (i == race) {
					readFile.close();
					in.close();
					return Double.parseDouble(lineOfText);
				}
				i++;
			}
			// Memory leaks not pog
			readFile.close();
			in.close();
		} catch (IOException e) {
			// Errors not pog
			System.out.println("Problem reading file");
			System.err.println("IOExeption: " + e.getMessage());
		}
		return -1;
	}// timeToBeat
}// Checkpoint File
